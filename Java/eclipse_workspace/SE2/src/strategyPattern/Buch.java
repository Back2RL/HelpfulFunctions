package strategyPattern;

public class Buch {
	private BuchStrategie strategie;

	public Buch(BuchStrategie strategie) {
		super();
		this.strategie = strategie;
	}

	public int leiheAus(/* Benutzer b */) {
		return strategie.leiheAus();
	}
}
