package PlayerInputData;

public class Test {
	public static void main(final String[] args) {
		final InputDataPackage test = new InputDataPackage();
		test.setMouseInput(1.0f, 1.0f);
		for (int i = 63; i >= 0; --i) {
			System.out.print((test.data & 1l << i) == 1l << i ? "1" : "0");
		}
		System.out.println();
		System.out.println("Yaw decompressed = " + test.getMouseYaw());
		System.out.println("Pitch decompressed = " + test.getMousePitch());
		test.setMouseInput(0, 0);
		for (int i = 63; i >= 0; --i) {
			System.out.print((test.data & 1l << i) == 1l << i ? "1" : "0");
		}
		System.out.println();
		System.out.println(Long.MAX_VALUE);
		System.out.println("Values");
		long x = 0;
		for (int i = 0; i < 64; ++i) {
			x |= 1l << i;
			System.out.println(i + " sum = " + x);
		}
		x &= InputDataPackage.mouseInputClear;
		for (int i = 63; i >= 0; --i) {
			System.out.print((x & 1l << i) == 1l << i ? "1" : "0");
		}
	}
}
0111111111110000