package SortingBySelection;

import java.awt.image.BufferedImage;

/**
 * Created by leonard on 09.11.16.
 */
public class LoadedImage {
	private String path;
	private BufferedImage mImage;

	public LoadedImage(String path, BufferedImage image) {
		this.path = path;
		mImage = image;
	}

	public BufferedImage getImage() {
		return mImage;
	}

	public String getPath() {
		return path;
	}
}
