package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.beans.binding.StringBinding;
import javafx.util.Duration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TooltipBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.domain.ships.Slot;
import nl.jixxed.eliteodysseymaterials.domain.ships.SlotType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipConfigEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class PowerThermalsStats extends Stats implements DestroyableEventTemplate {
    private DestroyableLabel retractedPowerLabel;
    private DestroyableLabel deployedPowerLabel;
    private PowerBar retractedPowerBar;
    private PowerBar deployedPowerBar;

    private DestroyableLabel idleThermals;
    private DestroyableLabel thrusterThermals;
    private DestroyableLabel fsdThermals;
    private DestroyableLabel silentRunTime;

    public PowerThermalsStats() {
        super();
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("power-thermal-stats");
        addTitle("ship.stats.thermalpower");

        this.idleThermals = createValueLabel("ship.stats.thermal.idle.thermals.value", Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(0D));
        this.thrusterThermals = createValueLabel("ship.stats.thermal.thruster.thermals.value", Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(0D));
        this.fsdThermals = createValueLabel("ship.stats.thermal.fsd.thermals.value", Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(0D));
        this.silentRunTime = createValueLabel("ship.stats.thermal.thruster.silentruntime.value", Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(0D));


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

        this.getNodes().addAll(idleLine, thrusterLine, fsdLine, silentRunningLine);


        this.getNodes().add(new GrowingRegion());
        this.retractedPowerLabel = createValueLabel("ship.stats.power.retracted.power.value", Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(0D), Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(0D));
        this.deployedPowerLabel = createValueLabel("ship.stats.power.deployed.power.value", Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(0D), Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(0D));
        this.retractedPowerBar = new PowerBar(true);
        this.deployedPowerBar = new PowerBar(false);

        this.getNodes().add(BoxBuilder.builder()
                .withNodes(createLabel("ship.stats.power.retracted.power"), new GrowingRegion(), this.retractedPowerLabel)
                .buildHBox());
        this.getNodes().add(BoxBuilder.builder()
                .withStyleClass("power-stats-powerbars")
                .withNodes(retractedPowerBar, deployedPowerBar).buildVBox());
        this.getNodes().add(BoxBuilder.builder()
                .withNodes(createLabel("ship.stats.power.deployed.power"), new GrowingRegion(), this.deployedPowerLabel)
                .buildHBox());
        this.getNodes().add(BoxBuilder.builder()
                .withNodes(
                        createLegend("P"),
                        new GrowingRegion(),
                        createLegend("1"),
                        new GrowingRegion(),
                        createLegend("2"),
                        new GrowingRegion(),
                        createLegend("3"),
                        new GrowingRegion(),
                        createLegend("4"),
                        new GrowingRegion(),
                        createLegend("5")
                ).buildHBox());

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
    }


    @Override
    protected void update() {
        final Map<Integer, Double> retractedPower = calculateRetractedPower();
        final Double retractedUsage = retractedPower.entrySet().stream().filter(entry -> !entry.getKey().equals(0)).mapToDouble(Map.Entry::getValue).reduce(0D, Double::sum);
        final Double powerBudget = retractedPower.get(0);
        final Double deployedUsage = calculateDeployedPower().entrySet().stream().filter(entry -> !entry.getKey().equals(0)).mapToDouble(Map.Entry::getValue).reduce(0D, Double::sum);
        this.retractedPowerLabel.addBinding(this.retractedPowerLabel.textProperty(), LocaleService.getStringBinding("ship.stats.power.retracted.power.value", Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(retractedUsage), Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(powerBudget)));
        this.deployedPowerLabel.addBinding(this.deployedPowerLabel.textProperty(), LocaleService.getStringBinding("ship.stats.power.deployed.power.value", Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(deployedUsage), Formatters.NUMBER_FORMAT_2_DUAL_DECIMAL.format(powerBudget)));
        if (retractedUsage > powerBudget) {
            if (!this.retractedPowerLabel.getStyleClass().contains("power-stats-overpower"))
                this.retractedPowerLabel.getStyleClass().add("power-stats-overpower");
        } else {
            this.retractedPowerLabel.getStyleClass().removeAll("power-stats-overpower");
        }
        if (deployedUsage > powerBudget) {
            if (!this.deployedPowerLabel.getStyleClass().contains("power-stats-overpower"))
                this.deployedPowerLabel.getStyleClass().add("power-stats-overpower");
        } else {
            this.deployedPowerLabel.getStyleClass().removeAll("power-stats-overpower");
        }
        this.retractedPowerBar.update();
        this.deployedPowerBar.update();

        this.idleThermals.addBinding(this.idleThermals.textProperty(), calculateIdleThermals().format());
        this.thrusterThermals.addBinding(this.thrusterThermals.textProperty(), calculateThrusterThermals().format());
        this.fsdThermals.addBinding(this.fsdThermals.textProperty(), calculateFsdThermals().format());
        this.silentRunTime.addBinding(this.silentRunTime.textProperty(), calculateSilentRunTime().format());
    }

    private Value calculateSilentRunTime() {
        return getShip().map(ship -> {
            final double heatCapacity = (double) ship.getAttributes().getOrDefault(HorizonsModifier.HEAT_CAPACITY, 0d);
            final double maximumHeatDissipation = (double) ship.getAttributes().getOrDefault(HorizonsModifier.HEAT_DISSIPATION_MAX, 0d);
            final double powerCapacity = (double) ship.getCoreSlots().stream()
                    .filter(slot -> SlotType.CORE_POWER_PLANT.equals(slot.getSlotType()))
                    .filter(Slot::isOccupied)
                    .filter(slot -> slot.getShipModule().isPowered())
                    .findFirst()
                    .map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.POWER_CAPACITY))
                    .orElse(Double.NaN);
            final double heatEfficiency = (double) ship.getCoreSlots().stream()
                    .filter(slot -> SlotType.CORE_POWER_PLANT.equals(slot.getSlotType()))
                    .filter(Slot::isOccupied)
                    .filter(slot -> slot.getShipModule().isPowered())
                    .findFirst()
                    .map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.HEAT_EFFICIENCY))
                    .orElse(Double.NaN);
            final double engineHeat = (double) ship.getCoreSlots().stream()
                    .filter(slot -> SlotType.CORE_THRUSTERS.equals(slot.getSlotType()))
                    .filter(Slot::isOccupied)
                    .filter(slot -> slot.getShipModule().isPowered())
                    .findFirst()
                    .map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.ENGINE_THERMAL_LOAD))
                    .orElse(Double.NaN);

            final double powerForHeat = getPowerForHeat(powerCapacity, heatEfficiency);

            PowerThermalsStats.Value heatLevelForDuration = getHeatLevelForDuration(engineHeat, powerForHeat, maximumHeatDissipation, heatCapacity, 60);
            return new Value((heatCapacity - heatCapacity * heatLevelForDuration.getValue() / 100) / (powerForHeat + engineHeat), Value.ValueType.SECONDS);// expect 25s
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

    Value getHeatLevelForDuration(double thermalLoad, double baseThermalLoad, double maximumHeatDissipation, double heatCapacity, double duration) {
        if (thermalLoad > 0) {
            thermalLoad += baseThermalLoad;
            if (baseThermalLoad > maximumHeatDissipation) {
                return new Value(Double.NaN, Value.ValueType.ERROR);//error
            } else if (thermalLoad > maximumHeatDissipation) {
                var baseHeatLevel = getEquilibriumHeatLevel(maximumHeatDissipation, baseThermalLoad);
                var time10 = getTimeUntilHeatLevel(heatCapacity, maximumHeatDissipation, thermalLoad, baseHeatLevel, 1.0);
                if ((time10 > duration)) {
                    return new Value(getHeatLevelAtTime(heatCapacity, maximumHeatDissipation, thermalLoad, baseHeatLevel, duration) / 1.5 * 100, Value.ValueType.PERCENTAGE);
                } else {
                    var time15 = (heatCapacity / 2) / (thermalLoad - maximumHeatDissipation); // displayed heatlevel 66% -> 100% is actual heatlevel 1.0 -> 1.5
                    if ((time10 + time15) > duration) {
                        return new Value((2 + ((duration - time10) / time15)) / 3 * 100, Value.ValueType.SECONDS);
                    } else {
                        duration -= time10 + time15;
                        var peakHeatLevel = 1.5 + (duration * (thermalLoad - maximumHeatDissipation) / heatCapacity);
                        var timeToCool = (peakHeatLevel - 1.5) / ((maximumHeatDissipation - baseThermalLoad) / heatCapacity);
                    }
                }
            } else {
                return new Value(getEquilibriumHeatLevel(maximumHeatDissipation, thermalLoad) / 1.5 * 100, Value.ValueType.PERCENTAGE);
            }
        }
        return new Value(Double.NaN, Value.ValueType.ERROR);
    }

    double getTimeUntilHeatLevel(double heatCapacity, double maximumHeatDissipation, double thermalLoad, double heatLevelAtStart, double heatLevel) {
        // https://forums.frontier.co.uk/threads/research-detailed-heat-mechanics.286628/post-6519883
        maximumHeatDissipation /= heatCapacity;
        if (thermalLoad == 0D) {
            var C = -1 / (maximumHeatDissipation * heatLevelAtStart);
            return ((1 / (maximumHeatDissipation * heatLevel)) + C);
        } else if (thermalLoad < 0D) {
            thermalLoad /= heatCapacity;
            var sqrtAdivB = Math.sqrt(-maximumHeatDissipation / thermalLoad);
            var sqrtAmulB = Math.sqrt(-maximumHeatDissipation * thermalLoad);

            var c = 1 / sqrtAmulB * Math.atan(sqrtAdivB * heatLevelAtStart);

            return -1 / sqrtAmulB * Math.atan(sqrtAdivB * heatLevel) + c;

        }
        thermalLoad /= heatCapacity;
        var sqrtAdivB = Math.sqrt(maximumHeatDissipation / thermalLoad);
        var sqrtAmulB = Math.sqrt(maximumHeatDissipation * thermalLoad);
        var cReal = 1D / sqrtAmulB * 1D / 2D * (Math.log(1D + sqrtAdivB * heatLevelAtStart) - Math.log(Math.abs(1D - sqrtAdivB * heatLevelAtStart)));
        return 1D / sqrtAmulB * 1D / 2D * (Math.log(1D + sqrtAdivB * heatLevel) - Math.log(Math.abs(1D - sqrtAdivB * heatLevel))) - cReal;
    }


    double getHeatLevelAtTime(double heatCapacity, double maximumHeatDissipation, double thermalLoad, double heatLevelAtStart, double seconds) {
        maximumHeatDissipation /= heatCapacity;
        if (thermalLoad == 0D) {
            var C = -1 / (maximumHeatDissipation * heatLevelAtStart);
            return ((1 / (seconds - C)) / maximumHeatDissipation);
        } else if (thermalLoad < 0) {
            thermalLoad /= heatCapacity;
            var sqrtAdivB = Math.sqrt(-maximumHeatDissipation / thermalLoad);
            var sqrtAmulB = Math.sqrt(-maximumHeatDissipation * thermalLoad);

            var c = 1 / sqrtAmulB * Math.atan(sqrtAdivB * heatLevelAtStart);
            return 1 / sqrtAdivB * Math.tan(sqrtAmulB * (-seconds + c));

        }
        thermalLoad /= heatCapacity;
        var sqrtAdivB = Math.sqrt(maximumHeatDissipation / thermalLoad);
        var sqrtAmulB = Math.sqrt(maximumHeatDissipation * thermalLoad);
        var cReal = 1 / sqrtAmulB * 1 / 2 * (Math.log(1 + sqrtAdivB * heatLevelAtStart) - Math.log(Math.abs(1 - sqrtAdivB * heatLevelAtStart)));
        var cImag = 1 / sqrtAmulB * 1 / 2 * (-Math.atan2(0, 1 - sqrtAdivB * heatLevelAtStart));
        return Math.sqrt(thermalLoad / maximumHeatDissipation) * (Math.exp(4 * sqrtAmulB * (seconds + cReal)) - 1) / (Math.exp(4 * sqrtAmulB * (seconds + cReal)) + 2 * Math.exp(2 * sqrtAmulB * (seconds + cReal)) * Math.cos(2 * sqrtAmulB * cImag) + 1);
    }

    double atanh(double x) {
        return (Math.log((1 + x) / (1 - x)) / 2);
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
            final double powerCapacity = (double) ship.getCoreSlots().stream()
                    .filter(slot -> SlotType.CORE_POWER_PLANT.equals(slot.getSlotType()))
                    .filter(Slot::isOccupied)
                    .filter(slot -> slot.getShipModule().isPowered())
                    .findFirst()
                    .map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.POWER_CAPACITY))
                    .orElse(Double.NaN);
            final double heatEfficiency = (double) ship.getCoreSlots().stream()
                    .filter(slot -> SlotType.CORE_POWER_PLANT.equals(slot.getSlotType()))
                    .filter(Slot::isOccupied)
                    .filter(slot -> slot.getShipModule().isPowered())
                    .findFirst()
                    .map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.HEAT_EFFICIENCY))
                    .orElse(Double.NaN);

            final double powerForHeat = getPowerForHeat(powerCapacity, heatEfficiency);

            return getHeatLevelForDuration(powerForHeat, 0, maximumHeatDissipation, heatCapacity, 60);
        }).orElse(new Value(0D, Value.ValueType.PERCENTAGE));
    }

    private double getPowerForHeat(double powerCapacity, double heatEfficiency) {
        final Map<Integer, Double> powerPerGroup = calculateRetractedPowerThermal();
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
                    .filter(Slot::isOccupied)
                    .filter(slot -> slot.getShipModule().isPowered())
                    .findFirst()
                    .map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.POWER_CAPACITY))
                    .orElse(Double.NaN);
            final double heatEfficiency = (double) ship.getCoreSlots().stream()
                    .filter(slot -> SlotType.CORE_POWER_PLANT.equals(slot.getSlotType()))
                    .filter(Slot::isOccupied)
                    .filter(slot -> slot.getShipModule().isPowered())
                    .findFirst()
                    .map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.HEAT_EFFICIENCY))
                    .orElse(Double.NaN);
            final double engineHeat = (double) ship.getCoreSlots().stream()
                    .filter(slot -> SlotType.CORE_THRUSTERS.equals(slot.getSlotType()))
                    .filter(Slot::isOccupied)
                    .filter(slot -> slot.getShipModule().isPowered())
                    .findFirst()
                    .map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.ENGINE_THERMAL_LOAD))
                    .orElse(Double.NaN);

            final double powerForHeat = getPowerForHeat(powerCapacity, heatEfficiency);

            return getHeatLevelForDuration(engineHeat, powerForHeat, maximumHeatDissipation, heatCapacity, 60);
        }).orElse(new Value(0D, Value.ValueType.PERCENTAGE));
    }

    private Value calculateFsdThermals() {
        return getShip().map(ship -> {
            final double heatCapacity = (double) ship.getAttributes().getOrDefault(HorizonsModifier.HEAT_CAPACITY, 0d);
            final double maximumHeatDissipation = (double) ship.getAttributes().getOrDefault(HorizonsModifier.HEAT_DISSIPATION_MAX, 0d);
            final double powerCapacity = (double) ship.getCoreSlots().stream()
                    .filter(slot -> SlotType.CORE_POWER_PLANT.equals(slot.getSlotType()))
                    .filter(Slot::isOccupied)
                    .filter(slot -> slot.getShipModule().isPowered())
                    .findFirst()
                    .map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.POWER_CAPACITY))
                    .orElse(Double.NaN);
            final double heatEfficiency = (double) ship.getCoreSlots().stream()
                    .filter(slot -> SlotType.CORE_POWER_PLANT.equals(slot.getSlotType()))
                    .filter(Slot::isOccupied)
                    .filter(slot -> slot.getShipModule().isPowered())
                    .findFirst()
                    .map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.HEAT_EFFICIENCY))
                    .orElse(Double.NaN);
            final double engineHeat = (double) ship.getCoreSlots().stream()
                    .filter(slot -> SlotType.CORE_THRUSTERS.equals(slot.getSlotType()))
                    .filter(Slot::isOccupied)
                    .filter(slot -> slot.getShipModule().isPowered())
                    .findFirst()
                    .map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.ENGINE_THERMAL_LOAD))
                    .orElse(Double.NaN);
            final double fsdHeat = (double) ship.getCoreSlots().stream()
                    .filter(slot -> SlotType.CORE_FRAME_SHIFT_DRIVE.equals(slot.getSlotType()))
                    .filter(Slot::isOccupied)
                    .filter(slot -> slot.getShipModule().isPowered())
                    .findFirst()
                    .map(slot -> slot.getShipModule().getAttributeValue(HorizonsModifier.FSD_HEAT_RATE))
                    .orElse(Double.NaN);

            final double powerForHeat = getPowerForHeat(powerCapacity, heatEfficiency);

            return getHeatLevelForDuration(fsdHeat, powerForHeat + engineHeat, maximumHeatDissipation, heatCapacity, 60);
        }).orElse(new Value(0D, Value.ValueType.PERCENTAGE));
    }

    private Map<Integer, Double> calculateRetractedPower() {
        return getShip().map(Ship::getRetractedPower).orElseGet(() -> new HashMap<>(Map.of(
                -1, 0D,
                0, 0D,
                1, 0D,
                2, 0D,
                3, 0D,
                4, 0D,
                5, 0D
        )));
    }

    private Map<Integer, Double> calculateDeployedPower() {
        return getShip().map(Ship::getDeployedPower).orElseGet(() -> new HashMap<>(Map.of(
                -1, 0D,
                0, 0D,
                1, 0D,
                2, 0D,
                3, 0D,
                4, 0D,
                5, 0D
        )));
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
                powerValues.compute(ship.getCargoHatch().getShipModule().isPassivePowerWithoutToggle() ? -1 : ship.getCargoHatch().getShipModule().getPowerGroup(), (key, value) -> value + (double) ship.getCargoHatch().getShipModule().getAttributeValue(HorizonsModifier.POWER_DRAW));
            }
            ship.getUtilitySlots().stream()
                    .filter(Slot::isOccupied)
                    .filter(slot -> slot.getShipModule().isPassivePower())
                    .filter(slot -> slot.getShipModule().isPowered())
                    .forEach(slot -> powerValues.compute(slot.getShipModule().isPassivePowerWithoutToggle() ? -1 : slot.getShipModule().getPowerGroup(), (key, value) -> value + (double) slot.getShipModule().getAttributeValue(HorizonsModifier.POWER_DRAW)));
            ship.getOptionalSlots().stream()
                    .filter(Slot::isOccupied)
                    .filter(slot -> slot.getShipModule().isPowered())
                    .forEach(slot -> powerValues.compute(slot.getShipModule().isPassivePowerWithoutToggle() ? -1 : slot.getShipModule().getPowerGroup(), (key, value) -> value + (double) slot.getShipModule().getAttributeValue(HorizonsModifier.POWER_DRAW)));
            ship.getCoreSlots().stream()
                    .filter(Slot::isOccupied)
                    .filter(slot -> slot.getShipModule().isPowered())
                    .forEach(slot -> powerValues.compute(slot.getShipModule().isPassivePowerWithoutToggle() ? -1 : slot.getShipModule().getPowerGroup(), (key, value) -> value + (double) slot.getShipModule().getAttributeValue(HorizonsModifier.POWER_DRAW)));
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
                            (key, value) -> value + (double) slot.getShipModule().getAttributeValue(HorizonsModifier.POWER_DRAW)
                    ));
            ship.getUtilitySlots().stream()
                    .filter(Slot::isOccupied)
                    .filter(slot -> !slot.getShipModule().isPassivePower())
                    .forEach(slot -> powerValues.compute(
                            slot.getShipModule().isPassivePowerWithoutToggle() ? -1 : slot.getShipModule().getPowerGroup(),
                            (key, value) -> value + (double) slot.getShipModule().getAttributeValue(HorizonsModifier.POWER_DRAW)
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
