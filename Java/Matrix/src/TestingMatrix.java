
public class TestingMatrix {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MatrixMN b = new MatrixMN(3, 3);
		double number = 1.0 / Math.sqrt(2.0);
		b.setElem(0, 0, number);
		b.setElem(0, 1, 0);
		b.setElem(0, 2, -number);
		b.setElem(1, 0, 0);
		b.setElem(1, 1, 1);
		b.setElem(1, 2, 0);
		b.setElem(2, 0, number);
		b.setElem(2, 1, 0);
		b.setElem(2, 2, number);

		MatrixMN c = new MatrixMN(2, 2);
		c.setElem(0, 0, 1);
		c.setElem(0, 1, 2);
		c.setElem(1, 0, 1);
		c.setElem(1, 1, 2);

		System.out.println(b.multiply(b).toStringMatForm());
		System.out.println(b.toStringMatForm());
		System.out.println(c.multiply(c).toStringMatForm());
		System.out.println(b.multiply(b).toStringMatForm());

		int m = 1000;
		int n = 1000;
		// O(n³)
		// 10000x10000 mit 8 Threads -> 2.7GB RAM
		System.out.println(m + "x" + n + "-Matrix: Generate");
		MatrixMN testMat = new MatrixMN(m, n);
		for (int i = 0; i < m; ++i) {
			for (int j = 0; j < n; ++j) {
				testMat.setElem(i, j, Math.random() * 1E-9);
			}
		}

		System.out.println(m + "x" + n + "-Matrix: Transpone");
		MatrixMN transponedMat = testMat.getTransponed();

		TimeMeasure timer = new TimeMeasure();
		timer.startTimer();

		MatrixMN result = testMat.multiplyThreaded(transponedMat, 1);
		// testMat.multiply(transponedMat);
		// MatrixMN result = testMat;
		timer.stopTimer();

		if (result != null)
			// System.out.println(result.toStringMatForm());
			System.out.println("1. Element = " + result.getElem(0, 0));
		System.out.println(b.multiplyThreaded(b, 3).toStringMatForm());
		System.out.println("normal = \n" + b.multiply(b).toStringMatForm());
	}

}
