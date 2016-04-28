
public class Node {
	private int content;

	private Node head;
	private Node tail;

	public int getContent() {
		return content;
	}

	public void setContent(int content) {
		this.content = content;
		checkPointer();
	}

	public Node getHead() {
		return head;
	}

	private void setHead(Node head) {
		this.head = head;

		if (previous != null && (previous.getHead() == null || !previous.getHead().equals(tail))) {
			previous.setHead(head);
		}
		if (next != null && (next.getHead() == null || !next.getHead().equals(head))) {
			next.setHead(head);
		}

	}

	public Node getTail() {
		return tail;
	}

	private void setTail(Node tail) {
		this.tail = tail;

		if (previous != null && (previous.getTail() == null || !previous.getTail().equals(tail))) {
			previous.setTail(head);
		}
		if (next != null && (next.getTail() == null || !next.getTail().equals(tail))) {
			next.setTail(tail);
		}
	}

	public Node getPrevious() {
		return previous;
	}

	public void setPrevious(Node previous) {
		this.previous = previous;
		checkPointer();
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
		checkPointer();
	}

	private Node previous;
	private Node next;

	public Node(int value, Node previous, Node next) {
		setContent(value);
		setPrevious(previous);
		setNext(next);
	}

	private void checkPointer() {
		if (previous == null) {
			head = this;
		}

		if (next == null) {
			tail = this;
		}

		if (previous != null && (previous.getNext() == null || !previous.getNext().equals(this))) {
			previous.setNext(this);
		}

		if (next != null && (next.getPrevious() == null || !next.getPrevious().equals(this))) {
			next.setPrevious(this);
		}

	}
}
