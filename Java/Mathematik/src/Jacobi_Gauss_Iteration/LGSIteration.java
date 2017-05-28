package Jacobi_Gauss_Iteration;

import java.util.Arrays;

public class LGSIteration {

    private static final double SMALL_NUMBER = 1E-8;

    // "Einzelschrittverfahren"
    public static double[] gaussSeidel(double[][] a, double[] b, final int step) {

        // invariant 1: check for null, negative stepsize, difference in array length
        if (a == null || b == null || step < 0 || a.length != b.length) {
            return null;
        }

        // invariant 2: check for a true quadratic mnatrix
        for (double[] line : a) {
            if (line.length != a.length) {
                return null;
            }
        }


        double[] resultX = new double[b.length];

        Arrays.fill(resultX, 0.0);

        for (int i = 0; i < step; ++i) {
            // go over all lines and calculate the new x
            for (int j = 0; j < a.length; j++) {

                // abort if the x is almost zero
                if (a[j][j] * a[j][j] < SMALL_NUMBER) return null;

                // set value to right side's value
                double newX = b[j];

                // go over the row and subtract all the values
                for (int k = 0; k < a.length; k++) {
                    // skip the current x
                    if (k == j) continue;
                    newX -= a[j][k] * resultX[k];
                }
                // divide the value by the current x
                resultX[j] = newX / a[j][j];
            }
        }
        return resultX;
    }

    // "Gesamtschrittverfahren"
    public static double[] jacobi(double[][] a, double[] b, final int step) {

        // invariant 1: check for null, negative stepsize, difference in array length
        if (a == null || b == null || step < 0 || a.length != b.length) {
            return null;
        }

        // invariant 2: check for a true quadratic mnatrix
        for (double[] line : a) {
            if (line.length != a.length) {
                return null;
            }
        }


        double[] resultX = new double[b.length];

        Arrays.fill(resultX, 0.0);

        double[] newX = new double[b.length];

        for (int i = 0; i < step; ++i) {
            // go over all lines and calculate the new x
            for (int j = 0; j < a.length; j++) {
                // abort if the x is almost zero
                if (a[j][j] * a[j][j] < SMALL_NUMBER) return null;

                // set value to right side's value
                newX[j] = b[j];

                // go over the row and subtract all the values
                for (int k = 0; k < a.length; k++) {
                    // skip the current x
                    if (k == j) continue;
                    newX[j] -= a[j][k] * resultX[k];
                }
                // divide the value by the current x
                newX[j] /= a[j][j];
            }
            // after each pass write the new x values into the result matrix
            for (int j = 0; j < resultX.length; j++) {
                resultX[j] = newX[j];
            }
        }
        return resultX;
    }


}
