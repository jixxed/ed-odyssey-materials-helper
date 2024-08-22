package nl.jixxed.eliteodysseymaterials.templates.horizons.hgefinder;

import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.service.LocationService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.LocationChangedEvent;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CopyableSystemLabel extends FlowPane implements Template {
    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance();

    static {
        NUMBER_FORMAT.setMaximumFractionDigits(2);
    }

    private Label systemName;
    private Label distance;
    private StarSystem starSystem;
    private DestroyableResizableImageView copyIcon;
    private final List<EventListener<?>> eventListeners = new ArrayList<>();

    public CopyableSystemLabel() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("copyable-system" );
//        this.setPrefWrapLength(500);
        this.systemName = LabelBuilder.builder()
                .withStyleClass("copyable-system-location")
                .withNonLocalizedText("")
                .build();

        this.distance = LabelBuilder.builder()
                .withStyleClass("copyable-system-distance")
                .withNonLocalizedText("")
                .build();

        this.copyIcon = ResizableImageViewBuilder.builder()
                .withStyleClass("copyable-system-copy-icon")
                .withImage("/images/other/copy.png")
                .build();


        this.setOnMouseClicked(event -> {
            copyLocationToClipboard();
            NotificationService.showInformation(NotificationType.COPY, "Clipboard", "System name copied.");
        });
        this.getChildren().addAll(this.systemName, new StackPane(this.copyIcon), this.distance);
    }

    @Override
    public void initEventHandling() {
        this.eventListeners.add(EventService.addListener(true, this, LocationChangedEvent.class, locationEvent -> {
            update();
        }));
    }

    public void setStarSystem(StarSystem starSystem) {
        this.starSystem = starSystem;
        this.systemName.setText(starSystem.getName());
        update();
    }

    private void update() {
        try {
            this.distance.setText("(" + NUMBER_FORMAT.format(getDistance()) + "Ly)");
        } catch (final IllegalArgumentException ex) {
            log.error("Error while updating distance", ex);
        }
    }

    public Double getDistance() {
        final StarSystem currentLocation = LocationService.getCurrentLocation().getStarSystem();
        return Math.sqrt(Math.pow(currentLocation.getX() - starSystem.getX(), 2) + Math.pow(currentLocation.getY() - starSystem.getY(), 2) + Math.pow(currentLocation.getZ() - starSystem.getZ(), 2));
    }

    private void copyLocationToClipboard() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(this.starSystem.getName());
        clipboard.setContent(content);
    }
}
