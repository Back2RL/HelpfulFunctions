/**
 *	Do 12. Nov 11:46:10 CET 2015
 *
 *	PR1, WS2015/16
 *
 *	Leonard Oertelt
 *	Matrikelnummer 1276156
 *	leonard.oertelt@stud.hs-hannover.de
 *	-----------------------------------------
 *	not yet working, wip
 */
import java.util.*;

public class NumberGuess {
	public static void main(String[] args) {
		Random rand = new Random();
		int number = rand.nextInt(100);
		// debug: uncomment to use
		System.out.println("Debug: number = " + number);

		Scanner console = new Scanner(System.in);

		int guess = 0;
		int guesses = 0;

		do {
			System.out.print("Your guess: ");
			guess = console.nextInt();
			guesses++;

			if (guess != number) {
				int hits = hits(number, guess);
				System.out.println("Wrong! Correct: " + hits);
			}
		} while (guess != number);

		System.out.println("You had to guess "+ guesses +" times.");
	}
	// returns the number of correct numbers of a guess:
	// e.g. number = 123 and guess is 120 --> returns 2
	public static int hits(int number, int guess) {

		int hits = 0;

		int firstNumberGuess = guess / 10;
		int secondNumberGuess = guess % 10;

		int firstNumberNumber = guess / 10;
		int secondNumberNumber = guess % 10;

		if (firstNumberGuess == firstNumberNumber || firstNumberGuess == secondNumberNumber) {
			hits++;
		}

		if (firstNumberGuess != secondNumberGuess && (secondNumberGuess == firstNumberNumber || secondNumberGuess == secondNumberNumber)) {
			hits++;
		}


		return hits;
	}
	// reads the userinput and checks if it is valid
	public static int readGuess(Scanner console) {
		int guess = 0;
		boolean valid = false;
		do {
if(!console.hasNextInt()) {
System.out.println("Not a number!");
console.next();
valid = false;
}
else {

			int guess = console.nextInt();
			valid = guess >= 0 && guess < 100;
}
			if (!valid) {
				System.out.println("Invalid number: 0-99 allowed");
			}
		} while (!valid);
		return guess;
	}

}

