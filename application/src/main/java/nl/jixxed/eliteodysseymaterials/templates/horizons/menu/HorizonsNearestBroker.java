package nl.jixxed.eliteodysseymaterials.templates.horizons.menu;

import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.builder.StackPaneBuilder;
import nl.jixxed.eliteodysseymaterials.domain.Location;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBrokerType;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.enums.TechnologyBroker;
import nl.jixxed.eliteodysseymaterials.helper.ClipboardHelper;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.LocationService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.TechnologyBrokerService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.LocationChangedEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.util.List;

public class HorizonsNearestBroker extends DestroyableVBox implements DestroyableEventTemplate {

    private final List<HorizonsBrokerType> horizonsBrokerTypes;
    private DestroyableLabel location;
    private DestroyableLabel title;
    private DestroyableLabel distance;
    private String system = "";


    HorizonsNearestBroker(final List<HorizonsBrokerType> horizonsBrokerTypes) {
        this.horizonsBrokerTypes = horizonsBrokerTypes;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("nearest-broker");
        this.title = LabelBuilder.builder()
                .withStyleClass("nearest-broker-title")
                .withNonLocalizedText("")
                .build();
        this.getNodes().add(this.title);
        this.getNodes().add(getTraderLocation());
        update();
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, LocationChangedEvent.class, locationEvent -> {
            update();
        }));
    }

    private void update() {
        try {
            final Location currentLocation = LocationService.getCurrentLocation();
            final TechnologyBroker closest = TechnologyBrokerService.findClosest(currentLocation.getStarSystem(), this.horizonsBrokerTypes);
            this.title.addBinding(this.title.textProperty(), LocaleService.getStringBinding(closest.getType().getLocalizationKey()));
            this.location.setText(closest.getName() + " | " + closest.getStarSystem().getName());
            final Double starDistance = TechnologyBrokerService.getDistance(closest.getStarSystem(), currentLocation.getStarSystem());
            if (starDistance > 0D) {
                this.distance.addBinding(this.distance.textProperty(), LocaleService.getStringBinding("menu.broker.distance", Formatters.NUMBER_FORMAT_2.format(starDistance)));
            } else {
                this.distance.addBinding(this.distance.textProperty(), LocaleService.getStringBinding("menu.broker.distance.in.system", Formatters.NUMBER_FORMAT_2.format(closest.getDistanceFromStar()), Formatters.NUMBER_FORMAT_2.format(closest.getDistanceFromStarVariance())));
            }

            this.system = closest.getStarSystem().getName();
            this.getStyleClass().remove("nearest-broker-hidden");

        } catch (final IllegalArgumentException ex) {
            this.getStyleClass().add("nearest-broker-hidden");
        }
    }

    private DestroyableFlowPane getTraderLocation() {
        this.location = LabelBuilder.builder()
                .withStyleClass("nearest-broker-location")
                .withNonLocalizedText("")
                .build();

        this.distance = LabelBuilder.builder()
                .withStyleClass("nearest-broker-distance")
                .withNonLocalizedText("(0Ly)")
                .build();

        final DestroyableResizableImageView copyIcon = ResizableImageViewBuilder.builder()
                .withStyleClass("nearest-broker-copy-icon")
                .withImage("/images/other/copy.png")
                .build();

        final DestroyableStackPane copyIconStackPane = StackPaneBuilder.builder()
                .withNodes(copyIcon)
                .build();

        return FlowPaneBuilder.builder()
                .withStyleClass("nearest-broker-location-line")
                .withOnMouseClicked(event -> {
                    copyLocationToClipboard();
                    NotificationService.showInformation(NotificationType.COPY, LocaleService.LocaleString.of("notification.clipboard.title"), LocaleService.LocaleString.of("notification.clipboard.system.copied.text"));
                })
                .withNodes(this.location, copyIconStackPane, this.distance)
                .build();

    }

    private void copyLocationToClipboard() {
        ClipboardHelper.copyToClipboard(this.system);
    }
}
