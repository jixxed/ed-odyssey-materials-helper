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
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;

import java.util.Optional;

@Slf4j
public class HandlingStats extends Stats implements Template {
    private final DestroyableLabel currentPitch;
    private final DestroyableLabel currentRoll;
    private final DestroyableLabel currentYaw;
    private final DestroyableLabel minimumPitch;
    private final DestroyableLabel minimumRoll;
    private final DestroyableLabel minimumYaw;
    private final DestroyableLabel maximumPitch;
    private final DestroyableLabel maximumRoll;
    private final DestroyableLabel maximumYaw;

    public HandlingStats() {
        super();
        initComponents();
        initEventHandling();
        final ModuleProfile moduleProfile = new ModuleProfile(0D, 0D, 0D, 0D, 0D, 0D);
        this.currentPitch = createValueLabel(String.format("%.2f", 0.0f));
        this.currentRoll = createValueLabel(String.format("%.2f", 0.0f));
        this.currentYaw = createValueLabel(String.format("%.2f", 0.0f));
        this.minimumPitch = createValueLabel(String.format("%.2f", 0.0f));
        this.minimumRoll = createValueLabel(String.format("%.2f", 0.0f));
        this.minimumYaw = createValueLabel(String.format("%.2f", 0.0f));
        this.maximumPitch = createValueLabel(String.format("%.2f", 0.0f));
        this.maximumRoll = createValueLabel(String.format("%.2f", 0.0f));
        this.maximumYaw = createValueLabel(String.format("%.2f", 0.0f));
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.handling.currentpitch"), new GrowingRegion(), this.currentPitch).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.handling.currentroll"), new GrowingRegion(), this.currentRoll).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.handling.currentyaw"), new GrowingRegion(), this.currentYaw).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.handling.minimumpitch"), new GrowingRegion(), this.minimumPitch).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.handling.minimumroll"), new GrowingRegion(), this.minimumRoll).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.handling.minimumyaw"), new GrowingRegion(), this.minimumYaw).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.handling.maximumpitch"), new GrowingRegion(), this.maximumPitch).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.handling.maximumroll"), new GrowingRegion(), this.maximumRoll).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.handling.maximumyaw"), new GrowingRegion(), this.maximumYaw).buildHBox());
    }

    private double calculatePitchCurrent(Ship ship, double pitchSpeed, ModuleProfile moduleProfile) {
        return pitchSpeed * getMassCurveMultiplier(ship.getEmptyMass()   + ship.getCurrentFuel(), moduleProfile) / 100;
    }

    private double calculateRollCurrent(Ship ship, double rollSpeed, ModuleProfile moduleProfile) {
        return rollSpeed * getMassCurveMultiplier(ship.getEmptyMass()   + ship.getCurrentFuel(), moduleProfile) / 100;
    }

    private double calculateYawCurrent(Ship ship, double yawSpeed, ModuleProfile moduleProfile) {
        return yawSpeed * getMassCurveMultiplier(ship.getEmptyMass()  + ship.getCurrentFuel(), moduleProfile) / 100;
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
//        document.getElementById('outfitting_stats_cur_pitch'    ).innerHTML = (isNaN(curHndRotMul) ? htmlErrorTH : formatAttrHTML('pitch', curHndRotMul * (pitch * powerdistEngMul + minpitch * (1 - powerdistEngMul))));
//        document.getElementById('outfitting_stats_cur_roll'     ).innerHTML = (isNaN(curHndRotMul) ? htmlErrorTH : formatAttrHTML('roll' , curHndRotMul * (roll  * powerdistEngMul + minroll  * (1 - powerdistEngMul))));
//        document.getElementById('outfitting_stats_cur_yaw'      ).innerHTML = (isNaN(curHndRotMul) ? htmlErrorTH : formatAttrHTML('yaw'  , curHndRotMul * (yaw   * powerdistEngMul + minyaw   * (1 - powerdistEngMul))));
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
            log.debug("update handling: " + this.getShip().isPresent());
            this.getShip().ifPresent(ship1 -> log.debug("type: " + ship1.getShipType()));
            this.currentPitch.setText(String.format("%.2f", calculatePitchCurrent(ship, (pitchSpeed * multiplier + minPitchSpeed * (1 - multiplier)), moduleProfile)));
            this.currentRoll.setText(String.format("%.2f", calculateRollCurrent(ship, (rollSpeed * multiplier + minRollSpeed * (1 - multiplier)), moduleProfile) ));
            this.currentYaw.setText(String.format("%.2f", calculateYawCurrent(ship, (yawSpeed * multiplier + minYawSpeed * (1 - multiplier)), moduleProfile)));
            this.minimumPitch.setText(String.format("%.2f", calculatePitchMinimum(ship, (minPitchSpeed), moduleProfile)));
            this.minimumRoll.setText(String.format("%.2f", calculateRollMinimum(ship, minRollSpeed, moduleProfile)));
            this.minimumYaw.setText(String.format("%.2f", calculateYawMinimum(ship, minYawSpeed, moduleProfile)));
            this.maximumPitch.setText(String.format("%.2f", calculatePitchMaximum(ship, pitchSpeed, moduleProfile)));
            this.maximumRoll.setText(String.format("%.2f", calculateRollMaximum(ship, rollSpeed, moduleProfile)));
            this.maximumYaw.setText(String.format("%.2f", calculateYawMaximum(ship, yawSpeed, moduleProfile)));
        });
    }

}
