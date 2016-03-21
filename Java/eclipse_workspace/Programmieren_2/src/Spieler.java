
/**
 * @author Leo
 *
 */
public class Spieler {
	private String name;
	private int nummer;

	/**
	 * Standard Konstruktor
	 */
	Spieler() {
		name = "";
		nummer = 0;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return
	 */
	public int getNummer() {
		return nummer;
	}

	/**
	 * @param nummer
	 */
	public void setNummer(int nummer) {
		this.nummer = nummer;
	}

	/**
	 * Konstruktor mit Argumenten
	 * 
	 * @param neuerName
	 * @param neueNummer
	 */
	Spieler(String neuerName, int neueNummer) {
		if (neuerName.equals("") || neuerName.length() == 0 || neuerName == null || neueNummer <= 0) {
			throw new IllegalArgumentException("kein gültiger Name oder keine gültige Nummer");
		}
		name = "none";
		nummer = 0;
	}

	@Override
	public String toString() {
		return "Spieler [name=" + name + ", nummer=" + nummer + "]";
	}
}
