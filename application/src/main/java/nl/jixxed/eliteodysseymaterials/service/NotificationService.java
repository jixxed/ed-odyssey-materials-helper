package nl.jixxed.eliteodysseymaterials.service;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.util.Duration;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.JournalInitEvent;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationService {
    private static boolean enabled = false;
    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();
    private static final BlockingQueue<String> soundQueue = new LinkedBlockingQueue<>();
    private static boolean isPlaying = false;
    private static MediaPlayer mediaPlayer;

    public static void init() {
        EVENT_LISTENERS.add(EventService.addStaticListener(JournalInitEvent.class, journalInitEvent -> enabled = journalInitEvent.isInitialised()));
    }

    /**
     * @deprecated Use {@link NotificationService#showInformation(NotificationType, LocaleService.LocaleString, LocaleService.LocaleString)} instead.
     */
    @Deprecated(since = "2.77")
    public static void showInformation(final NotificationType notificationType, final String title, final String text) {
        showInformation(notificationType, LocaleService.LocaleString.ofText(title), LocaleService.LocaleString.ofText(text), false);
    }

    /**
     * @deprecated Use {@link NotificationService#showWarning(NotificationType, LocaleService.LocaleString, LocaleService.LocaleString)} instead.
     */
    @Deprecated(since = "2.77")
    public static void showWarning(final NotificationType notificationType, final String title, final String text) {
        showWarning(notificationType, LocaleService.LocaleString.ofText(title), LocaleService.LocaleString.ofText(text), false);
    }

    /**
     * @deprecated Use {@link NotificationService#showError(NotificationType, LocaleService.LocaleString, LocaleService.LocaleString)} instead.
     */
    @Deprecated(since = "2.77")
    public static void showError(final NotificationType notificationType, final String title, final String text) {
        showError(notificationType, LocaleService.LocaleString.ofText(title), LocaleService.LocaleString.ofText(text), false);
    }

    /**
     * @deprecated Use {@link NotificationService#showInformation(NotificationType, LocaleService.LocaleString, LocaleService.LocaleString, boolean)} instead.
     */
    @Deprecated(since = "2.77")
    public static void showInformation(final NotificationType notificationType, final String title, final String text, final boolean silent) {
        showInformation(notificationType, LocaleService.LocaleString.ofText(title), LocaleService.LocaleString.ofText(text), silent);
    }

    /**
     * @deprecated Use {@link NotificationService#showWarning(NotificationType, LocaleService.LocaleString, LocaleService.LocaleString, boolean)} instead.
     */
    @Deprecated(since = "2.77")
    public static void showWarning(final NotificationType notificationType, final String title, final String text, final boolean silent) {
        showWarning(notificationType, LocaleService.LocaleString.ofText(title), LocaleService.LocaleString.ofText(text), silent);
    }

    /**
     * @deprecated Use {@link NotificationService#showError(NotificationType, LocaleService.LocaleString, LocaleService.LocaleString, boolean)} instead.
     */
    @Deprecated(since = "2.77")
    public static void showError(final NotificationType notificationType, final String title, final String text, final boolean silent) {
        showError(notificationType, LocaleService.LocaleString.ofText(title), LocaleService.LocaleString.ofText(text), silent);
    }

    public static void showInformation(final NotificationType notificationType, final LocaleService.LocaleString title, final LocaleService.LocaleString text) {
        showInformation(notificationType, title, text, false);
    }

    public static void showInformation(final NotificationType notificationType, final LocaleService.LocaleString title, final LocaleService.LocaleString text, final boolean silent) {
        final boolean active = PreferencesService.getPreference(PreferenceConstants.NOTIFICATION_PREFIX + notificationType.name(), notificationType.isDefaultEnabled());
        if (enabled && active) {
            log.info("NOTIFY: " + LocaleService.getLocalizedStringForLocale(Locale.ENGLISH, text.getKey(), text.getParameters()));
            final Screen screen = getScreen();
            if (screen != null) {
                Notifications.create()
                        .darkStyle()
                        .title(LocaleService.getLocalizedStringForCurrentLocale(title.getKey(), title.getParameters()))
                        .text(LocaleService.getLocalizedStringForCurrentLocale(text.getKey(), text.getParameters()))
                        .hideAfter(Duration.seconds(10))
                        .owner(screen)
                        .showInformation();
            }
            if (!silent) {
                playSound(notificationType);
            }
        }
    }

    private static Screen getScreen() {
        String screenId = PreferencesService.getPreference(PreferenceConstants.NOTIFICATION_SCREEN, "(" + Screen.getPrimary().hashCode() + ")");
        try {
            final int code = Integer.parseInt(screenId.substring(screenId.indexOf("(") + 1, screenId.indexOf(")")));
            return Screen.getScreens().stream().filter(screen -> code == screen.hashCode()).findFirst().orElse(Screen.getPrimary());
        } catch (NumberFormatException | IndexOutOfBoundsException ex) {
            return Screen.getPrimary();
        }
    }

    public static void showWarning(final NotificationType notificationType, final LocaleService.LocaleString title, final LocaleService.LocaleString text) {
        showWarning(notificationType, title, text, false);
    }

    static void showWarning(final NotificationType notificationType, final LocaleService.LocaleString title, final LocaleService.LocaleString text, final boolean silent) {
        final boolean active = PreferencesService.getPreference(PreferenceConstants.NOTIFICATION_PREFIX + notificationType.name(), notificationType.isDefaultEnabled());
        if (enabled && active) {
            log.warn("WARN: " + LocaleService.getLocalizedStringForLocale(Locale.ENGLISH, text.getKey(), text.getParameters()));
            final Screen screen = getScreen();
            if (screen != null) {
                Notifications.create()
                        .darkStyle()
                        .title(LocaleService.getLocalizedStringForCurrentLocale(title.getKey(), title.getParameters()))
                        .text(LocaleService.getLocalizedStringForCurrentLocale(text.getKey(), text.getParameters()))
                        .owner(screen)
                        .showWarning();
            }
            if (!silent) {
                playSound(notificationType);
            }
        }
    }

    public static void showError(final NotificationType notificationType, final LocaleService.LocaleString title, final LocaleService.LocaleString text) {
        showError(notificationType, title, text, false);
    }

    private static void showError(final NotificationType notificationType, final LocaleService.LocaleString title, final LocaleService.LocaleString text, final boolean silent) {
        final boolean active = PreferencesService.getPreference(PreferenceConstants.NOTIFICATION_PREFIX + notificationType.name(), notificationType.isDefaultEnabled());
        if (enabled && active) {
            log.error("ERROR: " + LocaleService.getLocalizedStringForLocale(Locale.ENGLISH, text.getKey(), text.getParameters()));
            final Screen screen = getScreen();
            if (screen != null) {
                Notifications.create()
                        .darkStyle()
                        .title(LocaleService.getLocalizedStringForCurrentLocale(title.getKey(), title.getParameters()))
                        .text(LocaleService.getLocalizedStringForCurrentLocale(text.getKey(), text.getParameters()))
                        .owner(screen)
                        .showError();
            }
            if (!silent) {
                playSound(notificationType);
            }
        }
    }


    private static void playSound(final NotificationType notificationType) {
        final boolean playSounds = PreferencesService.getPreference(PreferenceConstants.NOTIFICATION_SOUND, Boolean.TRUE);
        final double volume = PreferencesService.getPreference(PreferenceConstants.NOTIFICATION_VOLUME, 50);
        final String customSoundPath = PreferencesService.getPreference(PreferenceConstants.NOTIFICATION_SOUND_CUSTOM_FILE_PREFIX + notificationType.name(), "");
        if (playSounds) {
            try {
                final URI resource;
                if (Objects.equals(customSoundPath, "")) {
                    resource = NotificationService.class.getResource("/audio/tweet.mp3").toURI();
                } else {
                    resource = new File(customSoundPath).toURI();
                }

                soundQueue.add(resource.toString());
                if (!isPlaying) {
                    playNextSound(volume);
                }
            } catch (final URISyntaxException | NullPointerException ex) {
                log.error("Failed to play notification sound", ex);
            }
        }
    }

    private static synchronized void playNextSound(final double volume) {
        String nextSoundPath = soundQueue.poll();
        if (nextSoundPath != null) {
            isPlaying = true;
            try {
                final Media sound = new Media(nextSoundPath);
                mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.setVolume(volume / 100);
                mediaPlayer.setOnEndOfMedia(() -> {
                    mediaPlayer.dispose();
                    Platform.runLater(() -> playNextSound(volume));
                });
                mediaPlayer.play();
            } catch (final MediaException ex) {
                log.error("Failed to play notification sound", ex);
                isPlaying = false;
            }
        } else {
            isPlaying = false;
        }
    }

}
