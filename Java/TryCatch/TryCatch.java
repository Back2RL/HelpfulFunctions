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
 import java.util.*;
public class TryCatch {
	public static void main(String[] args) {

	    Scanner console = new Scanner(System.in);
	    Scanner input = null; // mandatory for use of try-catch
	    
	    String filename = new String();
	    
	    do {
	        System.out.print("Filename: ");
	        filename = console.nextLine();
	        try {
	            input = new  Scanner(new File(filename));
	        } catch (FileNotFoundException e) {
	            System.out.println("File not found");
	        }
	    } while (input == null);
	    
	    System.out.println(filename + " exists: " + new File(filename).exists());
	    
	    
	    console.close();
	    input.close();
	        


	}
}
