package SortingBySelection;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class ImageList extends JFrame {

    private JPanel content;
    private PreLoader preLoader;


    public ImageList(PreLoader preLoader, ArrayList<String> files) {
        this.preLoader = preLoader;
        if (preLoader == null) {
            this.preLoader = new PreLoader(100);
        }

        setTitle("Images");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    dispose();
                }
            }
        });

        content = new JPanel();
        content.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JScrollPane scroll = new JScrollPane(content);
        getContentPane().add(scroll);
        scroll.createHorizontalScrollBar();
        Thread loader = new Thread() {
            @Override
            public void run() {
                super.run();
                for (String path : files) {
                    try {
                        class ImagePanel extends JPanel {

                            private Image image;

                            public ImagePanel() {

                                image = preLoader.getImage(path);
                                if (image == null) {
                                    // handle exception...
                                    JOptionPane.showMessageDialog(this,
                                            "Fehler beim Laden des Bilds: ",
                                            "Fehler",
                                            JOptionPane.ERROR_MESSAGE);
                                    return;
                                }

                                this.setPreferredSize(new Dimension(image.getWidth(this), image.getHeight(this)));
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
        };
        loader.start();

        setPreferredSize(new Dimension(640, 480)

        );

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
                ImageList i = new ImageList(null, files);
                i.setVisible(true);
            }
        });
    }

}
