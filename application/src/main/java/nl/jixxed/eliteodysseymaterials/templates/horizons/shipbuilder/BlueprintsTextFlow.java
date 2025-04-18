package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.beans.binding.StringBinding;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;

public class BlueprintsTextFlow extends DestroyableHBox {
    DestroyableLabel blueprints = new DestroyableLabel();
    DestroyableLabel effects = new DestroyableLabel();

    public BlueprintsTextFlow() {
        super();
        this.getNodes().addAll(this.blueprints, this.effects);
        this.getStyleClass().add("shipbuilder-slots-slotbox-blueprints-flow");
    }

    public void setBlueprints(final StringBinding blueprints) {
        this.blueprints.addBinding(this.blueprints.textProperty(), blueprints);
    }

    public void setEffects(final StringBinding effects) {
        this.effects.addBinding(this.effects.textProperty(), effects);
    }

    public void setBlueprintsStyle(String styleClass) {
        this.blueprints.getStyleClass().clear();
        this.blueprints.getStyleClass().add(styleClass);
    }

    public void setEffectsStyle(String styleClass) {
        this.effects.getStyleClass().clear();
        this.effects.getStyleClass().add(styleClass);
    }
}
