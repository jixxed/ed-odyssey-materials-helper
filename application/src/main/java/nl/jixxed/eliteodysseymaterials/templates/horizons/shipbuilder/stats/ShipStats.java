package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.util.Duration;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TooltipBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipConfigEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;

public class ShipStats extends Stats implements DestroyableEventTemplate {
    private DestroyableLabel price;
    private DestroyableLabel rebuy;
    private DestroyableLabel mass;
    private DestroyableLabel mlf;
    private DestroyableLabel slf;
    private DestroyableLabel crew;
    private DestroyableLabel boostCost;
    private DestroyableLabel manoeuvrability;
    private DestroyableLabel cabinCapacity;
    private DestroyableLabel sensorRange;
    private DestroyableLabel effectiveSensorRange;

    public ShipStats() {
        super();
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("ship-stats");
        addTitle("ship.stats.ship");
        this.price = createValueLabel("ship.stats.price.price.value", Formatters.NUMBER_FORMAT_0.format(0D));
        this.rebuy = createValueLabel("ship.stats.price.rebuy.value", Formatters.NUMBER_FORMAT_0.format(0D));
        this.mass = createValueLabel("ship.stats.ship.mass.value", Formatters.NUMBER_FORMAT_1.format(0D));
        this.mlf = createValueLabel("ship.stats.ship.mlf.value", Formatters.NUMBER_FORMAT_0.format(0D));
        this.slf = createValueLabel("ship.stats.ship.slf.value", Formatters.NUMBER_FORMAT_0.format(0D));
        this.crew = createValueLabel("ship.stats.ship.crew.value", Formatters.NUMBER_FORMAT_0.format(0D));
        this.boostCost = createValueLabel("ship.stats.ship.boostcost.value", Formatters.NUMBER_FORMAT_0.format(0D));
        this.manoeuvrability = createValueLabel("ship.stats.ship.manoeuvrability.value", Formatters.NUMBER_FORMAT_0.format(0D));
        this.cabinCapacity = createValueLabel("ship.stats.ship.cabincapacity.value", Formatters.NUMBER_FORMAT_0.format(0D));
        this.sensorRange = createValueLabel("ship.stats.ship.sensorrange.value", Formatters.NUMBER_FORMAT_0.format(0D), Formatters.NUMBER_FORMAT_0.format(0D));
        this.effectiveSensorRange = createValueLabel("ship.stats.ship.effectivesensorrange.value", Formatters.NUMBER_FORMAT_0.format(0D), Formatters.NUMBER_FORMAT_0.format(0D));

        this.getNodes().add(BoxBuilder.builder()
                .withStyleClass("stat-line")
                .withNodes(createLabel("ship.stats.price.price"), new GrowingRegion(), this.price)
                .buildHBox());
        this.getNodes().add(BoxBuilder.builder()
                .withStyleClass("stat-line")
                .withNodes(createLabel("ship.stats.price.rebuy"), new GrowingRegion(), this.rebuy)
                .buildHBox());
        this.getNodes().add(BoxBuilder.builder()
                .withStyleClass("stat-line")
                .withNodes(createLabel("ship.stats.ship.mass"), new GrowingRegion(), this.mass)
                .buildHBox());
        this.getNodes().add(BoxBuilder.builder()
                .withStyleClass("stat-line")
                .withNodes(createLabel("ship.stats.ship.mlf"), new GrowingRegion(), this.mlf)
                .buildHBox());
        this.getNodes().add(BoxBuilder.builder()
                .withStyleClass("stat-line")
                .withNodes(createLabel("ship.stats.ship.slf"), new GrowingRegion(), this.slf)
                .buildHBox());
        this.getNodes().add(BoxBuilder.builder()
                .withStyleClass("stat-line")
                .withNodes(createLabel("ship.stats.ship.crew"), new GrowingRegion(), this.crew)
                .buildHBox());
        this.getNodes().add(BoxBuilder.builder()
                .withStyleClass("stat-line")
                .withNodes(createLabel("ship.stats.ship.boostcost"), new GrowingRegion(), this.boostCost)
                .buildHBox());
        this.getNodes().add(BoxBuilder.builder()
                .withStyleClass("stat-line")
                .withNodes(createLabel("ship.stats.ship.manoeuvrability"), new GrowingRegion(), this.manoeuvrability)
                .buildHBox());
        this.getNodes().add(BoxBuilder.builder()
                .withStyleClass("stat-line")
                .withNodes(createLabel("ship.stats.ship.cabincapacity"), new GrowingRegion(), this.cabinCapacity)
                .buildHBox());
        DestroyableHBox sensorRangeLine = BoxBuilder.builder()
                .withStyleClass("stat-line")
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
                .withStyleClass("stat-line")
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

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, ShipConfigEvent.class, event -> update()));
    }

