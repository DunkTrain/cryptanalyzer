package ru.cooper.cryptanalyzer.controllers.ui.helpers;

import javafx.application.Platform;
import javafx.scene.control.Alert;

/**
 * A utility class for displaying alert dialog windows.
 * <p>
 * Ensures thread-safe invocation by using {@link Platform#runLater(Runnable)}.
 */
public class AlertHelper {

    /**
     * Displays an informational alert dialog with the given title and message content.
     * <p>
     * This method is safe to call from any thread.
     *
     * @param title   the title of the alert dialog
     * @param content the content text of the alert, supports line breaks via {@code \n}
     */
    public void showAlert(String title, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }
}
