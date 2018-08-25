import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class ImageUtils {

	public static Vector<RawImageData> tileImage(Vector<RawImageData> imageData, int tileWidth, int tileHeight) {
		Vector<RawImageData> tiledImageData = new Vector<>();

		for (RawImageData rawImageData : imageData) {

			int tileColumns = rawImageData.getWidth() / tileWidth;
			int columnOverhang = rawImageData.getWidth() % tileWidth;
			int tileRows = rawImageData.getHeight() / tileHeight;
			int rowOverhang = rawImageData.getHeight() % tileHeight;

			double[][] pixels = rawImageData.getChannels_RGBA();

			System.out.println(tileColumns + "x" + tileRows + " Tiles for the image");


			// TODO: edge case if overhang of row/column != 0

			for (int row = 0; row < tileRows; ++row) { // for each tile row
				System.out.println("Row");
				for (int column = 0; column < tileColumns; ++column) { // for each tile column
					System.out.println("Column");
					Vector<List<Double>> tiledPixelData = new Vector<>();
					List<Double> channelDataR = new ArrayList<>();
					List<Double> channelDataG = new ArrayList<>();
					List<Double> channelDataB = new ArrayList<>();
					List<Double> channelDataA = new ArrayList<>();
					tiledPixelData.add(channelDataR);
					tiledPixelData.add(channelDataG);
					tiledPixelData.add(channelDataB);
					tiledPixelData.add(channelDataA);
					for (int y = row * tileHeight; y < (row + 1) * tileHeight; ++y) { // for all vertical pixels in that tile
						for (int x = column * tileWidth; x < (column + 1) * tileWidth; ++x) { // for all horizontal pixels in that tile
							//System.out.println(y*rawImageData.getWidth() + x);
							channelDataR.add(pixels[0][y * rawImageData.getWidth() + x]);
							channelDataG.add(pixels[1][y * rawImageData.getWidth() + x]);
							channelDataB.add(pixels[2][y * rawImageData.getWidth() + x]);
							channelDataA.add(pixels[3][y * rawImageData.getWidth() + x]);
						}
					}
					RawImageData tiledRawImageData = new RawImageData(rawImageData.getName() + "_" + row + "x" + column + ".png", tileWidth, tileHeight, tiledPixelData, column, row, rawImageData.getWidth(), rawImageData.getHeight());
					tiledImageData.add(tiledRawImageData);
				}
			}
		}
		System.out.println(tiledImageData.size() + " Tiles generated");

		return tiledImageData;
	}

	public static Vector<RawImageData> stitchImages(Vector<RawImageData> imageData) {
		Vector<RawImageData> result = new Vector<>();
		Set<String> alreadyProcessed = new HashSet<>();

		for (int i = 0; i < imageData.size(); i++) {

			RawImageData rawImageData = imageData.get(0);

			if (rawImageData.getTileColumnIndex() == RawImageData.NOT_TILED || rawImageData.getTileRowIndex() == RawImageData.NOT_TILED) {
				continue;
			}

			String name = rawImageData.getName();
			name = name.replace("_" + rawImageData.getTileRowIndex() + "x" + rawImageData.getTileColumnIndex() + ".png", "");
			System.out.println(name);

			if (alreadyProcessed.contains(name)) {
				continue;
			}
			alreadyProcessed.add(name);

			int tileWidth = rawImageData.getWidth();
			int tileHeight = rawImageData.getHeight();
			int imageWidth = rawImageData.getUntiledOrigWidth();
			int imageHeight = rawImageData.getUntiledOrigHeight();

			RawImageData untiledImage = new RawImageData(name + "_untiled.png", imageWidth, imageHeight, null, RawImageData.NOT_TILED, RawImageData.NOT_TILED, imageWidth, imageHeight);
			double[][] untiledPixelData = untiledImage.getChannels_RGBA();
			System.out.println("Pixels in Image " + untiledPixelData[0].length + "; Should be " + untiledImage.pixelCount());
			result.add(untiledImage);

			for (RawImageData otherRawImageData : imageData) {
				if (otherRawImageData.getName().startsWith(name)) {

					//System.out.println("Pixels in Tile " + otherRawImageData.getChannels_RGBA()[0].length + "; Should be " + otherRawImageData.pixelCount());

					for (int channelIndex = 0; channelIndex < otherRawImageData.getChannels_RGBA().length; channelIndex++) {
						double[] channelData = otherRawImageData.getChannels_RGBA()[channelIndex];

						for (int pixelIndex = 0; pixelIndex < channelData.length; pixelIndex++) {

							int rowOffsetWholeRows = imageWidth * tileHeight * otherRawImageData.getTileRowIndex();
							int rowOffsetpartialRow = imageWidth * (pixelIndex / tileWidth);

							//System.out.println("Rowoffset " + rowOffsetWholeRows);
							int column = tileWidth * otherRawImageData.getTileColumnIndex() + pixelIndex % tileWidth;
							//System.out.println("column " + column);


//							*************
//							*************
//							****XXX------
//							----XXX------


							untiledPixelData[channelIndex][rowOffsetWholeRows + rowOffsetpartialRow + column] = channelData[pixelIndex];
						}
					}
				}
			}
		}


		return result;
	}

	/**
	 * @param outImageData separated into one list per channel in order RGBA, converted byte to double 0..1.0
	 * @param imageFiles
	 * @throws IOException
	 */
	public static void loadImageData(Vector<RawImageData> outImageData, File[] imageFiles) throws IOException {
		if (imageFiles != null) {
			for (File image : imageFiles) {
				Vector<List<Double>> imageData = new Vector<>();
				BufferedImage bufferedImage = ImageIO.read(image);
				int channels = 3;
				if (bufferedImage.getColorModel().hasAlpha()) {
					channels = 4;
				}
				double[] pixelData = new double[channels * bufferedImage.getHeight() * bufferedImage.getWidth()];
				bufferedImage.getData().getPixels(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), pixelData);

				List<Double> data = new ArrayList<>();
				for (int i = 0; i < pixelData.length; i += channels) {
					data.add(pixelData[i] / 255.0);
				}
				imageData.add(data);

				data = new ArrayList<>();
				for (int i = 1; i < pixelData.length; i += channels) {
					data.add(pixelData[i] / 255.0);
				}
				imageData.add(data);

				data = new ArrayList<>();
				for (int i = 2; i < pixelData.length; i += channels) {
					data.add(pixelData[i] / 255.0);
				}
				imageData.add(data);

				data = new ArrayList<>();
				if (channels == 4) {
					for (int i = 3; i < pixelData.length; i += channels) {
						data.add(pixelData[i] / 255.0);
					}
				} else {
					for (int i = 0; i < pixelData.length; i += channels) {
						data.add(1.0);
					}
				}
				imageData.add(data);
				RawImageData rawImageData = new RawImageData(image.getName(), bufferedImage.getWidth(), bufferedImage.getHeight(), imageData, RawImageData.NOT_TILED, RawImageData.NOT_TILED, bufferedImage.getWidth(), bufferedImage.getHeight());
				outImageData.add(rawImageData);
			}
		}
	}
}
