/**
 *	Do 12. Nov 15:42:20 CET 2015
 *
 *	PR1, WS2015/16
 *
 *	Leonard Oertelt
 *	Matrikelnummer 1276156
 *	leonard.oertelt@stud.hs-hannover.de
 *	-----------------------------------------
 *	calculates primenumbers from 2 - n (n is userinput)
 */
import java.util.*;

public class Prime {
	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);
		System.out.print("Your number: ");
		int number = console.nextInt();
		if (number <= 1) {
			System.out.println("This number is too small.");
		}
		else {
			System.out.print(2);
			int cnt = 1;
			for(int i = 3; i <= number; i++) {
				if(countFactors(i) == 2) {
					cnt++;
					System.out.print(", " + i);
				}
			}
		}
	}

	// is the number a primenumber	
	public static boolean isPrime(int n) {
		return (n > 1 && countFactors(n) == 2);
	}

	// returns the number of factors a number has
	public static int countFactors(int n) {
		// not yet working
		return n;
	}

	// returns the next primenumber or n if n is primenumber
	public static int nextPrime(int n) {
		while (!isPrime(n)) {
			n++;
		}
		return n;
	}
}
