import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Wuerfeln {
	public static void main(String[] args) {
		int n = 10;

		int[] probs = new int[6];

		List<Integer> wuerfe = new ArrayList<Integer>();
		Random rand = new Random();
		for (int i = 0; i < n; ++i) {
			int wurf = rand.nextInt(6);
			++probs[wurf];
			wuerfe.add(wurf + 1);
		}
		Collections.sort(wuerfe);
		int summe = 0;
		for (int wurf : wuerfe) {
			summe += wurf;
		}
		double arithMittel = (double) summe / n;
		System.out.println("Arithmetisches Mittel = " + arithMittel);

		int summeProb = 0;
		for (int value : probs) {
			summeProb += value;
		}
		double probArithMittel = (double) summeProb / n;
		System.out.println("Arithmetisches Mittel = " + probArithMittel);

		int median = wuerfe.get(n / 2);
		System.out.println("Median = " + median);

		summe = 0;

		for (int wurf : wuerfe) {
			double wert = wurf - arithMittel;
			summe += wert * wert;
		}

		double standardAbweichung = Math.sqrt((1.0f / (n - 1)) * summe);
		System.out.println("Standarabweichung = " + standardAbweichung);
	}
}
