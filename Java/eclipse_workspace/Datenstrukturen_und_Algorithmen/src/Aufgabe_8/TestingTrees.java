package Aufgabe_8;

public class TestingTrees {
	public static void main(final String[] args) {
		final BinarySearchTree two = new BinarySearchTree();
		System.out.println("Generation of nodes");
		two.generateRandomTree(1000, 0, 1000);
		System.out.println("Wire generation");
		two.generateInOrderWire();
		two.printInOrder();
		System.out.println(two.getHeigth());
	}

}
