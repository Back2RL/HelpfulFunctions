import java.util.Arrays;

public class SortArray {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		BottomUpHeapSorter sorter = new BottomUpHeapSorter();

		int n = 100000000;
		int[] numbers = new int[n];

		Integer[] num = new Integer[n];
		for (int i = 0; i < n; ++i) {
			num[i] = (int) (Math.random() * 10000);
			numbers[i] = num[i];
		}

		// System.out.println(Arrays.toString(numbers));

		int anzahlDurchlaufe = (int) 1E0;
		System.out.println("Zeit Messung... (" + anzahlDurchlaufe + " Widerholungen)");

		long start = System.currentTimeMillis();

		for (int i = 0; i < anzahlDurchlaufe; ++i) {
			// Arrays.sort(numbers);
			sorter.sort(numbers);
			Arrays.sort(num);
		}

		double laufzeit = laufzeitSekunden(start);

		System.out.println(" Laufzeit = " + laufzeit + " Sekunden");
		System.out.println("Im Mittel benötigte die Berechnung " + laufzeit / anzahlDurchlaufe + " Sekunden.");

		System.out.println("sorted:");

		// System.out.println(Arrays.toString(numbers));
	}

	public static double laufzeitSekunden(long startTime) {
		return (System.currentTimeMillis() - startTime) * 0.001f;
	}
}
