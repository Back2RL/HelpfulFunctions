/**
 *	Di 10. Nov 12:17:31 CET 2015
 *
 *	PR1, WS2015/16
 *
 *	Leonard Oertelt
 *	Matrikelnummer 1276156
 *	leonard.oertelt@stud.hs-hannover.de
 *	-----------------------------------------
 *	generiert zufällige Buchstaben und prüft anschließen ob es sich um einen Vokal handelt
 */
import java.util.*;

public class IstVokal {
	public static void main(String[] args) {

		for (int i = 0; i<24; i++) {
			char c = (char) ('a' + randomInRange(0,26));
			String vokal = istVokal(c) ? "ein" : "kein";
			System.out.println(c + " ist " + vokal + " Vokal.");
		}

	}
	// prüft ob char c ein Vokal ist
	public static boolean istVokal(char c) {
		if ("aeiou".indexOf(c) >= 0) {
			return true;
		}
		return false;
	}

	// generiert eine Zufällige Integer-Zahl zwischen min und max
	public static int randomInRange(int min, int max) {
		Random rand = new Random();
		return rand.nextInt(max-min) + min;
	}
}
