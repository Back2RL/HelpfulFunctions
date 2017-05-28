package Aufgabe_8;

public class TreeNode {

	private int data;
	private TreeNode parent;
	private TreeNode leftChild;
	private TreeNode rightChild;
	/**
	 * stores information whether left-/rightchild are Wires or childnodes used
	 * instead of 2 seperate booleans
	 */
	private byte WireInformation;
	private static final byte leftWireMask = 1 << 2;
	private static final byte rightWireMask = 1;

	public boolean leftIsWire() {
		return (WireInformation & leftWireMask) == leftWireMask;
	}

	public boolean rightIsWire() {
		return (WireInformation & rightWireMask) == rightWireMask;
	}

	public void setLeftIsWire(final boolean isWire) {
		if (isWire) {
			WireInformation |= leftWireMask;
		} else {
			WireInformation &= ~leftWireMask;
		}
	}

	public void setRightIsWire(final boolean isWire) {
		if (isWire) {
			WireInformation |= rightWireMask;
		} else {
			WireInformation &= ~rightWireMask;
		}
	}

	public TreeNode(final TreeNode parent, final int data) {
		setParent(parent);
		setData(data);
	}

	public TreeNode getParent() {
		return parent;
	}

	public void setParent(final TreeNode parent) {
		this.parent = parent;
	}

	public int getData() {
		return data;
	}

	public void setData(final int data) {
		this.data = data;
	}

	public TreeNode getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(final TreeNode leftChild, final boolean isWire) {
		setLeftIsWire(isWire);
		this.leftChild = leftChild;
	}

	public TreeNode getRightChild() {
		return rightChild;
	}

	public void setRightChild(final TreeNode rightChild, final boolean isWire) {
		setRightIsWire(isWire);
		this.rightChild = rightChild;
	}

}