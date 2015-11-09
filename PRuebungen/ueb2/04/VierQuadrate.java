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
 *	Fr 9. Okt 13:53:15 CEST 2015
 *	-----------------------------------------
 *	tue 3 mal, zähler1
 *		tue 8 mal, zähler2
 *			schreibe =
 *			wenn zähler2 = 3
 *				schreibe +
 * 		wenn zähler1 kleiner 3,
 * 			dann tue 3 mal
 *				tue 7 mal, zähler3
 *					wenn zähler3 = 1 oder 7
 * 						schreibe #
 *					ansonsten
 *						schreibe " "
 *						wenn zähler3 = 4
 *							schreibe |
 */
public class VierQuadrate {

	public static void main(String[] args) {
		for(int i = 0; i < 3; i++){
			writeBorder();		
			if (i < 2) {
				writeBody();		
			}
		}
	}

	//	
	private static void writeBorder(){
		for(int i = 0; i < 8; i++){
			System.out.print("=");
			if(i == 3) {
				System.out.print("+");
			}
		}
		System.out.println();
	}

	//	
	private static void writeBody(){
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 8; j++) {	
				if(j == 0 || j == 7)	{
					System.out.print("#");
				}else {
					System.out.print(" ");
					if(j == 3){
						System.out.print("|");
					}
				}
			}
			System.out.println();
		}
	}
}
