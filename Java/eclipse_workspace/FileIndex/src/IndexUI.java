import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class IndexUI extends JFrame {
    public IndexUI() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Indexer");
        setPreferredSize(new Dimension(400, 300));

        buildPanels();
        showImage();

        pack();

    }

    private JPanel jpnlDatabase;
    private JFileChooser jfcDatabase;

    private void buildPanels() {

        jpnlDatabase = new JPanel();
        jpnlDatabase.setLayout(new GridBagLayout());
        jpnlDatabase.setBorder(BorderFactory.createTitledBorder("Database"));

        FileFilter filter = new FileNameExtensionFilter("Bilder",
                "gif", "png", "jpg");
        jfcDatabase = new
                JFileChooser("c:/programmierung/beispieldateien");
        jfcDatabase.addChoosableFileFilter(filter);
//        getContentPane().add(jfcDatabase);

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().add(jpnlDatabase);
    }

    private void showImage() {
        class ImagePanel extends JPanel {

            private BufferedImage image;

            public ImagePanel() {
                try {
                    image = ImageIO.read(new File("/home/leo/Bilder/running_tiger_hunting_anime_character_ao_no_exorcist.jpeg"));
                } catch (IOException ex) {
                    // handle exception...
                    System.out.println("failed to open image");
                }
                setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                float imageAspectRatio = image.getWidth() / image.getHeight();
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

        getContentPane().add(image);
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
