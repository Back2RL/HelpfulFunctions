package Leseaufgabe_05;
import java.util.Random;

public class MathLibrary {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Random rand = new Random();

		int iRand1 = rand.nextInt(11);
		int iRand2 = rand.nextInt(11);
		System.out.println(dotProduct(iRand1, iRand1, iRand1, iRand2, iRand2, iRand2));
		System.out.println(rand.nextInt(101));

	}

	// returns the DotProduct of two 3-dim Vectors
	public static double dotProduct(double x1, double y1, double z1, double x2, double y2, double z2) {
		return x1 * x2 + y1 * y2 + z1 * z2;
	}

}
