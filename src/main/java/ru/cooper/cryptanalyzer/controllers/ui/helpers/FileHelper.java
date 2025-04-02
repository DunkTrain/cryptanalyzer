package ru.cooper.cryptanalyzer.controllers.ui.helpers;

import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * Утилита для работы с файловой системой:
 * <ul>
 *   <li>Выбор файлов через диалоговые окна</li>
 *   <li>Чтение/запись текста в UTF-8</li>
 * </ul>
 */
public class FileHelper {

    /**
     * Настраивает диалог выбора текстовых файлов (*.txt).
     *
     * @param title заголовок окна
     * @return настроенный FileChooser
     */
    public FileChooser createTextFileChooser(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Текстовые файлы", "*.txt")
        );

        return fileChooser;
    }

    /**
     * Читает текст из файла в кодировке UTF-8.
     *
     * @param file файл для чтения
     * @return содержимое файла
     * @throws IOException если файл не существует или недоступен
     */
    public String readTextFromFile(File file) throws IOException {
        return Files.readString(file.toPath(), StandardCharsets.UTF_8);
    }

    /**
     * Сохраняет текст в файл в кодировке UTF-8.
     *
     * @param content текст для сохранения
     * @param file    целевой файл
     * @throws IOException если файл не может быть создан или записан
     */
    public void saveTextToFile(String content, File file) throws IOException {
        Files.writeString(file.toPath(), content, StandardCharsets.UTF_8);
    }
}
