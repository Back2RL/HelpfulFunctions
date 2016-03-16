/**
 *	Mo 30. Nov 16:39:21 CET 2015
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
import java.util.*;
import java.io.*;

public class Diff {
	public static void main(String[] args) {

		diff("text1.txt", "text2.txt");
		diff("text3.txt", "text4.txt");
		diff("text5.txt", "text6.txt");

	}

	public static void diff(String file1, String file2) {
		Scanner scanFile1 = null;
		Scanner scanFile2 = null;
		try {
			scanFile1 = new Scanner(new File(file1));
		} catch (FileNotFoundException e) {
			System.out.println("Kann Datei nicht finden: " + file1);
			return;
		}

		try {
			scanFile2 = new Scanner(new File(file2));
		} catch (FileNotFoundException e) {
			System.out.println("Kann Datei nicht finden: " + file2);
			return;
		}

		int line = 0;
		boolean equal = true;
		boolean b1, b2;
		do {
			line++;
			b1 = scanFile1.hasNextLine();
			b2 = scanFile2.hasNextLine();

			String lineFile1 = b1 ? scanFile1.nextLine() : "";
			String lineFile2 = b2 ? scanFile2.nextLine() : "";

			if (!lineFile1.equals(lineFile2)) {
				if(equal) {
					System.out.println("Unterschiede gefunden:");
					equal = false;	
				}
				System.out.println("Zeile "+line+":");
				System.out.println("< "+lineFile1);
				System.out.println("> "+lineFile2);
				System.out.println();
			}

		} while(b1 || b2);

		scanFile1.close();
		scanFile2.close();

		if(equal) {
			System.out.println("Keine Unterschiede gefunden");
		}

	}
}
