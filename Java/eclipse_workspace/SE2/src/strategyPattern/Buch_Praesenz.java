package strategyPattern;

public class Buch_Praesenz extends BuchStrategie {

	@Override
	public int leiheAus() {
		return 1;
	}
}
