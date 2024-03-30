package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.domain.ships.Slot;
import nl.jixxed.eliteodysseymaterials.domain.ships.SlotType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipConfigEvent;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;

import java.util.Optional;

@Slf4j
public class HandlingStats extends Stats implements Template {
    private RangeIndicator pitchIndicator;
    private RangeIndicator rollIndicator;
    private RangeIndicator yawIndicator;

    public HandlingStats() {
        super();
        initComponents();
        initEventHandling();

        pitchIndicator = new RangeIndicator(0D, 0D, 0D, "ship.stats.handling.pitch", "ship.stats.handling.pitch.value");
        this.getChildren().add(pitchIndicator);
        rollIndicator = new RangeIndicator(0D, 0D, 0D, "ship.stats.handling.roll", "ship.stats.handling.roll.value");
        this.getChildren().add(rollIndicator);
        yawIndicator = new RangeIndicator(0D, 0D, 0D, "ship.stats.handling.yaw", "ship.stats.handling.yaw.value");
        this.getChildren().add(yawIndicator);
    }

    private double calculatePitchCurrent(Ship ship, double pitchSpeed, ModuleProfile moduleProfile) {
        return pitchSpeed * getMassCurveMultiplier(ship.getEmptyMass() + ship.getCurrentFuel(), moduleProfile) / 100;
    }

    private double calculateRollCurrent(Ship ship, double rollSpeed, ModuleProfile moduleProfile) {
        return rollSpeed * getMassCurveMultiplier(ship.getEmptyMass() + ship.getCurrentFuel(), moduleProfile) / 100;
    }

    private double calculateYawCurrent(Ship ship, double yawSpeed, ModuleProfile moduleProfile) {
        return yawSpeed * getMassCurveMultiplier(ship.getEmptyMass() + ship.getCurrentFuel(), moduleProfile) / 100;
    }

    private double calculatePitchMinimum(Ship ship, double pitchSpeed, ModuleProfile moduleProfile) {
        return pitchSpeed * getMassCurveMultiplier(ship.getEmptyMass() + ship.getMaxFuel() + ship.getMaxCargo() + ship.getCurrentFuelReserve(), moduleProfile) / 100;
    }

    private double calculateRollMinimum(Ship ship, double rollSpeed, ModuleProfile moduleProfile) {
        return rollSpeed * getMassCurveMultiplier(ship.getEmptyMass() + ship.getMaxFuel() + ship.getMaxCargo() + ship.getCurrentFuelReserve(), moduleProfile) / 100;
    }

    private double calculateYawMinimum(Ship ship, double yawSpeed, ModuleProfile moduleProfile) {
        return yawSpeed * getMassCurveMultiplier(ship.getEmptyMass() + ship.getMaxFuel() + ship.getMaxCargo() + ship.getCurrentFuelReserve(), moduleProfile) / 100;
    }

    private double calculatePitchMaximum(Ship ship, double pitchSpeed, ModuleProfile moduleProfile) {
        return pitchSpeed * getMassCurveMultiplier(ship.getEmptyMass(), moduleProfile) / 100;
    }

    private double calculateRollMaximum(Ship ship, double rollSpeed, ModuleProfile moduleProfile) {
        return rollSpeed * getMassCurveMultiplier(ship.getEmptyMass(), moduleProfile) / 100;
    }

    private double calculateYawMaximum(Ship ship, double yawSpeed, ModuleProfile moduleProfile) {
        return yawSpeed * getMassCurveMultiplier(ship.getEmptyMass(), moduleProfile) / 100;
    }

    @Override
    public void initComponents() {
        this.getChildren().add(BoxBuilder.builder().withNodes(new GrowingRegion(), createTitle("ship.stats.handling"), new GrowingRegion()).buildHBox());
        this.getChildren().add(new Separator(Orientation.HORIZONTAL));

    }

    @Override
    public void initEventHandling() {
        eventListeners.add(EventService.addListener(this, ShipConfigEvent.class, event -> update()));
    }

    private static Double getMaximumMultiplier(Optional<Slot> thrusters) {
        return thrusters.map(Slot::getShipModule)
                .flatMap(shipModule -> { //enhanced thrusters
                    if (shipModule.getAttibutes().contains(HorizonsModifier.MAXIMUM_MULTIPLIER_ROTATION)) {
                        final Double rotation = (Double) shipModule.getAttributeValue(HorizonsModifier.MAXIMUM_MULTIPLIER_ROTATION);
                        return Optional.of(rotation);
                    }
                    return Optional.empty();
                })
                .orElseGet(() -> thrusters.map(Slot::getShipModule).map(shipModule -> (Double) shipModule.getAttributeValue(HorizonsModifier.MAXIMUM_MULIPLIER)).orElse(0D));
    }

