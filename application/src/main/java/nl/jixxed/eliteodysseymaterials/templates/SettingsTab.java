package nl.jixxed.eliteodysseymaterials.templates;

import javafx.application.Application;
import javafx.beans.binding.ListBinding;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.Main;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.*;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class SettingsTab extends EDOTab {
    private static final String SETTINGS_LABEL_CLASS = "settings-label";
    private static final String SETTINGS_DROPDOWN_CLASS = "settings-dropdown";
    private static final String SETTINGS_SPACING_10_CLASS = "settings-spacing-10";
    private static final String SETTINGS_JOURNAL_LINE_STYLE_CLASS = "settings-journal-line";
    private static final String SETTINGS_BUTTON_STYLE_CLASS = "settings-button";
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
    private Label overrideLabel;
    private ComboBox<Material> overrideSelect;
    private Button overrideAddButton;
    private Label overrideListLabel;
    private ListView<Material> overrideListView;
    private Button overrideRemoveButton;
    private Label trackingOptOutLabel;
    private Label trackingOptOutExplainLabel;
    private CheckBox trackingOptOutCheckBox;
    private Label wipLabel;
    private Label wipExplainLabel;
    private CheckBox wipCheckBox;


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
        final HBox trackingOptOutSetting = createTrackingOptOutSetting();
        final HBox wipSetting = createWIPSetting();
        final HBox notificationSetting = createNotificationSetting();
        final HBox customNotificationSoundSetting = createCustomNotificationSoundSetting();
        final HBox notificationSoundVolumeSetting = createNotificationVolumeSetting();
        final HBox irrelevantOverrideSetting = createIrrelevantOverrideSetting();
        final HBox irrelevantOverrideList = createIrrelevantOverrideList();

        final VBox settings = BoxBuilder.builder()
                .withStyleClass(SETTINGS_SPACING_10_CLASS)
                .withNodes(settingsLabel, langSetting, fontSetting, readingDirectionSetting, customJournalFolderSetting, soloModeSetting, notificationSetting, customNotificationSoundSetting, notificationSoundVolumeSetting, irrelevantOverrideSetting, irrelevantOverrideList, trackingOptOutSetting, wipSetting)
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

    private HBox createCustomNotificationSoundSetting() {
        this.customNotificationSoundLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.notification.sound.custom")).build();
        this.selectedNotificationSoundLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withNonLocalizedText(PreferencesService.getPreference(PreferenceConstants.NOTIFICATION_SOUND_CUSTOM_FILE, "")).build();

        final FileChooser notificationSoundSelect = new FileChooser();
        //Set extension filter for mp3 files
        final FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("MP3 files (*.mp3)", "*.mp3");
        notificationSoundSelect.getExtensionFilters().add(extFilter);

        this.customNotificationSoundSelectButton = ButtonBuilder.builder()
                .withStyleClass(SETTINGS_BUTTON_STYLE_CLASS)
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
                .withStyleClass(SETTINGS_BUTTON_STYLE_CLASS)
                .withText(LocaleService.getStringBinding("tab.settings.notification.sound.clear"))
                .withOnAction(e -> {
                    this.selectedNotificationSoundLabel.setText("");
                    PreferencesService.setPreference(PreferenceConstants.NOTIFICATION_SOUND_CUSTOM_FILE, "");
                })
                .build();

        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
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
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(this.soloModeLabel, this.soloModeCheckBox, this.soloModeExplainLabel)
                .buildHBox();
    }

    private HBox createTrackingOptOutSetting() {
        this.trackingOptOutLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.tracking.opt.out")).build();
        this.trackingOptOutExplainLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.tracking.opt.out.explain")).build();
        this.trackingOptOutCheckBox = CheckBoxBuilder.builder()
                .withValue(PreferencesService.getPreference(PreferenceConstants.TRACKING_OPT_OUT, Boolean.FALSE))
                .withChangeListener((observable, oldValue, newValue) -> {
                    PreferencesService.setPreference(PreferenceConstants.TRACKING_OPT_OUT, newValue);
                })
                .build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(this.trackingOptOutLabel, this.trackingOptOutCheckBox, this.trackingOptOutExplainLabel)
                .buildHBox();
    }

    private HBox createWIPSetting() {
        this.wipLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.wip")).build();
        this.wipExplainLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.wip.explain")).build();
        this.wipCheckBox = CheckBoxBuilder.builder()
                .withValue(PreferencesService.getPreference(PreferenceConstants.WIP, Boolean.FALSE))
                .withChangeListener((observable, oldValue, newValue) -> {
                    PreferencesService.setPreference(PreferenceConstants.WIP, newValue);
                    EventService.publish(new WipVisibilityEvent(newValue));
                })
                .build();
        return BoxBuilder.builder()
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
                .withNodes(this.wipLabel, this.wipCheckBox, this.wipExplainLabel)
                .buildHBox();
    }

    private HBox createCustomJournalFolderSetting() {
        this.journalFolderLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withText(LocaleService.getStringBinding("tab.settings.journal.folder")).build();
        this.selectedFolderLabel = LabelBuilder.builder().withStyleClass(SETTINGS_LABEL_CLASS).withNonLocalizedText(PreferencesService.getPreference(PreferenceConstants.JOURNAL_FOLDER, OsConstants.DEFAULT_WATCHED_FOLDER)).build();

        final DirectoryChooser journalFolderSelect = new DirectoryChooser();
        this.journalSelectButton = ButtonBuilder.builder()
                .withStyleClass(SETTINGS_BUTTON_STYLE_CLASS)
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
                .withStyleClasses(SETTINGS_JOURNAL_LINE_STYLE_CLASS, SETTINGS_SPACING_10_CLASS)
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
                .withItemsProperty(LocaleService.getListBinding(FontSize::values))
                .withValueChangeListener((obs, oldValue, newValue) -> {
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
                        EventService.publish(new LanguageChangedEvent(newValue.getLocale()));
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

    private HBox createIrrelevantOverrideSetting() {
        this.overrideLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText(LocaleService.getStringBinding("tab.settings.material.override"))
                .build();

        final ListBinding<Material> materialListBinding = LocaleService.getListBinding(Material.getAllIrrelevantMaterialsWithoutOverride().toArray(Material[]::new));
        this.overrideSelect = ComboBoxBuilder.builder(Material.class)
                .withStyleClass(SETTINGS_DROPDOWN_CLASS)
                .withItemsProperty(materialListBinding)
                .withValueChangeListener((observable, oldValue, newValue) -> {
                    this.overrideAddButton.setDisable(newValue == null || this.overrideListView.getItems().contains(newValue));
                })
                .asLocalized()
                .build();

        this.overrideAddButton = ButtonBuilder.builder()
                .withStyleClass(SETTINGS_BUTTON_STYLE_CLASS)
                .withText(LocaleService.getStringBinding("tab.settings.material.override.add"))
                .withOnAction(e -> {
                    if (this.overrideSelect.getSelectionModel().getSelectedItem() != null) {
                        final String irrelevantValues = PreferencesService.getPreference(PreferenceConstants.IRRELEVANT_OVERRIDE, "");
                        final List<Material> items = Arrays.stream(irrelevantValues.split(",")).filter(string -> !string.isEmpty()).map(Material::subtypeForName).collect(Collectors.toList());
                        items.add(this.overrideSelect.getSelectionModel().getSelectedItem());
                        PreferencesService.setPreference(PreferenceConstants.IRRELEVANT_OVERRIDE, items.stream().map(Material::name).collect(Collectors.joining(",")));
                        this.overrideListView.getItems().add(this.overrideSelect.getSelectionModel().getSelectedItem());
                        this.overrideAddButton.setDisable(true);
                        EventService.publish(new IrrelevantMaterialOverrideEvent());
                    }
                })
                .build();
        this.overrideAddButton.setDisable(true);

        return BoxBuilder.builder()
                .withStyleClass(SETTINGS_SPACING_10_CLASS)
                .withNodes(this.overrideLabel, this.overrideSelect, this.overrideAddButton)
                .buildHBox();
    }

    private HBox createIrrelevantOverrideList() {
        this.overrideListLabel = LabelBuilder.builder()
                .withStyleClass(SETTINGS_LABEL_CLASS)
                .withText(LocaleService.getStringBinding("tab.settings.material.override.list"))
                .build();
        this.overrideListView = new ListView<>();
        final String irrelevantValues = PreferencesService.getPreference(PreferenceConstants.IRRELEVANT_OVERRIDE, "");
        final ObservableList<Material> items = Arrays.stream(irrelevantValues.split(",")).filter(string -> !string.isEmpty()).map(Material::subtypeForName).collect(Collectors.toCollection(FXCollections::observableArrayList));
        this.overrideListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.overrideRemoveButton.setDisable(newValue == null);
        });
        this.overrideListView.getItems().addListener((ListChangeListener<Material>) c -> {
            SettingsTab.this.overrideAddButton.setDisable(SettingsTab.this.overrideListView.getItems().contains(SettingsTab.this.overrideSelect.getSelectionModel().getSelectedItem()));
        });
        this.overrideListView.setItems(items);
        this.overrideListView.setCellFactory(getCellFactory());
        this.overrideRemoveButton = ButtonBuilder.builder()
                .withStyleClass(SETTINGS_BUTTON_STYLE_CLASS)
                .withText(LocaleService.getStringBinding("tab.settings.material.override.remove"))
                .withOnAction(e -> {
                    final String currentIrrelevantValues = PreferencesService.getPreference(PreferenceConstants.IRRELEVANT_OVERRIDE, "");
                    final ObservableList<Material> currentItems = Arrays.stream(currentIrrelevantValues.split(",")).filter(string -> !string.isEmpty()).map(Material::subtypeForName).collect(Collectors.toCollection(FXCollections::observableArrayList));
                    currentItems.remove(this.overrideListView.getSelectionModel().getSelectedItem());
                    PreferencesService.setPreference(PreferenceConstants.IRRELEVANT_OVERRIDE, currentItems.stream().map(Material::name).collect(Collectors.joining(",")));
                    this.overrideListView.getItems().remove(this.overrideListView.getSelectionModel().getSelectedItem());
                    this.overrideAddButton.setDisable(this.overrideSelect.getSelectionModel().getSelectedItem() == null || this.overrideListView.getItems().contains(this.overrideSelect.getSelectionModel().getSelectedItem()));
                    EventService.publish(new IrrelevantMaterialOverrideEvent());
                })
                .build();
        this.overrideRemoveButton.setDisable(true);
        return BoxBuilder.builder()
                .withStyleClass(SETTINGS_SPACING_10_CLASS)
                .withNodes(this.overrideListLabel, this.overrideListView, this.overrideRemoveButton)
                .buildHBox();
    }

    private Callback<ListView<Material>, ListCell<Material>> getCellFactory() {
        return listView -> new ListCell<>() {
            //
//            @SuppressWarnings("java:S1068")
//            private final EventListener<StorageEvent> storageEventEventListener = EventService.addListener(RecipeBar.this, StorageEvent.class, event -> {
//                updateStyle(getItem());
//                updateText(getItem(), this.emptyProperty().get());
//            });
            @SuppressWarnings("java:S1068")
            private final EventListener<LanguageChangedEvent> engineerEventEventListener = EventService.addListener(this, LanguageChangedEvent.class, event -> {
                updateText(getItem(), this.emptyProperty().get());
            });


            @Override
            protected void updateItem(final Material item, final boolean empty) {
                super.updateItem(item, empty);
                updateText(item, empty);
            }

            private void updateText(final Material item, final boolean empty) {
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(LocaleService.getLocalizedStringForCurrentLocale(item.getLocalizationKey()));
                }
            }

        };
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
