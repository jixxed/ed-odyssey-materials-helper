/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.beans.binding.StringBinding;
import javafx.util.Duration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TooltipBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ships.PowerProfile;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.domain.ships.Slot;
import nl.jixxed.eliteodysseymaterials.domain.ships.SlotType;
import nl.jixxed.eliteodysseymaterials.enums.HardpointGroup;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HardpointGroupSelectedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.ShipConfigEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
public class ThermalStats extends Stats implements DestroyableEventTemplate {
    Set<HardpointGroup> selectedHardpointGroups = new HashSet<>(Set.of(HardpointGroup.A, HardpointGroup.B, HardpointGroup.C, HardpointGroup.D));

    private DestroyableLabel idleThermals;
    private DestroyableLabel thrusterThermals;
    private DestroyableLabel fsdThermals;
    private DestroyableLabel silentRunTime;
    private DestroyableLabel weaponThermalsFull;
    private DestroyableLabel weaponThermalsEmpty;
    private DestroyableLabel sensorRange;
    private DestroyableLabel effectiveSensorRange;

    public ThermalStats() {
        super();
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("thermal-stats");
        addTitle("ship.stats.thermal");

        this.idleThermals = createValueLabel("ship.stats.thermal.idle.thermals.value", Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(0D));
        this.thrusterThermals = createValueLabel("ship.stats.thermal.thruster.thermals.value", Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(0D));
        this.fsdThermals = createValueLabel("ship.stats.thermal.fsd.thermals.value", Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(0D));
        this.silentRunTime = createValueLabel("ship.stats.thermal.thruster.silentruntime.value", Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(0D));
        this.weaponThermalsFull = createValueLabel("ship.stats.thermal.weapon.thermals.value", Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(0D));
        this.weaponThermalsEmpty = createValueLabel("ship.stats.thermal.weapon.thermals.value", Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(0D));
        this.sensorRange = createValueLabel("ship.stats.ship.sensorrange.value", Formatters.NUMBER_FORMAT_0.format(0D), Formatters.NUMBER_FORMAT_0.format(0D));
        this.effectiveSensorRange = createValueLabel("ship.stats.ship.effectivesensorrange.value", Formatters.NUMBER_FORMAT_0.format(0D), Formatters.NUMBER_FORMAT_0.format(0D));

        DestroyableHBox idleLine = BoxBuilder.builder()
                .withStyleClass("thermal-line")
                .withNodes(createLabel("ship.stats.thermal.idle.thermals"), new GrowingRegion(), this.idleThermals)
                .buildHBox();
        DestroyableHBox thrusterLine = BoxBuilder.builder()
                .withStyleClass("thermal-line")
                .withNodes(createLabel("ship.stats.thermal.thruster.thermals"), new GrowingRegion(), this.thrusterThermals)
                .buildHBox();
        DestroyableHBox fsdLine = BoxBuilder.builder()
                .withStyleClass("thermal-line")
                .withNodes(createLabel("ship.stats.thermal.fsd.thermals"), new GrowingRegion(), this.fsdThermals)
                .buildHBox();
        DestroyableHBox silentRunningLine = BoxBuilder.builder()
                .withStyleClass("thermal-line")
                .withNodes(createLabel("ship.stats.thermal.thruster.silentruntime"), new GrowingRegion(), this.silentRunTime)
                .buildHBox();
        DestroyableHBox weaponLine = BoxBuilder.builder()
                .withStyleClass("thermal-line")
                .withNodes(createLabel("ship.stats.thermal.weapon.thermals"), new GrowingRegion(), this.weaponThermalsFull)
                .buildHBox();
        DestroyableHBox weaponLine2 = BoxBuilder.builder()
                .withStyleClass("thermal-line")
                .withNodes(new GrowingRegion(), this.weaponThermalsEmpty)
                .buildHBox();

        final DestroyableTooltip idleThermalsTooltip = TooltipBuilder.builder()
                .withShowDelay(Duration.ZERO)
                .withText("ship.stats.thermal.idle.thermals.tooltip")
                .build();
        idleThermalsTooltip.install(idleLine);
        final DestroyableTooltip thrusterThermalsTooltip = TooltipBuilder.builder()
                .withShowDelay(Duration.ZERO)
                .withText("ship.stats.thermal.thruster.thermals.tooltip")
                .build();
        thrusterThermalsTooltip.install(thrusterLine);
        final DestroyableTooltip fsdThermalsTooltip = TooltipBuilder.builder()
                .withShowDelay(Duration.ZERO)
                .withText("ship.stats.thermal.fsd.thermals.tooltip")
                .build();
        fsdThermalsTooltip.install(fsdLine);
        final DestroyableTooltip silentRunTimeTooltip = TooltipBuilder.builder()
                .withShowDelay(Duration.ZERO)
                .withText("ship.stats.thermal.thruster.silentruntime.tooltip")
                .build();
        silentRunTimeTooltip.install(silentRunningLine);
        final DestroyableTooltip weaponThermalsFullTooltip = TooltipBuilder.builder()
                .withShowDelay(Duration.ZERO)
                .withText("ship.stats.thermal.weapons.full.tooltip")
                .build();
        weaponThermalsFullTooltip.install(weaponThermalsFull);
        final DestroyableTooltip weaponThermalsEmptyTooltip = TooltipBuilder.builder()
                .withShowDelay(Duration.ZERO)
                .withText("ship.stats.thermal.weapons.empty.tooltip")
                .build();
        weaponThermalsEmptyTooltip.install(weaponThermalsEmpty);

        this.getNodes().addAll(idleLine, thrusterLine, fsdLine, silentRunningLine, weaponLine, weaponLine2, new GrowingRegion());
        DestroyableHBox sensorRangeLine = BoxBuilder.builder()
                .withStyleClass("thermal-line")
                .withNodes(createLabel("ship.stats.ship.sensorrange"), new GrowingRegion(), this.sensorRange)
                .buildHBox();
        var sensorRangeLineTooltip = TooltipBuilder.builder()
                .withStyleClass("stats-tooltip")
                .withText(LocaleService.getStringBinding("ship.stats.ship.sensorrange.tooltip"))
                .withShowDelay(Duration.millis(100))
                .build();
        sensorRangeLineTooltip.install(sensorRangeLine);
        this.getNodes().add(sensorRangeLine);
        DestroyableHBox effectiveSensorRangeLine = BoxBuilder.builder()
                .withStyleClass("thermal-line")
                .withNodes(createLabel("ship.stats.ship.effectivesensorrange"), new GrowingRegion(), this.effectiveSensorRange)
                .buildHBox();
        var effectiveSensorRangeLineTooltip = TooltipBuilder.builder()
                .withStyleClass("stats-tooltip")
                .withText(LocaleService.getStringBinding("ship.stats.ship.effectivesensorrange.tooltip"))
                .withShowDelay(Duration.millis(100))
                .build();
        effectiveSensorRangeLineTooltip.install(effectiveSensorRangeLine);
        this.getNodes().add(effectiveSensorRangeLine);
    }

