/**
 *	Di 27. Okt 11:08:59 CET 2015
 *
 *	PR1, WS2015/16
 *
 *	Leonard Oertelt
 *	Matrikelnummer 1276156
 *	leonard.oertelt@stud.hs-hannover.de
 *
 *	-----------------------------------------
 *	Programmbeschreibung
 */
import java.util.Scanner;

public class GeradeUngerade {
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		System.out.print("Bitte Platzierung eingeben: ");
		int platz = scanner.nextInt();

		if (platz == 1) {
			System.out.println("Gold");
		}
		else if (platz == 2) {
			System.out.println("Silber");
		}
		else if (platz == 3) {
			System.out.println("Bronze");
		}

		String aString = "Hallo Welt";

		if (aString.equals("Something")) { 
			// do something 
		}
		else {
			// something else
		}

	}
}
