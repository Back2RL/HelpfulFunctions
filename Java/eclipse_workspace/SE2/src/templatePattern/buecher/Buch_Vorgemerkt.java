package templatePattern.buecher;

import templatePattern.templates.BuchTemplate;

public class Buch_Vorgemerkt extends BuchTemplate {

	@Override
	public int leiheAus() {
		return 2 * 7;
	}
}
