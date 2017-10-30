package statePattern;

public class Main {

	public static void main(String[] args) {
		Door door = new Door(new Door_Open());
		door.schließen();
		door.abschließen();
		door.abschließen();
		door.aufschließen();
		door.oeffnen();
	}
}
