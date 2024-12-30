package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.beans.binding.StringBinding;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class BlueprintsTextFlow extends HBox {
    Label blueprints = new Label();
    Label effects = new Label();

    public BlueprintsTextFlow() {
        super();
        this.getChildren().addAll(this.blueprints, this.effects);
        this.getStyleClass().add("shipbuilder-slots-slotbox-blueprints-flow");
    }

    public void setBlueprints(final StringBinding blueprints) {
        this.blueprints.textProperty().bind(blueprints);
    }

    public void setEffects(final StringBinding effects) {
        this.effects.textProperty().bind(effects);
    }

    public void setBlueprintsStyle(String styleClass){
        this.blueprints.getStyleClass().clear();
        this.blueprints.getStyleClass().add(styleClass);
    }

    public void setEffectsStyle(String styleClass){
        this.effects.getStyleClass().clear();
        this.effects.getStyleClass().add(styleClass);
    }
}
