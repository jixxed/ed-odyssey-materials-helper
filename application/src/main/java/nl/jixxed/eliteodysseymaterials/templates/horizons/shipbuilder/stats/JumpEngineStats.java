package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.domain.ships.Slot;
import nl.jixxed.eliteodysseymaterials.domain.ships.SlotType;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.FrameShiftDriveBooster;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipConfigEvent;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;

import java.util.Optional;
import java.util.Set;

@Slf4j
public class JumpEngineStats extends Stats implements Template {
    private static final double BOOST_MARGIN = 0.005;
//    private DestroyableLabel minJumpRange;
//    private DestroyableLabel maxJumpRange;
//    private DestroyableLabel maxFueledJumpRange;
//    private DestroyableLabel currentJumpRange;
    private RangeIndicator jumpRangeIndicator;
    private RangeIndicator speedIndicator;
    private RangeIndicator boostIndicator;
    private RangeIndicator rechargeIndicator;

    public JumpEngineStats() {
        super();
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getChildren().add(BoxBuilder.builder().withNodes(new GrowingRegion(), createTitle("ship.stats.jumprange"), new GrowingRegion()).buildHBox());
        this.getChildren().add(new Separator(Orientation.HORIZONTAL));
//        this.minJumpRange = createValueLabel(String.format("%.2f", calculateJumpRangeMin()));
//        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.jumprange.min"), new GrowingRegion(),this.minJumpRange).buildHBox());

//        this.currentJumpRange = createValueLabel(String.format("%.2f", calculateJumpRangeCurrent()));
//        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.jumprange.current"), new GrowingRegion(), this.currentJumpRange).buildHBox());
//
//        this.maxFueledJumpRange = createValueLabel(String.format("%.2f", calculateJumpRangeMaxFueled()));
//        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.jumprange.maxfueled"), new GrowingRegion(), this.maxFueledJumpRange).buildHBox());
//
//        this.maxJumpRange = createValueLabel(String.format("%.2f", calculateJumpRangeMax()));
//        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.jumprange.max"), new GrowingRegion(), this.maxJumpRange).buildHBox());
        jumpRangeIndicator = new RangeIndicator(calculateJumpRangeMin(), calculateJumpRangeCurrent(), calculateJumpRangeMax(), "ship.stats.jumprange", "");
        this.getChildren().add(jumpRangeIndicator);


        speedIndicator = new RangeIndicator(0D,0D,0D, "ship.stats.engine.speed", "");
        boostIndicator = new RangeIndicator(0D,0D,0D, "ship.stats.engine.boost", "");
        rechargeIndicator = new RangeIndicator(0D,0D,0D, "ship.stats.engine.recharge", "");
        this.getChildren().add(BoxBuilder.builder().withNodes(new GrowingRegion(), createTitle("ship.stats.engine"), new GrowingRegion()).buildHBox());
        this.getChildren().add(new Separator(Orientation.HORIZONTAL));
        this.getChildren().add(speedIndicator);
        this.getChildren().add(boostIndicator);
        this.getChildren().add(rechargeIndicator);
    }
    @Override
    public void initEventHandling() {
        eventListeners.add(EventService.addListener(this, ShipConfigEvent.class, event -> update()));
    }


    public double calculateJumpRangeMin() {
        //added ship.getMaxFuelReserve() compared to ingame value
        return getShip().map(ship -> calculateJumpRange(ship.getEmptyMass() + ship.getMaxFuelReserve() + ship.getMaxCargo() + ship.getMaxFuel(), ship.getMaxFuel())).orElse(0.0D);
    }

    public double calculateJumpRangeCurrent() {
        return getShip().map(ship -> calculateJumpRange(ship.getEmptyMass() + ship.getCurrentCargo() + ship.getCurrentFuel() + ship.getCurrentFuelReserve(), ship.getCurrentFuel())).orElse(0.0D);
    }

