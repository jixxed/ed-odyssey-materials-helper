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

import javafx.geometry.Orientation;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ShipConfigEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableSeparator;

@Slf4j
public class PriceStats extends Stats implements DestroyableEventTemplate {
    private DestroyableLabel price;
    private DestroyableLabel rebuy;

    public PriceStats() {
        super();
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("price-stats");
        this.getNodes().add(BoxBuilder.builder()
                .withNodes(new GrowingRegion(), createTitle("ship.stats.price"), new GrowingRegion())
                .buildHBox());
        this.getNodes().add(new DestroyableSeparator(Orientation.HORIZONTAL));
        this.price = createValueLabel("ship.stats.price.price.value", Formatters.NUMBER_FORMAT_0.format(0D));
        this.rebuy = createValueLabel("ship.stats.price.rebuy.value", Formatters.NUMBER_FORMAT_0.format(0D));

        this.getNodes().add(BoxBuilder.builder()
                .withNodes(createLabel("ship.stats.price.price"), new GrowingRegion(), this.price)
                .buildHBox());
        this.getNodes().add(BoxBuilder.builder()
                .withNodes(createLabel("ship.stats.price.rebuy"), new GrowingRegion(), this.rebuy)
                .buildHBox());

    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, ShipConfigEvent.class, event -> update()));
    }


    @Override
    protected void update() {
        this.price.addBinding(this.price.textProperty(), LocaleService.getStringBinding("ship.stats.price.price.value", Formatters.NUMBER_FORMAT_0.format(calculatePrice())));
        this.rebuy.addBinding(this.rebuy.textProperty(), LocaleService.getStringBinding("ship.stats.price.rebuy.value", Formatters.NUMBER_FORMAT_0.format(calculateRebuy())));
    }

    private long calculateRebuy() {
        return getShip().map(Ship::getRebuy).orElse(0L);
    }

    private long calculatePrice() {
        return getShip().map(Ship::getTotalPrice).orElse(0L);
    }
}
