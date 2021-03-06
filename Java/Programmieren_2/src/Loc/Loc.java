package Loc;
import java.awt.Graphics;

/**
 * Invariante: alle Objekte dieser Klasse haben Koordinaten >= 0
 * 
 * @author Leo
 *
 */
public class Loc {
	int x;
	int y;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	{ // Object initializer
	}

	/**
	 * Constructor
	 * 
	 * @param x
	 *            Initial X value
	 * @param y
	 *            Initial Y value
	 * @exception IllegalArgumentException
	 *                bei ungültigen Parameterwerten
	 */
	public Loc(int x, int y) {
		setLocation(x, y);
	}

	/**
	 * Constructor
	 */
	public Loc() {
		this(0, 0); // ruft einen anderen Konstruktor auf (nicht unbedingt
					// notwendig)
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
	 * @exception IllegalArgumentException
	 *                bei ungültigen Parameterwerten
	 */
	public void setLocation(int newx, int newy) {
		if (!validArguments(x, y)) {
			throw new IllegalArgumentException("Die Invaiante ist verletzt");
		}
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
	 * @exception IllegalArgumentException
	 *                bei ungültigen Parameterwerten
	 */
	public void translate(int dx, int dy) {
		setLocation(x + dx, y + dy);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	/**
	 * prüft die Invariante
	 * 
	 * @param newX
	 * @param newY
	 * @return true wenn newX und newY die Bedingung >=0 erfüllen
	 */
	private boolean validArguments(int newX, int newY) {
		return newX >= 0 && newY >= 0;
	}
}
