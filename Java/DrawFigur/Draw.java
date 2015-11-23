/**
 *	Mo 23. Nov 21:57:41 CET 2015
 *
 *	PR1, WS2015/16
 *
 *	Leonard Oertelt
 *	Matrikelnummer 1276156
 *	leonard.oertelt@stud.hs-hannover.de
 *
 *	-----------------------------------------
 *	Work in progress...
 */
import java.util.*;
public class Draw {
	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);
		int size = console.nextInt();


		for (int i=0; i<size; i++) {
			for (int j=0; j<2; j++) {
				printChar('*',i/2);




			}


		}
	}

	public static void printChar(char c, int n) {
		for (int i=0; i<n; i++) {
			System.out.print(c);
		}


	}
}
