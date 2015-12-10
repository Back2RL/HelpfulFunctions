import java.util.ArrayList;
import java.util.HashSet;

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
public class Wrapper {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();

        list.add(new Integer(17));
        list.add(17); //works with all primitives

        Integer object = list.get(0);
        System.out.println(object.intValue());

        System.out.println(list.get(0) == list.get(1)); //is sometimes true and sometimes false
        //to compare the content use equals:
        System.out.println(list.get(0).equals(list.get(1)));




    }
}
