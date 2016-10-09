// Import-Anweisung für unseren JDialog

import javax.swing.*;

// Import-Anweisung für unser JLabel

public class DialogBeispiel {
    // main-Methode
    public static void main(String[] args) {
        // Erzeugung eines neuen JDialogs mit
        // dem Titel "Beispiel JDialog"
        JDialog meinJDialog = new JDialog();
        // Titel wird gesetzt
        meinJDialog.setTitle("Mein JDialog Beispiel");

        // Höhe und Breite des Fensters werden
        // auf 200 Pixel gesetzt
        meinJDialog.setSize(200, 200);
        // Dialog wird auf modal gesetzt
        meinJDialog.setModal(true);
        // Hinzufügen einer Komponente,
        // in diesem Fall ein JLabel
        meinJDialog.add(new JLabel("Beispiel JLabel"));
        // Wir lassen unseren JDialog anzeigen
        meinJDialog.setVisible(true);
    }
}