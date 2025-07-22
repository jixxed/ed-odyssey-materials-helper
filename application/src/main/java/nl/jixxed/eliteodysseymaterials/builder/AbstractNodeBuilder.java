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

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@SuppressWarnings("unchecked")
public abstract class AbstractNodeBuilder<T extends AbstractNodeBuilder<T>> {
    private final List<String> styleClasses = new ArrayList<>();
    private boolean visibility = true;
    private boolean managed = true;
    private boolean disable = false;
    private Boolean pickOnBounds;

    private EventHandler<MouseEvent> onMouseClicked;
    private ObservableValue<Boolean> visibilityObservable;
    private ObservableValue<Boolean> disableObservable;
    private ObservableValue<Boolean> managedObservable;
    private ChangeListener<Boolean> hoverPropertyChangeListener;
    private NodeOrientation nodeOrientation;

    private Boolean isMouseTransparent;

    public T withStyleClass(final String styleClass) {
        this.styleClasses.add(styleClass);
        return (T) this;
    }

    public T withStyleClasses(final String... styleClasses) {
        this.styleClasses.addAll(Arrays.asList(styleClasses));
        return (T) this;
    }

    public T withManaged(boolean managed) {
        this.managed = managed;
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

    public T withOnMouseClicked(final EventHandler<MouseEvent> onMouseClicked) {
        this.onMouseClicked = onMouseClicked;
        return (T) this;
    }

    public T withManagedProperty(final ObservableValue<Boolean> observable) {
        this.managedObservable = observable;
        return (T) this;
    }

    public T withDisable(boolean disable) {
        this.disable = disable;
        return (T) this;
    }

    public T withDisableProperty(final ObservableValue<Boolean> observable) {
        this.disableObservable = observable;
        return (T) this;
    }

    public T withPickOnBounds(boolean pickOnBounds) {
        this.pickOnBounds = pickOnBounds;
        return (T) this;
    }

    public T withMouseTransparent(Boolean isMouseTransparent) {
        this.isMouseTransparent = isMouseTransparent;
        return (T) this;
    }

    public <N extends Node & DestroyableComponent> N build(N node) {
        node.getStyleClass().addAll(this.styleClasses);
        node.setDisable(this.disable);
        node.setVisible(this.visibility);
        node.setManaged(this.managed);
        if (this.onMouseClicked != null) {
            node.addEventBinding(node.onMouseClickedProperty(), this.onMouseClicked);
//            node.registerEventHandler(MouseEvent.MOUSE_CLICKED, this.onMouseClicked);
        }
        if (this.visibilityObservable != null) {
            node.addBinding(node.visibleProperty(), this.visibilityObservable);
        }
        if (this.managedObservable != null) {
            node.addBinding(node.managedProperty(), this.managedObservable);
        }
        if (this.disableObservable != null) {
            node.addBinding(node.disableProperty(), this.disableObservable);
        }
        if (this.hoverPropertyChangeListener != null) {
            node.addChangeListener(node.hoverProperty(), this.hoverPropertyChangeListener);
        }
        if (this.nodeOrientation != null) {
            node.setNodeOrientation(this.nodeOrientation);
        }
        if (pickOnBounds != null) {
            node.setPickOnBounds(pickOnBounds);
        }
        if (this.isMouseTransparent != null) {
            node.setMouseTransparent(this.isMouseTransparent);
        }
        return node;
    }

    public abstract <N extends Node> N build();

}
