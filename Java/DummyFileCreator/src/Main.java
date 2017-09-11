import gui.AutoSelectingTextField;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import logic.LogicController;

import java.io.File;

public class Main extends Application {


	@Override
	public void start(Stage primaryStage) throws Exception {
		VBox root = new VBox();

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setFitToHeight(true);
		scrollPane.setFitToWidth(true);

		HBox statusBar = new HBox();
		statusBar.setMinWidth(512);
		statusBar.setAlignment(Pos.BOTTOM_LEFT);

		Label status = new Label();
		status.setText("Idle");
		status.setAlignment(Pos.BOTTOM_LEFT);

		ProgressBar progressBar = new ProgressBar(0.5);
		progressBar.setVisible(false);


		Runnable myRunner = new Runnable() {
			@Override
			public void run() {
//				Float progress = new Float(0);
//
//				Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
//				for (int i = 0; i < 10; ++i) {
//					progress += 0.1f;
//					try {
//						Thread.sleep(1000);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//					progressBar.setProgress(progress);
//				}
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
						tfOrigDirPathInput.textProperty().addListener(new ChangeListener<String>() {
							@Override
							public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
								if (LogicController.getInstance().setOriginalsDir(new File(tfOrigDirPathInput.getText()))) {
									Platform.runLater(new Runnable() {
										@Override
										public void run() {
											status.setText("Valid Originals-Directory");
										}
									});
								} else {
									Platform.runLater(new Runnable() {
										@Override
										public void run() {
											status.setText("Invalid Originals-Directory");
										}
									});
								}
							}
						});
						tfOrigDirPathInput.setTooltip(new Tooltip("path to the directory of Original Files (Files with a real size)"));

						AutoSelectingTextField tfDummyDirPathInput = new AutoSelectingTextField();
						tfDummyDirPathInput.textProperty().addListener(new ChangeListener<String>() {
							@Override
							public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
								if (LogicController.getInstance().setDummiesDir(new File(tfDummyDirPathInput.getText()))) {
									Platform.runLater(new Runnable() {
										@Override
										public void run() {
											status.setText("Valid Dummies-Directory");
										}
									});
								} else {
									Platform.runLater(new Runnable() {
										@Override
										public void run() {
											status.setText("Invalid Dummies-Directory");
										}
									});
								}
							}
						});
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
										LogicController.getInstance().setOriginalsDir(dir);
										tfOrigDirPathInput.setText(dir.getAbsolutePath());
									}
								});

							}
						});

						Button btnChooseDummyDir = new Button("Select");
						btnChooseDummyDir.setMinWidth(100);
						btnChooseDummyDir.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(final ActionEvent event) {
								DirectoryChooser directoryChooser = new DirectoryChooser();
								directoryChooser.setTitle("Choose the Directory that shall be the Target for the Dummy-Files");

								Platform.runLater(new Runnable() {
									@Override
									public void run() {
										File dir = directoryChooser.showDialog(primaryStage);
										LogicController.getInstance().setDummiesDir(dir);
										tfDummyDirPathInput.setText(dir.getAbsolutePath());
									}
								});

							}
						});

						Button btnStartDummyCreation = new Button("Create Dummies");
						btnStartDummyCreation.setMinWidth(100);
						btnStartDummyCreation.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(final ActionEvent event) {
								new Thread(new Runnable() {
									@Override
									public void run() {
										if(LogicController.getInstance().createDummies()) {
											Platform.runLater(new Runnable() {
												@Override
												public void run() {
													status.setText("Dummy-Creation finished");
												}
											});
										}else{
											Platform.runLater(new Runnable() {
												@Override
												public void run() {
													status.setText("Dummy-Creation failed");
												}
											});
										}
									}
								}).start();
							}
						});

						gridpane.add(tfOrigDirPathInput, 0, 0);
						gridpane.add(tfDummyDirPathInput, 0, 1);
						gridpane.add(btnChooseOrigDir, 1, 0);
						gridpane.add(btnChooseDummyDir, 1, 1);
						gridpane.add(btnStartDummyCreation, 0, 2);
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
		primaryStage.setMinHeight(128);
		primaryStage.setMinWidth(512);
		primaryStage.setAlwaysOnTop(true);
		primaryStage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}
}
