
public class Bankkonto {
	private static int LASTNUMBER = 0;
	@SuppressWarnings("unused")
	private int nummer;
	@SuppressWarnings("unused")
	private int kontoStand;

	public Bankkonto() {
		kontoStand = 0;
		Bankkonto.LASTNUMBER++;
		nummer = Bankkonto.LASTNUMBER;
	}
}
