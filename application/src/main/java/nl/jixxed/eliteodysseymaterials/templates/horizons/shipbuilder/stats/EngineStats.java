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
public class EngineStats extends Stats implements Template {
    private static final double BOOST_MARGIN = 0.005;
    private RangeIndicator speedIndicator;
    private RangeIndicator boostIndicator;
    private RangeIndicator rechargeIndicator;

    public EngineStats() {
        super();
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        speedIndicator = new RangeIndicator(0D,0D,0D, "ship.stats.engine.speed", "ship.stats.engine.speed.value");
        boostIndicator = new RangeIndicator(0D,0D,0D, "ship.stats.engine.boost", "ship.stats.engine.boost.value");
        rechargeIndicator = new RangeIndicator(0D,0D,0D, "ship.stats.engine.recharge", "ship.stats.engine.recharge.value");

        this.getChildren().add(BoxBuilder.builder().withNodes(new GrowingRegion(), createTitle("ship.stats.engine"), new GrowingRegion()).buildHBox());
        this.getChildren().add(new Separator(Orientation.HORIZONTAL));
        this.getChildren().add(speedIndicator);
        this.getChildren().add(boostIndicator);
        this.getChildren().add(rechargeIndicator);
    }

    @Override
    public void initEventHandling() {
        eventListeners.add(EventService.addListener(true, this, ShipConfigEvent.class, event -> update()));
    }

    private double calculateMinRecharge(double boostCost, double engineRecharge) {
        return boostCost / engineRecharge;
    }

    private double calculateCurrentRecharge(double boostCost, double engineRecharge, double multiplier) {
        return boostCost / (engineRecharge * Math.pow(multiplier, 1.1));
    }

    private double calculateMaxRecharge(double boostCost, double engineRecharge, double multiplier) {
        return boostCost /  (engineRecharge * Math.pow(multiplier > 0 ? 1D/8D : 0D, 1.1));
    }

    private double calculateMinSpeed(Ship ship, Double speed, ModuleProfile moduleProfile) {
        final Double minimumThrust = (Double) ship.getAttributes().getOrDefault(HorizonsModifier.MINIMUM_THRUST, 0.0D);
        return speed * (getMassCurveMultiplier(ship.getEmptyMass() + ship.getMaxFuel() + ship.getMaxCargo() + ship.getMaxFuelReserve(), moduleProfile) / 100D) * (minimumThrust / 100D);
    }

    private double calculateCurrentSpeed(Ship ship, Double speed, ModuleProfile moduleProfile, double multiplier) {
        final Double minimumThrust = (Double) ship.getAttributes().getOrDefault(HorizonsModifier.MINIMUM_THRUST, 0.0D);
        return speed * (getMassCurveMultiplier(ship.getEmptyMass() + ship.getCurrentFuel() + ship.getCurrentCargo() + ship.getCurrentFuelReserve(), moduleProfile) / 100D) * (multiplier + (minimumThrust / 100D) * (1D - multiplier));
    }

    private double calculateMaxSpeed(final Ship ship, final double speed, final ModuleProfile moduleProfile) {
        return speed * getMassCurveMultiplier(ship.getEmptyMass(), moduleProfile) / 100D;
    }

    private static Double getMaximumMultiplier(Optional<Slot> thrusters) {
        return thrusters
                .map(Slot::getShipModule)
                .flatMap(shipModule -> Optional.ofNullable((Double) shipModule.getAttributeValueOrDefault(HorizonsModifier.MAXIMUM_MULTIPLIER_SPEED, null)))
                .orElseGet(() -> thrusters.map(Slot::getShipModule).map(shipModule -> (Double) shipModule.getAttributeValue(HorizonsModifier.MAXIMUM_MULIPLIER)).orElse(0D));
    }

    private static Double getOptimalMultiplier(Optional<Slot> thrusters) {
        return thrusters
                .map(Slot::getShipModule)
                .flatMap(shipModule -> Optional.ofNullable((Double) shipModule.getAttributeValueOrDefault(HorizonsModifier.OPTIMAL_MULTIPLIER_SPEED, null)))
                .orElseGet(() -> thrusters.map(Slot::getShipModule).map(shipModule -> (Double) shipModule.getAttributeValue(HorizonsModifier.OPTIMAL_MULTIPLIER)).orElse(0D));
    }

    private static Double getMinimumMultiplier(Optional<Slot> thrusters) {
        return thrusters
                .map(Slot::getShipModule)
                .flatMap(shipModule -> Optional.ofNullable((Double) shipModule.getAttributeValueOrDefault(HorizonsModifier.MINIMUM_MULTIPLIER_SPEED, null)))
                .orElseGet(() -> thrusters.map(Slot::getShipModule).map(shipModule -> (Double) shipModule.getAttributeValue(HorizonsModifier.MINIMUM_MULTIPLIER)).orElse(0D));
    }

    @Override
    protected void update() {

        getShip().ifPresent(ship -> {
            final Optional<Slot> thrusters = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.CORE_THRUSTERS)).findFirst().filter(Slot::isOccupied);
            final Optional<Slot> powerDistributor = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.CORE_POWER_DISTRIBUTION)).findFirst().filter(Slot::isOccupied);
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
