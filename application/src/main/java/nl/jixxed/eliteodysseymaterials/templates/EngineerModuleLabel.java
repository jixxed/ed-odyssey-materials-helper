package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.control.Label;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.JournalProcessedEvent;

class EngineerModuleLabel extends Label {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private final Engineer engineer;

    EngineerModuleLabel(final Engineer engineer) {
        this.engineer = engineer;
        initComponents();
        initEventHandling();
    }

    private void initEventHandling() {
        EventService.addListener(JournalProcessedEvent.class, journalProcessedEvent -> this.updateStyle(APPLICATION_STATE.isEngineerUnlocked(this.engineer)));
    }

    private void initComponents() {
        this.textProperty().bind(LocaleService.getStringBinding(this.engineer.getLocalizationKey()));
        this.getStyleClass().add("engineer-label");
    }

    private void updateStyle(final boolean unlocked) {
        this.getStyleClass().removeAll("engineer-unlocked", "engineer-locked");
        this.getStyleClass().add((unlocked) ? "engineer-unlocked" : "engineer-locked");
    }
}
