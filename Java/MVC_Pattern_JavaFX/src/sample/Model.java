package sample;

import java.util.Observable;

public class Model extends Observable {
	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
		setChanged();
		notifyObservers();
	}

	private String message;
}
