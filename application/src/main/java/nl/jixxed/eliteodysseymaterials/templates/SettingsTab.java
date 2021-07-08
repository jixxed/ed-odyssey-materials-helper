package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.ApplicationLocale;
import nl.jixxed.eliteodysseymaterials.enums.FontSize;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.AfterFontSizeSetEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.FontSizeEvent;

public class SettingsTab extends Tab {
    public SettingsTab() {
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
        languageSelect.styleProperty().set("-fx-font-size: " + FontSize.valueOf(PreferencesService.getPreference(PreferenceConstants.TEXTSIZE, "NORMAL")).getSize() + "px");
        EventService.addListener(AfterFontSizeSetEvent.class, fontSizeEvent -> {
            languageSelect.styleProperty().set("-fx-font-size: " + fontSizeEvent.getFontSize() + "px");
            fontsizeSelect.styleProperty().set("-fx-font-size: " + fontSizeEvent.getFontSize() + "px");
        });
        fontsizeSelect.getSelectionModel().select(FontSize.valueOf(PreferencesService.getPreference(PreferenceConstants.TEXTSIZE, "NORMAL")));
        final HBox langSetting = new HBox(languageLabel, languageSelect);
        final HBox fontSetting = new HBox(fontsizeLabel, fontsizeSelect);

        final VBox value = new VBox(settingsLabel, langSetting, fontSetting);
        langSetting.setSpacing(10);
        fontSetting.setSpacing(10);
        value.setSpacing(10);
        scrollPane.setContent(value);

    }
}
