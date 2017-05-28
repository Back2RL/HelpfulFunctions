/**
 *	Sa 02. Jan 15:24:04 CEST 2016
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

import java.util.ArrayList;

public class Verdoppeln {
    public static void main(String[] args) {
        ArrayList<String> liste = new ArrayList<>();
        liste.add("Ich");
        liste.add("studiere");
        liste.add("in");
        liste.add("Hannover");

        verdoppeln(liste);
        System.out.println(liste);
    }

    public static void verdoppeln(ArrayList liste) {
        for (int i = 0; i < liste.size(); i += 2) {
            liste.add(i, liste.get(i));
        }
    }
}
