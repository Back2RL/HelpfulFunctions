import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class IndexUI extends JFrame {
    public IndexUI() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Indexer");
        setPreferredSize(new Dimension(400, 300));

        buildPanels();
        baueFotoGUI();
        //showImage();




        pack();

    }

    private JPanel jpnlDatabase;
    private JFileChooser jfcDatabase;
    private JPanel jpnlFoto1;
    private JPanel jpnlFoto2;
    private JTextField jtfFoto;
    private JButton jbtnFotoAuswahl;

    private void buildPanels() {

        jpnlDatabase = new JPanel();
        jpnlDatabase.setLayout(new GridBagLayout());
        jpnlDatabase.setBorder(BorderFactory.createTitledBorder("Database"));

        jpnlFoto1 = new JPanel();
        jpnlFoto1.setLayout(new BorderLayout());
        jpnlFoto1.setBorder(BorderFactory.createTitledBorder("Foto"));

        jpnlFoto2 = new JPanel();
        jpnlFoto2.setLayout(new BorderLayout());
        jpnlFoto2.setBorder(BorderFactory.createTitledBorder("Foto"));

        FileFilter filter = new FileNameExtensionFilter("Bilder",
                "gif", "png", "jpg");
        jfcDatabase = new
                JFileChooser("c:/programmierung/beispieldateien");
        jfcDatabase.addChoosableFileFilter(filter);
//        getContentPane().add(jfcDatabase);

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().add(jpnlDatabase);
        getContentPane().add(jpnlFoto1);
        getContentPane().add(jpnlFoto2);
    }

    /**
     * Baut Dateiauswahl-Felder für das Foto und Eventhandler.
     */
    private void baueFotoGUI() {
        jtfFoto = new JTextField(20);
        jpnlFoto1.add(jtfFoto, BorderLayout.CENTER);

        jbtnFotoAuswahl = new JButton("Auswählen");
        jpnlFoto1.add(jbtnFotoAuswahl, BorderLayout.EAST);

        jtfFoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {

                showImage();
            }
        });

        jbtnFotoAuswahl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "JPG & GIF Images", "jpg", "gif");
                chooser.setFileFilter(filter);
                chooser.setMultiSelectionEnabled(false);
                chooser.changeToParentDirectory();
                if (chooser.showOpenDialog(IndexUI.this)
                        == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    String path = file.getAbsolutePath();
                    jtfFoto.setText(path);




                    showImage();
                }
            }
        });

    }


    private void showImage() {
        class ImagePanel extends JPanel {

            private BufferedImage image;

            public ImagePanel() {
                try {
                    image = ImageIO.read(new File(jtfFoto.getText()));
                } catch (IOException ex) {
                    // handle exception...
                    JOptionPane.showMessageDialog(this,
                            "Fehler beim Laden des Bilds: " + ex.getMessage(),
                            "Fehler",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                String imgFilename = jtfFoto.getText();
                ImageIcon icon = new ImageIcon(imgFilename);
                int width = icon.getIconWidth();
                int height = icon.getIconHeight();

                float imageAspectRatio = (float)width / height;
                //float imageAspectRatio = 16.0f/9.0f;
                float jframeAspectRatio = getWidth() / getHeight();
                int x = 0;
                int y = 0;
                if (imageAspectRatio < jframeAspectRatio) {
                    y = getHeight();
                    x = (int) ((y * imageAspectRatio));
                } else {
                    x = getWidth();
                    y = (int) ((x / imageAspectRatio));
                }
                g.drawImage(image, getWidth() / 2 - x / 2, getHeight() / 2 - y / 2, x, y, null);
            }

        }
        ImagePanel image = new ImagePanel();


        // Erzeugung eines neuen JDialogs mit
        // dem Titel "Beispiel JDialog"
        JDialog meinJDialog = new JDialog();
        // Titel wird gesetzt
        meinJDialog.setTitle("Mein JDialog Beispiel");
        // Höhe und Breite des Fensters werden
        // auf 200 Pixel gesetzt
        meinJDialog.setSize(200,200);


        // Hinzufügen einer Komponente,
        // in diesem Fall ein JLabel
//        JLabel imageTest = new JLabel("Beispiel JLabel");
//        meinJDialog.add(imageTest);
        // Wir lassen unseren JDialog anzeigen

        meinJDialog.add(image);
        meinJDialog.setResizable(true);
        meinJDialog.setVisible(true);
        //getContentPane().add(image);
    }


    /**
     * Hilfsroutine beim Hinzufügen einer Komponente zu einem
     * Container im GridBagLayout.
     * Die Parameter sind Constraints beim Hinzufügen.
     *
     * @param x       x-Position
     * @param y       y-Position
     * @param width   Breite in Zellen
     * @param height  Höhe in Zellen
     * @param weightx Gewicht
     * @param weighty Gewicht
     * @param cont    Container
     * @param comp    Hinzuzufügende Komponente
     * @param insets  Abstände rund um die Komponente
     */
    private static void addComponent(final int x, final int y, final int width, final int height, final double weightx, final double weighty, Container cont, Component comp, Insets insets) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        gbc.insets = insets;
        cont.add(comp, gbc);
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                IndexUI f = new IndexUI();
                f.setVisible(true);
            }
        });
    }

}
