
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

		System.out.println(b.multiplyThreaded(b, 2).toStringMatForm());

		int m = 100;
		int n = 10000;
		System.out.println(m + "x" + n + "-Matrix: ");
		MatrixMN testMat = new MatrixMN(m, n);
		for (int i = 0; i < m; ++i) {
			for (int j = 0; j < n; ++j) {
				testMat.setElem(i, j, Math.random());
			}
		}
		MatrixMN transponedMat = testMat.getTransponed();

		TimeMeasure timer = new TimeMeasure();
		timer.startTimer();

		MatrixMN result = testMat.multiplyThreaded(transponedMat, 4);
		// testMat.multiply(transponedMat);

		timer.stopTimer();

		if (result != null)
			// System.out.println(result.toStringMatForm());
			System.out.println("1. Element = " + result.getElem(0, 0));
	}

}
