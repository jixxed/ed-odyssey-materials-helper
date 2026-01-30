package nl.jixxed.eliteodysseymaterials.templates.generic;

import javafx.css.PseudoClass;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.EdAwesomeIconViewPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModuleBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.service.ImageService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PinnedBlueprintService;
import nl.jixxed.eliteodysseymaterials.service.event.EngineerEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EngineerPinEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.JournalInitEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIcon;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

public class EngineerBlueprintLabel extends DestroyableStackPane implements DestroyableEventTemplate {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    public static final String LOWGRADE = "lowgrade";
    public static final String UNLOCKED = "unlocked";

    private final Engineer engineer;
    private final int blueprintGrade;
    private final boolean exact;
    private final HorizonsBlueprint horizonsBlueprint;
    private DestroyableLabel engineerName;
    private DestroyableResizableImageView gradeImage;
    private DestroyableResizableImageView pinnedImage;
    private Integer currentEngineerRank = 0;
    private boolean dragFlag = false;
    private DestroyableHBox bottomLayer;
    private DestroyableLabel button;

    public EngineerBlueprintLabel(final Engineer engineer) {
        this(engineer, false, 0);
    }

    public EngineerBlueprintLabel(final Engineer engineer, final boolean exact, final int blueprintGrade) {
        this(engineer, null, exact, blueprintGrade);
    }

    public EngineerBlueprintLabel(final Engineer engineer, final HorizonsBlueprint horizonsBlueprint, final boolean exact, final int blueprintGrade) {
        this.horizonsBlueprint = horizonsBlueprint;
        this.engineer = engineer;
        this.blueprintGrade = blueprintGrade;
        this.exact = exact;

        initComponents();
        initEventHandling();

    }

    public void initComponents() {
        this.getStyleClass().add("engineer");

        bottomLayer = BoxBuilder.builder()
                .withStyleClass("data")
                .buildHBox();

        this.getNodes().add(bottomLayer);


        this.engineerName = LabelBuilder.builder()
                .withStyleClass("name")
                .withText(LocaleService.getStringBinding(this.engineer.getLocalizationKey()))
                .withGraphic(this.engineer.isInColonia() ? EdAwesomeIconViewPaneBuilder.builder().withStyleClass("colonia-icon").withIcons(EdAwesomeIcon.OTHER_COLONIA).build() : null)
                .build();
        if (this.engineer.isHorizons()) {
            addGradeImage();
            addPinnedImage();
        }
        bottomLayer.getNodes().add(this.engineerName);
        if (this.engineer.isHorizons() && this.horizonsBlueprint != null) {
            final int engineerMaxGrade = HorizonsBlueprintConstants.getEngineerMaxGrade(this.horizonsBlueprint, this.engineer);
            if (engineerMaxGrade > 0) {
                bottomLayer.getNodes().add(LabelBuilder.builder()
                        .withStyleClass("maxgrade")
                        .withNonLocalizedText("\u2191" + engineerMaxGrade)
                        .build());
            }
        }
        if (this.horizonsBlueprint instanceof HorizonsModuleBlueprint) {
            button = LabelBuilder.builder()
                    .withStyleClass("pin-button")
                    .withText(PinnedBlueprintService.isPinned(this.engineer, this.horizonsBlueprint) ? "blueprint.engineer.unpin" : "blueprint.engineer.pin")
                    .build();
            DestroyableHBox topLayer = BoxBuilder.builder()
                    .withStyleClass("button-layer")
                    .withNodes(new GrowingRegion(), button, new GrowingRegion())
                    .withOnMouseClicked(_ -> processMultiClick())
                    .buildHBox();
            topLayer.visibleProperty().bind(this.hoverProperty());
            this.getNodes().add(topLayer);
        }

        update();
    }


    private void addPinnedImage() {
        this.pinnedImage = ResizableImageViewBuilder.builder()
                .withStyleClasses("pinned-image")
                .withImage("/images/ships/engineers/pinned.png")
                .build();
        bottomLayer.getNodes().add(this.pinnedImage);
    }

