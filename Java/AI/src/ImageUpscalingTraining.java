import neuralnet.Training;
import neuralnet.net.NeuronNet;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Random;
import java.util.Vector;

public class ImageUpscalingTraining {

	final static int TRAINING_RUNS = 3;
	final static boolean RANDOM_TRAINING = false;

	final static int UPDATE_INTERVAL = 10;

	final static int COLOR_CHANNEL_OFFSET = 0; // 0..3
	final static int COLOR_CHANNEL_SKIPPING = 4;

	final static int LOW_RES_WIDTH = 9;
	final static int LOW_RES_HEIGHT = 9;

	final static int HIGH_RES_WIDTH = 18;
	final static int HIGH_RES_HEIGHT = 18;

	public static void main(String[] args) throws IOException {

		Vector<RawImageData> lowResImageData = new Vector<>();
		Vector<RawImageData> highResImageData = new Vector<>();

		NeuronNet neuronNet = null;

		createDirectoryIfNotExists(new File("./training"));

		File highResDir = new File("./training/highRes");
		File lowResDir = new File("./training/lowRes");
		File results = new File("./training/results");

		createDirectoryIfNotExists(highResDir);
		createDirectoryIfNotExists(lowResDir);
		createDirectoryIfNotExists(results);


		ImageUtils.loadImageData(highResImageData, highResDir.listFiles());
		ImageUtils.loadImageData(lowResImageData, lowResDir.listFiles());


		int[] topology = new int[]{LOW_RES_WIDTH * LOW_RES_HEIGHT,
				LOW_RES_WIDTH * LOW_RES_HEIGHT,
				HIGH_RES_WIDTH*HIGH_RES_HEIGHT};

		neuronNet = NeuronNet.loadNeuralNet(topology);

		//trainNeuralNetOnData(lowResImageData, highResImageData, neuronNet, results,false);

		//System.exit(0);

		Vector<RawImageData> untiledLowResImageData = new Vector<>();
		Vector<RawImageData> untiledHighResImageData = new Vector<>();

		File untiledLowResImages = new File("./training/untiledLowResImages");
		File tiledLowResImages = new File("./training/tiledLowResImages");
		File untiledHighResImages = new File("./training/untiledHighResImages");
		File tiledHighResImages = new File("./training/tiledHighResImages");
		File finalTestResult = new File("./training/finalTestResult");

		createDirectoryIfNotExists(untiledLowResImages);
		createDirectoryIfNotExists(tiledLowResImages);
		createDirectoryIfNotExists(untiledHighResImages);
		createDirectoryIfNotExists(tiledHighResImages);
		createDirectoryIfNotExists(finalTestResult);

		ImageUtils.loadImageData(untiledLowResImageData, untiledLowResImages.listFiles());
		ImageUtils.loadImageData(untiledHighResImageData, untiledHighResImages.listFiles());

		lowResImageData = ImageUtils.tileImage(untiledLowResImageData, LOW_RES_WIDTH, LOW_RES_HEIGHT);
		highResImageData = ImageUtils.tileImage(untiledHighResImageData, HIGH_RES_WIDTH, HIGH_RES_HEIGHT);

		Vector<RawImageData> toUpscale = ImageUtils.tileImage(untiledHighResImageData, LOW_RES_WIDTH, LOW_RES_HEIGHT);


		//writeImagesToDir(tiledLowResImages, lowResImageData, lowResImageData.size());
		//writeImagesToDir(tiledHighResImages, highResImageData, highResImageData.size());

		for (int i = 0; i < 100 ; ++i) {
			trainNeuralNetOnData(lowResImageData, highResImageData, neuronNet, finalTestResult, false);

			Vector<RawImageData> upscaledTiles = new Vector<>();
			generateHighResTiles(toUpscale, neuronNet, upscaledTiles);

			Vector<RawImageData> stitchedImages = ImageUtils.stitchImages(upscaledTiles);
			writeImagesToDir(finalTestResult, stitchedImages, stitchedImages.size());
		}
	}

	private static void generateHighResTiles(Vector<RawImageData> lowResImageData, NeuronNet neuronNet, Vector<RawImageData> upscaledTiles) {
		for (RawImageData tile : lowResImageData) {
			RawImageData upscaledTile = new RawImageData(tile.getName(), tile.getWidth() * (HIGH_RES_WIDTH/LOW_RES_WIDTH), tile.getHeight() * (HIGH_RES_HEIGHT/LOW_RES_HEIGHT), null, tile.getTileColumnIndex(), tile.getTileRowIndex(), tile.getUntiledOrigWidth() * (HIGH_RES_WIDTH/LOW_RES_WIDTH), tile.getUntiledOrigHeight() * (HIGH_RES_HEIGHT/LOW_RES_HEIGHT));
			upscaledTiles.add(upscaledTile);
			for (int i = 0; i < tile.getChannels_RGBA().length; i++) {
				neuronNet.feedForward(tile.getChannels_RGBA()[i]);
				upscaledTile.getChannels_RGBA()[i] = neuronNet.getResults();
				for (int i1 = 0; i1 < upscaledTile.getChannels_RGBA()[i].length; i1++) {
					upscaledTile.getChannels_RGBA()[i][i1] = Math.min(Math.max(upscaledTile.getChannels_RGBA()[i][i1], 0.0), 1.0);
				}
			}
		}
	}

