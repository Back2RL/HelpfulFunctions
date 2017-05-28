package Aufgabe_8;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BinarySearchTree {

	private final boolean DEBUG = false;

	/**
	 * the first node in the tree (parent is null)
	 */
	private TreeNode root;

	/**
	 * default constructor, tree root is null
	 */
	public BinarySearchTree() {
	}

	private enum WireCreationStep {
		CheckCurrent, CheckLeft, CheckRight, GoToParent, Success, Failed
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
	 * @param data integer value to look for in the tree
	 * @return a TreeNode or null if element with data does not exist
	 */
	public TreeNode search(final int data) {
		return searchNode(root, data);
	}

	/**
	 * recursive method to find data in the tree
	 *
	 * @param current
	 * @param data
	 * @return
	 */
	private TreeNode searchNode(TreeNode current, final int data) {
		while (current != null) {
			if (current.getData() == data) {
				return current;
			}
			if (data < current.getData()) {
				if (!current.leftIsWire()) {
					current = current.getLeftChild();
				} else {
					return null;
				}
			} else {
				if (!current.rightIsWire()) {
					current = current.getRightChild();
				} else {
					return null;
				}
			}
		}
		return null;
	}

	public void insert(final int newData, final boolean duplicatesAllowed) {
		if (DEBUG)
			System.out.println("to insert: " + newData);
		if (root == null) {
			root = new TreeNode(null, newData);
			if (DEBUG)
				System.out.println("root = " + newData);
			return;
		}

		TreeNode curr = root;
		// TODO: replace list
		TreeNode previous = null;

		WireCreationStep step = WireCreationStep.CheckCurrent;
		boolean searchInsertPos = true;

		while (searchInsertPos) {
			switch (step) {
				case CheckCurrent: {
					if (newData < curr.getData()) {
						if (DEBUG)
							System.out.println(" curr: smaller");
						step = WireCreationStep.CheckLeft;
						continue;
					}
					if (newData > curr.getData()) {
						if (DEBUG)
							System.out.println(" curr: bigger");
						step = WireCreationStep.CheckRight;
						continue;
					}
					if (newData == curr.getData()) {
						if (DEBUG)
							System.out.println(" curr: equal");
						if (duplicatesAllowed) {
							if (DEBUG)
								System.out.println("curr: equal dup");
							step = WireCreationStep.CheckLeft;
							continue;
						} else {
							if (DEBUG)
								System.out.println(" curr: equal no dup");
							step = WireCreationStep.Failed;
							continue;
						}
					}
				}
				break;
				case CheckLeft: {
					if (curr.getLeftChild() == null || curr.leftIsWire()) {
						if (DEBUG)
							System.out.println(" left: null");
						final TreeNode newLeftChild = new TreeNode(curr, newData);
						newLeftChild.setRightChild(curr, true);
						curr.setLeftChild(newLeftChild, false);
						step = WireCreationStep.Success;
						continue;
					}

					if (curr.getLeftChild() != null || !curr.leftIsWire()) {
						if (DEBUG)
							System.out.println(" left: not null");
						previous = curr;
						curr = curr.getLeftChild();
						step = WireCreationStep.CheckCurrent;
						continue;
					}
				}
				break;
				case CheckRight: {

					if (curr.getRightChild() == null || curr.rightIsWire()) {
						if (DEBUG)
							System.out.println("right: null or wire");
						TreeNode rightWire = null;
						if (curr.rightIsWire()) {
							if (DEBUG)
								System.out.println("right: wire");
							rightWire = curr.getRightChild();
						}
						if (curr.getRightChild() == null && previous != null) {
							if (DEBUG)
								System.out.println("right: null");
							rightWire = previous;
						}
						if (rightWire == null) {
							if (DEBUG)
								System.out.println("right: right is tree end");
						}
						final TreeNode newRightChild = new TreeNode(curr, newData);
						newRightChild.setRightChild(rightWire, true);
						curr.setRightChild(newRightChild, false);
						step = WireCreationStep.Success;
						continue;
					}

					if (curr.getRightChild() != null && !curr.rightIsWire()) {
						if (DEBUG)
							System.out.println("right: not null not wire");
						curr = curr.getRightChild();
						step = WireCreationStep.CheckCurrent;
						continue;

					}
					//
					// // test
					// final TreeMap<Integer, String> test = new TreeMap<>();
					// try (FileOutputStream fos = new
					// FileOutputStream("Personen.dat");
					// ObjectOutputStream oos = new ObjectOutputStream(fos);) {
					// oos.writeObject(test);
					// } catch (final FileNotFoundException e) {
					// // TODO Auto-generated catch block
					// e.printStackTrace();
					// } catch (final IOException e) {
					// // TODO Auto-generated catch block
					// e.printStackTrace();
					// }
				}
				break;
				case Success: {
					if (DEBUG)
						System.out.println("added: " + newData);
					searchInsertPos = false;

				}
				break;
				case Failed: {
					if (DEBUG)
						System.out.println("not added: " + newData);
					searchInsertPos = false;
				}
				break;
				default:
					break;

			}

		}

		// generateInOrderWire();
	}

	/**
	 * @param maxNodes          maximum possible nodes (if random range is smaller than
	 *                          maxNodes and no duplicates allowed: maximum = (max - min + 1))
	 * @param min               smallest possible number to be generated (inclusive)
	 * @param max               biggest possible number to be generated (inclusive)
	 * @param duplicatesAllowed numbers can occur multiple times in the tree (can reduce tree
	 *                          size, see maxNodes)
	 */
	public void generateRandomTree(final int maxNodes, final int min, final int max, final boolean duplicatesAllowed) {
		final Random rand = new Random();

		for (int i = 0; i < maxNodes; ++i) {
			insert(min + rand.nextInt(max - min + 1), duplicatesAllowed);
		}
	}

	public void delete(final int data, final boolean all) {
		while (true) {
			final TreeNode delNode = search(data);
			if (delNode == null) {
				break;
			}
			delete(delNode);
			generateInOrderWire();
			if (!all) {
				break;
			}
		}
		generateInOrderWire();
	}

	public void delete(final TreeNode delNode) {
		if (delNode == null) {
			throw new IllegalArgumentException("delNode must not be null");
		}

		// 1. case: both children of delNode are null
		if (delNode.getLeftChild() == null && (delNode.getRightChild() == null || delNode.rightIsWire())) {

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
		if (delNode.getLeftChild() == null ^ (delNode.getRightChild() == null || delNode.rightIsWire())) {
			System.out.println("1 child");
			// is delNode the current root of the tree
			if (delNode == root) {
				if (delNode.getLeftChild() == null) {
					root = delNode.getRightChild();
				} else {
					root = delNode.getLeftChild();
				}
			}
			// the TreeNode is not the root and has a parent
			else {
				if (delNode.getLeftChild() == null) {
					final TreeNode child = delNode.rightIsWire() ? null : delNode.getRightChild();
					updateChildrenOfChildrenParent(delNode, child);
				} else {
					// 1. follow left in order and find the Node where
					// rightChild == delNode

					final TreeNode wireOfDelNode = delNode.getRightChild();

					TreeNode current = delNode;
					TreeNode previous = delNode.getParent();

					do {
						System.out.println("1x 1. debug " + current.getData());
						while (current.getLeftChild() != null) {
							current = current.getLeftChild();
						}
						System.out.println("1x 2. debug " + current.getData());
						// previous = current;
						// current = current.getRightChild();
						while (current.getRightChild() != delNode) {

							System.out.println("1x 3. debug " + current.getData());
							previous = current;
							current = current.getRightChild();
						}
						System.out.println("1x 4. debug " + current.getData());
						if (current.getRightChild() == delNode) {
							break;
						}

					} while (true);
					current.setRightChild(wireOfDelNode, true);
					updateChildrenOfChildrenParent(delNode, delNode.getLeftChild());

				}
			}
			return;
		}

		// last case: two children exist
		System.out.println("2 children");

		TreeNode current = delNode.getRightChild();

		System.out.println("2x 1. debug " + current.getData());
		while (current.getLeftChild() != null) {
			current = current.getLeftChild();
		}
		System.out.println("2x 2. debug " + current.getData());

		// replace date with the smallest of the right partial tree
		delNode.setData(current.getData());

		// delete the node the data was taken from
		delete(current);
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

	public void generateInOrderWire() {
		if (root == null) {
			return;
		}

		final List<TreeNode> parents = new ArrayList<TreeNode>();

		TreeNode curr = root;
		WireCreationStep currStep = WireCreationStep.CheckLeft;
		int cnt = 0;
		do {
			switch (currStep) {
				case CheckLeft: {
					// find the smallest one child on the left side
					if (curr != null && curr.getLeftChild() != null) {
						if (DEBUG)
							System.out.println(curr.getData() + " - left child exists");
						// store current node as parent
						parents.add(curr);
						// go left
						curr = curr.getLeftChild();
					} else {
						currStep = WireCreationStep.CheckRight;
					}
				}
				break;
				case CheckRight: {
					if (DEBUG)
						System.out.println(curr.getData() + " - current node");
					// was there no child found and there are also no right children
					if (curr == root && (curr.getRightChild() == null || curr.rightIsWire())) {
						curr.setRightChild(null, false);
						if (DEBUG)
							System.out.println(curr.getData() + " - is root -> return");
						return;
					}

					// smallest child found
					if ((curr.getRightChild() == null || curr.rightIsWire()) && parents.size() > 0) {
						// wire to parent
						currStep = WireCreationStep.GoToParent;
					} else {
						// go to right child
						curr = curr.getRightChild();
						currStep = WireCreationStep.CheckLeft;
					}
				}
				break;
				case GoToParent: {
					// there is no right child: wire to the most recent parent
					curr.setRightChild(parents.get(parents.size() - 1), true);
					// remove the most recent parent from the list
					parents.remove(parents.size() - 1);

					// follow the new wire to the linked parent
					curr = curr.getRightChild();
					if (DEBUG)
						System.out.println(curr.getData() + " - current node via wire reached");
					// perform check for right child
					currStep = WireCreationStep.CheckRight;

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
		if (root == null) {
			return;
		}

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
		System.out.println("Number of elements = " + cnt);
	}

	public int getHeigth() {
		return getHeight(root);
	}

	private int getHeight(final TreeNode x) {
		if (x == null)
			return 0;
		return Math.max((x.leftIsWire() || x.getLeftChild() == null) ? 0 : getHeight(x.getLeftChild()),
				(x.rightIsWire() || x.getRightChild() == null) ? 0 : getHeight(x.getRightChild())) + 1;
	}

}