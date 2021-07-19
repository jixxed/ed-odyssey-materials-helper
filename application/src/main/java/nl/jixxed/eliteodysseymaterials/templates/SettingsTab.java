package nl.jixxed.eliteodysseymaterials.templates;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import nl.jixxed.eliteodysseymaterials.AppConstants;
import nl.jixxed.eliteodysseymaterials.Main;
import nl.jixxed.eliteodysseymaterials.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.ApplicationLocale;
import nl.jixxed.eliteodysseymaterials.enums.FontSize;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.*;

import java.io.File;

public class SettingsTab extends Tab {
    public static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    public SettingsTab(final Application application) {
        super();
        this.textProperty().bind(LocaleService.getStringBinding("tabs.settings"));
        final ScrollPane scrollPane = new ScrollPane();
        scrollPane.pannableProperty().set(true);
        scrollPane.setContent(new VBox());
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        this.setContent(scrollPane);
        final Label settingsLabel = new Label();
        settingsLabel.textProperty().bind(LocaleService.getStringBinding("tabs.settings"));
        settingsLabel.getStyleClass().add("settings-header");


        final HBox langSetting = createLangSetting();
        final HBox fontSetting = creatFontSetting();
        final HBox customJournalFolderSetting = createCustomJournalFolderSetting(application);
//        final HBox commanderSetting = creatCommanderSetting();
        final VBox settings = new VBox(settingsLabel, langSetting, fontSetting, customJournalFolderSetting);
        langSetting.setSpacing(10);
        fontSetting.setSpacing(10);
//        commanderSetting.setSpacing(10);
        customJournalFolderSetting.setSpacing(10);
        settings.setSpacing(10);
        scrollPane.setContent(settings);

    }

    private HBox createCustomJournalFolderSetting(final Application application) {
        final Label journalFolderLabel = new Label();
        journalFolderLabel.textProperty().bind(LocaleService.getStringBinding("tab.settings.journal.folder"));
        journalFolderLabel.getStyleClass().add("settings-label");
        final Label selectedFolderLabel = new Label();
        journalFolderLabel.getStyleClass().add("settings-label");
        selectedFolderLabel.setText(PreferencesService.getPreference(PreferenceConstants.JOURNAL_FOLDER, AppConstants.WATCHED_FOLDER));
        final DirectoryChooser journalFolderSelect = new DirectoryChooser();
        final Button button = new Button();
        button.textProperty().bind(LocaleService.getStringBinding("tab.settings.journal.folder.select"));
        button.getStyleClass().add("settings-button");
        button.setOnAction(e -> {
            final File selectedDirectory = journalFolderSelect.showDialog(((Main) application).getPrimaryStage());
            if (selectedDirectory != null) {
                selectedFolderLabel.setText(selectedDirectory.getAbsolutePath());
                PreferencesService.setPreference(PreferenceConstants.JOURNAL_FOLDER, selectedDirectory.getAbsolutePath());
                EventService.publish(new WatchedFolderChangedEvent(selectedDirectory.getAbsolutePath()));
            }
        });
        final HBox customJournalFolderSetting = new HBox(journalFolderLabel, button, selectedFolderLabel);
        customJournalFolderSetting.setAlignment(Pos.CENTER_LEFT);
        return customJournalFolderSetting;
    }

