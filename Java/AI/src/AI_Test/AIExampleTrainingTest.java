package AI_Test;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

interface Primitive {
    /**
     * Zeichnet das Objekt und ver?ndert dabei ggf. die aktuelle Zeichenposition
     *
     * @param g        hierhin wird gezeichnet
     * @param position In/Out-Parameter
     */
    void zeichne(Graphics g, Point position);
}

class Linie implements Primitive {
    private int dx, dy;

    public Linie(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void zeichne(Graphics g, Point position) {
        g.drawLine(position.x, position.y, position.x + dx, position.y + dy);
        position.x += dx;
        position.y += dy;
    }
}

class Zeichen implements Primitive {
    private char c;

    public Zeichen(char c) {
        this.c = c;
    }

    @Override
    public void zeichne(Graphics g, Point position) {
        g.drawString(String.valueOf(c), position.x, position.y);
    }
}

@SuppressWarnings("serial")
public class AIExampleTrainingTest extends JFrame {
    private ArrayList<Primitive> primitives = new ArrayList<Primitive>();
    private MyPanel jpnl;

    public AIExampleTrainingTest() {
        setTitle("Sketchpad");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        jpnl = new MyPanel();
        jpnl.setPreferredSize(new Dimension(400, 300));
        // Normalerweise kann ein JPanel keinen Tastaturfokus erhalten.
        // Das ändern wir nun ...
        jpnl.setFocusable(true);


        // Listener hinzuf?gen
        KeyListener kl = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                int keyCode = evt.getKeyCode();
                int d = 2;
                switch (keyCode) {
                    case KeyEvent.VK_LEFT:
                        step(-d, 0);
                        break;
                    case KeyEvent.VK_RIGHT:
                        step(d, 0);
                        break;
                    case KeyEvent.VK_UP:
                        step(0, -d);
                        break;
                    case KeyEvent.VK_DOWN:
                        step(0, d);
                        break;
                }
                System.out.println(KeyEvent.getKeyText(evt.getKeyCode())); // nur so
            }

            @Override
            public void keyTyped(KeyEvent evt) {
                print(evt.getKeyChar());
            }
        };
        jpnl.addKeyListener(kl);

        // GAME LOOP

        synchronized (this) {
            Thread looper = new Thread() {

                boolean bIsRunning;
                private double deltaTime;

                @Override
                public void run() {

                    deltaTime = 0.0;
                    bIsRunning = true;
                    Thread.currentThread().setPriority(MAX_PRIORITY);

                    long time = System.nanoTime();
                    long prevTime = time;

                    int refreshRate = GraphicsEnvironment.getLocalGraphicsEnvironment().
                            getDefaultScreenDevice().getDisplayMode().getRefreshRate();
                    if (refreshRate == DisplayMode.REFRESH_RATE_UNKNOWN) {
                        refreshRate = 60;
                    }

                    long tickIntervall = 1_000_000_000L / (long) refreshRate;

                    Thread fpsDisplayer = new Thread() {
                        @Override
                        public void run() {
                            yield();
                            Thread.currentThread().setPriority(MIN_PRIORITY);
                            while (bIsRunning) {
                                System.out.println("FPS = " + (int) (1.0 / deltaTime));
                                try {
                                    sleep(1000);
                                } catch (InterruptedException e) {
                                }
                            }
                        }
                    };
                    fpsDisplayer.start();

                    while (bIsRunning) {

                        time = System.nanoTime();
                        deltaTime = (time - prevTime) * 1E-9;
                        prevTime = time;


                        tick(deltaTime);

                        jpnl.repaint();

                        long sleepTime = tickIntervall - (System.nanoTime() - time);
                        if (sleepTime > 0L) {
                            try {
                                //System.out.println(sleepTime / 1_000_000L + " - "+(int)(sleepTime % 1000L));
                                sleep(sleepTime / 1_000_000L, (int) (sleepTime % 1000L));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }


                    }
                }
            };
            looper.start();
        }


        getContentPane().add(jpnl, BorderLayout.CENTER);
        pack();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                AIExampleTrainingTest f = new AIExampleTrainingTest();
                f.setVisible(true);
            }
        });
    }

    private void step(int dx, int dy) {
        primitives.add(new Linie(dx, dy));
        jpnl.repaint();

    }

    private void print(char c) {
        primitives.add(new Zeichen(c));
        jpnl.repaint();
    }

    public void tick(final double dt) {

    }

    class MyPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Startpunkt ermitteln:
            Point position = new Point((int) jpnl.getBounds().getCenterX(), (int) jpnl.getBounds().getCenterY());

            for (Primitive o : primitives) {
                o.zeichne(g, position);
            }
        }
    }
}
