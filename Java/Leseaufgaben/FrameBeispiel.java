// Import-Anweisung für unseren JFrame

import javax.swing.*;

public class FrameBeispiel {
    public static void main(String[] args) {
        /* Erzeugung eines neuen Frames mit dem
           Titel "Mein JFrame Beispiel" */
        JFrame meinFrame = new JFrame("Mein JFrame Beispiel");
        /* Wir setzen die Breite und die Höhe
           unseres Fensters auf 200 Pixel */
        meinFrame.setSize(200, 200);
        /* Hinzufügen einer einfachen Komponente
           (hier: JLabel) */
        meinFrame.add(new JLabel("Beispiel JLabel"));
        // Wir lassen unseren Frame anzeigen
        meinFrame.setVisible(true);
    }
}