package statePattern;

public class Door_Open extends DoorState {

	@Override
	public DoorState schließen() {
		super.schließen();
		System.out.println("Door_Open: schließen");
		return new Door_Closed();
	}

}
