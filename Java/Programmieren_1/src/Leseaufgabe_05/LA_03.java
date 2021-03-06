package Leseaufgabe_05;
import java.util.Scanner;

public class LA_03 {

	public static void main(String[] args) {

		String buch = "Programmieren in Java";

		// a)
		int indexOfIn = buch.indexOf("in");
		String inBuch = buch.substring(indexOfIn, indexOfIn + 2);

		System.out.println(inBuch);

		// b)
		buch = buch.toUpperCase();
		System.out.println(buch);

		String s = "Guten Morgen";
		s = extractFirstWord(s);
		System.out.println(s);

		Scanner console = new Scanner(System.in);
		System.out.print("Wie alt sind Sie? "); // Prompt
		int alter = console.nextInt();
		System.out.println("Sie werden 40 in " + (40 - alter) + " Jahren.");

		// Scanner console = new Scanner(System.in);
		System.out.print("Wie ist Ihr Vorname? ");
		String name = console.next();
		System.out.print("Und wie alt sind Sie? ");
		alter = console.nextInt();
		System.out.println(name + " ist " + alter);
		System.out.println("Das ist ganz schön alt!");

		System.out.print("Bitte drei Zahlen eingeben: ");
		int num1 = console.nextInt();
		int num2 = console.nextInt();
		int num3 = console.nextInt();
		double average = (double) (num1 + num2 + num3) / 3.0;
		System.out.println("Der Durchschnitt ist " + average);

		System.out.print("How many numbers? ");
		int n = console.nextInt();
		int sum = readSum(console, n);
		System.out.println("The Sum is " + sum);

		int gesamtStunden = gesamtStunden(console, 2);
		System.out.println("Gesamtstunden für beide = " + gesamtStunden);

	}

	// extrahiert das erste Wort eines beliebigen Strings
	public static String extractFirstWord(String inputString) {
		int index = inputString.indexOf(" ");
		if (index == -1) { // es wurde kein Leerzeichen gefunden -> Nur ein Wort
							// im String
			return inputString;
		}
		return inputString.substring(0, index);
	}

	//
	public static int readSum(Scanner console, int n) {
		int sum = 0;
		for (int i = 1; i <= n; i++) {
			System.out.print("Type a number: ");
			sum += console.nextInt();
		}
		return sum;
	}

	//
	public static int gesamtStunden(Scanner console, int anzahlDerArbeiter) {
		int gesamtSummeDerStunden = 0;
		for (int i = 1; i <= anzahlDerArbeiter; i++) {
			System.out.print("Angestellter " + i + ": Wie viele Tage? ");
			int tage = console.nextInt();
			int summeDerStunden = stundenEinlesen(console, tage);
			System.out.println("Gesamtstunden für Angestellter " + i + " = " + summeDerStunden);
			gesamtSummeDerStunden += summeDerStunden;
		}
		return gesamtSummeDerStunden;
	}

	//
	public static int stundenEinlesen(Scanner console, int n) {
		int sum = 0;
		for (int i = 1; i <= n; i++) {
			System.out.print("Stunden? ");
			sum += Math.min(8, console.nextInt());
		}
		return sum;
	}

}
