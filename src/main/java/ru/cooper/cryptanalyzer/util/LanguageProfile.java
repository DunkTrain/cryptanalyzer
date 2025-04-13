package ru.cooper.cryptanalyzer.util;

import ru.cooper.cryptanalyzer.domain.model.Alphabet;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * Represents a language-specific profile used for cryptanalysis and text recognition.
 * Contains the alphabet, expected letter frequency distribution, dictionary, and
 * text structure recognition patterns.
 */
public class LanguageProfile {

    private final Alphabet alphabet;
    private final Map<Character, Double> letterFrequency;
    private final LanguageDictionary dictionary;
    private final Pattern meaningfulPattern;
    private final Pattern spacePattern;
    private final double minTextLength;

    /**
     * Constructs a new {@code LanguageProfile} instance.
     *
     * @param alphabet           the alphabet used in the language
     * @param letterFrequency    a map of character frequency percentages
     * @param dictionary         the dictionary containing valid language words
     * @param meaningfulPattern  regex pattern used to detect structured text
     * @param spacePattern       regex pattern used to check spacing between words
     * @param minTextLength      minimum number of characters required to analyze text
     */
    public LanguageProfile(Alphabet alphabet,
                           Map<Character, Double> letterFrequency,
                           LanguageDictionary dictionary,
                           Pattern meaningfulPattern,
                           Pattern spacePattern,
                           double minTextLength) {
        this.alphabet = alphabet;
        this.letterFrequency = letterFrequency;
        this.dictionary = dictionary;
        this.meaningfulPattern = meaningfulPattern;
        this.spacePattern = spacePattern;
        this.minTextLength = minTextLength;
    }

    /**
     * Returns the alphabet associated with this language profile.
     *
     * @return the alphabet instance
     */
    public Alphabet getAlphabet() {
        return alphabet;
    }

    /**
     * Returns the character frequency map.
     *
     * @return a map of characters to expected frequency values
     */
    public Map<Character, Double> getLetterFrequency() {
        return letterFrequency;
    }

    /**
     * Returns the dictionary used to check word validity.
     *
     * @return the language dictionary
     */
    public LanguageDictionary getDictionary() {
        return dictionary;
    }

    /**
     * Returns the pattern used to identify meaningful text.
     *
     * @return regex pattern for text structure
     */
    public Pattern getMeaningfulPattern() {
        return meaningfulPattern;
    }

    /**
     * Returns the pattern used to identify natural word spacing.
     *
     * @return regex pattern for space structure
     */
    public Pattern getSpacesPattern() {
        return spacePattern;
    }

    /**
     * Returns the minimum number of characters required to analyze text reliably.
     *
     * @return minimum text length
     */
    public double getMinTextLength() {
        return minTextLength;
    }
}
