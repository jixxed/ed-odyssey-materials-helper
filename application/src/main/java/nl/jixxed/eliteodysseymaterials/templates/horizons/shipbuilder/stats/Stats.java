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
import java.util.Arrays;
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

    protected static DestroyableLabel createLabel(final String localeKey, final String... values) {
        return LabelBuilder.builder().withStyleClass("shipbuilder-stats-label").withText(LocaleService.getStringBinding(localeKey, Arrays.stream(values).toArray())).build();
    }

    protected DestroyableLabel createValueLabel(final String key, final String... values) {
        return LabelBuilder.builder().withStyleClass("shipbuilder-stats-value").withText(LocaleService.getStringBinding(key, Arrays.stream(values).toArray())).build();
    }
    protected static DestroyableLabel createSmallLabel(final String localeKey, final String... values) {
        return LabelBuilder.builder().withStyleClass("shipbuilder-stats-label-small").withText(LocaleService.getStringBinding(localeKey, Arrays.stream(values).toArray())).build();
    }
    protected DestroyableLabel createValueSmallLabel(final String key, final String... values) {
        return LabelBuilder.builder().withStyleClass("shipbuilder-stats-value-small").withText(LocaleService.getStringBinding(key, Arrays.stream(values).toArray())).build();
    }

    public void initEventHandlingStats() {
        this.eventListeners.add(EventService.addListener(this, ShipBuilderEvent.class, event -> update()));
        this.eventListeners.add(EventService.addListener(this, HorizonsShipSelectedEvent.class, horizonsShipSelectedEvent -> {
            update();
        }));
    }

    public void initEventHandling() {
    }

    public Optional<Ship> getShip() {
        return Optional.ofNullable(ApplicationState.getInstance().getShip());
    }

    protected abstract void update();


    protected double getMassCurveMultiplier(final double mass, final ModuleProfile moduleProfile) {
        return (
                moduleProfile.minimumMultiplier() + Math.pow(
                        Math.min(
                                1.0,
                                (moduleProfile.maximumMass() - mass) / (moduleProfile.maximumMass() - moduleProfile.minimumMass())
                        ),
                        Math.log(
                                (moduleProfile.optimalMultiplier() - moduleProfile.minimumMultiplier()) / (moduleProfile.maximumMultiplier() - moduleProfile.minimumMultiplier())
                        ) / Math.log(
                                (moduleProfile.maximumMass() - moduleProfile.optimalMass()) / (moduleProfile.maximumMass() - moduleProfile.minimumMass())
                        )
                ) * (moduleProfile.maximumMultiplier() - moduleProfile.minimumMultiplier())
        );
    }
}
