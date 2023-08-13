package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.scene.layout.VBox;
import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;

import java.util.ArrayList;
import java.util.List;
public class Stats extends VBox {
    @Setter
    protected Ship ship;
    protected final List<EventListener<?>> eventListeners = new ArrayList<>();

    public Stats(final Ship ship) {
        this.ship = ship;
        this.getStyleClass().add("shipbuilder-stats");
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
}
