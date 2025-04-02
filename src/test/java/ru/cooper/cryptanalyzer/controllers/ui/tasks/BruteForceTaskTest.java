package ru.cooper.cryptanalyzer.controllers.ui.tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.cooper.cryptanalyzer.core.TextBruteForce;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Тесты для класса BruteForceTask")
class BruteForceTaskTest {

    private TextBruteForce mockBruteForce;
    private BruteForceTask task;

    @BeforeEach
    void setUp() throws Exception {
        mockBruteForce = mock(TextBruteForce.class);
        task = new BruteForceTask("зашифрованный текст");
        setField(task, "bruteForce", mockBruteForce);
    }

    @Test
    @DisplayName("Проверка установки текста в конструкторе")
    void testConstructorSetsText() throws Exception {
        BruteForceTask newTask = new BruteForceTask("тестовый текст");
        String actualText = getField(newTask, "text");
        assertEquals("тестовый текст", actualText, "Текст должен быть установлен");
    }

    @Test
    @DisplayName("Проверка вызова bruteForce с корректным текстом")
    void testCallWithValidText() throws Exception {
        var mockResult = new TextBruteForce.BruteForceResult("расшифрованный текст", 5, 0.85);
        when(mockBruteForce.bruteForce("зашифрованный текст")).thenReturn(mockResult);

        var result = task.call();

        verify(mockBruteForce).bruteForce("зашифрованный текст");
        assertEquals("расшифрованный текст", result.getDecryptedText(), "Текст должен быть расшифрован");
        assertEquals(5, result.getKey(), "Ключ должен быть верным");
        assertEquals(0.85, result.getConfidenceScore(), 0.001, "Уверенность должна быть верной");
    }

    @Test
    @DisplayName("Проверка вызова с null текстом")
    void testCallWithNullText() throws Exception {
        BruteForceTask nullTask = new BruteForceTask(null);
        var mockResult = new TextBruteForce.BruteForceResult("", 0, 0.0);
        when(mockBruteForce.bruteForce(null)).thenReturn(mockResult);
        setField(nullTask, "bruteForce", mockBruteForce);

        var result = nullTask.call();

        verify(mockBruteForce).bruteForce(null);
        assertEquals("", result.getDecryptedText(), "Текст должен быть пустым");
        assertEquals(0, result.getKey(), "Ключ должен быть 0");
        assertEquals(0.0, result.getConfidenceScore(), 0.001, "Уверенность должна быть 0");
    }

    @Test
    @DisplayName("Проверка состояния задачи до и после выполнения")
    void testTaskState() throws Exception {
        var mockResult = new TextBruteForce.BruteForceResult("расшифрованный текст", 5, 0.85);
        when(mockBruteForce.bruteForce("зашифрованный текст")).thenReturn(mockResult);

        assertFalse(task.isRunning(), "Задача не должна быть запущена");

        task.run();

        assertTrue(task.isDone(), "Задача должна быть завершена");
    }

    @Test
    @DisplayName("Проверка обработки исключений в call")
    void testCallThrowsException() {
        when(mockBruteForce.bruteForce("зашифрованный текст")).thenThrow(new RuntimeException("Ошибка взлома"));

        assertThrows(RuntimeException.class, () -> task.call(), "Должно выбросить исключение");
    }

    // Вспомогательные методы для работы с рефлексией
    private void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    private String getField(Object target, String fieldName) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return (String) field.get(target);
    }
}
