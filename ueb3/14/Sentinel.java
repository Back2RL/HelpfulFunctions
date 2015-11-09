import java.util.Scanner;

/**
 *	26.10.2015 16:58:29
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
public class Sentinel {

	public static void main(String[] args) {
		final String query = "Geben Sie einen Namen ein (oder eine leere Zeile zum Beenden): ";
		final String welcome = "Willkommen an alle:";
		String names = new String();
		Scanner c = new Scanner(System.in);
		boolean running = true;
		while (running) {
			System.out.print(query);
			String in = c.nextLine();
			if (!in.isEmpty()) {
				names = names.concat(" " + in);
			} else {
				running = false;
			}
		}
		c.close();
		System.out.println(welcome + names);
	}
}
