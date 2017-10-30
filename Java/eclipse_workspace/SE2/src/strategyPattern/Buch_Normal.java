package strategyPattern;

public class Buch_Normal extends BuchStrategie {

	@Override
	public int leiheAus() {
		return 4 * 7;
	}
}
