import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class IndexManager {

	private final String indexFileName = "index.csv";

	private String indexPath;
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
		// invariant 1
		if (pathToIndexDirectory == null || pathToIndexDirectory.equals("")) {
			throw new IllegalArgumentException("Given path must not be empty");
		}
		// invariant 2
		try {
			indexDirectory = new File(pathToIndexDirectory);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!indexDirectory.isDirectory()) {
			throw new IllegalArgumentException("Given path does not point to a directory");
		}
		indexDirectoryPath = indexDirectory.getAbsolutePath() + File.separatorChar;
	}

	/**
	 * attempts to load the index file
	 * 
	 * @return true when a file exists
	 */
	boolean loadIndex() {
		indexPath = indexDirectoryPath + indexFileName;
		System.out.println("Looking for file: \"" + indexPath + "\"");
		index = new File(indexPath);
		if (index.exists()) {
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
					new File(indexDirectoryPath + fileName + "_" + timeNowToString() + fileExt).toPath());
		} catch (FileAlreadyExistsException e) {
			System.out.println("There is already a valid backup file");
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Listing all files in directory: \"" + indexDirectoryPath + "\"");
		System.out.println("-----");
		for (File file : indexDirectory.listFiles()) {
			System.out.println(file.getName());
		}
		System.out.println("-----");
	}

	/**
	 * @return a String containing a timestamp with current date and time,
	 *         format: year-month-dayThour-minute-second-millisecond
	 */
	String timeNowToString() {
		LocalDateTime date = LocalDateTime.now();
		String timeString = date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		LocalDateTime parsedDate = LocalDateTime.parse(timeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		timeString = timeString.replaceAll(":", "-");
		timeString = timeString.replaceAll("\\.", "-");
		return timeString;
	}
}