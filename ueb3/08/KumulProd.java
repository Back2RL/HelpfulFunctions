import java.util.Scanner;

/**
 *	26.10.2015 17:01:21
 *
 *	PR1, WS2015/16
 *
 *	Leonard Oertelt
 *	Matrikelnummer 1276156
 *	leonard.oertelt@stud.hs-hannover.de
 * 
 *	Julian Opitz
 * 	Matrikelnummer 1302082
 * 	julian.opitz@stud.hs-hannover.de
 *
 */
public class KumulProd {

	public static void main(String[] args) {
		Scanner c = new Scanner(System.in);

		System.out.print("Wieviele Zahlen? ");
		if (c.hasNextInt()) {
			int[] numbers = new int[c.nextInt()];
			for (int i = 0; i < numbers.length; i++) {
				System.out.print((i + 1) + "-te Zahl: ");
				if (c.hasNextInt()) {
					numbers[i] = Integer.valueOf(c.nextInt());
				}
			}
			int prod = 1;
			for (int i : numbers) {
				prod *= i;
			}
			System.out.println("Das kumulative Produkt ist: " + prod);
			c.close();
		}
	}
}
