package JavaFX;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Created by Leo on 29.06.2016.
 */
public class Main extends Application {

    Button button;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                Stage window = primaryStage;
                window.setTitle("Titel");

                final ProgressBar bar = new ProgressBar();

                button = new Button("Button");
                button.setOnAction(event -> {

                    new Thread() {
                        @Override
                        public void run() {

                            try {
                                sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            int cnt = 0;
                            for (int i = 1; i <= 100; i++) {
                                try {
                                    sleep(20);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                cnt += 1;
                                final int counter = cnt;
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        bar.setProgress(counter / 100.0);
                                    }
                                });

                            }
                        }
                    }.start();

                });




                StackPane layout = new StackPane();
                layout.getChildren().addAll(button, bar);


                Scene scene = new Scene(layout, 300, 250);

                window.setScene(scene);
                window.show();

            }
        });
    }
}
