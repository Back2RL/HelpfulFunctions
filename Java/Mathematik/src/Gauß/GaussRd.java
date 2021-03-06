package Gauß;

public class GaussRd {
	public static final double SMALL_NUMBER = 1E-8;
	public static final int DECIMALS = 5;

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
	public static double[] gaussRd(final double[][] a, final double[] b) {

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

		final int[] p = new int[zeilen];
		// Vektor zum Merken der Reihenfolge initialisieren
		for (int i = 0; i < zeilen; ++i) {
			p[i] = i;
		}

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
				final double help = b[zeile];
				b[zeile] = b[max];
				b[max] = help;

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

					// Null erzeugen in dem von jeder Zeile (mit Ausnahme der
					// ersten)
					// die mit dem Faktor multiplizuirte Erste abgezogen wird
					for (int spalte = i; spalte < spalten; ++spalte) {
						a[p[zeile + 1]][spalte] = myRound(a[p[zeile + 1]][spalte] - factor * a[p[i]][spalte], DECIMALS);
					}
					b[p[zeile + 1]] = myRound(b[p[zeile + 1]] - factor * b[p[i]], DECIMALS);
				}
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
				// final double factor = a[zeile - 1][i] / a[i][i];
				final double factor = a[p[zeile - 1]][i] / a[p[i]][i];

				// Null erzeugen in dem von jeder Zeile (mit Ausnahme der
				// letzten)
				// die mit dem Faktor multiplizierte Letzte abgezogen wird
				for (int spalte = i; spalte > -1; --spalte) {
					a[p[zeile - 1]][spalte] = myRound(a[p[zeile - 1]][spalte] - factor * a[p[i]][spalte], DECIMALS);
				}
				b[p[zeile - 1]] = myRound(b[p[zeile - 1]] - factor * b[p[i]], DECIMALS);
			}
		}

		// diagonal elemente auf 1 normieren
		for (int i = 0; i < zeilen; ++i) {
			// Diagonal Elemente auf 1 normieren
			final double factor = 1.0 / a[p[i]][i];
			a[p[i]][i] = myRound(a[p[i]][i] * factor, DECIMALS);
			b[p[i]] = myRound(b[p[i]] * factor, DECIMALS);

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

	private static double sign(final double x) {
		if (x > 0) {
			return 1.d;
		} else if (x < 0) {
			return -1.d;
		} else {
			return 0.d;
		}
	}

	private static double exponent(final double x) {
		// Exponentenfunktion fuer Abschneiden und Runden
		return Math.floor(Math.log10(Math.abs(x))) + 1.d;
	}

	private static double myRound(final double x, final int anzstellen) {
		// liefert x auf anzstellen Stellen gerundet
		if (Math.abs(x) < 1e-20) {
			return 0;
		}
		final double ex = exponent(x);
		return sign(x) * Math.floor(Math.abs(x) * Math.pow(10, -ex + anzstellen) + 0.5) * Math.pow(10, ex - anzstellen);
	}

	// TESTING
	public static void main(final String[] args) {
		final double[][] a = { { 7, 3, -5 }, { -1, -2, 4 }, { -4, 1, -3 } };
		final double[] b = { -12, 5, 1 };
		// lösung -1,0,1
		final double[] result = GaussRd.gaussRd(a, b);
		System.out.println("Ergebnis:");
		for (final double number : result) {
			System.out.print(number + ", ");

		}
		System.out.println();
	}

}