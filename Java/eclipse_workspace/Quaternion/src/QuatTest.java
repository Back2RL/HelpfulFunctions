public class QuatTest {
	public static final int RUNS = 100000000;
	public static final boolean USEA = true;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long start = System.nanoTime();
		for (int i = 0; i < RUNS; ++i) {

			// System.out.println("Quat a:");
			FQuat a = new FQuat(Math.random(), 1, 1, 0);
			// System.out.println(a);
			// System.out.println(a.getEulerYaw());
			// System.out.println(a.getEulerPitch());
			// System.out.println(a.getEulerRoll());
			// System.out.println(Arrays.toString(a.getRotAngleAndAxis()));
			// System.out.println();
			//
			// System.out.println("Quat b: 20, 1, 0, 0");
			FQuat b = new FQuat();
			// b.fromRotAndAxis(45, 0, 0, 1);
			// System.out.println(b);
			// System.out.println(b.getEulerYaw());
			// System.out.println(b.getEulerPitch());
			// System.out.println(b.getEulerRoll());
			// System.out.println(Arrays.toString(b.getRotAngleAndAxis()));
			// System.out.println();
			//
			// System.out.println("Quat c: 20, 1, 0, 0");
			// FQuat c = new FQuat();
			// c.fromRotAndAxis(-90, 0, 1, 0);
			// System.out.println(c);
			// System.out.println(c.getEulerYaw());
			// System.out.println(c.getEulerPitch());
			// System.out.println(c.getEulerRoll());
			// System.out.println(Arrays.toString(c.getRotAngleAndAxis()));
			// System.out.println();

			// FQuat e = FQuat.multiply(b, c);
			// System.out.println("b mit c Multipliziert: " +
			// Arrays.toString(e.getRotAngleAndAxis()));
			// System.out.println("c mit b Multipliziert: " +
			// Arrays.toString(FQuat.multiply(c, b).getRotAngleAndAxis()));
			// System.out.println(e);
			// System.out.println(e.getEulerYaw());
			// System.out.println(e.getEulerPitch());
			// System.out.println(e.getEulerRoll());
			// System.out.println(Arrays.toString(e.getRotAngleAndAxis()));
			// System.out.println();
			if (USEA)
				FQuat.multiply(a, b);
			else
				FQuat.QuatMul(a, b);

		}
		long end = System.nanoTime();
		double runtime = (end - start) * 1E-9;
		System.out.println("Time = " + runtime + " seconds");
		System.out.println("Avg. = " + Math.round((end - start) * (1.0 / RUNS)) + " nanoseconds");

	}
}
