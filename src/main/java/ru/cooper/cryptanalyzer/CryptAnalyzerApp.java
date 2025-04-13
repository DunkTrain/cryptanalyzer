package ru.cooper.cryptanalyzer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ru.cooper.cryptanalyzer.util.UTF8Control;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Основной класс приложения "Криптоанализатор".
 * Запускает графический интерфейс пользователя (GUI) с использованием JavaFX.
 */
public class CryptAnalyzerApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Locale locale = Locale.forLanguageTag("ru");

            ResourceBundle bundle = ResourceBundle.getBundle(
                    "i18n.Messages",
                    locale,
                    new UTF8Control()
            );

            FXMLLoader loader = new FXMLLoader(
                    Objects.requireNonNull(getClass().getResource("/main-view.fxml")),
                    bundle
            );

            Parent root = loader.load();

            primaryStage.setTitle(bundle.getString("app.title"));

            primaryStage.setScene(new Scene(root, 1000, 800));

            Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icon.png")));
            primaryStage.getIcons().add(icon);

            primaryStage.show();

        } catch (IOException e) {
            System.err.println("Ошибка загрузки FXML файла: " + e.getMessage());
            showErrorAlert("Не удалось загрузить интерфейс приложения");
        } catch (Exception e) {
            System.err.println("Неожиданная ошибка: " + e.getMessage());
            e.printStackTrace();
            showErrorAlert("Произошла непредвиденная ошибка");
        }
    }

    /**
     * Отображает всплывающее окно с сообщением об ошибке.
     *
     * @param message Текст ошибки
     */
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Точка входа в приложение.
     *
     * @param args Аргументы командной строки
     */
    public static void main(String[] args) {
        launch(args);
    }
}
