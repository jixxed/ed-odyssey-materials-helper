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
import java.util.Set;

@Slf4j
public class EngineStats extends Stats implements Template {
    private static final double BOOST_MARGIN = 0.005;
    private DestroyableLabel currentSpeed;
    private DestroyableLabel currentBoost;
    private DestroyableLabel currentRecharge;
    private DestroyableLabel minSpeed;
    private DestroyableLabel minBoost;
    private DestroyableLabel minRecharge;
    private DestroyableLabel maxSpeed;
    private DestroyableLabel maxBoost;
    private DestroyableLabel maxRecharge;

    public EngineStats() {
        super();
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getChildren().add(BoxBuilder.builder().withNodes(new GrowingRegion(), createTitle("ship.stats.engine"), new GrowingRegion()).buildHBox());
        this.getChildren().add(new Separator(Orientation.HORIZONTAL));
        this.minSpeed = createValueLabel(String.format("%.2f", 0.0));
        this.minBoost = createValueLabel(String.format("%.2f", 0.0));
        this.minRecharge = createValueLabel(String.format("%.2f", 0.0));
        this.currentSpeed = createValueLabel(String.format("%.2f", 0.0));
        this.currentBoost = createValueLabel(String.format("%.2f", 0.0));
        this.currentRecharge = createValueLabel(String.format("%.2f", 0.0));
        this.maxSpeed = createValueLabel(String.format("%.2f", 0.0));
        this.maxBoost = createValueLabel(String.format("%.2f", 0.0));
        this.maxRecharge = createValueLabel(String.format("%.2f", 0.0));
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.engine.minspeed"), new GrowingRegion(), this.minSpeed).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.engine.minboost"), new GrowingRegion(), this.minBoost).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.engine.minrecharge"), new GrowingRegion(), this.minRecharge).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.engine.currentspeed"), new GrowingRegion(), this.currentSpeed).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.engine.currentboost"), new GrowingRegion(), this.currentBoost).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.engine.currentrecharge"), new GrowingRegion(), this.currentRecharge).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.engine.maxspeed"), new GrowingRegion(), this.maxSpeed).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.engine.maxboost"), new GrowingRegion(), this.maxBoost).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.engine.maxrecharge"), new GrowingRegion(), this.maxRecharge).buildHBox());
    }

    @Override
    public void initEventHandling() {
        eventListeners.add(EventService.addListener(this, ShipConfigEvent.class, event -> update()));
    }

    private double calculateMaxRecharge(double boostCost, double engineRecharge) {
        return boostCost / engineRecharge;
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

        log.debug("update engine: " + this.getShip().isPresent());
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
            this.currentSpeed.setText(String.format("%.2f", calculateCurrentSpeed(ship, topSpeed , moduleProfile, multiplier)));
            this.currentBoost.setText(engineCapacityEnough ? String.format("%.2f", calculateCurrentSpeed(ship, boostSpeed, moduleProfile, multiplier)) : "ERROR");
            this.currentRecharge.setText(engineCapacityEnough ? String.format("%.2f", calculateCurrentRecharge(boostCost, engineRecharge, multiplier)) : "ERROR");
            this.minSpeed.setText(String.format("%.2f", calculateMinSpeed(ship, topSpeed, moduleProfile)));
            this.minBoost.setText(engineCapacityEnough ? String.format("%.2f", calculateMinSpeed(ship, boostSpeed, moduleProfile)) : "ERROR");
            this.minRecharge.setText(engineCapacityEnough ? String.format("%.2f", calculateMaxRecharge(boostCost, engineRecharge)) : "ERROR");
            this.maxSpeed.setText(String.format("%.2f", calculateMaxSpeed(ship, topSpeed, moduleProfile)));
            this.maxBoost.setText(engineCapacityEnough ? String.format("%.2f", calculateMaxSpeed(ship, boostSpeed, moduleProfile)) : "ERROR");
            this.maxRecharge.setText(engineCapacityEnough ? String.format("%.2f", calculateMaxRecharge(boostCost, engineRecharge)) : "ERROR");
        });
    }

}
