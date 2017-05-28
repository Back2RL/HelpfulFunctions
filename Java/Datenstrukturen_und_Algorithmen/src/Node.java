
public class Node {
	private int accessCount = 0;

	public int getAccessCount() {
		return accessCount;
	}

	private int content;

	public int getContent() {
		accessCount++;
		return content;
	}

	public int getContentNoInkrement() {
		return content;
	}

	public void setContent(int content) {
		this.content = content;
	}

	public Node getPrevious() {
		return previous;
	}

	public void setPrevious(Node previous) {
		this.previous = previous;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	private Node previous;
	private Node next;

	public boolean isHead() {
		return previous == null;
	}

	public boolean isTail() {
		return next == null;
	}

	public Node(int value, Node previous, Node next) {
		setContent(value);
		setPrevious(previous);
		setNext(next);
	}
}
