package nl.jixxed.eliteodysseymaterials.service;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.JournalInitEvent;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationService {
    private static boolean enabled = true;

    static {
        EventService.addStaticListener(JournalInitEvent.class, journalInitEvent -> enabled = journalInitEvent.isInitialised());
    }

    public static void showInformation(final NotificationType notificationType, final String title, final String text) {
        final boolean active = PreferencesService.getPreference(PreferenceConstants.NOTIFICATION_PREFIX + notificationType.name(), Boolean.TRUE);
        if (enabled && active) {
            Notifications.create()
                    .darkStyle()
                    .title(title)
                    .text(text)
                    .showInformation();
            playSound(notificationType);
        }
    }

    public static void showWarning(final NotificationType notificationType, final String title, final String text) {
        final boolean active = PreferencesService.getPreference(PreferenceConstants.NOTIFICATION_PREFIX + notificationType.name(), Boolean.TRUE);
        if (enabled && active) {
            Notifications.create()
                    .darkStyle()
                    .title(title)
                    .text(text)
                    .showWarning();
            playSound(notificationType);
        }
    }

    public static void showError(final NotificationType notificationType, final String title, final String text) {
        final boolean active = PreferencesService.getPreference(PreferenceConstants.NOTIFICATION_PREFIX + notificationType.name(), Boolean.TRUE);
        if (enabled && active) {
            Notifications.create()
                    .darkStyle()
                    .title(title)
                    .text(text)
                    .showError();
            playSound(notificationType);
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
                    resource = new File(customSoundPath).toURI();     // For example
                }

                final Media sound = new Media(resource.toString());
                final MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.setVolume(volume / 100);
                mediaPlayer.setOnEndOfMedia(mediaPlayer::dispose);
                mediaPlayer.play();
            } catch (final URISyntaxException | NullPointerException ex) {
                log.error("Failed to play notification sound", ex);
            }
        }
    }

}
