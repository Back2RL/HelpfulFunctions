package SortingBySelection;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SelectSortTest extends JFrame {

    private static final boolean AUTOMATED = false;
    private static final boolean USING_KEYS = true;
    private static final boolean DEBUG = true;
    private static final int ANZAHL = 15;

    private JTextField analysisDir;
    private String analysisDirPath;
    private JButton startAnalysis;
    private JButton abortAnalysis;

    private Thread worker;

    private JDialog left;
    private JDialog right;

    private KeyAdapter listener;

    private int choice;

    private Point leftLocation;
    private Point rightLocation;

    private String leftPath;
    private String rightPath;

    private static final Dimension defaultSize = new Dimension(300, 150);
    private Dimension screenSize;

    private static class SortObject implements Comparable<SortObject> {

        public int getRating() {
            return rating;
        }

        // makes sure that the rating is higher then the other one
        public void increaseRating(SortObject other) {
            if (rating < other.getRating()) {
                rating = other.getRating() + 1;
//                other.rating++;
            } else {
                rating++;
                other.rating++;
            }
        }

        private int rating;
        public int value;
        public String path;

        public SortObject(int value, String path) {
            this.value = value;
            this.rating = 0;
            this.path = path;
        }

        /**
         * return 0 if rating is the same
         * returns -1 if the rating of o is lower
         * returns 1 if the rating of o is higher
         */
        @Override
        public int compareTo(SortObject o) {
            if (o.rating == rating) return 0;
            if (o.rating < rating) return -1;
            return 1;
        }
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                SelectSortTest f = new SelectSortTest();
                f.setVisible(true);
            }
        });
    }

    public SelectSortTest() {
        //single monitor
        // screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // multimonitor
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        screenSize = new Dimension(width, height);

        listener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);

                if (e.getKeyChar() == KeyEvent.VK_1) {
                    System.out.println("main: 1 pressed");
                    if (left != null) {
                        left.dispose();
                    }
                    if (right != null) {
                        right.dispose();
                    }
                    choice = 1;
                } else if (e.getKeyChar() == KeyEvent.VK_2) {
                    System.out.println("main: 2 pressed");
                    if (left != null) {
                        left.dispose();
                    }
                    if (right != null) {
                        right.dispose();
                    }
                    choice = 2;
                }
            }
        };

        setTitle("select sort demo");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(defaultSize);
        setLocation((int) (screenSize.width * 0.5 - defaultSize.width * 0.5), (int) (screenSize.height * 0.5 - defaultSize.height * 0.5));


        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);

                if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
                    System.out.println("Escape pressed");
                    System.exit(0);
                }
            }
        });
        addKeyListener(listener);

        JButton showLeft = new JButton();
        showLeft.setText("Open Left");
        showLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (right != null) {
                    right.dispose();
                }
                showRightImage(rightPath);
            }
        });


        JButton showRight = new JButton();
        showRight.setText("Open Right");
        showRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (right != null) {
                    right.dispose();
                }
                showRightImage(rightPath);
            }
        });

        DocumentListener docListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateAnalysisDirPath();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateAnalysisDirPath();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        };
        analysisDir = new JTextField("Pfad zum Verzeichnis hier eingeben");
        analysisDir.setToolTipText("Pfad zum Verzeichnis hier eingeben");
        analysisDir.getDocument().addDocumentListener(docListener);

        startAnalysis = new JButton("Start");
        abortAnalysis = new JButton("Abbruch");
        abortAnalysis.setEnabled(false);

        startAnalysis.setMinimumSize(new Dimension(100, 20));
        startAnalysis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                worker = new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        perform();

                        EventQueue.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                startAnalysis.setEnabled(true);
                                abortAnalysis.setEnabled(false);
                            }
                        });

                    }
                };
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        startAnalysis.setEnabled(false);
                        abortAnalysis.setEnabled(true);
                    }
                });
                worker.start();
            }
        });


        abortAnalysis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (worker != null && worker.isAlive()) {
                    worker.interrupt();
                    worker = null;


                }
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        abortAnalysis.setEnabled(false);
                        startAnalysis.setEnabled(true);
                        if (right != null) {
                            right.dispose();
                        }
                        if (left != null) {
                            left.dispose();
                        }
                    }
                });

            }
        });


        getContentPane().add(analysisDir, BorderLayout.NORTH);
        getContentPane().add(showLeft, BorderLayout.WEST);
        getContentPane().add(showRight, BorderLayout.EAST);
        getContentPane().add(abortAnalysis, BorderLayout.SOUTH);
        getContentPane().add(startAnalysis, BorderLayout.CENTER);
        pack();

    }

    private void updateAnalysisDirPath() {
        analysisDirPath = analysisDir.getText();
    }

    private void perform() {
        List<SortObject> unsorted = new ArrayList<>();

        List<SortObject> sorted = new ArrayList<>();


        List<File> files = analyzeDirectory(analysisDirPath);
        if (files == null) {
            System.err.println("Keine Dateien gefunden, Fehler beim Analysieren des Verzeichnis.");
            return;
        }
        for (File file : files) {
            unsorted.add(new SortObject((int) (Math.random() * ANZAHL) + 1, file.getPath()));
        }

        Scanner console = new Scanner(System.in);

        int cnt = 0;
        while (true) {
            if (DEBUG) {
                for (SortObject o : sorted
                        ) {
                    System.out.print(o.value + ", ");
                }
                System.out.println();
            }

            if (unsorted.size() == 0) {
                ArrayList<String> sortedList = new ArrayList<>();
                for (SortObject o : sorted
                        ) {
                    System.out.println(o.value + ", " + o.path);
                    sortedList.add(o.path);
                }

                System.out.println("Factor = " + (double) cnt / ANZAHL);
                System.out.println("Runs =          " + cnt);
                System.out.println("O(n*log(n)) ? = " + (int) (ANZAHL * Math.log(ANZAHL)));


                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        ImageList showList = new ImageList(sortedList);
                        showList.setVisible(true);
                    }
                });
                break;
            }


            SortObject toInsert = unsorted.get(0);
            unsorted.remove(0);

            if (sorted.size() == 0) {
                sorted.add(toInsert);
                continue;
            }

            int start = 0;
            int end = sorted.size() - 1;
            int mid = (start + end) / 2 + (start + end) % 2;

            int singleInsertCnt = 0;

            while (end >= start) {
                singleInsertCnt++;
                if (DEBUG) System.out.println("----------");
                if (DEBUG) System.out.println("indizes = " + start + " - " + mid + " - " + end);
                if (DEBUG) System.out.println("einzufügen = " + toInsert.value + " (" + toInsert.getRating() + ")");
                if (DEBUG)
                    System.out.println("zu vergleichender eintrag = " + sorted.get(mid).value + " (" + sorted.get(mid).getRating() + ")");

                if (DEBUG) System.out.println("Choose the better one:");
                if (DEBUG)
                    System.out.println("press 1 to choose the first and 2 to choose the second, choose 0 if equal");
                if (DEBUG) System.out.println(sorted.get(mid).value + " - " + toInsert.value);

                if (!AUTOMATED) {
                    leftPath = toInsert.path;
                    rightPath = sorted.get(mid).path;
                    showLeftImage(leftPath);
                    showRightImage(rightPath);

                    if (USING_KEYS) {
                        while (!(choice == 1 || choice == 2)) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                            }
                        }
                        if (choice == 1) {
                            end = mid - 1;
                            mid = (start + end) / 2;
                            choice = 0;
                            //          break;
                        } else if (choice == 2) {
                            start = mid + 1;
                            mid = (start + end) / 2;
                            choice = 0;
                        }

                    } else {
                        if (console.hasNextInt()) {
                            int next = console.nextInt();
                            if (next == 1) {
                                end = mid - 1;
                                mid = (start + end) / 2;

                                //          break;
                            } else if (next == 2) {
                                start = mid + 1;
                                mid = (start + end) / 2;
                            } else if (next == 0) {
                                end--;
                                mid = (start + end) / 2;
                            }
                        }
                    }
                    // testing
                } else {
                    if (sorted.get(mid).value > toInsert.value) {
                        end = mid - 1;
                        mid = (start + end) / 2;

                        //          break;
                    } else if (sorted.get(mid).value < toInsert.value) {
                        start = mid + 1;
                        mid = (start + end) / 2;
                    } else if (sorted.get(mid).value == toInsert.value) {
                        end--;
                        mid = (start + end) / 2;
                    }
                }


                ++cnt;
