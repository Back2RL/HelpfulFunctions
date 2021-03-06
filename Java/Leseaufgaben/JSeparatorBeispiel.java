// Import-Anweisungen

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

public class JSeparatorBeispiel {
    // main-Methode
    public static void main(String[] args) {
        /* Erzeugung eines neuen Dialoges */
        JDialog meinJDialog = new JDialog();
        meinJDialog.setTitle("JMenuBar für unser Java Tutorial Beispiel.");
        // Wir setzen die Breite auf 450 und die Höhe auf 300 Pixel
        meinJDialog.setSize(450, 300);
        // Zur Veranschaulichung erstellen wir hier eine Border
        Border bo = new LineBorder(Color.yellow);
        // Erstellung einer Menüleiste
        JMenuBar bar = new JMenuBar();
        // Wir setzen unsere Umrandung für unsere JMenuBar
        bar.setBorder(bo);
        // Erzeugung eines Objektes der Klasse JMenu
        JMenu menu = new JMenu("Ich bin ein JMenu");
        // Erzeugung eines Objektes der Klasse JMenuItem
        JMenuItem item = new JMenuItem("Ich bin das JMenuItem");
        // Wir fügen das JMenuItem unserem JMenu hinzu
        menu.add(item);
        // Erzeugung eines Objektes der Klasse JSeparator
        JSeparator sep = new JSeparator();
        // JSeparator wird unserem JMenu hinzugefügt
        menu.add(sep);
        // Erzeugung eines Objektes der Klasse JCheckBoxMenuItem
        JCheckBoxMenuItem checkBoxItem = new JCheckBoxMenuItem
                ("Ich bin das JCheckBoxMenuItem");
        // JCheckBoxMenuItem wird unserem JMenu hinzugefügt
        menu.add(checkBoxItem);
        // Erzeugung eines Objektes der Klasse JRadioButtonMenuItem
        JRadioButtonMenuItem radioButtonItem = new JRadioButtonMenuItem
                ("Ich bin ein JRadionButtonMenuItem");
        // JRadioButtonMenuItem wird unserem JMenu hinzugefügt
        menu.add(radioButtonItem);
        // Menü wird der Menüleiste hinzugefügt
        bar.add(menu);
        // Menüleiste wird für den Dialog gesetzt
        meinJDialog.setJMenuBar(bar);
        // Wir lassen unseren Dialog anzeigen
        meinJDialog.setVisible(true);
    }
}