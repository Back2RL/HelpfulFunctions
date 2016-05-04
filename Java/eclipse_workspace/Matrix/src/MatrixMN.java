import java.util.Arrays;

public class MatrixMN extends Thread {
	public static final boolean DEBUG = true;
	private double[][] matrix;
	private MatrixMN other;
	private MatrixMN result;

	public void run() {
		// TODO Auto-generated method stub
		if (DEBUG)
			System.out.println(getM() + "x" + getN());
		result = multiply(other);
	}

	public synchronized double[][] getMatrix() {
		return matrix;
	}

	public synchronized void setMatrix(double[][] matrix) {
		this.matrix = matrix;
	}

	/**
	 * Copy Constructor
	 * 
	 * @param matrix
	 */
	public MatrixMN(double[][] matrix) {
		this.matrix = matrix;
	}

	public void setElem(int x, int y, double value) {

		matrix[x][y] = value;
	}

	public double getElem(int x, int y) {
		return matrix[x][y];
	}

	public MatrixMN(int m, int n) {
		this.matrix = new double[m][n];
	}

	public static MatrixMN identity(int m, int n) {
		MatrixMN id = new MatrixMN(m, n);
		for (int i = 0; i < Math.min(m, n); ++i) {
			id.setElem(i, i, 1.0);
		}
		return id;
	}

	public int getM() {
		return matrix.length;
	}

	public int getN() {
		return matrix[0].length;
	}

	public int rg() {
		// TODO: Rang ausrechnen
		return -1;
	}

	public MatrixMN add(MatrixMN b) {
		if (this.getM() != b.getM() || this.getN() != b.getN()) {
			return null;
		}
		MatrixMN result = new MatrixMN(this.getM(), this.getN());
		for (int x = 0; x < this.getM(); ++x) {
			for (int y = 0; x < this.getN(); ++y) {
				result.setElem(x, y, this.getElem(x, y) + b.getElem(x, y));
			}
		}
		return result;
	}

	public void transpone() {
		MatrixMN result = new MatrixMN(getN(), getM());
		for (int x = 0; x < this.getM(); ++x) {
			for (int y = 0; x < this.getN(); ++y) {
				result.setElem(y, x, getElem(x, y));
			}
		}
		matrix = result.getMatrix();
	}

	public MatrixMN getTransponed() {
		MatrixMN result = new MatrixMN(getN(), getM());
		for (int m = 0; m < this.getM(); ++m) {
			for (int n = 0; n < this.getN(); ++n) {
				result.setElem(n, m, getElem(m, n));
			}
		}
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("MatrixMN [matrix=");
		for (int i = 0; i < getM(); ++i) {
			sb.append(Arrays.toString(matrix[i]));
		}
		sb.append("]");
		return sb.toString();
	}

	public String toStringMatForm() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < getM(); ++i) {
			sb.append(Arrays.toString(matrix[i]) + "\n");
		}
		return sb.toString();
	}

	public MatrixMN multiply(MatrixMN other) {
		if (getN() != other.getM() || other == null)
			return null;
		MatrixMN result = new MatrixMN(getM(), other.getN());

		for (int zeileRes = 0; zeileRes < getM(); ++zeileRes) {
			for (int spalteRes = 0; spalteRes < other.getN(); ++spalteRes) {
				for (int i = 0; i < getN(); ++i) {
					result.matrix[zeileRes][spalteRes] += matrix[zeileRes][i] * other.matrix[i][spalteRes];
				}
			}
		}
		return result;
	}

	public MatrixMN(double[][] matrix, MatrixMN other) {
		this.matrix = matrix;
		this.other = other;
	}

	public MatrixMN multiplyThreaded(MatrixMN other, int maxThreads) {
		if (getN() != other.getM() || other == null)
			return null;

		maxThreads = Math.max(Math.min(maxThreads, getM()), 1);
		if (DEBUG)
			System.out.println("using " + maxThreads + " Threads");

		MatrixMN[] threaded = new MatrixMN[maxThreads];

		int elems = getM() / maxThreads;
		if (DEBUG)
			System.out.println(elems + " line" + ((elems == 1) ? "" : "s") + " per Thread");
		int alreadyAsigned = 0;
		for (int i = 0; i < maxThreads; ++i) {

			if (i == maxThreads - 1) {
				threaded[i] = new MatrixMN(Arrays.copyOfRange(matrix, alreadyAsigned, getM()), other);
				if (DEBUG)
					System.out.println("last Thread");
				if (DEBUG)
					System.out.println("Asigned lines = " + (alreadyAsigned + (getM() - alreadyAsigned)));

			} else {
				threaded[i] = new MatrixMN(Arrays.copyOfRange(matrix, alreadyAsigned, alreadyAsigned + elems), other);
				alreadyAsigned += elems;
				if (DEBUG)
					System.out.println("Thread No " + (i + 1));
				if (DEBUG)
					System.out.println("Asigned lines = " + alreadyAsigned);

			}

		}
		for (int i = 0; i < maxThreads; ++i) {
			threaded[i].start();
		}
		int resultLines = 0;
		double[][] resultOfMultiplication = new double[getM()][];
		for (int i = 0; i < maxThreads; ++i) {
			if (DEBUG)
				System.out.println("Waiting for result " + (i + 1));
			try {
				threaded[i].join();
				for (int j = 0; j < threaded[i].result.getM(); j++) {
					resultOfMultiplication[resultLines] = threaded[i].result.matrix[j];
					++resultLines;
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		MatrixMN result = new MatrixMN(resultOfMultiplication);
		return result;
	}
}
