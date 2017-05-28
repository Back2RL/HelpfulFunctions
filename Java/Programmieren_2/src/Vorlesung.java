
public class Vorlesung {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private String titel;
	private int sws;

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public int getSws() {
		return sws;
	}

	public void setSws(int sws) {
		this.sws = sws;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Vorlesung))
			return false;
		Vorlesung other = (Vorlesung) obj;
		return titel.equals(other.getTitel()) && sws == other.getSws();
	}

}
