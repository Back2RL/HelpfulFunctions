package Aufgabe_1;

public class Prim {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Zahl bis zu der alle Primzahlen gesucht werden sollen
		int n = 100000000;
		System.out.println("Suche alle Primzahlen von 1 - " + n);

		long start = System.currentTimeMillis();

		// Array für das Sieb des T...
		boolean[] markiert = new boolean[n];
		int k = 2;
		while (k < n) {
			for (int vielfachesVonK = 2 * k; vielfachesVonK < n; vielfachesVonK += k) {
				markiert[vielfachesVonK] = true;
			}
			k = nextUnmarkedFrom(markiert, k, n);
		}
		// Zwischenspeichern der benötigten Zeit
		float calcTime = (System.currentTimeMillis() - start) * 0.001f;
		start = System.currentTimeMillis();
		// Ausgabe
		int cnt = 0;
		for (boolean b : markiert) {
			if (!b)
				System.out.println(cnt);
			++cnt;
		}

		System.out.println("Die Berechnung dauerte " + calcTime + " s");
		System.out.println("Die Ausgabe dauerte " + ((System.currentTimeMillis() - start) * 0.001f + " s"));

		// Basis ^ Exponent = Potenz
		// log(Basis){Potenz} = Exponent
	}

	/**
	 * Sucht das nächste Element in einem Array welches false gesetzt ist.
	 * 
	 * @param markiert
	 *            Array von Boolean in dem das nächste Unmarkierte (false
	 *            gesetzte Boolean) gesucht werden soll
	 * @param k
	 *            Startindex ab dem gesucht wird
	 * @param n
	 *            Endindex bis zu dem gesucht wird
	 * @return gibt den Index des Elements zurück an dem sich das gefundene
	 *         Boolean befindet
	 */
	public static int nextUnmarkedFrom(boolean[] markiert, int k, int n) {
		int nextK = k + 1;
		while (nextK < n && markiert[nextK]) {
			++nextK;
		}
		return nextK;
	}
}