    private DestroyableHBox createLegend(String number) {
        final DestroyableCircle circle = new DestroyableCircle();
        circle.addBinding(circle.radiusProperty(), ScalingHelper.getPixelDoubleBindingFromEm(0.5D));
        circle.getStyleClass().add("power-group-" + number.toLowerCase());
        return BoxBuilder.builder()
                .withStyleClass("power-legend")
                .withNodes(
                        circle,
                        LabelBuilder.builder()
                                .withStyleClass("legend-text")
                                .withNonLocalizedText(number)
                                .build())
                .buildHBox();
    }


    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, ShipConfigEvent.class, event -> update()));
        register(EventService.addListener(true, this, HardpointGroupSelectedEvent.class, event -> {
            if (event.isEnabled()) {
                selectedHardpointGroups.add(event.getGroup());
            } else {
                selectedHardpointGroups.remove(event.getGroup());
            }
            update();
        }));
    }


    @Override
    protected void update() {
        this.idleThermals.addBinding(this.idleThermals.textProperty(), calculateIdleThermals().format());
        this.thrusterThermals.addBinding(this.thrusterThermals.textProperty(), calculateThrusterThermals().format());
        this.fsdThermals.addBinding(this.fsdThermals.textProperty(), calculateFsdThermals().format());
        this.silentRunTime.addBinding(this.silentRunTime.textProperty(), calculateSilentRunTime().format());
        this.weaponThermalsFull.addBinding(this.weaponThermalsFull.textProperty(), calculateWeaponThermalsFull().format());
        this.weaponThermalsEmpty.addBinding(this.weaponThermalsEmpty.textProperty(), calculateWeaponThermalsEmpty().format());
        this.sensorRange.addBinding(this.sensorRange.textProperty(), LocaleService.getStringBinding("ship.stats.ship.sensorrange.value", Formatters.NUMBER_FORMAT_0.format(calculateSensorRangeMin()), Formatters.NUMBER_FORMAT_0.format(calculateSensorRangeMax())));
        this.effectiveSensorRange.addBinding(this.effectiveSensorRange.textProperty(), LocaleService.getStringBinding("ship.stats.ship.effectivesensorrange.value", Formatters.NUMBER_FORMAT_0.format(calculateEffectiveSensorRangeMin()), Formatters.NUMBER_FORMAT_0.format(calculateEffectiveSensorRangeMax())));
    }

    private double calculateEffectiveSensorRangeMax() {
        final PowerProfile retractedPower = calculateRetractedPower();
        double powerPlantEfficiency = getPowerPlantEfficiency();
        final double retractedUsage = retractedPower.usedPower();
        final double thermalLoad = retractedUsage * powerPlantEfficiency;
        return Math.min(14000D, Math.max(getShipSensorRangeMax(), getShip()
                .map(ship -> 1.75 * getShipSensorRangeMax() * Math.pow((1D + (thermalLoad - (double)ship.getAttributes().get(HorizonsModifier.HEAT_DISSIPATION_MIN)) / (double)ship.getAttributes().get(HorizonsModifier.HEAT_DISSIPATION_MIN)), 2D))
                .orElse(0D)));
    }

    private double calculateEffectiveSensorRangeMin() {
        final PowerProfile retractedPower = calculateRetractedPower();
        double powerPlantEfficiency = getPowerPlantEfficiency();
        final double retractedUsage = retractedPower.usedPower();
        final double thermalLoad = retractedUsage * powerPlantEfficiency;
        return Math.min(14000D, Math.max(getShipSensorRangeMin(), getShip()
                .map(ship -> getShipSensorRangeMin() * Math.pow((1D + (thermalLoad - (double)ship.getAttributes().get(HorizonsModifier.HEAT_DISSIPATION_MIN)) / (double)ship.getAttributes().get(HorizonsModifier.HEAT_DISSIPATION_MIN)), 2D))
                .orElse(0D)));
    }
    private double getPowerPlantEfficiency() {
        return (double) getShip().map(ship -> ship.getCoreSlots().stream()
                .filter(slot -> SlotType.CORE_POWER_PLANT.equals(slot.getSlotType()))
                .filter(Slot::isOccupied)
                .filter(slot -> slot.getShipModule().isPowered())
                .findFirst()
                .map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.HEAT_EFFICIENCY, true))
                .orElse(Double.NaN)).orElse(1D);
    }

    private double calculateSensorRangeMax() {
        return getShipSensorRangeMax();
    }

    private double calculateSensorRangeMin() {
        return getShipSensorRangeMin();
    }

    private double getShipSensorRangeMax() {
        return getShipSensorRangeMin() / 4000D * 7680D * 1.75;
    }

    private Double getShipSensorRangeMin() {
        return getShip().map(ship -> (double) ship.getAttributes().get(HorizonsModifier.SENSOR_LOCK_MIN)).orElse(0D);
    }

    private Value calculateSilentRunTime() {
        return getShip().map(ship -> {
            final double heatCapacity = (double) ship.getAttributes().getOrDefault(HorizonsModifier.HEAT_CAPACITY, 0d);
            final double maximumHeatDissipation = (double) ship.getAttributes().getOrDefault(HorizonsModifier.HEAT_DISSIPATION_MAX, 0d);
            final double powerCapacity = ship.getPowerCapacity();
            final double heatEfficiency = (double) ship.getCoreSlots().stream()
                    .filter(slot -> SlotType.CORE_POWER_PLANT.equals(slot.getSlotType()))
                    .filter(Slot::isOccupied)
                    .filter(slot -> slot.getShipModule().isPowered())
                    .findFirst()
                    .map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.HEAT_EFFICIENCY, true))
                    .orElse(Double.NaN);
            final double engineHeat = (double) ship.getCoreSlots().stream()
                    .filter(slot -> SlotType.CORE_THRUSTERS.equals(slot.getSlotType()))
                    .filter(Slot::isOccupied)
                    .filter(slot -> slot.getShipModule().isPowered())
                    .findFirst()
                    .map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.ENGINE_THERMAL_LOAD, true))
                    .orElse(Double.NaN);

            final double powerForHeat = getPowerForHeat(powerCapacity, heatEfficiency);

            double equilibriumHeatLevel = getEquilibriumHeatLevel(maximumHeatDissipation, powerForHeat + engineHeat) / 1.5;
            return new Value((heatCapacity * 1.5 - heatCapacity * 1.5 * equilibriumHeatLevel / 100D) / (powerForHeat + engineHeat), Value.ValueType.SECONDS);

        }).orElse(new Value(0D, Value.ValueType.SECONDS));
    }

    Value getHeatLevel(double thermalLoad, double baseThermalLoad, double maximumHeatDissipation, double heatCapacity) {
        if (thermalLoad > 0D) {
            thermalLoad += baseThermalLoad;
            if (baseThermalLoad > maximumHeatDissipation) {
                return new Value(Double.NaN, Value.ValueType.ERROR);//error
            } else if (thermalLoad > maximumHeatDissipation) {
                var heatlevelBase = getEquilibriumHeatLevel(maximumHeatDissipation, baseThermalLoad);
                var time10 = getTimeUntilHeatLevel(heatCapacity, maximumHeatDissipation, thermalLoad, heatlevelBase, 1.0);
                var time15 = (heatCapacity / 2) / (thermalLoad - maximumHeatDissipation); // displayed heatlevel 66% -> 100% is actual heatlevel 1.0 -> 1.5
                return new Value(time10 + time15, Value.ValueType.SECONDS);// <- time?
            } else {
                return new Value(getEquilibriumHeatLevel(maximumHeatDissipation, thermalLoad) / 1.5 * 100, Value.ValueType.PERCENTAGE);
            }
        }
        return new Value(Double.NaN, Value.ValueType.ERROR);
    }

    Value getHeatLevelForDuration(double addedThermalLoad, double baseThermalLoad, double maximumHeatDissipation, double heatCapacity, double duration) {
        double thermalLoad = addedThermalLoad + baseThermalLoad;
        if (thermalLoad > 0) {
            if (baseThermalLoad > maximumHeatDissipation) {
                return new Value(Double.NaN, Value.ValueType.ERROR);//error
            } else if (thermalLoad > maximumHeatDissipation) {
                var baseHeatLevel = getEquilibriumHeatLevel(maximumHeatDissipation, baseThermalLoad);
                var timeTo66 = getTimeUntilHeatLevel(heatCapacity, maximumHeatDissipation, thermalLoad, baseHeatLevel, 1.0);//time to get to 66%
                if ((timeTo66 > duration)) {//If the time to get to 66% exceeds the requested duration
                    return new Value(getHeatLevelAtTime(heatCapacity, maximumHeatDissipation, thermalLoad, baseHeatLevel, duration) / 1.5 * 100, Value.ValueType.PERCENTAGE);
                } else {//We reached 66% before requested duration
                    double remaining33 = heatCapacity / 2;
                    double notDissipatedHeat = thermalLoad - maximumHeatDissipation;
                    var timeFrom66to100 = remaining33 / notDissipatedHeat;
                    return new Value(timeTo66 + timeFrom66to100, Value.ValueType.SECONDS);
                }
            } else {
                return new Value(getEquilibriumHeatLevel(maximumHeatDissipation, thermalLoad) / 1.5 * 100, Value.ValueType.PERCENTAGE);
            }
        }
        return new Value(0.0, Value.ValueType.PERCENTAGE);
    }
    static double getTimeUntilHeatLevel(
            double heatCapacity,
            double maximumHeatDissipation,
            double thermalLoad,
            double heatLevelAtStart,
            double heatLevelTarget) {

        // Normalize by heat capacity
        double L = thermalLoad / heatCapacity;
        double D = maximumHeatDissipation / heatCapacity;

        // Equilibrium heat level
        double H_eq = Math.sqrt(L / D);

        // Safety check (physics requirement)
        if (Math.abs(heatLevelAtStart) >= H_eq ||
                Math.abs(heatLevelTarget) >= H_eq) {
            throw new IllegalArgumentException(
                    "Heat levels must be below equilibrium.");
        }

        double rate = Math.sqrt(L * D);

        return (
                atanh(heatLevelTarget / H_eq)
                        - atanh(heatLevelAtStart / H_eq)
        ) / rate;
    }
