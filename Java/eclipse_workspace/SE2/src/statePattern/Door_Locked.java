package statePattern;

public class Door_Locked extends DoorState {

	@Override
	public DoorState aufschließen() {
		super.aufschließen();
		System.out.println("Door_Locked: aufschließen");
		return new Door_Closed();
	}

	@Override
	public DoorState abschließen() {
		System.out.println("Door_Locked: abschließen");
		System.out.println("Die Tuer ist bereits abgeschlossen und kann kein zweites Mal abgeschlossen werden.");
		return super.abschließen();
	}

}