    public double calculateJumpRangeMax() {
        return getShip().map(ship -> {
            final double fuel = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.MAX_FUEL_PER_JUMP, 1D)).orElse(1D);
            return calculateJumpRange(ship.getEmptyMass() + fuel, fuel);
        }).orElse(0.0D);
    }

    public double calculateJumpRangeMaxFueled() {
        return getShip().map(ship -> calculateJumpRange(ship.getEmptyMass() + ship.getMaxFuel(), ship.getMaxFuel())).orElse(0.0D);
    }

    public double calculateJumpRange(final double mass, final double fuel) {
        return getShip().map(ship -> {
            final double maxFuelPerJump = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.MAX_FUEL_PER_JUMP, 1D)).orElse(1D);
            final double optimisedMass = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.FSD_OPTIMISED_MASS, 1D)).orElse(1D);
            final double fuelMultiplier = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.FUEL_MULTIPLIER, 1D)).orElse(1D);
            final double fuelPower = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.FUEL_POWER, 1D)).orElse(1D);
            final double jumpRangeIncrease = ship.getOptionalSlots().stream().filter(slot -> slot.getShipModule() instanceof FrameShiftDriveBooster).findFirst().map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.JUMP_RANGE_INCREASE, 1D)).orElse(0D);
            return calculateJumpDistance(mass, Math.min(fuel, maxFuelPerJump), optimisedMass, fuelMultiplier, fuelPower, jumpRangeIncrease);
        }).orElse(0.0D);
    }

    private double calculateJumpDistance(final double mass, final double fuel, final double optimisedMass, final double fuelMultiplier, final double fuelPower, final double jumpRangeIncrease) {
        return Math.pow(fuel / fuelMultiplier, 1 / fuelPower) * optimisedMass / mass + jumpRangeIncrease;
    }

    private double calculateMinRecharge(double boostCost, double engineRecharge) {
        return boostCost / engineRecharge;
    }
    private double calculateMaxRecharge(double boostCost, double engineRecharge, double multiplier) {
        return boostCost /  (engineRecharge * Math.pow(multiplier > 0 ? 1D/8D : 0D, 1.1));
    }

    private double calculateMaxSpeed(final Ship ship, final double speed, final ModuleProfile moduleProfile) {
        return speed * getMassCurveMultiplier(ship.getEmptyMass(), moduleProfile) / 100D;
    }


    private double calculateCurrentRecharge(double boostCost, double engineRecharge, double multiplier) {
        return boostCost / (engineRecharge * Math.pow(multiplier, 1.1));
    }


    private double calculateCurrentSpeed(Ship ship, Double speed, ModuleProfile moduleProfile, double multiplier) {
        final Double minimumThrust = (Double) ship.getAttributes().getOrDefault(HorizonsModifier.MINIMUM_THRUST, 0.0D);
        return speed * (getMassCurveMultiplier(ship.getEmptyMass() + ship.getCurrentFuel() + ship.getCurrentCargo() + ship.getCurrentFuelReserve(), moduleProfile) / 100D) * (multiplier + (minimumThrust / 100D) * (1D - multiplier));
    }

    private double calculateMinSpeed(Ship ship, Double speed, ModuleProfile moduleProfile) {
        final Double minimumThrust = (Double) ship.getAttributes().getOrDefault(HorizonsModifier.MINIMUM_THRUST, 0.0D);
        return speed * (getMassCurveMultiplier(ship.getEmptyMass() + ship.getMaxFuel() + ship.getMaxCargo() + ship.getCurrentFuelReserve(), moduleProfile) / 100D) * (minimumThrust / 100D);
    }

    private static Double getMaximumMultiplier(Optional<Slot> thrusters) {
        return thrusters.map(Slot::getShipModule)
                .flatMap(shipModule -> { //enhanced thrusters
                    if (shipModule.getAttibutes().containsAll(Set.of(HorizonsModifier.MAXIMUM_MULTIPLIER_SPEED, HorizonsModifier.MAXIMUM_MULTIPLIER_ROTATION, HorizonsModifier.MAXIMUM_MULTIPLIER_ACCELERATION))) {
                        final Double speed = (Double) shipModule.getAttributeValue(HorizonsModifier.MAXIMUM_MULTIPLIER_SPEED);
                        final Double rotation = (Double) shipModule.getAttributeValue(HorizonsModifier.MAXIMUM_MULTIPLIER_ROTATION);
                        final Double acceleration = (Double) shipModule.getAttributeValue(HorizonsModifier.MAXIMUM_MULTIPLIER_ACCELERATION);
                        return Optional.of(speed + rotation + acceleration / 3.0);
                    }
                    return Optional.empty();
                })
                .orElseGet(() -> thrusters.map(Slot::getShipModule).map(shipModule -> (Double) shipModule.getAttributeValue(HorizonsModifier.MAXIMUM_MULIPLIER)).orElse(0D));
    }

    private static Double getOptimalMultiplier(Optional<Slot> thrusters) {
        return thrusters.map(Slot::getShipModule)
                .flatMap(shipModule -> { //enhanced thrusters
                    if (shipModule.getAttibutes().containsAll(Set.of(HorizonsModifier.OPTIMAL_MULTIPLIER_SPEED, HorizonsModifier.OPTIMAL_MULTIPLIER_ROTATION, HorizonsModifier.OPTIMAL_MULTIPLIER_ACCELERATION))) {
                        final Double speed = (Double) shipModule.getAttributeValue(HorizonsModifier.OPTIMAL_MULTIPLIER_SPEED);
                        final Double rotation = (Double) shipModule.getAttributeValue(HorizonsModifier.OPTIMAL_MULTIPLIER_ROTATION);
                        final Double acceleration = (Double) shipModule.getAttributeValue(HorizonsModifier.OPTIMAL_MULTIPLIER_ACCELERATION);
                        return Optional.of(speed + rotation + acceleration / 3.0);
                    }
                    return Optional.empty();
                })
                .orElseGet(() -> thrusters.map(Slot::getShipModule).map(shipModule -> (Double) shipModule.getAttributeValue(HorizonsModifier.OPTIMAL_MULTIPLIER)).orElse(0D));
    }

    private static Double getMinimumMultiplier(Optional<Slot> thrusters) {
        return thrusters.map(Slot::getShipModule)
                .flatMap(shipModule -> { //enhanced thrusters
                    if (shipModule.getAttibutes().containsAll(Set.of(HorizonsModifier.MINIMUM_MULTIPLIER_SPEED, HorizonsModifier.MINIMUM_MULTIPLIER_ROTATION, HorizonsModifier.MINIMUM_MULTIPLIER_ACCELERATION))) {
                        final Double speed = (Double) shipModule.getAttributeValue(HorizonsModifier.MINIMUM_MULTIPLIER_SPEED);
                        final Double rotation = (Double) shipModule.getAttributeValue(HorizonsModifier.MINIMUM_MULTIPLIER_ROTATION);
                        final Double acceleration = (Double) shipModule.getAttributeValue(HorizonsModifier.MINIMUM_MULTIPLIER_ACCELERATION);
                        return Optional.of(speed + rotation + acceleration / 3.0);
                    }
                    return Optional.empty();
                })
                .orElseGet(() -> thrusters.map(Slot::getShipModule).map(shipModule -> (Double) shipModule.getAttributeValue(HorizonsModifier.MINIMUM_MULTIPLIER)).orElse(0D));
    }

    @Override
    protected void update() {
//        log.debug("update ranges: " + this.getShip().isPresent());
//        this.getShip().ifPresent(ship1 ->  log.debug("type: " + ship1.getShipType()));
//        this.minJumpRange.setText(String.format("%.2f", calculateJumpRangeMin()));
//        this.currentJumpRange.setText(String.format("%.2f", calculateJumpRangeCurrent()));
//        this.maxFueledJumpRange.setText(String.format("%.2f", calculateJumpRangeMaxFueled()));
//        this.maxJumpRange.setText(String.format("%.2f", calculateJumpRangeMax()));
        this.jumpRangeIndicator.updateValues(calculateJumpRangeMin(), calculateJumpRangeCurrent(), calculateJumpRangeMax());

        getShip().ifPresent(ship -> {
        final Optional<Slot> thrusters = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.CORE_THRUSTERS)).findFirst();
        final Optional<Slot> powerDistributor = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.CORE_POWER_DISTRIBUTION)).findFirst();
        final Double minimumMass = (Double) thrusters.map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.ENGINE_MINIMUM_MASS)).orElse(0D);
        final Double optimalMass = (Double) thrusters.map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.ENGINE_OPTIMAL_MASS)).orElse(0D);
        final Double maximumMass = (Double) thrusters.map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.MAXIMUM_MASS)).orElse(0D);
        final Double minimumMultiplier = getMinimumMultiplier(thrusters);
        final Double optimalMultiplier = getOptimalMultiplier(thrusters);
        final Double maximumMultiplier = getMaximumMultiplier(thrusters);
        final Double topSpeed = (Double) ship.getAttributes().getOrDefault(HorizonsModifier.TOP_SPEED, 0.0D);
        final Double boostSpeed = (Double) ship.getAttributes().getOrDefault(HorizonsModifier.BOOST_SPEED, 0.0D);
        final ModuleProfile moduleProfile = new ModuleProfile(minimumMass, optimalMass, maximumMass, minimumMultiplier, optimalMultiplier, maximumMultiplier);

        final double multiplier = ApplicationState.getInstance().getEnginePips() / 8.0;
        final double engineCapacity = (double) powerDistributor.map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.ENGINES_CAPACITY)).orElse(0D);
        final double engineRecharge = (double) powerDistributor.map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.ENGINES_RECHARGE)).orElse(0D);
        final double boostCost = (double) ship.getAttributes().getOrDefault(HorizonsModifier.BOOST_COST, 0D);
        final boolean engineCapacityEnough = engineCapacity > boostCost + BOOST_MARGIN;

        this.getShip().ifPresent(ship1 -> log.debug("type: " + ship1.getShipType()));
        var currentSpeed = calculateCurrentSpeed(ship, topSpeed , moduleProfile, multiplier);
        var currentBoost = engineCapacityEnough ? calculateCurrentSpeed(ship, boostSpeed, moduleProfile, multiplier) : Double.NaN;
        var currentRecharge = engineCapacityEnough ? calculateCurrentRecharge(boostCost, engineRecharge, multiplier) :Double.NaN;
        var minSpeed = calculateMinSpeed(ship, topSpeed, moduleProfile);
        var minBoost = engineCapacityEnough ? calculateMinSpeed(ship, boostSpeed, moduleProfile) : Double.NaN;
        var minRecharge = engineCapacityEnough ? calculateMinRecharge(boostCost, engineRecharge) : Double.NaN;
        var maxSpeed = calculateMaxSpeed(ship, topSpeed, moduleProfile);
        var maxBoost = engineCapacityEnough ?  calculateMaxSpeed(ship, boostSpeed, moduleProfile) : Double.NaN;
        var maxRecharge = engineCapacityEnough ?  calculateMaxRecharge(boostCost, engineRecharge, multiplier) : Double.NaN;



        this.speedIndicator.updateValues(minSpeed, currentSpeed, maxSpeed);
        this.boostIndicator.updateValues(minBoost, currentBoost, maxBoost);
        this.rechargeIndicator.updateValues(minRecharge, currentRecharge, maxRecharge);
        });
    }
}
