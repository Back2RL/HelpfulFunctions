package SortingBySelection;

import java.io.File;
import java.util.Objects;

public class RatedImage implements Comparable<RatedImage> {
    public String getPath() {
        return path;
    }

    private String path;
    private int rating;
    private String md5Hash;

    public RatedImage(String path) {
        this.rating = 0;
        this.path = path;
    }

    public RatedImage(String md5, String path) {
        this.md5Hash = md5;
        this.path = path;
    }

    public int getRating() {
        return rating;
    }

    // makes sure that the rating is higher then the other one
    public void increaseRating(RatedImage other) {
        if (rating < other.getRating()) {
            rating = other.getRating() + 1;
//                other.rating++;
        } else {
            rating++;
            other.rating++;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(md5Hash,rating,path);
    }

    public final void calculateMD5() {
        setMd5Hash(MD5.fromFile(new File(path)));
    }

    public String getMd5Hash() {
        return md5Hash;
    }

    private synchronized void setMd5Hash(String md5Hash) {
        this.md5Hash = md5Hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RatedImage) {
            RatedImage image = (RatedImage) obj;
            return rating == image.rating && md5Hash.equals(image.md5Hash);
        }
        return false;
    }

    /**
     * return 0 if rating is the same
     * returns -1 if the rating of o is lower
     * returns 1 if the rating of o is higher
     */
    @Override
    public int compareTo(RatedImage o) {
        if (this == o) return 0;
        if (o.rating == rating) return 0;
        if (o.rating < rating) return -1;
        return 1;
    }
}

