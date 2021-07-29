package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.control.Label;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

public class EngineerModuleLabel extends Label {
    private final Engineer engineer;

    EngineerModuleLabel(final Engineer engineer) {
        super();
        this.engineer = engineer;
        this.textProperty().bind(LocaleService.getStringBinding(engineer.getLocalizationKey()));
        this.getStyleClass().add("engineerLabel");
    }

    public Engineer getEngineer() {
        return this.engineer;
    }

    void updateStyle(final boolean unlocked) {
        this.getStyleClass().removeAll("engineer-unlocked", "engineer-locked");
        this.getStyleClass().add((unlocked) ? "engineer-unlocked" : "engineer-locked");
    }
}
