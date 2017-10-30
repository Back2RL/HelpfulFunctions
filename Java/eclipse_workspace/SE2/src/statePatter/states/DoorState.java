package statePatter.states;

public abstract class DoorState {
	public DoorState abschließen() {
		return this;
	}

	public DoorState aufschließen() {
		return this;
	}

	public DoorState oeffnen() {
		return this;
	}

	public DoorState schließen() {
		return this;
	}
}
