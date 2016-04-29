
public class LinkedList {
	private Node head;
	private Node tail;

	public Node getHead() {
		return head;
	}

	public Node getTail() {
		return tail;
	}

	/**
	 * @param head
	 */
	public LinkedList(Node head) {
		this.head = new Node(head.getContent(), null, null);
		this.tail = this.head;
	}

	public LinkedList() {
	}

	public LinkedList(LinkedList list) {
		this.head = list.getHead();
		this.tail = list.getTail();
	}

	public void add(int value) {
		if (head == null) {
			head = new Node(value, null, null);
			tail = head;
			return;
		}
		tail.setNext(new Node(value, tail, null));
		tail = tail.getNext();
	}

	@Override
	public String toString() {
		if (head == null)
			return "[]";
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(head.getContent());
		Node current = head.getNext();
		while (current != null && current.getNext() != null) {
			sb.append("," + current.getContent());
			current = current.getNext();
		}
		sb.append("]");
		return "LinkedList " + sb.toString();
	}

	public Node getElem(int value) {
		if (head == null)
			return null;
		Node current = head;
		while (current != null && current.getContentNoInkrement() != value) {
			current = current.getNext();
		}
		return current;
	}

}
