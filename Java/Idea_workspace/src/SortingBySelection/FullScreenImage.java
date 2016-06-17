package SortingBySelection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.net.URL;


public class FullScreenImage extends JFrame {

    private Image screenImage; // downloaded image
    private Image scaledImage; // downloaded image
    private int w, h; // Display height and width
    private volatile int scale;


    public FullScreenImage(String source) {
        // Exiting program on window close
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.out.println("Exiting Program");
                FullScreenImage.this.dispose();
            }
        });

        // Exiting program on mouse click
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (screenImage != null) {
                        if (scale == 100) {
                            calculateScaleToFillScreen();
                        } else {
                            scale = 100;
                        }
                        scaleImage();
                    }
                }
            }
        });

        // remove window frame
        this.setUndecorated(true);

        // window should be visible
        this.setVisible(true);

        // switching to fullscreen mode
        GraphicsEnvironment.getLocalGraphicsEnvironment().
                getDefaultScreenDevice().setFullScreenWindow(this);

        // getting display resolution: width and height
        w = this.getWidth();
        h = this.getHeight();

        System.out.println("Display resolution: " + String.valueOf(w) + "x" + String.valueOf(h));

        loadImage(source);

        FullScreenImage.this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                System.out.print("Button pressed: ");
                if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
                    System.out.println("Escape");
                    // close window when escape is pressed
                    EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            FullScreenImage.this.removeAll();
                            FullScreenImage.this.dispose();
                        }
                    });
                }else if(e.getKeyChar() == KeyEvent.VK_ENTER || e.getKeyChar() == KeyEvent.VK_SPACE){
                    if (screenImage != null) {
                        if (scale == 100) {
                            calculateScaleToFillScreen();
                        } else {
                            scale = 100;
                        }
                        scaleImage();
                    }
                }

            }
        });
    }

    // Program entry
    public static void main(String[] args) throws Exception {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (args.length < 1) // by default program will load AnyExample logo
                    new FullScreenImage("http://www.anyexample.com/i/logo.gif");
                else
                    new FullScreenImage(args[0]); // or first command-line argument
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        if (scaledImage != null) // if screenImage is not null (image loaded and ready)
            g.drawImage(scaledImage, // draw it
                    w / 2 - scaledImage.getWidth(this) / 2, // at the center
                    h / 2 - scaledImage.getHeight(this) / 2, // of screen
                    this);
        // to draw image at the center of screen
        // we calculate X position as a half of screen width minus half of image width
        // Y position as a half of screen height minus half of image height
    }

    private void scaleImage() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                FullScreenImage.this.getGraphics().clearRect(0, 0, w, h);
                scaledImage = screenImage.getScaledInstance((int) (screenImage.getWidth(FullScreenImage.this) * (double) scale / 100),
                        (int) (screenImage.getHeight(FullScreenImage.this) * (double) scale / 100), Image.SCALE_SMOOTH);
                FullScreenImage.this.paint(FullScreenImage.this.getGraphics());
            }
        });
    }

    private void loadImage(String sourcePath) {
        Thread imageLoader = new Thread() {
            @Override
            public void run() {
                // loading image
                if (sourcePath.startsWith("http://")) { // http:// URL was specified
                    try {
                        screenImage = Toolkit.getDefaultToolkit().getImage(new URL(sourcePath));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                } else {
                    screenImage = Toolkit.getDefaultToolkit().getImage(sourcePath); // otherwise - file
                }
                calculateScaleToFillScreen();
                scaleImage();
            }
        };
        imageLoader.start();
    }

    private void calculateScaleToFillScreen() {
        if (screenImage == null) {
            System.out.println("Image is null");
            return;
        }
        double imageW = screenImage.getWidth(FullScreenImage.this);
        double imageH = screenImage.getHeight(FullScreenImage.this);
        double screenRatio = (double) w / h;
        double imageRatio = imageW / imageH;
        if (imageRatio > screenRatio) {
            // image is more wide-screen then screen
            scale = (int) ((double) w / imageW * 100);
        } else {
            scale = (int) ((double) h / imageH * 100);
        }


    }
}


