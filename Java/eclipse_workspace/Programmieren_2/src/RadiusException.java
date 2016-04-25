// selfmade exceptions
public class RadiusException extends ArithmeticException {
	private int radius;

	public RadiusException(int radius, String msg) {
		super(msg);
		this.radius = radius;
	}
}
