package Aufgabe_6;

public class DSA_1276156_06 {

	public static void main(String[] args) {
		System.out.println("generating a random lists:");
		SinglyLinkedList liste = SinglyLinkedList.generateRandomListOfLength(10, 0, 10);
		System.out.println(liste);

		// find 1st Node
		int valueToFind = 5;
		Node entry = liste.get(valueToFind);
		if (entry == null) {

			System.out.println(valueToFind + " not found.");
		} else {
			System.out.print("Found Nodeobject with value = ");
			System.out.println(valueToFind + ": " + entry);

			System.out.println("\ninserting new node 1000 after 5");
			liste.insert(new Node(1000), entry);
			System.out.println(liste);

			System.out.println("\ninserting new node 5555 before 5");
			liste.insertBefore(new Node(5555), entry);
			System.out.println(liste);
		}

		System.out.println("\nconcating lists 1:");
		System.out.println(liste);
		System.out.println("and 2:");
		SinglyLinkedList liste2 = SinglyLinkedList.generateRandomListOfLength(10, 0, 10);
		System.out.println(liste2);
		System.out.println("result:");
		liste.concat(liste2);
		System.out.println(liste);
		System.out.println(liste2);

		System.out.println("\n2 sorted lists");
		SinglyLinkedList sorted1 = new SinglyLinkedList();
		sorted1.add(1);
		sorted1.add(1);
		sorted1.add(2);
		sorted1.add(100);
		sorted1.add(200);
		sorted1.add(300);
		System.out.println(sorted1);
		SinglyLinkedList sorted2 = new SinglyLinkedList();
		sorted2.add(2);
		sorted2.add(4);
		sorted2.add(8);
		sorted2.add(10);
		System.out.println(sorted2);

		sorted2.merge(sorted1);
		System.out.println("after merging:");
		System.out.println("list 1 = " + sorted1);
		System.out.println("list 2 = " + sorted2);

		System.out.println("\ndelete node with value = 8");
		Node is8 = sorted2.get(8);
		sorted2.delete(is8);
		System.out.println(sorted2);
		System.out.println("delete 200 and 300");
		sorted2.delete(200);
		sorted2.delete(300);
		System.out.println(sorted2);

	}

}
