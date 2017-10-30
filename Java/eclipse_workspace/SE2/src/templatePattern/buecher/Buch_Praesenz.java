package templatePattern.buecher;

import templatePattern.templates.BuchTemplate;

public class Buch_Praesenz extends BuchTemplate {

	@Override
	public int leiheAus() {
		return 1;
	}
}
