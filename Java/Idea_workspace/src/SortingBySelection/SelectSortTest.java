package SortingBySelection;

import SQLite.SQLManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.*;

public class SelectSortTest extends JFrame {

    private static final boolean AUTOMATED = false;
    private static final boolean USING_KEYS = true;
    private static final boolean DEBUG = false;
    private static final int ANZAHL = 15;


    private static final Dimension defaultSize = new Dimension(300, 150);

    private JTextField analysisDir;
    private String analysisDirPath;

    private List<File> pendingDirectories;
    private List<File> allFiles;
    private List<RatedImage> unsorted;
    private List<RatedImage> sorted;
    private ArrayList<String> sortedList;

    private JButton startAnalysis;
    private JButton abortAnalysis;
    private JDialog left;
    private JDialog right;

    private Thread worker;

    private int choice;
    private Point leftLocation;
    private Point rightLocation;
    private String leftPath;
    private String rightPath;
    private Dimension screenSize;

    private SQLManager data;

    public SelectSortTest() {

        pendingDirectories = new ArrayList<>();
        allFiles = new ArrayList<>();
        sortedList = new ArrayList<>();
        unsorted = new ArrayList<>();


        data = new SQLManager();

        sorted = data.getAllEntries();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        //single monitor
        // screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // multimonitor
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        screenSize = new Dimension(width, height);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                data.insertMultipleEntries(sorted);
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                processKeyInput(e);
            }
        });


        setTitle("select sort demo");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(defaultSize);
        setLocation((int) (screenSize.width * 0.5 - defaultSize.width * 0.5), (int) (screenSize.height * 0.5 - defaultSize.height * 0.5));


        JButton showLeft = new JButton();
        showLeft.setText("Open Left");
        showLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (left != null) {
                    left.dispose();
                }
                SelectSortTest.this.showLeftImage(leftPath);
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

        JFileChooser chooser = new JFileChooser(getProperty("user.dir"));
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setMultiSelectionEnabled(false);
        chooser.changeToParentDirectory();
        if (chooser.showOpenDialog(new JDialog(this))
                == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            analysisDirPath = file.getAbsolutePath();
            chooser.setCurrentDirectory(file);
        }
        analysisDir = new JTextField(analysisDirPath);
        analysisDir.setEnabled(false);
        analysisDir.setToolTipText("Pfad zum Verzeichnis hier eingeben");
        //analysisDir.getDocument().addDocumentListener(docListener);
        SelectSortTest thisObj = this;
        analysisDir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (chooser.showOpenDialog(new JDialog(thisObj))
                        == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    analysisDirPath = file.getAbsolutePath();
                    chooser.setCurrentDirectory(file);
                    analysisDir.setText(analysisDirPath);
                }
            }
        });

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
                        sortedList = new ArrayList<String>();
                        for (RatedImage o : sorted
                                ) {
                            out.println(o.path);
                            sortedList.add(o.path);
                        }
                        ImageList showList = new ImageList(sortedList);
                        showList.setVisible(true);
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

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                SelectSortTest f = new SelectSortTest();
                f.setVisible(true);
            }
        });
    }

    private static boolean isImage(File f) {
        boolean valid = true;
        try {
            Image image = ImageIO.read(f);
            if (image == null) {
                valid = false;
            }
        } catch (Exception ex) {
            System.out.println("Error while checking whether file is image");
            valid = false;
        }
        return valid;
    }

    private synchronized void setChoice(int newChoice) {
        this.choice = newChoice;
    }

    private void updateAnalysisDirPath() {
        analysisDirPath = analysisDir.getText();
    }

    private void perform() {


        List<File> files = analyzeDirectory(analysisDirPath);
        if (files == null) {
            err.println("Keine Dateien gefunden, Fehler beim Analysieren des Verzeichnis.");
            return;
        }
        unsorted.clear();

        for (File file : files) {
            if (!isAlreadySorted(file.getPath())) {
                unsorted.add(new RatedImage(file.getPath()));
            }
        }

        int cnt = 0;
        while (true) {

            if (unsorted.size() == 0) {
                sortedList = new ArrayList<>();
                for (RatedImage o : sorted
                        ) {
                    out.println(o.path);
                    sortedList.add(o.path);
                }

                out.println("Factor = " + (double) cnt / ANZAHL);
                out.println("Runs =          " + cnt);
                out.println("O(n*log(n)) ? = " + (int) (ANZAHL * Math.log(ANZAHL)));


                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        ImageList showList = new ImageList(sortedList);
                        showList.setVisible(true);
                    }
                });
                break;
            }


            RatedImage toInsert = unsorted.get(0);
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
                if (DEBUG) out.println("----------");
                if (DEBUG) out.println("indizes = " + start + " - " + mid + " - " + end);
                if (DEBUG) out.println("Choose the better one:");

                leftPath = toInsert.path;
                rightPath = sorted.get(mid).path;
                showLeftImage(leftPath);
                out.println(leftPath);
                showRightImage(rightPath);
                out.println(rightPath);

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

                }
                // testing


                ++cnt;
