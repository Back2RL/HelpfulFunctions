
public class VerkettungVonNodes {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Node first = new Node(1, null, null);
		Node second = new Node(2, first, null);
		Node third = new Node(3, second, null);

		Node curr = first;

		System.out.println(curr);
		while (curr != null) {
			System.out.println(curr.getContent());
			curr = curr.getNext();
			System.out.println(curr);
		}
		System.out.println("finished");
	}
}
