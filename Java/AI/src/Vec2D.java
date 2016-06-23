import java.awt.*;

/**
 * Created by Leo2400 on 23.06.2016.
 */
public class Vec2D extends Point.Double {

    public Vec2D(double x, double y) {
        setLocation(x, y);
    }

    public Vec2D getNormalized() {
        double len = getLength();
        return new Vec2D(getX() / len, getY() / len);
    }

    public double getLength() {
        return Math.sqrt(getX() * getX() + getY() * getY());
    }

    public void rotateByDeg(double degree) {
        double angRad = degreeToRadian(degree);

        double newX = Math.cos(angRad) * x - Math.sin(angRad) * y;
        double newY = Math.sin(angRad) * x + Math.cos(angRad) * y;
        setLocation(newX, newY);
    }

    public Vec2D getRotated(double angle){
        Vec2D newVec = new Vec2D(x,y);
        newVec.rotateByDeg(angle);
        return newVec;
    }

    public static double degreeToRadian(double degree) {
        return degree / 180.0 * Math.PI;
    }

    public Vec2D add(Vec2D toAdd){
        return new Vec2D(x + toAdd.x, y + toAdd.y);
    }

    public Vec2D multiply(Vec2D toMultiply){
        return new Vec2D(x * toMultiply.x, y * toMultiply.y);
    }

    public Vec2D multiplyWithDouble(double value){
        return new Vec2D(x * value, y * value);
    }

    public Vec2D subtract(Vec2D toSubtract){
        return new Vec2D(x - toSubtract.x, y - toSubtract.y);
    }

    public double dotProduct(Vec2D other){
        return x*other.x + y*other.y;
    }

}
