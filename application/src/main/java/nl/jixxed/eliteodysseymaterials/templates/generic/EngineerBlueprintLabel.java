package nl.jixxed.eliteodysseymaterials.templates.generic;

import javafx.css.PseudoClass;
import javafx.event.EventHandler;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TooltipBuilder;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModuleBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.service.ImageService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PinnedBlueprintService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;

public class EngineerBlueprintLabel extends DestroyableHBox implements DestroyableEventTemplate {
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
        this.engineerName = LabelBuilder.builder()
                .withStyleClass("name")
                .withText(LocaleService.getStringBinding(this.engineer.getLocalizationKey()))
                .build();
        if (this.engineer.isHorizons()) {
            addGradeImage();
            addPinnedImage();
            if (this.horizonsBlueprint instanceof HorizonsModuleBlueprint) {
                final Tooltip tooltip = TooltipBuilder.builder()
                        .withText(LocaleService.getStringBinding("blueprint.engineer.pinnable.tooltip"))
                        .build();
                Tooltip.install(this, tooltip);
                this.getStyleClass().add("pinnable");

            }
        }
        this.getNodes().add(this.engineerName);
        if (this.engineer.isHorizons() && this.horizonsBlueprint != null) {
            final int engineerMaxGrade = HorizonsBlueprintConstants.getEngineerMaxGrade(this.horizonsBlueprint, this.engineer);
            if (engineerMaxGrade > 0) {
                this.getNodes().add(LabelBuilder.builder()
                        .withStyleClass("maxgrade")
                        .withNonLocalizedText("\u2191" + engineerMaxGrade)
                        .build());
            }
        }
        if (this.horizonsBlueprint instanceof HorizonsModuleBlueprint) {
            this.addEventBinding(this.onMouseClickedProperty(), getClickMouseEventHandler());
            this.addEventBinding(this.onMouseDraggedProperty(), getDragMouseEventHandler());
        }

        update();
    }

    private EventHandler<MouseEvent> getDragMouseEventHandler() {
        return event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                this.dragFlag = true;
            }
        };
    }

    private EventHandler<MouseEvent> getClickMouseEventHandler() {
        return event -> {
            if (event.getButton().equals(MouseButton.SECONDARY)) {
                return;
            }
            if (!this.dragFlag && event.getClickCount() > 1) {
                processMultiClick();
            }

            this.dragFlag = false;
        };
    }

    private void addPinnedImage() {
        this.pinnedImage = ResizableImageViewBuilder.builder()
                .withStyleClasses("pinned-image")
                .withImage("/images/ships/engineers/pinned.png")
                .build();
        this.getNodes().add(this.pinnedImage);
    }

    private void addGradeImage() {
        this.gradeImage = ResizableImageViewBuilder.builder()
                .withStyleClasses("grade-image")
                .build();
        this.getNodes().add(this.gradeImage);
    }

    public void initEventHandling() {
        register(EventService.addListener(true, this, EngineerEvent.class, event -> update()));
        register(EventService.addListener(true, this, EngineerPinEvent.class, event -> {
            if (this.engineer.equals(event.getEngineer()) && !this.horizonsBlueprint.equals(event.getHorizonsBlueprint())) {
                update();
            }
        }));
        register(EventService.addListener(true, this, CommanderSelectedEvent.class, event -> update()));
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
        } else if (this.currentEngineerRank >= this.horizonsBlueprint.getHorizonsBlueprintGrade().getGrade()) {
            PinnedBlueprintService.pinBlueprint(this.engineer, this.horizonsBlueprint);
            EventService.publish(new EngineerPinEvent(this.engineer, this.horizonsBlueprint, true));
        }
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
}
