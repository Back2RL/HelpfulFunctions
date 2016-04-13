
public class DSA_1276156_02 {

	public enum Teilaufgabe {
		a_FibonacciRekursiv, a_FibonacciIterativ, b_FakultaetRekursiv, b_FakultaetIterativ, c_LogarithmusN, d_LogarithmusNOptimiert, LogarithmusEPS
	}

	public static void main(String[] args) {

		// Aufgabenteil waehlen:
		Teilaufgabe aufgabe = Teilaufgabe.c_LogarithmusN;

		int anzahlDurchlaufe = (int) 1E1;

		if (aufgabe.equals(Teilaufgabe.a_FibonacciRekursiv)) {
			anzahlDurchlaufe = 1;
		}

		// FibonacciRekursiv: eingabe <= 50, da die Berechnung sonst zu lange
		// dauert
		// FibonacciIterativ: wenn eingabe zu groß, dann passt die Lösung nicht
		// mehr in den Datentyp Integer; Overflow bei eingabe = 47
		// Fakultaet:
		int eingabe = 0;

		// verhindern das das Programm zu lange rechnet um eine Ausgabe zu
		// erzeugen
		if (eingabe < 47) {
			System.out.println("Fibonacci rekursiv: " + fibRekursiv(eingabe));
		}
		System.out.println("Fibonacci iterativ: " + fibIterativ(eingabe));
		// -------------- Fakultät ----------------

		System.out.println("Fakultaet iterativ: " + eingabe + "! = " + fakIterativ(eingabe));
		System.out.println("Fakultaet rekursiv: " + eingabe + "! = " + fakRekursiv(eingabe));

		// -------------- Logarithmus ----------------

		int schritte = 20;
		double eps = 1E-9;

		System.out.println("Logarithmus Formel 1:1 : " + logarithmusNIterationen(eingabe, schritte));
		System.out.println("Logarithmus optimiert : " + logarithmusNIterationenOptimized(eingabe, schritte));
		System.out
				.println("Logarithmus mit Abbruchkriterium (" + eps + "): " + logarithmusMitGenauigkeit(eingabe, 1E-9));

		// ----- Zeit messen ------
		System.out.println("Zeit Messung... (" + anzahlDurchlaufe + " Widerholungen)");
		long start = System.currentTimeMillis();

		for (int i = 0; i < anzahlDurchlaufe; ++i) {
			switch (aufgabe) {
			case a_FibonacciRekursiv:
				fibRekursiv(eingabe);
				break;
			case a_FibonacciIterativ:
				fibIterativ(eingabe);
				break;
			case b_FakultaetIterativ:
				fakIterativ(eingabe);
				break;
			case b_FakultaetRekursiv:
				fakRekursiv(eingabe);
				break;
			case c_LogarithmusN:
				logarithmusNIterationen(eingabe, schritte);
				break;
			case d_LogarithmusNOptimiert:
				logarithmusNIterationenOptimized(eingabe, schritte);
				break;
			case LogarithmusEPS:
				logarithmusMitGenauigkeit(eingabe, eps);
				break;
			default:
				break;
			}
		}

		double laufzeit = laufzeitSekunden(start);

		System.out.println(" Laufzeit = " + laufzeit + " Sekunden");
		System.out.println("Im Mittel benötigte die Berechnung " + laufzeit / anzahlDurchlaufe + " Sekunden.");

	}

	public static int fibRekursiv(int a) {
		if (a < 3) {
			return 1;
		} else {
			return fibRekursiv(a - 1) + fibRekursiv(a - 2);
		}
	}

	public static int fibIterativ(int a) {
		if (a < 3) {
			return 1;
		}
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
		for (int k = 0; k < n; ++k) {
			sum += 2 * Math.pow(x - 1, 2 * k + 1) / ((2 * k + 1) * Math.pow(x + 1, 2 * k + 1));
		}
		return sum;
	}

	public static double logarithmusNIterationenOptimized(double x, int n) {
		double sum = 0.0;
		double bruch = (x - 1) / (x + 1);
		for (int k = 0; k < n; ++k) {
			int exponent = 2 * k + 1;
			sum += Math.pow(bruch, exponent) / exponent;
		}
		return 2 * sum;
	}

	public static double logarithmusMitGenauigkeit(double x, double eps) {
		int k = 0;
		double bruch = (x - 1) / (x + 1);
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
