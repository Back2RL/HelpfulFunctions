package u1a06;

public class Datum {

	int tag;
	int monat;
	int jahr;

	Datum(int tag, int monat, int jahr) {
		this.tag = tag;
		this.monat = monat;
		this.jahr = jahr;
	}

	public boolean istSchaltjahr() {
		if (jahr % 4 == 0) {
			if (jahr % 100 != 0 || jahr % 400 == 0) {
				return true;
			}
		}
		return false;
	}

	public String getDeutscheSchreibung() {
		return String.format("%02d.%02d.%04d", tag, monat, jahr);
	}

	public String getAmerikanischeSchreibung() {
		return String.format("%02d/%02d/%04d", monat, tag, jahr);
	}

	public void setMorgen() {
		if (tag < 28) {
			++tag;
			return;
		}
		if (istSchaltjahr() && monat == 2) {
			if (tag == 28) {
				++tag;
				return;
			}
			if (tag == 29) {
				tag = 1;
				monat = 3;
				return;
			}
		}
		++tag;
		if (monat % 2 == 0 && monat < 7) {
			int neuerTag = (tag % 31) == 0 ? 1 : tag;
			if (neuerTag < tag) {
				++monat;
				if (monat % 13 == 0) {
					monat = 1;
					++jahr;
				}
				tag = neuerTag;
			}
		} else {
			int neuerTag = (tag % 32) == 0 ? 1 : tag;
			if (neuerTag < tag) {
				++monat;
				if (monat % 13 == 0) {
					monat = 1;
					++jahr;
				}
			}
			tag = neuerTag;
		}

	}
}
