package PlayerInputData;

public class Test {
	public static void main(final String[] args) {
		final InputDataPackage test = new InputDataPackage();
		// yaw and pitch each 11 bits
		test.setMouseInput(0.0000001f, -1.f);
		printLongBits(test.data);

		System.out.println("Yaw decompressed = " + test.getMouseYaw());
		System.out.println("Pitch decompressed = " + test.getMousePitch());
		test.setMouseInput(0, 0);
		printLongBits(test.data);
		System.out.println("------------------------");
		System.out.println("results from shifting");

		System.out.println("max long = " + Long.MAX_VALUE);
		long x = 0;
		for (int i = 0; i < 64; ++i) {
			x |= 1l << i;
			System.out.println(i + " sum = " + x);
			printLongBits(x);
		}

		System.out.println("------------------------");

		System.out.println("clear mouse input");
		x &= InputDataPackage.mouseInputClear;
		printLongBits(x);

		// // packetcounter 24 bits -> a day worth playing a 144 fps
		// x &= InputDataPackage.packetCounterClear;
		// printLongBits(x);
		//
		// // slot1
		// x &= ~(1l << 52);
		// printLongBits(x);
		// // slot2
		// x &= ~(1l << 53);
		// printLongBits(x);
		// // slot3
		// x &= ~(1l << 54);
		// printLongBits(x);
		// // slot4
		// x &= ~(1l << 55);
		// printLongBits(x);
		// // slot5
		// x &= ~(1l << 56);
		// printLongBits(x);
		// // slot6
		// x &= ~(1l << 57);
		// printLongBits(x);
		// // slot7
		// x &= ~(1l << 58);
		// printLongBits(x);
		// // slot8
		// x &= ~(1l << 59);
		// printLongBits(x);
		// // slot9
		// x &= ~(1l << 60);
		// printLongBits(x);
		// // slot10
		// x &= ~(1l << 61);
		// printLongBits(x);
		//
		// // 2 bits left
		//
		// System.out.println((long) (24.0 * 3600 * 144.0));
	}

	public static void printLongBits(final long number) {
		for (int i = 63; i >= 0; --i) {
			if (i % 8 == 7) {
				System.out.print(" ");
			}
			System.out.print((number & 1l << i) == 1l << i ? "1" : "0");
		}
		System.out.println();
	}
}
// 0111111111110000