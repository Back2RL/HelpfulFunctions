package statePattern;

public class Door_Closed extends DoorState {

	@Override
	public DoorState abschließen() {
		super.abschließen();
		System.out.println("Door_Closed: abschließen");
		return new Door_Locked();
	}

	@Override
	public DoorState oeffnen() {
		super.oeffnen();
		System.out.println("Door_Closed: öffnen");
		return new Door_Open();
	}

}
