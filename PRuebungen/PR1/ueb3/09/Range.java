/**
 *	Mo 26. Okt 14:42:10 CET 2015
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
public class Range {
	public static void main(String[] args) {

		printRange(2,7);
		printRange(19,11);
		printRange(5,5);

	}
	
	// gibt die Zahlen in den Grenzen a und b aus unter Berücksichtigung a>b
	public static void printRange(int a, int b) {
		System.out.print("[" + a);
		int sign = (int) Math.signum(a-b);
		for ( int i = a - sign; i != b - sign; i -= sign) {
			System.out.print(", "+ i);
		}
		System.out.println("]");
	}
}
