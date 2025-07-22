package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.NumberAxisBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ships.Slot;
import nl.jixxed.eliteodysseymaterials.domain.ships.SlotType;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.FrameShiftDriveBooster;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipConfigEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.components.HoverableLineChart;
import nl.jixxed.eliteodysseymaterials.templates.components.NumberAxisContainer;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JumpStats extends Stats implements DestroyableTemplate {
    private RangeIndicator jumpRangeIndicator;
    private RangeIndicator totalJumpRangeIndicator;
    private RangeIndicator totalJumpRangeIndicator2;
    private Map<Integer, XYChart.Data<Number, Number>> superCruisePitchMap;
    private Map<Integer, XYChart.Data<Number, Number>> superCruiseRollMap;
    private Map<Integer, XYChart.Data<Number, Number>> superCruiseYawMap;
    private HoverableLineChart chart;
    private DestroyableLabel supercruiseLabel;

    public JumpStats() {
        super();
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("jump-stats");
        addTitle("ship.stats.jump");
        jumpRangeIndicator = new RangeIndicator(calculateJumpRangeMin(), calculateJumpRangeCurrent(), calculateJumpRangeMax(), "ship.stats.jumprange", "ship.stats.jumprange.value");
        totalJumpRangeIndicator = new RangeIndicator(calculateTotalJumpRangeMin(), calculateTotalJumpRangeCurrent(), calculateTotalJumpRangeMax(), "ship.stats.totaljumprange", "ship.stats.totaljumprange.value");
        totalJumpRangeIndicator2 = new RangeIndicator(calculateTotalJumpRangeMin(), calculateTotalJumpRangeCurrent(), calculateTotalJumpRangeMax(), "ship.stats.totaljumprange", "ship.stats.totaljumprange.value");
        DestroyableHBox box = BoxBuilder.builder()
                .withStyleClass("ranges")
                .withNodes(jumpRangeIndicator, totalJumpRangeIndicator)
                .buildHBox();
        this.getNodes().add(box);
        this.getNodes().add(totalJumpRangeIndicator2);
        NumberAxis xAxis = NumberAxisBuilder.builder()
                .withLabel("ship.stats.jump.chart.xaxis")
                .withLowerBound(0D)
                .withUpperBound(100D)
                .withTickUnit(25D)
                .withAutoRanging(false)
                .build();
        NumberAxis yAxis = NumberAxisBuilder.builder()
                .withLabel("ship.stats.jump.chart.yaxis")
                .build();
        chart = new HoverableLineChart(new NumberAxisContainer(xAxis, "ship.stats.jump.chart.xaxis.tooltip"), new NumberAxisContainer(yAxis, "ship.stats.jump.chart.yaxis.tooltip"), "ship.stats.jump.chart.tooltip");
        chart.setLegendVisible(false);
        chart.setCreateSymbols(false);
        chart.setHorizontalGridLinesVisible(false);
        chart.setVerticalGridLinesVisible(false);
        XYChart.Series<Number, Number> superCruisePitchSeries = new XYChart.Series<>();
        superCruisePitchSeries.nameProperty().bind(LocaleService.getStringBinding("ship.stats.jump.chart.pitch"));
        XYChart.Series<Number, Number> superCruiseRollSeries = new XYChart.Series<>();
        superCruiseRollSeries.nameProperty().bind(LocaleService.getStringBinding("ship.stats.jump.chart.roll"));
        XYChart.Series<Number, Number> superCruiseYawSeries = new XYChart.Series<>();
        superCruiseYawSeries.nameProperty().bind(LocaleService.getStringBinding("ship.stats.jump.chart.yaw"));
        superCruisePitchMap = new HashMap<>();
        superCruiseRollMap = new HashMap<>();
        superCruiseYawMap = new HashMap<>();
        for (int index = 0; index <= 100; index++) {
            superCruisePitchMap.put(index, new XYChart.Data<>(index, 0));
            superCruiseRollMap.put(index, new XYChart.Data<>(index, 0));
            superCruiseYawMap.put(index, new XYChart.Data<>(index, 0));
        }
        superCruisePitchSeries.getData().addAll(superCruisePitchMap.values());
        superCruiseRollSeries.getData().addAll(superCruiseRollMap.values());
        superCruiseYawSeries.getData().addAll(superCruiseYawMap.values());
        chart.getData().add(superCruisePitchSeries);
        chart.getData().add(superCruiseRollSeries);
        chart.getData().add(superCruiseYawSeries);

        this.getNodes().add(new GrowingRegion());
        supercruiseLabel = LabelBuilder.builder().withText("ship.stats.jumprange.supercruise.profile").build();
        this.getNodes().add(supercruiseLabel);
        this.getNodes().add(chart);
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, ShipConfigEvent.class, event -> update()));
    }


    public double calculateJumpRangeMin() {
        return getShip().map(ship -> calculateJumpRange(ship.getEmptyMass() + ship.getMaxFuelReserve() + ship.getMaxCargo() + ship.getMaxFuel(), ship.getMaxFuel())).orElse(0.0D);
    }

    public double calculateJumpRangeCurrent() {
        return getShip().map(ship -> calculateJumpRange(ship.getEmptyMass() + ship.getCurrentCargo() + ship.getCurrentFuel() + ship.getCurrentFuelReserve(), ship.getCurrentFuel())).orElse(0.0D);
    }

    public double calculateJumpRangeMax() {
        return getShip().map(ship -> {
            final double fuel = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.MAX_FUEL_PER_JUMP, 1D)).orElse(1D);
            return calculateJumpRange(ship.getEmptyMass() + fuel, fuel);
        }).orElse(0.0D);
    }

    public double calculateJumpRangeMaxFueled() {
        return getShip().map(ship -> calculateJumpRange(ship.getEmptyMass() + ship.getMaxFuel(), ship.getMaxFuel())).orElse(0.0D);
    }

    public double calculateTotalJumpRangeMin() {
        return 0d;
    }

    public double calculateTotalJumpRangeCurrent() {
        return getShip().map(ship -> {
            var range = 0d;
            final double fuelCap = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.MAX_FUEL_PER_JUMP, 1D)).orElse(1D);
            double fuel = ship.getCurrentFuel();
            while (fuel > fuelCap) {
                range += calculateJumpRange(ship.getEmptyMass() + ship.getCurrentCargo() + fuel + ship.getCurrentFuelReserve(), fuel);
                fuel -= fuelCap;
            }
            return range + calculateJumpRange(ship.getEmptyMass() + ship.getCurrentCargo() + fuel + ship.getCurrentFuelReserve(), fuel);
        }).orElse(0.0D);

    }

    public double calculateTotalJumpRangeMax() {
        return getShip().map(ship -> {
            var range = 0d;
            final double fuelCap = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.MAX_FUEL_PER_JUMP, 1D)).orElse(1D);
            double fuel = ship.getMaxFuel();
            while (fuel > fuelCap) {
                range += calculateJumpRange(ship.getEmptyMass() + fuel, fuel);
                fuel -= fuelCap;
            }
            return range + calculateJumpRange(ship.getEmptyMass() + fuel, fuel);
        }).orElse(0.0D);
    }

    public double calculateJumpRange(final double mass, final double fuel) {
        return getShip().map(ship -> {
            final double maxFuelPerJump = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.MAX_FUEL_PER_JUMP, 1D)).orElse(1D);
            final double optimisedMass = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.FSD_OPTIMISED_MASS, 1D)).orElse(1D);
            final double fuelMultiplier = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.FUEL_MULTIPLIER, 1D)).orElse(1D);
            final double fuelPower = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.FUEL_POWER, 1D)).orElse(1D);
            final double jumpRangeIncrease = ship.getOptionalSlots().stream().filter(slot -> slot.getShipModule() instanceof FrameShiftDriveBooster).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.JUMP_RANGE_INCREASE, 1D)).orElse(0D);
            return calculateJumpDistance(mass, Math.min(fuel, maxFuelPerJump), optimisedMass, fuelMultiplier, fuelPower, jumpRangeIncrease);
        }).orElse(0.0D);
    }

    private double calculateJumpDistance(final double mass, final double fuel, final double optimisedMass, final double fuelMultiplier, final double fuelPower, final double jumpRangeIncrease) {
        if (fuel <= 0D) {
            return 0D;
        }
        return Math.pow(fuel / fuelMultiplier, 1 / fuelPower) * optimisedMass / mass + jumpRangeIncrease;
    }


    @Override
    protected void update() {
        this.jumpRangeIndicator.updateValues(calculateJumpRangeMin(), calculateJumpRangeCurrent(), calculateJumpRangeMax());
        this.totalJumpRangeIndicator.updateValues(calculateTotalJumpRangeMin(), calculateTotalJumpRangeCurrent(), calculateTotalJumpRangeMax());
        this.totalJumpRangeIndicator2.updateValues(calculateTotalJumpRangeMin(), calculateTotalJumpRangeCurrent(), calculateTotalJumpRangeMax());

        getShip().ifPresent(ship -> {
            if (ship.supportsGraphs()) {
                chart.setVisible(true);
                chart.setManaged(true);
                totalJumpRangeIndicator.setVisible(true);
                totalJumpRangeIndicator.setManaged(true);
                supercruiseLabel.setVisible(false);
                supercruiseLabel.setManaged(false);
                totalJumpRangeIndicator2.setVisible(false);
                totalJumpRangeIndicator2.setManaged(false);
                superCruisePitchMap.forEach((key, value) -> {
                    Double[][] profile = (Double[][]) ship.getAttributes().get(HorizonsModifier.SUPERCRUISE_PROFILE);
                    Double pivot = profile[0][1];
                    Double min = profile[0][0];
                    Double max = profile[0][2];
                    mapProfile(key, value, pivot, min, max);
                });
                superCruiseRollMap.forEach((key, value) -> {
                    Double[][] profile = (Double[][]) ship.getAttributes().get(HorizonsModifier.SUPERCRUISE_PROFILE);
                    Double pivot = profile[1][1];
                    Double min = profile[1][0];
                    Double max = profile[1][2];
                    mapProfile(key, value, pivot, min, max);
                });
                superCruiseYawMap.forEach((key, value) -> {
                    Double[][] profile = (Double[][]) ship.getAttributes().get(HorizonsModifier.SUPERCRUISE_PROFILE);
                    Double pivot = profile[2][1];
                    Double min = profile[2][0];
                    Double max = profile[2][2];
                    mapProfile(key, value, pivot, min, max);
                });
            } else {
                chart.setVisible(false);
                chart.setManaged(false);
                totalJumpRangeIndicator.setVisible(false);
                totalJumpRangeIndicator.setManaged(false);
                supercruiseLabel.setVisible(true);
                supercruiseLabel.setManaged(true);
                totalJumpRangeIndicator2.setVisible(true);
                totalJumpRangeIndicator2.setManaged(true);
            }
        });
    }

    private static void mapProfile(Integer key, XYChart.Data<Number, Number> value, Double pivot, Double min, Double max) {
        int distance = Math.abs(key - 50) * 2;
        if (key <= 50) {
            double pivotMax = pivot;
            double pivotMin = pivotMax * (min / 100);
            double y = pivotMax - (pivotMax - pivotMin) * (distance / 100.0);
            value.setYValue(y);
        } else {
            double pivotMin = pivot;
            double pivotMax = pivotMin * (max / 100);
            double y = pivotMin + (pivotMax - pivotMin) * (distance / 100.0);
            value.setYValue(y);
        }
    }
}
