package nl.jixxed.eliteodysseymaterials.templates.horizons.menu;

import javafx.css.PseudoClass;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.Location;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBrokerType;
import nl.jixxed.eliteodysseymaterials.enums.TechnologyBroker;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.LocationService;
import nl.jixxed.eliteodysseymaterials.service.TechnologyBrokerService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.LocationChangedEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;
import nl.jixxed.eliteodysseymaterials.templates.generic.CopyableLocation;

import java.util.List;

public class HorizonsNearestBroker extends DestroyableVBox implements DestroyableEventTemplate {

    private final List<HorizonsBrokerType> horizonsBrokerTypes;
    private DestroyableLabel title;
    private CopyableLocation copyableLocation;


    HorizonsNearestBroker(final List<HorizonsBrokerType> horizonsBrokerTypes) {
        this.horizonsBrokerTypes = horizonsBrokerTypes;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("nearest-broker");
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
            final TechnologyBroker closestBroker = TechnologyBrokerService.findClosest(currentLocation.getStarSystem(), this.horizonsBrokerTypes);
            this.title.addBinding(this.title.textProperty(), LocaleService.getStringBinding(closestBroker.getType().getLocalizationKey()));
            //replace system if null or changed
            if (copyableLocation == null || !closestBroker.getStarSystem().equals(copyableLocation.getStarSystem())) {
                this.getNodes().remove(copyableLocation);
                this.copyableLocation = new CopyableLocation(closestBroker.getStarSystem(), closestBroker.getName(), closestBroker.getDistanceFromStar(), closestBroker.getDistanceFromStarVariance());
                this.getNodes().add(copyableLocation);
            }
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass("hidden"), false);

        } catch (final IllegalArgumentException ex) {
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass("hidden"), true);
        }
    }

}