//                unsorted.sort(new Comparator<SortObject>() {
//                    @Override
//                    public int compare(SortObject o1, SortObject o2) {
//                        return o1.compareTo(o2);
//                    }
//                });
                if (DEBUG) System.out.println("run number = " + cnt);
            }

            if (DEBUG) System.out.println(start + " - " + mid + " - " + end);
            if (DEBUG) System.out.println("took " + singleInsertCnt + " runs to insert a single value");
            sorted.add(start, toInsert);

        }


    }

    private void showImage(JDialog o, String pathToImage) {
        if (pathToImage == null) return;
        class ImagePanel extends JPanel {

            private BufferedImage image;

            public ImagePanel() {
                try {
                    image = ImageIO.read(new File(pathToImage));
                } catch (IOException ex) {
                    // handle exception...
                    JOptionPane.showMessageDialog(this,
                            "Fehler beim Laden des Bilds: " + ex.getMessage(),
                            "Fehler",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (image == null) return;
                this.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
            }

            @Override
            protected void paintComponent(Graphics g) {
                if (image == null) return;
                super.paintComponent(g);

                ImageIcon icon = new ImageIcon(pathToImage);

                int width = icon.getIconWidth();
                int height = icon.getIconHeight();

                float imageAspectRatio = (float) width / height;
                float jframeAspectRatio = getWidth() * 0.5f / getHeight();

                int pnlW = (int) (getWidth());
                int pnlH = getHeight();

                System.out.println("Panelsize = " + pnlW + "x" + pnlH);

                int x = 0;
                int y = 0;

                System.out.println(imageAspectRatio + " vs " + jframeAspectRatio);

                if (imageAspectRatio < jframeAspectRatio) {
                    y = pnlH;
                    x = (int) ((y * imageAspectRatio));

                } else {
                    x = pnlW;
                    y = (int) ((x / imageAspectRatio));
                }

                g.drawImage(image, pnlW / 2 - x / 2, getHeight() / 2 - y / 2, x, y, null);
            }

        }
        ImagePanel image = new ImagePanel();


        o.add(image);
        o.addKeyListener(new KeyAdapter() {
            Dimension size;

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);


                if (e.getKeyChar() == KeyEvent.VK_SPACE) {
                    System.out.println("space pressed");
                    if (size == null) {
                        size = right.getSize();

                        o.setLocation(0, 0);
                        o.setSize(screenSize);
                        image.update(image.getGraphics());
                    } else {
                        o.setSize(size);

                        o.setLocation(o.equals(left) ? leftLocation : rightLocation);

                        size = null;
                    }
                }
            }
        });

    }

    private void showLeftImage(String path) {
        if (path == null) return;
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Dimension imageDialogSize = new Dimension((int) (screenSize.width * 0.4), (int) (screenSize.height * 0.9));

                left = new JDialog();
                left.setTitle("Left");
                left.setAlwaysOnTop(true);
                leftLocation = new Point((int) (screenSize.width * 0.25 - imageDialogSize.width * 0.5), (int) (screenSize.height * 0.05));
                left.setLocation(leftLocation);
                left.setPreferredSize(imageDialogSize);
                left.pack();
                left.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        super.keyPressed(e);
                        if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
                            System.out.println("left: Escape pressed");
                            left.dispose();
                        }
                    }
                });
                left.addKeyListener(listener);
                showImage(left, path);
                left.setVisible(true);
            }
        });
    }

    private void showRightImage(String path) {
        if (path == null) return;
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Dimension imageDialogSize = new Dimension((int) (screenSize.width * 0.4), (int) (screenSize.height * 0.9));

                right = new JDialog();
                right.setTitle("Right");
                right.setAlwaysOnTop(true);
                rightLocation = new Point((int) (screenSize.width * 0.75 - imageDialogSize.width * 0.5), (int) (screenSize.height * 0.05));
                right.setLocation(rightLocation);
                right.setPreferredSize(imageDialogSize);
                right.pack();
                right.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        super.keyPressed(e);
                        if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
                            System.out.println("right: Escape pressed");
                            right.dispose();
                        }
                    }
                });
                right.addKeyListener(listener);

                showImage(right, path);
                right.setVisible(true);
            }
        });
    }

    public List<File> analyzeDirectory(String dir) {
        File analysisDir = getDirectory(dir);
        if (analysisDir == null) {
            System.err.println("Fehler beim Verzeichnis finden.");
            return null;
        }
        if (dir == null || dir.equals("") || analysisDir == null) {
            // handle exception...
            JOptionPane.showMessageDialog(this,
                    "Angegebener Pfad ist nicht gültig: \"" + dir + "\".\r",
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }


        System.out.println("Starting analysis of: \"" + analysisDir + "\"");

        List<File> pendingDirectories = new ArrayList<>();
        List<File> allFiles = new ArrayList<>();

        while (true) {
            try {
                for (File currentFile : analysisDir.listFiles()) {
                    if (!currentFile.canRead() || currentFile.isHidden() || Files.isSymbolicLink(currentFile.toPath())) {
                        continue;
                    }

                    if (currentFile.isDirectory()) {
                        pendingDirectories.add(currentFile);
                        continue;
                    }
                    allFiles.add(currentFile);
                }
            } catch (NullPointerException e) {
                System.out.println("Can not access: " + analysisDir.toString() + " : NullPointerException");
            }
            if (!pendingDirectories.isEmpty()) {
                analysisDir = pendingDirectories.get(0);
                pendingDirectories.remove(0);
                continue;
            } else {
                break;
            }
        }
        // System.out.println(allFiles.toString());
        System.out.println("-----");
        System.out.println("Number of found files: " + allFiles.size());
        return allFiles;
    }

    private File getDirectory(String path) {
        // invariant 1
        if (path == null || path.equals("")) {
            JOptionPane.showMessageDialog(this,
                    "Angegebener Pfad ist null oder leer: \"" + path + "\".\r",
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
        // invariant 2
        File file = null;
        try {
            file = new File(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!file.isDirectory()) {
            JOptionPane.showMessageDialog(this,
                    "Angegebener Pfad ist kein Verzeichnis: \"" + path + "\".\r",
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return file;
    }

}
