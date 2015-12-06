/**
 * Do 22. Okt 12:01:22 CEST 2015
 * <p>
 * WS2015/16
 * <p>
 * Leonard Oertelt
 * Matrikelnummer 1276156
 * leonard.oertelt@stud.hs-hannover.de
 * <p>
 * Fr 3. Okt 14:24:49 CEST 2015
 * -----------------------------------------
 * Programmbeschreibung
 */

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Leseaufgabe07 {
    public static void main(String[] args) {

        // null and NullPointerException
        String[] words = new String[5];
        System.out.println(words[0]); // is "null" by default
        Point[] points = new Point[3];
        if (points[0] == null) {
            System.out.println("Yes point is indeed " + points[0]);
        }
        try {
            System.out.println(words[0].toUpperCase());
        } catch (NullPointerException e) {
            System.out.println("Error: " + e.getLocalizedMessage());
        }

        String sentence = "Hello World";
        char[] letters = sentence.toCharArray();
        String[] splitText = sentence.split(" ");
        for (int i = 0; i < splitText.length; i++) {
            System.out.println("\"" + splitText[i] + "\"");
        }


        // Strings and Arrays
        System.out.println(areAnagrams("tonne","noten"));

        //ArrayList<E>
        ArrayList<String> someWords = new ArrayList<String>();
        someWords.add("Hello World");
        someWords.add("and Good Bye");

        System.out.println(someWords.toString());
        if(someWords.contains("Hello World")) {
            System.out.println("Yes, someWords contains \"Hello World\"");
        }

    }

    public static boolean areAnagrams(String wordA, String wordB) {
        char[] contentA = wordA.toCharArray();
        char[] contentB = wordB.toCharArray();
        Arrays.sort(contentA);
        Arrays.sort(contentB);
        if (Arrays.equals(contentA,contentB)) {
            return true;
        }
        return false;
    }
}
