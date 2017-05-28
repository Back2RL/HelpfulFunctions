package Jacobi_Gauss_Iteration;

import java.util.Arrays;

/**
 * Created by Leo2400 on 17.06.2016.
 */
public class JacobiTest {

    public static void main(String[] args) {

        double[][] a = {{5, 4, 2}, {2, 6, 2}, {3, 4, 10}};

        double[] b = {10, 2, 22};

        double[] result = LGSIteration.gaussSeidel(a, b, 1000);

        System.out.println(Arrays.toString(result));
        result = LGSIteration.jacobi(a, b, 1000);
        System.out.println(Arrays.toString(result));

    }


}
