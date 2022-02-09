package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DestroyableResizableImageView extends Pane implements DestroyableComponent {

    private final ImageView iv;
    private final HashMap<ObservableValue, List<ChangeListener>> listenersMap = new HashMap<>();
    private final HashMap<EventType, List<EventHandler>> eventHandlersMap = new HashMap<>();

    public DestroyableResizableImageView() {
        this.iv = new ImageView();
        this.iv.setSmooth(true);
        getChildren().add(this.iv);
    }

    public final void setImage(final Image image) {
        this.iv.setImage(image);
//        setPrefSize(image.getWidth(), image.getHeight());
        this.iv.fitWidthProperty().bind(widthProperty());
        this.iv.fitHeightProperty().bind(heightProperty());
    }

    public final void setPreserveRatio(final boolean preserveRatio) {
        this.iv.setPreserveRatio(preserveRatio);
    }

    public final boolean isPreserveRatio() {
        return this.iv.isPreserveRatio();
    }

    public final BooleanProperty preserveRatioProperty() {
        return this.iv.preserveRatioProperty();
    }

    public final Image getImage() {
        return this.iv.getImage();
    }

    final void unbind() {
        this.iv.fitWidthProperty().unbind();
        this.iv.fitHeightProperty().unbind();
    }

    @Override
    public void destroyInternal() {
        this.iv.fitWidthProperty().unbind();
        this.iv.fitHeightProperty().unbind();
        this.eventHandlersMap.forEach((eventType, eventHandlers) -> eventHandlers.forEach(eventHandler -> removeEventHandler(eventType, eventHandler)));
    }

    public void addDestroyableEventHandler(final EventType eventType, final EventHandler eventHandler) {
        final List<EventHandler> eventHandlers = this.eventHandlersMap.getOrDefault(eventType, new ArrayList<>());
        eventHandlers.add(eventHandler);
        this.eventHandlersMap.put(eventType, eventHandlers);
        addEventHandler(eventType, eventHandler);
    }

    @Override
    public HashMap<ObservableValue, List<ChangeListener>> getListenersMap() {
        return this.listenersMap;
    }
}