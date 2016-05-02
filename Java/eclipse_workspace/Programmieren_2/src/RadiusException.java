// selfmade exceptions
public class RadiusException extends ArithmeticException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int radius;

	public RadiusException(int radius, String msg) {
		super(msg);
		this.setRadius(radius);
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}
}
