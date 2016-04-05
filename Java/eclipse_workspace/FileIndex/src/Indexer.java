
public class Indexer {

	public static void main(String[] args) {
		// print given arguments
		System.out.println("Given arguments: ");
		System.out.println("-----");
		for (String arg : args) {
			System.out.println(arg);
		}
		System.out.println("-----");

		// check for valid number of arguments
		if (args.length != 2) {
			System.out.println("help: ./Indexer [path to index-directory] [path to analysis-directory]");
			throw new IllegalArgumentException("Invalid Arguments");
		}

		// create an IndeManager object
		IndexManager index = new IndexManager(args[0]);

		// try to open an existing index file
		if (index.loadIndex()) {
			System.out.println("Index file found: Creating Backup");
			index.createIndexBackup();
		} else {
			System.out.println("File not found: A new index file will be created");
		}
	}
}
