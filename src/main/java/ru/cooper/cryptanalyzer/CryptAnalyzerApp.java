package ru.cooper.cryptanalyzer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Entry point for the CryptAnalyzer application.
 * <p>
 * Launches the JavaFX-based graphical user interface (GUI)
 * by loading the main view from FXML.
 */
public class CryptAnalyzerApp extends Application {

    /**
     * Initializes and displays the primary stage of the application.
     * <p>
     * Loads the UI layout from {@code /main-view.fxml}, sets the window title,
     * icon, and dimensions. Displays error alerts in case of failures.
     *
     * @param primaryStage the primary stage for this application
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/main-view.fxml")));

            primaryStage.setTitle("Криптоанализатор");

            primaryStage.setScene(new Scene(root, 1000, 800));

            Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icon.png")));
            primaryStage.getIcons().add(icon);

            primaryStage.show();

        } catch (IOException e) {
            System.err.println("Ошибка загрузки FXML файла: " + e.getMessage());
            showErrorAlert("Не удалось загрузить интерфейс приложения");
        } catch (Exception e) {
            System.err.println("Неожиданная ошибка: " + e.getMessage());
            showErrorAlert("Произошла непредвиденная ошибка");
        }
    }


    /**
     * Displays a modal alert dialog with an error message.
     *
     * @param message the error message to display
     */
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Launches the application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
