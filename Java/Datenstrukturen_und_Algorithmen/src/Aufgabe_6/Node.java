package Aufgabe_6;

/**
 * @author oertelt, opitzju * 14a)
 */
public class Node {
	// link to the next nodeobject
	private Node pointer;

	// contentrepresentation of the node
	private int value;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Node getPointer() {
		return pointer;
	}

	public void setPointer(Node pointer) {
		this.pointer = pointer;
	}

	public Node(int value) {
		setValue(value);
		setPointer(null);
	}

	public Node() {
		setValue(0);
		setPointer(null);
	}

	public Node(int value, Node pointer) {
		setValue(value);
		setPointer(pointer);
	}

	/**
	 * copy contructor (copies only the value, not the links)
	 * 
	 * @param other
	 */
	public Node(Node other) {
		setValue(value);
	}
}
