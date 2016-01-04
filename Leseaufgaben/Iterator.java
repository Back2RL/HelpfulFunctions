import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.*;
import java.util.Collections;

/**
 * Created by oertelt on 04.01.16.
 * L.8.6
 * example for faster/more efficient implementation when used with LinkedList
 */
public class Iterator {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        Collections.addAll(list, 1, 1, 3, 5, 5, 5, 5, 7, 7, 11);

        ListIterator<Integer> iter = list.listIterator();

        int oldVal = iter.next();
        while (iter.hasNext()) {
            int val = iter.next();
            if (val == oldVal) {
                iter.remove();
            } else {
                oldVal = val;
            }
        }
        System.out.println(list);
    }
}
