package Leseaufgabe_05;
import java.util.Random;

public class Glueckspiel {

	public static void main(String[] args) {
		Random rand = new Random();
		int n = rand.nextInt(10);

		if (n == 0) { // 10% chance
			System.out.println("Hauptgewinn!");
		} else {
			System.out.println("Niete.");
		}

	}

}
