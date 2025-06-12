package nl.jixxed.eliteodysseymaterials.templates.generic;

import javafx.scene.input.MouseEvent;
import lombok.Getter;
import lombok.NonNull;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.domain.Location;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.helper.ClipboardHelper;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.LocationService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.LocationChangedEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableFlowPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;


public class CopyableLocation extends DestroyableFlowPane implements DestroyableEventTemplate {

    @Getter
    private StarSystem starSystem;
    private Double distanceFromStarVariance;
    private DestroyableLabel distance;
    private String station;
    private Double distanceFromStar;
    private DestroyableLabel location;


    public CopyableLocation(@NonNull StarSystem starSystem, @NonNull String station, double distanceFromStar, double distanceFromStarVariance) {
        this.starSystem = starSystem;
        this.station = station;
        this.distanceFromStar = distanceFromStar;
        this.distanceFromStarVariance = distanceFromStarVariance;
        initComponents();
        initEventHandling();
    }

    public CopyableLocation(@NonNull StarSystem starSystem, @NonNull String station) {
        this(starSystem, station, 0D, 0D);
    }

    public CopyableLocation(@NonNull StarSystem starSystem) {
        this(starSystem, "", 0D, 0D);
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("copyable-location");
        location = LabelBuilder.builder()
                .withStyleClass("location")
                .withNonLocalizedText(this.station.isEmpty() ? starSystem.getName() : this.station + " | " + starSystem.getName())
                .build();

        this.distance = LabelBuilder.builder()
                .withStyleClass("distance")
                .withNonLocalizedText("(0Ly)")
                .build();

        final DestroyableResizableImageView copyIcon = ResizableImageViewBuilder.builder()
                .withStyleClass("copy-icon")
                .withImage("/images/other/copy.png")
                .build();

        this.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            copyLocationToClipboard();
            NotificationService.showInformation(NotificationType.COPY, LocaleService.LocaleString.of("notification.clipboard.title"), LocaleService.LocaleString.of("notification.clipboard.system.copied.text"));
        });
        this.getNodes().addAll(location, copyIcon, this.distance);

        update();
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, LocationChangedEvent.class, _ -> update()));
    }

    private void update() {
        final Location currentLocation = LocationService.getCurrentLocation();

        location.setText(this.station.isEmpty() ? starSystem.getName() : this.station + " | " + starSystem.getName());
        final Double starDistance = starSystem.getDistance(currentLocation.getStarSystem());
        if (starDistance > 0D || distanceFromStar == 0D) {
            this.distance.addBinding(this.distance.textProperty(), LocaleService.getStringBinding("system.copy.distance", Formatters.NUMBER_FORMAT_2.format(starDistance)));
        } else {
            this.distance.addBinding(this.distance.textProperty(), LocaleService.getStringBinding("system.copy.distance.in.system", Formatters.NUMBER_FORMAT_2.format(distanceFromStar), Formatters.NUMBER_FORMAT_2.format(distanceFromStarVariance)));
        }
    }

    private void copyLocationToClipboard() {
        ClipboardHelper.copyToClipboard(this.starSystem.getName());
    }


    public void setLocation(@NonNull StarSystem starSystem, @NonNull String station, double distanceFromStar, double distanceFromStarVariance) {
        this.starSystem = starSystem;
        this.station = station;
        this.distanceFromStar = distanceFromStar;
        this.distanceFromStarVariance = distanceFromStarVariance;
        update();
    }

    public void setLocation(@NonNull StarSystem starSystem, @NonNull String station) {
        setLocation(starSystem, station, 0D, 0D);
    }

    public void setLocation(@NonNull StarSystem starSystem) {
        setLocation(starSystem, "", 0D, 0D);
    }
}
