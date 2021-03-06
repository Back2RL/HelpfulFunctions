package Leseaufgabe_05;
import java.util.Scanner;

// berechnet aus einer Anzahl von Jahren die Anzahl der Monate (0 <= x <= 100)
public class LA_15_11_10 {

	public static void main(String[] args) {

		Scanner console = new Scanner(System.in);
		System.out.print("Bitte Jahre eingeben: ");
		int jahre = console.nextInt();
		System.out.println("Das sind " + alterInMonaten(jahre) + " Monate.");

		console.close();
	}

	public static int alterInMonaten(int jahre) {
		if (jahre < 0) {
			throw new IllegalArgumentException("Jahre muss >= 0 sein!");
		}
		if (jahre > 100) {
			throw new IllegalArgumentException("Jahre muss <=100 sein!");
		}
		return jahre * 12;
	}

}
