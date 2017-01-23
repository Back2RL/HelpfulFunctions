package Vector;

/**
 * Created by leonard on 19.01.17.
 */
public class FVector {
	private float x;
	private float y;

	public float getX() {
		return x;
	}

	public void setX(final float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(final float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(final float z) {
		this.z = z;
	}

	private float z;

	public FVector(final float x, final float y, final float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public FVector(final FVector other) {
		this.x = other.getX();
		this.x = other.getY();
		this.x = other.getZ();
	}


	public float getLengthSquared() {
		return x * x + y * y + z * z;
	}

	public float getLength() {
		return (float) Math.sqrt(getLengthSquared());
	}

	public FVector normalize() {
		final float length = getLength();
		if (length <= 0) {
			x = y = z = 0;
			return this;
		}
		x /= length;
		y /= length;
		z /= length;
		return this;
	}

	public FVector getNormalized() {
		FVector result = new FVector(this);
		return result.normalize();
	}

	public static FVector cross(final FVector pV1, final FVector pV2) {
		return new FVector(pV1.getY() * pV2.getZ() - pV1.getZ() * pV2.getY(),
				pV1.getZ() * pV2.getX() - pV1.getX() * pV2.getZ(),
				pV1.getX() * pV2.getY() - pV1.getY() * pV2.getX());
	}

	public float dot(final FVector other) {
		return x * other.getX() + y * other.getY() + z * other.getZ();
	}
}
