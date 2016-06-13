// Import-Anweisungen

import javax.swing.*;
import java.awt.*;

public class JSplitPaneBeispiel {
    // main-Methode
    public static void main(String[] args) {
        // Erzeugung eines neuen Dialoges
        JDialog meinJDialog = new JDialog();
        meinJDialog.setTitle("JSplitPane Beispiel");
        meinJDialog.setSize(450, 300);

        // Erzeugung zweier JPanel-Objekte
        JPanel panelRot = new JPanel();
        JPanel panelGelb = new JPanel();
        // Hintergrundfarben der JPanels werden gesetzt
        panelRot.setBackground(Color.red);
        panelGelb.setBackground(Color.yellow);
        //Beschriftungen für die beiden Seiten werden erstellt
        JLabel labelRot = new JLabel("Ich bin auf der roten Seite");
        JLabel labelGelb = new JLabel("Ich bin auf der gelben Seite");
        //Labels werden unseren Panels hinzugefügt
        panelRot.add(labelRot);
        panelGelb.add(labelGelb);

        // Erzeugung eines JSplitPane-Objektes mit horizontaler Trennung
        JSplitPane splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        // Hier setzen wir links unser rotes JPanel und rechts das gelbe
        splitpane.setLeftComponent(panelRot);
        splitpane.setRightComponent(panelGelb);

        // Hier fügen wir unserem Dialog unser JSplitPane hinzu
        meinJDialog.add(splitpane);
        // Wir lassen unseren Dialog anzeigen
        meinJDialog.setVisible(true);

    }
}