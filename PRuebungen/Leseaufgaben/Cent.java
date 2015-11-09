/**
 *	Di 27. Okt 11:33:13 CET 2015
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

public class Cent {
	public static final double EPS = 1E-7;

	public static void main(String[] args) {
		double euro = 0.01 + 0.02 + 0.1 + 0.02 + 0.2 + 0.05;

		if (Math.abs(euro - 0.4) < EPS) {
			System.out.println("Das waren 40 Cent");
		}
		else {
			System.out.println("Das waren NICHT 40 Cent");
		}
	}
}
