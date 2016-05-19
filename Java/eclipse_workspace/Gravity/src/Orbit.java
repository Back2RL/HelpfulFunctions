
public class Orbit {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Planet earth = new Planet(5.972E24, 9.81, 6371000.0);
		// Planet moon = new Planet(7.34767309E22, 1.62, 1737000.0);
		// double distance = 384402.0E3;
		Planet earth = new Planet(5.972E24, false, 637.10);
		Planet moon = new Planet(7.34767309E22, false, 173.7);
		double distance = 38440.2;
		System.out.println("Earth: " + earth);
		System.out.println("Moon:  " + moon);
		System.out.println();

		double earthAccel = earth.getAccelTowards(moon, distance);
		double moonAccel = moon.getAccelTowards(earth, distance);
		System.out.println(moonAccel + " m/s² Moon");
		System.out.println(earthAccel + " m/s² Earth");
		System.out.println();

		double earthForce = earth.getForceTowards(moon, distance);
		double moonForce = moon.getForceTowards(earth, distance);
		System.out.println(moonForce + " Newton Moon");
		System.out.println(earthForce + " Newton Earth");
		System.out.println();

		double jacDistEarth = earth.getJacobiDist(moon, distance);
		double jacDistMoon = moon.getJacobiDist(earth, distance);
		System.out.println(jacDistMoon + " m to Moonorbitcenter");
		System.out.println(jacDistEarth + " m to Earthorbitcenter");
		System.out.println();

		// System.out.println((jacDistEarth + jacDistMoon) + " = " + distance +
		// "?");

		double orbitVelMoon = moon.getOrbitVelocity(moonAccel, false, jacDistMoon);
		double orbitVelEarth = earth.getOrbitVelocity(earthForce, true, jacDistEarth);
		System.out.println(orbitVelMoon + " m/s Moon");
		System.out.println(orbitVelEarth + " m/s Earth");
		System.out.println();

	}

}
