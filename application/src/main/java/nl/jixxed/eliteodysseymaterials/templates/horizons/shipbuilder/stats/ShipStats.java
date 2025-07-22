package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipConfigEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;

public class ShipStats extends Stats implements DestroyableEventTemplate {
    private DestroyableLabel price;
    private DestroyableLabel rebuy;
    private DestroyableLabel mass;
    private DestroyableLabel mlf;
    private DestroyableLabel slf;
    private DestroyableLabel crew;
    private DestroyableLabel manoeuvrability;
    private DestroyableLabel cabinCapacity;

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
        this.mass = createValueLabel("ship.stats.ship.mass.value", Formatters.NUMBER_FORMAT_0.format(0D));
        this.mlf = createValueLabel("ship.stats.ship.mlf.value", Formatters.NUMBER_FORMAT_0.format(0D));
        this.slf = createValueLabel("ship.stats.ship.slf.value", Formatters.NUMBER_FORMAT_0.format(0D));
        this.crew = createValueLabel("ship.stats.ship.crew.value", Formatters.NUMBER_FORMAT_0.format(0D));
        this.manoeuvrability = createValueLabel("ship.stats.ship.manoeuvrability.value", Formatters.NUMBER_FORMAT_0.format(0D));
        this.cabinCapacity = createValueLabel("ship.stats.ship.cabincapacity.value", Formatters.NUMBER_FORMAT_0.format(0D));


        this.getNodes().add(BoxBuilder.builder()
                .withNodes(createLabel("ship.stats.price.price"), new GrowingRegion(), this.price)
                .buildHBox());
        this.getNodes().add(BoxBuilder.builder()
                .withNodes(createLabel("ship.stats.price.rebuy"), new GrowingRegion(), this.rebuy)
                .buildHBox());
        this.getNodes().add(BoxBuilder.builder()
                .withNodes(createLabel("ship.stats.ship.mass"), new GrowingRegion(), this.mass)
                .buildHBox());
        this.getNodes().add(BoxBuilder.builder()
                .withNodes(createLabel("ship.stats.ship.mlf"), new GrowingRegion(), this.mlf)
                .buildHBox());
        this.getNodes().add(BoxBuilder.builder()
                .withNodes(createLabel("ship.stats.ship.slf"), new GrowingRegion(), this.slf)
                .buildHBox());
        this.getNodes().add(BoxBuilder.builder()
                .withNodes(createLabel("ship.stats.ship.crew"), new GrowingRegion(), this.crew)
                .buildHBox());
        this.getNodes().add(BoxBuilder.builder()
                .withNodes(createLabel("ship.stats.ship.manoeuvrability"), new GrowingRegion(), this.manoeuvrability)
                .buildHBox());
        this.getNodes().add(BoxBuilder.builder()
                .withNodes(createLabel("ship.stats.ship.cabincapacity"), new GrowingRegion(), this.cabinCapacity)
                .buildHBox());

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
        this.mass.addBinding(this.mass.textProperty(), LocaleService.getStringBinding("ship.stats.ship.mass.value", Formatters.NUMBER_FORMAT_0.format(calculateMass())));
        this.mlf.addBinding(this.mlf.textProperty(), LocaleService.getStringBinding("ship.stats.ship.mlf.value", Formatters.NUMBER_FORMAT_0.format(calculateMlf())));
        this.slf.addBinding(this.slf.textProperty(), LocaleService.getStringBinding(calculateSlf() ? "ship.view.yes" : "ship.view.no"));
        this.crew.addBinding(this.crew.textProperty(), LocaleService.getStringBinding("ship.stats.ship.crew.value", Formatters.NUMBER_FORMAT_0.format(calculateCrew())));
        this.manoeuvrability.addBinding(this.manoeuvrability.textProperty(), LocaleService.getStringBinding("ship.stats.ship.manoeuvrability.value", Formatters.NUMBER_FORMAT_0.format(calculateManoeuvrability())));
        this.cabinCapacity.addBinding(this.cabinCapacity.textProperty(), LocaleService.getStringBinding("ship.stats.ship.cabincapacity.value", Formatters.NUMBER_FORMAT_0.format(calculateCabinCapacity())));
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
