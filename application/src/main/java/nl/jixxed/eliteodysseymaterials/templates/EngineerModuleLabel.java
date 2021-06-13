package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.control.Label;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;

public class EngineerModuleLabel extends Label {
    private final Engineer engineer;

    public EngineerModuleLabel(final Engineer engineer) {
        super(engineer.friendlyName());
        this.engineer = engineer;
        this.getStyleClass().add("engineerLabel");
    }

    public Engineer getEngineer() {
        return this.engineer;
    }

    public void updateStyle(final boolean unlocked) {
        this.getStyleClass().removeAll("engineer-unlocked", "engineer-locked");
        this.getStyleClass().add((unlocked) ? "engineer-unlocked" : "engineer-locked");
    }
}
