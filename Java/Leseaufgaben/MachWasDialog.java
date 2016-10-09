import javax.swing.*;

public class MachWasDialog {
    public static void main(String[] args) {
        // JFileChooser-Objekt erstellen
        JFileChooser chooser = new JFileChooser();
        // Dialog zum Speichern von Dateien anzeigen
        chooser.showDialog(null, "MACH WAS");
    }
}