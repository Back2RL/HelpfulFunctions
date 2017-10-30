package templatePattern;

import templatePattern.buecher.Buch_Normal;
import templatePattern.templates.BuchTemplate;

public class Main {

	public static void main(String[] args) {
		BuchTemplate buch = new Buch_Normal();
		System.out.println(buch.leiheAus());
	}
}
