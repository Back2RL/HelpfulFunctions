/**
 * Do 22. Okt 12:01:22 CEST 2015
 * <p>
 * WS2015/16
 * <p>
 * Leonard Oertelt
 * Matrikelnummer 1276156
 * leonard.oertelt@stud.hs-hannover.de
 * <p>
 * Fr 3. Okt 14:24:49 CEST 2015
 * -----------------------------------------
 * Programmbeschreibung
 */

public class Arrays {
    public static void main(String[] args) {
        double[][] temperaturen = new double[3][5];
        temperaturen[0][3] = 23.5;
        temperaturen[0][3] = 19.0;

        int[] a = {2, 5, 1, 6, 14, 7, 9};
        for (int i = 1; i < a.length; i++) {
            a[i] += a[i - 1];
        }
        for (int zahl : a) {
            System.out.println(zahl);
        }

        int[][] jagged = new int[3][];
        jagged[0] = new int[2];
        jagged[1] = new int[3];
        jagged[2] = new int[1];
        //jagged[0][2] = 1; //ArrayIndexOutOfBoundsException

    }
}
