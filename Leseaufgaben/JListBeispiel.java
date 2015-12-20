
import javax.swing.*;

public class JListBeispiel {

    public static void main(String[] args) {
        JFrame meinJFrame = new JFrame();
        meinJFrame.setTitle("JListBeispiel");
        meinJFrame.setSize(300, 300);
        JPanel panel = new JPanel();

        JLabel frage = new JLabel("Für welche Themen interessierst du dich?");
        panel.add(frage);

        // Array für unsere JList
        String interessen[] = {"Politik", "Autos", "Mode",
                "Film- und Fernsehen", "Computer", "Tiere", "Sport"};

        //JList mit Einträgen wird erstellt
        JList themenAuswahl = new JList(interessen);

        //JList wird Panel hinzugefügt
        panel.add(themenAuswahl);

        meinJFrame.add(panel);
        meinJFrame.setVisible(true);

    }
}