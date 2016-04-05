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
	private File indexDirectory;
	private File index;
	private String indexDirectoryPath;
	private Map<String, TreeSet<File>> indexFromFile = new TreeMap<String, TreeSet<File>>();

	public Map<String, TreeSet<File>> getIndexFromFile() {
		return indexFromFile;
	}

	IndexManager(String pathToIndexDirectory) {
		try {
			indexDirectory = new File(pathToIndexDirectory);
			if (!indexDirectory.isDirectory()) {
				throw new IllegalArgumentException("Given path does not point to a directory");
			}
			indexDirectoryPath = indexDirectory.getAbsolutePath() + File.separatorChar;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	boolean loadIndex() {
		indexPath = indexDirectoryPath + indexFileName;
		System.out.println(indexPath);
		index = new File(indexPath);
		if (index.exists()) {
			return true;
		} else {
			return false;
		}
	}

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

		for (File file : indexDirectory.listFiles()) {
			System.out.println(file.getName());
		}
	}

	String timeNowToString() {
		LocalDateTime date = LocalDateTime.now();
		String timeString = date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		LocalDateTime parsedDate = LocalDateTime.parse(timeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		timeString = timeString.replaceAll(":", "-");
		timeString = timeString.replaceAll("\\.", "-");
		return timeString;
	}
}
