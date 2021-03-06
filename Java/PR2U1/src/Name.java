
public class Name {

	String vorname = "Vorname";
	String nachname = "Nachname";

	public Name(String vorname, String nachname) {
		if (vorname == null || vorname.equals("") || nachname == null || nachname.equals("")) {
			throw new IllegalArgumentException("Ungültiger Name");
		}

		this.vorname = vorname;
		this.nachname = nachname;
	}

	public String getVollerName() {
		return vorname + " " + nachname;
	}

	public String getVollerNameRAK() {
		return nachname + ", " + vorname;
	}
}
