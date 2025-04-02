package ru.cooper.cryptanalyzer.controllers.ui.helpers;

import javafx.application.Platform;
import javafx.scene.control.Alert;

/**
 * Утилита для отображения диалоговых окон с сообщениями.
 * Гарантирует безопасный вызов из любого потока (использует {@link Platform#runLater(Runnable)}
 */
public class AlertHelper {

    /**
     * Показывает информационное диалоговое окно.
     *
     * @param title   заголовок окна
     * @param content текст сообщения (поддерживает переносы строк через \n)
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
