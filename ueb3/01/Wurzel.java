/**
 *	Mi 21. Okt 12:58:24 CEST 2015
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
 *	Diese Programm berechnet die n-te Wurzel einer Zahl
 *	und gibt das Ergenis aus.
 */
public class Wurzel {

	public static void main(String[] args) {

		System.out.println(zahlHoch1Durchn(256, 4));
		System.out.println(zahlHoch1Durchn(0.16, 2));

	}

	//berechnet die n-te Wurzel einer Zahl
	public static double zahlHoch1Durchn(double a, int n) {
//		if (n == 0) {
//			return 0.0; // Fehler: Division durch 0!
//		}
		return Math.pow(a, 1.0/n);
	}
}
