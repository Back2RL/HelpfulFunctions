package Gauß;

public class GaussPivotLU {
	public static final double SMALL_NUMBER = 1E-8;

	/**
	 * berechnet für eine NxN Matrix a mit dem Ergebnisvektor Nx1 b die
	 * Koeffizienten;
	 * 
	 * @param a
	 *            NxN Matrix
	 * @param b
	 *            Nx1 Matrix
	 * @return Nx1 Matrix mit den Koeffizienten oder null falls die Berechnung
	 *         fehlschlägt
	 */
	public static double[][] gaussLU(final double[][] a) {

		final int zeilen = a.length;
		// existieren Zeilen
		if (zeilen < 1) {
			System.out.println("Zeilen > 0 erwartet");
			return null;
		}
		final int spalten = a[0].length;
		// existieren Spalten und sind es genauso viele wie Zeilen
		if (spalten < 1 || spalten != zeilen) {
			System.out.println("Spalten > 0 erwartet");
			return null;
		}

		final int[] p = new int[zeilen];
		// Vektor zum Merken der Reihenfolge initialisieren
		for (int i = 0; i < zeilen; ++i) {
			p[i] = i;
		}

		final double[][] l = new double[zeilen][spalten];

		// Nullen erzeugen links unter der Diagonalen (optimiert)
		for (int i = 0; i < zeilen; ++i) {
			// 1. spalte auf 1 bringen
			for (int zeile = i; zeile < zeilen - 1; ++zeile) {
				// pivotelement suchen
				int max = zeile;
				for (int j = zeile + 1; j < zeilen; ++j) {
					if (Math.abs(a[j][zeile]) > Math.abs(a[max][zeile])) {
						max = j;
					}
				}

				for (int j = zeile; j < zeilen; ++j) {
					final double help = a[zeile][j];
					a[zeile][j] = a[max][j];
					a[max][j] = help;
				}

				// Abbruch falls Diagonalelement == 0
				if (Math.abs(a[i][i] - 0.0) < SMALL_NUMBER) {
					System.out.println("Elem " + i + "," + i + " is 0, abort");
					return null;
				}

				for (int j = zeile + 1; j < zeilen; ++j) {
					// Faktor mit dem multipliziert wird um das 1. element der
					// nächsten gewählten Zeile auf 0 zu
					// bringen
					// final double factor = a[zeile + 1][i] / a[i][i];
					final double factor = a[p[zeile + 1]][i] / a[p[i]][i];
					if (factor != 0) {
						l[p[zeile + 1]][i] = factor;
					}

					// Null erzeugen in dem von jeder Zeile (mit Ausnahme der
					// ersten)
					// die mit dem Faktor multiplizuirte Erste abgezogen wird
					for (int spalte = i; spalte < spalten; ++spalte) {
						a[p[zeile + 1]][spalte] -= factor * a[p[i]][spalte];
					}
				}
			}
		}

		// debug start
		for (int i = 0; i < zeilen; ++i) {
			for (final double number : a[i]) {
				System.out.print(number + ", ");
			}
			System.out.println();
		}

		for (int i = 0; i < zeilen; ++i) {
			for (final double number : l[i]) {
				System.out.print(number + ", ");
			}
			System.out.println();
		}
		// debug end

		for (int i = 0; i < zeilen; ++i) {
			for (int j = 0; j < spalten; j++) {
				if (l[i][j] != 0) {
					a[i][j] = l[i][j];
				}
			}
		}

		// // debug start
		// for (int i = 0; i < zeilen; ++i) {
		// for (final double number : a[i]) {
		// System.out.print(number + ", ");
		// }
		// System.out.println();
		// }
		//
		// for (int i = 0; i < zeilen; ++i) {
		// for (final double number : l[i]) {
		// System.out.print(number + ", ");
		// }
		// System.out.println();
		// }
		// // debug end

		return a;
	}

	// TESTING
	public static void main(final String[] args) {
		final double[][] a = { { 7, 3, -5 }, { -1, -2, 4 }, { -4, 1, -3 } };
		final double[] b = { -12, 5, 1 };
		// lösung -1,0,1
		final double[][] result = GaussPivotLU.gaussLU(a);
		System.out.println("Ergebnis:");
		// for (final double number : result) {
		// System.out.print(number + ", ");
		//
		// }
		System.out.println();
	}

}