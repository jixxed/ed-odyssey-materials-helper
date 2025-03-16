package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsShipSelectedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.ShipBuilderEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

import java.util.Arrays;
import java.util.Optional;

public abstract class Stats extends DestroyableVBox implements DestroyableEventTemplate {


    protected Stats() {
        this.getStyleClass().add("shipbuilder-stats");
        initEventHandlingStats();
    }

    protected static DestroyableLabel createTitle(final String localeKey) {
        return LabelBuilder.builder()
                .withStyleClass("shipbuilder-stats-title")
                .withText(localeKey)
                .build();
    }

    protected static DestroyableLabel createLabel(final String localeKey, final String... values) {
        return LabelBuilder.builder()
                .withStyleClass("shipbuilder-stats-label")
                .withText(localeKey, Arrays.stream(values).toArray())
                .build();
    }

    protected DestroyableLabel createValueLabel(final String key, final String... values) {
        return LabelBuilder.builder()
                .withStyleClass("shipbuilder-stats-value")
                .withText(key, Arrays.stream(values).toArray())
                .build();
    }

    protected static DestroyableLabel createSmallLabel(final String localeKey, final String... values) {
        return LabelBuilder.builder()
                .withStyleClass("shipbuilder-stats-label-small")
                .withText(localeKey, Arrays.stream(values).toArray())
                .build();
    }

    protected DestroyableLabel createValueSmallLabel(final String key, final String... values) {
        return LabelBuilder.builder()
                .withStyleClass("shipbuilder-stats-value-small")
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
