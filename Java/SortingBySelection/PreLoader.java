package SortingBySelection;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PreLoader {

    private static PreLoader mPreloadInstance;
    private final int MAX_PRELOAD_IMAGES = 100;
    private List<LoadedImage> loadedImages;
    private List<String> queued;
    private Thread loader;

    private PreLoader() {
        loadedImages = new ArrayList<>();
        queued = new ArrayList<>();

        loader = new Thread() {
            @Override
            public void run() {
                // set priority lower
                Thread.currentThread().setPriority(MIN_PRIORITY);

                // loading loop
                while (true) {
                    // sleep
                    try {
                        sleep(1000);
                        System.out.println(loadedImages.size() + " Images Loaded...");
                    } catch (InterruptedException e) {
                        System.err.println("PreLoader: Loading Thread was interrupted.");
                        return;
                    }

                    // check whether a new image is requested to be loaded
                    String nextImage = getNextQueued();
                    if (nextImage == null) {
                        continue;
                    }

                    BufferedImage loadedImage = loadImage(nextImage);

                    // check if loading was successful
                    if (loadedImage == null) {
                        continue;
                    }

                    // add loaded image to loaded list
                    addLoadedImage(nextImage, loadedImage);
                }
            }
        };

        loader.start();
    }

    public static PreLoader getPreloadInstance() {
        if (mPreloadInstance == null) {
            mPreloadInstance = new PreLoader();
        }
        return mPreloadInstance;
    }

    private synchronized String getNextQueued() {
        if (queued.size() > 0) {
            String path = queued.get(0);
            queued.remove(0);
            return path;
        }
        return null;
    }

    private synchronized void addLoadedImage(String path, BufferedImage image) {
        loadedImages.add(new LoadedImage(path, image));
        while (loadedImages.size() > MAX_PRELOAD_IMAGES) {
            loadedImages.remove(loadedImages.size() - 1);
        }
        System.out.println(loadedImages.size() + " images loaded");
    }

    public synchronized BufferedImage getImage(String path) {
        BufferedImage loadedImage = getPreloadedImage(path);
        if (loadedImage == null) {
            if (queued.contains(path)) {
                queued.remove(path);
            }
            loadedImage = loadImage(path);
            // check if loading was successful
            if (loadedImage == null) {
                return null;
            }
            // add loaded image to loaded list
            addLoadedImage(path, loadedImage);
        }
        return loadedImage;
    }

    private synchronized BufferedImage getPreloadedImage(String path) {
        for (LoadedImage image : loadedImages) {
            if (image.getPath().equals(path)) {
                // move the accessed image to index 0
                loadedImages.remove(image);
                loadedImages.add(0, image);
                return image.getImage();
            }
        }
        return null;
    }

    public synchronized void addToQueue(String path) {
        queued.add(0, path);
        loader.notify();
    }

    private synchronized BufferedImage loadImage(String path) {
        BufferedImage loadedImage = null;
        // load the image from the given path
        if (path.startsWith("http://")) {
            try {
                loadedImage = toBufferedImage(Toolkit.getDefaultToolkit().getImage(new URL(path)));
            } catch (MalformedURLException e) {
                System.err.println("PreLoader: Image could not be loaded from URL, trying to load from file");
                try {
                    loadedImage = toBufferedImage(Toolkit.getDefaultToolkit().getImage(path));
                } catch (SecurityException se) {
                    System.err.println("PreLoader: Image could also not be loaded from file");
                }
            }
        } else {
            try {
                loadedImage = toBufferedImage(Toolkit.getDefaultToolkit().getImage(path));
            } catch (SecurityException se) {
                System.err.println("PreLoader: Image could not be loaded from file");
            }
        }
        return loadedImage;
    }

    public static BufferedImage toBufferedImage(Image img) {
        if (img == null) return null;
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        int x, y;
        // Create a buffered image with transparency
        do {
            x = img.getWidth(null);
            y = img.getHeight(null);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (x == -1 || y == -1);
        BufferedImage bimage = new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }
}