    private void addGradeImage() {
        this.gradeImage = ResizableImageViewBuilder.builder()
                .withStyleClasses("grade-image")
                .build();
        bottomLayer.getNodes().add(this.gradeImage);
    }

    public void initEventHandling() {
        register(EventService.addListener(true, this, EngineerEvent.class, event -> update()));
        register(EventService.addListener(true, this, EngineerPinEvent.class, event -> {
            if (this.engineer.equals(event.getEngineer()) && !this.horizonsBlueprint.equals(event.getHorizonsBlueprint())) {
                update();
            }
        }));
        register(EventService.addListener(true, this, 0, JournalInitEvent.class, event -> update()));
    }

    private void update() {
        if (this.exact) {
            this.updateStyle(APPLICATION_STATE.isEngineerUnlockedExact(this.engineer), APPLICATION_STATE.getEngineerRank(this.engineer));
        } else {
            this.updateStyle(APPLICATION_STATE.isEngineerUnlocked(this.engineer), APPLICATION_STATE.getEngineerRank(this.engineer));
        }
        if (this.engineer.isHorizons()) {
            final Integer engineerRank = APPLICATION_STATE.getEngineerRank(this.engineer);
            if (!this.currentEngineerRank.equals(engineerRank)) {//only update if image has changed
                final String imageString = getEngineerRankImage(engineerRank);
                this.gradeImage.setImage(ImageService.getImage("/images/ships/engineers/ranks/" + imageString + ".png"));
                this.currentEngineerRank = engineerRank;
            }
            setPinnedVisibility(PinnedBlueprintService.isPinned(this.engineer, this.horizonsBlueprint));
        }
    }

    private void setPinnedVisibility(boolean visible) {
        this.pinnedImage.setVisible(visible);
        this.pinnedImage.setManaged(visible);
    }


    private String getEngineerRankImage(final Integer engineerRank) {
        final String imageString;
        if ((engineerRank > 0)) {
            imageString = "rank_" + engineerRank;
        } else {
            if (APPLICATION_STATE.isEngineerInvited(this.engineer)) {
                imageString = "invited";
            } else {
                imageString = "lock";
            }
        }
        return imageString;
    }


    private void processMultiClick() {
        if (PinnedBlueprintService.isPinned(this.engineer, this.horizonsBlueprint)) {
            PinnedBlueprintService.unpinBlueprint(this.engineer);
            EventService.publish(new EngineerPinEvent(this.engineer, this.horizonsBlueprint, false));
        } else /*if (this.currentEngineerRank >= this.horizonsBlueprint.getHorizonsBlueprintGrade().getGrade()) */{
            PinnedBlueprintService.pinBlueprint(this.engineer, this.horizonsBlueprint);
            EventService.publish(new EngineerPinEvent(this.engineer, this.horizonsBlueprint, true));
        }
        button.textProperty().bind(LocaleService.getStringBinding(PinnedBlueprintService.isPinned(this.engineer, this.horizonsBlueprint) ? "blueprint.engineer.unpin" : "blueprint.engineer.pin"));
        update();
    }


    private void updateStyle(final boolean unlocked, final Integer currentEngineerRank) {
        if (unlocked && currentEngineerRank >= this.blueprintGrade) {
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass(UNLOCKED), true);
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass(LOWGRADE), false);

        } else if (unlocked) {
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass(UNLOCKED), true);
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass(LOWGRADE), true);
        } else {

            this.pseudoClassStateChanged(PseudoClass.getPseudoClass(UNLOCKED), false);
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass(LOWGRADE), false);
        }
    }

    public String getEngineerName() {
        return this.engineerName.getText();
    }

    @Override
    public void destroyInternal() {
        super.destroyInternal();
//        if (tooltip != null) {
//            Tooltip.uninstall(this, tooltip);
//        }
    }
}
