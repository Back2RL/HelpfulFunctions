package Aufgabe_4;

import java.util.Arrays;

public class DSA_1276156_04 {

	public static final int ANZAHL_DURCHLAEUFE = (int) 1E0;
	public static final int ANZAHL_ELEMENTE = 500000;

	public static void main(String[] args) {

		// langes Array mit 10 Zufallszahlen
		int[] B = new int[10];
		for (int i = 0; i < B.length; ++i) {
			B[i] = (int) (Math.random() * 10);
		}
		System.out.println("   Array B: " + Arrays.toString(B));
		// System.out.println("sortiert B: " +
		// Arrays.toString(straightInsertion(B, B.length)));
		System.out.println("sortiert B: " + Arrays.toString(straightInsertionSkript(B, B.length)));
		// System.out.println(Arrays.toString(straightSelection(A, A.length)));
		// System.out.println(Arrays.toString(straightSelection(B, B.length)));
		// System.out.println(Arrays.toString(binaryInsertionFaster(B,
		// B.length)));

		// langes Array mit vielen Zufallszahlen
		int[] randomArray = new int[ANZAHL_ELEMENTE];
		for (int i = 0; i < randomArray.length; ++i) {
			randomArray[i] = (int) (Math.random() * ANZAHL_ELEMENTE);
		}

		int[] A = { 13, 38, 64, 13, 12, 71, 26 };
		System.out.println();
		System.out.println("Einfuegestelle finden: ");
		int[] aSorted = simplestBubbleSort(A, A.length);
		System.out.println(Arrays.toString(aSorted));
		int zahl = 14;
		System.out.println("Einfuegestelle der Zahl " + zahl + " ist bei Index = "
				+ findeEinfuegestelleBinaer(aSorted, zahl, 0, aSorted.length - 1));

		// System.out.println("Array A: " + Arrays.toString(A));
		// System.out.println(Arrays.toString(simplestBubbleSort(A, A.length)));
		// System.out.println(Arrays.toString(checkingBubbleSort(A, A.length)));
		// System.out.println(Arrays.toString(simplestBubbleSort(randomArray,
		// randomArray.length)));
		// System.out.println(Arrays.toString(checkingBubbleSort(randomArray,
		// randomArray.length)));
		// System.out.println(Arrays.toString(straightSelection(randomArray,
		// randomArray.length)));
		// System.out.println(Arrays.toString(binaryInsertion(randomArray,
		// randomArray.length)));

		// ----- Zeit messen (viele Arrayelemente) ------
		System.out.println();
		System.out.print("Zeit Messung... (" + ANZAHL_DURCHLAEUFE + " Wiederholungen / ");
		System.out.println(ANZAHL_ELEMENTE + " Elemente)");
		long start = System.currentTimeMillis();

		for (int i = 0; i < ANZAHL_DURCHLAEUFE; ++i) {
			// simplestBubbleSort(randomArray, randomArray.length);
			// checkingBubbleSort(randomArray, randomArray.length);
			// binaryInsertionSlow(randomArray, randomArray.length);
			binaryInsertionFaster(randomArray, randomArray.length);
			// straightInsertionSkript(randomArray, randomArray.length);
			// straightInsertion(randomArray, randomArray.length);
			// straightSelection(randomArray, randomArray.length);
		}
		double laufzeit = laufzeitSekunden(start);

		System.out.println(" Laufzeit = " + laufzeit + " Sekunden");
		System.out.println("Im Mittel benötigte die Berechnung " + laufzeit / ANZAHL_DURCHLAEUFE + " Sekunden.");

	}

