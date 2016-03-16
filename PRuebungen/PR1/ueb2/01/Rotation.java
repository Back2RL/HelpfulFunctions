/**
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
 *	Fr 9. Okt 13:56:48 CEST 2015
 *	-----------------------------------------
 *	Dieses Programm vertauscht die Koordinaten x und y, wobei es x zusätzlich negiert.
 */
public class Rotation {
	/* Methode, die beim Programmstart zuerst ausgeführt wird. */
	public static void main(String[] args) {
	int x= 2;
	int y= 3;

	// y sichern, da es mit -x beschrieben wird	
	int tempY= y;
	
	y = -x;
	x = tempY;

	System.out.println(x + " " + y);
	}
}
