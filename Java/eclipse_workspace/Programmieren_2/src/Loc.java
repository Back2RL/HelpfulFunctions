import java.awt.Graphics;

public class Loc {
	int x;
	int y;

	{ // Object initializer
	}

	/**
	 * Constructor
	 * 
	 * @param x
	 *            Initial X value
	 * @param y
	 *            Initial Y value
	 */
	public Loc(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Constructor
	 */
	public Loc() {
	}

	/**
	 * Constructor that reads the values from a string
	 * 
	 * @param s
	 *            String that contains the coordinates in form of "x,y" of type
	 *            int
	 */
	public Loc(String s) {
		String[] tokens = s.split(",");
		if (tokens.length != 2) {
			throw new IllegalArgumentException("String contains no valid location");
		}
		try {
			this.x = Integer.parseInt(tokens[0]);
			this.y = Integer.parseInt(tokens[1]);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Draws the location (dot) and its coordinates
	 * 
	 * @param g
	 *            Graphics object to draw the location
	 */
	public void draw(Graphics g) {
		g.fillOval(x, y, 3, 3);
		g.drawString("(" + x + "," + y + ")", x, y);
	}

	/**
	 * calculates the distance between two locations
	 * 
	 * @param loc
	 *            The second location
	 * @return Returns the distance (double)
	 */
	public double distance(Loc loc) {
		int dx = loc.x - x;
		int dy = loc.y - y;
		return Math.sqrt(dx * dx + dy * dy);
	}

	/**
	 * calculates the distance to (0,0)
	 * 
	 * @return Returns the distance (double)
	 */
	public double distanceFromOrigin() {
		return distance(new Loc());
	}

	/**
	 * Overwrite this objects location
	 * 
	 * @param newx
	 *            New X location
	 * @param newy
	 *            New Y location
	 */
	public void setLocaion(int newx, int newy) {
		this.x = newx;
		this.y = newy;
	}

	/**
	 * Translates this location by dx/dy
	 * 
	 * @param dx
	 *            The X-Offset
	 * @param dy
	 *            The Y-Offset
	 */
	public void translate(int dx, int dy) {
		x += dx;
		y += dy;
	}
}