	// 9a & d)
	// Zeit Messung... (1 Widerholungen / 100000 Elemente)
	// Laufzeit = 12.9350004196167 Sekunden
	// Im Mittel benötigte die Berechnung 12.9350004196167 Sekunden.
	// Zeit Messung... (1 Widerholungen / 10000 Elemente)
	// Laufzeit = 0.12600000202655792 Sekunden
	// Im Mittel benötigte die Berechnung 0.12600000202655792 Sekunden.
	// O(n²)
	public static int[] simplestBubbleSort(int[] A, int n) {
		for (int j = 0; j < n - 1; ++j) {
			for (int i = 0; i < n - 1 - j; ++i) {
				if (A[i] > A[i + 1]) {
					int temp = A[i];
					A[i] = A[i + 1];
					A[i + 1] = temp;
				}
			}
		}
		return A;
	}

	// 9b & d)
	// Zeit Messung... (1 Widerholungen / 100000 Elemente)
	// Laufzeit = 11.828001022338867 Sekunden
	// Im Mittel benötigte die Berechnung 11.828001022338867 Sekunden.
	// Zeit Messung... (1 Widerholungen / 10000 Elemente)
	// Laufzeit = 0.11900000274181366 Sekunden
	// Im Mittel benötigte die Berechnung 0.11900000274181366 Sekunden.
	// O(n²)
	public static int[] checkingBubbleSort(int[] A, int n) {
		boolean bSwap = false;
		for (int j = 0; j < n - 1; ++j) {
			for (int i = 0; i < n - 1 - j; ++i) {
				if (A[i] > A[i + 1]) {
					bSwap = true;
					int temp = A[i];
					A[i] = A[i + 1];
					A[i + 1] = temp;
				}
			}
			if (!bSwap) {
				// System.out.println("Did not swap -> break");
				break;
			}
			bSwap = false;
		}
		return A;
	}

	// 9c & d)
	// Zeit Messung... (1 Widerholungen / 500000 Elemente)
	// Laufzeit = 80.95700073242188 Sekunden
	// Im Mittel benötigte die Berechnung 80.95700073242188 Sekunden.
	// Zeit Messung... (1 Widerholungen / 50000 Elemente)
	// Laufzeit = 0.8330000638961792 Sekunden
	// Im Mittel benötigte die Berechnung 0.8330000638961792 Sekunden.
	// O(n²)
	public static int[] straightSelection(int[] A, int n) {
		for (int i = 0; i < n; ++i) {
			int pos_min = i;
			for (int j = i + 1; j < n; ++j) {
				if (A[j] < A[pos_min]) {
					pos_min = j;
				}
			}
			int help = A[pos_min];
			A[pos_min] = A[i];
			A[i] = help;
		}
		return A;
	}

	// 10b)
	// Zeit Messung... (1 Widerholungen / 500000 Elemente)
	// Laufzeit = 86.69800567626953 Sekunden
	// Im Mittel benötigte die Berechnung 86.69800567626953 Sekunden.
	public static int[] straightInsertionSkript(int[] A, int n) {
		for (int i = 0; i < n; ++i) {
			int pos_found = 0;
			int j = i;
			int temp = A[i];
			while (j > 0 && pos_found == 0) {
				if (A[j - 1] < temp) {
					pos_found = 1;
				} else {
					A[j] = A[j - 1];
					j -= 1;
				}
			}
			A[j] = temp;
		}
		return A;
	}

	// 10b) mit findeeinfuegestelle-Methodenaufruf
	// Zeit Messung... (1 Wiederholungen / 10000 Elemente)
	// Laufzeit = 11.54800033569336 Sekunden
	// Im Mittel benötigte die Berechnung 11.54800033569336 Sekunden.
	// Zeit Messung... (1 Wiederholungen / 5000 Elemente)
	// Laufzeit = 1.5010000467300415 Sekunden
	// Im Mittel benötigte die Berechnung 1.5010000467300415 Sekunden.
	public static int[] straightInsertion(int[] A, int n) {
		for (int i = 1; i < n; ++i) {
			int temp = A[i];
			int index = findeEinfuegestelle(A, i, temp);
			while (A[index] == temp && index != i) {
				index++;
			}
			A[i] = A[index];
			A[index] = temp;
			if (index < i && A[i] != A[index])
				i--;
		}
		return A;
	}

