import javax.swing.*;

public class JRadioButtonBeispiel {
    public static void main(String[] args) {
        JFrame meinJFrame = new JFrame();
        meinJFrame.setTitle("JButtonGroup Beispiel");
        meinJFrame.setSize(250, 250);
        JPanel panel = new JPanel();

        JLabel frage = new JLabel("Welches Geschlecht hast du?");
        panel.add(frage);

        //JRadioButtons werden erstellt
        JRadioButton auswahl1 = new JRadioButton("weiblich");
        JRadioButton auswahl2 = new JRadioButton("männlich");

        //ButtonGroup wird erstellt
        ButtonGroup gruppe = new ButtonGroup();

        //JRadioButtons werden zur ButtonGroup hinzugefügt
        gruppe.add(auswahl1);
        gruppe.add(auswahl2);

        //JRadioButtons werden Panel hinzugefügt
        panel.add(auswahl1);
        panel.add(auswahl2);

        meinJFrame.add(panel);
        meinJFrame.setVisible(true);

    }
}