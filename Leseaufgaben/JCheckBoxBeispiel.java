import java.awt.Color;
import javax.swing.*;

public class JCheckBoxBeispiel {
    public static void main(String[] args) {
        JFrame meinJFrame = new JFrame();
        meinJFrame.setSize(450, 300);
        meinJFrame.setTitle("JCheckBox Beispiel");

        JPanel panel = new JPanel();
        panel.setBackground(Color.YELLOW);
        JLabel label = new JLabel("Ich möchte meinen Kaffee mit...");

        panel.add(label);


        //JCheckBoxen werden erstellt
        JCheckBox checkBoxMilch = new JCheckBox("Milch");
        JCheckBox checkBoxZucker = new JCheckBox("Zucker");

        //JCheckBoxen werden Panel hinzugefügt
        panel.add(checkBoxMilch);
        panel.add(checkBoxZucker);

        meinJFrame.add(panel);

        meinJFrame.setVisible(true);
    }
}