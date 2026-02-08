package nl.jixxed.eliteodysseymaterials.templates.settings.sections;

import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.util.StringConverter;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.FXApplication;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.RegistryService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static nl.jixxed.eliteodysseymaterials.templates.settings.SettingsTab.*;

@Slf4j
public class Notifications extends DestroyableVBox implements DestroyableTemplate {

    public Notifications() {
        this.initComponents();
    }

    @Override
    public void initComponents() {
        final DestroyableLabel notificationLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withText("tab.settings.title.notification")
                .build();
        final DestroyableHBox screenSetting = createScreenSetting();
        final DestroyableHBox notificationSetting = createNotificationSetting();
        final DestroyableHBox notificationSoundVolumeSetting = createNotificationVolumeSetting();
        final DestroyableHBox notificationsListHeader = createNotificationListHeader();

        this.getStyleClass().addAll("settingsblock", SETTINGS_SPACING_10_CLASS);
        this.getNodes().addAll(notificationLabel, screenSetting, notificationSetting, notificationSoundVolumeSetting, notificationsListHeader);
        Arrays.stream(NotificationType.values()).forEach(notificationType -> this.getNodes().add(createCustomNotificationSoundSetting(notificationType)));
    }

    /**
     * Returns a configuration option where a display can be selected from a dropdown to be used to display notifications
     */
    private DestroyableHBox createScreenSetting() {
        final DestroyableLabel screenLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.notification.screen")
                .build();

        final DestroyableComboBox<Screen> screenComboBox = ComboBoxBuilder.builder(Screen.class)
                .withStyleClass(SETTINGS_DROPDOWN_CLASS)
                .withItemsProperty(Screen.getScreens())
                .withValueChangeListener((_, _, newValue) -> {
                    if (newValue != null) {
                        PreferencesService.setPreference(PreferenceConstants.NOTIFICATION_SCREEN, "(" + newValue.hashCode() + ")");
                    }
                })
                .withConverter(new StringConverter<>() {
                    final Pattern pattern = Pattern.compile("\\((\\d+)\\)");

                    @Override
                    public String toString(final Screen screen) {
                        if (screen == null)
                            return null;
                        return (int) screen.getBounds().getWidth() + "x" + (int) screen.getBounds().getHeight() + " (" + screen.hashCode() + ")";
                    }

                    @Override
                    public Screen fromString(final String string) {
                        try {
                            final int code = extractCode(string);
                            return Screen.getScreens().stream()
                                    .filter(screen -> code == screen.hashCode())
                                    .findFirst()
                                    .orElse(Screen.getPrimary());
                        } catch (NumberFormatException | IndexOutOfBoundsException | NullPointerException ex) {
                            return Screen.getPrimary();
                        }
                    }

                    private int extractCode(final String string) {
                        final Matcher matcher = pattern.matcher(string);
                        return matcher.find() ? Integer.parseInt(matcher.group(1)) : -1;
                    }
                })
                .build();
        screenComboBox.getSelectionModel()
                .select(screenComboBox.converterProperty().get().fromString(PreferencesService.getPreference(PreferenceConstants.NOTIFICATION_SCREEN, "(" + Screen.getPrimary().hashCode() + ")")));
        //button to test notifications
        final DestroyableButton testNotificationButton = ButtonBuilder.builder()
                .withText("tab.settings.notification.test")
                .withOnAction(_ -> NotificationService.showInformation(NotificationType.SUCCESS, LocaleService.LocaleString.of("notification.test.title"), LocaleService.LocaleString.of("notification.test.text")))
                .build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(screenLabel, screenComboBox, testNotificationButton)
                .buildHBox();
    }


    private DestroyableHBox createNotificationListHeader() {
        final DestroyableLabel headerNotification = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.notification.header.notification")
                .build();
        final DestroyableLabel headerEnabled = LabelBuilder.builder()
                .withText("tab.settings.notification.header.enabled")
                .build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(headerNotification, headerEnabled)
                .buildHBox();
    }

