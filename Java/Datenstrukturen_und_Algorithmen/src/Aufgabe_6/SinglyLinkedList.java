package Aufgabe_6;

/**
 * @author oertelt, opitzju * 14a)
 */
public class SinglyLinkedList {
	private static final boolean CHECK_ELEMENT_EXISTENCE = false;

	private Node head;
	private Node tail;

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

	public SinglyLinkedList() {
		head = new Node();
		tail = new Node();
		head.setPointer(tail);
		tail.setPointer(head);
	}

	/**
	 * 14b) find the first nodeobject that has the the value
	 * 
	 * @param value
	 *            data to look for
	 * @return the found node or null if there is none
	 */
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

	/**
	 * 14c) static method to insert a node object after a given node in a given
	 * list
	 * 
	 * @param x
	 *            object to be inserted
	 * @param p
	 *            previous object after which the new node will be inserted
	 * @param L
	 *            list of nodes
	 * @throws IllegalArgumentException
	 *             if given previous node is not element of the list
	 */
	public static void insert(Node x, Node p, SinglyLinkedList L) {
		L.insert(x, p);
	}

	/**
	 * 14c) non-static insert method, inserts after a given nodeobject
	 * 
	 * @param x
	 *            object to be inserted
	 * @param previous
	 * @throws IllegalArgumentException
	 *             if given previous node is not element of the list
	 */
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

	/**
	 * 14e) inserts a nodeobject before a given node
	 * 
	 * @param x
	 * @param next
	 * @param L
	 * @throws IllegalArgumentException
	 *             if given next node is not element of the list
	 */
	public static void insertBefore(Node x, Node next, SinglyLinkedList L) {
		L.insertBefore(x, next);
	}

	/**
	 * 14e) inserts a nodeobject before a given node
	 * 
	 * @param x
	 * @param next
	 * @throws IllegalArgumentException
	 *             if given next node is not element of the list
	 */
	public void insertBefore(Node x, Node next) {
		Node previous = getPrevious(next);
		if (previous == null) {
			throw new IllegalArgumentException("Node is not part of List");
		}
		insert(x, previous);
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

	/**
	 * 14d) generates a random list using insert method (14c)
	 * 
	 * @param length
	 *            number of listelements to generate
	 * @param min
	 *            minimum value of a node
	 * @param max
	 *            maximum value of a node
	 * @return the list object
	 */
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

	/**
	 * 14g) concats a given list to the list, the given list will be empty
	 * afterwards
	 * 
	 * @param L
	 *            list that will be concated
	 */
	public void concat(SinglyLinkedList L) {
		tail.getPointer().setPointer(L.head.getPointer());
		tail = L.getTail();
		L.head.setPointer(L.tail);
		L.tail.setPointer(L.head);
	}

	/**
	 * 14h) expects both the lists to be sorted (ascending), the given list will
	 * be empty afterwards
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

	/**
	 * creates a new node that will be added at the end
	 * 
	 * @param value
	 */
	public void add(int value) {
		Node added = new Node(value);
		tail.getPointer().setPointer(added);
		added.setPointer(tail);
		tail.setPointer(added);
	}

	/**
	 * 14f) deletes first nodeobject with value
	 * 
	 * @param value
	 * @throws IllegalArgumentException
	 *             if node with value does not exist in the list or if the node
	 *             is not part of a valid list
	 */
	public void delete(int value) {
		Node x = get(value);
		if (x == null) {
			throw new IllegalArgumentException("Node is not part of List");
		}
		delete(x);
	}

	/**
	 * 14f) deletes a given node from a list
	 * 
	 * @param x
	 *            node to delete
	 * 
	 * @throws IllegalArgumentException
	 *             node is not part of a valid list
	 */
	public void delete(Node x) {
		Node next = x.getPointer();
		Node previous = getPrevious(x);
		if (previous == null || next == null) {
			throw new IllegalArgumentException("Node is not part of a list");
		}
		previous.setPointer(next);
		if (next.equals(tail)) {
			next.setPointer(previous);
		}
	}

	/**
	 * finds the previous nodeobject in the list
	 * 
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
