import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *	26.10.2015 17:01:16
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
public class Geheim {

	private static final String nameQery = "Benutzername bitte: ";
	private static final String passQuery = "Geheimwort bitte: ";
	private static final String partCorr = "Benutzername richtig, Geheimwort falsch";
	private static final String corr = "Richtig";
	private static final String wrong = "falsch";
	private static final Map<String, String> users = new HashMap<String, String>();

	public static void main(String[] args) {
		final Scanner c = new Scanner(System.in);
		users.put("schumacher", "benzin");
		users.put("obama", "change");
		users.put("dent", "42");
		String name = new String();
		String pass = new String();
		System.out.print(nameQery);
		name = c.nextLine();
		System.out.print(passQuery);
		pass = c.nextLine();

		if (users.containsKey(name)) {
			if (users.get(name).equals(pass)) {
				System.out.println(corr);
			} else {
				System.out.println(partCorr);
			}
		} else {
			System.out.println(wrong);
		}
		c.close();
	}

}
