package nl.jixxed.eliteodysseymaterials.templates.settings.sections;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.FXApplication;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static nl.jixxed.eliteodysseymaterials.templates.settings.SettingsTab.*;
@Slf4j
public class Notifications extends VBox implements Template {
    private final List<EventListener<?>> eventListeners = new ArrayList<>();
    private CheckBox notificationSoundCheckBox;
    private Label notificationSoundLabel;
    private Label notificationVolumeLabel;
    private Slider notificationVolumeSlider;
    private Button playNotificationButton;
    private Application application;
    public Notifications(Application application) {
        this.application = application;
        this.initComponents();
        this.initEventHandling();
    }
    @Override
    public void initComponents() {
        final Label notificationLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withText(LocaleService.getStringBinding("tab.settings.title.notification"))
                .build();
        final HBox notificationSetting = createNotificationSetting();
        final HBox notificationSoundVolumeSetting = createNotificationVolumeSetting();
        final HBox notificationsListHeader = createNotificationListHeader();

        this.getStyleClass().addAll("settingsblock", SETTINGS_SPACING_10_CLASS);
        this.getChildren().addAll(notificationLabel, notificationSetting, notificationSoundVolumeSetting, notificationsListHeader);
        Arrays.stream(NotificationType.values()).forEach(notificationType -> this.getChildren().add(createCustomNotificationSoundSetting(notificationType)));
    }

    @Override
    public void initEventHandling() {

    }
    private HBox createNotificationListHeader() {
        final DestroyableLabel headerNotification = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.notification.header.notification")).build();
        final DestroyableLabel headerEnabled = LabelBuilder.builder().withText(LocaleService.getStringBinding("tab.settings.notification.header.enabled")).build();
        return BoxBuilder.builder().withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS).withNodes(headerNotification, headerEnabled).buildHBox();
    }
    private HBox createNotificationVolumeSetting() {
        this.notificationVolumeLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.notification.volume")).build();
        this.notificationVolumeSlider = SliderBuilder.builder()
                .withMin(0)
                .withMax(100)
                .withValue(PreferencesService.getPreference(PreferenceConstants.NOTIFICATION_VOLUME, 50))
                .withChangeListener((observable, oldValue, newValue) -> PreferencesService.setPreference(PreferenceConstants.NOTIFICATION_VOLUME, newValue.intValue()))
                .build();
        this.playNotificationButton = ButtonBuilder.builder()
                .withText(LocaleService.getStringBinding("tab.settings.notification.play"))
                .withOnAction(event -> {
                    final double volume = PreferencesService.getPreference(PreferenceConstants.NOTIFICATION_VOLUME, 50);
                    final String customSoundPath = PreferencesService.getPreference(PreferenceConstants.NOTIFICATION_SOUND_CUSTOM_FILE, "");
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
                        mediaPlayer.play();
                    } catch (final URISyntaxException | NullPointerException ex) {
                        log.error("Failed to play notification sound", ex);
                    }
                })
                .build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(this.notificationVolumeLabel, this.notificationVolumeSlider, this.playNotificationButton)
                .buildHBox();

    }

    private HBox createNotificationSetting() {
        this.notificationSoundLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.notification")).build();
        this.notificationSoundCheckBox = CheckBoxBuilder.builder()
                .withValue(PreferencesService.getPreference(PreferenceConstants.NOTIFICATION_SOUND, Boolean.TRUE))
                .withChangeListener((observable, oldValue, newValue) -> PreferencesService.setPreference(PreferenceConstants.NOTIFICATION_SOUND, newValue))
                .build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(this.notificationSoundLabel, this.notificationSoundCheckBox)
                .buildHBox();

    }

    private HBox createCustomNotificationSoundSetting(final NotificationType notificationType) {
        final Label customNotificationSoundLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.notification.sound.custom", LocaleService.LocalizationKey.of("notification.type." + notificationType.name().toLowerCase()))).build();
        final Label selectedNotificationSoundLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withNonLocalizedText(PreferencesService.getPreference(PreferenceConstants.NOTIFICATION_SOUND_CUSTOM_FILE_PREFIX + notificationType.name(), "")).build();

        final FileChooser notificationSoundSelect = new FileChooser();
        //Set extension filter for mp3 files
        final FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("MP3 files (*.mp3)", "*.mp3");
        notificationSoundSelect.getExtensionFilters().add(extFilter);
        final CheckBox notificationEnabledCheckBox = CheckBoxBuilder.builder()
                .withValue(PreferencesService.getPreference(PreferenceConstants.NOTIFICATION_PREFIX + notificationType.name(), notificationType.isDefaultEnabled()))
                .withChangeListener((observable, oldValue, newValue) -> PreferencesService.setPreference(PreferenceConstants.NOTIFICATION_PREFIX + notificationType.name(), newValue))
                .build();
        final Button customNotificationSoundSelectButton = ButtonBuilder.builder()
                .withStyleClass(SETTINGS_BUTTON_STYLE_CLASS)
                .withText(LocaleService.getStringBinding("tab.settings.notification.sound.select"))
                .withOnAction(e -> {
                    final File selectedFile = notificationSoundSelect.showOpenDialog(((FXApplication) this.application).getPrimaryStage());
                    if (selectedFile != null) {
                        selectedNotificationSoundLabel.setText(selectedFile.getAbsolutePath());
                        PreferencesService.setPreference(PreferenceConstants.NOTIFICATION_SOUND_CUSTOM_FILE_PREFIX + notificationType.name(), selectedFile.getAbsolutePath());
                    }
                })
                .build();
        final Button customNotificationSoundClearButton = ButtonBuilder.builder()
                .withStyleClass(SETTINGS_BUTTON_STYLE_CLASS)
                .withText(LocaleService.getStringBinding("tab.settings.notification.sound.clear"))
                .withOnAction(e -> {
                    selectedNotificationSoundLabel.setText("");
                    PreferencesService.setPreference(PreferenceConstants.NOTIFICATION_SOUND_CUSTOM_FILE_PREFIX + notificationType.name(), "");
                })
                .build();

        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(customNotificationSoundLabel, notificationEnabledCheckBox, customNotificationSoundSelectButton, customNotificationSoundClearButton, selectedNotificationSoundLabel)
                .buildHBox();
    }

}
