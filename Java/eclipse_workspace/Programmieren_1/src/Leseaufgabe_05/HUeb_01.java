package Leseaufgabe_05;
import java.util.Random;

public class HUeb_01 {

	public static void main(String[] args) {
		// Scanner console = new Scanner(System.in);
		// System.out.print("Geben Sie 2 Zahlen ein: ");
		// int a = console.nextInt();
		// int b = console.nextInt();
		// console.
		// System.out.println("Anzahl der verschiedenen Zeichen = " +
		// numUnique(a, b));
		System.out.println("Anzahl der Durchgänge = " + throwDiceUntilSumEqualsN(7));

	}

	public static int numUnique(int a, int b) {
		return a == b ? 1 : 2;
	}

	public static int throwDiceUntilSumEqualsN(int N) {
		Random dice = new Random();

		int counts = 0;

		int sum = throwDiceTwice(6, dice);

		while (sum != N) {
			System.out.println("Die Summe ist " + sum);
			sum = throwDiceTwice(6, dice);
			counts++;
		}

		System.out.println("Hurra Gewonnen!");

		return counts;

	}

	public static int throwDiceTwice(int sides, Random dice) {

		return dice.nextInt(sides) + 1 + dice.nextInt(sides) + 1;
	}
}
