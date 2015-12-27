import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Created by Leo on 24.12.2015.
 */
public class Kunze {
    public static void main(String[] args) {
        ArrayList<String> auswahl = new ArrayList<>();
        auswahl.add("Statistik erstellen und Programmende");
        auswahl.add("Beerenmix Fruchtaufstrich 200ml");
        auswahl.add("Pflaume Holunder Fruchtaufstrich 200 ml");
        auswahl.add("Sauerkirsche Fruchtaufstrich");
        auswahl.add("Apfel Birnen Saft 1l");
        auswahl.add("Apfel Schorle 1l");
        auswahl.add("Apfelsaft Familienpackung 10l");
        auswahl.add("Brotzeitbrett Kunze");

        int zähler = 0;
        for (String auswahlElement : auswahl
                ) {
            System.out.println("<" + zähler + "> " + auswahlElement);
            zähler++;
        }
        System.out.println();

        TreeMap<String, TreeMap<Double, Integer>> produktDaten = new TreeMap<String, TreeMap<Double, Integer>>();

        int wahl;
        double preis = 0.0;
        int anzahl = 0;
        Scanner console = new Scanner(System.in);

        do {
            wahl = readInt(console, "Ihre Wahl? ");
            if (wahl == 0) {
                break;
            }
            String name = auswahl.get(wahl);
            produktDaten.put(name, new TreeMap<Double, Integer>());
            produktDaten.get(name).put(readDouble(console, "Zu welchem Preis? "), readInt(console, "Wie viele Einheiten? "));
            System.out.println();

        } while (true);
        System.out.println("Statistik (in der obigen Sortierung)");


    }

    public static double round2(double value) {
        if (Double.isNaN(value)) return value;
        if (Double.isInfinite(value)) return value;
        return Math.round(value * 100) / 100.0;
    }

    public static int readInt(Scanner console, String text) {
        do {
            System.out.print(text);
            if (console.hasNextInt()) {
                return console.nextInt();
            }
            console.next();
        } while (true);
    }

    public static double readDouble(Scanner console, String text) {
        do {
            System.out.print(text);
            if (console.hasNextDouble()) {
                return console.nextDouble();
            }
            console.next();
        } while (true);
    }


}
