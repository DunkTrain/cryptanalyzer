package com.javarush.cryptanalyzer.shevchenko.services;

import com.javarush.cryptanalyzer.shevchenko.constants.CryptoAlphabet;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс для дешифрования шифра Цезаря методом перебора (brute-force).
 * Использует статический анализ частоты букв и структуры текста
 * для определения наиболее вероятного расшифрованного варианта.
 */
public class TextBruteForce extends TextDecoder {

    /**
     * Статистика частоты букв в русском языке согласно частотному словарю.
     */
    private static final Map<Character, Double> RUSSIAN_LETTER_FREQUENCY = new HashMap<>() {{
        put('о', 0.1097); put('е', 0.0849); put('а', 0.0801); put('и', 0.0735);
        put('н', 0.0669); put('т', 0.0666); put('с', 0.0574); put('р', 0.0545);
        put('в', 0.0473); put('л', 0.0473); put('к', 0.0352); put('м', 0.0321);
        put('д', 0.0249); put('п', 0.0233); put('у', 0.0217); put('я', 0.0201);
        put('г', 0.0170); put('з', 0.0094); put('б', 0.0094); put('ч', 0.0072);
        put('й', 0.0072); put('ь', 0.0072); put('ю', 0.0072); put('ш', 0.0049);
        put('щ', 0.0036); put('ц', 0.0036); put('э', 0.0036); put('ж', 0.0023);
        put('ф', 0.0023); put('ъ', 0.0011); put('ё', 0.0011);
    }};

    /**
     * Регулярное выражение для проверки осмысленности текста.
     * Проверяет наличие нескольких слов с разделителями.
     */
    private static final String MEANINGFUL_TEXT_REGEX = "^([а-яА-Я]+(\\s|,\\s|:\\s|;\\s|-\\s)){2,}";
    private static final Pattern MEANINGFUL_PATTERN = Pattern.compile(MEANINGFUL_TEXT_REGEX);

    /**
     * Выполняет взлом шифра Цезаря методом полного перебора.
     *
     * @param textInput зашифрованный текст
     * @return результат brute-force с наиболее вероятным расшифрованным текстом.
     */
    public static BruteForceResult bruteForce(String textInput) {
        BruteForceResult bestResult = new BruteForceResult();
        double bestScore = Double.NEGATIVE_INFINITY;

        for (int key = 0; key < CryptoAlphabet.LENGTH_ALPHABET; key++) {
            String decryptedText = decrypt(textInput, key);
            double score = calculateTextQuality(decryptedText);

            if (score > bestScore) {
                bestScore = score;
                bestResult = new BruteForceResult(decryptedText, key, score);
            }
        }

        return bestResult;
    }

    /**
     * Вычисляет качество расшифрованного текста на основе структуры и частоты букв.
     *
     * @param text расшифрованный текст
     * @return оценка качества текста
     */
    private static double calculateTextQuality(String text) {
        Matcher matcher = MEANINGFUL_PATTERN.matcher(text);
        double structureScore = matcher.find() ? 10.0 : 0.0;
        double frequencyScore = analyzeLetterFrequency(text);

        return structureScore + frequencyScore;
    }

    /**
     * Анализирует частоту букв в расшифрованном тексте.
     *
     * @param text расшифрованный текст
     * @return оценка соответствия частоты букв эталонной статистике
     */
    private static double analyzeLetterFrequency(String text) {
        Map<Character, Integer> letterCount = new HashMap<>();
        int totalLetters = 0;

        for (char c : text.toLowerCase().toCharArray()) {
            if (Character.isLetter(c)) {
                letterCount.put(c, letterCount.getOrDefault(c, 0) + 1);
                totalLetters++;
            }
        }

        double frequencyScore = 0.0;

        for (Map.Entry<Character, Double> entry : RUSSIAN_LETTER_FREQUENCY.entrySet()) {
            double expectedFrequency = entry.getValue();
            double actualFrequency = letterCount.getOrDefault(entry.getKey(), 0) / (double) totalLetters;

            frequencyScore += 10.0 * (1.0 - Math.abs(expectedFrequency - actualFrequency));
        }

        return frequencyScore;
    }

    /**
     * Внутренний класс для хранения результатов brute-force.
     */
    public static class BruteForceResult {

        private final String decryptedText;
        private final int key;
        private final double confidenceScore;

        /**
         * Конструктор по умолчанию с пустыми значениями.
         */
        public BruteForceResult() {
            this.decryptedText = "";
            this.key = 0;
            this.confidenceScore = 0.0;
        }

        /**
         * Конструктор с параметрами результата brute-force.
         *
         * @param decryptedText расшифрованный текст
         * @param key использованный ключ
         * @param confidenceScore оценка уверенности
         */
        public BruteForceResult(String decryptedText, int key, double confidenceScore) {
            this.decryptedText = decryptedText;
            this.key = key;
            this.confidenceScore = confidenceScore;
        }

        /**
         * @return расшифрованный текст
         */
        public String getDecryptedText() {
            return decryptedText;
        }

        /**
         * @return использованный ключ
         */
        public int getKey() {
            return key;
        }

        /**
         * @return оценка уверенности в расшифровке
         */
        public double getConfidenceScore() {
            return confidenceScore;
        }

        /**
         * Представление результата в виде строки.
         *
         * @return форматированная строка с результатом
         */
        @Override
        public String toString() {
            return String.format("Расшифрованный текст (ключ %d, уверенность %.2f%%): %s",
                    key, confidenceScore * 10, decryptedText);
        }
    }
}
