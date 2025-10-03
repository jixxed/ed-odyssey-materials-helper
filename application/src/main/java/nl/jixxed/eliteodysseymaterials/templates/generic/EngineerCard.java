package nl.jixxed.eliteodysseymaterials.templates.generic;

import javafx.geometry.Orientation;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.EdAwesomeIconViewPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyBlueprintName;
import nl.jixxed.eliteodysseymaterials.service.ImageService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.BlueprintClickEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EngineerEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.JournalInitEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIcon;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIconView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class EngineerCard extends DestroyableVBox implements DestroyableEventTemplate {
    protected static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    protected static final String ENGINEER_CATEGORY_STYLE_CLASS = "category";


    @Getter
    protected final Engineer engineer;

    protected DestroyableResizableImageView image;

    protected DestroyableLabel name;
    protected CopyableLocation location;
    private DestroyableSeparator unlockSeparator;
    private DestroyableLabel unlockRequirementsTitle;
    private List<UnlockRequirement> unlockRequirementsLabels;
    protected DestroyableVBox unlockSection;

    public EngineerCard(final Engineer engineer) {
        this.engineer = engineer;
        initComponents();
        initEventHandling();
    }

    public void initComponents() {
        this.getStyleClass().add("engineer-card");

        this.image = register(getEngineerImageView());
        this.name = register(getEngineerName());
        this.location = register(getEngineerLocation());
        this.unlockRequirementsTitle = getUnlockRequirementsTitle();
        this.unlockRequirementsLabels = getUnlockRequirements();
        registerAll(this.unlockRequirementsLabels);
        this.unlockSeparator = new DestroyableSeparator(Orientation.HORIZONTAL);
        unlockSection = register(BoxBuilder.builder()
                .withNodes(this.unlockSeparator, this.unlockRequirementsTitle)
                .buildVBox());
        unlockSection.getNodes().addAll(this.unlockRequirementsLabels);
        updateVisibility();
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, EngineerEvent.class, _ -> updateVisibility()));

        register(EventService.addListener(true, this, JournalInitEvent.class, event -> {
            if (event.isInitialised()) {
                updateVisibility();
            }
        }));
    }

    private void updateVisibility() {
        final boolean visible = this.engineer.getPrerequisites().stream().anyMatch(engineerPrerequisite -> !engineerPrerequisite.isCompleted());
        unlockSection.setVisible(visible);
        unlockSection.setManaged(visible);
    }

    protected List<UnlockRequirement> getUnlockRequirements() {
        return this.engineer.getPrerequisites().stream()
                .map(UnlockRequirement::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private DestroyableLabel getUnlockRequirementsTitle() {
        return LabelBuilder.builder()
                .withStyleClass(ENGINEER_CATEGORY_STYLE_CLASS)
                .withText(LocaleService.getStringBinding("tab.engineer.unlock.prerequisites"))
                .build();
    }


    private CopyableLocation getEngineerLocation() {
        return new CopyableLocation(this.engineer.getStarSystem(), this.engineer.getSettlement().getSettlementName());

    }

    private DestroyableLabel getEngineerName() {
        return LabelBuilder.builder()
                .withStyleClass("name")
                .withText(this.engineer.getLocalizationKey())
                .withGraphic(this.engineer.isInColonia() ? EdAwesomeIconViewPaneBuilder.builder().withStyleClass("colonia-icon").withIcons(EdAwesomeIcon.OTHER_COLONIA).build() : null)
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
