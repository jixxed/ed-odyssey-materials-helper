package nl.jixxed.eliteodysseymaterials.service;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class PreferencesService {
    private final static String PREFERENCES_FILE = System.getenv("PROGRAMDATA") + "\\odyssey-materials-helper\\pref.properties";
    private final static PreferencesService instance = new PreferencesService();

    private Properties prop;

    private PreferencesService() {
        //create folder if not exists
        final File targetFile = new File(PREFERENCES_FILE);
        final File parent = targetFile.getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            throw new IllegalStateException("Couldn't create dir: " + parent);
        }
        //create file if not exists
        if (!targetFile.exists()) {
            try {
                targetFile.createNewFile();
            } catch (final IOException e) {
                throw new IllegalStateException("Couldn't create pref file: " + targetFile);
            }
        }
        Observable
                .create(emitter -> {
                    this.prop = new Properties() {
                        @Override
                        public synchronized Object setProperty(final String key, final String value) {
                            final Object property = super.setProperty(key, value);
                            emitter.onNext(value);
                            return property;
                        }
                    };
                })
                .debounce(1000, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribe((newValue) -> {
                    try (final OutputStream output = new FileOutputStream(PREFERENCES_FILE)) {
                        instance.prop.store(output, null);
                    } catch (final IOException e) {
                        log.error("Error writing preferences", e);
                    }
                });

        try (final FileInputStream fis = new FileInputStream(targetFile)) {
            this.prop.load(fis);
        } catch (final IOException e) {
            log.error("Error reading preferences", e);
            throw new IllegalStateException("Couldn't load pref file: " + targetFile);
        }

    }

    public static <T> void setPreference(final String key, final List<T> value, final Function<T, String> mapper) {
        if (value == null || value.isEmpty()) {
            instance.prop.setProperty(key, "");
        } else {
            instance.prop.setProperty(key, value.stream().map(mapper).collect(Collectors.joining(",")));
        }
    }

    public static void setPreference(final String key, final Object value) {
        if (value == null) {
            instance.prop.setProperty(key, "");
        } else {
            instance.prop.setProperty(key, value.toString());
        }
    }

    public static <T> T getPreference(final String key, @NonNull final T defaultValue) {
        final String value = instance.prop.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        if (defaultValue instanceof String) {
            return (T) value;
        }
        if (defaultValue instanceof Boolean) {
            return (T) Boolean.valueOf(value);
        }
        if (defaultValue instanceof Integer) {
            return (T) Integer.valueOf(value);
        }
        if (defaultValue instanceof Float) {
            return (T) Float.valueOf(value);
        }
        if (defaultValue instanceof Double) {
            return (T) Double.valueOf(value);
        }
        throw new IllegalArgumentException("Unknown configuration value type: " + defaultValue.getClass().getSimpleName());
    }


}
