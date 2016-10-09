/**
 * Created by oertelt on 04.01.16.
 */
public class Fibonacci {

    public static void main(String[] args) {

        for (int i = 0; i <= 47; i++) {
            long start = System.currentTimeMillis();
            int fibVal = fib(i);
            long stop = System.currentTimeMillis();
            System.out.println(i + ";\t" + (stop - start) / 1000.0 + "s;\t" + fibVal);
        }


    }

    // naiv implementation SLOW!
    /*
    public static int fib(int i) {
        if (i <= 1) return 1;
        return fib(i - 1) + fib(i - 2);
    }
*/
    // better solution
    public static int fib(int i) {
        if (i <= 1) return 1;
        int a = 1, b = 1;
        for (int k = 2; k <= i; k++) {
            int c = a + b;
            a = b;
            b = c;
        }
        return b;
    }
}
