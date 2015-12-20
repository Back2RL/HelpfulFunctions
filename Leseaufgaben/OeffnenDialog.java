import javax.swing.*;
import java.awt.*;
import java.util.*;
import javax.swing.JOptionPane;

public class OeffnenDialog
{
    public static void main(String[] args) {
        // JFileChooser-Objekt erstellen
        JFileChooser chooser = new JFileChooser();
        // Dialog zum Oeffnen von Dateien anzeigen
        int rueckgabeWert = chooser.showOpenDialog(null);

        /* Abfrage, ob auf "Öffnen" geklickt wurde */
        if (rueckgabeWert == JFileChooser.APPROVE_OPTION) {
            // Ausgabe der ausgewaehlten Datei
            System.out.println("Die zu öffnende Datei ist: " +
                    chooser.getSelectedFile().getName());
        }
// Bsp. 1: Eingabe-Dialog
        JOptionPane.showInputDialog("Dies ist ein Input Dialog");

// Bsp. 2: Dialog zur Bestätigung
        JOptionPane.showConfirmDialog(null, "Dies ist ein Confirm Dialog");

// Bsp. 3: Nachrichten-Dialog
        JOptionPane.showMessageDialog(null, "Dies ist ein Message Dialog");

// Bsp. 4: Optionsdialog mit Warnhinweis
        JOptionPane.showOptionDialog(null, "Dies ist ein Optionsdialog","Optionsdialog",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE, null,
                new String[]{"A", "B", "C"}, "B");


}
}