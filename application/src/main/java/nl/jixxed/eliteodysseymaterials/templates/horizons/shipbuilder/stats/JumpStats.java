package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.beans.binding.StringBinding;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.NumberAxisBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TooltipBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ships.Slot;
import nl.jixxed.eliteodysseymaterials.domain.ships.SlotType;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.FrameShiftDriveBooster;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipConfigEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.components.HoverableLineChart;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

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
        jumpRangeIndicator = new RangeIndicator(calculateJumpRangeMinMaintenance(), calculateJumpRangeCurrentMaintenance(), calculateJumpRangeMaxMaintenance(), "ship.stats.jumprange", "ship.stats.jumprange.value");
        DestroyableTooltip tooltip = TooltipBuilder.builder()
                .withText("ship.stats.jumprange.explain")
                .withShowDelay(Duration.ZERO)
                .build();
        tooltip.install(jumpRangeIndicator);
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

        XYChart.Series<Number, Number> superCruisePitchSeries = new XYChart.Series<>();
        superCruisePitchSeries.nameProperty().bind(LocaleService.getStringBinding("ship.stats.jump.chart.pitch"));
        XYChart.Series<Number, Number> superCruiseRollSeries = new XYChart.Series<>();
        superCruiseRollSeries.nameProperty().bind(LocaleService.getStringBinding("ship.stats.jump.chart.roll"));
        XYChart.Series<Number, Number> superCruiseYawSeries = new XYChart.Series<>();
        superCruiseYawSeries.nameProperty().bind(LocaleService.getStringBinding("ship.stats.jump.chart.yaw"));

        Function<Map<Object, Number>, StringBinding> tooltipFunction = data -> LocaleService.getStringBinding("ship.stats.jump.chart.tooltip",
                LocaleService.LocalizationKey.of("ship.stats.jump.chart.throttle"), Formatters.NUMBER_FORMAT_0.format(data.get("x")),
                LocaleService.LocalizationKey.of("ship.stats.jump.chart.roll"), Formatters.NUMBER_FORMAT_1.format(data.get(superCruiseRollSeries)),
                LocaleService.LocalizationKey.of("ship.stats.jump.chart.pitch"), Formatters.NUMBER_FORMAT_1.format(data.get(superCruisePitchSeries)),
                LocaleService.LocalizationKey.of("ship.stats.jump.chart.yaw"), Formatters.NUMBER_FORMAT_1.format(data.get(superCruiseYawSeries))
        );

        chart = new HoverableLineChart(xAxis, yAxis, tooltipFunction);
        chart.setLegendVisible(false);
        chart.setCreateSymbols(false);
        chart.setHorizontalGridLinesVisible(false);
        chart.setVerticalGridLinesVisible(false);
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

    public double getSynthesisBoost() {
        return getShip()
                .map(ship -> ship.getCoreSlots().stream()
                        .filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE)
                        .findFirst()
                        .filter(Slot::isOccupied)
                        .map(slot -> slot.getShipModule().synthesisBlueprints().stream()
                                .filter(bp -> bp.getHorizonsBlueprintGrade().equals(slot.getShipModule().getSynthesisGrade()))
                                .findFirst()
                                .map(bp -> 1D + bp.getModifiers().get(HorizonsModifier.JUMP_RANGE).getModifier().getEnd())
                                .orElse(1D))
                        .orElse(1D))
                .orElse(0.0D);
    }

    public double calculateJumpRangeMinOutfitting() {
        return getShip().map(ship -> {
            final double maxFuelPerJump = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.MAX_FUEL_PER_JUMP, 1D, true)).orElse(1D);
            return calculateJumpRange(ship.getEmptyMass() + ship.getMaxFuel() + ship.getMaxCargo(), maxFuelPerJump) * getSynthesisBoost();
        }).orElse(0.0D);
    }

    public double calculateJumpRangeCurrentOutfitting() {
        return getShip().map(ship -> {
            final double maxFuelPerJump = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.MAX_FUEL_PER_JUMP, 1D, true)).orElse(1D);
            return calculateJumpRange(ship.getEmptyMass() + ship.getCurrentFuel() + ship.getCurrentCargo() + ship.getCurrentFuelReserve(), Math.min(ship.getCurrentFuel() + ship.getCurrentFuelReserve(), maxFuelPerJump)) * getSynthesisBoost();
        }).orElse(0.0D);
    }

    public double calculateJumpRangeMaxOutfitting() {
        return getShip().map(ship -> {
            final double maxFuelPerJump = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.MAX_FUEL_PER_JUMP, 1D, true)).orElse(1D);
            return calculateJumpRange(ship.getEmptyMass() + maxFuelPerJump, maxFuelPerJump) * getSynthesisBoost();
        }).orElse(0.0D);
    }

    public double calculateJumpRangeMinMaintenance() {
        return getShip().map(ship -> {
            final double maxFuelPerJump = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.MAX_FUEL_PER_JUMP, 1D, true)).orElse(1D);
            return calculateJumpRange(ship.getEmptyMass() + ship.getCurrentFuel() + ship.getMaxCargo(), Math.min(ship.getCurrentFuel(), maxFuelPerJump)) * getSynthesisBoost();
        }).orElse(0.0D);
    }

    public double calculateJumpRangeCurrentMaintenance() {
        return getShip().map(ship -> {
            final double maxFuelPerJump = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.MAX_FUEL_PER_JUMP, 1D, true)).orElse(1D);
            return calculateJumpRange(ship.getEmptyMass() + ship.getCurrentFuel() + ship.getCurrentCargo(), Math.min(ship.getCurrentFuel(), maxFuelPerJump)) * getSynthesisBoost();
        }).orElse(0.0D);    }

    public double calculateJumpRangeMaxMaintenance() {
        return getShip().map(ship -> {
            final double maxFuelPerJump = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.MAX_FUEL_PER_JUMP, 1D, true)).orElse(1D);
            return calculateJumpRange(ship.getEmptyMass() + ship.getCurrentFuel(), Math.min(ship.getCurrentFuel(), maxFuelPerJump)) * getSynthesisBoost();
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
            final double fuelCap = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.MAX_FUEL_PER_JUMP, 1D, true)).orElse(1D);
            double fuel = ship.getCurrentFuel();
            while (fuel > fuelCap) {
                range += calculateJumpRange(ship.getEmptyMass() + fuel + ship.getCurrentCargo(), Math.min(fuel, fuelCap)) * getSynthesisBoost();
                fuel -= fuelCap;
            }
            return range + calculateJumpRange(ship.getEmptyMass() + fuel + ship.getCurrentCargo(), Math.min(fuel, fuelCap)) * getSynthesisBoost();
        }).orElse(0.0D);

    }

    public double calculateTotalJumpRangeMax() {
        return getShip().map(ship -> {
            var range = 0d;
            final double fuelCap = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.MAX_FUEL_PER_JUMP, 1D, true)).orElse(1D);
            double fuel = ship.getMaxFuel();
            while (fuel > fuelCap) {
                range += calculateJumpRange(ship.getEmptyMass() + fuel, Math.min(fuel, fuelCap)) * getSynthesisBoost();
                fuel -= fuelCap;
            }
            return (range + calculateJumpRange(ship.getEmptyMass() + fuel, Math.min(fuel, fuelCap))) * getSynthesisBoost();
        }).orElse(0.0D);
    }

    public double calculateJumpRange(final double mass, final double fuel) {
        return getShip().map(ship -> {
            final double maxFuelPerJump = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.MAX_FUEL_PER_JUMP, 1D, true)).orElse(1D);
            final double optimisedMass = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.FSD_OPTIMISED_MASS, 1D, true)).orElse(1D);
            final double fuelMultiplier = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.FUEL_MULTIPLIER, 1D, true)).orElse(1D);
            final double fuelPower = ship.getCoreSlots().stream().filter(slot -> slot.getSlotType() == SlotType.CORE_FRAME_SHIFT_DRIVE).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.FUEL_POWER, 1D, true)).orElse(1D);
            final double jumpRangeIncrease = ship.getOptionalSlots().stream().filter(slot -> slot.getShipModule() instanceof FrameShiftDriveBooster).findFirst().filter(Slot::isOccupied).map(slot -> (double) slot.getShipModule().getAttributeValueOrDefault(HorizonsModifier.JUMP_RANGE_INCREASE, 1D, true)).orElse(0D);
            return calculateJumpDistance(mass, Math.min(fuel, maxFuelPerJump), optimisedMass, fuelMultiplier, fuelPower, jumpRangeIncrease);
        }).orElse(0.0D);
    }

    public double calculateJumpDistance(final double mass, final double fuel, final double optimisedMass, final double fuelMultiplier, final double fuelPower, final double jumpRangeIncrease) {
        if (fuel <= 0D) {
            return 0D;
        }
        return Math.pow(fuel / fuelMultiplier, 1 / fuelPower) * optimisedMass / mass + jumpRangeIncrease;
    }


    @Override
    protected void update() {
        this.jumpRangeIndicator.updateValues(calculateJumpRangeMinMaintenance(), calculateJumpRangeCurrentMaintenance(), calculateJumpRangeMaxMaintenance());
        this.totalJumpRangeIndicator.updateValues(calculateTotalJumpRangeMin(), calculateTotalJumpRangeCurrent(), calculateTotalJumpRangeMax());
        this.totalJumpRangeIndicator2.updateValues(calculateTotalJumpRangeMin(), calculateTotalJumpRangeCurrent(), calculateTotalJumpRangeMax());

        getShip().ifPresent(ship -> {
            if (ship.supportsGraphs()) {
                chart.setVisible(true);
                chart.setManaged(true);
                supercruiseLabel.setVisible(true);
                supercruiseLabel.setManaged(true);
                totalJumpRangeIndicator.setVisible(true);
                totalJumpRangeIndicator.setManaged(true);
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
                supercruiseLabel.setVisible(false);
                supercruiseLabel.setManaged(false);
                totalJumpRangeIndicator.setVisible(false);
                totalJumpRangeIndicator.setManaged(false);
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
