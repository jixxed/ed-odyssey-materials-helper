package nl.jixxed.eliteodysseymaterials.templates;

import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import nl.jixxed.eliteodysseymaterials.Main;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.ApplicationLocale;
import nl.jixxed.eliteodysseymaterials.enums.FontSize;
import nl.jixxed.eliteodysseymaterials.enums.MaterialOrientation;
import nl.jixxed.eliteodysseymaterials.enums.Tabs;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.*;

import java.io.File;

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
        EventService.addListener(AfterFontSizeSetEvent.class, fontSizeEvent -> {
            final String fontSizeStyle = "-fx-font-size: " + fontSizeEvent.getFontSize() + "px;";
            this.journalSelectButton.setStyle(fontSizeStyle);
            this.fontsizeSelect.setStyle(fontSizeStyle);
            this.languageSelect.setStyle(fontSizeStyle);
            this.readingDirectionSelect.setStyle(fontSizeStyle);
        });
    }

    private void initComponents() {
        this.textProperty().bind(LocaleService.getStringBinding("tabs.settings"));
        this.scrollPane = new ScrollPane();
        setupScrollPane(this.scrollPane);
        final Label settingsLabel = LabelBuilder.builder()
                .withStyleClass("settings-header")
                .withText(LocaleService.getStringBinding("tabs.settings"))
                .build();

        final HBox langSetting = createLangSetting();
        final HBox fontSetting = creatFontSetting();
        final HBox customJournalFolderSetting = createCustomJournalFolderSetting();
        final HBox readingDirectionSetting = createReadingDirectionSetting();
        final HBox soloModeSetting = createSoloModeSetting();
        final VBox settings = BoxBuilder.builder()
                .withStyleClass(SETTINGS_SPACING_10_CLASS)
                .withNodes(settingsLabel, langSetting, fontSetting, readingDirectionSetting, customJournalFolderSetting, soloModeSetting)
                .buildVBox();

        this.scrollPane.setContent(settings);
        this.setContent(this.scrollPane);
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
                .withItemsProperty(LocaleService.getListBinding(FontSize.values()))
                .withValueChangeListener((obs, oldValue, newValue) -> {
                    if (newValue != null) {
                        PreferencesService.setPreference(PreferenceConstants.TEXTSIZE, newValue.name());
                        EventService.publish(new FontSizeEvent(newValue.getSize()));
                    }
                })
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
