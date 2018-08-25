import java.util.List;
import java.util.Vector;

public class RawImageData {
	private final int width;
	private final int height;

	private final double[][] channel_R;
	private final double[][] channel_G;
	private final double[][] channel_B;
	private final double[][] channel_A;

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public double[][] getChannel_R() {
		return channel_R;
	}

	public double[][] getChannel_G() {
		return channel_G;
	}

	public double[][] getChannel_B() {
		return channel_B;
	}

	public double[][] getChannel_A() {
		return channel_A;
	}

	public RawImageData(int width, int height, Vector<List<Double>> imageData) {
		this.width = width;
		this.height = height;

		channel_R = new double[height][width];
		channel_G = new double[height][width];
		channel_B = new double[height][width];
		channel_A = new double[height][width];

		assert imageData.size() == 4 : "4 Channels expected, " + imageData.size() + "exist!";
		for (int pixelIndex = 0; pixelIndex < imageData.get(0).size(); pixelIndex++) {
			channel_R[pixelIndex / height][pixelIndex%width] = imageData.get(0).get(pixelIndex);
		}
		for (int pixelIndex = 0; pixelIndex < imageData.get(1).size(); pixelIndex++) {
			channel_G[pixelIndex / height][pixelIndex%width] = imageData.get(0).get(pixelIndex);
		}
		for (int pixelIndex = 0; pixelIndex < imageData.get(2).size(); pixelIndex++) {
			channel_B[pixelIndex / height][pixelIndex%width] = imageData.get(0).get(pixelIndex);
		}
		for (int pixelIndex = 0; pixelIndex < imageData.get(3).size(); pixelIndex++) {
			channel_A[pixelIndex / height][pixelIndex%width] = imageData.get(0).get(pixelIndex);

		}
	}
}
