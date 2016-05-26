package Aufgabe_8;

import java.util.Scanner;

public class TestingTrees {
	public static void main(String[] args) {
		BinarySearchTree test = new BinarySearchTree();
		test.generateRandomUnsortedTree(10, 1, 2);
		// TreeNode result = test.search(2);
		// System.out.println(result != null ? result.getData() : -1);
		test.printElems();
		test.delete(2);
		System.out.println();
		test.printElems();
		
		BinarySearchTree two = new BinarySearchTree();
		Scanner console = new Scanner(System.in);
		while(console.hasNextInt()){
			two.insert(console.nextInt());
			two.printElems();
		}
		
		
	}

}
