package ru.cooper.cryptanalyzer.controllers.ui.helpers;

import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * A utility class for file system operations:
 * <ul>
 *   <li>Selecting text files using a file chooser dialog</li>
 *   <li>Reading and writing text files using UTF-8 encoding</li>
 * </ul>
 */
public class FileHelper {

    /**
     * Creates and configures a {@link FileChooser} for selecting text files (*.txt).
     *
     * @param title the title of the file chooser dialog
     * @return a configured {@code FileChooser} instance
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
     * Reads the contents of the given file using UTF-8 encoding.
     *
     * @param file the file to read from
     * @return the file content as a string
     * @throws IOException if the file does not exist or cannot be read
     */
    public String readTextFromFile(File file) throws IOException {
        return Files.readString(file.toPath(), StandardCharsets.UTF_8);
    }

    /**
     * Writes the given text content to the specified file using UTF-8 encoding.
     *
     * @param content the text content to write
     * @param file    the target file
     * @throws IOException if the file cannot be created or written to
     */
    public void saveTextToFile(String content, File file) throws IOException {
        Files.writeString(file.toPath(), content, StandardCharsets.UTF_8);
    }
}
