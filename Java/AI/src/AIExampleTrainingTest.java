import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private static final boolean DEBUG = false;

    private ArrayList<Primitive> primitives = new ArrayList<Primitive>();
    private MyPanel jpnl;

    private ArrayList<Creature> actors;
    private GenPool genpool;
    private static final double mutationRate = 0.01;
    private final static int numberOfLifeforms = 100;
    private final static int[] topology = new int[]{1, 2, 2, 2};
    private final static double runTime = 10.0;
    private Vec2D targetLocation;

    public AIExampleTrainingTest() {
        targetLocation = new Vec2D(0, 0);
        setTitle("Sketchpad");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        jpnl = new MyPanel();
        jpnl.setPreferredSize(new Dimension(640, 480));
        // jpnl.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
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
                if (DEBUG) System.out.println(KeyEvent.getKeyText(evt.getKeyCode())); // nur so
            }

            @Override
            public void keyTyped(KeyEvent evt) {
                print(evt.getKeyChar());
            }
        };
        jpnl.addKeyListener(kl);

        jpnl.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (DEBUG) System.out.println("Mouse moved to " + e.getPoint().toString());
                targetLocation = new Vec2D(e.getPoint().getX(), e.getPoint().getY());
            }
        });

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
                                if (DEBUG) System.out.println("FPS = " + (int) (1.0 / deltaTime));
                                try {
                                    sleep(1000);
                                } catch (InterruptedException e) {
                                }
                            }
                        }
                    };
                    fpsDisplayer.start();


                    genpool = new GenPool(numberOfLifeforms, topology);


                    actors = new ArrayList<>();

                    for (int i = 0; i < numberOfLifeforms; ++i) {
                        Random rand = new Random();
                        actors.add(new Creature(1000, 800, genpool.getBrains().get(i)));
                    }


                    while (true) {
                        Random rand = new Random();

                        List<NeuronNet> survivors = new ArrayList<>();
                        for (NeuronNet brain : genpool.getBrains()) {
                            survivors.add(brain);
                        }

                        while (actors.size() < numberOfLifeforms) {
                            // TODO: evolve
                            NeuronNet brain = new NeuronNet(topology);
                            NeuronNet parent1 = survivors.get(rand.nextInt(survivors.size()));
                            NeuronNet parent2 = survivors.get(rand.nextInt(survivors.size()));

                            List<Double> newGenome = new ArrayList<>();
                            for (int i = 0; i < parent1.getGenome().size(); ++i) {
                                // Mutation?
                                if (rand.nextDouble() < mutationRate) {
                                    newGenome.add(rand.nextDouble() * 2.0 - 1.0);
                                } else {
                                    // no mutation, get one of the parents gene
                                    if (rand.nextBoolean()) {
                                        newGenome.add(parent1.getGenome().get(i));
                                    } else {
                                        newGenome.add(parent2.getGenome().get(i));
                                    }
                                }
                            }
                            brain.setGenome(newGenome);
                            genpool.getBrains().add(brain);
                            actors.add(new Creature(1280, 800, brain));
                        }
                        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                        Vec2D startLoc = new Vec2D(200, 200);
                        for (Creature actor : actors) {

                            // TODO:
                            actor.getBrain().setFitness(0.0);
                            // TODO: set random startlocation
                            actor.setLocation(startLoc);
                            //actor.setLocation(new Vec2D(Math.random() * screenSize.getWidth(),Math.random() * screenSize.getHeight()));
                        }

                        // TODO: new random targetlocation

                        targetLocation = new Vec2D(Math.random() * screenSize.getWidth(), Math.random() * screenSize.getHeight());
//                        try {
//                            Robot robot = new Robot(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
//                            robot.mouseMove((int)(Math.random() * screenSize.getWidth()), (int)(Math.random() * screenSize.getHeight()));
//                        } catch (AWTException e) {
//
//                        } catch (HeadlessException e) {
//
//                        }

                        double elapsedTime = 0.0;
                        if (DEBUG) System.out.println("nextGen");

                        while (bIsRunning) {

                            time = System.nanoTime();
                            deltaTime = (time - prevTime) * 1E-9;
                            elapsedTime += deltaTime;
                            if (elapsedTime > runTime) {
                                break;
                            }
                            prevTime = time;


                            tick(deltaTime);

                            jpnl.repaint();

                            long sleepTime = tickIntervall - (System.nanoTime() - time);
                            if (sleepTime > 0L) {
                                try {
                                    //if(DEBUG)System.out.println(sleepTime / 1_000_000L + " - "+(int)(sleepTime % 1000L));
                                    sleep(sleepTime / 1_000_000L, (int) (sleepTime % 1000L));
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }


                        }
                        calcFitness();
                        if (DEBUG) System.out.println(genpool.getBrains().toString());
                        genpool.sortBrains();
                        if (DEBUG) System.out.println(genpool.getBrains().toString());
                        genpool.setBrains(genpool.getBrains().subList(0, genpool.getBrains().size() / 2));

                        int actorNum = actors.size();
                        int cnt = 0;
                        int removed = 0;
                        while (cnt < actorNum - removed) {
                            if (genpool.getBrains().contains(actors.get(cnt).getBrain())) {
                                cnt++;
                                continue;
                            }
                            ++removed;
                            actors.remove(cnt);
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
        for (Creature actor : actors) {


            Vec2D dirToTarget = targetLocation.subtract(actor.getLocation()).getNormalized();
            double dotRightToTarget = actor.getForwardDir().getRotated(90).dotProduct(dirToTarget);

            List<Double> inputVals = new ArrayList<>();

            double angle = Math.acos(actor.getForwardDir().dotProduct(dirToTarget)) / Math.PI;

            inputVals.add(Math.signum(dotRightToTarget) * angle);

            actor.learn(inputVals, dt);

            double distance = actor.getLocation().distance(targetLocation);
            double fitness = actor.getBrain().getFitness() + distance * 0.001;
            actor.getBrain().setFitness(fitness);

            actor.move(dt);
        }
    }

    class MyPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Startpunkt ermitteln:
            Point position = new Point((int) jpnl.getBounds().getCenterX(), (int) jpnl.getBounds().getCenterY());

            for (Creature actor : actors) {
                actor.draw(g);
            }


            for (Primitive o : primitives) {
                o.zeichne(g, position);
            }
        }
    }

    public void calcFitness() {
        double avgFittness = 0;
        double factor = 1.0 / actors.size();

        for (Creature actor : actors) {

            avgFittness += actor.getBrain().getFitness() * factor;
        }
    }
}
