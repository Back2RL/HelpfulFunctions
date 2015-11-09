/**
 *	Mi 21. Okt 13:15:57 CEST 2015
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
 *	Dieses Programm vertauscht zwei Point-Objekte
 */
import java.awt.Point;

public class Swap {
	public static void main(String[] args) {
		Point p1 = new Point(5, 2);
		Point p2 = new Point(-3, 6);

		swapPoints(p1, p2);

		System.out.println("p1: ("+p1.x+", "+p1.y+")");
		System.out.println("p2: ("+p2.x+", "+p2.y+")");

	}
	
	//vertauscht die Point-Objekte
	public static void swapPoints(Point p1, Point p2) {
		int tempX = p1.x;
		int tempY = p1.y;

		p1.x = p2.x;
		p1.y = p2.y;
		p2.x = tempX;
		p2.y = tempY;
	}
}
