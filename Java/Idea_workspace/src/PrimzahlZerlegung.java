import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TimeZone;

/**
 * Created by Leo on 08.01.2016.
 */
public class PrimzahlZerlegung {
    public static void main(String[] args) {
        // Zu zerlegende Zahl inlesen
        Scanner console = new Scanner(System.in);
        System.out.print("Geben Sie Ihre Zahl ein: ");
        int zahl = (int) console.nextLong();
        console.close();

        // Startzeit speichern
        long start = System.nanoTime();

        List<Integer> Teiler = new ArrayList<>();
        Teiler.add(1);
        int teiler = 2;
        int rest = zahl;
        do {
            if (rest % teiler == 0) {
                Teiler.add(teiler);
                rest /= teiler;
            } else {
                ++teiler;
            }

        } while (teiler < zahl || rest == 0);

        if (Teiler.size() == 1) Teiler.add(zahl);

        System.out.println("Laufzeit = " + (System.nanoTime() - start) * 1E-9f + " s");
        System.out.println(Teiler);
    }
}