    private HBox creatFontSetting() {
        final Label fontsizeLabel = new Label();
        fontsizeLabel.textProperty().bind(LocaleService.getStringBinding("tab.settings.textsize"));
        fontsizeLabel.getStyleClass().add("settings-label");
        final ComboBox<FontSize> fontsizeSelect = new ComboBox<>();
        fontsizeSelect.getStyleClass().add("settings-dropdown");
        fontsizeSelect.itemsProperty().bind(LocaleService.getListBinding(FontSize.values()));
        fontsizeSelect.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                PreferencesService.setPreference(PreferenceConstants.TEXTSIZE, newValue.name());
                EventService.publish(new FontSizeEvent(newValue.getSize()));
            }
        });
        fontsizeSelect.styleProperty().set("-fx-font-size: " + FontSize.valueOf(PreferencesService.getPreference(PreferenceConstants.TEXTSIZE, "NORMAL")).getSize() + "px");
        EventService.addListener(AfterFontSizeSetEvent.class, fontSizeEvent -> {
            fontsizeSelect.styleProperty().set("-fx-font-size: " + fontSizeEvent.getFontSize() + "px");
        });
        fontsizeSelect.getSelectionModel().select(FontSize.valueOf(PreferencesService.getPreference(PreferenceConstants.TEXTSIZE, "NORMAL")));

        return new HBox(fontsizeLabel, fontsizeSelect);
    }

    private HBox createLangSetting() {
        final Label languageLabel = new Label();
        languageLabel.textProperty().bind(LocaleService.getStringBinding("tab.settings.language"));
        languageLabel.getStyleClass().add("settings-label");
        final ComboBox<ApplicationLocale> languageSelect = new ComboBox<>();
        languageSelect.getStyleClass().add("settings-dropdown");
        languageSelect.itemsProperty().bind(LocaleService.getListBinding(ApplicationLocale.values()));
        languageSelect.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                LocaleService.setCurrentLocale(newValue.getLocale());
                PreferencesService.setPreference(PreferenceConstants.LANGUAGE, newValue.name());
            }
        });
        languageSelect.getSelectionModel().select(ApplicationLocale.valueOf(PreferencesService.getPreference(PreferenceConstants.LANGUAGE, "ENGLISH")));
        languageSelect.styleProperty().set("-fx-font-size: " + FontSize.valueOf(PreferencesService.getPreference(PreferenceConstants.TEXTSIZE, "NORMAL")).getSize() + "px");
        EventService.addListener(AfterFontSizeSetEvent.class, fontSizeEvent -> {
            languageSelect.styleProperty().set("-fx-font-size: " + fontSizeEvent.getFontSize() + "px");
        });

        return new HBox(languageLabel, languageSelect);
    }

    private HBox creatCommanderSetting() {
        final Label commanderLabel = new Label();
        commanderLabel.textProperty().bind(LocaleService.getStringBinding("tab.settings.commander"));
        commanderLabel.getStyleClass().add("settings-label");
        final ComboBox<String> commanderSelect = new ComboBox<>();
        commanderSelect.getStyleClass().add("settings-dropdown");
        commanderSelect.itemsProperty().set(FXCollections.observableArrayList(
                APPLICATION_STATE.getCommanders()
        ));
        EventService.addListener(CommanderAddedEvent.class, commanderAddedEvent -> {
            commanderSelect.getItems().add(commanderAddedEvent.getName());
            final String preferredName = PreferencesService.getPreference(PreferenceConstants.COMMANDER, "");
            if (preferredName.isBlank() || commanderAddedEvent.getName().equals(preferredName)) {
                commanderSelect.getSelectionModel().select(commanderAddedEvent.getName());
            }
        });
        EventService.addListener(0, WatchedFolderChangedEvent.class, event -> {
            commanderSelect.getItems().clear();
        });
        EventService.addListener(0, CommanderResetEvent.class, event -> {
            commanderSelect.getItems().clear();
        });
        commanderSelect.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                PreferencesService.setPreference(PreferenceConstants.COMMANDER, newValue);
            }
            if (oldValue != null && newValue != null) {
                EventService.publish(new CommanderSelectedEvent(newValue));
            }
        });
        commanderSelect.styleProperty().set("-fx-font-size: " + FontSize.valueOf(PreferencesService.getPreference(PreferenceConstants.TEXTSIZE, "NORMAL")).getSize() + "px");
        EventService.addListener(AfterFontSizeSetEvent.class, fontSizeEvent -> {
            commanderSelect.styleProperty().set("-fx-font-size: " + fontSizeEvent.getFontSize() + "px");
        });

        return new HBox(commanderLabel, commanderSelect);
    }

}
