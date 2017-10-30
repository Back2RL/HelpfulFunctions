package templatePattern;

public class Buch_Vorgemerkt extends BuchTemplate {

	@Override
	public int leiheAus() {
		return 2 * 7;
	}
}
