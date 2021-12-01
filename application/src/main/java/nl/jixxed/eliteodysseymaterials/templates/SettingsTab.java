package nl.jixxed.eliteodysseymaterials.templates;

import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.Main;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.ApplicationLocale;
import nl.jixxed.eliteodysseymaterials.enums.FontSize;
import nl.jixxed.eliteodysseymaterials.enums.MaterialOrientation;
import nl.jixxed.eliteodysseymaterials.enums.Tabs;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.*;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

@Slf4j
public class SettingsTab extends EDOTab {
    private static final String SETTINGS_LABEL_CLASS = "settings-label";
    private static final String SETTINGS_DROPDOWN_CLASS = "settings-dropdown";
    private static final String SETTINGS_SPACING_10_CLASS = "settings-spacing-10";
    private final Application application;
    private ScrollPane scrollPane;
    private Label journalFolderLabel;
    private Label selectedFolderLabel;
    private Button journalSelectButton;
    private Label fontsizeLabel;
    private ComboBox<FontSize> fontsizeSelect;
    private ComboBox<ApplicationLocale> languageSelect;
    private Label languageLabel;
    private Label readingDirectionLabel;
    private ComboBox<MaterialOrientation> readingDirectionSelect;
    private CheckBox soloModeCheckBox;
    private Label soloModeLabel;
    private Label soloModeExplainLabel;
    private CheckBox notificationSoundCheckBox;
    private Label notificationSoundLabel;
    private Label customNotificationSoundLabel;
    private Label selectedNotificationSoundLabel;
    private Button customNotificationSoundSelectButton;
    private Button customNotificationSoundClearButton;
    private Label notificationVolumeLabel;
    private Slider notificationVolumeSlider;
    private Button playNotificationButton;


    SettingsTab(final Application application) {
        this.application = application;
        initComponents();
        initEventHandling();
        applyFontSizingHack();
    }

    private void applyFontSizingHack() {
        final String fontSizeStyle = "-fx-font-size: " + FontSize.valueOf(PreferencesService.getPreference(PreferenceConstants.TEXTSIZE, "NORMAL")).getSize() + "px";
        this.journalSelectButton.styleProperty().set(fontSizeStyle);
        this.fontsizeSelect.styleProperty().set(fontSizeStyle);
        this.languageSelect.styleProperty().set(fontSizeStyle);
        this.readingDirectionSelect.styleProperty().set(fontSizeStyle);

    }

    private void initEventHandling() {
        EventService.addListener(this, AfterFontSizeSetEvent.class, fontSizeEvent -> {
            final String fontSizeStyle = "-fx-font-size: " + fontSizeEvent.getFontSize() + "px;";
            this.journalSelectButton.setStyle(fontSizeStyle);
            this.fontsizeSelect.setStyle(fontSizeStyle);
            this.languageSelect.setStyle(fontSizeStyle);
            this.readingDirectionSelect.setStyle(fontSizeStyle);
        });
    }

    private void initComponents() {
        this.textProperty().bind(LocaleService.getStringBinding("tabs.settings"));

        final Label settingsLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withText(LocaleService.getStringBinding("tabs.settings"))
                .build();

        final HBox langSetting = createLangSetting();
        final HBox fontSetting = creatFontSetting();
        final HBox customJournalFolderSetting = createCustomJournalFolderSetting();
        final HBox readingDirectionSetting = createReadingDirectionSetting();
        final HBox soloModeSetting = createSoloModeSetting();
        final HBox notificationSetting = createNotificationSetting();
        final HBox customNotificationSoundSetting = createCustomNotificationSoundSetting();
        final HBox notificationSoundVolumeSetting = createNotificationVolumeSetting();

        final VBox settings = BoxBuilder.builder()
                .withStyleClass(SETTINGS_SPACING_10_CLASS)
                .withNodes(settingsLabel, langSetting, fontSetting, readingDirectionSetting, customJournalFolderSetting, soloModeSetting, notificationSetting, customNotificationSoundSetting, notificationSoundVolumeSetting)
                .buildVBox();
        this.scrollPane = ScrollPaneBuilder.builder()
                .withContent(settings)
                .build();
        this.setContent(this.scrollPane);
    }

