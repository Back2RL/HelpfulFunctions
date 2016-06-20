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

    private static final boolean USING_KEYS = true;
    private static final boolean DEBUG = false;

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
    private Thread dataBaseLoader;
    private Thread directoryAnalyzer;

    private PreLoader preLoader;

    private int choice;
    private Point leftLocation;
    private Point rightLocation;
    private String leftPath;
    private String rightPath;
    private Dimension screenSize;

    private SQLManager data;

    public SelectSortTest() {

        preLoader =  new PreLoader(100);

        // get the Screensize
        screenSize = getScreenSize();
        buildMainWindow(this);

        // init lists
        pendingDirectories = new ArrayList<>();
        allFiles = new ArrayList<>();

        startDatabaseLoader();

        // set the visual style to system
        initVisualStyle();


        buildShowButtons();
        buildDirectoryChooser();
        buildStartStopButtons();

        // Windowlistener
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                data.insertMultipleEntries(sorted);
            }
        });

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

    private boolean isImage(File f) {
        boolean valid = true;
        try {
            // TODO: enable start of comparison while images are loaded in the background
            Image image = preLoader.getImage(f.getPath());
            //Image image = ImageIO.read(f);
            if (image == null) {
                valid = false;
            }
            preLoader.addLoadedImage(f.getPath(),image);
        } catch (Exception ex) {
            System.out.println("Error while checking whether file is image");
            valid = false;
        }
        return valid;
    }

    private void buildShowButtons() {
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

        getContentPane().add(showLeft, BorderLayout.WEST);
        getContentPane().add(showRight, BorderLayout.EAST);
    }

    private void buildStartStopButtons() {
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

                        startComparing();

                        EventQueue.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                //startAnalysis.setEnabled(true);
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
                if (directoryAnalyzer != null && directoryAnalyzer.isAlive()) {
                    directoryAnalyzer.interrupt();
                    directoryAnalyzer = null;
                }
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        abortAnalysis.setEnabled(false);
                        // TODO: fix issues with linked list in database, duplicates cause infinite loop
                        //startAnalysis.setEnabled(true);
                        if (right != null) {
                            right.dispose();
                        }
                        if (left != null) {
                            left.dispose();
                        }
                        sortedList = new ArrayList<String>();
                        for (RatedImage o : sorted) {
                            out.println(o.getPath());
                            sortedList.add(o.getPath());
                        }
                        ImageList showList = new ImageList(preLoader,sortedList);
                        sortedList = null;
                        showList.setVisible(true);
                    }
                });
            }
        });

        getContentPane().add(abortAnalysis, BorderLayout.SOUTH);
        getContentPane().add(startAnalysis, BorderLayout.CENTER);
    }

    private void buildDirectoryChooser() {
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
        getContentPane().add(analysisDir, BorderLayout.NORTH);

        analysisDir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (chooser.showOpenDialog(new JDialog(SelectSortTest.this))
                        == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    analysisDirPath = file.getAbsolutePath();
                    chooser.setCurrentDirectory(file);
                    analysisDir.setText(analysisDirPath);
                }
            }
        });
    }

    private synchronized void setChoice(int newChoice) {
        this.choice = newChoice;
    }

    private void updateAnalysisDirPath() {
        analysisDirPath = analysisDir.getText();
    }

    private void startComparing() {

        // wait until dataBaseLoader has finished loading
        while (dataBaseLoader.isAlive()) {
            try {
                System.out.println("Waiting for Database Loader to finish loading.");
                dataBaseLoader.join(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Database Loader has finished loading.");


        synchronized (this) {
            directoryAnalyzer = new Thread() {
                @Override
                public void run() {
                    List<File> files = analyzeDirectory(analysisDirPath);
                    if (files == null) {
                        err.println("Keine Dateien gefunden, Fehler beim Analysieren des Verzeichnis.");
                        return;
                    }

                    unsorted = new ArrayList<>();
                    for (File file : files) {
                        if (!isAlreadySorted(file.getPath())) {
                            System.out.println("Is not yet sorted");
                            unsorted.add(new RatedImage(file.getPath()));
                        } else {
                            System.out.println("Already sorted");
                        }
                    }
                }
            };
            directoryAnalyzer.start();
        }


        // wait until Analyzer has finished analysing
        while (directoryAnalyzer.isAlive()) {
            try {
                System.out.println("Waiting for Analyzer to finish analysing.");
                directoryAnalyzer.join(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Analyzer has finished analysing.");

        System.out.println("Comparison can start:");
        int cnt = 0;
        while (true) {

            if (unsorted.size() == 0) {
                sortedList = new ArrayList<>();
                for (RatedImage o : sorted) {
                    out.println(o.getPath());
                    sortedList.add(o.getPath());
                }

                System.out.println("Comparison is over.");
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        ImageList showList = new ImageList(preLoader,sortedList);
                        showList.setVisible(true);
                    }
                });
                break;
            }


            RatedImage toInsert = unsorted.get(0);
            unsorted.remove(0);

            if (sorted.size() == 0) {
                toInsert.calculateMD5();
                sorted.add(toInsert);
                continue;
            }

            int start = 0;
            int end = sorted.size() - 1;
            int mid = (start + end) / 2 + (start + end) % 2;

            leftPath = toInsert.getPath();
            showLeftImage(leftPath);
            out.println(leftPath);

            while (end >= start) {

                rightPath = sorted.get(mid).getPath();
                showRightImage(rightPath);
                out.println(rightPath);

                while (!(choice == 1 || choice == 2)) {
                    try {
                        Thread.sleep(1000);
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

                ++cnt;
            }

            // calculate the corresponding MD5 hash for the new image
            toInsert.calculateMD5();
            // add the image to the already rated/sorted images at the choosen position
            sorted.add(start, toInsert);
        }
    }

    private void showImage(JDialog o, String pathToImage) {
        if (pathToImage == null) return;
        class ImagePanel extends JPanel {

            private Image image;

            public ImagePanel() {

                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        FullScreenImage fullscreenImage = new FullScreenImage(preLoader,pathToImage);
                    }
                });


                ImagePanel thisObj = this;
                new Thread() {

                    @Override
                    public void run() {

                            image = preLoader.getImage(pathToImage);
                        if(image == null){
                            JOptionPane.showMessageDialog(thisObj,
                                    "Fehler beim Laden des Bilds: ",
                                    "Fehler",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        EventQueue.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                thisObj.setPreferredSize(new Dimension(image.getWidth(thisObj), image.getHeight(thisObj)));
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
        for (Component comp : o.getComponents()) {
            if (comp instanceof ImagePanel) {
                o.remove(comp);
            }
        }
        o.add(image);
        o.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyChar() == KeyEvent.VK_SPACE) {
                    out.println("space pressed");
                    FullScreenImage fullscreenImage = new FullScreenImage(preLoader,pathToImage);
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
                if (left == null) {
                    left = new JDialog();
                    left.setTitle("Left");
                    left.setAlwaysOnTop(true);

                    left.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyPressed(KeyEvent e) {
                            super.keyPressed(e);
                            if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
                                out.println("left: Escape pressed");
                                left.dispose();
                                left = null;
                            }
                        }
                    });
                    left.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyPressed(KeyEvent e) {
                            processKeyInput(e);
                        }
                    });
                }
                leftLocation = new Point((int) (screenSize.width * 0.25 - imageDialogSize.width * 0.5), (int) (screenSize.height * 0.05));
                left.setLocation(leftLocation);
                left.setPreferredSize(imageDialogSize);
                left.pack();

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

                if (right == null) {
                    right = new JDialog();
                    right.setTitle("Right");
                    right.setAlwaysOnTop(true);
                    right.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyPressed(KeyEvent e) {
                            super.keyPressed(e);
                            if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
                                out.println("right: Escape pressed");
                                right.dispose();
                                right = null;
                            }
                        }
                    });
                    right.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyPressed(KeyEvent e) {
                            processKeyInput(e);
                        }
                    });
                }
                rightLocation = new Point((int) (screenSize.width * 0.75 - imageDialogSize.width * 0.5), (int) (screenSize.height * 0.05));
                right.setLocation(rightLocation);
                right.setPreferredSize(imageDialogSize);
                right.pack();


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
        if (sortedList == null) {
            sortedList = new ArrayList<>();
            for (RatedImage obj : sorted) {
                sortedList.add(obj.getPath());
            }
        }
        return sortedList.contains(path);
    }

    private void closeProgram() {
        for (KeyListener listener : getKeyListeners()) {
            removeKeyListener(listener);
        }
    }

    private void processKeyInput(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_1) {
            out.println("main: 1 pressed");
            setChoice(1);
        } else if (e.getKeyChar() == KeyEvent.VK_2) {
            out.println("main: 2 pressed");
            setChoice(2);
        }
        if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
            out.println("Escape pressed");
            closeProgram();
        }
    }

    private Dimension getScreenSize() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        return new Dimension(width, height);
    }

    private void initVisualStyle() {
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
    }

    private void buildMainWindow(SelectSortTest obj) {
        obj.setTitle("Favorite Image Sort");
        obj.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        obj.setPreferredSize(defaultSize);
        // center main window
        obj.setLocation((int) (screenSize.width * 0.5 - defaultSize.width * 0.5), (int) (screenSize.height * 0.5 - defaultSize.height * 0.5));
    }

    private void startDatabaseLoader() {
        dataBaseLoader = new Thread() {
            @Override
            public void run() {
                loadDataFromDatabase();
            }
        };
        dataBaseLoader.start();
    }

    private synchronized void loadDataFromDatabase() {
        data = new SQLManager();
        sorted = data.getAllEntries();
    }

    private enum ZoomStatus {
        noneZoomed, leftZoomed, rightZoomed, bothZoomed
    }
}
