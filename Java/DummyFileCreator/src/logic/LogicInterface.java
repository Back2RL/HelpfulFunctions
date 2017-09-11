package logic;

import java.io.File;

public interface LogicInterface {

	public boolean setOriginalsDir(final File newOriginalsDir);
	public boolean setDummiesDir(final File newDummiesDir);
	public boolean createDummies();

}