//                unsorted.sort(new Comparator<RatedImage>() {
//                    @Override
//                    public int compare(RatedImage o1, SortObject o2) {
//                        return o1.compareTo(o2);
//                    }
//                });
                if (DEBUG) out.println("run number = " + cnt);
            }

            if (DEBUG) out.println(start + " - " + mid + " - " + end);
            if (DEBUG) out.println("took " + singleInsertCnt + " runs to insert a single value");
            toInsert.calculateMD5();
            sorted.add(start, toInsert);

        }


    }

    private void showImage(JDialog o, String pathToImage) {
        if (pathToImage == null) return;
        class ImagePanel extends JPanel {

            private BufferedImage image;


            public ImagePanel() {

                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        FullScreenImage fullscreenImage = new FullScreenImage(pathToImage);
                    }
                });


                ImagePanel thisObj = this;
                new Thread() {

                    @Override
                    public void run() {

                        try {
                            image = ImageIO.read(new File(pathToImage));
                        } catch (IOException ex) {
                            // handle exception...
                            JOptionPane.showMessageDialog(thisObj,
                                    "Fehler beim Laden des Bilds: " + ex.getMessage(),
                                    "Fehler",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        if (image == null) return;
                        EventQueue.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                thisObj.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
                                thisObj.paintComponent(thisObj.getGraphics());
                            }
                        });
                    }
                }.start();
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

                out.println("Panelsize = " + pnlW + "x" + pnlH);

                int x = 0;
                int y = 0;

                out.println(imageAspectRatio + " vs " + jframeAspectRatio);

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
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyChar() == KeyEvent.VK_SPACE) {
                    out.println("space pressed");
                    FullScreenImage fullscreenImage = new FullScreenImage(pathToImage);
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
                            out.println("left: Escape pressed");
                            left.dispose();
                        }
                    }
                });
                left.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        processKeyInput(e);
                    }
                });
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
                            out.println("right: Escape pressed");
                            right.dispose();
                        }
                    }
                });
                right.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        processKeyInput(e);
                    }
                });

                showImage(right, path);
                right.setVisible(true);
            }
        });
    }

    public List<File> analyzeDirectory(String dir) {
        File analysisDir = getDirectory(dir);
        if (analysisDir == null) {
            err.println("Fehler beim Verzeichnis finden.");
            return null;
        }
        if (dir == null || dir.equals("")) {
            // handle exception...
            JOptionPane.showMessageDialog(this,
                    "Angegebener Pfad ist nicht gültig: \"" + dir + "\".\r",
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }


        out.println("Starting analysis of: \"" + analysisDir + "\"");


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
                    if (!isImage(currentFile)) {
                        continue;
                    }
                    allFiles.add(currentFile);
                }
            } catch (NullPointerException e) {
                out.println("Can not access: " + analysisDir.toString() + " : NullPointerException");
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
        out.println("-----");
        out.println("Number of found files: " + allFiles.size());
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
        if (file != null && !file.isDirectory()) {
            JOptionPane.showMessageDialog(this,
                    "Angegebener Pfad ist kein Verzeichnis: \"" + path + "\".\r",
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return file;
    }

    public boolean isAlreadySorted(String path) {
        for (RatedImage obj : sorted) {
            if (obj.getPath().equals(path)) {
                return true;
            }
        }
        return false;
    }

    private void closeProgram() {
        for (KeyListener listener : getKeyListeners()) {
            removeKeyListener(listener);
        }
    }

    private void processKeyInput(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_1) {
            out.println("main: 1 pressed");
            if (left != null) {
                left.setVisible(false);
                left.dispose();
            }
            if (right != null) {
                right.setVisible(false);
                right.dispose();
            }
            setChoice(1);
        } else if (e.getKeyChar() == KeyEvent.VK_2) {
            out.println("main: 2 pressed");
            if (left != null) {
                left.setVisible(false);
                left.dispose();
            }
            if (right != null) {
                right.setVisible(false);
                right.dispose();
            }
            setChoice(2);
        }
        if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
            out.println("Escape pressed");
            closeProgram();
        }
    }

    private enum ZoomStatus {
        noneZoomed, leftZoomed, rightZoomed, bothZoomed
    }



}
