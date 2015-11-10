/**
 *	Mo 26. Okt 15:48:13 CET 2015
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
import java.util.Random;

public class RandomText {
	public static void main(String[] args) {

		zeilen(5,8);
	}

	// erzeugt eine zufällige Anzahl an Zeilen mit Vokalen
	public static void zeilen(int min, int max) {
		int random = randomInRange(min, max);
		for ( int i = 0; i < random; i++) {
			vokale(4,6);
		}
	}

	// gibt eine zufällige Anzahl an zufälligen Vokalen aus
	public static void vokale(int min, int max) {

		String vokale = "aeiou";
		int random = randomInRange(min, max);

		for ( int i = 0; i < random; i++) {
			System.out.print(vokale.charAt(randomInRange(0,4)));

		}
		System.out.println();
	}

	// erzeugt eine zufällige Integerzahl lower <= x <= upper
	public static int randomInRange(int lower, int upper) {
		// return (int) (Math.round(Math.random() * (upper - lower) + lower));
		// Alternative:
		Random random = new Random();		
		return random.nextInt(upper - lower + 1) + lower;
	}
}


