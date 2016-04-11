import java.util.Scanner;

public class Indexer {

	public static void main(String[] args) {

		// check for valid number of arguments
		if (args.length != 2) {
			System.out.println("help: ./Indexer [path to index-directory] [path to analysis-directory]");
			throw new IllegalArgumentException("Invalid Arguments");
		}
		// print given arguments
		System.out.println("Given arguments: ");
		System.out.println("-----");
		System.out.println("Indexdirectory:    \"" + args[0] + "\"");
		System.out.println("Analysisdirectory: \"" + args[1] + "\"");
		System.out.println("-----");

		// create an IndeManager object
		IndexManager index = new IndexManager(args[0]);

		// try to open an existing index file
		if (index.loadIndex()) {
			String userInput;
			boolean bDoBackup;
			boolean bValidInput;
			Scanner console = null;
			do {
				System.out.print("Create Backup of existing Index? (y/n) : ");
				console = new Scanner(System.in);
				userInput = console.nextLine();
				bDoBackup = userInput.trim().toLowerCase().equals("y");
				bValidInput = userInput.trim().toLowerCase().equals("n");
			} while (!bDoBackup && !bValidInput);
			if (console != null)
				console.close();
			if (bDoBackup) {
				System.out.println("Creating Backup of Indexfile.");
				index.createIndexBackup();
			}

			// index.printIndex();
		} else {
			System.out.println("File not found: A new index file will be created");
		}
		index.analyzeDirecetory(args[1]);
	}
}
