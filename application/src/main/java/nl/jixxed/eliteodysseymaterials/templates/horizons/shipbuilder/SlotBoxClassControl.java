package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import nl.jixxed.eliteodysseymaterials.builder.FontAwesomeIconViewBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ShipConfiguration;
import nl.jixxed.eliteodysseymaterials.domain.ships.HardpointModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipService;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableFontAwesomeIconView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

public class SlotBoxClassControl extends DestroyableVBox implements DestroyableEventTemplate {
    private DestroyableLabel classLabel;
    private DestroyableFontAwesomeIconView classUp;
    private DestroyableFontAwesomeIconView classDown;
    private final SlotBox slotBox;

    public SlotBoxClassControl(SlotBox slotBox) {
        this.slotBox = slotBox;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("class-control");
        classLabel = LabelBuilder.builder()
                .withStyleClass("control-text")
                .build();
        if (!isCurrentShip()) {
            classUp = FontAwesomeIconViewBuilder.builder()
                    .withStyleClass("control-button")
                    .withIcon(FontAwesomeIcon.ANGLE_UP)
                    .withOnMouseClicked(event -> {
                        slotBox.getSlot().getShipModule().findHigherClass().ifPresent(slotBox::replaceModule);
                        event.consume();
                    })
                    .build();
            classDown = FontAwesomeIconViewBuilder.builder()
                    .withStyleClass("control-button")
                    .withIcon(FontAwesomeIcon.ANGLE_DOWN)
                    .withOnMouseClicked(event -> {
                        slotBox.getSlot().getShipModule().findLowerClass().ifPresent(slotBox::replaceModule);
                        event.consume();
                    })
                    .build();
            this.getNodes().addAll(classUp, classLabel, classDown);
        } else {
            this.getNodes().addAll(new GrowingRegion(), classLabel, new GrowingRegion());
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

    public void updateClass(ShipModule shipModule) {
        if (shipModule != null && !(shipModule instanceof HardpointModule)) {
            if (!isCurrentShip()) {
                this.classUp.setVisible(shipModule.findHigherClass().isPresent());
                this.classDown.setVisible(shipModule.findLowerClass().isPresent());
            }
            this.classLabel.setText(shipModule.getModuleClass().name());
        }
    }
}
