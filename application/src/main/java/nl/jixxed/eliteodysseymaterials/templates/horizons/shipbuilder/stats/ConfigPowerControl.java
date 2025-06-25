package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.css.PseudoClass;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.FontAwesomeIconViewBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ShipConfiguration;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipBuilderEvent;
import nl.jixxed.eliteodysseymaterials.service.ships.ShipService;
import nl.jixxed.eliteodysseymaterials.templates.components.FontAwesomeIconViewPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableFontAwesomeIconView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;

@Slf4j
public class ConfigPowerControl extends DestroyableHBox implements DestroyableEventTemplate {

    private DestroyableFontAwesomeIconView power;
    private DestroyableLabel powerLabel;
    private DestroyableHBox powerIconAndLabel;
    private DestroyableFontAwesomeIconView powerUp;
    private DestroyableFontAwesomeIconView powerDown;

    public ConfigPowerControl() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("power-control");
        power = FontAwesomeIconViewBuilder.builder()
                .withStyleClass("icon")
                .withIcon(FontAwesomeIcon.POWER_OFF)
                .build();
        powerLabel = LabelBuilder.builder()
                .withStyleClass("control-text")
                .withNonLocalizedText(String.valueOf(0))
                .build();
        FontAwesomeIconViewPane fontAwesomeIconViewPane = new FontAwesomeIconViewPane(this.power);
        powerIconAndLabel = BoxBuilder.builder()
                .withStyleClass("power-icon-and-label")
                .withNodes(fontAwesomeIconViewPane, this.powerLabel)
                .withOnMouseClicked(event -> {
                    if (!isCurrentShip()) {
                        ApplicationState.getInstance().getShip().getCargoHatch().getShipModule().togglePower();
                        notifyChanged();
                        updatePower(ApplicationState.getInstance().getShip().getCargoHatch().getShipModule());
                    }
                })
                .buildHBox();
        powerIconAndLabel.setOnMousePressed(_ -> powerIconAndLabel.pseudoClassStateChanged(PseudoClass.getPseudoClass("pressed"), true));
        powerIconAndLabel.setOnMouseReleased(_ -> powerIconAndLabel.pseudoClassStateChanged(PseudoClass.getPseudoClass("pressed"), false));
        powerDown = FontAwesomeIconViewBuilder.builder()
                .withStyleClass("control-button")
                .withIcon(FontAwesomeIcon.ANGLE_LEFT)
                .withOnMouseClicked(event -> {
                    if (!isCurrentShip()) {
                        ApplicationState.getInstance().getShip().getCargoHatch().getShipModule().decreasePowerGroup();
                        notifyChanged();
                        updatePower(ApplicationState.getInstance().getShip().getCargoHatch().getShipModule());
                    }
                })
                .build();
        powerUp = FontAwesomeIconViewBuilder.builder()
                .withStyleClass("control-button")
                .withIcon(FontAwesomeIcon.ANGLE_RIGHT)
                .withOnMouseClicked(event -> {
                    if (!isCurrentShip()) {
                        ApplicationState.getInstance().getShip().getCargoHatch().getShipModule().increasePowerGroup();
                        notifyChanged();
                        updatePower(ApplicationState.getInstance().getShip().getCargoHatch().getShipModule());
                    }
                })
                .build();
        this.getNodes().addAll(new FontAwesomeIconViewPane(powerDown), powerIconAndLabel, new FontAwesomeIconViewPane(powerUp));
        if (ApplicationState.getInstance().getShip() != null) {
            updatePower(ApplicationState.getInstance().getShip().getCargoHatch().getShipModule());
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

    public void updatePower(ShipModule shipModule) {
        log.debug("Updating power for {}", shipModule);
        if (shipModule != null) {
//            this.power.setImage(ImageService.getImage("/images/ships/icons/" + (shipModule.isPowered() ? "powered" : "unpowered") + shipModule.getPowerGroup() + ".png"));
            this.powerLabel.setText(String.valueOf(shipModule.getPowerGroup()));
            powerIconAndLabel.pseudoClassStateChanged(PseudoClass.getPseudoClass("powered"), shipModule.isPowered());
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass("configurable"), !isCurrentShip());
            log.debug("Powered is changed to {}", shipModule.isPowered());
            log.debug("Configurable is changed to {}", !isCurrentShip());
//            if (!isCurrentShip()) {
//                this.powerLabel.setGraphic(this.power);
//                this.powerLabel.register(this.power);
//            }
            final boolean hasPowerToggle = shipModule.hasPowerToggle();
            if (!isCurrentShip()) {
                final int powerGroup = shipModule.getPowerGroup();
                this.powerUp.setManaged(true);
                this.powerDown.setManaged(true);
                this.powerUp.setVisible(hasPowerToggle && powerGroup < 5);
                this.powerDown.setVisible(hasPowerToggle && powerGroup > 1);
                this.powerIconAndLabel.setVisible(hasPowerToggle);
            } else {
                this.powerUp.setVisible(false);
                this.powerDown.setVisible(false);
                this.powerUp.setManaged(false);
                this.powerDown.setManaged(false);
                this.powerIconAndLabel.setVisible(hasPowerToggle);
//                this.powerBox.getNodes().clear();
//                this.powerBox.getNodes().addAll(new GrowingRegion(), this.power, new GrowingRegion());
            }
        }
    }

    private void notifyChanged() {
        EventService.publish(new ShipBuilderEvent());
//        update();
    }
//    private static DestroyableResizableImageView createIconWithTooltip(String imageResource, Integer powerGroup, String... styleClasses) {
//        final DestroyableResizableImageView icon = createIconWithoutTooltip(imageResource, styleClasses);
//        final DestroyableTooltip tooltip = TooltipBuilder.builder()
//                .withShowDelay(Duration.seconds(0.1))
//                .withText("ship.stats.config.power.group", powerGroup)
//                .build();
//        tooltip.install(icon);
//        return icon;
//    }
//
//    private static DestroyableResizableImageView createIconWithoutTooltip(String imageResource, String... styleClasses) {
//        return ResizableImageViewBuilder.builder()
//                .withStyleClasses(styleClasses)
//                .withImage(imageResource)
//                .build();
//    }
}
