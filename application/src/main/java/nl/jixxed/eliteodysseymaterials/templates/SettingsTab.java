package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationLocale;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

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
        languageSelect.itemsProperty().bind(LocaleService.getListBinding(ApplicationLocale.values()));
        languageSelect.setValue(ApplicationLocale.ENGLISH);
        languageSelect.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                LocaleService.setCurrentLocale(newValue.getLocale());
            }
        });
        final HBox langSetting = new HBox(languageLabel, languageSelect);
        final VBox value = new VBox(settingsLabel, langSetting);
        langSetting.setSpacing(10);
        value.setSpacing(10);
        scrollPane.setContent(value);

    }
}
