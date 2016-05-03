package Aufgabe_6;

public class SinglyLinkedList {
	private static final boolean WITH_CHECK = false;

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
		Node L1 = head;
		Node L2 = L.head.getPointer();
		while (true) {
			if ((L1.equals(head) || L1.getValue() <= L2.getValue())
					&& (L1.getPointer().getValue() >= L2.getValue() || L1.getPointer().equals(tail))) {
				Node temp = L2.getPointer();
				this.insert(L2, L1);
				L2 = temp;
				if (L2.equals(L.tail)) {
					break;
				}
			} else {
				if (!L1.equals(tail)) {
					L1 = L1.getPointer();
				} else {
					break;
				}
			}
		}
		L.head.setPointer(L.tail);
		L.tail.setPointer(L.head);
	}

	public void add(int value) {
		insertBefore(new Node(value), tail);
	}
}
