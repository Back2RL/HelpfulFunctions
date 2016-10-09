package Nr_8_GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClickCountFrame extends JFrame {
    private int numClicks;

    public ClickCountFrame() {
        numClicks = 0;
        setTitle("Count Clicks");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 70);
        final JLabel jLabelClicks = new JLabel("0 Clicks");

        JButton jButtonClick = new JButton("Click");
        jButtonClick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EventQueue.invokeLater(new Runnable() {
                                           @Override
                                           public void run() {
                                               setCursor(new Cursor(Cursor.WAIT_CURSOR));
                                               jButtonClick.setEnabled(false);
                                           }
                                       });

                Thread backGroundOperation = new Thread() {
                    @Override
                    public void run() {
                        for (     long i = 0;
                                  i < 10_000_000_000L; ++i){
                        }

                        ++numClicks;
                        EventQueue.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                jLabelClicks.setText(numClicks + " Clicks");
                                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                jButtonClick.setEnabled(true);
                            }
                        });
                    }
                };
                backGroundOperation.start();
            }
        });


        getContentPane().add(jLabelClicks, BorderLayout.WEST);
        getContentPane().add(jButtonClick, BorderLayout.EAST);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame f = new ClickCountFrame();
                f.setVisible(true);
            }
        });
    }
}
