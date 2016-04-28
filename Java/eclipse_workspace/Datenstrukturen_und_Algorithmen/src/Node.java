
public class Node {
	private int content;

	private Node head;
	private Node tail;

	public int getContent() {
		return content;
	}

	public void setContent(int content) {
		this.content = content;
	}

	public Node getHead() {
		return head;
	}

	public void setHead(Node head) {
		this.head = head;
		checkPointer();
	}

	public Node getTail() {
		return tail;
	}

	public void setTail(Node tail) {
		this.tail = tail;
		checkPointer();
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
		content = value;
		this.previous = previous;
		this.next = next;
		checkPointer();
	}

	private void checkPointer() {
		if (previous == null) {
			head = this;
			if (next != null) {
				next.setPrevious(this);
				next.setHead(this);
			}
		} else {
			head = previous.getHead();
			if (next != null) {
				if (!next.getPrevious().equals(this))
					next.setPrevious(this);
				if (!next.getHead().equals(head))
					next.setPrevious(tail);
			}
		}

		if (next == null) {
			tail = this;
			if (previous != null) {
				previous.setNext(this);
				previous.setTail(this);
			}
		} else {
			tail = next.getTail();
			if (previous != null) {
				if (!previous.getNext().equals(this))
					previous.setNext(this);
				if (!previous.getTail().equals(tail))
					previous.setTail(tail);
			}
		}

	}
}
