import java.util.Arrays;

public class MatrixMN {
	private double[][] matrix;

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

}
