import javax.swing.*;
import java.util.Vector;

public class ComboBoxModelBeispiel {

    public static void main(String[] args) {
        JFrame meinJFrame = new JFrame();
        meinJFrame.setTitle("JComboBox Beispiel");
        meinJFrame.setSize(300, 100);
        JPanel panel = new JPanel();

        JLabel label = new JLabel("Highscore");
        panel.add(label);

        //neuer Vector wird erstellt
        Vector vec = new Vector();

        //JComboBox mit Highscore-Daten wird erzeugt
        JComboBox highscoreCombo = new JComboBox(new HighscoreMutableModel());

        //Highscore-Objekte werden in die JComboBox eingetragen
        highscoreCombo.addItem(new Highscore("Ben", 3));
        highscoreCombo.addItem(new Highscore("Jochen", 1));
        highscoreCombo.addItem(new Highscore("Robert", 2));

        panel.add(highscoreCombo);
        meinJFrame.add(panel);
        meinJFrame.setVisible(true);

    }
}