    private HBox createNotificationVolumeSetting() {
        this.notificationVolumeLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.notification.volume")).build();
        this.notificationVolumeSlider = SliderBuilder.builder()
                .withMin(0)
                .withMax(100)
                .withValue(PreferencesService.getPreference(PreferenceConstants.NOTIFICATION_VOLUME, 50))
                .withChangeListener((observable, oldValue, newValue) -> {
                    PreferencesService.setPreference(PreferenceConstants.NOTIFICATION_VOLUME, newValue.intValue());
                })
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
                .withStyleClasses("settings-journal-line", SETTINGS_SPACING_10_CLASS)
                .withNodes(this.notificationVolumeLabel, this.notificationVolumeSlider, this.playNotificationButton)
                .buildHBox();

    }

    private HBox createNotificationSetting() {
        this.notificationSoundLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.notification")).build();
        this.notificationSoundCheckBox = CheckBoxBuilder.builder()
                .withValue(PreferencesService.getPreference(PreferenceConstants.NOTIFICATION_SOUND, Boolean.TRUE))
                .withChangeListener((observable, oldValue, newValue) -> {
                    PreferencesService.setPreference(PreferenceConstants.NOTIFICATION_SOUND, newValue);
                })
                .build();
        return BoxBuilder.builder()
                .withStyleClasses("settings-journal-line", SETTINGS_SPACING_10_CLASS)
                .withNodes(this.notificationSoundLabel, this.notificationSoundCheckBox)
                .buildHBox();

    }

    private HBox createCustomNotificationSoundSetting() {
        this.customNotificationSoundLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.notification.sound.custom")).build();
        this.selectedNotificationSoundLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withNonLocalizedText(PreferencesService.getPreference(PreferenceConstants.NOTIFICATION_SOUND_CUSTOM_FILE, "")).build();

        final FileChooser notificationSoundSelect = new FileChooser();
        //Set extension filter for mp3 files
        final FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("MP3 files (*.mp3)", "*.mp3");
        notificationSoundSelect.getExtensionFilters().add(extFilter);

        this.customNotificationSoundSelectButton = ButtonBuilder.builder()
                .withStyleClass("settings-button")
                .withText(LocaleService.getStringBinding("tab.settings.notification.sound.select"))
                .withOnAction(e -> {
                    final File selectedFile = notificationSoundSelect.showOpenDialog(((Main) this.application).getPrimaryStage());
                    if (selectedFile != null) {
                        this.selectedNotificationSoundLabel.setText(selectedFile.getAbsolutePath());
                        PreferencesService.setPreference(PreferenceConstants.NOTIFICATION_SOUND_CUSTOM_FILE, selectedFile.getAbsolutePath());
                    }
                })
                .build();
        this.customNotificationSoundClearButton = ButtonBuilder.builder()
                .withStyleClass("settings-button")
                .withText(LocaleService.getStringBinding("tab.settings.notification.sound.clear"))
                .withOnAction(e -> {
                    this.selectedNotificationSoundLabel.setText("");
                    PreferencesService.setPreference(PreferenceConstants.NOTIFICATION_SOUND_CUSTOM_FILE, "");
                })
                .build();

        return BoxBuilder.builder()
                .withStyleClasses("settings-journal-line", SETTINGS_SPACING_10_CLASS)
                .withNodes(this.customNotificationSoundLabel, this.customNotificationSoundSelectButton, this.customNotificationSoundClearButton, this.selectedNotificationSoundLabel)
                .buildHBox();
    }

    private HBox createSoloModeSetting() {
        this.soloModeLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.solo.mode")).build();
        this.soloModeExplainLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.solo.mode.explain")).build();
        this.soloModeCheckBox = CheckBoxBuilder.builder()
                .withValue(PreferencesService.getPreference(PreferenceConstants.SOLO_MODE, Boolean.FALSE))
                .withChangeListener((observable, oldValue, newValue) -> {
                    PreferencesService.setPreference(PreferenceConstants.SOLO_MODE, newValue);
                    EventService.publish(new SoloModeEvent(newValue));
                })
                .build();
        return BoxBuilder.builder()
                .withStyleClasses("settings-journal-line", SETTINGS_SPACING_10_CLASS)
                .withNodes(this.soloModeLabel, this.soloModeCheckBox, this.soloModeExplainLabel)
                .buildHBox();
    }

