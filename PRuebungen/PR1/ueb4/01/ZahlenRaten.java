/**
 *	Mo 30. Nov 13:01:14 CET 2015
 *
 *	PR1, WS2015/16
 *
 *	Leonard Oertelt
 *	Matrikelnummer 1276156
 *	leonard.oertelt@stud.hs-hannover.de
 * 
 *	Julian Opitz
 * 	Matrikelnummer 1302082
 * 	julian.opitz@stud.hs-hannover.de
 *
 *	-----------------------------------------
 *	Programmbeschreibung
 */
import java.util.*;
public class ZahlenRaten {
	public static void main(String[] args) {

		System.out.println("Denken Sie sich eine Zahl zwischen 1 und 10 aus.");
		System.out.println("Ich werde versuchen, diese zu erraten");
		System.out.println();
		Scanner console = new Scanner(System.in);
//		int geheimZahl = readNumber(console);
		guessUserNumber(console);


	}
/*	public static int readNumber(Scanner console) {
		int zahl = 0;
		do { 
			System.out.print("Geben Sie Ihre Zahl (1-10) ein: ");
			if(console.hasNextInt()) {
				zahl = console.nextInt();
				continue;
			} else {
				console.next();	
			}
		} while(zahl < 1 || zahl > 10);

		return zahl;
	}
*/
	public static boolean readAwnser(Scanner console) {
		String read = console.next();
		if(read.toLowerCase().equals("j")) {
			return true;
		} else if(read.toLowerCase().equals("n")) {
			return false;
		} 
		System.out.println("Bitte antworten Sie mit J oder N.");
		return false;

	}

	public static int nextRandomNumber(Random rand) {
		return rand.nextInt(10) + 1;
	}

	public static void guessUserNumber(Scanner console) {
		Random rand = new Random();
		int guesses = 0;
		do {
			guesses++;
			System.out.print("Ist es die " + nextRandomNumber(rand) +"? (J/N) ");
		} while(!readAwnser(console));

		System.out.println();
		System.out.println("Ich habe "+guesses+" Versuche gebraucht.");

	}
}
