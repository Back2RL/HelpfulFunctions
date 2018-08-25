import neuralnet.net.NeuronNet;

import java.awt.*;
import java.util.List;

public class Creature {
	private static final boolean DEBUG = false;

	public static final double velocity = 200.0;
	public static double turnRate = 360.0;
	private double currVelocity;

	public Vec2D getLocation() {
		return location;
	}

	public void setLocation(Vec2D location) {
		this.location = location;
	}

	private Vec2D location;
	public NeuronNet brain;

	public Vec2D getForwardDir() {
		return forwardDir;
	}

	public void setForwardDir(Vec2D forwardDir) {
		this.forwardDir = forwardDir;
	}

	private Vec2D forwardDir;

	public NeuronNet getBrain() {
		return brain;
	}

	public void setBrain(NeuronNet brain) {
		this.brain = brain;
	}

	public Creature(int x, int y, NeuronNet brain) {
		//turnRate = new Random().nextBoolean() ? turnRate : -turnRate;
		this.brain = brain;
		location = new Vec2D(x, y);
		forwardDir = new Vec2D(Math.random() * 2.0 - 1.0, Math.random() * 2.0 - 1.0).getNormalized();
	}


	public void draw(Graphics g) {
		g.fillOval((int) location.getX(), (int) location.getY(), 5, 5);
	}


	public void move(double deltaTime) {
		location = location.add(forwardDir.multiplyWithDouble(currVelocity * deltaTime));
	}

	public void learn(double[] inputVals, double dt) {


		brain.feedForward(inputVals);

		//List<Double> targetVals = new ArrayList<>();
		//targetVals.add( - dotRight * Math.signum(dotRight));

		//brain.backProp(targetVals);

		double[] resultVals = brain.getResults();

		if (DEBUG) {
			System.out.println("Target : Generated");

			System.out.println(brain.toString() + "      : " + resultVals.toString());

			// :::::::::::::::::::::::::::::::::::::::::::::::::::::::

			System.out.println("recent average Error = " + brain.getRecentAverageError());
			System.out.println("recent  Error =                  " + brain.getError());
		}
		forwardDir.rotateByDeg(turnRate * resultVals[0] * dt);
		currVelocity = velocity * -0.5 + resultVals[1] * velocity * 1.5;


	}

}
