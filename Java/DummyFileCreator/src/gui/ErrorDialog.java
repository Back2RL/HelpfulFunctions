package gui;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ErrorDialog extends Alert {
    public ErrorDialog(AlertType alertType, Exception exception, String title, String headerMessage) {
        super(alertType);

        // Get the Stage.
        Stage stage = (Stage) getDialogPane().getScene().getWindow();
        // Add a custom icon.
        // stage.getIcons().add(new Image(new BufferedInputStream(getClass().getResourceAsStream("warning.png"))));
        try {
            stage.getIcons().add(new Image(ErrorDialog.class.getResource("/res/warning.png").toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle(title);
        setHeaderText(headerMessage);
        setContentText(exception.getMessage());

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);
        // Set expandable Exception into the dialog pane.
        getDialogPane().setExpandableContent(expContent);
        showAndWait();
    }

}
