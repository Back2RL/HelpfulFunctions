// Dieses Programm sucht einen Weg durch einen Irrgarten
// Autor Robert Garmann

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Irrgarten_v2 {

    // Hauptprogramm.
    // Intialisierung des Irrgartens und Durchführung der Suche.
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 1) {
            System.out.println("Dateiname als Argument erwartet");
            return;
        }
        Scanner input = new Scanner(new File(args[0]));
        char[][] irrgarten = init(input);
        input.close();
        System.out.println("Irrgarten:");
        print(irrgarten);
        System.out.println();
        // Suche Startpunkt
        for (int y = 0; y < irrgarten.length; y++) {
            for (int x = 0; x < irrgarten[y].length; x++) {
                if ('S' == irrgarten[y][x]) {
                    suche(irrgarten, x, y, "");
                    return; // es gibt (hoffentlich) nur einen Startpunkt
                }
            }
        }
        System.out.println("Kein Startpunkt");
    }

    // Initialisiert den Irrgarten und liefert ihn als
    // 2-dimensionales Array. Der erste Index
    // ist der y-Index, der zweite der x-Index.
    public static char[][] init(Scanner input) {
        ArrayList<String> lines = new ArrayList<String>();
        int breite = 0;
        while (input.hasNextLine()) {
            String line = input.nextLine();
            if (breite < line.length()) {
                breite = line.length();
            }
            lines.add(line);
        }
        // Entferne Leerzeilen am Anfang und am Ende
        while (lines.size() > 0 && lines.get(0).trim().equals("")) {
            lines.remove(0);
        }
        while (lines.size() > 0 && lines.get(lines.size() - 1).trim().equals("")) {
            lines.remove(lines.size() - 1);
        }
        int hoehe = lines.size();

        char[][] irrgarten = new char[hoehe][breite];
        for (int y = 0; y < hoehe; y++) {
            Arrays.fill(irrgarten[y], ' ');
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                irrgarten[y][x] = line.charAt(x);
            }
        }
        return irrgarten;
    }

    // Ausgabe des Irrgartens
    public static void print(char[][] irrgarten) {
        for (int i = 0; i < irrgarten.length; i++) {
            char[] zeile = irrgarten[i];
            for (int j = 0; j < zeile.length; j++) {
                System.out.print(zeile[j]);
            }
            System.out.println();
        }
    }

    // Rekursive Funktion.
    // Liefert einen der folgenden Strings:
    //  "Draußen", wenn x/y außerhalb des Irrgartens liegt
    //  "R R ... R R Ziel", wenn ein Weg zum Ziel gefunden wurde
    //                 (R steht hier als Platzhalter für eine Richtung)
    //  "Wand", wenn an x/y eine Wand ist
    //  "Zyklus", wenn an x/y ein Brotkrumen liegt
    // Die Funktion legt als erstes einen Brotkrumen ab und versucht dann
    // in allen vier Himmelsrichtungen einen Weg zum Ziel zu finden.
    // Bei erfolgloser Suche wird der Brotkrumen wieder gelöscht.
    public static void suche(char[][] irrgarten, int x, int y, String s) {

        StringBuilder sb = new StringBuilder(s);


        if (y < 0 || y >= irrgarten.length || x < 0 || x >= irrgarten[0].length) {
            sb.append("Draußen"); // tut nichts, lokale Variable
            return;
        }

        // Bin ich am Ziel?
        if (irrgarten[y][x] == 'Z') {
            sb.append("Ziel"); // Ja!
//        }

//        if (sb.toString().endsWith("Ziel")) {
            System.out.println("Lösung:");
            print(irrgarten);
            System.out.println(sb.toString());
            System.out.println();
            return;
        }

        // Gegen eine Wand gerannt?
        if (irrgarten[y][x] == '#') {
            //sb.append("Wand"); // Ja!
            return;
        }

        // War ich hier schonmal?
        if (irrgarten[y][x] == '*') {
            sb.append("Zyklus"); // Ja!
            return;
        }

        // Brotkrumen ablegen
        irrgarten[y][x] = '*';


        suche(irrgarten, x, y + 1, sb.toString() + "S ");
        suche(irrgarten, x, y - 1, sb.toString() + "N ");
        suche(irrgarten, x + 1, y, sb.toString() + "O ");
        suche(irrgarten, x - 1, y, sb.toString() + "W ");


        // und wieder aufräumen
        irrgarten[y][x] = ' ';

        // Ziel nicht gefunden

    }

    public static void lösungAusgeben(char[][] irrgarten, String lösungsWeg) {
        System.out.println("Lösung:");
        print(irrgarten);
        System.out.println(lösungsWeg);
        System.out.println();
    }
}

