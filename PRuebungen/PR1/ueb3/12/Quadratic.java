/**
 *	26.10.2015 17:02:54
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
public class Quadratic {

	public static void main(String[] args) {
		quadratic(1, -12, 35);
		quadratic(0.2, -0.8, 0.8);
		quadratic(1, 12, 37);
		quadratic(0.1, 0.6, 0.9);
		quadratic(0, 4.0, 7.0);
		quadratic(1.0, -4.2, 4.21);
	}

	public static void quadratic(double a, double b, double c) {
		if (a != 0) {
			double[] results = new double[2];
			double temp = Math.sqrt(round(b * b - 4 * a * c));
			if (!Double.isNaN(temp)) {
				results[0] = (temp - b) / (2 * a);
				results[1] = (-b - temp) / (2 * a);
				if (results[0] == results[1]) {
					System.out.println("Lösung: "
							+ String.format("%.1f", results[0]));
				} else {
					System.out.println("Erste  Lösung = "
							+ String.format("%.1f", results[0]));
					System.out.println("Zweite Lösung = "
							+ String.format("%.1f", results[1]));
				}
			} else {
				System.out.println("Keine reelle Lösung");
			}
		} else {
			System.out.println("a muss ungleich 0 sein");
		}
	}

	public static double round(double value) {
		if (Double.isNaN(value)) {
			return value;
		}
		if (Double.isInfinite(value)) {
			return value;
		}
		return Math.round(value * 100000) / 100000.0;
	}

}
