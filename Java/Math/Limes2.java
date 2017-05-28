package Math;

/**
 * Created by leonard on 11.11.16.
 */
public class Limes2 {
    public static void main(String args[]){
        int MAX = 100000;
        double sum = 0;
        for(int n = 1; n < MAX; ++n){
            sum += 1.0/(n*n-0.25);
            System.out.println(0.25*sum);
        }
        System.out.println("my Solution = "+(1.0/2.0));

    }
}
