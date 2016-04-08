// http://www.cprogramming.com/tutorial/3d/quaternions.html
public class FQuat {
	public static final float EPS = (float) 1E-9;
	private float w, x, y, z;

	public float getW() {
		return w;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	private boolean bMagIsSet = false;
	private float magnitude;

	public FQuat(float w, float x, float y, float z) {
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public FQuat() {
		w = 1.0f;
		magnitude = 1.0f;
		bMagIsSet = true;
	}

	public float getMagnitude() {
		if (bMagIsSet) {
			return magnitude;
		}
		magnitude = (float) Math.sqrt(w * w + x * x + y * y + z * z);
		bMagIsSet = true;
		return magnitude;
	}

	public boolean isNormalized() {
		if (bMagIsSet) {
			return Math.abs(1 - magnitude) < EPS;
		}
		return Math.abs(1 - (w * w + x * x + y * y + z * z)) < EPS;
	}

	public void normalize() {
		if (isNormalized()) {
			return;
		}
		float mag = getMagnitude();
		w /= mag;
		x /= mag;
		y /= mag;
		z /= mag;
		bMagIsSet = true;
		magnitude = 1.0f;
	}

	public static FQuat multiply(FQuat a, FQuat b) {
		if (!a.isNormalized()) {
			a.normalize();
		}
		if (!b.isNormalized()) {
			b.normalize();
		}
		float w = a.getW() * b.getW() - a.getX() * b.getX() - a.getY() * b.getY() - a.getZ() * b.getZ();
		float x = a.getW() * b.getX() + a.getX() * b.getW() + a.getY() * b.getZ() - a.getZ() * b.getY();
		float y = a.getW() * b.getY() - a.getX() * b.getZ() + a.getY() * b.getW() + a.getZ() * b.getX();
		float z = a.getW() * b.getZ() + a.getX() * b.getY() - a.getY() * b.getX() + a.getZ() * b.getW();
		return new FQuat(w, x, y, z);
	}

	public void multiplyWith(FQuat b) {
		if (!isNormalized()) {
			normalize();
		}
		if (!b.isNormalized()) {
			b.normalize();
		}
		float newW = w * b.getW() - x * b.getX() - y * b.getY() - z * b.getZ();
		float newX = w * b.getX() + x * b.getW() + y * b.getZ() - z * b.getY();
		float newY = w * b.getY() - x * b.getZ() + y * b.getW() + z * b.getX();
		float newZ = w * b.getZ() + x * b.getY() - y * b.getX() + z * b.getW();
		w = newW;
		x = newX;
		y = newY;
		z = newZ;
		// disable flag to be able to check for floatingpoint inaccuracy
		bMagIsSet = false;
	}

	public void fromRotAndAxis(float angle, float axisX, float axisY, float axisZ) {
		// TODO: implement using an object for the axis
		// normalize the axis
		float tempAxisMag = (float) Math.sqrt(axisX * axisX + axisY * axisY + axisZ * axisZ);
		axisX /= tempAxisMag;
		axisY /= tempAxisMag;
		axisZ /= tempAxisMag;

		bMagIsSet = false;
		float halfAngle = 0.5f * angle;
		float sinHalfAngle = (float) Math.sin(halfAngle);
		w = (float) Math.cos(halfAngle);
		x = axisX * sinHalfAngle;
		y = axisY * sinHalfAngle;
		z = axisZ * sinHalfAngle;
	}
}
