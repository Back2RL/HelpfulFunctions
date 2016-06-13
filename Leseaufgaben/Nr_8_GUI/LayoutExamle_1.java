package Nr_8_GUI;

import javax.swing.*;
import java.awt.*;

public class LayoutExamle_1 extends JFrame{
    public LayoutExamle_1(){
        setTitle("Verschachtelte Layouts");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        southPanel.add(new JButton("OK"));
        southPanel.add(new JButton("Cancel"));
        add(southPanel,BorderLayout.SOUTH);

        JScrollPane center = new JScrollPane(new JEditorPane());
        center.setPreferredSize(new Dimension(300,200));
        add(center,BorderLayout.CENTER);
        pack();


    }
    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                LayoutExamle_1 l = new LayoutExamle_1();
                l.setVisible(true);
            }
        });
    }

}
