package nl.jixxed.eliteodysseymaterials.templates.generic;

import javafx.geometry.Orientation;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyBlueprintName;
import nl.jixxed.eliteodysseymaterials.helper.ClipboardHelper;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.service.ImageService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.LocationService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.event.BlueprintClickEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.LocationChangedEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class EngineerCard extends DestroyableVBox implements DestroyableEventTemplate {
    protected static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    protected static final String ENGINEER_CATEGORY_STYLE_CLASS = "engineer-category";


    @Getter
    protected final Engineer engineer;

    protected DestroyableResizableImageView image;

    protected DestroyableLabel name;
    private DestroyableLabel engineerDistance;
    protected DestroyableFlowPane location;
    protected DestroyableLabel unlockRequirementsTitle;
    protected List<DestroyableHBox> unlockRequirementsLabels;
    protected DestroyableSeparator unlockSeparator;

    public EngineerCard(final Engineer engineer) {
        this.engineer = engineer;
        initComponents2();
        initEventHandling2();
    }

    private void initComponents2() {
        this.getStyleClass().add("engineer-card");

        this.image = register(getEngineerImageView());
        this.name = register(getEngineerName());
        this.location = register(getEngineerLocation());
        this.unlockRequirementsTitle = register(getUnlockRequirementsTitle());
        this.unlockRequirementsLabels = getUnlockRequirements();
        registerAll(this.unlockRequirementsLabels);
        this.unlockSeparator = register(new DestroyableSeparator(Orientation.HORIZONTAL));
    }


    private void initEventHandling2() {
        register(EventService.addListener(true, this, LocationChangedEvent.class, locationChangedEvent ->
                addBinding(this.engineerDistance.textProperty(), LocaleService.getStringBinding("tab.engineer.distance", Formatters.NUMBER_FORMAT_2.format(
                        engineer.getDistance(
                                locationChangedEvent.getCurrentStarSystem().getX(),
                                locationChangedEvent.getCurrentStarSystem().getY(),
                                locationChangedEvent.getCurrentStarSystem().getZ()
                        )
                )))));
    }

    protected List<DestroyableHBox> getUnlockRequirements() {
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

    private DestroyableLabel getUnlockRequirementsTitle() {
        return LabelBuilder.builder()
                .withStyleClass(ENGINEER_CATEGORY_STYLE_CLASS)
                .withText(LocaleService.getStringBinding("tab.engineer.unlock.prerequisites"))
                .build();
    }


    private DestroyableFlowPane getEngineerLocation() {
        DestroyableLabel engineerLocation = LabelBuilder.builder()
                .withStyleClass("engineer-location")
                .withNonLocalizedText(this.engineer.getSettlement().getSettlementName() + " | " + this.engineer.getStarSystem().getName())
                .build();
        this.engineerDistance = LabelBuilder.builder()
                .withStyleClass("engineer-distance")
                .withText("tab.engineer.distance", Formatters.NUMBER_FORMAT_2.format(
                        engineer.getDistance(
                                LocationService.getCurrentStarSystem().getX(),
                                LocationService.getCurrentStarSystem().getY(),
                                LocationService.getCurrentStarSystem().getZ()
                        )))
                .build();
        final DestroyableResizableImageView copyIcon = ResizableImageViewBuilder.builder()
                .withStyleClass("engineer-copy-icon")
                .withImage("/images/other/copy.png")
                .build();

        final DestroyableStackPane copyIconStackPane = StackPaneBuilder.builder()
                .withNodes(copyIcon)
                .build();

        return FlowPaneBuilder.builder()
                .withStyleClass("engineer-location-line")
                .withOnMouseClicked(event -> {
                    copyLocationToClipboard();
                    NotificationService.showInformation(NotificationType.COPY, LocaleService.LocaleString.of("notification.clipboard.title"), LocaleService.LocaleString.of("notification.clipboard.system.copied.text"));
                })
                .withNodes(engineerLocation, copyIconStackPane, this.engineerDistance)
                .build();

    }

    private void copyLocationToClipboard() {
        ClipboardHelper.copyToClipboard(this.engineer.getStarSystem().getName());
    }


    private DestroyableLabel getEngineerName() {
        return LabelBuilder.builder()
                .withStyleClass("engineer-name")
                .withText(LocaleService.getStringBinding(this.engineer.getLocalizationKey()))
                .withOnMouseClicked(_ -> EventService.publish(new BlueprintClickEvent(this.engineer.isOdyssey() ? OdysseyBlueprintName.forEngineer(this.engineer) : HorizonsBlueprintName.forEngineer(this.engineer))))
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
