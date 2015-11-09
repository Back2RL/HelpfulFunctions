/**
 *	Mo 26. Okt 15:05:27 CET 2015
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
public class Encode {
	public static void main(String[] args) {
		encode("Hallo");
		decode("Ibmmp");
	}
	// ersetzt jeden Buchstaben durch den nächsten im Alphabet
	public static void encode(String text) {
		for ( int i = 0; i < text.length(); i++) {
			System.out.print((char) ((text.charAt(i) + 1)));
		}
		System.out.println();
	}
	// ersetzt jeden Buchstaben durch den vorherigen 
	public static void decode(String text) {
		for ( int i = 0; i < text.length(); i++) {
			System.out.print((char) ((text.charAt(i) - 1)));
		}
		System.out.println();

	}

}
