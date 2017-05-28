// http://www.cprogramming.com/tutorial/3d/quaternions.html
// http://www.cbcity.de/tutorial-rotationsmatrix-und-quaternion-einfach-erklaert-in-din70000-zyx-konvention

public class FQuat {
	public static final double EPS = 1E-9;

	private boolean bMagIsSet = false;
	private double magnitude;
	private double w, x, y, z;

	public double getW() {
		return w;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public FQuat(double w, double x, double y, double z) {
		this.w = (double) w;
		this.x = (double) x;
		this.y = (double) y;
		this.z = (double) z;
		normalize();
	}

	public FQuat() {
		normalize();
	}

	public double getMagnitude() {
		if (bMagIsSet) {
			return magnitude;
		}
		magnitude = Math.sqrt(w * w + x * x + y * y + z * z);
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

		double mag = getMagnitude();
		w /= mag;
		x /= mag;
		y /= mag;
		z /= mag;
		bMagIsSet = true;
		magnitude = 1.0f;
	}

	public static FQuat multiply(FQuat a, FQuat b) {
		// if (!a.isNormalized()) {
		// a.normalize();
		// }
		// if (!b.isNormalized()) {
		// b.normalize();
		// }
		double newW = a.w * b.w - a.x * b.x - a.y * b.y - a.z * b.z;
		double newX = a.w * b.x + a.x * b.w + a.y * b.z - a.z * b.y;
		double newY = a.w * b.y - a.x * b.z + a.y * b.w + a.z * b.x;
		double newZ = a.w * b.z + a.x * b.y - a.y * b.x + a.z * b.w;
		// return new FQuat(newW, newX, newY, newZ);
		FQuat res = new FQuat(newW, newX, newY, newZ);
		res.normalize();
		return res;
	}

	public void multiplyWith(FQuat b) {
		if (!isNormalized()) {
			normalize();
		}
		if (!b.isNormalized()) {
			b.normalize();
		}
		double newW = w * b.w - x * b.x - y * b.y - z * b.z;
		double newX = w * b.x + x * b.w + y * b.z - z * b.y;
		double newY = w * b.y - x * b.z + y * b.w + z * b.x;
		double newZ = w * b.z + x * b.y - y * b.x + z * b.w;
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
	public double[] getRotAngleAndAxis() {
		float angle = 2.0f * (float) Math.acos((w));
		float s = (float) Math.sin(0.5 * angle);
		if (s == 0.0f) {
			double[] result = { (angle * (180.0f / Math.PI)), x, y, z };
			return result;
		}

		double[] result = { (angle * (180.0f / Math.PI)), x / s, y / s, z / s };
		return result;
	}

	public double getEulerYaw() {
		normalize();
		double divider = (w * w + x * x - y * y - z * z);
		if (divider == 0.0f) {
			return (Math.atan2(2.0 * (x * y + w * z), 1.0) * (180.0 / Math.PI));
		}
		return (Math.atan2(2.0 * (x * y + w * z), divider) * (180.0 / Math.PI));
	}

	public double getEulerPitch() {
		normalize();
		return (Math.asin(2.0 * (w * y - x * z)) * (180.0f / Math.PI));
	}

	public double getEulerRoll() {
		normalize();
		double divider = (w * w - x * x - y * y + z * z);
		if (divider == 0.0f) {
			return -Math.atan(-2.0 * (y * z + w * x)) * (180.0 / Math.PI);
		}
		return -Math.atan(-2.0 * (y * z + w * x) / divider) * (180.0 / Math.PI);
	}

	public String toString() {
		return "(" + w + ", " + x + ", " + y + ", " + z + ")";
	}

	public static FQuat QuatMul(FQuat q1, FQuat q2) {
		// if (!q1.isNormalized()) {
		// q1.normalize();
		// }
		// if (!q2.isNormalized()) {
		// q2.normalize();
		// }
		double A = (q1.w + q1.x) * (q2.w + q2.x);
		double B = (q1.z - q1.y) * (q2.y - q2.z);
		double C = (q1.w - q1.x) * (q2.y + q2.z);
		double D = (q1.y + q1.z) * (q2.w - q2.x);
		double E = (q1.x + q1.z) * (q2.x + q2.y);
		double F = (q1.x - q1.z) * (q2.x - q2.y);
		double G = (q1.w + q1.y) * (q2.w - q2.z);
		double H = (q1.w - q1.y) * (q2.w + q2.z);
		return new FQuat(B + (-E - F + G + H) / 2, A - (E + F + G + H) / 2, C + (E - F + G - H) / 2,
				D + (E - F - G + H) / 2);

	}

	public static FQuat slerp(FQuat qa, FQuat qb, double t) {
		// quaternion to return
		qa.normalize();
		qb.normalize();
		FQuat qm = new FQuat();
		// Calculate angle between them. (dot product)
		double cosHalfTheta = qa.w * qb.w + qa.x * qb.x + qa.y * qb.y + qa.z * qb.z;

		// Calculate temporary values.
		double halfTheta = Math.acos(cosHalfTheta);
		double sinHalfTheta = Math.sqrt(1.0 - cosHalfTheta * cosHalfTheta);
		// if theta = 180 degrees then result is not fully defined
		// we could rotate around any axis normal to qa or qb
		if (Math.abs(sinHalfTheta) < 0.001) { // fabs is floating point absolute
			qm.w = (qa.w * 0.5 + qb.w * 0.5);
			qm.x = (qa.x * 0.5 + qb.x * 0.5);
			qm.y = (qa.y * 0.5 + qb.y * 0.5);
			qm.z = (qa.z * 0.5 + qb.z * 0.5);
			return qm;
		}
		double ratioA = Math.sin((1 - t) * halfTheta) / sinHalfTheta;
		double ratioB = Math.sin(t * halfTheta) / sinHalfTheta;
		// calculate Quaternion.
		qm.w = (qa.w * ratioA + qb.w * ratioB);
		qm.x = (qa.x * ratioA + qb.x * ratioB);
		qm.y = (qa.y * ratioA + qb.y * ratioB);
		qm.z = (qa.z * ratioA + qb.z * ratioB);
		return qm;
	}
}