    private static Double getOptimalMultiplier(Optional<Slot> thrusters) {
        return thrusters.map(Slot::getShipModule)
                .flatMap(shipModule -> { //enhanced thrusters
                    if (shipModule.getAttibutes().contains(HorizonsModifier.OPTIMAL_MULTIPLIER_ROTATION)) {
                        final Double rotation = (Double) shipModule.getAttributeValue(HorizonsModifier.OPTIMAL_MULTIPLIER_ROTATION);
                        return Optional.of(rotation);
                    }
                    return Optional.empty();
                })
                .orElseGet(() -> thrusters.map(Slot::getShipModule).map(shipModule -> (Double) shipModule.getAttributeValue(HorizonsModifier.OPTIMAL_MULTIPLIER)).orElse(0D));
    }

    private static Double getMinimumMultiplier(Optional<Slot> thrusters) {
        return thrusters.map(Slot::getShipModule)
                .flatMap(shipModule -> { //enhanced thrusters
                    if (shipModule.getAttibutes().contains(HorizonsModifier.MINIMUM_MULTIPLIER_ROTATION)) {
                        final Double rotation = (Double) shipModule.getAttributeValue(HorizonsModifier.MINIMUM_MULTIPLIER_ROTATION);
                        return Optional.of(rotation);
                    }
                    return Optional.empty();
                })
                .orElseGet(() -> thrusters.map(Slot::getShipModule).map(shipModule -> (Double) shipModule.getAttributeValue(HorizonsModifier.MINIMUM_MULTIPLIER)).orElse(0D));
    }

    @Override
    protected void update() {
        getShip().ifPresent(ship -> {
            final Optional<Slot> thrusters = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.CORE_THRUSTERS)).findFirst();
            final Double minimumMass = (Double) thrusters.map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.ENGINE_MINIMUM_MASS)).orElse(0D);
            final Double optimalMass = (Double) thrusters.map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.ENGINE_OPTIMAL_MASS)).orElse(0D);
            final Double maximumMass = (Double) thrusters.map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.MAXIMUM_MASS)).orElse(0D);
            final Double minimumMultiplier = getMinimumMultiplier(thrusters);
            final Double optimalMultiplier = getOptimalMultiplier(thrusters);
            final Double maximumMultiplier = getMaximumMultiplier(thrusters);
            final Double pitchSpeed = (Double) ship.getAttributes().getOrDefault(HorizonsModifier.PITCH_SPEED, 0.0D);
            final Double yawSpeed = (Double) ship.getAttributes().getOrDefault(HorizonsModifier.YAW_SPEED, 0.0D);
            final Double rollSpeed = (Double) ship.getAttributes().getOrDefault(HorizonsModifier.ROLL_SPEED, 0.0D);
            final Double minPitchSpeed = (Double) ship.getAttributes().getOrDefault(HorizonsModifier.MIN_PITCH_SPEED, 0.0D);
            final Double minYawSpeed = yawSpeed;//reported to be unaffected by pips
            final Double minRollSpeed = rollSpeed;//reported to be unaffected by pips
            final double multiplier = ApplicationState.getInstance().getEnginePips() / 8.0;
            final ModuleProfile moduleProfile = new ModuleProfile(minimumMass, optimalMass, maximumMass, minimumMultiplier, optimalMultiplier, maximumMultiplier);
            var currentPitch = calculatePitchCurrent(ship, (pitchSpeed * multiplier + minPitchSpeed * (1 - multiplier)), moduleProfile);
            var currentRoll = calculateRollCurrent(ship, (rollSpeed * multiplier + minRollSpeed * (1 - multiplier)), moduleProfile);
            var currentYaw = calculateYawCurrent(ship, (yawSpeed * multiplier + minYawSpeed * (1 - multiplier)), moduleProfile);
            var minimumPitch = calculatePitchMinimum(ship, (minPitchSpeed), moduleProfile);
            var minimumRoll = calculateRollMinimum(ship, minRollSpeed, moduleProfile);
            var minimumYaw = calculateYawMinimum(ship, minYawSpeed, moduleProfile);
            var maximumPitch = calculatePitchMaximum(ship, pitchSpeed, moduleProfile);
            var maximumRoll = calculateRollMaximum(ship, rollSpeed, moduleProfile);
            var maximumYaw = calculateYawMaximum(ship, yawSpeed, moduleProfile);


            this.pitchIndicator.updateValues(minimumPitch, currentPitch, maximumPitch);
            this.rollIndicator.updateValues(minimumRoll, currentRoll, maximumRoll);
            this.yawIndicator.updateValues(minimumYaw, currentYaw, maximumYaw);

        });
    }

}
