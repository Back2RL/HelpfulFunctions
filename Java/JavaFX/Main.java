import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    private Button button;
    private boolean firstTime;
    private TrayIcon trayIcon;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        createTrayIcon(primaryStage);
        firstTime = true;
        Platform.setImplicitExit(false);
        Platform.runLater(() -> {
            primaryStage.setTitle("Titel");

            final ProgressBar bar = new ProgressBar();

            button = new Button("Button");
            button.setOnAction(event -> new Thread(() -> {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int cnt = 0;
                for (int i = 1; i <= 100; i++) {
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    cnt += 1;
                    final int counter = cnt;
                    Platform.runLater(() -> bar.setProgress(counter / 100.0));

                }
            }).start());

            VBox layout = new VBox();
            layout.getChildren().addAll(button, bar);

            Scene scene = new Scene(layout, 300, 250);

            primaryStage.setScene(scene);
            primaryStage.show();

        });
    }

    private void createTrayIcon(final Stage stage) {
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            java.awt.Image image;

            stage.setOnCloseRequest(o -> hide(stage));

            final ActionListener closeListener = e -> System.exit(0);

            ActionListener showListener = e -> Platform.runLater(stage::show);
            PopupMenu popup = new PopupMenu();

            MenuItem showItem = new MenuItem("Show");
            showItem.addActionListener(showListener);
            popup.add(showItem);

            MenuItem closeItem = new MenuItem("Close");
            closeItem.addActionListener(closeListener);
            popup.add(closeItem);

            try {
                URL url = new URL("http://www.digitalphotoartistry.com/rose1.jpg");
                image = ImageIO.read(url);
                trayIcon = new TrayIcon(image, "Title", popup);
            } catch (IOException e) {
                e.printStackTrace();
            }

            trayIcon.addActionListener(showListener);

            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }
    }

    private void showProgramIsMinimizedMsg() {
        if (firstTime) {
            trayIcon.displayMessage("Some message.", "Some other message.", TrayIcon.MessageType.INFO);
            firstTime = false;
        }
    }

    private void hide(final Stage stage) {
        Platform.runLater(() -> {
            if (SystemTray.isSupported()) {
                stage.hide();
                showProgramIsMinimizedMsg();
            } else {
                System.exit(0);
            }
        });
    }
}
