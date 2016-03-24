/**
 *	Sa 02. Jan 15:48:07 CEST 2016
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
import java.util.Scanner;
import java.util.TreeMap;

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

        // Auswahlmöglichkeiten auf Console ausgeben
        int zaehler = 0;
        for (String auswahlElement : auswahl) {
            System.out.println("<" + zaehler + "> " + auswahlElement);
            zaehler++;
        }
        System.out.println();

        // Collection anlegen zum speichern von Produktpreisen mit jeweiliger Anzahl
        TreeMap<String, TreeMap<Double, Integer>> produktDaten = new TreeMap<String, TreeMap<Double, Integer>>();

        Scanner console = new Scanner(System.in);

        // Produktdaten vom Benutzer einlesen
        while (true) {
            int wahl = readInt(console, "Ihre Wahl? ");
            if (wahl == 0) {
                break; // fertig mit Einlesen
            }

            String name = null;
            try {
                // prüfen ob bereits Daten zu einem Produkt existieren
                name = auswahl.get(wahl);
                if (!produktDaten.containsKey(name)) {
                    produktDaten.put(name, new TreeMap<>());
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Es existiert zu <" + wahl + "> keine Auswahlmöglichkeit");
                System.out.println();
                continue;
            }

            // Preis und Anzahl eines Produktes einlesen
            double preis = readDouble(console, "Zu welchem Preis? ");
            int anzahl = readInt(console, "Wie viele Einheiten? ");

            // prüfen ob das Produkt bereits zum selben Preis eine Anzahl hat
            if (produktDaten.get(name).containsKey(preis)) {
                produktDaten.get(name).put(preis, produktDaten.get(name).get(preis) + anzahl); // Anzahlen addieren
            } else {
                produktDaten.get(name).put(preis, anzahl); // Preis mit Anzahl anlegen
            }
            System.out.println();
        }

        // Statistik ausgeben
        System.out.println("Statistik (in der obigen Sortierung)");
        System.out.println();


        for (String name : auswahl) {
            try {
                if (produktDaten.get(name).keySet().size() > 0) { // sind Daten zu einem Produkt verfügbar?
                    System.out.println("Statistik für " + name);
                    System.out.println("  Einzelverkäufe (nach Preis aufsteigend sortiert):");

                    int anzahl = 0;
                    double preis = 0.0;
                    for (Double key : produktDaten.get(name).keySet()) {
                        anzahl += produktDaten.get(name).get(key);  // Gesamtanzahl des Produkts ermitteln
                        preis += produktDaten.get(name).get(key) * key; // Gesamteinnahme berechnen
                        System.out.println("    " + produktDaten.get(name).get(key) + " Einheiten zu " + round2(key) + " EUR");
                    }
                    System.out.println("  Akkumulierte Werte:");
                    System.out.println("    Einheiten (gesamt): " + anzahl);
                    System.out.println("    Durchschnittspreis: " + round2(preis/anzahl) + " EUR");
                    System.out.println();
                }
            } catch (NullPointerException e) {
                e.getLocalizedMessage();
            }
        }
    }

    // Double auf 2 Stellen runden
    public static double round2(double value) {
        if (Double.isNaN(value)) return value;
        if (Double.isInfinite(value)) return value;
        return Math.round(value * 100) / 100.0;
    }

    // Integer von der Console einlesen
    public static int readInt(Scanner console, String text) {
        do {
            System.out.print(text);
            if (console.hasNextInt()) {
                return console.nextInt();
            }
            console.next();
        } while (true);
    }

    // Double von der Console einlesen
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
