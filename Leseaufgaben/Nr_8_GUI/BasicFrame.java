package Nr_8_GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BasicFrame extends JFrame {
    public BasicFrame() {
        setTitle("Title");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JLabel yellowLabel = new JLabel("Hello World");
        yellowLabel.setOpaque(true);
        yellowLabel.setBackground(Color.YELLOW);
        yellowLabel.setPreferredSize(new Dimension(200, 80));

        getContentPane().add(yellowLabel, BorderLayout.CENTER);

        // creating buttons
        JButton buttonPAFF = new JButton("PAFF");
        getContentPane().add(buttonPAFF, BorderLayout.NORTH);

        JButton buttonPOFF = new JButton("POFF");
        getContentPane().add(buttonPOFF, BorderLayout.SOUTH);

        // create an action listener to do sth
        class MyListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        yellowLabel.setText(yellowLabel.getText() + " X");
                    }
                });
            }
        }
        // create a listener to perform the task when paff is pressed
        MyListener aListener = new MyListener();
        buttonPAFF.addActionListener(aListener);

        // one listener can listen to multiple inputs:
        buttonPOFF.addActionListener(aListener);

        // listeners can be created directly:
        JButton buttonPENG = new JButton("Peng");
        getContentPane().add(buttonPENG, BorderLayout.EAST);

       buttonPENG.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               EventQueue.invokeLater(new Runnable() {
                   @Override
                   public void run() {
                       yellowLabel.setText(yellowLabel.getText()+" Peng");
                       buttonPENG.setEnabled(false);
                   }
               });
               Thread backGroundOperation = new Thread(){
                   @Override
                   public void run() {
                       try {
                           Thread.sleep(3000);
                       } catch (InterruptedException e1) {
                           e1.printStackTrace();
                       }
                       EventQueue.invokeLater(new Runnable() {
                           @Override
                           public void run() {
                               buttonPENG.setEnabled(true);
                           }
                       });
                   }
               };
               backGroundOperation.start();
           }
       });

        // show tooltip:
        buttonPENG.setToolTipText("schreibt Peng");

        // hotkey alt + key
        buttonPOFF.setMnemonic(KeyEvent.VK_O);
        // show tooltip:
        buttonPOFF.setToolTipText("(Alt + o)");

        // set buttonIcon:
        buttonPENG.setIcon(new ImageIcon("paff.png"));

// mouselistener events, all methods have to be implemented
     /* yellowLabel.addMouseListener(new MouseListener() {
          @Override
          public void mouseClicked(MouseEvent e) {
              EventQueue.invokeLater(new Runnable() {
                  @Override
                  public void run() {
                      if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() > 1){
                          if(yellowLabel.getBackground().equals(Color.YELLOW)){
                              yellowLabel.setBackground(Color.BLUE);
                          } else{
                              yellowLabel.setBackground(Color.YELLOW);
                          }
                      }
                  }
              });
          }

          @Override
          public void mousePressed(MouseEvent e) {
          }

          @Override
          public void mouseReleased(MouseEvent e) {
          }

          @Override
          public void mouseEntered(MouseEvent e) {
          }

          @Override
          public void mouseExited(MouseEvent e) {
          }
      });
*/
        yellowLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() > 1){
                            if(yellowLabel.getBackground().equals(Color.YELLOW)){
                                yellowLabel.setBackground(Color.BLUE);
                            } else{
                                yellowLabel.setBackground(Color.YELLOW);
                            }
                        }
                    }
                });
            }
        });

        MouseAdapter mouseAdapterForButtonPoff = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        getContentPane().setCursor(new Cursor(Cursor.MOVE_CURSOR));
                    }
                });
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                Point p = e.getLocationOnScreen();
                System.out.println("dragged to ("+p.x+","+p.y+")");

            }
            @Override
            public void mouseReleased(MouseEvent e) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        getContentPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    }
                });
            }
        };

        buttonPOFF.addMouseListener(mouseAdapterForButtonPoff);
        buttonPOFF.addMouseMotionListener(mouseAdapterForButtonPoff);



        pack();
    }

    public static void main(String[] args) {
        JFrame f = new BasicFrame();
        f.setVisible(true);
    }
}
