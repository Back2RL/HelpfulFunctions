package A1_Maximale_Teilsumme;

public class MaxTeilsumme {

	public static void main(String[] args) {
		//
		int durchlaufe = 100;
		int anzahlZufallszahlen = 1000;

		int[] array = { 31, -41, 59, 26, -53, 58, 97, -93, -23, 84 };

		long start = System.currentTimeMillis();
		for (int i = 0; i < durchlaufe; ++i) {
			int maxSum1 = findeMaxTeilsumme1(array);
		}
		double laufzeit = laufzeitSekunden(start);
		System.out.println(" Laufzeit = " + laufzeit + " Sekunden");
		System.out.println("Im Mittel benötigte die Berechnung " + laufzeit / durchlaufe + " Sekunden.");

		start = System.currentTimeMillis();
		for (int i = 0; i < durchlaufe; ++i) {
			int[] randomArray = generiereZahlenfolge(anzahlZufallszahlen); // 10*n+1
			int maxSum1 = findeMaxTeilsumme1(randomArray); // 4.5*n^3+2*n^2+3*n+6
			// System.out.println("Die Berechnung der Summe " + maxSum1);

			// in Summe pro Schleife: 4.5*n^3 + 2*n^2 + 13*n + 7
		}
		laufzeit = laufzeitSekunden(start);
		System.out.println(" Laufzeit = " + laufzeit + " Sekunden");
		System.out.println("Im Mittel benötigte die Berechnung " + laufzeit / durchlaufe + " Sekunden.");

		start = System.currentTimeMillis();
		for (int i = 0; i < durchlaufe; ++i) {
			int[] randomArray = generiereZahlenfolge(anzahlZufallszahlen); // 10*n+1
			int maxSum2 = findeMaxTeilsumme2(randomArray); // 9*n^2+4*n+6
			// System.out.println("Die Berechnung der Summe " + maxSum2);

			// in Summe pro Schleife: 9*n^2 + 13*n + 7
		}
		laufzeit = laufzeitSekunden(start);
		System.out.println(" Laufzeit = " + laufzeit + " Sekunden");
		System.out.println("Im Mittel benötigte die Berechnung " + laufzeit / durchlaufe + " Sekunden.");

	}

	public static int findeMaxTeilsumme1(int[] array) {
		int curstart = 0; // 1
		int curend = array.length; // 1 + 1 = 2
		int cursum = Integer.MIN_VALUE; // 1 + 1 = 2

		for (int i = 0; i < array.length; ++i) { // ( 1 + 1 + 1) * array.length
													// = 3*array.length
			for (int j = i; j < array.length; ++j) {
				// 3*array.length * (0.5 * array.length)
				int tmpsum = 0; // 1 * array.length* (0.5 * array.length)
				for (int k = i; k <= j; ++k) {
					// 3 * array.length^2 * (0.5 * array.length)
					tmpsum += array[k]; // 2*array.length^2 * (0.5 *
										// array.length)
				} // -> 5 * array.length^2 * (0.5 * array.length)
				if (tmpsum > cursum) { // 1*array.length^2 * (0.5 *
										// array.length)
					cursum = tmpsum; // 1*array.length^2 * (0.5 * array.length)
					curstart = i; // 1*array.length^2 * (0.5 * array.length)
					curend = j; // 1*array.length^2 * (0.5 * array.length)
				} // -> 4 * array.length^2 * (0.5 * array.length)
			} // -> 4.5 * array.length^3 + 2 * array.length^2
		} // -> 4.5 * array.length^3 + 2 * array.length^2 + 3 * array.length
		return cursum; // 1
	}// -> 4.5 * array.length^3 + 2 * array.length^2 + 3 * array.length + 6

	public static int findeMaxTeilsumme2(int[] array) {
		int curstart = 0; // 1
		int curend = array.length; // 2
		int cursum = Integer.MIN_VALUE; // 2

		for (int i = 0; i < array.length; ++i) { // = 3*array.length
			int tmpsum = 0; // 1 * array.length
			for (int j = i; j < array.length; ++j) { // 3 * array.length^2
				tmpsum += array[j]; // 2 * array.length^2

				if (tmpsum > cursum) { // 1 * array.length^2
					cursum = tmpsum; // 1 * array.length^2
					curstart = i; // 1 * array.length^2
					curend = j; // 1 * array.length^2
				} // 4 * array.length^2
			} // 9 * array.length^2
		} // 9 * array.length^2 + 4 * array.length
		return cursum; // 1
	} // 9 * array.length^2 + 4 * array.length + 6

	public static int[] generiereZahlenfolge(int anzahl) {
		int[] array = new int[anzahl]; // anzahl
		for (int i = 0; i < anzahl; ++i) { // 3 * anzahl
			array[i] = (int) Math.round(Math.random() * 2000.0f) - 1000; // 6 *
																			// anzahl
		} // 9 * anzahl
		return array; // 1
	} // 10 * anzahl + 1

	/**
	 * @param startTime
	 *            Startzeit in Millisekunden
	 * @return Zeit die seit Startzeit vergangen ist (in Sekunden)
	 */
	public static double laufzeitSekunden(long startTime) {
		return (System.currentTimeMillis() - startTime) * 0.001f;
	}
}