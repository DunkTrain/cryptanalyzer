package ru.cooper.cryptanalyzer.controllers.ui.validators;

/**
 * Валидатор для проверки ключа шифрования/дешифрования.
 * Контролирует:
 * <ui>
 *     <li>Только цифровой ввод</li>
 *     <li>Диапазон ключа (1..maxKeyValue</li>
 * </ui>
 */
public class KeyValidator {

    private final int maxKeyValue;

    /**
     * Создает валидатор с указанным максимальным значением ключа.
     *
     * @param maxKeyValue максимально допустимое значение ключа.
     */
    public KeyValidator(int maxKeyValue) {
        this.maxKeyValue = maxKeyValue;
    }

    /**
     * Проверяет корректность ввода в текстовое поле ключа.
     *
     * @param newValue новое значение поля.
     * @param oldValue предыдущее значение поля (восстанавливается при ошибке).
     * @throws IllegalArgumentException если ввод содержит недопустимые символы.
     */
    public void validate(String newValue, String oldValue) {
        if(!newValue.matches("\\d*")) {
            throw new IllegalArgumentException("Только цифры");
        }

        if(!newValue.isEmpty()) {
            try {
                int value = Integer.parseInt(newValue);
                validateKeyRange(value);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Ключ должен быть числом");
            }
        }
    }

    /**
     * Проверяет, что ключ находится в допустимом диапазоне.
     *
     * @param key проверяемый ключ.
     * @throws IllegalArgumentException если ключ вне диапазона [1, maxKeyValue]
     */
    public void validateKeyRange(int key) {
        if (key < 1 || key > maxKeyValue) {
            throw new IllegalArgumentException("Ключ должен быть числом от 1 до " + maxKeyValue);
        }
    }
}
