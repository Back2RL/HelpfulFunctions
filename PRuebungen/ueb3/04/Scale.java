import java.awt.Point;

/**
 *	26.10.2015 17:03:04
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
public class Scale {
	public static void main(String[] args) {
		int x = 4;
		int y = 3;
		int faktor = 2;
		Point p = new Point(x, y);
		skaliere(p, faktor);
		System.out.println("x=" + p.x + ", y=" + p.y);
	}

	private static void skaliere(Point p, int faktor) {
		p.setLocation(p.getX() * faktor, p.getY() * faktor);
	}

}