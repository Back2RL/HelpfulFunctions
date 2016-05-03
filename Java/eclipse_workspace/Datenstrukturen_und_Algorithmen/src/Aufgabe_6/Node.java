package Aufgabe_6;

public class Node {
	private Node pointer;
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
	}

	public Node() {
		setValue(value);
	}

	public Node(Node other) {
		setValue(value);
		setPointer(other.pointer);
	}
}
