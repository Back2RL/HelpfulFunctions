/**
 *	Di 10. Nov 10:34:11 CET 2015
 *
 *	PR1, WS2015/16
 *
 *	Leonard Oertelt
 *	Matrikelnummer 1276156
 *	leonard.oertelt@stud.hs-hannover.de
 *
 *	-----------------------------------------
 *	Programmbeschreibung
 */
public class FirstWord {
	public static void main(String[] args) {
		System.out.println(firstWord("Hallo Welt!"));
	}
	public static String firstWord(String s) {
		int start = 0;
		int stop = 0;
		while (stop < s.length() && s.charAt(stop) != ' ') {
			stop++;
		}
		return s.substring(start,stop);
	}
}
