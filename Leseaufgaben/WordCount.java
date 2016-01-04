import java.io.IOException;
import java.util.HashMap;
import java.io.*;
import java.util.*;

/**
 * Created by Leo on 15.12.2015.
 */
public class WordCount {
    public static void main(String[] args) throws IOException {

        HashMap<String, Integer> woerter = new HashMap<>();
        Scanner input = new Scanner(new File("test.txt"));

        while (input.hasNext()) {
            String wort = input.next();
            int anzahl;
            if (woerter.containsKey(wort)) {

            }
        }

/*
        Iterator<String> iter = woerter.keySet().iterator();
        for (String wort : woerter.keySet()
                ) {
            System.out.println(wort + ": " + woerter.get(wort));
        }
*/

    }
}
