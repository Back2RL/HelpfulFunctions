// Import-Anweisung

import java.io.File;
import java.io.IOException;

public class ErstelleDatei {
    public static void main(String[] args) {
        // Erzeugung unseres File-Objektes
        File beispielDatei = new File("C:/Beispiel.txt");
        /* Überprüfung, ob die mit dem Pfad assozierte Datei
           bzw. Verzeichnis existiert */
        if (!beispielDatei.exists()) {
            try {
                // Erstelle Datei auf Festplatte
                boolean wurdeErstellt = beispielDatei.createNewFile();
                // Überprüfung, ob die Datei erstellt wurde
                if (wurdeErstellt) {
                    System.out.println("Beispiel.txt wurde erfolgreich" +
                            " auf dem Laufwerk c erstellt");
                } else {
                    System.out.println("Beispiel.txt wurde nicht erfolgreich" +
                            " auf dem Laufwerk c erstellt");
                }
            } catch (IOException ex) {
                // Ein Fehler ist aufgetreten.
                ex.printStackTrace();
            }

        }
    }
}