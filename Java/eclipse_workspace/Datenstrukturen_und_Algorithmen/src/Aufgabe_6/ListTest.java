package Aufgabe_6;

public class ListTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SinglyLinkedList liste = SinglyLinkedList.generateRandomListOfLength(10, 0, 10);
		SinglyLinkedList liste2 = SinglyLinkedList.generateRandomListOfLength(10, 0, 10);
		// System.out.println(liste);

		Node entry = liste.get(5);
		Node notExisting = new Node(9999);

		// System.out.println(entry);
		liste.insert(new Node(1000), entry);
		// System.out.println(liste);

		liste.insertBefore(new Node(5555), entry);
		System.out.println(liste);
		liste.concat(liste2);
		System.out.println(liste);

		SinglyLinkedList sorted1 = new SinglyLinkedList();
		sorted1.add(1);
		sorted1.add(3);
		sorted1.add(5);
		System.out.println(sorted1);
		SinglyLinkedList sorted2 = new SinglyLinkedList();
		sorted2.add(2);
		sorted2.add(4);
		sorted2.add(6);
		System.out.println(sorted2);
		sorted1.merge(sorted2);
		System.out.println("merged " + sorted1);

	}

}
