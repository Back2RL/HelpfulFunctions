import javafx.util.Pair;
import neuralnet.Training;
import neuralnet.net.NeuronNet;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.*;

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


		Vector<RawImageData> lowResImageData = new Vector<>();// contains Lists for each channel in order ARGB
		Vector<RawImageData> highResImageData =new Vector<>(); // contains Lists for each channel in order ARGB

		ImageUtils.loadImageData(lowResImageData, lowResDir.listFiles());
		ImageUtils.loadImageData(highResImageData, highResDir.listFiles());

		int[] topology = new int[]{lowResImageData.get(0).pixelCount(),
//				128,
				highResImageData.get(0).pixelCount()};

		NeuronNet net;

		net = NeuronNet.loadNeuralNet(topology);

		train(results, lowResImageData, highResImageData, net);
		writeImages(results, lowResImageData, highResImageData, net);
		System.out.println(net.getError());

		lowResImageData = new Vector<>();
		highResImageData = new Vector<>();

		ImageUtils.loadImageData(lowResImageData, finalTestLowRes.listFiles());
		ImageUtils.loadImageData(highResImageData, finalTestHighRes.listFiles());
		writeImages(finalTestResult, lowResImageData, highResImageData, net);

		NeuronNet.saveNeuralNet(net);
	}



	private static void train(File outputDir, Vector<RawImageData> lowResImageData,  Vector<RawImageData> highResImageData, NeuronNet net) throws IOException {
		Random rand = new Random();
		for (int i = 0; i < TRAINING_RUNS; ++i) {

			if (RANDOM_TRAINING) {
				int imageIndex = rand.nextInt(lowResImageData.size());
				int channelIndex = rand.nextInt(4);
				Training.singleIteration(net, lowResImageData.get(imageIndex).getChannels_RGBA()[channelIndex],highResImageData.get(imageIndex).getChannels_RGBA()[channelIndex]);
			} else {
				for (int imageIndex = 0; imageIndex < lowResImageData.size(); imageIndex++) {
					double[][] lowResImageChannelData = lowResImageData.get(imageIndex).getChannels_RGBA();
					double[][] highResImageChannelData = highResImageData.get(imageIndex).getChannels_RGBA();
					for (int channelIndex = 0; channelIndex < lowResImageChannelData.length; channelIndex++) {
						Training.singleIteration(net,lowResImageChannelData[channelIndex] , highResImageChannelData[channelIndex]);
					}
				}
			}
			if (i % UPDATE_INTERVAL == 0) {
				writeImages(outputDir, lowResImageData, highResImageData, net);
				System.out.println(net.getError());
			}
		}
	}

	private static void writeImages(File outputDir, Vector<RawImageData> lowResImageData, Vector<RawImageData> highResImageData, NeuronNet net) throws IOException {
		for (int imageIndex = 0; imageIndex < lowResImageData.size(); imageIndex += 4) {
			double[] result0 = Training.getResultFromInput(net, lowResImageData.get(imageIndex).getChannels_RGBA()[0]);
			double[] result1 = Training.getResultFromInput(net, lowResImageData.get(imageIndex).getChannels_RGBA()[1]);
			double[] result2 = Training.getResultFromInput(net, lowResImageData.get(imageIndex).getChannels_RGBA()[2]);
			double[] result3 = Training.getResultFromInput(net, lowResImageData.get(imageIndex).getChannels_RGBA()[3]);
			BufferedImage outImage = new BufferedImage(HIGH_RES_WIDTH, HIGH_RES_HEIGHT, BufferedImage.TYPE_INT_ARGB);


			int index = 0;
			for (int y = 0; y < HIGH_RES_HEIGHT; ++y) {
				for (int x = 0; x < HIGH_RES_WIDTH; ++x) {
					int r = 0;
					int g = 0;
					int b = 0;
					int a = 255;


					r = (int) (255.5 * Math.min(Math.max(result0[index], 0.0), 1.0));
					g = (int) (255.5 * Math.min(Math.max(result1[index], 0.0), 1.0));
					b = (int) (255.5 * Math.min(Math.max(result2[index], 0.0), 1.0));
					a = (int) (255.5 * Math.min(Math.max(result3[index], 0.0), 1.0));
					int argb = a << 24 | r << 16 | g << 8 | b;
					outImage.setRGB(x, y, argb);
					index++;
				}
			}

			File outputfile = new File(outputDir.getAbsolutePath() + File.separator + lowResImageData.get(imageIndex).getName());
			ImageIO.write(outImage, "png", outputfile);

			BufferedImage outHighResImage = new BufferedImage(HIGH_RES_WIDTH, HIGH_RES_HEIGHT, BufferedImage.TYPE_INT_ARGB);

			result0 = highResImageData.get(imageIndex).getChannels_RGBA()[0];
			result1 = highResImageData.get(imageIndex).getChannels_RGBA()[1];
			result2 = highResImageData.get(imageIndex).getChannels_RGBA()[2];
			result3 = highResImageData.get(imageIndex).getChannels_RGBA()[3];
			index = 0;
			for (int y = 0; y < HIGH_RES_HEIGHT; ++y) {
				for (int x = 0; x < HIGH_RES_WIDTH; ++x) {
					int r = 0;
					int g = 0;
					int b = 0;
					int a = 255;


					r = (int) (255.5 * result0[index]);
					g = (int) (255.5 * result1[index]);
					b = (int) (255.5 * result2[index]);
					a = (int) (255.5 * result3[index]);
					int argb = a << 24 | r << 16 | g << 8 | b;
					outHighResImage.setRGB(x, y, argb);
					index++;
				}
			}

			File outputHighResfile = new File(outputDir.getAbsolutePath() + File.separator + highResImageData.get(imageIndex).getName());
			ImageIO.write(outHighResImage, "png", outputHighResfile);
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
