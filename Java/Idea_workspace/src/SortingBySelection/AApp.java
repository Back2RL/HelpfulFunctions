package SortingBySelection;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Leo2400 on 20.06.2016.
 */
public class AApp {
    private JPanel panel1;
    private JButton button1;
    private JButton button2;
    private JButton openButton;
    private JTextField textField1;

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        panel1.setPreferredSize(new Dimension(200, 70));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(0, 0));
        panel1.add(panel2, BorderLayout.NORTH);
        final JToolBar toolBar1 = new JToolBar();
        panel2.add(toolBar1, BorderLayout.NORTH);
        button1 = new JButton();
        button1.setText("Button");
        toolBar1.add(button1);
        button2 = new JButton();
        button2.setText("Button");
        toolBar1.add(button2);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout(0, 0));
        panel2.add(panel3, BorderLayout.CENTER);
        panel3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), null));
        textField1 = new JTextField();
        textField1.setPreferredSize(new Dimension(300, 24));
        panel3.add(textField1, BorderLayout.WEST);
        openButton = new JButton();
        openButton.setMinimumSize(new Dimension(20, 32));
        openButton.setPreferredSize(new Dimension(30, 20));
        openButton.setText("Open");
        panel3.add(openButton, BorderLayout.CENTER);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}
