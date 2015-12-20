
// Import-Anweisungen
import java.awt.Color;
import javax.swing.*;

public class JDesktopPaneBeispiel
{
    // main-Methode
    public static void main(String[] args)
    {
        // Erzeugung eines neuen Dialoges
        JDialog meinJDialog = new JDialog();
        meinJDialog.setTitle("JDesktopPane Beispiel");
        meinJDialog.setSize(450,300);

        // JDesktopPane wird erstellt
        JDesktopPane deskPane = new JDesktopPane();

        // Hintergrundfarbe wird auf blau gesetzt
        deskPane.setBackground(Color.blue);

        //JInternalFrame wird erstellt
        JInternalFrame inFrame1 = new JInternalFrame("Dokument 1", true,
                true, true, true);
        JInternalFrame inFrame2 = new JInternalFrame("Dokument 2");

        //JInternalFrames werden unserem JDesktopPane hinzugefügt
        deskPane.add(inFrame1);
        deskPane.add(inFrame2);

        //Größe der JInternalFrames wird gesetzt
        inFrame1.setSize(200,200);
        inFrame2.setSize(200,200);

        //Position der JInternalFrames wird gesetzt
        inFrame1.setLocation(0,0);
        inFrame2.setLocation (200,0);

        //JInternalFrames werden sichtbar gemacht
        inFrame1.show();
        inFrame2.show();

        // JDesktopPane wird dem Dialog hinzugefügt
        meinJDialog.add(deskPane);

        // Wir lassen unseren Dialog anzeigen
        meinJDialog.setVisible(true);

    }
}