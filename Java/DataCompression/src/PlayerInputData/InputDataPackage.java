package PlayerInputData;

public class InputDataPackage {
	public long data;

	public InputDataPackage() {
	}

	// Movement
	static final long moveLeft = 1l << 3;
	static final long moveRight = 1l << 2;
	static final long moveBack = 1l << 1;
	static final long moveForward = 1l << 0;

	static final long mouseInputClear = ~(16777215 << 4);
	static final long mouseYawIsNegative = 1l << 1;
	static final long mousePitchIsNegative = 1l << 27;
	static final long mouseYaw = 2047l << 4;
	static final long mousePitch = 2047l << 16;

	static final long packetCounterClear = ~(16777215l << 28);

	public void setMouseInput(final float x, final float y) {
		data &= mouseInputClear;
		data |= ((long) (2047 * Math.abs(x))) << 4;
		if (x < 0.0f) {
			data |= mouseYawIsNegative;
		}

		data |= ((long) (2047 * Math.abs(y))) << 16;
		if (y < 0.0f) {
			data |= mousePitchIsNegative;
		}
	}

	public float getMouseYaw() {
		if ((data & mouseYawIsNegative) == mouseYawIsNegative) {
			return 0.0f - (((data & mouseYaw) >> 4) / 2047.0f);
		}
		return ((data & mouseYaw) >> 4) / 2047.0f;
	}

	public float getMousePitch() {
		if ((data & mousePitchIsNegative) == mousePitchIsNegative) {
			return 0.0f - (((data & mousePitch) >> 16) / 2047.0f);
		}
		return ((data & mousePitch) >> 16) / 2047.0f;
	}

	public void getMouseInput() {

	}
}