	private static void trainNeuralNetOnData(Vector<RawImageData> lowResImageData, Vector<RawImageData> highResImageData, NeuronNet neuronNet, File results, boolean writeImagesToFile) throws IOException {
		if(writeImagesToFile)writeImagesToDir(results, highResImageData, highResImageData.size()); // write High-res files
		train(results, lowResImageData, highResImageData, neuronNet,writeImagesToFile);
		if(writeImagesToFile) generateAndWriteHighResTilesToDir(results, lowResImageData, lowResImageData.size(), neuronNet);
		System.out.println("Error = " + neuronNet.getError());
		System.out.println("Recent Error = " + neuronNet.getRecentAverageError());
		NeuronNet.saveNeuralNet(neuronNet);
	}


	private static void train(File outputDir, Vector<RawImageData> lowResImageData, Vector<RawImageData> highResImageData, NeuronNet net, boolean writeImagesToFile) throws IOException {
		Random rand = new Random();
		for (int i = 0; i < TRAINING_RUNS; ++i) {

			if (RANDOM_TRAINING) {
				int imageIndex = rand.nextInt(lowResImageData.size());
				int channelIndex = rand.nextInt(4);
				Training.singleIteration(net, lowResImageData.get(imageIndex).getChannels_RGBA()[channelIndex], highResImageData.get(imageIndex).getChannels_RGBA()[channelIndex]);
			} else {
				for (int imageIndex = 0; imageIndex < lowResImageData.size(); imageIndex++) {
					double[][] lowResImageChannelData = lowResImageData.get(imageIndex).getChannels_RGBA();
					double[][] highResImageChannelData = highResImageData.get(imageIndex).getChannels_RGBA();
					for (int channelIndex = 0; channelIndex < lowResImageChannelData.length; channelIndex++) {
						Training.singleIteration(net, lowResImageChannelData[channelIndex], highResImageChannelData[channelIndex]);
					}
				}
			}
			if (i % UPDATE_INTERVAL == 0 && writeImagesToFile) {
				generateAndWriteHighResTilesToDir(outputDir, lowResImageData, lowResImageData.size(), net);
				NeuronNet.saveNeuralNet(net);
				System.out.println("Run : " + i);
				System.out.println("Error = " + net.getError());
				System.out.println("Recent Error = " + net.getRecentAverageError());

			}
		}
	}


	private static void generateAndWriteHighResTilesToDir(File outputDir, Vector<RawImageData> lowResImageData, int imageCount, NeuronNet net) throws IOException {
		double[] result0;
		double[] result1;
		double[] result2;
		double[] result3;
		int index;

		for (int imageIndex = 0; imageIndex < imageCount; imageIndex++) {
			if (lowResImageData != null) {
				result0 = Training.getResultFromInput(net, lowResImageData.get(imageIndex).getChannels_RGBA()[0]);
				result1 = Training.getResultFromInput(net, lowResImageData.get(imageIndex).getChannels_RGBA()[1]);
				result2 = Training.getResultFromInput(net, lowResImageData.get(imageIndex).getChannels_RGBA()[2]);
				result3 = Training.getResultFromInput(net, lowResImageData.get(imageIndex).getChannels_RGBA()[3]);
				BufferedImage outImage = new BufferedImage(HIGH_RES_WIDTH, HIGH_RES_HEIGHT, BufferedImage.TYPE_INT_ARGB);


				index = 0;
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
			}
		}
	}

	private static void writeImagesToDir(File outputDir, Vector<RawImageData> imageData, int imageCount){
		double[] result0;
		double[] result1;
		double[] result2;
		double[] result3;
		int index;

		for (int imageIndex = 0; imageIndex < imageCount; imageIndex++) {
			if (imageData != null) {
				BufferedImage outHighResImage = new BufferedImage(imageData.get(imageIndex).getWidth(), imageData.get(imageIndex).getHeight(), BufferedImage.TYPE_INT_ARGB);

				result0 = imageData.get(imageIndex).getChannels_RGBA()[0];
				result1 = imageData.get(imageIndex).getChannels_RGBA()[1];
				result2 = imageData.get(imageIndex).getChannels_RGBA()[2];
				result3 = imageData.get(imageIndex).getChannels_RGBA()[3];
				index = 0;
				for (int y = 0; y < imageData.get(imageIndex).getHeight(); ++y) {
					for (int x = 0; x < imageData.get(imageIndex).getWidth(); ++x) {
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

				File outputHighResfile = new File(outputDir.getAbsolutePath() + File.separator + imageData.get(imageIndex).getName()+"_AI_UPSCALED.png");
				try {
					ImageIO.write(outHighResImage, "png", outputHighResfile);
				} catch (IOException e) {
					e.printStackTrace();
				}
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
