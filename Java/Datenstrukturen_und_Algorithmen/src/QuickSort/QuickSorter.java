package QuickSort;

import java.util.Arrays;
import java.util.Random;

public class QuickSorter {
    public static void main(String[] args) {

        int[] liste = new int[100];
        for (int i = 0; i < 100; ++i) {
            liste[i] = new Random().nextInt(100);
        }

        System.out.println(Arrays.toString(liste));

        quicksort(liste, 0, 99);
        System.out.println(Arrays.toString(liste));


    }


    public static void quicksort(final int[] L, final int links, final int rechts) {
        if (links < rechts) {
            int pivotPos = bestimmePivotPos(L, links, rechts);
            pivotPos = partitioniere(L, links, rechts, pivotPos);

            quicksort(L, links, pivotPos - 1);
            quicksort(L, pivotPos + 1, rechts);
        }
    }

    public static int bestimmePivotPos(final int[] L, final int links, final int rechts) {
        return (links + rechts) / 2;
    }


    /**
     * Teilt ein Array in zwei Teilmengen auf bezgl. eines Pivot-Elements:
     * alle Elemente vor dem Pivot-Element sind kleiner als das Pivot-Element
     * alle Elemente nach dem Pivot-Element sind groeßer;er als das Pivot-Element
     *
     * @param L      das Array
     * @param links  Index des ersten Elements, welches den Anfang des zu partitionierenden Bereiches markiert (inklusive)
     * @param rechts Index des letzteni Elements, welches das Ende des zu partitionierenden Bereiches markiert (inklusive)
     * @param pivot  Index des Privot-Elements
     * @return Index des Pivot-Elements nach dem Partitionieren
     */
    public static int partitioniere(final int[] L, final int links, final int rechts, final int pivot) {
        if (L == null) {
            throw new IllegalArgumentException("L must not be null");
        }
        if (L.length == 0) {
            System.err.println("L is empty");
            return -1;
        }
        if (links < 0 || links >= L.length
                || rechts < 0 || rechts >= L.length
                || pivot < 0 || pivot >= L.length) {
            throw new IllegalArgumentException("Illegal parameters:\r " +
                    "length(L) = " + L.length + "\r" +
                    "links = " + links + "\r" +
                    "rechts = " + rechts + "\r" +
                    "pivot = " + pivot);
        }


        int i = links;
        int j = rechts - 1;

        int k = L[pivot];
        // Pivot-Element mit dem Element ganz rechts vertauschen
        vertausche(L, pivot, rechts);

        while (i < j) {
            // suche links ein Element groeßer als k
            while (L[i] <= k && i < rechts) {
                ++i;
            }
            // suche rechts ein Element kleiner als k
            while (L[j] >= k && j > links) {
                --j;
            }

            if (i < j) {
                vertausche(L, i, j);
            }
        }
        if (L[i] > k) {
            vertausche(L, i, rechts);
        }
        return i;
    }

    /**
     * Vertauscht die Array-Elemente der beiden Indize
     *
     * @param L      das Array
     * @param indexA Index des einen Elements
     * @param indexB Index des anderen Elements
     */
    public static void vertausche(final int[] L, final int indexA, final int indexB) {
        final int temp = L[indexA];
        L[indexA] = L[indexB];
        L[indexB] = temp;
    }


}
