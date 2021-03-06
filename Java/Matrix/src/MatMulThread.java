
public class MatMulThread extends Thread {
	private MatrixMN left;
	private MatrixMN other;
	private MatrixMN result;
	private int firstLine;
	private int lastLine;

	/**
	 * @param left
	 * @param other
	 * @param firstLine
	 * @param lastLine
	 */
	public MatMulThread(MatrixMN left, MatrixMN other, int firstLine, int lastLine) {
		this.left = left;
		this.other = other;
		this.firstLine = firstLine;
		this.lastLine = lastLine;
	}

	public MatrixMN getResult() {
		return result;
	}

	public void run() {
		// TODO Auto-generated method stub
		if (MatrixMN.DEBUG)
			System.out.println(Thread.currentThread().getName() + ": " + (lastLine - firstLine) + "x" + left.getN());

		if (other == null || left == null || left.getN() != other.getM()) {
			System.err.println("Matrix multiplication failed: leftMat = " + other + "; rightMat = " + other);
			return;
		}
		MatrixMN result = new MatrixMN(lastLine - firstLine, other.getN());

		for (int line = firstLine; line < lastLine; ++line) {
			for (int row = 0; row < other.getN(); ++row) {
				for (int i = 0; i < left.getN(); ++i) {
					result.setElem(line - firstLine, row,
							result.getElem(line - firstLine, row) + left.getElem(line, i) * other.getElem(i, row));
				}
			}
		}
		this.result = result;
	}
}
