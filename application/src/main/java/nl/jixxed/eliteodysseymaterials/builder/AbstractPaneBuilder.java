package nl.jixxed.eliteodysseymaterials.builder;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class AbstractPaneBuilder<T extends AbstractPaneBuilder<T>> extends AbstractNodeBuilder<T> {
    private final List<Node> nodes = new ArrayList<>();

    public T withNode(final Node node) {
        this.nodes.add(node);
        return (T) this;
    }

    public T withNodes(final Node... nodes) {
        this.nodes.addAll(Arrays.asList(nodes));
        return (T) this;
    }

    public T withNodes(final List<? extends Node> nodes) {
        this.nodes.addAll(nodes);
        return (T) this;
    }

    public <P extends Pane & DestroyableComponent> P build(P pane) {
        super.build(pane);
        pane.getChildren().addAll(this.nodes);
        return pane;
    }
}
