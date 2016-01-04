import javax.swing.*;

public class JToggleButtonBeispiel {
    public static void main(String[] args) {
        JFrame meinJFrame = new JFrame();
        meinJFrame.setTitle("JToggleButton Beispiel");

        JPanel panel = new JPanel();

        // JToggleButton mit Text "Drück mich" wird erstellt
        JToggleButton toggleButton = new JToggleButton("Drück mich", true);

        // JToggleButton wird dem Panel hinzugefügt
        panel.add(toggleButton);

        meinJFrame.add(panel);
        //Fenstergröße wird so angepasst, dass der Inhalt angezeigt wird
        meinJFrame.pack();
        meinJFrame.setVisible(true);
    }
}