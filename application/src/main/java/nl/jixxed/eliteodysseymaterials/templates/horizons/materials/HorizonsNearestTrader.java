package nl.jixxed.eliteodysseymaterials.templates.horizons.materials;

import javafx.css.PseudoClass;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.Location;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsStorageType;
import nl.jixxed.eliteodysseymaterials.enums.MaterialTrader;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.LocationService;
import nl.jixxed.eliteodysseymaterials.service.MaterialTraderService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.LocationChangedEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;
import nl.jixxed.eliteodysseymaterials.templates.generic.CopyableLocation;

public class HorizonsNearestTrader extends DestroyableVBox implements DestroyableEventTemplate {

    private final HorizonsStorageType type;
    private DestroyableLabel title;
    private CopyableLocation copyableLocation;


    HorizonsNearestTrader(final HorizonsStorageType type) {
        this.type = type;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("nearest-trader");
        this.title = LabelBuilder.builder()
                .withStyleClass("title")
                .withNonLocalizedText("")
                .build();
        this.getNodes().add(this.title);
        update();
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, LocationChangedEvent.class, _ -> update()));
    }

    private void update() {
        try {
            final Location currentLocation = LocationService.getCurrentLocation();
            final MaterialTrader closestTrader = MaterialTraderService.findClosest(currentLocation.getStarSystem(), this.type);
            this.title.addBinding(this.title.textProperty(), LocaleService.getStringBinding(closestTrader.getType().getLocalizationKey()));
            //replace system if null or changed
            if (copyableLocation == null || !closestTrader.getStarSystem().equals(copyableLocation.getStarSystem())) {
                this.getNodes().remove(copyableLocation);
                this.copyableLocation = new CopyableLocation(closestTrader.getStarSystem(), closestTrader.getName(), closestTrader.getDistanceFromStar(), closestTrader.getDistanceFromStarVariance());
                this.getNodes().add(copyableLocation);
            }
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass("hidden"), false);

        } catch (final IllegalArgumentException ex) {
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass("hidden"), true);
        }
    }
}
