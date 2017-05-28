import javax.xml.transform.sax.SAXSource;

/**
 * Created by leonard on 17.11.16.
 */
public class DeltaEpsilon {

    public static void main(String[] args) {
        double x0 = 1;
        double x = 2;

        double eps = Math.abs(Math.sqrt(x0) - Math.sqrt(x));
        double deltaCalc = Math.sqrt(eps);
        double delta = Math.abs(x0 - x);

        System.out.println("Epsilon = " + eps);
        System.out.println("Delta = " + delta + "  < CalcDelta = " + deltaCalc);

    }
}
