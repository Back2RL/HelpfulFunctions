package Gauß;

public class GaussNoPivot {
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
	public static double[] gaussNoPivot(final double[][] a, final double[] b) {

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
		// hat b die gleiche Zeilenanzahl
		if (b.length != zeilen) {
			System.out.println("Zeilen von b != Zeilen von a");
			return null;
		}

		// Nullen erzeugen links unter der Diagonalen (optimiert)
		for (int i = 0; i < zeilen; ++i) {
			// 1. spalte auf 1 bringen
			for (int zeile = i; zeile < zeilen - 1; ++zeile) {
				// Abbruch falls Diagonalelement == 0
				if (Math.abs(a[i][i] - 0.0) < SMALL_NUMBER) {
					System.out.println("Elem " + i + "," + i + " is 0, abort");
					return null;
				}
				// Faktor mit dem multipliziert wird um das 1. element der
				// nächsten gewählten Zeile auf 0 zu
				// bringen
				final double factor = a[zeile + 1][i] / a[i][i];

				// Null erzeugen in dem von jeder Zeile (mit Ausnahme der
				// ersten)
				// die mit dem Faktor multiplizuirte Erste abgezogen wird
				for (int spalte = i; spalte < spalten; ++spalte) {
					a[zeile + 1][spalte] -= factor * a[i][spalte];
				}
				b[zeile + 1] -= factor * b[i];
			}
		}

		// rechts oben die Null-Dreiecksmatrix erzeugen
		for (int i = zeilen - 1; i > -1; --i) {
			// letzte spalte auf 1 bringen
			for (int zeile = i; zeile > 0; --zeile) {
				// Abbruch falls Diagonalelement == 0
				if (Math.abs(a[i][i] - 0.0) < SMALL_NUMBER) {
					System.out.println("Elem " + i + "," + i + " is 0, abort");
					return null;
				}
				// Faktor mit dem multipliziert wird um das letzte Element der
				// nächsten gewählten Zeile auf 0 zu
				// bringen
				final double factor = a[zeile - 1][i] / a[i][i];

				// Null erzeugen in dem von jeder Zeile (mit Ausnahme der
				// letzten)
				// die mit dem Faktor multiplizierte Letzte abgezogen wird
				for (int spalte = i; spalte > -1; --spalte) {
					a[zeile - 1][spalte] -= factor * a[i][spalte];
				}
				b[zeile - 1] -= factor * b[i];
			}
		}

		// diagonal elemente auf 1 normieren
		for (int i = 0; i < zeilen; ++i) {
			// Diagonal Elemente auf 1 normieren
			final double factor = 1.0 / a[i][i];
			a[i][i] *= factor;
			b[i] *= factor;

			// // Kurzform, da a nicht zurückgegeben wird
			// b[i] /= a[i][i];
		}

		// debug
		for (int i = 0; i < zeilen; ++i) {
			for (final double number : a[i]) {
				System.out.print(number + ", ");

			}
			System.out.println(b[i] + ", ");
		}
		return b;
	}

	// TESTING
	public static void main(final String[] args) {
		final double[][] a = { { 7, 3, -5 }, { -1, -2, 4 }, { -4, 1, -3 } };
		final double[] b = { -12, 5, 1 };
		// lösung -1,0,1
		final double[] result = GaussNoPivot.gaussNoPivot(a, b);
		System.out.println("Ergebnis:");
		for (final double number : result) {
			System.out.print(number + ", ");

		}
		System.out.println();
	}

}
