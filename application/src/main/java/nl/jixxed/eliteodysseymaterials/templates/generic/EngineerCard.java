package nl.jixxed.eliteodysseymaterials.templates.generic;

import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.service.ImageService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.LocationService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.event.BlueprintClickEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.LocationChangedEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EngineerCard extends VBox {
    protected static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    protected static final Function<BlueprintName, HBox> RECIPE_TO_ENGINEER_BLUEPRINT_LABEL = recipeName -> BoxBuilder.builder()
            .withNodes(LabelBuilder.builder()
                            .withStyleClass("engineer-bullet")
                            .withNonLocalizedText("\u2022")
                            .withOnMouseClicked(event -> EventService.publish(new BlueprintClickEvent(recipeName)))
                            .build(),
                    LabelBuilder.builder()
                            .withStyleClass("engineer-blueprint")
                            .withText(LocaleService.getStringBinding(recipeName.getLocalizationKey()))
                            .withOnMouseClicked(event -> EventService.publish(new BlueprintClickEvent(recipeName)))
                            .build()).buildHBox();
    protected static final String ENGINEER_CATEGORY_STYLE_CLASS = "engineer-category";


    @Getter
    protected final Engineer engineer;

    protected DestroyableResizableImageView image;

    protected Label name;
    private Label engineerLocation;
    private Label engineerDistance;
    private DestroyableResizableImageView copyIcon;
    protected FlowPane location;
    protected Label unlockRequirementsTitle;
    protected List<HBox> unlockRequirementsLabels;
    protected Separator unlockSeparator;

    public EngineerCard(final Engineer engineer) {
        this.engineer = engineer;
        initComponents();
        initEventHandling(engineer);
    }

    private void initEventHandling(final Engineer engineer) {
        this.eventListeners.add(EventService.addListener(true, this, LocationChangedEvent.class, locationChangedEvent ->
                this.engineerDistance.setText("(" + Formatters.NUMBER_FORMAT_2.format(
                        engineer.getDistance(
                                locationChangedEvent.getCurrentStarSystem().getX(),
                                locationChangedEvent.getCurrentStarSystem().getY(),
                                locationChangedEvent.getCurrentStarSystem().getZ()
                        )
                ) + "Ly)")));
    }

    private void initComponents() {
        this.image = getEngineerImageView();
        this.name = getEngineerName();
        this.location = getEngineerLocation();
        this.unlockRequirementsTitle = getUnlockRequirementsTitle();
        this.unlockRequirementsLabels = getUnlockRequirements();
        this.unlockSeparator = new Separator(Orientation.HORIZONTAL);
        this.getStyleClass().add("engineer-card");
    }

    protected List<HBox> getUnlockRequirements() {
        return this.engineer.getPrerequisites().stream()
                .map(prerequisite -> BoxBuilder.builder()
                        .withNodes(LabelBuilder.builder()
                                        .withStyleClass("engineer-bullet")
                                        .withNonLocalizedText(Boolean.TRUE.equals((prerequisite.isCompleted())) ? "\u2714" : "\u2022")
                                        .withOnMouseClicked(event -> EventService.publish(new BlueprintClickEvent(prerequisite.getBlueprintName())))
                                        .build(),
                                LabelBuilder.builder()
                                        .withStyleClass("engineer-prerequisite")
                                        .withText(LocaleService.getStringBinding(prerequisite.getLocalisationKey()))
                                        .withOnMouseClicked(event -> EventService.publish(new BlueprintClickEvent(prerequisite.getBlueprintName())))
                                        .build()).buildHBox())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private Label getUnlockRequirementsTitle() {
        return LabelBuilder.builder()
                .withStyleClass(ENGINEER_CATEGORY_STYLE_CLASS)
                .withText(LocaleService.getStringBinding("tab.engineer.unlock.prerequisites"))
                .build();
    }


    private FlowPane getEngineerLocation() {
        this.engineerLocation = LabelBuilder.builder()
                .withStyleClass("engineer-location")
                .withNonLocalizedText(this.engineer.getSettlement().getSettlementName() + " | " + this.engineer.getStarSystem().getName())
                .build();
        String distance = "(" + Formatters.NUMBER_FORMAT_2.format(
                engineer.getDistance(
                        LocationService.getCurrentStarSystem().getX(),
                        LocationService.getCurrentStarSystem().getY(),
                        LocationService.getCurrentStarSystem().getZ()
                )
        ) + "Ly)";
        this.engineerDistance = LabelBuilder.builder()
                .withStyleClass("engineer-distance")
                .withNonLocalizedText(distance)
                .build();

        this.copyIcon = ResizableImageViewBuilder.builder()
                .withStyleClass("engineer-copy-icon")
                .withImage("/images/other/copy.png")
                .build();

        return FlowPaneBuilder.builder().withStyleClass("engineer-location-line")
                .withOnMouseClicked(event -> {
                    copyLocationToClipboard();
                    NotificationService.showInformation(NotificationType.COPY, "Clipboard", "System name copied.");
                })
                .withNodes(this.engineerLocation, new StackPane(this.copyIcon), this.engineerDistance)
                .build();

    }

    private void copyLocationToClipboard() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(this.engineer.getStarSystem().getName());
        clipboard.setContent(content);
    }


    private Label getEngineerName() {
        return LabelBuilder.builder()
                .withStyleClass("engineer-name")
                .withText(LocaleService.getStringBinding(this.engineer.getLocalizationKey()))
                .withOnMouseClicked(event -> EventService.publish(new BlueprintClickEvent(this.engineer.isOdyssey() ? OdysseyBlueprintName.forEngineer(this.engineer) : HorizonsBlueprintName.forEngineer(this.engineer))))
                .build();
    }

    private DestroyableResizableImageView getEngineerImageView() {
        return ResizableImageViewBuilder.builder()
                .withStyleClass("engineer-image")
                .withPreserveRatio(true)
                .withImage(ImageService.getImage("/images/engineer/locked.png"))
                .build();

    }
}
