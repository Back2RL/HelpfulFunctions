import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TimeZone;

/**
 * Created by Leo on 08.01.2016.
 */
public class PrimzahlZerlegung {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        System.out.print("Geben Sie Ihre Zahl ein: ");
        int zahl = (int)console.nextLong();

        System.out.println(TimeZone.getDefault());
        System.out.println((int)(System.nanoTime() *0.000000001));
        System.out.println((int)(System.currentTimeMillis()*0.001));
long start = System.currentTimeMillis();
        List<Integer> Teiler = new ArrayList<>();
        Teiler.add(1);
        int teiler = 2;
        int rest = zahl;
        do {
            if (rest % teiler == 0) {
                Teiler.add(teiler);
                rest /= teiler;
            } else {
                teiler++;
            }

        } while (teiler < zahl || rest == 0);
        if(Teiler.size() == 1)Teiler.add(zahl);
long end = System.currentTimeMillis();
        System.out.print(end - start);
        System.out.println("ms");
        System.out.println(Teiler);
    }
}



