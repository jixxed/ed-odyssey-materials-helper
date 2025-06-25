package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.transform.Scale;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.PaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ships.ImageSlot;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.domain.ships.SlotType;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

@Slf4j
public class ShipView extends DestroyableStackPane implements DestroyableTemplate {

    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private final SlotBox slotBox;
    private DestroyableCircle target;
    private DestroyableResizableImageView shipImage;
    private DestroyableGroup zoomGroup;
    private DestroyablePane viewport;
    private ParallelTransition zoomAndCenter;
    private DestroyablePane container;

    public ShipView(SlotBox slotBox) {
        this.slotBox = slotBox;
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("ship-view");
        final Ship ship = APPLICATION_STATE.getShip();
        final int imageIndex = getImageIndex();
        shipImage = ResizableImageViewBuilder.builder()
                .withImage("/images/ships/ship/" + ship.getShipType().name().toLowerCase() + "." + imageIndex + ".png")
                .withStyleClass("ship-view-image")
                .withPreserveRatio(true)
                .build();
        target = new DestroyableCircle();
        target.getStyleClass().add("target");
        zoomGroup = new DestroyableGroup(shipImage, target);
        viewport = PaneBuilder.builder()
                .withStyleClass("viewport")
                .withNodes(zoomGroup)
                .build();
        container = PaneBuilder.builder()
                .withStyleClass("container")
                .build();
        this.getNodes().addAll(viewport, container);
//        if (ship == null) {//can happen directly after selecting a ship in the list
//            return;
//        }
    }

    public void animate() {
        if (slotBox.getSlot() instanceof ImageSlot imageSlot) {
            final double x = this.shipImage.getWidth() / 1920D * imageSlot.getX();
            final double y = this.shipImage.getHeight() / 1080D * imageSlot.getY();
            this.target.setCenterX(x);
            this.target.setCenterY(y);
            resetZoom();
            Platform.runLater(this::zoomToTarget);
        }
    }

    private int getImageIndex() {
        final Ship ship = APPLICATION_STATE.getShip();
        int index = slotBox.getSlot().getIndex();
        int imageIndex = -1;
        if (slotBox.getSlot().getSlotType() == SlotType.HARDPOINT) {
            imageIndex = ship.getHardpointSlots().get(Math.min(index, ship.getHardpointSlots().size() - 1)).getImageIndex();
        } else if (slotBox.getSlot().getSlotType() == SlotType.UTILITY) {
            imageIndex = ship.getUtilitySlots().get(Math.min(index, ship.getUtilitySlots().size() - 1)).getImageIndex();
        }
        return imageIndex;
    }

    public void resetZoom() {
        if (zoomAndCenter != null && zoomAndCenter.getStatus() == ParallelTransition.Status.RUNNING) {
            zoomAndCenter.stop();
        }
        // Reset scale
        zoomGroup.setScaleX(1.0);
        zoomGroup.setScaleY(1.0);

        // Reset position
        zoomGroup.setTranslateX(0.0);
        zoomGroup.setTranslateY(0.0);
    }

    public void zoomToTarget() {
        double scaleFactor = 4.0;

        // Get target position in scene coordinates
        Point2D targetInScene = target.localToScene(target.getCenterX(), target.getCenterY());

        // Get viewport center in scene coordinates
        double viewportCenterX = viewport.getWidth() / 2.0;
        double viewportCenterY = viewport.getHeight() / 2.0;
        Point2D viewportCenterInScene = viewport.localToScene(viewportCenterX, viewportCenterY);

        // Calculate the required movement to center the target
        double deltaX = viewportCenterInScene.getX() - targetInScene.getX();
        double deltaY = viewportCenterInScene.getY() - targetInScene.getY();

        // Clear previous transforms
        zoomGroup.getTransforms().clear();

        // Create a scale transform with the pivot point at the target's position
        Point2D targetInParent = target.localToParent(target.getCenterX(), target.getCenterY());
        Scale scale = new Scale(1, 1, targetInParent.getX(), targetInParent.getY());
        zoomGroup.getTransforms().add(scale);

        // Animate the scale to 2x
        Timeline scaleTimeline = new Timeline(
                new KeyFrame(Duration.millis(3000),
                        new KeyValue(scale.xProperty(), scaleFactor),
                        new KeyValue(scale.yProperty(), scaleFactor)
                )
        );

        // Animate the translation to center the target
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(3000), zoomGroup);
        translateTransition.setByX(deltaX);
        translateTransition.setByY(deltaY);
        translateTransition.setInterpolator(Interpolator.EASE_BOTH);

        // Run both animations together
        zoomAndCenter = new ParallelTransition(scaleTimeline, translateTransition);
        zoomAndCenter.play();
    }
}
