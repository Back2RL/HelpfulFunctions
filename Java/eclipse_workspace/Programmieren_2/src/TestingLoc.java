
public class TestingLoc {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Loc location = new Loc("7,3");
		System.out.println(location + " has a length of " + location.distanceFromOrigin());
		long startTime = System.nanoTime();

		for (int i = 0; i < (int) 1E9; i++) {
			// long test = System.nanoTime();
			long test = System.currentTimeMillis();
		}

		long endTime = System.nanoTime();

		System.out.println("Total time: " + ((endTime - startTime) * 1E-9));
	}

}
