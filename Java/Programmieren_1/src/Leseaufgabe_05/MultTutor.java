package Leseaufgabe_05;
import java.util.Random;
import java.util.Scanner;

public class MultTutor {
	public static final int MAXNUM = 20;

	public static void main(String[] args) {
		Random rand = new Random();

		int a = rand.nextInt(MAXNUM) + 1;
		int b = rand.nextInt(MAXNUM) + 1;
		System.out.print(a + " * " + b + " = ");
		Scanner scanner = new Scanner(System.in);
		int antwort = scanner.nextInt();
		if (antwort == a * b) {
			System.out.println("Korrekt!");
		} else {
			System.out.println("Falsch! Richtig wäre " + a * b + ".");
		}

		for (int i = 0; i < MAXNUM; i++) {

			System.out.print(zufallsVokal(rand));
		}
		System.out.println();
		for (int i = 0; i < MAXNUM; i++) {

			System.out.print(zufallsBuchstabe(rand));
		}
		System.out.println();

		scanner.close();
	}

	public static char zufallsVokal(Random rand) {
		String vokale = "aeiou";
		int laenge = vokale.length();
		char c = vokale.charAt(rand.nextInt(laenge));
		return c;
	}

	public static char zufallsBuchstabe(Random rand) {
		char c = (char) ('A' + rand.nextInt(26));
		return c;
	}

}
