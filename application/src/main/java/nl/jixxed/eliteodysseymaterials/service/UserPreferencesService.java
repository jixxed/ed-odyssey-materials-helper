package nl.jixxed.eliteodysseymaterials.service;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.AppConstants;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Commander;
import nl.jixxed.eliteodysseymaterials.enums.GameVersion;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.TerminateApplicationEvent;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * PreferencesService that contains only user specific data and is stored in user specific folders
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserPreferencesService {

    private static Properties prop;
    private static Disposable subscribe;
    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();

    static {
        EVENT_LISTENERS.add(EventService.addStaticListener(TerminateApplicationEvent.class, event -> {
            if (subscribe != null) {
                subscribe.dispose();
            }
        }));
    }

    public static synchronized void loadUserPreferences(final Commander commander) {
        log.info("Loading user preferences for " + commander.getFid());
        if (prop != null) {
            prop.clear();
        }
        //create folder if not exists
        final String commanderFolder = ApplicationState.getInstance().getGameVersion().equals(GameVersion.LIVE) ? commander.getLiveCommanderFolder() : commander.getCommanderFolder();
        final File targetFile = new File(commanderFolder + OsConstants.getOsSlash() + AppConstants.USER_PREFERENCES_FILE);
        final File parent = targetFile.getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            throw new IllegalStateException("Couldn't create dir: " + parent);
        }
        //create file if not exists
        if (!targetFile.exists()) {
            try {
                final boolean created = targetFile.createNewFile();
                if (!created) {
                    throw new IllegalStateException("Couldn't create pref file: " + targetFile);
                }
            } catch (final IOException e) {
                throw new IllegalStateException("Couldn't create pref file: " + targetFile);
            }
        }
        if (subscribe != null) {
            subscribe.dispose();
        }
        subscribe = Observable
                .create(emitter -> prop = new Properties() {
                    @Override
                    public synchronized Object setProperty(final String key, final String value) {
                        final Object property = super.setProperty(key, value);
                        emitter.onNext(value);
                        return property;
                    }
                })
                .debounce(1000, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribe(_ -> saveProperties(commanderFolder));

        try (final FileInputStream fis = new FileInputStream(targetFile)) {
            prop.load(fis);
        } catch (final IOException e) {
            log.error("Error reading preferences", e);
            throw new IllegalStateException("Couldn't load pref file: " + targetFile);
        }

    }

    private static synchronized void saveProperties(String commanderFolder) {
        try (final OutputStream output = new FileOutputStream(commanderFolder + OsConstants.getOsSlash() + AppConstants.USER_TEMP_PREFERENCES_FILE)) {
            prop.store(output, null);
            Files.copy(Path.of(commanderFolder + OsConstants.getOsSlash() + AppConstants.USER_TEMP_PREFERENCES_FILE), Path.of(commanderFolder + OsConstants.getOsSlash() + AppConstants.USER_PREFERENCES_FILE), StandardCopyOption.REPLACE_EXISTING);
        } catch (final IOException e) {
            log.error("Error writing preferences", e);
        }
    }

    public static synchronized <T> void setPreference(final String key, final List<T> value, final Function<T, String> mapper) {
        if (prop != null) {
            if (value == null || value.isEmpty()) {
                prop.setProperty(key, "");
            } else {
                prop.setProperty(key, value.stream().map(mapper).collect(Collectors.joining(",")));
            }
        }
    }

    public static synchronized void setPreference(final String key, final LocalDateTime value) {
        if (prop != null) {
            if (value == null) {
                prop.setProperty(key, "");
            } else {
                prop.setProperty(key, value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")));
            }
        }
    }

    public static synchronized void setPreference(final String key, final Object value) {
        if (prop != null) {
            if (value == null) {
                prop.setProperty(key, "");
            } else {
                prop.setProperty(key, value.toString());
            }
        }
    }

    public static synchronized <T> T getPreference(final String key, @NonNull final T defaultValue) {
        if (prop != null) {
            final String value = prop.getProperty(key);
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
            if (defaultValue instanceof LocalDateTime) {
                return (T) LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
            }
            throw new IllegalArgumentException("Unknown configuration value type: " + defaultValue.getClass().getSimpleName());
        }
        return defaultValue;
    }


    public static synchronized void removePreference(final String key) {
        if (prop != null) {
            prop.remove(key);
        }
    }
}
