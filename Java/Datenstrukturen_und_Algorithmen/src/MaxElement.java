import java.util.Random;

// example for Divide and Conquer
public class MaxElement {
	private final static int testSize = 100000000;

	public static void main(String[] args) {
		int[] zahlen = { 1, 2, 3, 451, 5, 6, 1, 23, 1, 222 };
		int[] data = getRandomData(testSize);
		long start = System.currentTimeMillis();
		int max = maxDaC(data, 0, testSize - 1);
		System.out.println(
				"divide and conquer algorithm: " + (System.currentTimeMillis() - start) / 1000.0 + " seconds, " + max);
		start = System.currentTimeMillis();
		max = max(data);
		System.out.println("simple loop: " + (System.currentTimeMillis() - start) / 1000.0 + " seconds, " + max);

	}

	public static int[] getRandomData(int size) {
		int[] ret = new int[size];
		Random r = new Random();
		for (int i = 0; i < ret.length; i++) {
			ret[i] = r.nextInt();
		}
		return ret;
	}

	public static int max(int[] a) {
		int ret = Integer.MIN_VALUE;
		for (int i : a) {
			if (i > ret)
				ret = i;
		}
		return ret;
	}

	public static int maxDaC(int[] a, int start, int end) {
		if (start == end) {
			return a[start];
		} else {
			int left = maxDaC(a, start, (start + end) / 2);
			int right = maxDaC(a, (start + end) / 2 + 1, end);
			return (left >= right) ? left : right;
		}
	}
}
