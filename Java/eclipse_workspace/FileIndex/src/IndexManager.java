import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class IndexManager {

	private boolean debug = false;
	private final String indexFileName = "index.csv";
	private String indexDirectoryPath;
	private File index;
	private File indexDirectory;
	private Map<String, TreeSet<File>> indexFromFile = new TreeMap<String, TreeSet<File>>();

	/**
	 * @param pathToIndexDirectory
	 * @throws IllegalArgumentException
	 *             given path has to point to a directory and must not be
	 *             empty/null
	 */
	IndexManager(String pathToIndexDirectory) {
		indexDirectory = getDirectory(pathToIndexDirectory);
		indexDirectoryPath = getDirectory(pathToIndexDirectory).getAbsolutePath() + File.separatorChar;
	}

	private File getDirectory(String path) {
		// invariant 1
		if (path == null || path.equals("")) {
			throw new IllegalArgumentException("Given path must not be empty");
		}
		// invariant 2
		File file = null;
		try {
			file = new File(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!file.isDirectory()) {
			throw new IllegalArgumentException("Given path does not point to a directory");
		}
		return file;
	}

	/**
	 * attempts to load the index file
	 * 
	 * @return true when a file exists
	 */
	boolean loadIndex() {
		String indexPath = indexDirectoryPath + indexFileName;
		System.out.println("Looking for file: \"" + indexPath + "\"");
		index = new File(indexPath);
		if (index.exists()) {
			scanIndex();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * creates a backup of the index file, modified with a timestamp
	 */
	void createIndexBackup() {
		int dotIndex = indexFileName.lastIndexOf(".");
		String fileName = null;
		String fileExt = "";
		if (dotIndex == -1) {
			fileName = indexFileName;
		} else {
			fileName = indexFileName.substring(0, dotIndex);
			fileExt = indexFileName.substring(dotIndex);
		}

		try {
			Files.copy(index.toPath(),
					new File(indexDirectoryPath + fileName + "_" + Time.timeNowToString() + fileExt).toPath());
		} catch (FileAlreadyExistsException e) {
			System.out.println("There is already a valid backup file");
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (debug) {
			System.out.println("Listing all files in directory: \"" + indexDirectoryPath + "\"");
			System.out.println("-----");
			for (File file : indexDirectory.listFiles()) {
				System.out.println(file.getName());
			}
		}
		System.out.println("-----");
	}

	/**
	 * load content of indexfile into memory
	 */
	private void scanIndex() {
		Scanner indexScanner = null;
		try {
			indexScanner = new Scanner(index);
			int lineCnt = 0;
			System.out.println("-----");
			while (indexScanner.hasNextLine()) {
				++lineCnt;

				String line = indexScanner.nextLine();
				if (debug)
					System.out.println(line);

				String[] lineContent = line.split(",");
				if (lineContent.length < 2) {
					System.out.println("No valid index in line: " + lineCnt);
					continue;
				}
				if (debug)
					System.out.println(Arrays.toString(lineContent));
				TreeSet<File> entries = new TreeSet<>();
				for (int i = 1; i < lineContent.length; ++i) {
					entries.add(new File(lineContent[i]));
				}
				indexFromFile.put(lineContent[0], entries);

			}
			System.out.println("-----");
			System.out.println("Indexfile loaded.");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (indexScanner != null) {
				indexScanner.close();
			}
		}

	}

	public void printIndex() {
		for (String key : indexFromFile.keySet()) {
			int cnt = 0;
			for (File file : indexFromFile.get(key)) {
				++cnt;
				System.out.println(key + ": " + cnt + ". " + file.getAbsolutePath());
			}
		}
		System.out.println();
	}

	public void analyzeDirecetory(String dir) {
		File analysisDir = getDirectory(dir);
		System.out.println("Starting analysis of: \"" + analysisDir + "\"");

		ArrayList<File> pendingDirectories = new ArrayList<>();
		ArrayList<File> allFiles = new ArrayList<>();

		while (true) {
			for (File currentFile : analysisDir.listFiles()) {
				if (currentFile.isDirectory()) {
					pendingDirectories.add(currentFile);
				} else {
					allFiles.add(currentFile);
				}
			}
			if (!pendingDirectories.isEmpty()) {
				analysisDir = pendingDirectories.get(0);
				pendingDirectories.remove(0);
			} else {
				break;
			}
		}
		// System.out.println(allFiles.toString());
		System.out.println("-----");
		System.out.println("Number of found files: " + allFiles.size());

		int alreadyIndexed = 0;
		for (File file : allFiles) {
			if (indexFromFile.containsKey(file.toString())) {
				++alreadyIndexed;
				System.out.println("Dup found");
				continue;
			}
			TreeSet<File> entries = new TreeSet<>();
			indexFromFile.put(file.toString(), entries);
		}

		System.out.println("Number of already indexed files: " + alreadyIndexed);

		PrintWriter writer = null;
		try {
			writer = new PrintWriter(indexDirectoryPath + indexFileName, "UTF-8");

			for (String key : indexFromFile.keySet()) {
				writer.print(key);
				if (indexFromFile.get(key) != null) {
					for (File file : indexFromFile.get(key)) {
						writer.print("," + file.toString());
					}
				}
				writer.println();
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
		System.out.println("New index has beem written");

	}
}
