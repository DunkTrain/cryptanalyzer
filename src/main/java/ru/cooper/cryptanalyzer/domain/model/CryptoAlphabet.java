package ru.cooper.cryptanalyzer.domain.model;

/**
 * Представляет криптографический алфавит, используемый для шифрования/дешифрования.
 * Содержит русские буквы (в верхнем и нижнем регистре), цифры и специальные символы.
 * <p>
 * Представляет методы для работы с алфавитом:
 * <ui>
 *     <li>Получение индекса символа</li>
 *     <li>Получение символа по индексу</li>
 *     <li>Проверка принадлежности символа алфавиту</li>
 * </ui>
 * <p>Класс является утилитарным - создание экземпляра запрещено
 */
public final class CryptoAlphabet {

    public static final String LETTERS_UPPER_CASE = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    public static final String LETTERS_LOWER_CASE = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    public static final String NUMBERS = "0123456789";
    public static final String SYMBOLS = ".,\":-!? ";

    public static final String ALPHABET = LETTERS_UPPER_CASE + LETTERS_LOWER_CASE + NUMBERS + SYMBOLS;
    public static final int LENGTH_ALPHABET = ALPHABET.length();

    private CryptoAlphabet() {
        throw new UnsupportedOperationException("Это служебный класс и не может быть инстанциирован");
    }

    /**
     * Возвращает индекс указанного символа в алфавите.
     *
     * @param symbol символ для поиска
     * @return индекс символа или -1, если символ не найден
     */
    public static int getIndexOf(char symbol) {
        return ALPHABET.indexOf(symbol);
    }

    /**
     * Возвращает символ алфавита по указанному индексу.
     *
     * @param index символа
     * @return символ по указанному индексу
     */
    public static char getCharAt(int index) {
        return ALPHABET.charAt(index);
    }

    /**
     * Проверяет, содержится ли символ в алфавите.
     *
     * @param symbol символ для проверки
     * @return true если символ присутствует в алфавите, false в противном случае
     */
    public static boolean contains(char symbol) {
        return getIndexOf(symbol) != -1;
    }
}
