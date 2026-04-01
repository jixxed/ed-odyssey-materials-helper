package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import javafx.event.EventDispatcher;
import javafx.scene.control.TabPane;
import javafx.scene.control.skin.TabPaneSkin;
import javafx.scene.input.ScrollEvent;
import lombok.Getter;

@Getter
public class DestroyableTabPane extends TabPane implements DestroyableComponent {

    @Override
    public void destroyInternal() {
        this.getTabs().clear();
        DestroyableComponent.super.destroyInternal();
    }

    public static void reverseTabPaneScrollingDirection(DestroyableTabPane tabPane) {
        ((TabPaneSkin) tabPane.getSkin()).getChildren().stream().filter(node -> node.getStyleClass().contains("tab-header-area")).findFirst().ifPresent(header -> {
            EventDispatcher originalDispatcher = header.getEventDispatcher();
            header.setEventDispatcher((event, tail) -> {
                if (event instanceof ScrollEvent e) {
                    ScrollEvent reversedEvent = new ScrollEvent(
                            e.getEventType(), e.getX(), e.getY(), e.getScreenX(),
                            e.getScreenY(), e.isShiftDown(), e.isControlDown(), e.isAltDown(),
                            e.isMetaDown(), e.isDirect(), e.isInertia(), e.getDeltaX(),
                            -e.getDeltaY(), // reverse it
                            e.getTotalDeltaX(),
                            -e.getTotalDeltaY(), // reverse it
                            e.getMultiplierX(), e.getMultiplierY(), e.getTextDeltaXUnits(),
                            e.getTextDeltaX(), e.getTextDeltaYUnits(), e.getTextDeltaY(),
                            e.getTouchCount(), e.getPickResult()
                    );

                    return originalDispatcher.dispatchEvent(reversedEvent, tail);
                }

                return originalDispatcher.dispatchEvent(event, tail);
            });
        });
    }
}
