package nl.jixxed.eliteodysseymaterials.templates.generic;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import lombok.Getter;
import lombok.NonNull;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.domain.Location;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.helper.ClipboardHelper;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.LocationService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.PermitService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.LocationChangedEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.EdAwesomeIconViewPane;
import nl.jixxed.eliteodysseymaterials.templates.components.FontAwesomeIconViewPane;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIcon;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIconView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;


public class CopyableLocation extends DestroyableFlowPane implements DestroyableEventTemplate {

    @Getter
    private StarSystem starSystem;
    private Double distanceFromStarVariance;
    private DestroyableLabel distance;
    private String station;
    private Double distanceFromStar;
    private DestroyableLabel location;
    private EdAwesomeIconViewPane permitIcon;


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

        FontAwesomeIconViewPane copyIcon = new FontAwesomeIconViewPane(FontAwesomeIconViewBuilder.builder()
                .withStyleClass("copy-icon")
                .withIcon(FontAwesomeIcon.CLONE)
                .build());
        permitIcon = EdAwesomeIconViewPaneBuilder.builder()
                .withStyleClass("permit-icon")
                .withIcons(new EdAwesomeIconView(EdAwesomeIcon.OTHER_PERMIT))
                .build();
        final DestroyableTooltip tooltip = TooltipBuilder.builder()
                .withShowDelay(Duration.ZERO)
                .withText("location.tooltip.permit.required")
                .build();
        tooltip.install(permitIcon);

//        final DestroyableResizableImageView copyIcon = ResizableImageViewBuilder.builder()
//                .withStyleClass("copy-icon")
//                .withImage("/images/other/copy.png")
//                .build();

        this.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            copyLocationToClipboard();
            NotificationService.showInformation(NotificationType.COPY, LocaleService.LocaleString.of("notification.clipboard.title"), LocaleService.LocaleString.of("notification.clipboard.system.copied.text"));
        });
        this.getNodes().addAll(location, permitIcon, copyIcon, this.distance);

        update();
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, LocationChangedEvent.class, _ -> update()));
    }

    private void update() {
        final Location currentLocation = LocationService.getCurrentLocation();
        boolean permitSystem = PermitService.isPermitSystem(starSystem);
        permitIcon.setVisible(permitSystem);
        permitIcon.setManaged(permitSystem);
        location.setText(this.station.isEmpty() ? starSystem.getName() : this.station + " | " + starSystem.getName());
        final Double starDistance = starSystem.getDistance(currentLocation.getStarSystem());
        if (starDistance > 0D || distanceFromStar == 0D) {
            this.distance.addBinding(this.distance.textProperty(), LocaleService.getStringBinding("system.copy.distance", Formatters.NUMBER_FORMAT_2.format(starDistance)));
        } else if (distanceFromStarVariance > 0D) {
            this.distance.addBinding(this.distance.textProperty(), LocaleService.getStringBinding("system.copy.distance.in.system", Formatters.NUMBER_FORMAT_2.format(distanceFromStar), Formatters.NUMBER_FORMAT_2.format(distanceFromStarVariance)));
        } else {
            this.distance.addBinding(this.distance.textProperty(), LocaleService.getStringBinding("system.copy.distance.in.system.no.variance", Formatters.NUMBER_FORMAT_2.format(distanceFromStar)));
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
