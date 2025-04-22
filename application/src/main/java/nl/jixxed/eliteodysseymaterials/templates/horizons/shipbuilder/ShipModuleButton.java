package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.css.PseudoClass;
import javafx.scene.layout.HBox;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ships.ExternalModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.Mounting;
import nl.jixxed.eliteodysseymaterials.domain.ships.Origin;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.core_internals.PowerDistributor;
import nl.jixxed.eliteodysseymaterials.domain.ships.core_internals.PowerPlant;
import nl.jixxed.eliteodysseymaterials.domain.ships.core_internals.Thrusters;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.ShieldGenerator;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ModuleHighlightEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableButton;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class ShipModuleButton extends DestroyableButton {
    private static final double BOOST_MARGIN = 0.005;
    private final ShipModule shipModule;

    public ShipModuleButton(ShipModule shipModule) {
        this.shipModule = shipModule;
        this.addEventBinding(this.onMouseEnteredProperty(), _ ->
                EventService.publish(new ModuleHighlightEvent(shipModule)));
        final List<DestroyableResizableImageView> icons = getIcons(shipModule);
        if (!icons.isEmpty()) {
            HBox imagesBox = BoxBuilder.builder()
                    .withStyleClass("ships-button-image-box")
                    .withNodes(icons.toArray(DestroyableResizableImageView[]::new))
                    .buildHBox();
            this.setGraphic(imagesBox);

        }
        checkOverload(shipModule);
    }

    private void checkOverload(ShipModule shipModule) {
        switch (shipModule) {
            case ShieldGenerator shieldGenerator -> {
                double maxMass = ApplicationState.getInstance().getShip().getMaximumMass();
                final double maxMassForModule = (double) shieldGenerator.getAttributeValue(HorizonsModifier.SHIELDGEN_MAXIMUM_MASS);
                this.pseudoClassStateChanged(PseudoClass.getPseudoClass("overload"), maxMass > maxMassForModule);
//                if (maxMass > maxMassForModule) {
//                    this.getStyleClass().add("module-button-overload");
//                }
            }
            case Thrusters thrusters -> {
                double maxMass = ApplicationState.getInstance().getShip().getMaximumMass();
                final double maxMassForModule = (double) thrusters.getAttributeValue(HorizonsModifier.MAXIMUM_MASS);
                this.pseudoClassStateChanged(PseudoClass.getPseudoClass("overload"), maxMass > maxMassForModule);
//                if (maxMass > maxMassForModule) {
//                    this.getStyleClass().add("module-button-overload");
//                }
            }
            case PowerPlant powerPlant -> {
                final Map<Integer, Double> power = ApplicationState.getInstance().getShip().getRetractedPower();
                double usedPower = power.get(1) + power.get(2) + power.get(3) + power.get(4) + power.get(5);
                final double available = (double) powerPlant.getAttributeValue(HorizonsModifier.POWER_CAPACITY) - usedPower;
                this.pseudoClassStateChanged(PseudoClass.getPseudoClass("overload"), available < 0D);
//                if (available < 0D) {
//                    this.getStyleClass().add("module-button-overload");
//                }
            }
            case PowerDistributor powerDistributor -> {
                final double engineCapacity = (double) powerDistributor.getAttributeValue(HorizonsModifier.ENGINES_CAPACITY);
                final double boostCost = (double) ApplicationState.getInstance().getShip().getAttributes().getOrDefault(HorizonsModifier.BOOST_COST, 0D);
                final boolean engineCapacityEnough = engineCapacity > boostCost + BOOST_MARGIN;
                this.pseudoClassStateChanged(PseudoClass.getPseudoClass("overload"), !engineCapacityEnough);
//                if (!engineCapacityEnough) {
//                    this.getStyleClass().add("module-button-overload");
//                }
            }
            case null, default -> {
            }
        }
    }

    private static List<DestroyableResizableImageView> getIcons(ShipModule shipModule) {
        List<DestroyableResizableImageView> images = new ArrayList<>();
        if (shipModule instanceof ExternalModule externalModule && !externalModule.getMounting().equals(Mounting.NA)) {
            images.add(createImage("/images/ships/icons/" + externalModule.getMounting().name().toLowerCase() + ".png"));
        }
        if (Origin.GUARDIAN.equals(shipModule.getOrigin()) || Origin.POWERPLAY.equals(shipModule.getOrigin())) {
            images.add(createImage("/images/ships/icons/" + shipModule.getOrigin().name().toLowerCase() + ".png"));
        }
        if (shipModule.isLegacy()) images.add(createImage("/images/ships/icons/legacy.png"));
        if (shipModule.isPreEngineered()) images.add(createImage("/images/ships/icons/preengineered.png"));
        if (shipModule.isStoreExclusive()) images.add(createImage("/images/ships/icons/arx.png"));
        if (shipModule.isAdvanced()) images.add(createImage("/images/ships/icons/advanced2.png"));
        if (shipModule.isEnhanced()) images.add(createImage("/images/ships/icons/enhanced2.png"));
        if (shipModule.isDumbfire()) images.add(createImage("/images/ships/icons/dumb2.png"));
        if (shipModule.isSeeker()) images.add(createImage("/images/ships/icons/seeker2.png"));
        if (shipModule.isCGExclusive()) images.add(createImage("/images/ships/icons/cg.png"));
        return images;
    }

    private static DestroyableResizableImageView createImage(String imageResource) {
        return ResizableImageViewBuilder.builder()
                .withImage(imageResource)
                .withStyleClass("ships-button-image")
                .build();
    }
}
