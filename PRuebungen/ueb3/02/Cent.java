/**
 *	26.10.2015 17:01:02
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
 */
public class Cent {

	public static void main(String[] args) {
		System.out.println(zaehle20CentStuecke(64));
		System.out.println(zaehle20CentStuecke(1235));
	}

	public static int zaehle20CentStuecke(int cents) {
		if (cents > 99) {
			cents = cents % 100;
		}
		return (cents - (cents % 20)) / 20;
	}

}
