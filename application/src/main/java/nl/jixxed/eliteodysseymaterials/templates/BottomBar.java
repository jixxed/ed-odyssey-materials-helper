package nl.jixxed.eliteodysseymaterials.templates;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Orientation;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import jfxtras.styles.jmetro.JMetroStyleClass;
import nl.jixxed.eliteodysseymaterials.AppConstants;
import nl.jixxed.eliteodysseymaterials.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.FontSize;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.*;

import java.io.File;

public class BottomBar extends HBox {

    final Label watchedFileLabel = new Label();
    final Label lastTimeStampLabel = new Label();
    public static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    public BottomBar() {
        super();
        final Region region = new Region();
        final Label commanderLabel = new Label();
        commanderLabel.textProperty().bind(LocaleService.getStringBinding("tab.settings.commander"));
        this.getChildren().addAll(this.watchedFileLabel, new Separator(Orientation.VERTICAL), this.lastTimeStampLabel, region, commanderLabel, creatCommanderSetting());
        HBox.setHgrow(region, Priority.ALWAYS);
        this.getStyleClass().add("bottom-bar");
        this.getStyleClass().add(JMetroStyleClass.BACKGROUND);

        final File watchedFolder = new File(PreferencesService.getPreference(PreferenceConstants.JOURNAL_FOLDER, AppConstants.WATCHED_FOLDER));

        this.watchedFileLabel.textProperty().bind(LocaleService.getStringBinding("statusbar.watching.none", watchedFolder.getAbsolutePath()));
        EventService.addListener(WatchedFolderChangedEvent.class, watchedFolderChangedEvent -> {
            this.watchedFileLabel.textProperty().bind(LocaleService.getStringBinding("statusbar.watching.none", watchedFolderChangedEvent.getPath()));
            this.lastTimeStampLabel.textProperty().bind(LocaleService.getStringBinding("statusbar.last.message", "none", "none"));

        });

        EventService.addListener(JournalProcessedEvent.class, journalProcessedEvent -> {
            Platform.runLater(() -> {
                this.watchedFileLabel.textProperty().bind(LocaleService.getStringBinding("statusbar.watching", journalProcessedEvent.getFile().getAbsolutePath()));
                this.lastTimeStampLabel.textProperty().bind(LocaleService.getStringBinding("statusbar.last.message", journalProcessedEvent.getTimestamp(), journalProcessedEvent.getJournalEventType().friendlyName()));
            });
        });
    }

    private ComboBox<String> creatCommanderSetting() {

        final ComboBox<String> commanderSelect = new ComboBox<>();
        commanderSelect.getStyleClass().add("bottombar-dropdown");
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

        return commanderSelect;
    }
}
