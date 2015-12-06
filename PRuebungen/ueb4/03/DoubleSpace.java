/**
 *	Mo 30. Nov 14:39:21 CET 2015
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
import java.io.*;
import java.util.*;

public class DoubleSpace {
	public static void main(String[] args) throws IOException {

		doubleSpace("1.txt","2.txt");
	}

	public static void doubleSpace(String file1, String file2) throws IOException {

		File fRead = new File(file1);

		Scanner	scanFRead = new Scanner(fRead);

		PrintStream fWrite = new PrintStream(new File(file2));

		if(scanFRead.hasNextLine()) {
			fWrite.println(scanFRead.nextLine());
			while (scanFRead.hasNextLine()) {
				fWrite.println();
				fWrite.println(scanFRead.nextLine());
			}
		}
		fWrite.close();
		scanFRead.close();

	}
}
