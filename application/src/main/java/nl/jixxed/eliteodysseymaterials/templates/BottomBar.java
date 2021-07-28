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
import nl.jixxed.eliteodysseymaterials.domain.Commander;
import nl.jixxed.eliteodysseymaterials.enums.FontSize;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.*;

import java.io.File;

public class BottomBar extends HBox {

    private final Label watchedFileLabel = new Label();
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private String system = "";
    private String body = "";
    private String station = "";

    public BottomBar() {
        super();
        final Region region = new Region();
        final Label commanderLabel = new Label();
        final Label locationLabel = new Label();
        commanderLabel.textProperty().bind(LocaleService.getStringBinding("tab.settings.commander"));
        this.getChildren().addAll(this.watchedFileLabel, new Separator(Orientation.VERTICAL), region, locationLabel, new Separator(Orientation.VERTICAL), commanderLabel, createCommanderSetting());
        HBox.setHgrow(region, Priority.ALWAYS);
        this.getStyleClass().add("bottom-bar");
        this.getStyleClass().add(JMetroStyleClass.BACKGROUND);

        final File watchedFolder = new File(PreferencesService.getPreference(PreferenceConstants.JOURNAL_FOLDER, AppConstants.WATCHED_FOLDER));

        this.watchedFileLabel.textProperty().bind(LocaleService.getStringBinding("statusbar.watching.none", watchedFolder.getAbsolutePath()));
        EventService.addListener(WatchedFolderChangedEvent.class, watchedFolderChangedEvent -> {
            this.watchedFileLabel.textProperty().bind(LocaleService.getStringBinding("statusbar.watching.none", watchedFolderChangedEvent.getPath()));
        });

        EventService.addListener(SimpleLocationEvent.class, simpleLocationEvent -> {
            this.system = simpleLocationEvent.getStarSystem().orElse(this.system);
            switch (simpleLocationEvent.getLocationType()) {
                case LOCATION:
                    final String body = simpleLocationEvent.getBody().orElse("");
                    final String station = simpleLocationEvent.getStation().orElse("");
                    this.body = body.equals(station) ? "" : body;
                    break;
                case DOCKED:
                    break;
                case UNDOCKED:
                    this.station = "";
                    break;
                default:
                    this.body = simpleLocationEvent.getBody().orElse("");
                    break;
            }
            this.station = simpleLocationEvent.getStation().orElse("");
            Platform.runLater(() -> {
                locationLabel.setText(this.system +
                        (this.body.isBlank() ? "" : " | " + this.body) +
                        (this.station.isBlank() || this.station.equals(this.body) ? "" : " | " + this.station));
            });
        });
        EventService.addListener(JournalProcessedEvent.class, journalProcessedEvent -> {
            Platform.runLater(() -> {
                this.watchedFileLabel.textProperty().bind(LocaleService.getStringBinding("statusbar.watching", journalProcessedEvent.getFile().getName()));
            });
        });
    }

    private ComboBox<Commander> createCommanderSetting() {

        final ComboBox<Commander> commanderSelect = new ComboBox<>();
        commanderSelect.getStyleClass().add("bottombar-dropdown");
        commanderSelect.itemsProperty().set(FXCollections.observableArrayList(
                APPLICATION_STATE.getCommanders()
        ));
        EventService.addListener(CommanderAddedEvent.class, commanderAddedEvent -> {
            commanderSelect.getItems().add(commanderAddedEvent.getCommander());
            final String preferredName = PreferencesService.getPreference(PreferenceConstants.COMMANDER, "");
            if (preferredName.isBlank() || commanderAddedEvent.getCommander().getName().equals(preferredName)) {
                commanderSelect.getSelectionModel().select(commanderAddedEvent.getCommander());
            }
        });
        EventService.addListener(CommanderAllListedEvent.class, event -> {
            if (!commanderSelect.getItems().isEmpty() && commanderSelect.getSelectionModel().getSelectedIndex() == -1) {
                commanderSelect.getSelectionModel().select(commanderSelect.getItems().get(0));
                PreferencesService.setPreference(PreferenceConstants.COMMANDER, commanderSelect.getItems().get(0));
                EventService.publish(new CommanderSelectedEvent(commanderSelect.getItems().get(0)));
            }
        });
        EventService.addListener(0, WatchedFolderChangedEvent.class, event -> {
            commanderSelect.getItems().clear();
        });
        EventService.addListener(0, CommanderResetEvent.class, event -> {
            commanderSelect.getItems().clear();
        });
        commanderSelect.valueProperty().addListener((obs, oldValue, newValue) -> {
            Platform.runLater(() -> {
                if (newValue != null) {
                    PreferencesService.setPreference(PreferenceConstants.COMMANDER, newValue);
                }
                if (oldValue != null && newValue != null) {
                    EventService.publish(new CommanderSelectedEvent(newValue));
                }

            });
        });
        commanderSelect.styleProperty().set("-fx-font-size: " + FontSize.valueOf(PreferencesService.getPreference(PreferenceConstants.TEXTSIZE, "NORMAL")).getSize() + "px");
        EventService.addListener(AfterFontSizeSetEvent.class, fontSizeEvent -> {
            commanderSelect.styleProperty().set("-fx-font-size: " + fontSizeEvent.getFontSize() + "px");
        });

        return commanderSelect;
    }
}
