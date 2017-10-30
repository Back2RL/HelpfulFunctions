package statePattern;

public class Door{

	private DoorState state;
	
	public Door(DoorState state) {
		super();
		this.state = state;
	}

	
	public void abschließen() {
		state = state.abschließen();
	}

	public void aufschließen() {
		state = state.aufschließen();
	}

	public void oeffnen() {
		state = state.oeffnen();
	}

	public void schließen() {
		state = state.schließen();
	}

}
