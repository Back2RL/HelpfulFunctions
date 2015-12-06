/**
 * So 06. Dez 16:25:00 CEST 2015
 *
 * PR1, WS2015/16
 *
 * Leonard Oertelt
 * Matrikelnummer 1276156
 * leonard.oertelt@stud.hs-hannover.de
 *
 * Julian Opitz
 * Matrikelnummer 1302082
 * julian.opitz@stud.hs-hannover.de
 *
 * -----------------------------------------
 * Programmbeschreibung
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class WordLengths {
    public static void main(String[] args) {
        try {
            wordLengths(args[0]);
        }
        catch (IndexOutOfBoundsException e){
            System.out.println("Es wurde kein Dateiname übergeben.");
        }
    }

    public static void wordLengths(String filename) {
        File file = new File(filename);
        Scanner readFile = null;
        try {
            readFile = new Scanner(file);
            int[] lengths = new int[80];
            while (readFile.hasNext()) {
                String currentWord = readFile.next();
                lengths[currentWord.length() - 1]++;
            }

            int lastIndex = 0;
            for (int i = 0; i < 80; i++) {
                if (lengths[i] > 0) {
                    lastIndex = i;
                }
            }

            for (int i = 0; i <= lastIndex; i++) {
                System.out.print((i + 1) + ": " + lengths[i] + "\t");
                printStars(lengths[i]);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Datei \"" + filename + "\" kann nicht gefunden werden.");
        }

    }

    public static void printStars(int n) {
        for (int i = 0; i < n; i++) {
            System.out.print("*");
        }
        System.out.println();
    }

}
