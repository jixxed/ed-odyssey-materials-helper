package nl.jixxed.eliteodysseymaterials.helper;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public class CSVResourceBundle extends ResourceBundle {
    private static final Map<String, ResourceBundle> BUNDLES = new HashMap<>();
    private Properties properties = new Properties();

    private CSVResourceBundle(final Locale locale, final String... baseNames) {
        Arrays.stream(baseNames).forEach(baseName -> {
            try (final CSVParser csvParser = CSVFormat.DEFAULT.parse(new InputStreamReader(Objects.requireNonNull(CSVResourceBundle.class.getResourceAsStream("/locale/" + baseName + ".csv")), StandardCharsets.UTF_8))) {
                // find language column
                final CSVRecord header = csvParser.iterator().next();
                final int translationColumn = IntStream.range(2, header.size()).map(col -> {
                    final String language = header.get(col);
                    if (!language.isEmpty() && locale.getLanguage().equals(new Locale(language).getLanguage())) {
                        return col;
                    }
                    return 0;
                }).filter(index -> index > 0).max().orElseThrow(IllegalArgumentException::new);

                // reading to properties
                StreamSupport.stream(csvParser.spliterator(), true).forEach(csvRecord -> {
                    final String key = csvRecord.get(0);
                    String translation = csvRecord.get(translationColumn);
                    translation = translation.isEmpty() ? csvRecord.get(1) : translation;
                    this.properties.put(key, translation);
                });

            } catch (final IOException e) {
                throw new IllegalArgumentException(e);
            }
        });
    }

    CSVResourceBundle(final Properties properties) {
        this.properties = properties;
    }

    public static ResourceBundle getResourceBundle(final Locale locale,final String... resourceBundleNames) {
        return BUNDLES.computeIfAbsent("merged" + locale, key -> new CSVResourceBundle(locale,resourceBundleNames));
    }

    @Override
    protected Object handleGetObject(final String key) {
        return this.properties != null ? this.properties.get(key) : this.parent.getObject(key);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Enumeration<String> getKeys() {
        return this.properties != null ? (Enumeration<String>) this.properties.propertyNames() : this.parent.getKeys();
    }

}