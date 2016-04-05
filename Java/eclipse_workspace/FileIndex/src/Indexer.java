
public class Indexer {

	public static void main(String[] args) {

		if (args.length != 2) {
			System.out.println("help: ./Indexer [path to index-directory] [path to analysis-directory]");
			throw new IllegalArgumentException("Invalid Arguments");
		}

		IndexManager index = new IndexManager(args[0]);
		if (index.loadIndex()) {
			index.createIndexBackup();
		} else {
			System.out.println("Index will be created");
		}
	}
}