	public static int findeEinfuegestelle(int[] A, int index, int zahl) {
		for (int i = index; i > 0; --i) {
			if (A[i - 1] < zahl) {
				return i;
			}
		}
		return 0;
	}

	// 10d) schlechte Lösung
	// Zeit Messung... (1 Widerholungen / 50000 Elemente)
	// Laufzeit = 34.04800033569336 Sekunden
	// Im Mittel benötigte die Berechnung 34.04800033569336 Sekunden.
	public static int[] binaryInsertionSlow(int[] A, int n) {
		for (int i = 1; i < n; ++i) {
			int temp = A[i];
			int index = findeEinfuegestelleBinaer(A, temp, 0, i - 1);
			// System.out.println(Arrays.toString(A) + " i=" + i + " temp=" +
			// temp + " index=" + index);
			while (A[index] == temp && index != i) {
				index++;
				// System.out.println(Arrays.toString(A) + " i=" + i + " temp="
				// + temp + " index=" + index + "+");
			}
			A[i] = A[index];
			A[index] = temp;
			if (index < i && A[i] != A[index])
				i--;
		}
		return A;
	}

	// 10d) bessere Lösung
	// Zeit Messung... (1 Widerholungen / 500000 Elemente)
	// Laufzeit = 25.211000442504883 Sekunden
	// Im Mittel benötigte die Berechnung 25.211000442504883 Sekunden.
	public static int[] binaryInsertionFaster(int[] A, int n) {
		for (int i = 1; i < n; ++i) {
			int temp = A[i];
			int index = findeEinfuegestelleBinaer(A, temp, 0, i - 1);
			// System.out.println(Arrays.toString(A) + " i=" + i + " temp=" +
			// temp + " index=" + index);
			while (A[index] == temp && index != i) {
				index++;
				// System.out.println(Arrays.toString(A) + " i=" + i + " temp="
				// + temp + " index=" + index + "+");
			}

			for (int j = i; j > index; --j) {
				A[j] = A[j - 1];
			}
			A[index] = temp;
			if (index < i && A[i] != A[index])
				i--;
		}
		return A;
	}

	// 10c)
	/**
	 * sucht rekursiv die Zahl k in einem Array von Integern und gibt deren
	 * Index, falls vorhanden zurück
	 * 
	 * @param A
	 *            Array von Integern
	 * @param k
	 *            Zahl nach der gesucht wird
	 * @param start
	 *            Index welcher den Start des Suchbereichs markiert
	 * @param end
	 *            Index welcher das Ende des Suchbereichs markiert
	 * @return Index(k) oder Index der postion an der k stehen müsste; -1 falls
	 *         A leer ist
	 */
	public static int findeEinfuegestelleBinaer(int[] A, int k, int start, int end) {
		if (start < 0 || end >= A.length || end < 0) {
			throw new IndexOutOfBoundsException("Die angegebenen Grenzen sprengen den Rahmen des Arrays!");
		}
		// leeres Array -> return -1
		if (A.length == 0)
			return -1;
		// aktuellen Bereich (definiert durch start/end) halbieren
		int m = (start + end) / 2;
		// Zahl gefunden? zurückgeben
		if (A[m] == k) {
			return m;
		}
		// Zahl nicht gefunden aber start/end sind identisch
		// index zurückgeben an der k stehen sollte
		if (start == end) {
			if (A[m] < k) {
				// k ist größer als das betrachtete Element -> index +1
				// zurückgeb.
				return m + 1;
			}
			return m;
		}
		// Zahl ist kleiner -> neue Suche unterhalb des Index m
		if (k < A[m]) {
			return findeEinfuegestelleBinaer(A, k, start, m);
		}
		// Zahl ist größer -> neue Suche oberhalb des Index m
		return findeEinfuegestelleBinaer(A, k, m + 1, end);
	}

	public static double laufzeitSekunden(long startTime) {
		return (System.currentTimeMillis() - startTime) * 0.001f;
	}
}
