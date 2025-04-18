package nl.jixxed.eliteodysseymaterials.builder;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.Destroyable;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public abstract class AbstractPaneBuilder<T extends AbstractPaneBuilder<T>> extends AbstractNodeBuilder<T> {
    private final List<Node> nodes = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public <E extends Node & DestroyableComponent> T withNode(final E node) {
        this.nodes.add(node);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public <E extends Node & DestroyableComponent> T withNodes(final E... nodes) {
        this.nodes.addAll(Arrays.asList(nodes));
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public <E extends Node & DestroyableComponent> T withNodes(final Collection<E> nodes) {
        this.nodes.addAll(nodes);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public <P extends Pane & DestroyableComponent> P build(P pane) {
        super.build(pane);
        pane.getChildren().addAll(this.nodes);
        pane.registerAll((List<Destroyable>) (List<?>) this.nodes);
        return pane;
    }
}
