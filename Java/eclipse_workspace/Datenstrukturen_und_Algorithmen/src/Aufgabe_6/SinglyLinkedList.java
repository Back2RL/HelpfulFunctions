package Aufgabe_6;

public class SinglyLinkedList {
	private static final boolean WITH_CHECK = true;

	private Node head;

	public Node getHead() {
		return head;
	}

	public void setHead(Node head) {
		this.head = head;
	}

	public Node getTail() {
		return tail;
	}

	public void setTail(Node tail) {
		this.tail = tail;
	}

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

		L.insert(x, p);
	}

	// c
	public static void insertBefore(Node x, Node p, SinglyLinkedList L) {
		L.insertBefore(x, p);
	}

	public void insertBefore(Node x, Node p) {
		Node previous = head;
		while (!previous.getPointer().equals(p) && !previous.getPointer().equals(tail)) {
			previous = previous.getPointer();
		}
		if (previous.equals(tail)) {
			throw new IllegalArgumentException("Node is not part of List");
		}
		insert(x, previous);
	}

	public void insert(Node x, Node p) {
		if (p == null || x == null) {
			throw new IllegalArgumentException("Nodes must not be null");
		}

		if (WITH_CHECK) {
			if (!contains(p)) {
				System.err.println("Node with value " + p.getValue() + " is not entry of List");
				throw new IllegalArgumentException();
			}
		}

		x.setPointer(p.getPointer());
		p.setPointer(x);

		// tail.pointer umbiegen falls x neues letztes Element ist
		if (x.getPointer().equals(tail)) {
			tail.setPointer(x);
		}
	}

	public boolean contains(Node p) {
		Node currSelected = head;
		while (!currSelected.equals(tail) && !currSelected.equals(p)) {
			currSelected = currSelected.getPointer();
		}
		if (currSelected.equals(tail)) {
			return false;
		}
		return true;
	}

	// d
	public static SinglyLinkedList generateRandomListOfLength(int length, int min, int max) {
		SinglyLinkedList result = new SinglyLinkedList();
		for (int i = 0; i < length; ++i) {
			Node newEntry = new Node(min + (int) (Math.random() * max));
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
			currSelected = currSelected.getPointer();
		}
		while (!currSelected.equals(tail)) {
			sb.append(", " + currSelected.getValue());
			currSelected = currSelected.getPointer();
		}
		sb.append("]");
		return sb.toString();
	}

	public void concat(SinglyLinkedList L) {
		tail.getPointer().setPointer(L.head.getPointer());
		tail = L.getTail();
	}

	/**
	 * expects both the list the method called on as well as the second list to
	 * be sorted
	 * 
	 * @param L
	 */
	public void merge(SinglyLinkedList L) {
		Node currL1 = head;
		Node currL2 = L.head;
		while (true) {
			System.out.println(this);
			System.out.println(currL1.getValue() + " / " + currL2.getValue());
			if (currL1.getValue() < currL2.getValue()) {
				insertBefore(new Node(currL2), currL1.getPointer());
				currL1 = currL1.getPointer();
			}
			if (currL1.getValue() == currL2.getValue()) {
				currL2 = currL2.getPointer();
				currL1 = currL1.getPointer();
			}
			if (currL1.getValue() > currL2.getValue()) {
				insertBefore(new Node(currL2), currL1);
				if (currL1.equals(tail)) {
					tail.getPointer().setPointer(L.head.getPointer());
					break;
				}
			}
		}

	}

	public void add(int value) {
		insertBefore(new Node(value), tail);
	}
}
