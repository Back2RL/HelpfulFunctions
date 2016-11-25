package Math;

/**
 * Created by leonard on 11.11.16.
 */
public class Limes {
    public static void main(String args[]){
        int MAX = 250;
        double sum = 0;
        for(int n = 2; n < MAX; ++n){
            sum += Math.pow(2,3*n) / Math.pow(3,2*n);
            System.out.println(sum);
        }
        System.out.println("my Solution = "+(64.0/9));

    }
}
