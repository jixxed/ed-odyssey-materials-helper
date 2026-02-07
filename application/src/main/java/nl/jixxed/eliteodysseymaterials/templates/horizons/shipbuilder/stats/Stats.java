package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.geometry.Orientation;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsShipSelectedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.ShipBuilderEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableSeparator;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

import java.util.Arrays;
import java.util.Optional;

public abstract class Stats extends DestroyableVBox implements DestroyableEventTemplate {


    protected Stats() {
        this.getStyleClass().add("stats");
        initEventHandlingStats();
    }

    protected void addTitle(String titleKey) {
        this.getNodes().add(BoxBuilder.builder()
                .withNodes(new GrowingRegion(), createTitle(titleKey), new GrowingRegion())
                .buildHBox());
        DestroyableSeparator separator = new DestroyableSeparator(Orientation.HORIZONTAL);
        separator.getStyleClass().add("splitter");
        this.getNodes().add(separator);
    }

    protected static DestroyableLabel createTitle(final String localeKey) {
        return LabelBuilder.builder()
                .withStyleClass("title")
                .withText(localeKey)
                .build();
    }

    protected static DestroyableLabel createLabel(final String localeKey, final String... values) {
        return LabelBuilder.builder()
                .withStyleClass("key")
                .withText(localeKey, Arrays.stream(values).toArray())
                .build();
    }

    protected DestroyableLabel createValueLabel(final String key, final String... values) {
        return LabelBuilder.builder()
                .withStyleClass("value")
                .withText(key, Arrays.stream(values).toArray())
                .build();
    }

    protected static DestroyableLabel createSmallLabel(final String localeKey, final String... values) {
        return LabelBuilder.builder()
                .withStyleClass("key-small")
                .withText(localeKey, Arrays.stream(values).toArray())
                .build();
    }

    protected DestroyableLabel createValueSmallLabel(final String key, final String... values) {
        return LabelBuilder.builder()
                .withStyleClass("value-small")
                .withText(key, Arrays.stream(values).toArray())
                .build();
    }

    public void initEventHandlingStats() {
        register(EventService.addListener(true, this, ShipBuilderEvent.class, _ -> update()));
        register(EventService.addListener(true, this, HorizonsShipSelectedEvent.class, _ ->
                update()));
    }

    public Optional<Ship> getShip() {
        return Optional.ofNullable(ApplicationState.getInstance().getShip());
    }

    protected abstract void update();

}
