package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {


	@Override
	public void start(Stage primaryStage) throws Exception {
		VBox root = new VBox();

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setFitToHeight(true);
		scrollPane.setFitToWidth(true);

		HBox statusBar = new HBox();
		statusBar.setAlignment(Pos.BOTTOM_LEFT);

		Label status = new Label();
		status.setText("Idle");
		status.setAlignment(Pos.BOTTOM_LEFT);

		ProgressBar progressBar = new ProgressBar(0.5);


		Runnable myRunner = new Runnable() {
			@Override
			public void run() {
				Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Platform.runLater(new Runnable() {
					@Override
					public void run() {

						GridPane gridpane = new GridPane();
						gridpane.setAlignment(Pos.TOP_LEFT);
						ColumnConstraints column1 = new ColumnConstraints();
						column1.setPercentWidth(80);
						//ColumnConstraints column2 = new ColumnConstraints();
						//column2.setPercentWidth(20);
						gridpane.getColumnConstraints().add(column1);


						AutoSelectingTextField tfOrigDirPathInput = new AutoSelectingTextField();
						tfOrigDirPathInput.setTooltip(new Tooltip("path to the directory of Original Files (Files with a real size"));

						AutoSelectingTextField tfDummyDirPathInput = new AutoSelectingTextField();
						tfDummyDirPathInput.setTooltip(new Tooltip("path to the directory where the created Dummy-Files will be placed (empty Files)"));

						Button btnChooseOrigDir = new Button("Select");
						btnChooseOrigDir.setMinWidth(100);
						btnChooseOrigDir.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(final ActionEvent event) {
								DirectoryChooser directoryChooser = new DirectoryChooser();
								directoryChooser.setTitle("Choose the Directory that contains the original Files");


								Platform.runLater(new Runnable() {
									@Override
									public void run() {
										File dir = directoryChooser.showDialog(primaryStage);
										if (dir != null) {
											tfOrigDirPathInput.setText(dir.getAbsolutePath());
										}
									}
								});

							}
						});

						Button btnChooseDummyDir = new Button("Select");
						btnChooseDummyDir.setMinWidth(100);

						gridpane.add(tfOrigDirPathInput, 0, 0);
						gridpane.add(tfDummyDirPathInput, 0, 1);
						gridpane.add(btnChooseOrigDir, 1, 0); // column=1 row=0
						gridpane.add(btnChooseDummyDir, 1, 1);  // column=2 row=0
						scrollPane.setContent(gridpane);

					}
				});
			}
		};
		new Thread(myRunner).start();


		statusBar.getChildren().addAll(status, progressBar);
		root.getChildren().addAll(scrollPane, statusBar);
		VBox.setVgrow(scrollPane, Priority.ALWAYS);

		Scene scene = new Scene(root);
		primaryStage.setTitle("DummyFileCreator");
		primaryStage.setScene(scene);
		primaryStage.setMinHeight(100);
		primaryStage.setMinWidth(100);
		primaryStage.setAlwaysOnTop(true);
		primaryStage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}
}
