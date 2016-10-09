// Import-Anweisungen

import javax.swing.*;
import java.awt.*;

public class JToolBarBeispiel extends JDialog{
    public JToolBarBeispiel(){

        setTitle("JToolBar Beispiel");
        setSize(450, 300);
        // Erstellung einer Menüleiste
        JMenuBar menu = new JMenuBar();
        // Menü wird hinzugefügt
        menu.add(new JMenu("Datei"));

        //Toolbar wird erstellt
        JToolBar tbar = new JToolBar();
        //Größe der Toolbar wird gesetzt
        tbar.setSize(230, 20);

        // Schaltflächen werden erzeugt und unserer JToolBar hinzugefügt
        tbar.add(new JButton("Drehen"));
        tbar.add(new JButton("Verkleinern"));
        tbar.add(new JButton("Vergrößern"));

        // Menüleiste wird für den Dialog gesetzt
        setJMenuBar(menu);

        //Unsere Toolbar wird zum Dialog hinzugefügt
        add(tbar);

        JPanel panel = new JPanel();
        panel.setBackground(Color.YELLOW);
        add(panel);

    }


    // main-Methode
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JToolBarBeispiel j = new JToolBarBeispiel();
                j.setVisible(true);
            }
        });

    }
}