package ru.cooper.cryptanalyzer.util;

import ru.cooper.cryptanalyzer.domain.model.languages.EnglishAlphabet;
import ru.cooper.cryptanalyzer.domain.model.languages.RussianAlphabet;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Utility class that provides predefined language profiles for text analysis,
 * such as frequency analysis in cryptographic or linguistic applications.
 * <p>
 * This class is non-instantiable and serves as a static holder for language-specific profiles.
 */
public final class LanguageProfiles {

    /**
     * Private constructor to prevent instantiation.
     * This is a utility class and should not be instantiated.
     *
     * @throws UnsupportedOperationException always
     */
    private LanguageProfiles() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Returns a language profile for the Russian language.
     * <p>
     * Includes:
     * <ul>
     *     <li>Letter frequencies based on statistical data</li>
     *     <li>Regular expressions for detecting meaningful Russian phrases</li>
     *     <li>A Russian dictionary</li>
     * </ul>
     *
     * @return a {@link LanguageProfile} object tailored for the Russian language
     */
    public static LanguageProfile russianProfile() {
        Map<Character, Double> russianFrequencies = new HashMap<>();

        russianFrequencies.put('о', 0.1097); russianFrequencies.put('е', 0.0849);
        russianFrequencies.put('а', 0.0801); russianFrequencies.put('и', 0.0735);
        russianFrequencies.put('н', 0.0669); russianFrequencies.put('т', 0.0666);
        russianFrequencies.put('с', 0.0574); russianFrequencies.put('р', 0.0545);
        russianFrequencies.put('в', 0.0473); russianFrequencies.put('л', 0.0473);
        russianFrequencies.put('к', 0.0352); russianFrequencies.put('м', 0.0321);
        russianFrequencies.put('д', 0.0249); russianFrequencies.put('п', 0.0233);
        russianFrequencies.put('у', 0.0217); russianFrequencies.put('я', 0.0201);
        russianFrequencies.put('г', 0.0170); russianFrequencies.put('з', 0.0094);
        russianFrequencies.put('б', 0.0094); russianFrequencies.put('ч', 0.0072);
        russianFrequencies.put('й', 0.0072); russianFrequencies.put('ь', 0.0072);
        russianFrequencies.put('ю', 0.0072); russianFrequencies.put('ш', 0.0049);
        russianFrequencies.put('щ', 0.0036); russianFrequencies.put('ц', 0.0036);
        russianFrequencies.put('э', 0.0036); russianFrequencies.put('ж', 0.0023);
        russianFrequencies.put('ф', 0.0023); russianFrequencies.put('ъ', 0.0011);
        russianFrequencies.put('ё', 0.0011);

        Pattern meaningfulPattern = Pattern.compile("^\\W*[а-яА-Я]+([\\s,.:;-]+[а-яА-Я]+)*");
        Pattern spacesPattern = Pattern.compile("\\b[а-яА-Я]{2,}\\s+[а-яА-Я]{2,}\\b");

        return new LanguageProfile(
                RussianAlphabet.getInstance(),
                russianFrequencies,
                LanguageDictionary.createRussianDictionary(),
                meaningfulPattern,
                spacesPattern,
                10.0
        );
    }

    /**
     * Returns a language profile for the English language.
     * <p>
     * Includes:
     * <ul>
     *     <li>Letter frequencies based on English text corpus statistics</li>
     *     <li>Regular expressions for detecting meaningful English phrases</li>
     *     <li>An English dictionary</li>
     * </ul>
     *
     * @return a {@link LanguageProfile} object tailored for the English language
     */
    public static LanguageProfile englishProfile() {
        Map<Character, Double> englishFrequencies = new HashMap<>();
        englishFrequencies.put('e', 0.1270); englishFrequencies.put('t', 0.0906);
        englishFrequencies.put('a', 0.0817); englishFrequencies.put('o', 0.0751);
        englishFrequencies.put('i', 0.0697); englishFrequencies.put('n', 0.0675);
        englishFrequencies.put('s', 0.0633); englishFrequencies.put('h', 0.0609);
        englishFrequencies.put('r', 0.0599); englishFrequencies.put('d', 0.0425);
        englishFrequencies.put('l', 0.0403); englishFrequencies.put('c', 0.0278);
        englishFrequencies.put('u', 0.0276); englishFrequencies.put('m', 0.0241);
        englishFrequencies.put('w', 0.0236); englishFrequencies.put('f', 0.0223);
        englishFrequencies.put('g', 0.0202); englishFrequencies.put('y', 0.0197);
        englishFrequencies.put('p', 0.0193); englishFrequencies.put('b', 0.0149);
        englishFrequencies.put('v', 0.0098); englishFrequencies.put('k', 0.0077);
        englishFrequencies.put('j', 0.0015); englishFrequencies.put('x', 0.0015);
        englishFrequencies.put('q', 0.0010); englishFrequencies.put('z', 0.0007);

        Pattern meaningfulPattern = Pattern.compile("^\\W*[a-zA-Z]+([\\s,.:;-]+[a-zA-Z]+)*");
        Pattern spacesPattern = Pattern.compile("\\b[a-zA-Z]{2,}\\s+[a-zA-Z]{2,}\\b");

        return new LanguageProfile(
                EnglishAlphabet.getInstance(),
                englishFrequencies,
                LanguageDictionary.createEnglishDictionary(),
                meaningfulPattern,
                spacesPattern,
                10.0
        );
    }
}
