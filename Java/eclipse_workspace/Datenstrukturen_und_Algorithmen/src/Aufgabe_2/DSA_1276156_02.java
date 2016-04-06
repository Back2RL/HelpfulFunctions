package Aufgabe_2;

public class DSA_1276156_02 {

	public static void main(String[] args) {
		int anzahlDurchlaufe = (int) 1E1;

		// rekursiv: eingabe <= 50, da die Berechnung sonst zu lange dauert
		// iterativ: wenn eingabe zu groß, dann passt die Lösung nicht mehr in
		// den Datentyp Integer
		// Integer Overflow bei eingabe = 47
		int eingabe = 10;

		boolean bUseIterativ = true;

		// verhindern das das Programm zu lange rechnet um eine Ausgabe zu
		// erzeugen
		if (eingabe < 47) {
			System.out.println(fibRekursiv(eingabe));
		}
		System.out.println(fibIterativ(eingabe));

		long start = System.currentTimeMillis();

		for (int i = 0; i < anzahlDurchlaufe; ++i) {
			if (bUseIterativ) {
				fibIterativ(eingabe);
			} else {
				fibRekursiv(eingabe);
			}

		}

		double laufzeit = laufzeitSekunden(start);

		System.out.println(" Laufzeit = " + laufzeit + " Sekunden");
		System.out.println("Im Mittel benötigte die Berechnung " + laufzeit / anzahlDurchlaufe + " Sekunden.");

		// -------------- Fakultät ----------------

		int fakVonN = 15;

		System.out.println("Iterativ: " + fakVonN + "! = " + fakIterativ(fakVonN));
		System.out.println("Rekursiv: " + fakVonN + "! = " + fakRekursiv(fakVonN));

		// -------------- Logarithmus ----------------

		System.out.println(logarithmusNIterationen(100.0, 1000));
		System.out.println(logarithmus(100.0));
	}

	public static int fibRekursiv(int a) {
		if (a < 3)
			return 1;
		else
			return fibRekursiv(a - 1) + fibRekursiv(a - 2);
	}

	public static int fibIterativ(int a) {
		if (a < 3)
			return 1;

		int sum_n = 1;
		int sum_n_minus_1 = 1;
		for (int i = 2; i < a; ++i) {
			int tempSum = sum_n + sum_n_minus_1;
			sum_n_minus_1 = sum_n;
			sum_n = tempSum;
		}
		return sum_n;
	}

	public static double laufzeitSekunden(long startTime) {
		return (System.currentTimeMillis() - startTime) * 0.001f;
	}

	public static int fakRekursiv(int n) {
		if (n < 1) {
			return 1;
		}
		return n * fakRekursiv(n - 1);
	}

	public static int fakIterativ(int n) {
		if (n < 1) {
			return 1;
		}
		int produkt = 1;
		for (int i = 2; i <= n; ++i) {
			produkt *= i;
		}
		return produkt;

	}

	public static double logarithmusNIterationen(double x, int n) {

		double sum = 0.0;
		double bruch = (x - 1) / (x + 1);
		for (int k = 0; k < n; ++k) {
			int exponent = 2 * k + 1;
			sum += Math.pow(bruch, exponent) / exponent;
		}
		return 2 * sum;
	}

	public static double logarithmus(double x) {

		int k = 0;
		double bruch = (x - 1) / (x + 1);
		double eps = 1E-20;
		double oldSum = 0;
		double sum = 0;
		do {
			oldSum = sum;
			int exponent = 2 * k + 1;
			sum += Math.pow(bruch, exponent) / exponent;
			++k;
		} while (Math.abs(oldSum - sum) > eps);
		return 2 * sum;
	}

}
