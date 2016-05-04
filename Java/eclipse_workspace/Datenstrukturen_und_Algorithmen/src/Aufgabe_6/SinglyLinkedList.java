package Aufgabe_6;

public class SinglyLinkedList {
	private static final boolean CHECK_ELEMENT_EXISTENCE = false;

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

	public void insertBefore(Node x, Node next) {
		Node previous = getPrevious(next);
		if (previous == null) {
			throw new IllegalArgumentException("Node is not part of List");
		}
		insert(x, previous);
	}

	public void insert(Node x, Node previous) {
		if (previous == null || x == null) {
			throw new IllegalArgumentException("Nodes must not be null");
		}
		if (CHECK_ELEMENT_EXISTENCE) {
			if (!contains(previous)) {
				System.err.println("Node with value " + previous.getValue() + " is not entry of List");
				throw new IllegalArgumentException();
			}
		}
		x.setPointer(previous.getPointer());
		previous.setPointer(x);
		// tail.pointer umbiegen falls x neues letztes Element ist
		if (x.getPointer().equals(tail)) {
			tail.setPointer(x);
		}
	}

	public boolean contains(int value) {
		if (get(value) == null) {
			return false;
		}
		return true;
	}

	public boolean contains(Node x) {
		Node currSelected = head;
		while (!currSelected.equals(tail) && !currSelected.equals(x)) {
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
		Node added = new Node(value);
		tail.getPointer().setPointer(added);
		added.setPointer(tail);
		tail.setPointer(added);
	}

	public void delete(int value) {
		Node x = get(value);
		if (x == null) {
			System.err.println(value + " not entry of list");
			return;
		}
		delete(x);
	}

	public void delete(Node x) {
		Node next = x.getPointer();
		Node previous = getPrevious(x);
		previous.setPointer(next);
		if (next.equals(tail)) {
			next.setPointer(previous);
		}
	}

	/**
	 * @param x
	 * @return Node that links to x or null if x not element of list
	 */
	public Node getPrevious(Node x) {
		Node curr = head;
		// gehe Liste durch und prüfe ob das nächste Element x ist
		while (!curr.getPointer().equals(x) && !curr.getPointer().equals(tail)) {
			curr = curr.getPointer();
		}
		// ist das gefundene Element das Tail Element dann ist x nicht in der
		// Liste enthalten
		// return null
		if (curr.getPointer().equals(tail)) {
			return null;
		}
		return curr;
	}
}
