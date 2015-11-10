/**
 *	Mi 21. Okt 13:30:41 CEST 2015
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
 *	Dieses Programm gibt den String "Gut Holz!" vertikal aus.
 */
public class Vertical {
	public static void main(String[] args) {

		vertical("Gut Holz!");

	}

	//gibt einen Input-String vertikal aus
	public static void vertical(String satz) {
		for (int i = 0; i< satz.length(); i++) {
			System.out.println(satz.charAt(i));
		}	
	}
}
