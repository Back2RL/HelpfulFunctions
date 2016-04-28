
public class VerkettungVonNodes {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Node first = new Node(1, null, null);
		Node second = new Node(2, first, null);
		Node third = new Node(3, second, null);
		third.setNext(new Node(5, null, null));

		Node curr = first.getHead();

		while (curr != null && !(curr.getContentNoInkrement() == 5)) {
			System.out.println(curr.getContent());

			System.out.println(curr);
			System.out.println("AC = " + curr.getAccessCount());
			curr = curr.getNext();
		}
		System.out.println("-----");
		curr = first;

		while (curr != null) {
			System.out.println(curr.getContent());

			System.out.println(curr);
			System.out.println("AC = " + curr.getAccessCount());
			curr = curr.getNext();
		}
		System.out.println("-----");
		curr = first;

		while (curr != null) {
			System.out.println(curr.getContent());

			System.out.println(curr);
			System.out.println("AC = " + curr.getAccessCount());
			curr = curr.getNext();
		}
		System.out.println("-----");
		curr = first;

		while (curr != null) {
			System.out.println(curr.getContent());

			System.out.println(curr);
			System.out.println("AC = " + curr.getAccessCount());
			curr = curr.getNext();
		}
		System.out.println("finished");

	}
}
