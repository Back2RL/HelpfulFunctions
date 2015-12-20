// Import-Anweisungen
import javax.swing.*;
import javax.swing.plaf.metal.MetalIconFactory;

public class JLabelBeispiel
{
    // main-Methode
    public static void main(String[] args)
    {
        // Erzeugung eines neuen Dialoges
        JDialog meinJDialog = new JDialog();
        meinJDialog.setTitle("JLabel Beispiel");
        meinJDialog.setSize(450,300);

        // Wir holen uns ein Icon aus dem dem Java Look and Feel
        Icon icon = MetalIconFactory.getFileChooserHomeFolderIcon();

        // Wir erstellen ein JLabel mit einem Text und unserem Icon
        // Die horizontale Ausrichtung setzen wir auf "CENTER"
        JLabel label = new JLabel ("My 127.0.0.1 is my castle",
                icon, JLabel.CENTER);

        // Die vertikale Ausrichtung des JLabels setzen wir auf "TOP"
        label.setVerticalAlignment(JLabel.TOP);

        // Die relative Ausrichtung des Textes zum Icon setzen wir auf "LEFT"
        label.setHorizontalTextPosition(JLabel.LEFT);

        // Wir fügen das JLabel unserem Dialog hinzu
        meinJDialog.add(label);

        // Wir lassen unseren Dialog anzeigen
        meinJDialog.setVisible(true);

    }
}