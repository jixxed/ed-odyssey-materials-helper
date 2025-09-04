package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.css.PseudoClass;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.EdAwesomeIconViewPaneBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.domain.ships.core_internals.PowerDistributor;
import nl.jixxed.eliteodysseymaterials.domain.ships.core_internals.PowerPlant;
import nl.jixxed.eliteodysseymaterials.domain.ships.core_internals.Thrusters;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.ShieldGenerator;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ModuleSelectHoverEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.EdAwesomeIconViewPane;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIcon;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIconView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableButton;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class ShipModuleButton extends DestroyableButton {
    private static final double BOOST_MARGIN = 0.005;
    private final ShipModule shipModule;

    public ShipModuleButton(SlotBox slotBox, ShipModule shipModule) {
        this.shipModule = shipModule;
        this.addEventBinding(this.onMouseEnteredProperty(), _ ->
                EventService.publish(new ModuleSelectHoverEvent(slotBox, shipModule, true)));
        this.addEventBinding(this.onMouseExitedProperty(), _ ->
                EventService.publish(new ModuleSelectHoverEvent(slotBox, null, false)));
        final List<EdAwesomeIconViewPane> icons = getIcons(shipModule);
        if (!icons.isEmpty()) {
            DestroyableHBox imagesBox = BoxBuilder.builder()
                    .withStyleClass("image-box")
                    .withNodes(icons.toArray(EdAwesomeIconViewPane[]::new))
                    .buildHBox();
            this.setGraphic(imagesBox);
            this.register(imagesBox);

        }
        checkOverload(shipModule);
    }

    private void checkOverload(ShipModule shipModule) {
        switch (shipModule) {
            case ShieldGenerator shieldGenerator -> {
                double maxMass = ApplicationState.getInstance().getShip().getMaximumMass();
                final double maxMassForModule = (double) shieldGenerator.getAttributeValue(HorizonsModifier.SHIELDGEN_MAXIMUM_MASS, false);
                this.pseudoClassStateChanged(PseudoClass.getPseudoClass("overload"), maxMass > maxMassForModule);
//                if (maxMass > maxMassForModule) {
//                    this.getStyleClass().add("module-button-overload");
//                }
            }
            case Thrusters thrusters -> {
                double maxMass = ApplicationState.getInstance().getShip().getMaximumMass();
                final double maxMassForModule = (double) thrusters.getAttributeValue(HorizonsModifier.MAXIMUM_MASS, false);
                this.pseudoClassStateChanged(PseudoClass.getPseudoClass("overload"), maxMass > maxMassForModule);
//                if (maxMass > maxMassForModule) {
//                    this.getStyleClass().add("module-button-overload");
//                }
            }
            case PowerPlant powerPlant -> {
                final PowerProfile powerProfile = ApplicationState.getInstance().getShip().getRetractedPower();
                double usedPower = powerProfile.usedPower();
                final double available = (double) powerPlant.getAttributeValue(HorizonsModifier.POWER_CAPACITY, false) - usedPower;
                this.pseudoClassStateChanged(PseudoClass.getPseudoClass("overload"), available < 0D);
//                if (available < 0D) {
//                    this.getStyleClass().add("module-button-overload");
//                }
            }
            case PowerDistributor powerDistributor -> {
                final double engineCapacity = (double) powerDistributor.getAttributeValue(HorizonsModifier.ENGINES_CAPACITY, false);
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

    private static List<EdAwesomeIconViewPane> getIcons(ShipModule shipModule) {
        List<EdAwesomeIconViewPane> images = new ArrayList<>();
        if (shipModule instanceof ExternalModule externalModule && !externalModule.getMounting().equals(Mounting.NA)) {
            images.add(createImage("module-image", externalModule.getMounting().getIcon()));
        }
        if (Origin.GUARDIAN.equals(shipModule.getOrigin()) ) {
            images.add(createImage("module-image", EdAwesomeIcon.SHIPS_GUARDIAN_1, EdAwesomeIcon.SHIPS_GUARDIAN_2));
        }
        if (Origin.POWERPLAY.equals(shipModule.getOrigin())) {
            images.add(createImage("module-image", EdAwesomeIcon.OTHER_POWERPLAY_OPEN));
        }
        if (shipModule.isLegacy()) images.add(createImage("module-image", EdAwesomeIcon.SHIPS_LEGACY));
        if (shipModule.isPreEngineered()) images.add(createImage("module-image", EdAwesomeIcon.SHIPS_PREENGINEERED));
        if (shipModule.isStoreExclusive()) images.add(createImage("module-image", EdAwesomeIcon.OTHER_ARX));
        if (shipModule.isAdvanced()) images.add(createImage("module-image-wide", EdAwesomeIcon.SHIPS_ADVANCED));
        if (shipModule.isEnhanced()) images.add(createImage("module-image-wide", EdAwesomeIcon.SHIPS_ENHANCED));
        if (shipModule.isDumbfire()) images.add(createImage("module-image-wide", EdAwesomeIcon.SHIPS_DUMBFIRE));
        if (shipModule.isSeeker()) images.add(createImage("module-image-wide", EdAwesomeIcon.SHIPS_SEEKER));
        if (shipModule.isCGExclusive()) images.add(createImage("module-image", EdAwesomeIcon.OTHER_COMMUNITYGOAL));
        return images;
    }

    private static EdAwesomeIconViewPane createImage(String styleClass, EdAwesomeIcon ... icons) {
        return EdAwesomeIconViewPaneBuilder.builder()
                .withIcons(Arrays.stream(icons).map(EdAwesomeIconView::new).toArray(EdAwesomeIconView[]::new))
                .withStyleClass(styleClass)
                .build();
    }
}
