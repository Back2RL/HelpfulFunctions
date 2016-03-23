package A1_Maximale_Teilsumme;

import java.util.Random;

public class MaxTeilsumme {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int durchläufe = 20000;
		int[] array = { 31, -41, 59, 26, -53, 58, 97, -93, -23, 84 };
		long start = System.currentTimeMillis();
		for (int i = 0; i < durchläufe; ++i) {

			int[] randomArray = generiereZahlenfolge(6000);

			// // int maxSum1 = findeMaxTeilsumme1(randomArray);
			int maxSum2 = findeMaxTeilsumme2(randomArray);
			// // System.out.println("Die Berechnung der Summe " + maxSum1);
			// System.out.println("Die Berechnung der Summe " + maxSum2);
		}
		long deltaT = System.currentTimeMillis() - start;
		double laufzeit = deltaT * 0.001f;

		System.out.println(" benötigte " + laufzeit + " Sekunden.");

		System.out.println("Im Mittel benötigte die Berechnung " + laufzeit / durchläufe + " Sekunden.");

	}

	public static int findeMaxTeilsumme1(int[] array) {
		int curstart = 0;
		int curend = array.length;
		int cursum = Integer.MIN_VALUE;

		for (int i = 0; i < array.length; ++i) {
			for (int j = i; j < array.length; ++j) {
				int tmpsum = 0;
				for (int k = i; k <= j; ++k) {
					tmpsum += array[k];
				}
				if (tmpsum > cursum) {
					cursum = tmpsum;
					curstart = i;
					curend = j;
				}
			}
		}
		return cursum;
	}

	public static int findeMaxTeilsumme2(int[] array) {
		int curstart = 0;
		int curend = array.length;
		int cursum = Integer.MIN_VALUE;

		for (int i = 0; i < array.length; ++i) {
			int tmpsum = 0;
			for (int j = i; j < array.length; ++j) {
				tmpsum += array[j];

				if (tmpsum > cursum) {
					cursum = tmpsum;
					curstart = i;
					curend = j;
				}
			}
		}
		return cursum;
	}

	public static int[] generiereZahlenfolge(int anzahl) {
		int[] array = new int[anzahl];
		Random rand = new Random();
		for (int i = 0; i < anzahl; ++i) {
			array[i] = rand.nextInt(2001) - 1000;
		}
		return array;
	}
}
