/**
 * Created by oertelt on 04.01.16.
 * "recursive telephone-book search"
 * searches for a key by dividing the search area in 2 halves every time until key is found
 */
public class BinarySearch {
    public static void main(String[] args) {

        int[] a = new int[]{1,4,4,4,7,8,11,15,21,22,23,24,25,25,26,30};
        int key = 20;
        int index = binarySearch(a,key);
        System.out.println("index of key "+key+" is: "+index);
    }

    // searches the index of a given key in an array
    // returns the index or -1 if not existent
    public static int binarySearch(int[] a, int key) {
        return binarySearchRecursive(a,key,0,a.length-1);
    }

    // searches the index of a given key in an array only in a given area, defined by from/to
    // returns one index (if multiple exist) or -1 if not existent
    public static int binarySearchRecursive(int[] a, int key, int from, int to) {
        if (from >= to) {
            // empty index area
            return -1;
        }

        // get middle of area
        int middle = (from + to) / 2;

        if (a[middle] == key) {
            // key index found
            return middle;
        }

        if (a[middle] > key) {
            // continue search in left half of area
            return binarySearchRecursive(a, key, from, middle);
        }
        // continue search in left half of area
        return binarySearchRecursive(a, key, middle + 1, to);
    }
}
