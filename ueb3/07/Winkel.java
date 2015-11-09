/**
 *	Mo 26. Okt 14:21:26 CET 2015
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

import java.util.Scanner;

public class Winkel {
	public static void main(String[] args) {

		Scanner console = new Scanner(System.in);
		System.out.println("Dieses Programm errechnet die drei Winkel eines Dreiecks.");
		System.out.print("Bitte Seitenlänge a eingeben: ");
		double a = console.nextDouble();
		System.out.print("Bitte Seitenlänge b eingeben: ");
		double b = console.nextDouble();
		System.out.print("Bitte Seitenlänge c eingeben: ");
		double c = console.nextDouble();

		double[] winkel = innenWinkelDreieck(a, b, c);
		System.out.println("alpha = " + round1(winkel[0])); 
		System.out.println("beta  = " + round1(winkel[1])); 
		System.out.println("gamma = " + round1(winkel[2])); 

	}

	// berechnet alle Innenwinkel in einem beliebigen Dreieck
	// 	     a² = b² + c² - 2bc * cos(alpha)
	// a² - b² - c² =  -2bc * cos(alpha)
	// acos((a² - b² - c²)/(-2bc)) =  alpha
	// acos((b² - a² - c²)/(-2ac)) =  beta
	// acos((c² - a² - b²)/(-2ab)) =  gamma
	public static double[] innenWinkelDreieck(double a, double b, double c) {
		double[] winkel = new double[3];

		winkel[0] = Math.toDegrees(Math.acos((a*a - b*b - c*c)/(-2.0*b*c)));
		winkel[1] = Math.toDegrees(Math.acos((b*b - a*a - c*c)/(-2.0*a*c)));
		winkel[2] = Math.toDegrees(Math.acos((c*c - a*a - b*b)/(-2.0*a*b)));
		return winkel;
	}

	// rundet eine eingegeben Zahl auf eine Nachkommastelle.
	public static double round1(double value) {
		if(Double.isNaN(value)) return value;
		if(Double.isInfinite(value)) return value;
		return Math.round(value * 10) / 10.0;
	}


}
