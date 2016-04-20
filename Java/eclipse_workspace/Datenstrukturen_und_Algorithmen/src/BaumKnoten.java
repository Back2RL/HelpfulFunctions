
public class BaumKnoten {
	public BaumKnoten left;
	public BaumKnoten right;

	public BaumKnoten(BaumKnoten left, BaumKnoten right) {
		this.left = left;
		this.right = right;
	}

	public static int anzahlKnoten(BaumKnoten b) {
		if (b == null)
			return 0;
		return anzahlKnoten(b.left) + anzahlKnoten(b.right) + 1;
	}
}