//    double getTimeUntilHeatLevel(double heatCapacity, double maximumHeatDissipation, double thermalLoad, double heatLevelAtStart, double heatLevel) {
//        // https://forums.frontier.co.uk/threads/research-detailed-heat-mechanics.286628/post-6519883
//        maximumHeatDissipation /= heatCapacity;
//
//
//        thermalLoad /= heatCapacity;
//        var sqrtAdivB = Math.sqrt(maximumHeatDissipation / thermalLoad);
//        var sqrtAmulB = Math.sqrt(maximumHeatDissipation * thermalLoad);
//        var C = -atanh(heatLevelAtStart * sqrtAdivB) / sqrtAmulB;
//        return ((atanh(heatLevel * sqrtAdivB) / sqrtAmulB) + C);
//
////        if (thermalLoad == 0D) {
////            var C = -1 / (maximumHeatDissipation * heatLevelAtStart);
////            return ((1 / (maximumHeatDissipation * heatLevel)) + C);
////        } else if (thermalLoad > 0D) {
////            thermalLoad /= heatCapacity;
////            var sqrtAdivB = Math.sqrt(-maximumHeatDissipation / thermalLoad);
////            var sqrtAmulB = Math.sqrt(-maximumHeatDissipation * thermalLoad);
////
////            var c = 1 / sqrtAmulB * Math.atan(sqrtAdivB * heatLevelAtStart);
////
////            return -1 / sqrtAmulB * Math.atan(sqrtAdivB * heatLevel) + c;
////
////        }
////        thermalLoad /= heatCapacity;
////        var sqrtAdivB = Math.sqrt(maximumHeatDissipation / thermalLoad);
////        var sqrtAmulB = Math.sqrt(maximumHeatDissipation * thermalLoad);
////        var cReal = 1D / sqrtAmulB * 1D / 2D * (Math.log(1D + sqrtAdivB * heatLevelAtStart) - Math.log(Math.abs(1D - sqrtAdivB * heatLevelAtStart)));
////        return 1D / sqrtAmulB * 1D / 2D * (Math.log(1D + sqrtAdivB * heatLevel) - Math.log(Math.abs(1D - sqrtAdivB * heatLevel))) - cReal;
//    }


    double getHeatLevelAtTime(double heatCapacity, double maximumHeatDissipation, double thermalLoad, double heatLevelAtStart, double seconds) {
        maximumHeatDissipation /= heatCapacity;
        thermalLoad /= heatCapacity;
        var sqrtAdivB = Math.sqrt(maximumHeatDissipation / thermalLoad);
        var sqrtAmulB = Math.sqrt(maximumHeatDissipation * thermalLoad);
        var C = -atanh(heatLevelAtStart * sqrtAdivB) / sqrtAmulB;
        return (tanh((seconds - C) * sqrtAmulB) / sqrtAdivB);
//        if (thermalLoad == 0D) {
//            var C = -1 / (maximumHeatDissipation * heatLevelAtStart);
//            return ((1 / (seconds - C)) / maximumHeatDissipation);
//        } else if (thermalLoad > 0) {
//            thermalLoad /= heatCapacity;
//            var sqrtAdivB = Math.sqrt(-maximumHeatDissipation / thermalLoad);
//            var sqrtAmulB = Math.sqrt(-maximumHeatDissipation * thermalLoad);
//
//            var c = 1 / sqrtAmulB * Math.atan(sqrtAdivB * heatLevelAtStart);
//            return 1 / sqrtAdivB * Math.tan(sqrtAmulB * (-seconds + c));
//
//        }
//        thermalLoad /= heatCapacity;
//        var sqrtAdivB = Math.sqrt(maximumHeatDissipation / thermalLoad);
//        var sqrtAmulB = Math.sqrt(maximumHeatDissipation * thermalLoad);
//        var cReal = 1 / sqrtAmulB * 1 / 2 * (Math.log(1 + sqrtAdivB * heatLevelAtStart) - Math.log(Math.abs(1 - sqrtAdivB * heatLevelAtStart)));
//        var cImag = 1 / sqrtAmulB * 1 / 2 * (-Math.atan2(0, 1 - sqrtAdivB * heatLevelAtStart));
//        return Math.sqrt(thermalLoad / maximumHeatDissipation) * (Math.exp(4 * sqrtAmulB * (seconds + cReal)) - 1) / (Math.exp(4 * sqrtAmulB * (seconds + cReal)) + 2 * Math.exp(2 * sqrtAmulB * (seconds + cReal)) * Math.cos(2 * sqrtAmulB * cImag) + 1);
    }

    static double atanh(double x) {
        return 0.5 * Math.log((1.0 + x) / (1.0 - x));
    }

    double tanh(double x) {
        var a = Math.exp(+x);
        var b = Math.exp(-x);
        return ((Double.isInfinite(a)) ? 1 : ((Double.isInfinite(b)) ? -1 : ((a - b) / (a + b))));
    }

    static double getEquilibriumHeatLevel(double maximumHeatDissipation, double thermalLoad) {
        return Math.sqrt(thermalLoad / maximumHeatDissipation);
    }

    private Value calculateIdleThermals() {
        return getShip().map(ship -> {
            final double heatCapacity = (double) ship.getAttributes().getOrDefault(HorizonsModifier.HEAT_CAPACITY, 0d);
            final double maximumHeatDissipation = (double) ship.getAttributes().getOrDefault(HorizonsModifier.HEAT_DISSIPATION_MAX, 0d);
            final double powerCapacity = ship.getPowerCapacity();
            final double heatEfficiency = (double) ship.getCoreSlots().stream()
                    .filter(slot -> SlotType.CORE_POWER_PLANT.equals(slot.getSlotType()))
                    .filter(Slot::isOccupied)
                    .filter(slot -> slot.getShipModule().isPowered())
                    .findFirst()
                    .map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.HEAT_EFFICIENCY, true))
                    .orElse(Double.NaN);

            final double powerForHeat = getPowerForHeat(powerCapacity, heatEfficiency);

            return getHeatLevelForDuration(powerForHeat, 0, maximumHeatDissipation, heatCapacity, 60);
        }).orElse(new Value(0D, Value.ValueType.PERCENTAGE));
    }

    private double getDeployedPowerForHeat(double powerCapacity, double heatEfficiency) {
        final Map<Integer, Double> powerPerGroup = calculateDeployedPowerThermal();
        double powerForHeat = powerPerGroup.values().stream().reduce(0D, Double::sum);
        //iterating over power groups, disabling groups while we are overpower
        for (var p = 5; p >= 1 && powerForHeat > powerCapacity; p--) {
            powerForHeat -= powerPerGroup.get(p);
        }
        powerForHeat *= heatEfficiency;
        return powerForHeat;
    }
    private double getPowerForHeat(double powerCapacity, double heatEfficiency) {
        final Map<Integer, Double> powerPerGroup = calculateRetractedPowerThermal();
        double powerForHeat = powerPerGroup.values().stream().reduce(0D, Double::sum);
        //iterating over power groups, disabling groups while we are overpower
        for (var p = 5; p >= 1 && powerForHeat > powerCapacity; p--) {
            powerForHeat -= powerPerGroup.get(p);
        }
        powerForHeat *= heatEfficiency;
        return powerForHeat;
    }

    private Value calculateWeaponThermalsFull() {
        return calculateWeaponThermalsBase(1.0);
    }
    private Value calculateWeaponThermalsEmpty() {
        return calculateWeaponThermalsBase(0.0);
    }
    private Value calculateWeaponThermalsBase(final double usedCapacityPercentage) {
        return getShip().map(ship -> {
            final double heatCapacity = (double) ship.getAttributes().getOrDefault(HorizonsModifier.HEAT_CAPACITY, 0d);
            final double maximumHeatDissipation = (double) ship.getAttributes().getOrDefault(HorizonsModifier.HEAT_DISSIPATION_MAX, 0d);
            final double powerCapacity = ship.getPowerCapacity();
            final double heatEfficiency = (double) ship.getCoreSlots().stream()
                    .filter(slot -> SlotType.CORE_POWER_PLANT.equals(slot.getSlotType()))
                    .filter(Slot::isOccupied)
                    .filter(slot -> slot.getShipModule().isPowered())
                    .findFirst()
                    .map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.HEAT_EFFICIENCY, true))
                    .orElse(Double.NaN);
            final double engineHeat = (double) ship.getCoreSlots().stream()
                    .filter(slot -> SlotType.CORE_THRUSTERS.equals(slot.getSlotType()))
                    .filter(Slot::isOccupied)
                    .filter(slot -> slot.getShipModule().isPowered())
                    .findFirst()
                    .map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.ENGINE_THERMAL_LOAD, true))
                    .orElse(Double.NaN);
            final double weaponCapacity = (double) ship.getCoreSlots().stream()
                    .filter(slot -> SlotType.CORE_POWER_DISTRIBUTION.equals(slot.getSlotType()))
                    .filter(Slot::isOccupied)
                    .filter(slot -> slot.getShipModule().isPowered())
                    .findFirst()
                    .map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.WEAPONS_CAPACITY, true))
                    .orElse(Double.NaN);
            final double weaponRecharge = (double) ship.getCoreSlots().stream()
                    .filter(slot -> SlotType.CORE_POWER_DISTRIBUTION.equals(slot.getSlotType()))
                    .filter(Slot::isOccupied)
                    .filter(slot -> slot.getShipModule().isPowered())
                    .findFirst()
                    .map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.WEAPONS_RECHARGE, true))
                    .orElse(Double.NaN);

            final double weaponHeat = ship.getHardpointSlots().stream()
                    .filter(Slot::isOccupied)
                    .filter(slot -> slot.getShipModule().isPowered())
                    .filter(slot -> selectedHardpointGroups.contains(slot.getHardpointGroup()))
                    .mapToDouble(slot -> {
                        double thermalLoad = (double) slot.getShipModule().getAttributeValue(HorizonsModifier.THERMAL_LOAD, true);
                        double distDraw = (double) slot.getShipModule().getAttributeValue(HorizonsModifier.DISTRIBUTOR_DRAW, true);
                        double rateOfFire = (double) slot.getShipModule().getAttributeValue(HorizonsModifier.RATE_OF_FIRE, true);
                        double heat = thermalLoad * (1 + 4 * Math.clamp(1 - ((weaponCapacity * usedCapacityPercentage) - distDraw) / weaponCapacity, 0D, 1D));
                        heat *= (Double.POSITIVE_INFINITY == rateOfFire) ? 1 : rateOfFire;
                        return heat;
                    })
                    .sum();

            final double powerForHeat = getDeployedPowerForHeat(powerCapacity, heatEfficiency);
            //powerForHeat should never be 0, since that would mean everything is unpowered
            if(powerForHeat == 0.0){
                return new Value(Double.NaN, Value.ValueType.ERROR);
            }
            return getHeatLevelForDuration(weaponHeat, powerForHeat + engineHeat, maximumHeatDissipation, heatCapacity, 60);
        }).orElse(new Value(0D, Value.ValueType.PERCENTAGE));
    }

    private Value calculateThrusterThermals() {
        return getShip().map(ship -> {
            final double heatCapacity = (double) ship.getAttributes().getOrDefault(HorizonsModifier.HEAT_CAPACITY, 0d);
            final double maximumHeatDissipation = (double) ship.getAttributes().getOrDefault(HorizonsModifier.HEAT_DISSIPATION_MAX, 0d);
            final double powerCapacity = ship.getPowerCapacity();
            final double heatEfficiency = (double) ship.getCoreSlots().stream()
                    .filter(slot -> SlotType.CORE_POWER_PLANT.equals(slot.getSlotType()))
                    .filter(Slot::isOccupied)
                    .filter(slot -> slot.getShipModule().isPowered())
                    .findFirst()
                    .map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.HEAT_EFFICIENCY, true))
                    .orElse(Double.NaN);
            final double engineHeat = (double) ship.getCoreSlots().stream()
                    .filter(slot -> SlotType.CORE_THRUSTERS.equals(slot.getSlotType()))
                    .filter(Slot::isOccupied)
                    .filter(slot -> slot.getShipModule().isPowered())
                    .findFirst()
                    .map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.ENGINE_THERMAL_LOAD, true))
                    .orElse(Double.NaN);

            final double powerForHeat = getPowerForHeat(powerCapacity, heatEfficiency);

            return getHeatLevelForDuration(engineHeat, powerForHeat, maximumHeatDissipation, heatCapacity, 60);
        }).orElse(new Value(0D, Value.ValueType.PERCENTAGE));
    }

    private Value calculateFsdThermals() {
        return getShip().map(ship -> {
            final double heatCapacity = (double) ship.getAttributes().getOrDefault(HorizonsModifier.HEAT_CAPACITY, 0d);
            final double maximumHeatDissipation = (double) ship.getAttributes().getOrDefault(HorizonsModifier.HEAT_DISSIPATION_MAX, 0d);
            final double powerCapacity = ship.getPowerCapacity();
            final double heatEfficiency = (double) ship.getCoreSlots().stream()
                    .filter(slot -> SlotType.CORE_POWER_PLANT.equals(slot.getSlotType()))
                    .filter(Slot::isOccupied)
                    .filter(slot -> slot.getShipModule().isPowered())
                    .findFirst()
                    .map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.HEAT_EFFICIENCY, true))
                    .orElse(Double.NaN);
            final double engineHeat = (double) ship.getCoreSlots().stream()
                    .filter(slot -> SlotType.CORE_THRUSTERS.equals(slot.getSlotType()))
                    .filter(Slot::isOccupied)
                    .filter(slot -> slot.getShipModule().isPowered())
                    .findFirst()
                    .map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.ENGINE_THERMAL_LOAD, true))
                    .orElse(Double.NaN);
            final double fsdHeat = (double) ship.getCoreSlots().stream()
                    .filter(slot -> SlotType.CORE_FRAME_SHIFT_DRIVE.equals(slot.getSlotType()))
                    .filter(Slot::isOccupied)
                    .filter(slot -> slot.getShipModule().isPowered())
                    .findFirst()
                    .map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.FSD_HEAT_RATE, true))
                    .orElse(Double.NaN);

            final double powerForHeat = getPowerForHeat(powerCapacity, heatEfficiency);

            return getHeatLevelForDuration(fsdHeat, powerForHeat + engineHeat, maximumHeatDissipation, heatCapacity, 60);
        }).orElse(new Value(0D, Value.ValueType.PERCENTAGE));
    }

    private PowerProfile calculateRetractedPower() {
        return getShip().map(Ship::getRetractedPower).orElseGet(PowerProfile::new);
    }

    private PowerProfile calculateDeployedPower() {
        return getShip().map(Ship::getDeployedPower).orElseGet(PowerProfile::new);
    }

    private Map<Integer, Double> calculateRetractedPowerThermal() {
        Map<Integer, Double> powerValues = new HashMap<>(Map.of(
                -1, 0D,
                1, 0D,
                2, 0D,
                3, 0D,
                4, 0D,
                5, 0D
        ));

        getShip().ifPresent(ship -> {
            if (ship.getCargoHatch().isOccupied() && ship.getCargoHatch().getShipModule().isPowered()) {
                powerValues.compute(ship.getCargoHatch().getShipModule().isPassivePowerWithoutToggle() ? -1 : ship.getCargoHatch().getShipModule().getPowerGroup(), (key, value) -> value + (double) ship.getCargoHatch().getShipModule().getAttributeValue(HorizonsModifier.POWER_DRAW, true));
            }
            ship.getUtilitySlots().stream()
                    .filter(Slot::isOccupied)
                    .filter(slot -> slot.getShipModule().isPassivePower())
                    .filter(slot -> slot.getShipModule().isPowered())
                    .forEach(slot -> powerValues.compute(slot.getShipModule().isPassivePowerWithoutToggle() ? -1 : slot.getShipModule().getPowerGroup(), (key, value) -> value + (double) slot.getShipModule().getAttributeValue(HorizonsModifier.POWER_DRAW, true)));
            ship.getOptionalSlots().stream()
                    .filter(Slot::isOccupied)
                    .filter(slot -> slot.getShipModule().isPowered())
                    .forEach(slot -> powerValues.compute(slot.getShipModule().isPassivePowerWithoutToggle() ? -1 : slot.getShipModule().getPowerGroup(), (key, value) -> value + (double) slot.getShipModule().getAttributeValue(HorizonsModifier.POWER_DRAW, true)));
            ship.getCoreSlots().stream()
                    .filter(Slot::isOccupied)
                    .filter(slot -> slot.getShipModule().isPowered())
                    .forEach(slot -> powerValues.compute(slot.getShipModule().isPassivePowerWithoutToggle() ? -1 : slot.getShipModule().getPowerGroup(), (key, value) -> value + (double) slot.getShipModule().getAttributeValue(HorizonsModifier.POWER_DRAW, true)));
        });
        return powerValues;
    }

    private Map<Integer, Double> calculateDeployedPowerThermal() {
        Map<Integer, Double> powerValues = calculateRetractedPowerThermal();
        getShip().ifPresent(ship -> {
            ship.getHardpointSlots().stream()
                    .filter(Slot::isOccupied)
                    .filter(slot -> slot.getShipModule().isPowered())
                    .forEach(slot -> powerValues.compute(
                            slot.getShipModule().isPassivePowerWithoutToggle() ? -1 : slot.getShipModule().getPowerGroup(),
                            (key, value) -> value + (double) slot.getShipModule().getAttributeValue(HorizonsModifier.POWER_DRAW, true)
                    ));
            ship.getUtilitySlots().stream()
                    .filter(Slot::isOccupied)
                    .filter(slot -> !slot.getShipModule().isPassivePower())
                    .forEach(slot -> powerValues.compute(
                            slot.getShipModule().isPassivePowerWithoutToggle() ? -1 : slot.getShipModule().getPowerGroup(),
                            (key, value) -> value + (double) slot.getShipModule().getAttributeValue(HorizonsModifier.POWER_DRAW, true)
                    ));
        });

        return powerValues;
    }


    @AllArgsConstructor
    static class Value {
        enum ValueType {
            PERCENTAGE,
            SECONDS,
            ERROR
        }

        @Getter
        private double value;
        private Value.ValueType valueType;

        StringBinding format() {
            switch (valueType) {
                case PERCENTAGE:
                    return LocaleService.getStringBinding("ship.stats.thermal.thermals.value.percentage", Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(value));
                case SECONDS:
                    return LocaleService.getStringBinding("ship.stats.thermal.thermals.value.time", ConvertSecondToHHMMSSString((int) value));
                case ERROR:
                    return LocaleService.getStringBinding("ship.stats.thermal.thermals.value.error");
            }
            return LocaleService.getStringBinding("ship.stats.thermal.thermals.value.error");
        }

        private String ConvertSecondToHHMMSSString(int nSecondTime) {
            return DateTimeFormatter.ISO_TIME.format(LocalTime.MIN.plusSeconds(nSecondTime));
        }
    }
}