    private DestroyableHBox createNotificationVolumeSetting() {
        DestroyableLabel notificationVolumeLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.notification.volume")
                .build();
        DestroyableSlider notificationVolumeSlider = SliderBuilder.builder()
                .withMin(0)
                .withMax(100)
                .withValue(PreferencesService.getPreference(PreferenceConstants.NOTIFICATION_VOLUME, 50))
                .withChangeListener((observable, oldValue, newValue) -> PreferencesService.setPreference(PreferenceConstants.NOTIFICATION_VOLUME, newValue.intValue()))
                .build();
        // For example
        DestroyableButton playNotificationButton = ButtonBuilder.builder()
                .withText("tab.settings.notification.play")
                .withOnAction(_ -> {
                    final double volume = PreferencesService.getPreference(PreferenceConstants.NOTIFICATION_VOLUME, 50);
                    final String customSoundPath = PreferencesService.getPreference(PreferenceConstants.NOTIFICATION_SOUND_CUSTOM_FILE, "");
                    try {
                        final URI resource;
                        if (Objects.equals(customSoundPath, "")) {
                            resource = Objects.requireNonNull(NotificationService.class.getResource("/audio/pop.mp3")).toURI();
                        } else {
                            resource = new File(customSoundPath).toURI();     // For example
                        }

                        final Media sound = new Media(resource.toString());
                        final MediaPlayer mediaPlayer = new MediaPlayer(sound);
                        mediaPlayer.setVolume(volume / 100);
                        mediaPlayer.play();
                    } catch (final URISyntaxException | NullPointerException | MediaException ex) {
                        log.error("Failed to play notification sound", ex);
                        NotificationService.showError(NotificationType.ERROR, LocaleService.LocaleString.of("notification.play.error.title"), RegistryService.hasMediaServices() ? LocaleService.LocaleString.of("notification.play.error.text") : LocaleService.LocaleString.of("notification.media.feature.pack.text"), true);
                    }
                })
                .build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(notificationVolumeLabel, notificationVolumeSlider, playNotificationButton)
                .buildHBox();

    }

    private DestroyableHBox createNotificationSetting() {
        DestroyableLabel notificationSoundLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.notification")
                .build();
        DestroyableCheckBox notificationSoundCheckBox = CheckBoxBuilder.builder()
                .withSelected(PreferencesService.getPreference(PreferenceConstants.NOTIFICATION_SOUND, Boolean.TRUE))
                .withSelectedProperty((_, _, newValue) -> PreferencesService.setPreference(PreferenceConstants.NOTIFICATION_SOUND, newValue))
                .build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(notificationSoundLabel, notificationSoundCheckBox)
                .buildHBox();

    }

    private DestroyableHBox createCustomNotificationSoundSetting(final NotificationType notificationType) {
        final DestroyableLabel customNotificationSoundLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText("tab.settings.notification.sound.custom", LocaleService.LocalizationKey.of("notification.type." + notificationType.name().toLowerCase()))
                .build();
        final DestroyableLabel selectedNotificationSoundLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withNonLocalizedText(PreferencesService.getPreference(PreferenceConstants.NOTIFICATION_SOUND_CUSTOM_FILE_PREFIX + notificationType.name(), ""))
                .build();

        final FileChooser notificationSoundSelect = new FileChooser();
        //Set extension filter for mp3 files
        final FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("MP3 files (*.mp3)", "*.mp3");
        notificationSoundSelect.getExtensionFilters().add(extFilter);
        final DestroyableCheckBox notificationEnabledCheckBox = CheckBoxBuilder.builder()
                .withSelected(PreferencesService.getPreference(PreferenceConstants.NOTIFICATION_PREFIX + notificationType.name(), notificationType.isDefaultEnabled()))
                .withSelectedProperty((_, _, newValue) -> PreferencesService.setPreference(PreferenceConstants.NOTIFICATION_PREFIX + notificationType.name(), newValue))
                .build();
        final DestroyableButton customNotificationSoundSelectButton = ButtonBuilder.builder()
                .withStyleClass(SETTINGS_BUTTON_STYLE_CLASS)
                .withText("tab.settings.notification.sound.select")
                .withOnAction(e -> {
                    final File selectedFile = notificationSoundSelect.showOpenDialog((FXApplication.getInstance()).getPrimaryStage());
                    if (selectedFile != null) {
                        selectedNotificationSoundLabel.setText(selectedFile.getAbsolutePath());
                        PreferencesService.setPreference(PreferenceConstants.NOTIFICATION_SOUND_CUSTOM_FILE_PREFIX + notificationType.name(), selectedFile.getAbsolutePath());
                    }
                })
                .build();
        final DestroyableButton customNotificationSoundClearButton = ButtonBuilder.builder()
                .withStyleClass(SETTINGS_BUTTON_STYLE_CLASS)
                .withText("tab.settings.notification.sound.clear")
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
