// Import-Anweisungen

import javax.swing.*;
import java.awt.*;

public class JSplitPaneBeispiel extends JFrame {
    public JSplitPaneBeispiel() {
        // Erzeugung eines neuen Dialoges
        setTitle("JSplitPane Beispiel");
        setSize(450, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

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
        add(splitpane);
    }

    // main-Methode
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JSplitPaneBeispiel j = new JSplitPaneBeispiel();
                // Wir lassen unser JFrame anzeigen
                j.setVisible(true);
            }
        });
    }
}