import java.util.ArrayList;

/**
 * Created by Leo on 23.12.2015.
 */
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
