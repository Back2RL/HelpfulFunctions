
public class Planet {

	// [SI unit] https://de.wikipedia.org/wiki/Gravitationskonstante
	public final double GRAVITATION_CONST = 6.67408E-11;

	private double mass;
	private double gravity;
	private double radius;

	@Override
	public String toString() {
		return "Planet [mass=" + mass + ", gravity=" + gravity + ", radius=" + radius + "]";
	}

	/**
	 * @param mass
	 * @param gravity
	 * @param radius
	 */
	public Planet(double mass, double gravity, double radius) {
		this.mass = mass;
		this.gravity = gravity;
		this.radius = radius;
	}

	/**
	 * @param gravityOrMass
	 * @param isGravity
	 *            if true the mass of the planet will be calculated from the
	 *            gravity and radius, if false the gravity of the planet will be
	 *            calculated from the mass and radius
	 * @param radius
	 */
	public Planet(double gravityOrMass, boolean isGravity, double radius) {
		this.radius = radius;
		if (isGravity) {
			this.gravity = gravityOrMass;
			this.mass = gravity * (radius * radius) / GRAVITATION_CONST;
		} else {
			this.mass = gravityOrMass;
			this.gravity = GRAVITATION_CONST * mass / (radius * radius);
		}
	}

	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public double getGravity() {
		return gravity;
	}

	public void setGravity(double gravity) {
		this.gravity = gravity;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public Planet() {
		// TODO Auto-generated constructor stub
	}

	public double getAccelTowards(Planet other, double distance) {
		return GRAVITATION_CONST * other.getMass() / (distance * distance);
	}

	public double getForceTowards(Planet other, double distance) {
		return mass * getAccelTowards(other, distance);
	}

	public double getOrbitVelocity(Planet other, double distance) {
		return Math.sqrt(getAccelTowards(other, distance) * distance);
	}

	public double getOrbitVelocityFromAccel(double accel, double distance) {
		return Math.sqrt(accel * distance);
	}

	public double getOrbitVelocityFromForce(double force, double distance) {
		return Math.sqrt(force / mass * distance);
	}

	public double getJacobiDist(Planet other, double distance) {
		double M = this.mass + other.getMass();
		return other.getMass() * distance / M;
	}

}
