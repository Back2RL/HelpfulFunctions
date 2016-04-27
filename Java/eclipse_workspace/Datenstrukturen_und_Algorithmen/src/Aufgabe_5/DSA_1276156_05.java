/**
 * 
 */
package Aufgabe_5;

import java.util.Arrays;

/**
 * @author oertelt, opitzju
 *
 */
public class DSA_1276156_05 {
	public static final int ANZAHL_ELEMENTE = 100000;
	public static final int MAX_AUSGABE = 10000;

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// langes Array mit vielen Zufallszahlen
		int[] randomArray = generateRandomArray(1, 1000, ANZAHL_ELEMENTE);

		if (ANZAHL_ELEMENTE <= MAX_AUSGABE)
			System.out.println(Arrays.toString(randomArray));

		System.out.println(
				"Min/Max des Array = " + Arrays.toString(divideConquerMinMax(randomArray, 0, ANZAHL_ELEMENTE - 1)));
		mergeSort(randomArray, 0, randomArray.length - 1);
		if (ANZAHL_ELEMENTE <= MAX_AUSGABE) {
			System.out.println("Das sortierte Array:");
			System.out.println(Arrays.toString(randomArray));
		}

		System.out.println();

		/*
		 * Zeit Messung...(100000 Elemente)
		 * ----- 
		 * Insertionsort:
		 * Laufzeit = 3.433000087738037 Sekunden
		 * 
		 * Mergesort: 
		 * Laufzeit = 0.010000000707805157 Sekunden 
		 * -----
		 * Verhältnis: Insertionsort/Mergesort = 343.2999844748538
		 */
		compareSortAlgorithms(generateRandomArray(1, 1000, ANZAHL_ELEMENTE));

	}

	/**
	 * @param liste
	 *            Array aus dem die größte und kleinste Zahl gesucht wird
	 * @param a
	 *            Index der den Anfang des Suchbereichs definiert
	 * @param b
	 *            Index der das Ende des Suchbereichs definiert
	 * @return Integer Array mit Index 0 = kleinste Zahl aus Suchbereich, Index
	 *         1 = größte Zahl aus Suchbereich
	 * @throws IllegalArgumentException
	 *             wenn liste == null oder leer
	 * @throws IndexOutOfBoundsException
	 *             wenn a oder b ungültige Arrayindizes beschreiben
	 */
	public static int[] divideConquerMinMax(int[] liste, int a, int b) {
		if (liste == null || liste.length == 0) {
			throw new IllegalArgumentException("Array is empty or null!");
		}
		if (a < 0 || b >= liste.length) {
			throw new IndexOutOfBoundsException("a < 0 or b > last arrayindex!");
		}
		if (a == b) {
			// result[0] contains min, result[1] contains max
			int[] result = { liste[a], liste[b] };
			return result;
		}
		if ((b - a) == 1) {
			if (liste[a] < liste[b]) {
				// result[0] contains min, result[1] contains max
				int[] result = { liste[a], liste[b] };
				return result;
			}
			// result[0] contains min, result[1] contains max
			int[] result = { liste[b], liste[a] };
			return result;
		}
		int middle = (a + b) / 2;
		int[] result1 = divideConquerMinMax(liste, a, middle - 1);
		int[] result2 = divideConquerMinMax(liste, middle, b);
		int min = 0;
		// result[0] contains min
		if (result1[0] <= result2[0]) {
			min = result1[0];
		} else {
			min = result2[0];
		}

		// result[1] contains max
		int max = 0;
		if (result1[1] >= result2[1]) {
			max = result1[1];
		} else {
			max = result2[1];
		}
		int[] result = { min, max };

		return result;
	}

	/**
	 * Sortiert Arrayinhalt
	 * 
	 * @param liste
	 *            zu sortierendes Array
	 * @param lo
	 *            Index der den Anfang des Suchbereichs definiert
	 * @param hi
	 *            Index der das Ende des Suchbereichs definiert
	 * @throws IllegalArgumentException
	 *             wenn liste == null oder leer
	 * @throws IndexOutOfBoundsException
	 *             wenn a oder b ungültige Arrayindizes beschreiben
	 */
	public static void mergeSort(int[] liste, int lo, int hi) {
		if (liste == null || liste.length == 0) {
			throw new IllegalArgumentException("Array is empty or null!");
		}
		if (lo < 0 || hi >= liste.length) {
			throw new IndexOutOfBoundsException("a < 0 or b > last arrayindex!");
		}

		if (lo >= hi)
			return;

		int mid = (lo + hi) / 2;
		mergeSort(liste, lo, mid);
		mergeSort(liste, mid + 1, hi);
		merge(liste, lo, mid, hi);
	}

	/**
	 * führt den Zusammenfüge-Schritte (Merging) des Mergesort Altgorithmus aus
	 * 
	 * @param liste
	 *            zu sortierendes Array
	 * @param lo
	 *            Index der den Anfang des Suchbereichs definiert
	 * @param mid
	 *            Index der die Mitte des Suchbereichs definiert
	 * @param hi
	 *            Index der das Ende des Suchbereichs definiert
	 */
	public static void merge(int[] liste, int lo, int mid, int hi) {
		int i = 0;
		int j = lo;
		int[] result = new int[mid - lo + 1];
		while (j <= mid) {
			result[i] = liste[j];
			i++;
			j++;
		}
		i = 0;
		int k = lo;
		while (k < j && j <= hi) {
			if (result[i] < liste[j]) {
				liste[k] = result[i];
				i++;
			} else {
				liste[k] = liste[j];
				j++;
			}
			k++;
		}
		while (k < j) {
			liste[k] = result[i];
			i++;
			k++;
		}
		return;
	}

	/**
	 * @param min
	 *            kleinste mögliche Zahl
	 * @param max
	 *            größte möglich Zahl
	 * @param n
	 *            Anzahl Arrayelemente
	 * @return das Array mit Zufallszahlen
	 */
	public static int[] generateRandomArray(int min, int max, int n) {
		int[] array = new int[n];
		for (int i = 0; i < n; ++i) {
			array[i] = min + (int) (Math.random() * max);
		}
		return array;
	}

	public static int[] straightInsertionSkript(int[] A) {
		int n = A.length;
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

	@SuppressWarnings("unused")
	public static void compareSortAlgorithms(int[] array) {
		int[] a = Arrays.copyOf(array, array.length);
		int[] b = Arrays.copyOf(array, array.length);
		if (ANZAHL_ELEMENTE <= MAX_AUSGABE) {
			System.out.println("Zu sortierendes Array:");
			System.out.println(Arrays.toString(array));
		}
		System.out.println();
		// ----- Zeit messen (viele Arrayelemente) ------
		System.out.println("Zeit Messung...(" + ANZAHL_ELEMENTE + " Elemente)");
		System.out.println("-----");

		// Zeitmessung Insertionsort
		System.out.println("Insertionsort:");
		long start = System.currentTimeMillis();
		straightInsertionSkript(a);
		double laufzeitInsertion = laufzeitSekunden(start);
		if (ANZAHL_ELEMENTE <= MAX_AUSGABE)
			System.out.println(Arrays.toString(a));
		// Zeitausgabe
		System.out.println(" Laufzeit = " + laufzeitInsertion + " Sekunden");
		System.out.println();

		// Zeitmessung Mergesort
		System.out.println("Mergesort:");
		start = System.currentTimeMillis();
		mergeSort(b, 0, b.length - 1);
		double laufzeitMerge = laufzeitSekunden(start);
		if (ANZAHL_ELEMENTE <= MAX_AUSGABE)
			System.out.println(Arrays.toString(b));
		// Zeitausgabe
		System.out.println(" Laufzeit = " + laufzeitMerge + " Sekunden");
		System.out.println("-----");
		System.out.println("Verhältnis: Insertionsort/Mergesort = " + laufzeitInsertion / laufzeitMerge);
	}

	public static double laufzeitSekunden(long startTime) {
		return (System.currentTimeMillis() - startTime) * 0.001f;
	}
}
