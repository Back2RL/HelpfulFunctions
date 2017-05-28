package Aufgabe_8;

public class TestingTrees {
	public static void main(final String[] args) {

		final Runtime rt = Runtime.getRuntime();
		System.out.println(rt.totalMemory());
		System.out.println(rt.freeMemory());
		final long start = rt.totalMemory() - rt.freeMemory();
		System.out.println(start);
		final BinarySearchTree two = new BinarySearchTree();

		two.generateRandomTree(10000000, 1, 1000000000, true);
		//
		// final TreeMap<Integer, Byte> test = new TreeMap<>();
		// for (int i = 0; i < 10000000; ++i) {
		// two.insert(i, false);
		// // test.put(i, (byte) 1);
		// }
		// System.out.println(test.lastKey());

		// two.insert(5, false);
		// System.out.println("added");
		// two.insert(4, false);
		// System.out.println("added");
		// two.insert(3, false);
		// System.out.println("added");
		// two.insert(2, false);
		// System.out.println("added");
		// two.insert(1, false);
		// System.out.println("added");
		// two.insert(0, false);
		// System.out.println("added last");
		// two.printInOrder();
		//
		// two.delete(2, false);
		// two.printInOrder();
		// two.delete(1, false);
		// two.printInOrder();
		// two.delete(3, false);
		// two.printInOrder();
		// two.delete(4, false);
		// two.printInOrder();
		// two.delete(5, false);
		// two.printInOrder();
		// two.delete(6, false);
		// two.printInOrder();
		// two.delete(7, false);
		// two.printInOrder();
		// two.delete(8, false);
		// two.printInOrder();
		// two.delete(9, false);
		System.out.println(rt.totalMemory());
		System.out.println(rt.freeMemory());
		final long end = rt.totalMemory() - rt.freeMemory();
		System.out.println(end);
		System.out.println(((end - start) / (1024 * 1024)) + " Mbyte");
		// two.printInOrder();
		final int searchNum = 999;
		// two.delete(100, false);
		System.out.println(two.search(500));
		System.out.println(searchNum + " is in TreeNode = " + two.search(searchNum));
		System.out.println("Tree height = " + two.getHeigth());
	}

}