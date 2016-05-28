package Aufgabe_8;

public class TestingTrees {
	public static void main(final String[] args) {

		final Runtime rt = Runtime.getRuntime();
		System.out.println(rt.totalMemory());
		System.out.println(rt.freeMemory());
		final long start = rt.totalMemory() - rt.freeMemory();
		System.out.println(start);
		final BinarySearchTree two = new BinarySearchTree();
		two.generateRandomTree(100000, 0, Integer.MAX_VALUE - 1, false);
		// two.insert(7, false);
		// two.insert(8, false);
		// two.insert(9, false);
		// two.insert(10, false);
		// two.insert(5, false);
		// two.insert(4, false);
		// two.insert(3, false);
		// two.insert(2, false);
		// two.insert(1, false);
		// two.insert(0, false);
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
		System.out.println((end - start) + "bytes");
		// two.printInOrder();
		final int searchNum = 999;
		two.delete(100, false);
		System.out.println(two.search(500));
		System.out.println(searchNum + " is in TreeNode = " + two.search(searchNum));
		System.out.println("Tree height = " + two.getHeigth());
	}

}
