import java.awt.*;


import java.awt.event.*;
import java.util.*;
import java.util.List;

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

    private ArrayList<Creature> actors;
    private GenPool genpool;

    private Vec2D targetLocation;

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

                    int numberOfLifeforms = 1000;
                    int[] topology = new int[]{3,2,2};
                    genpool = new GenPool(numberOfLifeforms, topology);


                    actors = new ArrayList<>();

                    for (int i = 0; i < numberOfLifeforms; ++i) {
                        Random rand = new Random();
                        actors.add(new Creature(1280, 800, genpool.getBrains().get(i)));
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
                                if (rand.nextDouble() < 0.01) {
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
                        Vec2D startLoc = new Vec2D(2000,800);
                        for(Creature actor:actors){
                            actor.setLocation(startLoc);
                        }

                        double runTime = 10.0;
                        double elapsedTime = 0.0;
                        System.out.println("nextGen");

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
                                    //System.out.println(sleepTime / 1_000_000L + " - "+(int)(sleepTime % 1000L));
                                    sleep(sleepTime / 1_000_000L, (int) (sleepTime % 1000L));
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }


                        }
                        calcFitness();
                        System.out.println(genpool.getBrains().toString());
                        genpool.sortBrains();
                        System.out.println(genpool.getBrains().toString());
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
            targetLocation = new Vec2D(1280, 720);



            Vec2D dirToTarget = targetLocation.subtract(actor.getLocation()).getNormalized();
            double dotForward = actor.getForwardDir().dotProduct(dirToTarget);
            double dotRight = actor.getForwardDir().getRotated(90.0).dotProduct(dirToTarget);
            double distance = Math.min(1000.0,actor.getLocation().distance(targetLocation)) / 1000.0;

            List<Double> inputVals = new ArrayList<>();

            inputVals.add(dotForward);
            inputVals.add(dotRight);
            inputVals.add(distance);

            actor.learn(inputVals, dt);

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
            double fitness = actor.getLocation().distance(targetLocation);
            System.out.println(fitness);
            actor.getBrain().setFitness(fitness);
            avgFittness += fitness * factor;
        }
    }
}
