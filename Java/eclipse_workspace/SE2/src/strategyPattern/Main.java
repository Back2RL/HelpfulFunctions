package strategyPattern;

import strategyPattern.buecher.Buch;
import strategyPattern.strategies.Buch_Normal;

public class Main {

	public static void main(String[] args) {
		Buch buch = new Buch(new Buch_Normal());
		System.out.println(buch.leiheAus());
	}

}
