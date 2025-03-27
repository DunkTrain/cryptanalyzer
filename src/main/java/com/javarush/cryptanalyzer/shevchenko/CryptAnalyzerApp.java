package com.javarush.cryptanalyzer.shevchenko;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.javarush.cryptanalyzer.shevchenko.view.ProgramMenu.presentProgramMenu;

//import static com.javarush.cryptanalyzer.shevchenko.view.ProgramMenu.presentProgramMenu;

public class CryptAnalyzerApp /*extends Application*/ {

    private static final Logger LOGGER = Logger.getLogger(CryptAnalyzerApp.class.getName());

    public static void main(String[] args) {
        // START
        presentProgramMenu();
    }

//    @Override
//    public void start(Stage primaryStage) {
//        try {
//            LOGGER.info("Начало загрузки FXML");
//            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/main-view.fxml")));
//            LOGGER.info("FXML успешно загружен");
//
//            primaryStage.setTitle("Криптоанализатор");
//            primaryStage.setScene(new Scene(root, 600, 400));
//
//            LOGGER.info("Подготовка к отображению окна");
//            primaryStage.show();
//            LOGGER.info("Окно отображено");
//        } catch (IOException e) {
//            LOGGER.log(Level.SEVERE, "Ошибка при загрузке FXML", e);
//        } catch (Exception e) {
//            LOGGER.log(Level.SEVERE, "Неожиданная ошибка", e);
//        }
//    }
//
//    public static void main(String[] args) {
//        LOGGER.info("Запуск приложения");
//        launch(args);
//    }
}
