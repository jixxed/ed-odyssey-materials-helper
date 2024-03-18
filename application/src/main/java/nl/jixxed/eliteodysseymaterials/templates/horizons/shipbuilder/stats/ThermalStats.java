package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.beans.binding.StringBinding;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ships.Slot;
import nl.jixxed.eliteodysseymaterials.domain.ships.SlotType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipConfigEvent;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ThermalStats extends Stats implements Template {
    private DestroyableLabel idleThermals;
    private DestroyableLabel thrusterThermals;
    private DestroyableLabel fsdThermals;

    public ThermalStats() {
        super();
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getChildren().add(BoxBuilder.builder().withNodes(new GrowingRegion(), createTitle("ship.stats.thermal"), new GrowingRegion()).buildHBox());
        this.getChildren().add(new Separator(Orientation.HORIZONTAL));
        this.idleThermals = createValueSmallLabel("ship.stats.thermal.idle.thermals.value", Formatters.NUMBER_FORMAT_2.format(0D));
        this.thrusterThermals = createValueSmallLabel("ship.stats.thermal.thruster.thermals.value", Formatters.NUMBER_FORMAT_2.format(0D));
        this.fsdThermals = createValueSmallLabel("ship.stats.thermal.fsd.thermals.value", Formatters.NUMBER_FORMAT_2.format(0D));

        this.getChildren().add(BoxBuilder.builder().withNodes(createSmallLabel("ship.stats.thermal.idle.thermals"), new GrowingRegion(), this.idleThermals).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createSmallLabel("ship.stats.thermal.thruster.thermals"), new GrowingRegion(), this.thrusterThermals).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createSmallLabel("ship.stats.thermal.fsd.thermals"), new GrowingRegion(), this.fsdThermals).buildHBox());

    }

    @Override
    public void initEventHandling() {
        eventListeners.add(EventService.addListener(this, ShipConfigEvent.class, event -> update()));
    }

    Value getHeatLevel(double thermalLoad, double baseThermalLoad, double maximumHeatDissipation, double heatCapacity) {
        if (thermalLoad > 0) {
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

    //    var updateUIStatsThmLevel = function(elementid, thmload, thmloadBase, heatdismax, heatcap, duration) {
//        var el = document.getElementById(elementid);
//        el.className = '';
//        if (thmload > 0) {
//            thmload += thmloadBase;
//            if (thmloadBase > heatdismax) {
//                el.innerHTML = '<small class="semantic" edsy-text="n-a">N/A</small>';
//                el.className = 'error';
//            } else if (thmload > heatdismax) {
//                var heatlevelBase = getEquilibriumHeatLevel(heatdismax, thmloadBase);
//                var time10 = getTimeUntilHeatLevel(heatcap, heatdismax, thmload, heatlevelBase, 1.0);
//                if (duration && (time10 > duration)) {
//                    var heatpct = getHeatLevelAtTime(heatcap, heatdismax, thmload, heatlevelBase, duration) / 1.5;
//                    el.innerHTML = ('<abbr edsy-vals="' + encodeHTML(JSON.stringify({'number':heatpct,'number#':6,'number%':true})) + '" edsy-title="interp-number" title="' + formatPctText(heatpct, 6) + '">' + formatPctHTML(heatpct, 1) + '</abbr>');
//                } else {
//                    var time15 = (heatcap / 2) / (thmload - heatdismax); // displayed heatlevel 66% -> 100% is actual heatlevel 1.0 -> 1.5
//                    if (duration && ((time10 + time15) > duration)) {
//                        var heatpct = (2 + ((duration - time10) / time15)) / 3;
//                        el.innerHTML = ('<abbr edsy-vals="' + encodeHTML(JSON.stringify({'number':heatpct,'number#':6,'number%':true})) + '" edsy-title="interp-number" title="' + formatPctText(heatpct, 6) + '">' + formatPctHTML(heatpct, 1) + '</abbr>');
//                    } else if (duration) {
//                        duration -= time10 + time15;
//                        var heatlevelPeak = 1.5 + (duration * (thmload - heatdismax) / heatcap);
//                        var timeCool = (heatlevelPeak - 1.5) / ((heatdismax - thmloadBase) / heatcap);
//                        el.innerHTML = ('<abbr class="error" edsy-vals="' + encodeHTML(JSON.stringify({'number':(heatlevelPeak/1.5),'number#':1,'number%':true,'seconds':(duration + timeCool),'seconds#':1})) + '" edsy-title="interp-peak-heat-number-overheat-seconds" title="Peak heat level ' + formatPctText(heatlevelPeak / 1.5, 1) + '; over 100% for ' + formatNumText(duration + timeCool, 1) + 's">' + formatTimeHTML(time10 + time15, true) + '</abbr>');
//                    } else {
//                        el.innerHTML = formatTimeHTML(time10 + time15, true);
//                        el.className = 'error';
//                    }
//                }
//            } else {
//                var heatpct = getEquilibriumHeatLevel(heatdismax, thmload) / 1.5;
//                el.innerHTML = ('<abbr edsy-vals="' + encodeHTML(JSON.stringify({'number':heatpct,'number#':6,'number%':true})) + '" edsy-title="interp-number" title="' + formatPctText(heatpct, 6) + '">' + formatPctHTML(heatpct, 1) + '</abbr>');
//            }
//        } else {
//            el.innerHTML = '<small class="semantic" edsy-text="n-a">N/A</small>';
//        }
//    }; // updateUIStatsThmLevel()
    double getHeatLevelForDuration(double thermalLoad, double baseThermalLoad, double maximumHeatDissipation, double heatCapacity, double duration) {
        if (thermalLoad > 0) {
            thermalLoad += baseThermalLoad;
            if (baseThermalLoad > maximumHeatDissipation) {
                return Double.NaN;//error
            } else if (thermalLoad > maximumHeatDissipation) {
                var baseHeatLevel = getEquilibriumHeatLevel(maximumHeatDissipation, baseThermalLoad);
                var time10 = getTimeUntilHeatLevel(heatCapacity, maximumHeatDissipation, thermalLoad, baseHeatLevel, 1.0);
                if ((time10 > duration)) {
                    return getHeatLevelAtTime(heatCapacity, maximumHeatDissipation, thermalLoad, baseHeatLevel, duration) / 1.5;
                } else {
                    var time15 = (heatCapacity / 2) / (thermalLoad - maximumHeatDissipation); // displayed heatlevel 66% -> 100% is actual heatlevel 1.0 -> 1.5
                    if ((time10 + time15) > duration) {
                        return (2 + ((duration - time10) / time15)) / 3;
                    } else {
                        duration -= time10 + time15;
                        var peakHeatLevel = 1.5 + (duration * (thermalLoad - maximumHeatDissipation) / heatCapacity);
                        var timeToCool = (peakHeatLevel - 1.5) / ((maximumHeatDissipation - baseThermalLoad) / heatCapacity);
                    }
                }
            } else {
                return getEquilibriumHeatLevel(maximumHeatDissipation, thermalLoad) / 1.5;
            }
        }
        return Double.NaN;
    }
//    var getTimeUntilHeatLevel = function(heatcap, heatdismax, thmload, heatlevel0, heatlevel) {
//        // https://forums.frontier.co.uk/threads/research-detailed-heat-mechanics.286628/post-6519883
//        heatdismax /= heatcap;
//        if (!thmload) {
//            var C = -1 / (heatdismax * heatlevel0);
//            return ((1 / (heatdismax * heatlevel)) + C);
//        }
//        thmload /= heatcap;
//        var sqrtAdivB = sqrt(heatdismax / thmload);
//        var sqrtAmulB = sqrt(heatdismax * thmload);
//        var C = -atanh(heatlevel0 * sqrtAdivB) / sqrtAmulB
//        return ((atanh(heatlevel * sqrtAdivB) / sqrtAmulB) + C);
//    }; // getTimeUntilHeatLevel()

    double getTimeUntilHeatLevel(double heatCapacity, double maximumHeatDissipation, double thermalLoad, double heatLevelAtStart, double heatLevel) {
        // https://forums.frontier.co.uk/threads/research-detailed-heat-mechanics.286628/post-6519883
        maximumHeatDissipation /= heatCapacity;
        if (thermalLoad == 0D) {
            var C = -1 / (maximumHeatDissipation * heatLevelAtStart);
            return ((1 / (maximumHeatDissipation * heatLevel)) + C);
        }
        thermalLoad /= heatCapacity;
        var sqrtAdivB = Math.sqrt(maximumHeatDissipation / thermalLoad);
        var sqrtAmulB = Math.sqrt(maximumHeatDissipation * thermalLoad);
        var C = -atanh(heatLevelAtStart * sqrtAdivB) / sqrtAmulB;
        return ((atanh(heatLevel * sqrtAdivB) / sqrtAmulB) + C);
    }


    double getHeatLevelAtTime(double heatCapacity, double maximumHeatDissipation, double thermalLoad, double heatLevelAtStart, double seconds) {
        maximumHeatDissipation /= heatCapacity;
        if (thermalLoad > 0) {
            var C = -1 / (maximumHeatDissipation * heatLevelAtStart);
            return ((1 / (seconds - C)) / maximumHeatDissipation);
        }
        thermalLoad /= heatCapacity;
        var sqrtAdivB = Math.sqrt(maximumHeatDissipation / thermalLoad);
        var sqrtAmulB = Math.sqrt(maximumHeatDissipation * thermalLoad);
        var C = -atanh(heatLevelAtStart * sqrtAdivB) / sqrtAmulB;
        return (tanh((seconds - C) * sqrtAmulB) / sqrtAdivB);
    }

    double atanh(double x) {
        return (Math.log((1 + x) / (1 - x)) / 2);
    }

    double tanh(double x) {
        var a = Math.exp(+x);
        var b = Math.exp(-x);
        return ((Double.isInfinite(a)) ? 1 : ((Double.isInfinite(b)) ? -1 : ((a - b) / (a + b))));
    }

    double getEquilibriumHeatLevel(double maximumHeatDissipation, double thermalLoad) {
        return Math.sqrt(thermalLoad / maximumHeatDissipation);
    }

    private Value calculateIdleThermals() {
        return getShip().map(ship -> {
            final double heatCapacity = (double) ship.getAttributes().getOrDefault(HorizonsModifier.HEAT_CAPACITY, 0d);
            final double maximumHeatDissipation = (double) ship.getAttributes().getOrDefault(HorizonsModifier.HEAT_DISSIPATION_MAX, 0d);
            final double powerCapacity = (double) ship.getCoreSlots().stream()
                    .filter(slot -> SlotType.CORE_POWER_PLANT.equals(slot.getSlotType()))
                    .filter(slot -> slot.getShipModule().isPowered())
                    .findFirst()
                    .map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.POWER_CAPACITY))
                    .orElse(Double.NaN);
            final double heatEfficiency = (double) ship.getCoreSlots().stream()
                    .filter(slot -> SlotType.CORE_POWER_PLANT.equals(slot.getSlotType()))
                    .filter(slot -> slot.getShipModule().isPowered())
                    .findFirst()
                    .map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.HEAT_EFFICIENCY))
                    .orElse(Double.NaN);

            final double powerForHeat = getPowerForHeat(powerCapacity, heatEfficiency);

            return getHeatLevel(powerForHeat, 0, maximumHeatDissipation, heatCapacity);
        }).orElse(new Value(0D, Value.ValueType.PERCENTAGE));
    }

    private double getPowerForHeat(double powerCapacity, double heatEfficiency) {
        final Map<Integer, Double> powerPerGroup = calculateRetractedPower();
        double powerForHeat = powerPerGroup.values().stream().reduce(0D, Double::sum);
        //iterating over power groups
        for (var p = 5; p >= 1 && powerForHeat > powerCapacity; p--) {
            powerForHeat -= powerPerGroup.get(p);
        }
        powerForHeat *= heatEfficiency;
        return powerForHeat;
    }

    private Value calculateThrusterThermals() {
        return getShip().map(ship -> {
            final double heatCapacity = (double) ship.getAttributes().getOrDefault(HorizonsModifier.HEAT_CAPACITY, 0d);
            final double maximumHeatDissipation = (double) ship.getAttributes().getOrDefault(HorizonsModifier.HEAT_DISSIPATION_MAX, 0d);
            final double powerCapacity = (double) ship.getCoreSlots().stream()
                    .filter(slot -> SlotType.CORE_POWER_PLANT.equals(slot.getSlotType()))
                    .filter(slot -> slot.getShipModule().isPowered())
                    .findFirst()
                    .map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.POWER_CAPACITY))
                    .orElse(Double.NaN);
            final double heatEfficiency = (double) ship.getCoreSlots().stream()
                    .filter(slot -> SlotType.CORE_POWER_PLANT.equals(slot.getSlotType()))
                    .filter(slot -> slot.getShipModule().isPowered())
                    .findFirst()
                    .map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.HEAT_EFFICIENCY))
                    .orElse(Double.NaN);
            final double engineHeat = (double) ship.getCoreSlots().stream()
                    .filter(slot -> SlotType.CORE_THRUSTERS.equals(slot.getSlotType()))
                    .filter(slot -> slot.getShipModule().isPowered())
                    .findFirst()
                    .map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.ENGINE_THERMAL_LOAD))
                    .orElse(Double.NaN);

            final double powerForHeat = getPowerForHeat(powerCapacity, heatEfficiency);

            return getHeatLevel(engineHeat, powerForHeat, maximumHeatDissipation, heatCapacity);
        }).orElse(new Value(0D, Value.ValueType.PERCENTAGE));
    }

    private Value calculateFsdThermals() {
        return getShip().map(ship -> {
            final double heatCapacity = (double) ship.getAttributes().getOrDefault(HorizonsModifier.HEAT_CAPACITY, 0d);
            final double maximumHeatDissipation = (double) ship.getAttributes().getOrDefault(HorizonsModifier.HEAT_DISSIPATION_MAX, 0d);
            final double powerCapacity = (double) ship.getCoreSlots().stream()
                    .filter(slot -> SlotType.CORE_POWER_PLANT.equals(slot.getSlotType()))
                    .filter(slot -> slot.getShipModule().isPowered())
                    .findFirst()
                    .map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.POWER_CAPACITY))
                    .orElse(Double.NaN);
            final double heatEfficiency = (double) ship.getCoreSlots().stream()
                    .filter(slot -> SlotType.CORE_POWER_PLANT.equals(slot.getSlotType()))
                    .filter(slot -> slot.getShipModule().isPowered())
                    .findFirst()
                    .map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.HEAT_EFFICIENCY))
                    .orElse(Double.NaN);
            final double engineHeat = (double) ship.getCoreSlots().stream()
                    .filter(slot -> SlotType.CORE_THRUSTERS.equals(slot.getSlotType()))
                    .filter(slot -> slot.getShipModule().isPowered())
                    .findFirst()
                    .map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.ENGINE_THERMAL_LOAD))
                    .orElse(Double.NaN);
            final double fsdHeat = (double) ship.getCoreSlots().stream()
                    .filter(slot -> SlotType.CORE_FRAME_SHIFT_DRIVE.equals(slot.getSlotType()))
                    .filter(slot -> slot.getShipModule().isPowered())
                    .findFirst()
                    .map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.FSD_HEAT_RATE))
                    .orElse(Double.NaN);

            final double powerForHeat = getPowerForHeat(powerCapacity, heatEfficiency);

            return getHeatLevel(fsdHeat, powerForHeat + engineHeat, maximumHeatDissipation, heatCapacity);
        }).orElse(new Value(0D, Value.ValueType.PERCENTAGE));
    }

    private Map<Integer, Double> calculateRetractedPower() {
        Map<Integer, Double> powerValues = new HashMap<>(Map.of(
                1, 0D,
                2, 0D,
                3, 0D,
                4, 0D,
                5, 0D
        ));

        getShip().ifPresent(ship -> {
            if (ship.getCargoHatch().isOccupied() && ship.getCargoHatch().getShipModule().isPowered()) {
                powerValues.compute(ship.getCargoHatch().getShipModule().getPowerGroup(), (key, value) -> value + (double) ship.getCargoHatch().getShipModule().getAttributeValue(HorizonsModifier.POWER_DRAW));
            }
            ship.getUtilitySlots().stream()
                    .filter(Slot::isOccupied)
                    .filter(slot -> slot.getShipModule().isPassivePower())
                    .filter(slot -> slot.getShipModule().isPowered())
                    .forEach(slot -> powerValues.compute(slot.getShipModule().getPowerGroup(), (key, value) -> value + (double) slot.getShipModule().getAttributeValue(HorizonsModifier.POWER_DRAW)));
            ship.getOptionalSlots().stream()
                    .filter(Slot::isOccupied)
                    .filter(slot -> slot.getShipModule().isPowered())
                    .forEach(slot -> powerValues.compute(slot.getShipModule().getPowerGroup(), (key, value) -> value + (double) slot.getShipModule().getAttributeValue(HorizonsModifier.POWER_DRAW)));
            ship.getCoreSlots().stream()
                    .filter(Slot::isOccupied)
                    .filter(slot -> slot.getShipModule().isPowered())
                    .forEach(slot -> powerValues.compute(slot.getShipModule().getPowerGroup(), (key, value) -> value + (double) slot.getShipModule().getAttributeValue(HorizonsModifier.POWER_DRAW)));
        });
        return powerValues;
    }

    private Map<Integer, Double> calculateDeployedPower() {
        Map<Integer, Double> powerValues = calculateRetractedPower();
        getShip().ifPresent(ship -> {
            ship.getHardpointSlots().stream()
                    .filter(Slot::isOccupied)
                    .filter(slot -> slot.getShipModule().isPowered())
                    .forEach(slot -> powerValues.compute(
                            slot.getShipModule().getPowerGroup(),
                            (key, value) -> value + (double) slot.getShipModule().getAttributeValue(HorizonsModifier.POWER_DRAW)
                    ));
            ship.getUtilitySlots().stream()
                    .filter(Slot::isOccupied)
                    .filter(slot -> !slot.getShipModule().isPassivePower())
                    .forEach(slot -> powerValues.compute(
                            slot.getShipModule().getPowerGroup(),
                            (key, value) -> value + (double) slot.getShipModule().getAttributeValue(HorizonsModifier.POWER_DRAW)
                    ));
        });

        return powerValues;
    }


    @Override
    protected void update() {
        this.idleThermals.textProperty().bind(calculateIdleThermals().format());
        this.thrusterThermals.textProperty().bind(calculateThrusterThermals().format());
        this.fsdThermals.textProperty().bind(calculateFsdThermals().format());
    }

    @AllArgsConstructor
    class Value {
        enum ValueType {
            PERCENTAGE,
            SECONDS,
            ERROR
        }

        private double value;
        private ValueType valueType;

        StringBinding format() {
            switch (valueType) {
                case PERCENTAGE:
                    return LocaleService.getStringBinding("ship.stats.thermal.thermals.value.percentage", Formatters.NUMBER_FORMAT_2.format(value));
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
