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
import java.util.*;
import java.io.*;
public class TryCatchAdvanced {
    public static void main(String[] args) { 
        Scanner input= null; 
        Scanner erloeseScanner= null; 
        try { 
            Scanner console= new Scanner(System.in); 
            input= getInput(console); 
            while (input.hasNextLine()) { 
                String produkt= input.nextLine(); 
                String erloese= null; 
                try { 
                    erloese= input.nextLine(); 
                    System.out.println(produkt+ ": "); 
                    erloeseScanner= new Scanner(erloese); // (*) 
                    verarbeite(erloeseScanner); 
                    erloeseScanner.close(); 
                } catch (NoSuchElementException e) { 
                    System.out.println("Erlöse für "+produkt+" nicht gefunden."); 
                } 
            } 
        } catch (Exception e) { 
            System.out.println("Sonstiger Fehler"); 
        } finally { 
            if (input != null)          input.close(); 
            if (erloeseScanner != null) erloeseScanner.close(); 
        } 
    } 
}