import neuralnet.Training;
import neuralnet.net.NeuronNet;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class ImageUpscalingTraining {

	final static int TRAINING_RUNS = 10000;
	final static boolean RANDOM_TRAINING = false;

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

		File finalTestLowRes = new File("./training/finalTestLowRes");
		File finalTestHighRes = new File("./training/finalTestHighRes");
		File finalTestResult = new File("./training/finalTestResult");

		createDirectoryIfNotExists(new File("./training"));
		createDirectoryIfNotExists(lowResDir);
		createDirectoryIfNotExists(highResDir);
		createDirectoryIfNotExists(results);

		createDirectoryIfNotExists(finalTestLowRes);
		createDirectoryIfNotExists(finalTestResult);


		Vector<List<Double>> lowResImageData = new Vector<>(); // contains Lists for each channel in order ARGB
		Vector<List<Double>> highResImageData = new Vector<>(); // contains Lists for each channel in order ARGB

		loadImageData(lowResImageData, lowResDir.listFiles());
		loadImageData(highResImageData, highResDir.listFiles());

		int[] topology = new int[]{lowResImageData.get(0).size(),
//				128,
				highResImageData.get(0).size()};

		NeuronNet net = new NeuronNet(topology);

		train(results, lowResImageData, highResImageData, net);
		writeImages(results,lowResImageData, highResImageData, net);
		System.out.println(net.getError());

		lowResImageData = new Vector<>();
		highResImageData = new Vector<>();

		loadImageData(lowResImageData, finalTestLowRes.listFiles());
		loadImageData(highResImageData, finalTestHighRes.listFiles());
		writeImages(finalTestResult,lowResImageData, highResImageData, net);
	}

	private static void train(File outputDir, Vector<List<Double>> lowResImageData, Vector<List<Double>> highResImageData, NeuronNet net) throws IOException {
		Random rand = new Random();
		for (int i = 0; i < TRAINING_RUNS; ++i) {

			if(RANDOM_TRAINING){
				int index = rand.nextInt(lowResImageData.size());
				Training.singleIteration(net, lowResImageData.get(index), highResImageData.get(index));
			}else {
				for (int imageIndex = 0; imageIndex < lowResImageData.size(); imageIndex+=4) {
					List<Double> result = Training.singleIteration(net, lowResImageData.get(imageIndex), highResImageData.get(imageIndex));
				}
			}
			if (i % UPDATE_INTERVAL == 0) {
				writeImages(outputDir, lowResImageData, highResImageData, net);
				System.out.println(net.getError());
			}
		}
	}

	private static void writeImages(File outputDir, Vector<List<Double>> lowResImageData, Vector<List<Double>> highResImageData, NeuronNet net) throws IOException {
		for (int imageIndex = 0; imageIndex < lowResImageData.size(); imageIndex+=4) {
			List<Double> result0 = Training.getResultFromInput(net, lowResImageData.get(imageIndex));
			List<Double> result1 = Training.getResultFromInput(net, lowResImageData.get(imageIndex+1));
			List<Double> result2 = Training.getResultFromInput(net, lowResImageData.get(imageIndex+2));
			List<Double> result3 = Training.getResultFromInput(net, lowResImageData.get(imageIndex+3));
			BufferedImage outImage = new BufferedImage(HIGH_RES_WIDTH, HIGH_RES_HEIGHT, BufferedImage.TYPE_INT_ARGB);


			int index = 0;
			for (int y = 0; y < HIGH_RES_HEIGHT; ++y) {
				for (int x = 0; x < HIGH_RES_WIDTH; ++x) {
					int r = 0;
					int g = 0;
					int b = 0;
					int a = 255;


					r = (int) (255.0 * Math.min(Math.max(result0.get(index), 0), 1));
					g = (int) (255.0 * Math.min(Math.max(result1.get(index), 0), 1));
					b = (int) (255.0 * Math.min(Math.max(result2.get(index), 0), 1));
					a = (int) (255.0 * Math.min(Math.max(result3.get(index), 0), 1));
					int argb = a << 24 | r << 16 | g << 8 | b;
					outImage.setRGB(x, y, argb);
					index++;
				}
			}

			File outputfile = new File(outputDir.getAbsolutePath()+File.separator+"image" + imageIndex + ".png");
			ImageIO.write(outImage, "png", outputfile);

			BufferedImage outHighResImage = new BufferedImage(HIGH_RES_WIDTH, HIGH_RES_HEIGHT, BufferedImage.TYPE_INT_ARGB);

			result0 = highResImageData.get(imageIndex);
			result1 = highResImageData.get(imageIndex+1);
			result2 = highResImageData.get(imageIndex+2);
			result3 = highResImageData.get(imageIndex+3);

			index = 0;
			for (int y = 0; y < HIGH_RES_HEIGHT; ++y) {
				for (int x = 0; x < HIGH_RES_WIDTH; ++x) {
					int r = 0;
					int g = 0;
					int b = 0;
					int a = 255;


					r = (int) (255.0 * result0.get(index));
					g = (int) (255.0 * result1.get(index));
					b = (int) (255.0 * result2.get(index));
					a = (int) (255.0 * result3.get(index));
					int argb = a << 24 | r << 16 | g << 8 | b;
					outHighResImage.setRGB(x, y, argb);
					index++;
				}
			}

			File outputHighResfile = new File(outputDir.getAbsolutePath()+File.separator+"image" + imageIndex + "_target.png");
			ImageIO.write(outHighResImage, "png", outputHighResfile);
		}
	}

	private static void loadImageData(Vector<List<Double>> outImageData, File[] imageFiles) throws IOException {
		if (imageFiles != null) {
			for (File image : imageFiles) {
				BufferedImage bufferedImage = ImageIO.read(image);
				int channels = 3;
				if(bufferedImage.getColorModel().hasAlpha()){
					channels = 4;
				}
				double[] pixelData = new double[channels * bufferedImage.getHeight() * bufferedImage.getWidth()];
				bufferedImage.getData().getPixels(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), pixelData);

				List<Double> data = new ArrayList<>();
				for (int i = 0; i < pixelData.length; i+=channels) {
					data.add(pixelData[i] / 255.0);
				}
				outImageData.add(data);

				data = new ArrayList<>();
				for (int i = 1; i < pixelData.length; i+=channels) {
					data.add(pixelData[i] / 255.0);
				}
				outImageData.add(data);

				data = new ArrayList<>();
				for (int i = 2; i < pixelData.length; i+=channels) {
					data.add(pixelData[i] / 255.0);
				}
				outImageData.add(data);

				data = new ArrayList<>();
				if(channels == 4) {
					for (int i = 3; i < pixelData.length; i += channels) {
						data.add(pixelData[i] / 255.0);
					}
				} else {
					for (int i = 0; i < pixelData.length; i += channels) {
						data.add(1.0);
					}
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

	// Todo: Image slicer and combiner!

}
