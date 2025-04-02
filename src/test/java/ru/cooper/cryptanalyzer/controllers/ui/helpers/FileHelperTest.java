package ru.cooper.cryptanalyzer.controllers.ui.helpers;

import javafx.stage.FileChooser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тесты для класса FileHelper")
class FileHelperTest {

    private FileHelper fileHelper;

    @BeforeEach
    void setUp() {
        fileHelper = new FileHelper();
    }

    @Test
    @DisplayName("Проверка создания FileChooser для текстовых файлов")
    void testCreateTextFileChooser() {
        String title = "Выберите файл";
        FileChooser fileChooser = fileHelper.createTextFileChooser(title);

        assertEquals(title, fileChooser.getTitle(), "Заголовок должен быть установлен");
        assertEquals(1, fileChooser.getExtensionFilters().size(), "Должен быть один фильтр");

        var filter = fileChooser.getExtensionFilters().getFirst();

        assertEquals("Текстовые файлы", filter.getDescription(),
                "Описание фильтра должно быть корректным");
        assertTrue(filter.getExtensions().contains("*.txt"), "Фильтр должен включать *.txt");
    }

    @Test
    @DisplayName("Проверка чтения текста из файла")
    void testReadTextFromFile(@TempDir Path tempDir) throws IOException {
        File tempFile = tempDir.resolve("test.txt").toFile();
        String content = "Привет, мир!";
        Files.writeString(tempFile.toPath(), content);

        String result = fileHelper.readTextFromFile(tempFile);

        assertEquals(content, result, "Текст должен быть прочитан корректно");
    }

    @Test
    @DisplayName("Проверка выброса исключения при чтении несуществующего файла")
    void testReadTextFromFileThrowsException() {
        File nonExistentFile = new File("non_existent.txt");

        assertThrows(IOException.class, () -> fileHelper.readTextFromFile(nonExistentFile),
                "Должно выбросить исключение для несуществующего файла");
    }

    @Test
    @DisplayName("Проверка сохранения текста в файл")
    void testSaveTextToFile(@TempDir Path tempDir) throws IOException {
        File tempFile = tempDir.resolve("output.txt").toFile();
        String content = "Тестовый текст";

        fileHelper.saveTextToFile(content, tempFile);
        String savedContent = Files.readString(tempFile.toPath());

        assertEquals(content, savedContent, "Текст должен быть сохранен корректно");
    }

    @Test
    @DisplayName("Проверка выброса исключения при записи в недоступный файл")
    void testSaveTextToFileThrowsException(@TempDir Path tempDir) throws IOException {
        File tempDirFile = tempDir.toFile();
        File readOnlyFile = new File(tempDirFile, "readonly.txt");
        Files.writeString(readOnlyFile.toPath(), "initial");
        readOnlyFile.setReadOnly(); // Делаем файл только для чтения

        assertThrows(IOException.class, () -> fileHelper.saveTextToFile("новый текст", readOnlyFile),
                "Должно выбросить исключение при записи в файл только для чтения");
    }
}
