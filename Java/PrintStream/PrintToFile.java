/**
 *	Mi 21. Okt 13:17:04 CEST 2015
 *
 *	PR1, WS2015/16
 *
 *	Leonard Oertelt
 *	Matrikelnummer 1276156
 *	leonard.oertelt@stud.hs-hannover.de
 * 
 *	-----------------------------------------
 *	Programmbeschreibung
 */
import java.io.*;
public class PrintToFile {
	public static void main(String[] args) throws IOException {

	    PrintStream output = new PrintStream(new File("output.txt"));
	    
	    output.println("this is being written in a file");
	    output.append("test");
	    
	    output.close();


	}
}
