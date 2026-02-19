package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.Slot;
import nl.jixxed.eliteodysseymaterials.domain.ships.SlotType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipConfigEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

import java.util.Optional;

@Slf4j
public class EngineStats extends Stats implements DestroyableTemplate {
    private RangeIndicator speedIndicator;
    private RangeIndicator boostIndicator;
    private RechargeRangeIndicator rechargeIndicator;
    private DestroyableLabel forwardAcceleration;
    private DestroyableLabel reverseAcceleration;
    private DestroyableLabel lateralAcceleration;

    public EngineStats() {
        super();
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("engine-stats");
        addTitle("ship.stats.engine");
        speedIndicator = new RangeIndicator(0D, 0D, 0D, "ship.stats.engine.speed", "ship.stats.engine.speed.value");
        boostIndicator = new RangeIndicator(0D, 0D, 0D, "ship.stats.engine.boost", "ship.stats.engine.boost.value");
        rechargeIndicator = new RechargeRangeIndicator(0D, 0D, 0D, 0D, "ship.stats.engine.recharge", "ship.stats.engine.recharge.value");

        this.getNodes().add(speedIndicator);
        this.getNodes().add(boostIndicator);
        this.getNodes().add(rechargeIndicator);
        this.getNodes().add(new GrowingRegion());
        this.forwardAcceleration = createValueLabel("ship.stats.engine.acceleration.value", Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(0D));
        this.reverseAcceleration = createValueLabel("ship.stats.engine.acceleration.value", Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(0D));
        this.lateralAcceleration = createValueLabel("ship.stats.engine.acceleration.value", Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(0D));
        this.getNodes().add(BoxBuilder.builder()
                .withNodes(createLabel("ship.stats.engine.acceleration"))
                .buildHBox());
        this.getNodes().add(BoxBuilder.builder()
                .withNodes(createLabel("ship.stats.engine.acceleration.forward"), new GrowingRegion(), this.forwardAcceleration)
                .buildHBox());
        this.getNodes().add(BoxBuilder.builder()
                .withNodes(createLabel("ship.stats.engine.acceleration.reverse"), new GrowingRegion(), this.reverseAcceleration)
                .buildHBox());
        this.getNodes().add(BoxBuilder.builder()
                .withNodes(createLabel("ship.stats.engine.acceleration.lateral"), new GrowingRegion(), this.lateralAcceleration)
                .buildHBox());

    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, ShipConfigEvent.class, event -> update()));
    }

    private double calculateMinRecharge(double boostCost, double engineRecharge) {
        return boostCost / engineRecharge;
    }

    private double calculateCurrentRecharge(double boostCost, double engineRecharge, double multiplier) {
        return boostCost / (engineRecharge * Math.pow(multiplier, 1.1));
    }

    private double calculateMaxRecharge(double boostCost, double engineRecharge, double multiplier) {
        return boostCost / (engineRecharge * Math.pow(multiplier > 0 ? 1D / 8D : 0D, 1.1));
    }

    private double calculateMinSpeed(Ship ship, Double speed, ModuleProfile moduleProfile) {
        final Double minimumThrust = (Double) ship.getAttributes().getOrDefault(HorizonsModifier.MINIMUM_THRUST, 0.0D);
        return speed * (moduleProfile.getMassCurveMultiplier(ship.getEmptyMass() + ship.getMaxFuel() + ship.getMaxCargo() + ship.getMaxFuelReserve()) / 100D) * (minimumThrust / 100D);
    }

    private double calculateCurrentSpeed(Ship ship, Double speed, ModuleProfile moduleProfile, double multiplier) {
        final Double minimumThrust = (Double) ship.getAttributes().getOrDefault(HorizonsModifier.MINIMUM_THRUST, 0.0D);
        return speed * (moduleProfile.getMassCurveMultiplier(ship.getEmptyMass() + ship.getCurrentFuel() + ship.getCurrentCargo() + ship.getCurrentFuelReserve()) / 100D) * (multiplier + (minimumThrust / 100D) * (1D - multiplier));
    }

    private double calculateMaxSpeed(final Ship ship, final double speed, final ModuleProfile moduleProfile) {
        return speed * moduleProfile.getMassCurveMultiplier(ship.getEmptyMass()) / 100D;
    }
    private static Double getMaximumMultiplier(Optional<Slot> thrusters, Ship ship) {
        return thrusters
                .map(Slot::getShipModule)
                .flatMap(shipModule -> {
                    if (shipModule.getAttibutes().contains(HorizonsModifier.MAXIMUM_BOOSTED_MULTIPLIER) && isOverMinimumMass(shipModule, ship)) {
                        final Double boosted = (Double) shipModule.getAttributeValue(HorizonsModifier.MAXIMUM_BOOSTED_MULTIPLIER, true);
                        return Optional.of(boosted * ModuleProfile.MAGIC_NUMBER);
                    } else{
                        return Optional.ofNullable((Double) shipModule.getAttributeValueOrDefault(HorizonsModifier.MAXIMUM_MULTIPLIER_SPEED, null, true));
                    }
                })
                .orElseGet(() -> thrusters.map(Slot::getShipModule).map(shipModule -> (Double) shipModule.getAttributeValue(HorizonsModifier.MAXIMUM_MULIPLIER, true)).orElse(0D));
    }

    private static Double getOptimalMultiplier(Optional<Slot> thrusters, Ship ship) {
        return thrusters
                .map(Slot::getShipModule)
                .flatMap(shipModule -> {
                    if (shipModule.getAttibutes().contains(HorizonsModifier.OPTIMAL_BOOSTED_MULTIPLIER) && isOverMinimumMass(shipModule, ship)) {
                        final Double boosted = (Double) shipModule.getAttributeValue(HorizonsModifier.OPTIMAL_BOOSTED_MULTIPLIER, true);
                        return Optional.of(boosted * ModuleProfile.MAGIC_NUMBER);
                    } else{
                        return Optional.ofNullable((Double) shipModule.getAttributeValueOrDefault(HorizonsModifier.OPTIMAL_MULTIPLIER_SPEED, null, true));
                    }
                })
                .orElseGet(() -> thrusters.map(Slot::getShipModule).map(shipModule -> (Double) shipModule.getAttributeValue(HorizonsModifier.OPTIMAL_MULTIPLIER, true)).orElse(0D));
    }

    private static Double getMinimumMultiplier(Optional<Slot> thrusters, Ship ship) {
        return thrusters
                .map(Slot::getShipModule)
                .flatMap(shipModule -> {
                    if (shipModule.getAttibutes().contains(HorizonsModifier.MINIMUM_BOOSTED_MULTIPLIER) && isOverMinimumMass(shipModule, ship)) {
                        final Double boosted = (Double) shipModule.getAttributeValue(HorizonsModifier.MINIMUM_BOOSTED_MULTIPLIER, true);
                        return Optional.of(boosted * ModuleProfile.MAGIC_NUMBER);
                    } else{
                        return Optional.ofNullable((Double) shipModule.getAttributeValueOrDefault(HorizonsModifier.MINIMUM_MULTIPLIER_SPEED, null, true));
                    }
                })
                .orElseGet(() -> thrusters.map(Slot::getShipModule).map(shipModule -> (Double) shipModule.getAttributeValue(HorizonsModifier.MINIMUM_MULTIPLIER, true)).orElse(0D));
    }
    private static boolean isOverMinimumMass(ShipModule shipModule, Ship ship) {
        var mass = ship.getEmptyMass() + ship.getCurrentFuel() + ship.getCurrentCargo() + ship.getCurrentFuelReserve();
        var minMass = (Double) shipModule.getAttributeValue(HorizonsModifier.ENGINE_MINIMUM_MASS, true);
        return mass > minMass;
    }

    private Double calculateForwardAcceleration(Ship ship, final ModuleProfile moduleProfile) {
        return (Double) ship.getAttributes().get(HorizonsModifier.FORWARD_ACCELERATION) * moduleProfile.getMassCurveMultiplier(ship.getEmptyMass() + ship.getCurrentFuel() + ship.getCurrentCargo() + ship.getCurrentFuelReserve()) / 100D;
    }

    private Double calculateReverseAcceleration(Ship ship, final ModuleProfile moduleProfile) {
        return (Double) ship.getAttributes().get(HorizonsModifier.REVERSE_ACCELERATION) * moduleProfile.getMassCurveMultiplier(ship.getEmptyMass() + ship.getCurrentFuel() + ship.getCurrentCargo() + ship.getCurrentFuelReserve()) / 100D;
    }

    private Double calculateLateralAcceleration(Ship ship, final ModuleProfile moduleProfile) {
        return (Double) ship.getAttributes().get(HorizonsModifier.LATERAL_ACCELERATION) * moduleProfile.getMassCurveMultiplier(ship.getEmptyMass() + ship.getCurrentFuel() + ship.getCurrentCargo() + ship.getCurrentFuelReserve()) / 100D;
    }

    @Override
    protected void update() {

        getShip().ifPresent(ship -> {
            final Optional<Slot> thrusters = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.CORE_THRUSTERS)).findFirst().filter(Slot::isOccupied);
            final Optional<Slot> powerDistributor = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType().equals(SlotType.CORE_POWER_DISTRIBUTION)).findFirst().filter(Slot::isOccupied);
            final Double minimumMass = (Double) thrusters.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(HorizonsModifier.ENGINE_MINIMUM_MASS, true)).orElse(0D);
            final Double optimalMass = (Double) thrusters.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(HorizonsModifier.ENGINE_OPTIMAL_MASS, true)).orElse(0D);
            final Double maximumMass = (Double) thrusters.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(HorizonsModifier.MAXIMUM_MASS, true)).orElse(0D);
            final Double minimumMultiplier = getMinimumMultiplier(thrusters, ship);
            final Double optimalMultiplier = getOptimalMultiplier(thrusters, ship);
            final Double maximumMultiplier = getMaximumMultiplier(thrusters, ship);
            final Double topSpeed = (Double) ship.getAttributes().getOrDefault(HorizonsModifier.TOP_SPEED, 0.0D);
            final Double boostSpeed = (Double) ship.getAttributes().getOrDefault(HorizonsModifier.BOOST_SPEED, 0.0D);
            final Double boostInterval = (Double) ship.getAttributes().getOrDefault(HorizonsModifier.BOOST_INTERVAL, 0.0D);
            final ModuleProfile moduleProfile = new ModuleProfile(minimumMass, optimalMass, maximumMass, minimumMultiplier, optimalMultiplier, maximumMultiplier);

            final double multiplier = ApplicationState.getInstance().getEnginePips() / 8.0;
            final double engineCapacity = (double) powerDistributor.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(HorizonsModifier.ENGINES_CAPACITY, true)).orElse(0D);
            final double engineRecharge = (double) powerDistributor.map(Slot::getShipModule).map(sm -> sm.getAttributeValue(HorizonsModifier.ENGINES_RECHARGE, true)).orElse(0D);
            final double boostCost = (double) ship.getAttributes().getOrDefault(HorizonsModifier.BOOST_COST, 0D);
            final boolean engineCapacityEnough = engineCapacity > boostCost;

            var currentSpeed = calculateCurrentSpeed(ship, topSpeed, moduleProfile, multiplier);
            var currentBoost = engineCapacityEnough ? calculateCurrentSpeed(ship, boostSpeed, moduleProfile, multiplier) : Double.NaN;
            var currentRecharge = engineCapacityEnough ? calculateCurrentRecharge(boostCost, engineRecharge, multiplier) : Double.NaN;
            var minSpeed = calculateMinSpeed(ship, topSpeed, moduleProfile);
            var minBoost = engineCapacityEnough ? calculateMinSpeed(ship, boostSpeed, moduleProfile) : Double.NaN;
            var minRecharge = engineCapacityEnough ? calculateMinRecharge(boostCost, engineRecharge) : Double.NaN;
            var maxSpeed = calculateMaxSpeed(ship, topSpeed, moduleProfile);
            var maxBoost = engineCapacityEnough ? calculateMaxSpeed(ship, boostSpeed, moduleProfile) : Double.NaN;
            var maxRecharge = engineCapacityEnough ? calculateMaxRecharge(boostCost, engineRecharge, multiplier) : Double.NaN;


            this.speedIndicator.updateValues(minSpeed, currentSpeed, maxSpeed);
            this.boostIndicator.updateValues(minBoost, currentBoost, maxBoost);
            this.rechargeIndicator.updateValues(minRecharge, currentRecharge, maxRecharge, boostInterval);
            this.forwardAcceleration.addBinding(this.forwardAcceleration.textProperty(), LocaleService.getStringBinding("ship.stats.engine.acceleration.value", Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(calculateForwardAcceleration(ship, moduleProfile))));
            this.reverseAcceleration.addBinding(this.reverseAcceleration.textProperty(), LocaleService.getStringBinding("ship.stats.engine.acceleration.value", Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(calculateReverseAcceleration(ship, moduleProfile))));
            this.lateralAcceleration.addBinding(this.lateralAcceleration.textProperty(), LocaleService.getStringBinding("ship.stats.engine.acceleration.value", Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(calculateLateralAcceleration(ship, moduleProfile))));
        });
    }


}
