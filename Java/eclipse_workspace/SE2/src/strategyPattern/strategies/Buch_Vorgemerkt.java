package strategyPattern.strategies;

public class Buch_Vorgemerkt extends BuchStrategie {

	@Override
	public int leiheAus() {
		return 2 * 7;
	}
}
