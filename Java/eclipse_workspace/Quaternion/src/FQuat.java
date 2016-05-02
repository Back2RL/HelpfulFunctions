// http://www.cprogramming.com/tutorial/3d/quaternions.html
// http://www.cbcity.de/tutorial-rotationsmatrix-und-quaternion-einfach-erklaert-in-din70000-zyx-konvention

public class FQuat {
	public static final float EPS = (float) 1E-9;

	private boolean bMagIsSet = false;
	private float magnitude;
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

	public FQuat(float w, float x, float y, float z) {
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
		normalize();
	}

	public FQuat() {
		normalize();
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

		if (w == 0 && x == 0 && y == 0 && z == 0) {
			w = 1.0f;
			bMagIsSet = true;
			magnitude = 1.0f;
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

		float halfAngle = 0.5f * angle * (float) (Math.PI / 180.0f);
		float sinHalfAngle = (float) Math.sin(halfAngle);
		w = (float) Math.cos(halfAngle);
		x = axisX * sinHalfAngle;
		y = axisY * sinHalfAngle;
		z = axisZ * sinHalfAngle;
		normalize();
	}

	/**
	 * @return index 0 : angle, index 1-3 : vector with x,y,z
	 */
	public float[] getRotAngleAndAxis() {
		float angle = 2.0f * (float) Math.acos((w));
		float s = (float) Math.sin(0.5 * angle);
		if (s == 0.0f) {
			float[] result = { (float) (angle * (180.0f / Math.PI)), x, y, z };
			return result;
		}

		float[] result = { (float) (angle * (180.0f / Math.PI)), x / s, y / s, z / s };
		return result;
	}

	public float getEulerYaw() {
		normalize();
		float divider = (w * w + x * x - y * y - z * z);
		if (divider == 0.0f) {
			return (float) (Math.atan2(2.0 * (x * y + w * z), 1.0) * (180.0 / Math.PI));
		}
		return (float) (Math.atan2(2.0 * (x * y + w * z), divider) * (180.0 / Math.PI));
	}

	public float getEulerPitch() {
		normalize();
		return (float) (Math.asin(2.0 * (w * y - x * z)) * (180.0f / Math.PI));
	}

	public float getEulerRoll() {
		normalize();
		float divider = (w * w - x * x - y * y + z * z);
		if (divider == 0.0f) {
			return (float) -Math.atan(-2.0 * (y * z + w * x)) * (float) (180.0f / Math.PI);
		}
		return (float) -Math.atan(-2.0 * (y * z + w * x) / divider) * (float) (180.0f / Math.PI);
	}

	public String toString() {
		return "(" + w + ", " + x + ", " + y + ", " + z + ")";
	}

	public static FQuat QuatMul(FQuat q1, FQuat q2) {
		float A, B, C, D, E, F, G, H;
		A = (q1.getW() + q1.getX()) * (q2.getW() + q2.getX());
		B = (q1 -> z - q1 -> y) * (q2 -> y - q2 -> z);
		C = (q1 -> w - q1 -> x) * (q2 -> y + q2 -> z);
		D = (q1 -> y + q1 -> z) * (q2 -> w - q2 -> x);
		E = (q1 -> x + q1 -> z) * (q2 -> x + q2 -> y);
		F = (q1 -> x - q1 -> z) * (q2 -> x - q2 -> y);
		G = (q1 -> w + q1 -> y) * (q2 -> w - q2 -> z);
		H = (q1 -> w - q1 -> y) * (q2 -> w + q2 -> z);
		return new FQuat(B + (-E - F + G + H) / 2, A - (E + F + G + H) / 2, C + (E - F + G - H) / 2,
				D + (E - F - G + H) / 2);

	}

}
