import java.util.Scanner;

/**
 *	26.10.2015 17:02:59
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
public class Reverse {

	public static void main(String[] args) {
		String query = "Bitte geben Sie Ihren vollen Namen ein: ";
		String answer = "Ihr Name in umgekehrter Schreibweise ist: ";
		Scanner c = new Scanner(System.in);
		System.out.print(query);
		if (c.hasNext()) {
			String input = c.nextLine();
			if (input.contains(" ")) {
				String[] name = input.split("\\s+");
				System.out.println(answer + name[1] + ", " + name[0]);
			}
		}
		c.close();
	}

}
