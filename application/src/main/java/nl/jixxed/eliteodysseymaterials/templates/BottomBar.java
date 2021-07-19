package nl.jixxed.eliteodysseymaterials.templates;

import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import jfxtras.styles.jmetro.JMetroStyleClass;
import nl.jixxed.eliteodysseymaterials.AppConstants;
import nl.jixxed.eliteodysseymaterials.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.JournalProcessedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.WatchedFolderChangedEvent;

import java.io.File;

public class BottomBar extends HBox {

    final Label watchedFileLabel = new Label();
    final Label lastTimeStampLabel = new Label();

    public BottomBar() {
        super();
        this.getChildren().addAll(this.watchedFileLabel, new Separator(Orientation.VERTICAL), this.lastTimeStampLabel);
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
}
