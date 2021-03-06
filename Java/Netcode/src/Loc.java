import java.awt.Graphics;

/**
 * Invariante: alle Objekte dieser Klasse haben Koordinaten >= 0
 * 
 * @author Leo
 *
 */
public class Loc {
	double x;
	double y;

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
	public Loc(double x, double y) {
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
		g.fillOval((int) x, (int) y, 3, 3);
		g.drawString("(" + x + "," + y + ")", (int) x, (int) y);
	}

	/**
	 * calculates the distance between two locations
	 * 
	 * @param loc
	 *            The second location
	 * @return Returns the distance (double)
	 */
	public double distance(Loc loc) {
		double dx = loc.x - x;
		double dy = loc.y - y;
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
	public void setLocation(double newx, double newy) {
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
	public void translate(double dx, double dy) {
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

	public Loc normalize() {
		double length = distanceFromOrigin();
		return new Loc(this.x / length, this.y / length);
	}

	/**
	 * Hessesche Normalform
	 * 
	 * @param lineLoc
	 *            location on the line
	 * @param lineDir
	 *            direction of the line
	 * @param dot
	 *            location of which the distance to the line is requested
	 * @return shortest distance between line and dot
	 */
	public static double distanceLineDot(Loc lineLoc, Loc lineDir, Loc dot) {
		Loc n = new Loc(lineDir.y, -1.0 * lineDir.x);
		double len = n.distanceFromOrigin();
		n.normalize();
		lineLoc.translate(-1.0 * dot.x, -1.0 * dot.y);
		return Math.abs(lineLoc.x * n.x + lineLoc.y * n.y) / len;
	}

}
