package sample;


import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.Observable;
import java.util.Observer;

public class View extends VBox{

	private Controller controller = new Controller();
	private Model model = controller.getModel();

	public View() {
		super();
		setAlignment(Pos.CENTER);
		Button actionButton = new Button("Action");

		actionButton.setOnAction(event -> controller.action());

		class listeningTextField extends TextField implements Observer{
			@Override
			public void update(final Observable o, final Object arg) {
					Platform.runLater(() -> setText(model.getMessage()));
			}
		}
		listeningTextField responseText = new listeningTextField();
		model.addObserver(responseText);


		getChildren().addAll(actionButton,responseText);
	}
}
