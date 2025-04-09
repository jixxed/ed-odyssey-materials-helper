package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import javafx.scene.Group;
import javafx.scene.Node;

import java.util.Collection;

public class DestroyableGroup extends Group implements DestroyableComponent {
    public DestroyableGroup() {
        super();
    }

    public DestroyableGroup(Node... children) {
        super(children);
    }

    public DestroyableGroup(Collection<Node> children) {
        super(children);
    }
}
