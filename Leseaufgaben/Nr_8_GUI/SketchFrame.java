package Nr_8_GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SketchFrame extends JFrame{
    private JPanel jpnl;
    private int x,y;
    public SketchFrame(){

        setTitle("SketchPad");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        jpnl = new JPanel();
        jpnl.setPreferredSize(new Dimension(400,300));

        // manually set the panel to be focusable
        jpnl.setFocusable(true);

        // listener
        KeyListener kl = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                int d = 2;
                switch (keyCode){
                    case KeyEvent.VK_LEFT: step(-d,0); break;
                    case KeyEvent.VK_RIGHT: step(d,0); break;
                    case KeyEvent.VK_UP: step(0,-d); break;
                    case KeyEvent.VK_DOWN: step(0,d); break;
                }
                System.out.println(KeyEvent.getKeyText(e.getKeyCode()));
            }
        };
        jpnl.addKeyListener(kl);

        getContentPane().add(jpnl,BorderLayout.CENTER);
        pack();

        x= (int) jpnl.getBounds().getCenterX();
        y= (int) jpnl.getBounds().getCenterY();

    }
    public void step(final int dx, final int dy ){
        Graphics g = getGraphics();
        g.drawLine(x,y,x+dx,y+dy);
        g.dispose();
        x +=dx;
        y +=dy;
    }
    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                SketchFrame f = new SketchFrame();
                f.setVisible(true);
            }
        });
    }

}
