package de.hsh.pr2;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);
		System.out.print("Ihre Eingabe: ");
		int n = console.nextInt();
		console.close();
		System.out.println(fakultaet(n));
		test();
	}

	/**
	 * Methode um Fakultät zu testen
	 */
	public static void test() {
		int n = 5;
		int expected = 120;
		int observed = fakultaet(n);
		if (expected != observed) {
			throw new AssertionError("fakultaet(" + n + ") should return " + expected + " but returns " + observed);
		}
	}

	/**
	 * Diese Methode berechnet die Fakultät einer Zahl
	 * 
	 * @param n
	 *            Zahl , für die die Fakultät berechnet werden soll
	 * @return liefert das Ergebnis
	 */
	public static int fakultaet(int n) {
		if (n == 0)
			return 1;
		return n * fakultaet(n - 1);
	}
}
