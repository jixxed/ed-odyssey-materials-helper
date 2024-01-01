package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsShipSelectedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.ShipBuilderEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Stats extends VBox {
    protected final List<EventListener<?>> eventListeners = new ArrayList<>();

    protected Stats() {
        this.getStyleClass().add("shipbuilder-stats");
        initEventHandlingStats();
    }

    protected static DestroyableLabel createTitle(final String localeKey) {
        return LabelBuilder.builder().withStyleClass("shipbuilder-stats-title").withText(LocaleService.getStringBinding(localeKey)).build();
    }

    protected static DestroyableLabel createLabel(final String localeKey) {
        return LabelBuilder.builder().withStyleClass("shipbuilder-stats-label").withText(LocaleService.getStringBinding(localeKey)).build();
    }

    protected DestroyableLabel createValueLabel(final String text) {
        return LabelBuilder.builder().withStyleClass("shipbuilder-stats-value").withNonLocalizedText(text).build();
    }

    public void initEventHandlingStats() {
        this.eventListeners.add(EventService.addListener(this, ShipBuilderEvent.class, event -> update()));
        this.eventListeners.add(EventService.addListener(this, HorizonsShipSelectedEvent.class, horizonsShipSelectedEvent -> {
//            ApplicationState.getInstance().getPreferredCommander()
//                    .flatMap(commander -> ShipService.getShipConfigurations(commander).getSelectedShipConfiguration())
//                    .ifPresent(configuration -> this.ship = Optional.ofNullable(ShipMapper.toShip(configuration)));
            update();
        }));
    }

    public void initEventHandling() {
    }

    public Optional<Ship> getShip() {
        return Optional.ofNullable(ApplicationState.getInstance().getShip());
    }

    protected abstract void update();
}
