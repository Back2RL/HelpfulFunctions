import javafx.util.Pair;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ImageUtils {

	public static Vector<Pair<String, Vector<List<Double>>>> tileImage(Vector<Pair<String, Vector<List<Double>>>> imageData, int tileWidth, int tileHeight) {
		Vector<Pair<String, Vector<List<Double>>>> tiledImageData = new Vector<>();

		for (Pair<String, Vector<List<Double>>> namedImageData : imageData) {
			String name = namedImageData.getKey();
			Vector<List<Double>> imageChannelData = namedImageData.getValue();

			assert imageChannelData.size() == 4 : "4 Channels were expected";
			for (int i = 0; i < imageChannelData.get(0).size(); i++) {
			// tile the Image
			}
		}


		return tiledImageData;
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
				RawImageData rawImageData = new RawImageData(image.getName(),bufferedImage.getWidth(), bufferedImage.getHeight(),imageData);
				outImageData.add(rawImageData);
			}
		}
	}
}
