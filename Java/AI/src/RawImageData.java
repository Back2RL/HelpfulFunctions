import java.util.List;
import java.util.Vector;

public class RawImageData {

	public static final int NOT_TILED = -1;

	private final String name;
	private final int width;
	private final int height;
	private final int tileColumnIndex;
	private final int tileRowIndex;
	private final double[][] channels_RGBA;

	public int getUntiledOrigWidth() {
		return untiledOrigWidth;
	}

	public int getUntiledOrigHeight() {
		return untiledOrigHeight;
	}

	private final int untiledOrigWidth;
	private final int untiledOrigHeight;

	public RawImageData(String name, int width, int height, Vector<List<Double>> imageData, int tileColumnIndex, int tileRowIndex, int untiledOrigWidth, int untiledOrigHeight) {

		this.name = name;
		this.width = width;
		this.height = height;
		this.tileColumnIndex = tileColumnIndex;
		this.tileRowIndex = tileRowIndex;
		this.untiledOrigWidth = untiledOrigWidth;
		this.untiledOrigHeight = untiledOrigHeight;

		channels_RGBA = new double[4][];
		int pixelCount = pixelCount();
		channels_RGBA[0] = new double[pixelCount];
		channels_RGBA[1] = new double[pixelCount];
		channels_RGBA[2] = new double[pixelCount];
		channels_RGBA[3] = new double[pixelCount];

		if(imageData != null) {
			assert imageData.size() == 4 : "4 Channels expected, " + imageData.size() + "exist!";


			for (int pixelIndex = 0; pixelIndex < imageData.get(0).size(); pixelIndex++) {
				channels_RGBA[0][pixelIndex] = imageData.get(0).get(pixelIndex);
			}
			for (int pixelIndex = 0; pixelIndex < imageData.get(1).size(); pixelIndex++) {
				channels_RGBA[1][pixelIndex] = imageData.get(1).get(pixelIndex);
			}
			for (int pixelIndex = 0; pixelIndex < imageData.get(2).size(); pixelIndex++) {
				channels_RGBA[2][pixelIndex] = imageData.get(2).get(pixelIndex);
			}
			for (int pixelIndex = 0; pixelIndex < imageData.get(3).size(); pixelIndex++) {
				channels_RGBA[3][pixelIndex] = imageData.get(3).get(pixelIndex);
			}
		}

	}

	public String getName() {
		return name;
	}

	public int getTileColumnIndex() {
		return tileColumnIndex;
	}

	public int getTileRowIndex() {
		return tileRowIndex;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public double[][] getChannels_RGBA() {
		return channels_RGBA;
	}

	public int pixelCount(){
		return width*height;
	}
}
