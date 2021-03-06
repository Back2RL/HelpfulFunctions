import java.awt.geom.Rectangle2D;

public class Actor {

	private Loc position;
	private Loc velocity;

	public double rotation;

	public Rectangle2D.Double basicBlock;

	public Loc getVelocity() {
		return velocity;
	}

	public void setVelocity(Loc velocity) {
		this.velocity = velocity;
	}

	public Loc getPosition() {
		return position;
	}

	public void setPosition(Loc position) {
		basicBlock.x = position.x;
		basicBlock.y = position.y;
		this.position = position;
	}

	public Loc getForwardVector() {
		return new Loc(Math.sin(rotation), Math.cos(rotation));
	}

	public Loc getRightVector() {
		double rightRot = rotation - Math.PI * 0.5d;
		return new Loc(Math.sin(rightRot), Math.cos(rightRot));
	}
}
