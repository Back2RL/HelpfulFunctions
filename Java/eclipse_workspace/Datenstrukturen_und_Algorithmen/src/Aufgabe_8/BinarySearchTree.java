package Aufgabe_8;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BinarySearchTree {

	private final boolean DEBUG = false;
	private TreeNode root;
	private int nodeGeneratingCounter;

	/**
	 * default constructor, treeroot is null
	 */
	public BinarySearchTree() {
	}

	/**
	 * constructor that initializes the tree with a TreeNode
	 * 
	 * @param root
	 */
	public BinarySearchTree(final TreeNode root) {
		this.root = root;
	}

	/**
	 * @param data
	 *            integer value to look for in the tree
	 * @return a TreeNode or null if element with data does not exist
	 */
	public TreeNode search(final int data) {
		return findNodeWithData(root, data);
	}

	/**
	 * recursive method to find data in the tree
	 * 
	 * @param current
	 * @param data
	 * @return
	 */
	private TreeNode findNodeWithData(final TreeNode current, final int data) {
		if (current == null)
			return current;
		if (current.getData() == data) {
			return current;
		}
		if (current.getLeftChild() != null) {
			final TreeNode result = findNodeWithData(current.getLeftChild(), data);
			if (result != null && result.getData() == data) {
				return result;
			}
		}
		if (current.getRightChild() != null) {
			final TreeNode result = findNodeWithData(current.getRightChild(), data);
			if (result != null && result.getData() == data) {
				return result;
			}
		}
		return null;
	}

	public void insert(final int newData) {
		if (root == null) {
			root = new TreeNode(null, newData);
			return;
		}

		TreeNode curr = root;
		TreeNode prev = null;
		while (true) {
			if (curr == null) {
				curr = new TreeNode(prev, newData);
				if (newData <= prev.getData()) {
					prev.setLeftChild(curr, false);
				} else {
					prev.setRightChild(curr, false);
				}
				break;
			}

			if (newData <= curr.getData()) {
				prev = curr;
				curr = curr.getLeftChild();
			} else {
				prev = curr;
				curr = curr.getRightChild();
			}
		}
	}

	public void generateRandomTree(final int maxNodes, final int min, final int max) {
		final Random rand = new Random();

		for (int i = 0; i < maxNodes; ++i) {
			insert(min + rand.nextInt(max - min + 1));
		}
	}

	/**
	 * generates recursively childnodes for a parentnode while maximum not
	 * reached or no children where generated
	 * 
	 * @param parent
	 * @param maxNodes
	 * @param rand
	 * @param min
	 * @param max
	 */
	private void generateNode(final TreeNode parent, final int maxNodes, final Random rand, final int min,
			final int max) {
		if (nodeGeneratingCounter >= maxNodes)
			return;
		final boolean leftHasElement = rand.nextBoolean();
		if (leftHasElement && nodeGeneratingCounter < maxNodes) {
			nodeGeneratingCounter++;
			parent.setLeftChild(new TreeNode(parent, min + rand.nextInt(max - min + 1)), false);
			generateNode(parent.getLeftChild(), maxNodes, rand, min, max);
		}
		final boolean rightHasElement = rand.nextBoolean();
		if (rightHasElement && nodeGeneratingCounter < maxNodes) {
			nodeGeneratingCounter++;
			parent.setRightChild(new TreeNode(parent, min + rand.nextInt(max - min + 1)), false);
			generateNode(parent.getRightChild(), maxNodes, rand, min, max);
		}
	}

	public void delete(final int data) {
		final TreeNode delNode = search(data);
		if (delNode == null) {
			throw new IllegalArgumentException("data not element of the tree");
		}
		delete(delNode);
	}

	public void delete(final TreeNode delNode) {
		if (delNode == null) {
			throw new IllegalArgumentException("delNode must not be null");
		}

		// 1. case: both children of delNode are null
		if (delNode.getLeftChild() == null && delNode.getRightChild() == null) {

			System.out.println("0 children");
			// is delNode the current root of the tree
			if (delNode == root) {
				root = null;
			} else {
				// the TreeNode has a parent
				updateChildrenOfChildrenParent(delNode, null);
			}
			// delNode has been removed for the tree
			return;
		}

		// 2. case: one child exists
		if (delNode.getLeftChild() == null ^ delNode.getRightChild() == null) {
			System.out.println("1 child");
			// is delNode the current root of the tree
			if (delNode == root) {
				if (delNode.getLeftChild() == null) {
					root = delNode.getRightChild();
				} else {
					root = delNode.getLeftChild();
				}
			} else {
				// the TreeNode has a parent
				if (delNode.getLeftChild() == null) {
					updateChildrenOfChildrenParent(delNode, delNode.getRightChild());
				} else {
					updateChildrenOfChildrenParent(delNode, delNode.getLeftChild());
				}
			}
			return;
		}

		// last case: two children exist
		System.out.println("2 children");
		TreeNode curr = delNode.getRightChild();
		while (curr.getLeftChild() != null) {
			curr = curr.getLeftChild();
		}
		// replace date with the smallest of the right partial tree
		delNode.setData(curr.getData());

		// delete the node the data was taken from
		updateChildrenOfChildrenParent(curr, curr.getRightChild());
	}

	private void updateChildrenOfChildrenParent(final TreeNode child, final TreeNode newChild) {
		if (child == null) {
			throw new IllegalArgumentException("child is null");
		}
		if (child.getParent() == null) {
			throw new IllegalArgumentException("child has no parent: parent is null");
		}

		if (child.getParent().getLeftChild() == child) {
			child.getParent().setLeftChild(newChild, false);
		} else {
			child.getParent().setRightChild(newChild, false);
		}
	}

	private enum WireStatus {
		FindLeft, GoToParent, CheckRight
	}

	public void generateInOrderWire() {
		if (root == null) {
			return;
		}

		final List<TreeNode> parents = new ArrayList<TreeNode>();

		TreeNode curr = root;
		WireStatus status = WireStatus.FindLeft;
		int cnt = 0;
		do {
			switch (status) {
			case FindLeft: {
				// find the smallest one child on the left side
				if (curr != null && curr.getLeftChild() != null) {
					if (DEBUG)
						System.out.println(curr.getData() + " - left child exists");
					// store current node as parent
					parents.add(curr);
					// go left
					curr = curr.getLeftChild();
				} else {
					status = WireStatus.CheckRight;
				}
			}
				break;
			case CheckRight: {
				if (DEBUG)
					System.out.println(curr.getData() + " - current node");
				// was there no child found and there are also no right children
				if (curr == root && curr.getRightChild() == null) {
					if (DEBUG)
						System.out.println(curr.getData() + " - is root -> return");
					return;
				}

				// smallest child found
				// wire up the right sides until there is a child
				if (curr.getRightChild() == null && parents.size() > 0) {
					status = WireStatus.GoToParent;
				} else {
					curr = curr.getRightChild();
					status = WireStatus.FindLeft;
				}
			}
				break;
			case GoToParent: {
				// no right children: wire to parent
				curr.setRightChild(parents.get(parents.size() - 1), true);
				parents.remove(parents.size() - 1);

				// follow the new wire to parent
				curr = curr.getRightChild();
				if (DEBUG)
					System.out.println(curr.getData() + " - current node via wire reached");
				status = WireStatus.CheckRight;

			}
				break;
			default:
				break;
			}
			if (DEBUG)
				System.out.println("runs = " + ++cnt);
		} while (curr != null);

	}

	public void printInOrder() {
		System.out.println("Print:");
		int cnt = 0;
		TreeNode current = root;
		TreeNode previous = null;

		do {
			while (current.getLeftChild() != null && !current.leftIsWire()) {
				current = current.getLeftChild();
			}
			System.out.println(current.getData());
			++cnt;
			previous = current;
			current = current.getRightChild();
			while (previous.rightIsWire() && (current != null)) {

				System.out.println(current.getData());
				++cnt;
				previous = current;
				current = current.getRightChild();
			}

		} while (current != null);
		System.out.println("Anzahl = " + cnt);
	}

}
