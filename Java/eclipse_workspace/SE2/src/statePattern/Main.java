package statePattern;

public class Main {

	public static void main(String[] args) {
		DoorState door = new Door_Open();
		door = door.schließen();
		door = door.abschließen();
		door = door.abschließen();
		door = door.aufschließen();
		door = door.oeffnen();
		System.out.println(door.getClass());
	}
}
