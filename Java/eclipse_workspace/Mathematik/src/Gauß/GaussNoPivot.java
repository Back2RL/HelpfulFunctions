package Gauß;

public class GaussNoPivot {
	public static final double SMALL_NUMBER = 1E-8;

	public static double[] gaussNoPivot(final double[][] a, final double[] b) {
		System.out.println("drin");

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

		final int N = zeilen;
		// gehe über die spalten
		for (int spalte = 0; spalte < N; spalte++) {

			if (Math.abs(a[spalte][spalte] - 0.0) < SMALL_NUMBER) {
				System.out.println("Elem " + spalte + "," + spalte + " is 0, abort");
				return null;
			}
			// erzeuge Nullen
			for (int j = spalte + 1; j < N; j++) {
				final double factor = a[spalte][j] / a[spalte][spalte];
				for (int k = N - 1; k > spalte; k--) {
					a[k][j] -= a[k][spalte] * factor;
					b[spalte] -= b[j] * factor;
				}
			}
		}

		// debug
		for (int i = 0; i < zeilen; ++i) {
			for (final double number : a[i]) {
				System.out.print(number + ", ");

			}
			System.out.println();
		}
		System.out.println("fertig");
		return null;
	}

	public static void main(final String[] args) {
		final double[][] a = { { 7, 3, -5 }, { -1, -2, 4 }, { -4, 1, -3 } };
		final double[] b = { -12, 5, 1 };
		// lösung -1,0,1
		System.out.println("hallo");
		GaussNoPivot.gaussNoPivot(a, b);
	}

}
