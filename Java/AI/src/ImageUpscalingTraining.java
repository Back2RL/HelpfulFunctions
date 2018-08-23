import neuralnet.Training;
import neuralnet.net.NeuronNet;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ImageUpscalingTraining {

	final static int TRAINING_RUNS = 10000;

	final static int UPDATE_INTERVAL = 100;

	final static int COLOR_CHANNEL_OFFSET = 0; // 0..3
	final static int COLOR_CHANNEL_SKIPPING = 4;

	final static int LOW_RES_WIDTH = 8;
	final static int LOW_RES_HEIGHT = 8;

	final static int HIGH_RES_WIDTH = 16;
	final static int HIGH_RES_HEIGHT = 16;


	public static void main(String[] args) throws IOException {

		File lowResDir = new File("./training/lowRes");
		File highResDir = new File("./training/highRes");
		File results = new File("./training/results");

		createDirectoryIfNotExists(new File("./training"));
		createDirectoryIfNotExists(lowResDir);
		createDirectoryIfNotExists(highResDir);
		createDirectoryIfNotExists(results);


		Vector<List<Double>> lowResImageData = new Vector<>();
		Vector<List<Double>> highResImageData = new Vector<>();

		loadImageData(lowResImageData, lowResDir.listFiles());
		loadImageData(highResImageData, highResDir.listFiles());

		int[] topology = new int[]{lowResImageData.get(0).size(),
				//256,
				highResImageData.get(0).size()};
		NeuronNet net = new NeuronNet(topology);

		for (int i = 0; i < TRAINING_RUNS; ++i) {

			for (int imageIndex = 0; imageIndex < lowResImageData.size(); ++imageIndex) {
				List<Double> result = Training.singleIteration(net, lowResImageData.get(imageIndex), highResImageData.get(imageIndex));
			}
			if(i%UPDATE_INTERVAL == 0){
				writeImages(lowResImageData, highResImageData, net);
			}

			System.out.println(net.getError());
		}
		writeImages(lowResImageData, highResImageData, net);
		System.out.println(net.getError());




		System.out.println("Sleeping");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


	}

	private static void writeImages(Vector<List<Double>> lowResImageData, Vector<List<Double>> highResImageData, NeuronNet net) throws IOException {
		for (int imageIndex = 0; imageIndex < lowResImageData.size(); ++imageIndex) {
			List<Double> result = Training.singleIteration(net, lowResImageData.get(imageIndex), highResImageData.get(imageIndex));
			BufferedImage outImage = new BufferedImage(HIGH_RES_WIDTH,HIGH_RES_HEIGHT, BufferedImage.TYPE_INT_ARGB);



			int index = 0;
			for (int y = 0; y < HIGH_RES_HEIGHT; ++y) {
				for (int x = 0; x < HIGH_RES_WIDTH; ++x) {
					int r = 0;
					int g = 0;
					int b = 0;
					int a = 255;

					double value = Math.min(Math.max(result.get(index),0),1);
					 r = (int) (255.0 * value);
					 //g = (int) (255.0 * result.get(index));
					 //b = (int) (255.0 * value);
					 //a = (int) (255.0 - 255.0 * result.get(index + 3));
					int argb = a<< 24 |r << 16 | g << 8 | b;
					outImage.setRGB(x, y, argb);
					index +=5-COLOR_CHANNEL_SKIPPING;
				}
			}

			File outputfile = new File("./training/results/image" + imageIndex + ".png");
			ImageIO.write(outImage, "png", outputfile);
		}
	}

	private static void loadImageData(Vector<List<Double>> outImageData, File[] imageFiles) throws IOException {
		if (imageFiles != null) {
			for (File image : imageFiles) {
				BufferedImage bufferedImage = ImageIO.read(image);
				double[] pixelData = new double[4 *bufferedImage.getHeight()*bufferedImage.getWidth()];
				bufferedImage.getData().getPixels(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), pixelData);
				List<Double> data = new ArrayList<>();
				for (int i = COLOR_CHANNEL_OFFSET; i < pixelData.length; i+=COLOR_CHANNEL_SKIPPING) {
					data.add(pixelData[i] / 255.0);
				}
				outImageData.add(data);
			}
		}
	}


	private static void createDirectoryIfNotExists(File dir) {
		if (!dir.exists()) {
			try {
				Files.createDirectory(dir.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
