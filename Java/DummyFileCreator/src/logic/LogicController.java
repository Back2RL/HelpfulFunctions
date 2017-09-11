package logic;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogicController implements LogicInterface {

	private Settings settings = null;

	private static LogicController ourInstance = new LogicController();

	public static LogicController getInstance() {
		return ourInstance;
	}

	private LogicController() {
		// TODO: load/validate from XML File
		settings = new Settings();

	}

	@Override
	public boolean setOriginalsDir(final File newOriginalsDir) {
		if (isValidDirectory(newOriginalsDir)) {
			settings.setOriginalsDir(newOriginalsDir);
			return true;
		}
		return false;
	}

	@Override
	public boolean setDummiesDir(final File newDummiesDir) {
		if (isValidDirectory(newDummiesDir)) {
			settings.setDummiesDir(newDummiesDir);
			return true;
		}
		return false;
	}

	private boolean isValidDirectory(final File directory) {
		return directory != null && directory.isDirectory() && directory.canRead();
	}

	@Override
	public boolean createDummies() {

		int created = 0;
		int skipped = 0;
		int failed = 0;

		File originals = settings.getOriginalsDir();
		File dummies = settings.getDummiesDir();

		if (!isValidDirectory(originals)) {
			Logger.getGlobal().log(Level.WARNING, "Creation of Dummies failed: " +
					"invalid Originals Directory");
			return false;
		}
		if (!isValidDirectory(dummies)) {
			Logger.getGlobal().log(Level.WARNING, "Creation of Dummies failed: " +
					"invalid Dummies Directory");
			return false;
		}
		if (originals.equals(dummies)) {
			Logger.getGlobal().log(Level.WARNING, "Creation of Dummies failed: " +
					"Dummy and Original Directory are the same");
			return false;
		}

		File[] childFiles = originals.listFiles();
		if (childFiles == null || childFiles.length == 0) {
			Logger.getGlobal().log(Level.WARNING, "Creation of Dummies failed: " +
					"Originals Directory contains no Files");
			return false;
		}
		Logger.getGlobal().log(Level.INFO, "Starting creation of Dummies.\n" +
				"Source: " + originals.getPath() + "\n" +
				"Target: " + dummies.getPath());
		for (File childFile : childFiles) {
			File newDummy = new File(dummies.getPath() + File.separator + childFile.getName());
			Logger.getGlobal().log(Level.INFO, "creating Dummy for " + childFile.getName() + ":\n"
					+ newDummy.getPath());
			try {
				if (!newDummy.exists()) {
					if (newDummy.createNewFile()) {
						Logger.getGlobal().log(Level.INFO, "created Dummy: " + newDummy.getPath());
						++created;
					} else {
						Logger.getGlobal().log(Level.WARNING, "Dummy-File could not be created: " +
								newDummy.getPath());
						++failed;
					}
				} else {
					++skipped;
					Logger.getGlobal().log(Level.INFO, "skipped Dummy: " +
							newDummy.getPath() + "\nFile exists.");
				}
			} catch (IOException e) {
				e.printStackTrace();
				++failed;
			}
		}
		Logger.getGlobal().log(Level.INFO, "Creation of Dummies finished.\n" +
				"  total: " + childFiles.length + "\ncreated: " + created +
				"\nskipped: " + skipped + "\n failed: " + failed);
		return true;
	}
}
