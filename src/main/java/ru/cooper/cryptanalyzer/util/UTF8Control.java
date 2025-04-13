package ru.cooper.cryptanalyzer.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Позволяет загружать .properties в кодировке UTF-8.
 */
public class UTF8Control extends ResourceBundle.Control {
    @Override
    public ResourceBundle newBundle(String baseName,
                                    Locale locale,
                                    String format,
                                    ClassLoader loader,
                                    boolean reload)
            throws IllegalAccessException, InstantiationException, IOException {

        // Имя ресурса (пример: "i18n.Messages_en.properties")
        String resourceName = toResourceName(toBundleName(baseName, locale), "properties");
        ResourceBundle bundle = null;
        try (InputStream stream = loader.getResourceAsStream(resourceName)) {
            if (stream != null) {
                // Читаем поток как UTF-8
                try (InputStreamReader isr = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
                    bundle = new PropertyResourceBundle(isr);
                }
            }
        }
        return bundle;
    }
}
