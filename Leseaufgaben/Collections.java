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

import java.util.ArrayList;

public class Collections {
    public static void main(String[] args) {
        //foreach example:
        int sum = 0;
        ArrayList<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("World");
        for (String s : list) {
            sum += s.length();
        }
        System.out.println("length = " + sum);


//        list.sort();


    }
}
