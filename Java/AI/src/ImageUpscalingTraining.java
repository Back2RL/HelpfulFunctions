import javafx.util.Pair;
import neuralnet.Training;
import neuralnet.net.NeuronNet;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class ImageUpscalingTraining {

	final static int TRAINING_RUNS = 100;
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


		Vector<Pair<String,Vector<List<Double>>>> lowResImageData = new Vector<>();// contains Lists for each channel in order ARGB
		Vector<Pair<String,Vector<List<Double>>>> highResImageData =new Vector<>(); // contains Lists for each channel in order ARGB

		ImageUtils.loadImageData(lowResImageData, lowResDir.listFiles());
		ImageUtils.loadImageData(highResImageData, highResDir.listFiles());

		int[] topology = new int[]{lowResImageData.get(0).getValue().size(),
//				128,
				highResImageData.get(0).getValue().size()};

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



	private static void train(File outputDir, Vector<Pair<String, Vector<List<Double>>>> lowResImageData,  Vector<Pair<String, Vector<List<Double>>>> highResImageData, NeuronNet net) throws IOException {
		Random rand = new Random();
		for (int i = 0; i < TRAINING_RUNS; ++i) {

			if (RANDOM_TRAINING) {
				int imageIndex = rand.nextInt(lowResImageData.size());
				int channelIndex = rand.nextInt(lowResImageData.size());
				Training.singleIteration(net, lowResImageData.get(imageIndex).getValue().get(channelIndex), highResImageData.get(imageIndex).getValue().get(channelIndex));
			} else {
				for (int imageIndex = 0; imageIndex < lowResImageData.size(); imageIndex++) {
					Vector<List<Double>> lowResImageChannelData = lowResImageData.get(imageIndex).getValue();
					Vector<List<Double>> highResImageChannelData = highResImageData.get(imageIndex).getValue();
					for (int channelIndex = 0; channelIndex < lowResImageChannelData.size(); channelIndex++) {
						assert lowResImageChannelData.size() == 4 : "lowResImageChannelData size is not 4";
						assert highResImageChannelData.size() == 4 : "highResImageChannelData size is not 4";
						Training.singleIteration(net,lowResImageChannelData.get(channelIndex) , highResImageChannelData.get(channelIndex));
					}
				}
			}
			if (i % UPDATE_INTERVAL == 0) {
				writeImages(outputDir, lowResImageData, highResImageData, net);
				System.out.println(net.getError());
			}
		}
	}

	private static void writeImages(File outputDir, Vector<Pair<String, Vector<List<Double>>>> lowResImageData, Vector<Pair<String, Vector<List<Double>>>> highResImageData, NeuronNet net) throws IOException {
		for (int imageIndex = 0; imageIndex < lowResImageData.size(); imageIndex += 4) {
			List<Double> result0 = Training.getResultFromInput(net, lowResImageData.get(imageIndex).getValue().get(0));
			List<Double> result1 = Training.getResultFromInput(net, lowResImageData.get(imageIndex).getValue().get(1));
			List<Double> result2 = Training.getResultFromInput(net, lowResImageData.get(imageIndex).getValue().get(2));
			List<Double> result3 = Training.getResultFromInput(net, lowResImageData.get(imageIndex).getValue().get(3));
			BufferedImage outImage = new BufferedImage(HIGH_RES_WIDTH, HIGH_RES_HEIGHT, BufferedImage.TYPE_INT_ARGB);


			int index = 0;
			for (int y = 0; y < HIGH_RES_HEIGHT; ++y) {
				for (int x = 0; x < HIGH_RES_WIDTH; ++x) {
					int r = 0;
					int g = 0;
					int b = 0;
					int a = 255;


					r = (int) (255.5 * Math.min(Math.max(result0.get(index), 0.0), 1.0));
					g = (int) (255.5 * Math.min(Math.max(result1.get(index), 0.0), 1.0));
					b = (int) (255.5 * Math.min(Math.max(result2.get(index), 0.0), 1.0));
					a = (int) (255.5 * Math.min(Math.max(result3.get(index), 0.0), 1.0));
					int argb = a << 24 | r << 16 | g << 8 | b;
					outImage.setRGB(x, y, argb);
					index++;
				}
			}

			File outputfile = new File(outputDir.getAbsolutePath() + File.separator + lowResImageData.get(imageIndex).getKey());
			ImageIO.write(outImage, "png", outputfile);

			BufferedImage outHighResImage = new BufferedImage(HIGH_RES_WIDTH, HIGH_RES_HEIGHT, BufferedImage.TYPE_INT_ARGB);

			result0 = highResImageData.get(imageIndex).getValue().get(0);
			result1 = highResImageData.get(imageIndex).getValue().get(1);
			result2 = highResImageData.get(imageIndex).getValue().get(2);
			result3 = highResImageData.get(imageIndex).getValue().get(3);
			index = 0;
			for (int y = 0; y < HIGH_RES_HEIGHT; ++y) {
				for (int x = 0; x < HIGH_RES_WIDTH; ++x) {
					int r = 0;
					int g = 0;
					int b = 0;
					int a = 255;


					r = (int) (255.5 * result0.get(index));
					g = (int) (255.5 * result1.get(index));
					b = (int) (255.5 * result2.get(index));
					a = (int) (255.5 * result3.get(index));
					int argb = a << 24 | r << 16 | g << 8 | b;
					outHighResImage.setRGB(x, y, argb);
					index++;
				}
			}

			File outputHighResfile = new File(outputDir.getAbsolutePath() + File.separator + highResImageData.get(imageIndex).getKey());
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
