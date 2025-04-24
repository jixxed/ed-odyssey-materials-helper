package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;

public class DestroyableScrollPane extends ScrollPane implements DestroyableComponent {

    public final <E extends Node & Destroyable> void setContentNode(E value) {
        getDestroyables().add(value);
        setContent(value);
    }

    @Override
    public void destroyInternal() {
        this.setContent(null);
        DestroyableComponent.super.destroyInternal();
    }
}
