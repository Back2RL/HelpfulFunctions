package u1a06;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Datum datum = new Datum(31, 12, 2016);
		System.out.println(datum.getDeutscheSchreibung());
		System.out.println(datum.getAmerikanischeSchreibung());
		datum.setMorgen();
		System.out.println(datum.getDeutscheSchreibung());
		System.out.println(datum.getAmerikanischeSchreibung());

	}

}
