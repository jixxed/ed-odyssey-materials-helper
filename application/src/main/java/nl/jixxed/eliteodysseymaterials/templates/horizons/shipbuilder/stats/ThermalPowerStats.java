package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ships.Slot;
import nl.jixxed.eliteodysseymaterials.domain.ships.SlotType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipConfigEvent;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ThermalPowerStats extends Stats implements Template {
    private DestroyableLabel idleThermals;
    private DestroyableLabel thrusterThermals;
    private DestroyableLabel fsdThermals;
    private DestroyableLabel retractedPower;
    private DestroyableLabel deployedPower;
    private PowerBar retractedPowerBar;
    private PowerBar deployedPowerBar;

    public ThermalPowerStats() {
        super();
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getChildren().add(BoxBuilder.builder().withNodes(new GrowingRegion(), createTitle("ship.stats.thermal"), new GrowingRegion()).buildHBox());
        this.getChildren().add(new Separator(Orientation.HORIZONTAL));
        this.idleThermals = createValueLabel(String.format("%.2f", 0D));
        this.thrusterThermals = createValueLabel(String.format("%.2f", 0D));
        this.fsdThermals = createValueLabel(String.format("%.2f", 0D));
        this.retractedPower = createValueLabel(String.format("%.2f", 0D));
        this.deployedPower = createValueLabel(String.format("%.2f", 0D));
        this.retractedPowerBar = new PowerBar();
        this.deployedPowerBar = new PowerBar();

        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.thermalpower.idleThermals"), new GrowingRegion(), this.idleThermals).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.thermalpower.thrusterThermals"), new GrowingRegion(), this.thrusterThermals).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.thermalpower.fsdThermals"), new GrowingRegion(), this.fsdThermals).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(new GrowingRegion(), createTitle("ship.stats.power"), new GrowingRegion()).buildHBox());
        this.getChildren().add(new Separator(Orientation.HORIZONTAL));
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.thermalpower.retractedPower"), new GrowingRegion(), this.retractedPower).buildHBox());
        this.getChildren().add(retractedPowerBar);
        this.getChildren().add(deployedPowerBar);
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.thermalpower.deployedPower"), new GrowingRegion(), this.deployedPower).buildHBox());

    }

    @Override
    public void initEventHandling() {
        eventListeners.add(EventService.addListener(this, ShipConfigEvent.class, event -> update()));
    }

    double getHeatLevel(double thermalLoad, double baseThermalLoad, double maximumHeatDissipation, double heatCapacity) {
        if (thermalLoad > 0) {
            thermalLoad += baseThermalLoad;
            if (baseThermalLoad > maximumHeatDissipation) {
                return Double.NaN;//error
            } else if (thermalLoad > maximumHeatDissipation) {
                var heatlevelBase = getEquilibriumHeatLevel(maximumHeatDissipation, baseThermalLoad);
                var time10 = getTimeUntilHeatLevel(heatCapacity, maximumHeatDissipation, thermalLoad, heatlevelBase, 1.0);
                var time15 = (heatCapacity / 2) / (thermalLoad - maximumHeatDissipation); // displayed heatlevel 66% -> 100% is actual heatlevel 1.0 -> 1.5
                return time10 + time15;
            } else {
                return getEquilibriumHeatLevel(maximumHeatDissipation, thermalLoad) / 1.5;
            }
        }
        return Double.NaN;
    }

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
//                        el.innerHTML = ('<abbr class="error" edsy-vals="' + encodeHTML(JSON.stringify({'number':(peakHeatLevel/1.5),'number#':1,'number%':true,'seconds':(duration + timeToCool),'seconds#':1})) + '"
//                        edsy-title="interp-peak-heat-number-overheat-seconds" title="Peak heat level ' + formatPctText(peakHeatLevel / 1.5, 1) + '; over 100% for ' + formatNumText(duration + timeToCool, 1) + 's">' +
//                        formatTimeHTML(time10 + time15, true) + '</abbr>');
                    }
                }
            } else {
                return getEquilibriumHeatLevel(maximumHeatDissipation, thermalLoad) / 1.5;
            }
        }
        return Double.NaN;
    }

    double getTimeUntilHeatLevel(double heatCapacity, double maximumHeatDissipation, double thermalLoad, double heatLevelAtStart, double heatLevel) {
        // https://forums.frontier.co.uk/threads/research-detailed-heat-mechanics.286628/post-6519883
        maximumHeatDissipation /= heatCapacity;
        if (thermalLoad > 0) {
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
        // https://forums.frontier.co.uk/threads/research-detailed-heat-mechanics.286628/post-6519883
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
        // https://forums.frontier.co.uk/threads/research-detailed-heat-mechanics.286628/post-6399855
        return Math.sqrt(thermalLoad / maximumHeatDissipation);
    }

//    var heatcap = slot.getEffectiveAttrValue('heatcap'); //ship HEAT_CAPACITY

//    var heatdismax = slot.getEffectiveAttrValue('heatdismax'); // ship HEAT_DISSIPATION_MAX


    //    var thmload_ct = current.fit.getStat('thmload_ct'); //thruster heat
//    var thmload_cfsd = current.fit.getStat('thmload_cfsd'); //fsd heat
//    var thmload_pwrdraw_ret = pwrdraw_ret[0];
//		for (var p = MAX_POWER_PRIORITY;  p >= 1 && thmload_pwrdraw_ret > pwrcap;  p--) {
//        thmload_pwrdraw_ret -= pwrdraw_ret[p];
//    }
//    thmload_pwrdraw_ret *= heateff;
//    var thmload_pwrdraw_dep = pwrdraw_dep[0];
//		for (var p = MAX_POWER_PRIORITY;  p >= 1 && thmload_pwrdraw_dep > pwrcap;  p--) {
//        thmload_pwrdraw_dep -= pwrdraw_dep[p];
//    }
    // update displays
//    updateUIStatsThmLevel('outfitting_stats_idl_heat'     , thmload_pwrdraw_ret       , 0                               , heatdismax, heatcap);
//    updateUIStatsThmLevel('outfitting_stats_thr_heat'     , thmload_ct                , thmload_pwrdraw_ret             , heatdismax, heatcap);
//    updateUIStatsThmLevel('outfitting_stats_fsd_heat'     , thmload_cfsd              , thmload_pwrdraw_ret + thmload_ct, heatdismax, heatcap);
//    updateUIStatsThmLevel('outfitting_stats_wpnfull_heat' , thmload_hardpoint_wepfull , thmload_pwrdraw_dep + thmload_ct, heatdismax, heatcap);
//    updateUIStatsThmLevel('outfitting_stats_wpnempty_heat', thmload_hardpoint_wepempty, thmload_pwrdraw_dep + thmload_ct, heatdismax, heatcap);
//    updateUIStatsThmLevel('outfitting_stats_scb_heat'     , thmload_iscb              , thmload_pwrdraw_dep + thmload_ct, heatdismax, heatcap, spinup_iscb);
    private double calculateIdleThermals() {
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

            return getHeatLevel(powerForHeat, 0, maximumHeatDissipation, heatCapacity) * 100;
        }).orElse(0D);
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

    private double calculateThrusterThermals() {
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

            return getHeatLevel(engineHeat, powerForHeat, maximumHeatDissipation, heatCapacity) * 100;
        }).orElse(0D);
    }

    private double calculateFsdThermals() {
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

            return getHeatLevel(fsdHeat, powerForHeat + engineHeat, maximumHeatDissipation, heatCapacity) * 100;
        }).orElse(0D);
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
            if(ship.getCargoHatch().isOccupied() && ship.getCargoHatch().getShipModule().isPowered()){
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
        log.debug("update thermals/power: " + this.getShip().isPresent());
        this.getShip().ifPresent(ship1 -> log.debug("type: " + ship1.getShipType()));
        this.idleThermals.setText(String.format("%.2f", calculateIdleThermals()));
        this.thrusterThermals.setText(String.format("%.2f", calculateThrusterThermals()));
        this.fsdThermals.setText(String.format("%.2f", calculateFsdThermals()));
        this.retractedPower.setText(String.format("%.2f", calculateRetractedPower().values().stream().reduce(0D, Double::sum)));
        this.deployedPower.setText(String.format("%.2f", calculateDeployedPower().values().stream().reduce(0D, Double::sum)));
        this.retractedPowerBar.update(true);
        this.deployedPowerBar.update(false);
    }
}
