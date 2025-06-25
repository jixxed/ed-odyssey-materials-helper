package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import nl.jixxed.eliteodysseymaterials.builder.FontAwesomeIconViewBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ShipConfiguration;
import nl.jixxed.eliteodysseymaterials.domain.ships.HardpointModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipService;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

public class SlotBoxMountingControl extends DestroyableVBox implements DestroyableEventTemplate {
    private DestroyableLabel mountingLabel;
    private DestroyableFontAwesomeIconView mountingUp;
    private DestroyableFontAwesomeIconView mountingDown;
    private final SlotBox slotBox;

    public SlotBoxMountingControl(SlotBox slotBox) {
        this.slotBox = slotBox;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("mounting-control");
        mountingLabel = LabelBuilder.builder()
                .withStyleClass("control-text")
                .build();
        if (!isCurrentShip()) {
            mountingUp = FontAwesomeIconViewBuilder.builder()
                    .withStyleClass("control-button")
                    .withIcon(FontAwesomeIcon.ANGLE_UP)
                    .withOnMouseClicked(event -> {
                        ((HardpointModule) slotBox.getSlot().getShipModule()).findHigherMounting().ifPresent(slotBox::replaceModule);
                        event.consume();
                    })
                    .build();
            mountingDown = FontAwesomeIconViewBuilder.builder()
                    .withStyleClass("control-button")
                    .withIcon(FontAwesomeIcon.ANGLE_DOWN)
                    .withOnMouseClicked(event -> {
                        ((HardpointModule) slotBox.getSlot().getShipModule()).findLowerMounting().ifPresent(slotBox::replaceModule);
                        event.consume();
                    })
                    .build();
            this.getNodes().addAll(mountingUp, mountingLabel, mountingDown);
        } else {
            this.getNodes().addAll(new GrowingRegion(), mountingLabel, new GrowingRegion());
        }
    }

    @Override
    public void initEventHandling() {

    }

    private static boolean isCurrentShip() {
        return ApplicationState.getInstance().getPreferredCommander()
                .flatMap(commander -> ShipService.getShipConfigurations(commander).getSelectedShipConfiguration())
                .map(shipConfiguration -> ShipConfiguration.CURRENT == shipConfiguration)
                .orElse(Boolean.FALSE);
    }

    public void updateMounting(ShipModule shipModule) {
        if (shipModule instanceof HardpointModule hardpointModule) {
            if (!isCurrentShip()) {
                this.mountingUp.setVisible(hardpointModule.findHigherMounting().isPresent());
                this.mountingDown.setVisible(hardpointModule.findLowerMounting().isPresent());
            }
            if (this.mountingLabel.getGraphic() != null) {
                ((Destroyable) this.mountingLabel.getGraphic()).destroy();
            }
            var graphic = createIconWithoutTooltip("/images/ships/icons/" + hardpointModule.getMounting().name().toLowerCase() + ".png", "icon");
            this.mountingLabel.setGraphic(graphic);
            this.mountingLabel.register(graphic);
        }
    }

    private static DestroyableResizableImageView createIconWithoutTooltip(String imageResource, String... styleClasses) {
        return ResizableImageViewBuilder.builder()
                .withStyleClasses(styleClasses)
                .withImage(imageResource)
                .build();
    }
}
