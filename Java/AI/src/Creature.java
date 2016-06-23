import java.awt.*;
import java.util.*;
import java.util.List;

public class Creature {

    public static final double velocity = 50.0;
    public static final double turnRate = 90.0;

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

    public Creature(int x, int y) {
        brain =  new NeuronNet(new int[]{2,3,4,5,4,2,1,1});
        location = new Vec2D(x, y);
        forwardDir = new Vec2D(Math.random() * 2.0 - 1.0, Math.random() * 2.0 - 1.0).getNormalized();
    }


    public void draw(Graphics g) {
        g.fillOval((int) location.getX(), (int) location.getY(), 3, 3);
    }

    public static void main(String[] args) {

        Creature test = new Creature(5, 5);
        System.out.println(test.getForwardDir());
        test.getForwardDir().rotateByDeg(90.0);
        System.out.println(test.getForwardDir());

    }

    public void move(double deltaTime){
        location = location.add(forwardDir.multiplyWithDouble(velocity * deltaTime));
    }

    public void learn(double dotForward, double dotRight, double dt){

        List<Double> inputVals = new ArrayList<>();
        inputVals.add(dotForward);
        inputVals.add(dotRight);
        brain.feedForward(inputVals);

        List<Double> targetVals = new ArrayList<>();
        targetVals.add( - dotRight * Math.signum(dotRight));

        brain.backProp(targetVals);

        List<Double> resultVals = brain.getResults();

        System.out.println("Target : Generated");
        System.out.println(brain.toString() + "      : " + resultVals.toString());

        // :::::::::::::::::::::::::::::::::::::::::::::::::::::::

        System.out.println("recent average Error = " + brain.getRecentAverageError());
        System.out.println("recent  Error =                  " + brain.getError());

        forwardDir.rotateByDeg(turnRate * resultVals.get(0) * dt);


    }

}
