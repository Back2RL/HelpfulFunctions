package SortingBySelection;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PreLoader {

    private class LoadedImage {
        private String path;

        public java.awt.Image getImage() {
            return Image;
        }

        public void setImage(java.awt.Image image) {
            Image = image;
        }

        private Image Image;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public LoadedImage(String path, java.awt.Image image) {
            this.path = path;
            Image = image;
        }
    }


    private final int MAX_PRELOADED_IMAGES;
    private List<LoadedImage> loadedImages;
    private List<String> queued;

    private Thread loader;

    public PreLoader(int maxPreloadedImages) {
        this.MAX_PRELOADED_IMAGES = maxPreloadedImages;
        loadedImages = new ArrayList<>();
        queued = new ArrayList<>();

        loader = new Thread() {
            @Override
            public void run() {
                // set priority lower
                yield();

                // loading loop
                while (true) {
                    // sleep
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        System.err.println("PreLoader: Loading Thread was interrupted.");
                        return;
                    }

                    // check whether a new image is requested to be loaded
                    String nextImage = getNextQueued();
                    if (nextImage == null) {
                        continue;
                    }

                    Image loadedImage = loadImage(nextImage);

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

    private synchronized String getNextQueued() {
        if (queued.size() > 0) {
            String path = queued.get(0);
            queued.remove(0);
            return path;
        }
        return null;
    }

    public synchronized void addLoadedImage(String path, Image image) {
        loadedImages.add(new LoadedImage(path, image));
        while (loadedImages.size() > MAX_PRELOADED_IMAGES) {
            loadedImages.remove(loadedImages.size() - 1);
        }
        System.out.println(loadedImages.size() + " images loaded");
    }

    public synchronized Image getImage(String path){
        Image loadedImage = getPreloadedImage(path);
        if(loadedImage == null){
            if(queued.contains(path)){
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

    private synchronized Image getPreloadedImage(String path){
        for(LoadedImage image:loadedImages){
            if(image.getPath().equals(path)){
                // move the accessed image to index 0
                loadedImages.remove(image);
                loadedImages.add(0,image);
                return image.getImage();
            }
        }
        return null;
    }

    public synchronized void addToQueue(String path){
        queued.add(0,path);
        loader.notify();
    }

    private synchronized Image loadImage(String path){
        Image loadedImage = null;
        // load the image from the given path
        if (path.startsWith("http://")) { // http:// URL was specified
            try {
                loadedImage = Toolkit.getDefaultToolkit().getImage(new URL(path));
            } catch (MalformedURLException e) {
                System.err.println("PreLoader: Image could not be loaded from URL, trying to load from file");
                // try loading from file instead
                try {
                    loadedImage = Toolkit.getDefaultToolkit().getImage(path);
                } catch (SecurityException se) {
                    System.err.println("PreLoader: Image could also not be loaded from file");
                }
            }
        } else {
            try {
                loadedImage = Toolkit.getDefaultToolkit().getImage(path);
            } catch (SecurityException se) {
                System.err.println("PreLoader: Image could not be loaded from file");
            }
        }
        return loadedImage;
    }
}
