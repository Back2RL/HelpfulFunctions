package logic;

import java.io.File;

public class Settings {
	private File lastBrowserDir = null;
	private File originalsDir = null;
	private File dummiesDir = null;

	public File getOriginalsDir() {
		return originalsDir;
	}

	public void setOriginalsDir(final File originalsDir) {
		this.originalsDir = originalsDir;
	}

	public File getDummiesDir() {
		return dummiesDir;
	}

	public void setDummiesDir(final File dummiesDir) {
		this.dummiesDir = dummiesDir;
	}

	public File getLastBrowserDir() {
		return lastBrowserDir;
	}

	public void setLastBrowserDir(File lastBrowserDir) {
		this.lastBrowserDir = lastBrowserDir;
	}
}
