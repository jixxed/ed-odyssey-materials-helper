package nl.jixxed.eliteodysseymaterials.templates.horizons.materials;

import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.builder.StackPaneBuilder;
import nl.jixxed.eliteodysseymaterials.domain.Location;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsStorageType;
import nl.jixxed.eliteodysseymaterials.enums.MaterialTrader;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.helper.ClipboardHelper;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.LocationService;
import nl.jixxed.eliteodysseymaterials.service.MaterialTraderService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.LocationChangedEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.text.NumberFormat;

public class HorizonsNearestTrader extends DestroyableVBox implements DestroyableEventTemplate {
    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance();

    static {
        NUMBER_FORMAT.setMaximumFractionDigits(2);
    }

    private final HorizonsStorageType type;
    private DestroyableLabel location;
    private DestroyableLabel title;
    private DestroyableLabel distance;
    private String system = "";


    HorizonsNearestTrader(final HorizonsStorageType type) {
        this.type = type;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("nearest-trader");
        this.title = LabelBuilder.builder()
                .withStyleClass("nearest-trader-title")
                .withText(this.type.getLocalizationKey())
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
            final MaterialTrader closest = MaterialTraderService.findClosest(currentLocation.getStarSystem(), this.type);
            this.location.setText(closest.getName() + " | " + closest.getStarSystem().getName());
            final Double starDistance = MaterialTraderService.getDistance(closest.getStarSystem(), currentLocation.getStarSystem());
            if (starDistance > 0D) {
                this.distance.addBinding(this.distance.textProperty(), LocaleService.getStringBinding("tab.trader.distance", Formatters.NUMBER_FORMAT_2.format(starDistance)));
            } else {
                this.distance.addBinding(this.distance.textProperty(), LocaleService.getStringBinding("tab.trader.distance.in.system", Formatters.NUMBER_FORMAT_2.format(closest.getDistanceFromStar()), Formatters.NUMBER_FORMAT_2.format(closest.getDistanceFromStarVariance())));
            }

            this.system = closest.getStarSystem().getName();
            this.getStyleClass().remove("nearest-trader-hidden");

        } catch (final IllegalArgumentException ex) {
            this.getStyleClass().add("nearest-trader-hidden");
        }
    }

    private DestroyableFlowPane getTraderLocation() {
        this.location = LabelBuilder.builder()
                .withStyleClass("nearest-trader-location")
                .withNonLocalizedText("")
                .build();

        this.distance = LabelBuilder.builder()
                .withStyleClass("nearest-trader-distance")
                .withText("tab.trader.distance", 0)
                .build();

        final DestroyableResizableImageView copyIcon = ResizableImageViewBuilder.builder()
                .withStyleClass("nearest-trader-copy-icon")
                .withImage("/images/other/copy.png")
                .build();

        final DestroyableStackPane copyIconStackPane = StackPaneBuilder.builder()
                .withNodes(copyIcon)
                .build();

        return FlowPaneBuilder.builder()
                .withStyleClass("nearest-trader-location-line")
                .withOnMouseClicked(_ -> {
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