//    private void createEntry(String titleKey, Ship ship, HorizonsModifier modifier) {
//        flow.getNodes().add(BoxBuilder.builder()
//                .withNodes(title(titleKey), value(modifier.format(ship.getAttributes().get(modifier))))
//                .buildVBox());
//
//    }

//    private DestroyableLabel title(String titleKey) {
//        return LabelBuilder.builder()
//                .withStyleClass("title")
//                .withText(titleKey)
//                .build();
//    }
//
//    private DestroyableLabel value(String text) {
//        return LabelBuilder.builder()
//                .withStyleClass("value")
//                .withNonLocalizedText(text)
//                .build();
//    }


    @Override
    protected void update() {
        this.price.addBinding(this.price.textProperty(), LocaleService.getStringBinding("ship.stats.price.price.value", Formatters.NUMBER_FORMAT_0.format(calculatePrice())));
        this.rebuy.addBinding(this.rebuy.textProperty(), LocaleService.getStringBinding("ship.stats.price.rebuy.value", Formatters.NUMBER_FORMAT_0.format(calculateRebuy())));
        this.mass.addBinding(this.mass.textProperty(), LocaleService.getStringBinding("ship.stats.ship.mass.value", Formatters.NUMBER_FORMAT_1.format(calculateMass())));
        this.mlf.addBinding(this.mlf.textProperty(), LocaleService.getStringBinding("ship.stats.ship.mlf.value", Formatters.NUMBER_FORMAT_0.format(calculateMlf())));
        this.slf.addBinding(this.slf.textProperty(), LocaleService.getStringBinding(calculateSlf() ? "ship.view.yes" : "ship.view.no"));
        this.crew.addBinding(this.crew.textProperty(), LocaleService.getStringBinding("ship.stats.ship.crew.value", Formatters.NUMBER_FORMAT_0.format(calculateCrew())));
        this.boostCost.addBinding(this.boostCost.textProperty(), LocaleService.getStringBinding("ship.stats.ship.boostcost.value", Formatters.NUMBER_FORMAT_0.format(calculateBoostCost())));
        this.manoeuvrability.addBinding(this.manoeuvrability.textProperty(), LocaleService.getStringBinding("ship.stats.ship.manoeuvrability.value", Formatters.NUMBER_FORMAT_0.format(calculateManoeuvrability())));
        this.cabinCapacity.addBinding(this.cabinCapacity.textProperty(), LocaleService.getStringBinding("ship.stats.ship.cabincapacity.value", Formatters.NUMBER_FORMAT_0.format(calculateCabinCapacity())));
        this.sensorRange.addBinding(this.sensorRange.textProperty(), LocaleService.getStringBinding("ship.stats.ship.sensorrange.value", Formatters.NUMBER_FORMAT_0.format(calculateSensorRangeMin()), Formatters.NUMBER_FORMAT_0.format(calculateSensorRangeMax())));
        this.effectiveSensorRange.addBinding(this.effectiveSensorRange.textProperty(), LocaleService.getStringBinding("ship.stats.ship.effectivesensorrange.value", Formatters.NUMBER_FORMAT_0.format(calculateEffectiveSensorRangeMin()), Formatters.NUMBER_FORMAT_0.format(calculateEffectiveSensorRangeMax())));
    }

    private double calculateBoostCost() {
        return (double) getShip().map(ship -> ship.getAttributes().get(HorizonsModifier.BOOST_COST)).orElse(Double.NaN);
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

    private PowerProfile calculateRetractedPower() {
        return getShip().map(Ship::getRetractedPower).orElseGet(PowerProfile::new);
    }

    private double calculateCabinCapacity() {
        return getShip().map(Ship::getMaxPassenger).orElse(0D);
    }

    private long calculateRebuy() {
        return getShip().map(Ship::getRebuy).orElse(0L);
    }

    private long calculatePrice() {
        return getShip().map(Ship::getTotalPrice).orElse(0L);
    }

    private double calculateMass() {
        return getShip().map(ship -> ship.getEmptyMass() + ship.getCurrentCargo() + ship.getCurrentFuel() + ship.getCurrentFuelReserve()).orElse(0D);
    }

    private double calculateMlf() {
        return getShip().map(ship -> (double) ship.getAttributes().get(HorizonsModifier.MASS_LOCK)).orElse(0D);
    }

    private boolean calculateSlf() {
        return getShip().map(Ship::getShipType).map(ShipType::isFighterBay).orElse(Boolean.FALSE);
    }

    private int calculateCrew() {
        return getShip().map(Ship::getShipType).map(ShipType::getMultiCrewSeats).orElse(0);
    }

    private double calculateManoeuvrability() {
        return getShip().map(ship -> (double) ship.getAttributes().get(HorizonsModifier.MANOEUVRABILITY)).orElse(0D);
    }
}
