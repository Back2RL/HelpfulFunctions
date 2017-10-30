package templatePattern.buecher;

import templatePattern.templates.BuchTemplate;

public class Buch_Normal extends BuchTemplate {

	@Override
	public int leiheAus() {
		return 4 * 7;
	}
}
