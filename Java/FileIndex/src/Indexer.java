import java.io.File;
import java.util.Scanner;

public class Indexer {

	public static final boolean DEBUG = false;

	public static void main(String[] args) {

		// check for valid number of arguments
		if (args.length != 2) {
			System.out.println("help: ./Indexer [path to index-directory] [path to analysis-directory]");
			throw new IllegalArgumentException("Invalid Arguments");
		}

		// make sure the given paths end with system specific sperator char
		String indexDir = shallEndWithSuffix(args[0], "" + File.separatorChar);
		String analyzeDir = shallEndWithSuffix(args[1], "" + File.separatorChar);

		// print given paths
		System.out.println("Given arguments: ");
		System.out.println("-----");
		System.out.println("Indexdirectory:    \"" + indexDir);
		System.out.println("Analysisdirectory: \"" + analyzeDir);
		System.out.println("-----");

		// create an IndeManager object
		IndexManager index = new IndexManager(indexDir);

		// try to open an existing index file
		if (index.loadIndex()) {
			try (Scanner console = new Scanner(System.in)) {
				// while no valid user input received continue asking for input
				while (true) {
					System.out.print("Create Backup of existing Index? (yes: y; no: n; exit: x) : ");
					String userInput = console.nextLine().trim().toLowerCase();

					// create backup of existing index
					if (userInput.equals("y")) {
						System.out.println("Creating Backup of Indexfile.");
						index.createIndexBackup();
						break;
					}

					// no backup, break
					if (userInput.equals("n")) {
						break;
					}

					// exit
					if (userInput.equals("x")) {
						System.out.println("Exit");
						System.exit(0);
					}
				}
			} catch (Exception e) {
				// in case anything happens
				e.printStackTrace();
			}

			if (DEBUG) {
				index.printIndex();
			}
		} else {
			System.out.println("File not found: A new index file will be created");
		}

		// look for not indexed files/duplicates and create new index file
		index.analyzeDirectory(analyzeDir);
		index.saveIndex();
	}

	public static String shallEndWithSuffix(String in, String suffix) {
		if (!in.endsWith(suffix))
			return in + suffix;
		return in;
	}
}