    private HBox createCustomJournalFolderSetting() {
        this.journalFolderLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.journal.folder")).build();
        this.selectedFolderLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withNonLocalizedText(PreferencesService.getPreference(PreferenceConstants.JOURNAL_FOLDER, OsConstants.DEFAULT_WATCHED_FOLDER)).build();

        final DirectoryChooser journalFolderSelect = new DirectoryChooser();
        this.journalSelectButton = ButtonBuilder.builder()
                .withStyleClass("settings-button")
                .withText(LocaleService.getStringBinding("tab.settings.journal.folder.select"))
                .withOnAction(e -> {
                    final File selectedDirectory = journalFolderSelect.showDialog(((Main) this.application).getPrimaryStage());
                    if (selectedDirectory != null) {
                        this.selectedFolderLabel.setText(selectedDirectory.getAbsolutePath());
                        PreferencesService.setPreference(PreferenceConstants.JOURNAL_FOLDER, selectedDirectory.getAbsolutePath());
                        EventService.publish(new WatchedFolderChangedEvent(selectedDirectory.getAbsolutePath()));
                    }
                })
                .build();

        return BoxBuilder.builder()
                .withStyleClasses("settings-journal-line", SETTINGS_SPACING_10_CLASS)
                .withNodes(this.journalFolderLabel, this.journalSelectButton, this.selectedFolderLabel)
                .buildHBox();
    }

    private HBox creatFontSetting() {
        this.fontsizeLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText(LocaleService.getStringBinding("tab.settings.textsize"))
                .build();
        this.fontsizeSelect = ComboBoxBuilder.builder(FontSize.class)
                .withStyleClass(SETTINGS_DROPDOWN_CLASS)
                .withItemsProperty(LocaleService.getListBinding(() -> FontSize.values()))
                .withValueChangeListener((obs, oldValue, newValue) -> {
                    log.info("o: " + oldValue + " n: " + newValue);
                    if (newValue != null) {
                        PreferencesService.setPreference(PreferenceConstants.TEXTSIZE, newValue.name());
                        EventService.publish(new FontSizeEvent(newValue.getSize()));
                    }
                })
                .asLocalized()
                .build();

        this.fontsizeSelect.getSelectionModel().select(FontSize.valueOf(PreferencesService.getPreference(PreferenceConstants.TEXTSIZE, "NORMAL")));

        return BoxBuilder.builder()
                .withStyleClass(SETTINGS_SPACING_10_CLASS)
                .withNodes(this.fontsizeLabel, this.fontsizeSelect)
                .buildHBox();
    }

    private HBox createLangSetting() {
        this.languageLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText(LocaleService.getStringBinding("tab.settings.language"))
                .build();
        this.languageSelect = ComboBoxBuilder.builder(ApplicationLocale.class)
                .withStyleClass(SETTINGS_DROPDOWN_CLASS)
                .withItemsProperty(LocaleService.getListBinding(ApplicationLocale.values()))
                .withValueChangeListener((obs, oldValue, newValue) -> {
                    if (newValue != null) {
                        LocaleService.setCurrentLocale(newValue.getLocale());
                        PreferencesService.setPreference(PreferenceConstants.LANGUAGE, newValue.name());
                    }
                })
                .asLocalized()
                .build();

        this.languageSelect.getSelectionModel().select(ApplicationLocale.valueOf(PreferencesService.getPreference(PreferenceConstants.LANGUAGE, "ENGLISH")));


        return BoxBuilder.builder()
                .withStyleClass(SETTINGS_SPACING_10_CLASS)
                .withNodes(this.languageLabel, this.languageSelect)
                .buildHBox();
    }

    private HBox createReadingDirectionSetting() {
        this.readingDirectionLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText(LocaleService.getStringBinding("tab.settings.reading.direction"))
                .build();
        this.readingDirectionSelect = ComboBoxBuilder.builder(MaterialOrientation.class)
                .withStyleClass(SETTINGS_DROPDOWN_CLASS)
                .withItemsProperty(LocaleService.getListBinding(MaterialOrientation.values()))
                .withValueChangeListener((obs, oldValue, newValue) -> {
                    if (newValue != null) {
                        PreferencesService.setPreference(PreferenceConstants.ORIENTATION, newValue.name());
                        EventService.publish(new OrientationChangeEvent(newValue));
                    }
                })
                .asLocalized()
                .build();

        this.readingDirectionSelect.getSelectionModel().select(MaterialOrientation.valueOf(PreferencesService.getPreference(PreferenceConstants.ORIENTATION, "HORIZONTAL")));

        return BoxBuilder.builder()
                .withStyleClass(SETTINGS_SPACING_10_CLASS)
                .withNodes(this.readingDirectionLabel, this.readingDirectionSelect)
                .buildHBox();
    }

    @Override
    public Tabs getTabType() {
        return Tabs.SETTINGS;
    }

}
