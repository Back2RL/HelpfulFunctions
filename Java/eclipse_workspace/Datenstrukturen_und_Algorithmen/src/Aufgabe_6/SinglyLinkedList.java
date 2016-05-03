package Aufgabe_6;

public class SinglyLinkedList {
	private static final boolean WITH_CHECK = false;

	private Node head;
	private Node tail;

	public SinglyLinkedList() {
		head = new Node();
		tail = new Node();
		head.setPointer(tail);
		tail.setPointer(head);
	}

	// b
	public Node get(int value) {
		Node currSelected = head.getPointer();
		while (!currSelected.equals(tail) && currSelected.getValue() != value) {
			currSelected = currSelected.getPointer();
		}
		if (currSelected.equals(tail)) {
			return null;
		}
		return currSelected;
	}

	// c
	public static void insert(Node x, Node p, SinglyLinkedList L) {
		Node previous = L.head;
		while (!previous.equals(p) && !previous.equals(L.tail)) {
			previous = previous.getPointer();
		}
		if (previous.equals(L.tail)) {
			throw new IllegalArgumentException("Node is not part of List");
		}

		L.insert(x, previous);
	}

	// c
	public static void insertBefore(Node x, Node p, SinglyLinkedList L) {
		L.insert(x, p);
	}

	public void insert(Node x, Node p) {
		if (WITH_CHECK) {
			if (!contains(p))
				throw new IllegalArgumentException("Node is not entry of List");
		}

		x.setPointer(p.getPointer());
		p.setPointer(x);
		// tail.pointer umbiegen falls x neues letztes Element ist
		if (x.getPointer().equals(tail)) {
			tail.setPointer(x);
		}
	}

	public boolean contains(Node p) {
		Node currSelected = head.getPointer();
		while (!currSelected.equals(tail) && currSelected.equals(p)) {
			currSelected = currSelected.getPointer();
		}
		if (currSelected.equals(tail)) {
			return false;
		}
		return true;
	}

	// d
	public static SinglyLinkedList generateRandomListOfLength(int length) {
		SinglyLinkedList result = new SinglyLinkedList();
		for (int i = 0; i < length; ++i) {
			Node newEntry = new Node(1 + (int) (Math.random() * 100));
			result.insert(newEntry, result.head);
		}
		return result;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		Node currSelected = head.getPointer();
		if (!currSelected.equals(tail)) {
			sb.append(currSelected.getValue());
		}
		while (!currSelected.equals(tail)) {
			sb.append(", " + currSelected.getValue());
			currSelected = currSelected.getPointer();
		}
		sb.append("]");
		return sb.toString();
	}
}
