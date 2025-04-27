package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import lombok.Getter;

@Getter
public class DestroyableTitledPane extends TitledPane implements DestroyableComponent {

    public final <E extends Node & Destroyable> void setContentNode(E value) {
        getDestroyables().add(value);
        setContent(value);
    }

}
