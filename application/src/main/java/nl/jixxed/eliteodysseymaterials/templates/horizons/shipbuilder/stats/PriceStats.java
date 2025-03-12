package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipConfigEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

@Slf4j
public class PriceStats extends Stats implements DestroyableTemplate {
    private DestroyableLabel price;
    private DestroyableLabel rebuy;

    public PriceStats() {
        super();
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getChildren().add(BoxBuilder.builder().withNodes(new GrowingRegion(), createTitle("ship.stats.price"), new GrowingRegion()).buildHBox());
        this.getChildren().add(new Separator(Orientation.HORIZONTAL));
        this.price = createValueSmallLabel("ship.stats.price.price.value", Formatters.NUMBER_FORMAT_0.format(0D));
        this.rebuy = createValueSmallLabel("ship.stats.price.rebuy.value", Formatters.NUMBER_FORMAT_0.format(0D));

        this.getChildren().add(BoxBuilder.builder().withNodes(createSmallLabel("ship.stats.price.price"), new GrowingRegion(), this.price).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createSmallLabel("ship.stats.price.rebuy"), new GrowingRegion(), this.rebuy).buildHBox());

    }

    @Override
    public void initEventHandling() {
        eventListeners.add(EventService.addListener(true, this, ShipConfigEvent.class, event -> update()));
    }


    @Override
    protected void update() {
        this.price.textProperty().bind(LocaleService.getStringBinding("ship.stats.price.price.value", Formatters.NUMBER_FORMAT_0.format(calculatePrice())));
        this.rebuy.textProperty().bind(LocaleService.getStringBinding("ship.stats.price.rebuy.value", Formatters.NUMBER_FORMAT_0.format(calculateRebuy())));
    }

    private long calculateRebuy() {
        return getShip().map(Ship::getRebuy).orElse(0L);
    }

    private long calculatePrice() {
        return getShip().map(Ship::getTotalPrice).orElse(0L);
    }
}
