package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import javafx.scene.Group;
import javafx.scene.Node;

import java.util.Collection;

public class DestroyableGroup extends Group implements DestroyableComponent {
    public DestroyableGroup() {
        super();
    }

    public <E extends Node & DestroyableComponent> DestroyableGroup(E... children) {
        super(children);
        registerAll(children);
    }

    public <E extends Node & DestroyableComponent> DestroyableGroup(Collection<E> children) {
        super((Collection<Node>) (Collection<?>) children);
        registerAll(children);
    }
}
