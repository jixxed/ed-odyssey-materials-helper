package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;

public class DestroyableAccordion extends Accordion implements DestroyableComponent {
    public DestroyableAccordion() {
    }

    public DestroyableAccordion(TitledPane... titledPanes) {
        super(titledPanes);
    }


    @Override
    public void destroyInternal() {
        this.getChildren().clear();
        DestroyableComponent.super.destroyInternal();
    }
}
