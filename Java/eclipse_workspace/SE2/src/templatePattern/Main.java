package templatePattern;

import templatePattern.Buch_Normal;

public class Main {

	public static void main(String[] args) {
		BuchTemplate buch = new Buch_Normal();
		System.out.println(buch.leiheAus());
	}
}
