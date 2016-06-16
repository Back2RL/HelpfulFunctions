package SortingBySelection;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImageList extends JFrame {

    private JPanel content;


    public ImageList(ArrayList<String> files) {

        setTitle("Images");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        content = new JPanel();
        content.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JScrollPane scroll = new JScrollPane(content);
        getContentPane().add(scroll);
        scroll.createHorizontalScrollBar();
        new Thread() {
            @Override
            public void run() {
                super.run();
                for (String path : files) {
                    try {
                        class ImagePanel extends JPanel {

                            private BufferedImage image;

                            public ImagePanel() {
                                try {
                                    image = ImageIO.read(new File(path));
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
                                super.paintComponent(g);
//
                                ImageIcon icon = new ImageIcon(path);
//
                                int width = icon.getIconWidth();
                                int height = icon.getIconHeight();

                                float imageAspectRatio = (float) width / height;


                                int y = getHeight();
                                int x = (int) ((y * imageAspectRatio));
                                g.drawImage(image, 0, 0, x, y, null);
                            }

                        }
                        EventQueue.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                ImagePanel image = new ImagePanel();
                                content.add(image, FlowLayout.LEFT);
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
setPreferredSize(new Dimension(640,480));
        pack();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> files = new ArrayList<String>();
                files.add("X:\\Christmas\\__Merry_Christmas___by_love1008.jpg");
                files.add("X:\\Christmas\\2100917268_7f1873c831_o.jpg");
                files.add("X:\\Christmas\\80_01_01.jpg");
                ImageList i = new ImageList(files);
                i.setVisible(true);
            }
        });
    }

}
