package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.control.Label;
import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.JournalLineProcessedEvent;

import java.util.function.Consumer;

class EngineerModuleLabel extends Label {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private final Engineer engineer;
    @Setter
    private Consumer<JournalLineProcessedEvent> journalProcessedEventConsumer;

    EngineerModuleLabel(final Engineer engineer) {
        this(engineer, false);
    }

    EngineerModuleLabel(final Engineer engineer, final boolean exact) {
        this.engineer = engineer;
        this.journalProcessedEventConsumer = event -> {
            if (exact) {
                this.updateStyle(APPLICATION_STATE.isEngineerUnlockedExact(this.engineer));
            } else {
                this.updateStyle(APPLICATION_STATE.isEngineerUnlocked(this.engineer));
            }
        };
        initComponents();
        initEventHandling();
    }

    private void initEventHandling() {
        EventService.addListener(this, JournalLineProcessedEvent.class, this.journalProcessedEventConsumer);
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
