package nl.jixxed.eliteodysseymaterials.builder;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class AbstractNodeBuilder<T extends AbstractNodeBuilder<T>> {
    private final List<String> styleClasses = new ArrayList<>();
    private boolean visibility = true;
    private EventHandler<? super MouseEvent> onMouseClicked;
    private ObservableValue<Boolean> visibilityObservable;
    private ObservableValue<Boolean> managedObservable;
    private ChangeListener<Boolean> hoverPropertyChangeListener;
    private NodeOrientation nodeOrientation;

    public T withStyleClass(final String styleClass) {
        this.styleClasses.add(styleClass);
        return (T) this;
    }

    public T withStyleClasses(final String... styleClasses) {
        this.styleClasses.addAll(Arrays.asList(styleClasses));
        return (T) this;
    }

    public T withVisibility(final boolean visibility) {
        this.visibility = visibility;
        return (T) this;
    }

    public T withNodeOrientation(final NodeOrientation nodeOrientation) {
        this.nodeOrientation = nodeOrientation;
        return (T) this;
    }

    public T withVisibilityProperty(final ObservableValue<Boolean> visibilityObservable) {
        this.visibilityObservable = visibilityObservable;
        return (T) this;
    }

    public T withHoverProperty(final ChangeListener<Boolean> hoverPropertyChangeListener) {
        this.hoverPropertyChangeListener = hoverPropertyChangeListener;
        return (T) this;
    }

    public T withOnMouseClicked(final EventHandler<? super MouseEvent> onMouseClicked) {
        this.onMouseClicked = onMouseClicked;
        return (T) this;
    }

    public T withManagedProperty(final ObservableValue<Boolean> observable) {
        this.managedObservable = observable;
        return (T) this;
    }

    public <N extends Node & DestroyableComponent> N build(N node) {
        node.getStyleClass().addAll(this.styleClasses);
        if (this.onMouseClicked != null) {
            node.registerEventHandler(MouseEvent.MOUSE_CLICKED, this.onMouseClicked);
        }
        if (this.visibilityObservable != null) {
            node.addBinding(node.visibleProperty(), this.visibilityObservable);
        }
        if (this.managedObservable != null) {
            node.addBinding(node.managedProperty(), this.managedObservable);
        }
        if (this.hoverPropertyChangeListener != null) {
            node.addChangeListener(node.hoverProperty(), this.hoverPropertyChangeListener);
        }
        if (this.nodeOrientation != null) {
            node.setNodeOrientation(this.nodeOrientation);
        }
        node.setVisible(this.visibility);
        return node;
    }

    public abstract <N extends Node> N build();
}
