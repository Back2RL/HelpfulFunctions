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
 *	Fr 9. Okt 14:05:50 CEST 2015
 *	-----------------------------------------
 *	
 */
public class VierQuadrate {
	
	public static final int SIZE = 6;

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
		for(int i = 0; i < (2 * SIZE + 2); i++){
			System.out.print("=");
			if(i == SIZE) {
				System.out.print("+");
			}
		}
		System.out.println();
	}

	//	
	private static void writeBody(){
		for (int i = 0; i < SIZE; i++){
			for (int j = 0; j < (2 * SIZE + 2); j++) {	
				if(j == 0 || j == (2 * SIZE + 1))	{
					System.out.print("#");
				}else {
					System.out.print(" ");
					if(j == SIZE){
						System.out.print("|");
					}
				}
			}
			System.out.println();
		}
	}
}
