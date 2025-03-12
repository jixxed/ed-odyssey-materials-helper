package nl.jixxed.eliteodysseymaterials.templates.horizons.engineers;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.ImageService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PinnedBlueprintService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.SegmentType;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegment;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegmentView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;
import nl.jixxed.eliteodysseymaterials.templates.generic.EngineerCard;
import org.controlsfx.control.SegmentedBar;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("java:S2177")
class HorizonsEngineerCard extends EngineerCard {

    private Label hardpointTitle;
    private Label utilityMountTitle;
    private Label coreInternalTitle;
    private Label optionalInternalTitle;
    private Label pinnedBlueprintTitle;
    private final Separator pinnedBlueprintSeparator = new Separator(Orientation.HORIZONTAL);
    private List<HBox> hardpointBlueprintLabels;
    private List<HBox> utilityMountBlueprintLabels;
    private List<HBox> coreInternalBlueprintLabels;
    private List<HBox> optionalInternalBlueprintLabels;

    protected DestroyableResizableImageView grade;
    private DestroyableResizableImageView gradeIcon;

    private SegmentedBar<TypeSegment> segmentedBar;
    private TypeSegment present;
    private TypeSegment notPresent;


    HorizonsEngineerCard(final Engineer engineer) {
        super(engineer);
        initComponents();
        initEventHandling(engineer);
    }


    private void initComponents() {
        if (APPLICATION_STATE.isEngineerUnlockedExact(engineer)) {
            this.image.setImage(ImageService.getImage("/images/engineer/" + engineer.name().toLowerCase() + ".jpg"));
        } else {
            this.image.setImage(ImageService.getImage("/images/engineer/locked.png"));
        }
        this.pinnedBlueprintTitle = getPinnedBlueprintTitle();
        this.hardpointTitle = getHardpointTitle();
        this.utilityMountTitle = getUtilityMountTitle();
        this.coreInternalTitle = getCoreInternalTitle();
        this.optionalInternalTitle = getOptionalInternalTitle();
        this.grade = getEngineerGrade();
        this.hardpointBlueprintLabels = getBlueprints(HorizonsBlueprintConstants.getHardpointBlueprints(),false);
        this.utilityMountBlueprintLabels = getBlueprints(HorizonsBlueprintConstants.getUtilityMountBlueprints(),false);
        this.coreInternalBlueprintLabels = getBlueprints(HorizonsBlueprintConstants.getCoreInternalBlueprints(),false);
        this.optionalInternalBlueprintLabels = getBlueprints(HorizonsBlueprintConstants.getOptionalInternalBlueprints(),false);

        this.segmentedBar = new SegmentedBar<>();
        this.segmentedBar.setOrientation(Orientation.HORIZONTAL);
        this.segmentedBar.setInfoNodeFactory(segment -> null);
        this.segmentedBar.setSegmentViewFactory(segment -> new TypeSegmentView(segment, Map.of(
                SegmentType.PRESENT, Color.rgb(255, 255, 255),
                SegmentType.NOT_PRESENT, Color.rgb(128, 128, 128)
        ), false));
        final Integer engineerProgress = APPLICATION_STATE.getEngineerRank(this.engineer).equals(5) ? 100 : APPLICATION_STATE.getEngineerProgress(this.engineer);
        this.present = new TypeSegment(Math.max(0D, engineerProgress), SegmentType.PRESENT);
        this.notPresent = new TypeSegment(Math.max(0D, 100 - engineerProgress), SegmentType.NOT_PRESENT);
        this.segmentedBar.getSegments().addAll(this.present, this.notPresent);
        final StackPane stackPane = new StackPane(this.segmentedBar, this.grade);
        HBox.setHgrow(stackPane, Priority.ALWAYS);
        this.grade.translateXProperty().bind(this.segmentedBar.widthProperty().subtract(ScalingHelper.getPixelDoubleBindingFromEm(0.25)));
        this.segmentedBar.setTranslateY(stackPane.getHeight() / 2);
        StackPane.setAlignment(this.segmentedBar, Pos.CENTER_LEFT);
        this.getChildren().addAll(this.image, this.name, BoxBuilder.builder().withStyleClass("grade-box").withNodes(stackPane).buildHBox(), this.location);
        if (hasPinnedBlueprint()) {
            this.getChildren().addAll(this.pinnedBlueprintSeparator, this.pinnedBlueprintTitle);
            this.getChildren().addAll(getPinnedBlueprintLabels());
        }
        if (!this.hardpointBlueprintLabels.isEmpty()) {
            this.getChildren().addAll(new Separator(Orientation.HORIZONTAL), this.hardpointTitle);
            this.getChildren().addAll(this.hardpointBlueprintLabels);
        }
        if (!this.utilityMountBlueprintLabels.isEmpty()) {
            this.getChildren().addAll(new Separator(Orientation.HORIZONTAL), this.utilityMountTitle);
            this.getChildren().addAll(this.utilityMountBlueprintLabels);
        }
        if (!this.coreInternalBlueprintLabels.isEmpty()) {
            this.getChildren().addAll(new Separator(Orientation.HORIZONTAL), this.coreInternalTitle);
            this.getChildren().addAll(this.coreInternalBlueprintLabels);
        }
        if (!this.optionalInternalBlueprintLabels.isEmpty()) {
            this.getChildren().addAll(new Separator(Orientation.HORIZONTAL), this.optionalInternalTitle);
            this.getChildren().addAll(this.optionalInternalBlueprintLabels);
        }
        if (!APPLICATION_STATE.isEngineerUnlockedExact(engineer)) {
            this.getChildren().addAll(this.unlockSeparator, this.unlockRequirementsTitle);
            this.getChildren().addAll(this.unlockRequirementsLabels);
        }
        this.getStyleClass().add("engineer-card");
    }

    private boolean hasPinnedBlueprint() {
       return PinnedBlueprintService.hasPinnedBlueprint(this.engineer);
    }

    private List<HBox> getPinnedBlueprintLabels() {
        HorizonsBlueprint blueprint = PinnedBlueprintService.getPinnedBlueprint(this.engineer);
        blueprint = (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe(blueprint.getBlueprintName(), blueprint.getHorizonsBlueprintType(), HorizonsBlueprintGrade.forDigit(HorizonsBlueprintConstants.getEngineerMaxGrade(blueprint, this.engineer)));
        return getBlueprints(Map.of(blueprint.getHorizonsBlueprintName(), Map.of(blueprint.getHorizonsBlueprintType(), Map.of(HorizonsBlueprintGrade.forDigit(HorizonsBlueprintConstants.getEngineerMaxGrade(blueprint, this.engineer)), blueprint))),true);
    }

    private Label getPinnedBlueprintTitle() {
        return LabelBuilder.builder()
                .withStyleClass(ENGINEER_CATEGORY_STYLE_CLASS)
                .withText(LocaleService.getStringBinding("tab.engineer.pinned.blueprint"))
                .build();
    }

    private void initEventHandling(final Engineer engineer) {
        this.eventListeners.add(EventService.addListener(true, this, JournalInitEvent.class, event -> {
            if (event.isInitialised()) {
                updatePinnedBlueprint();
            }
        }));
        this.eventListeners.add(EventService.addListener(true, this, 9, EngineerPinEvent.class, engineerPinEvent -> {
            if (this.engineer.equals(engineerPinEvent.getEngineer())) {
                updatePinnedBlueprint();
            }
        }));
        this.eventListeners.add(EventService.addListener(true, this, EngineerEvent.class, engineerEvent -> {
            this.getChildren().removeAll(this.unlockSeparator, this.unlockRequirementsTitle);
            this.getChildren().removeAll(this.unlockRequirementsLabels);
            final Integer rank = APPLICATION_STATE.getEngineerRank(this.engineer);
            final String imageString;
            if ((rank > 0)) {
                imageString = "rank_" + rank;
            } else if (APPLICATION_STATE.isEngineerInvited(this.engineer)) {
                imageString = "invited";
            } else {
                imageString = "lock";
            }
            final Integer engineerProgress = rank.equals(5) ? 100 : APPLICATION_STATE.getEngineerProgress(this.engineer);
            this.present.setValue(engineerProgress);
            this.notPresent.setValue(100.0 - engineerProgress);
            this.gradeIcon.setImage(ImageService.getImage("/images/ships/engineers/ranks/" + imageString + ".png"));
            if (APPLICATION_STATE.isEngineerUnlockedExact(engineer)) {
                this.image.setImage(ImageService.getImage("/images/engineer/" + engineer.name().toLowerCase() + ".jpg"));
            } else {
                this.image.setImage(ImageService.getImage("/images/engineer/locked.png"));
                this.unlockRequirementsLabels = getUnlockRequirements();
                this.getChildren().addAll(this.unlockSeparator, this.unlockRequirementsTitle);
                this.getChildren().addAll(this.unlockRequirementsLabels);
            }
        }));
    }

    private void updatePinnedBlueprint() {
        if (this.getChildren().contains(this.pinnedBlueprintSeparator)) {
            this.getChildren().remove(4, 7);
        }
        if (hasPinnedBlueprint()) {
            this.getChildren().add(4, this.pinnedBlueprintSeparator);
            this.getChildren().add(5, this.pinnedBlueprintTitle);
            this.getChildren().addAll(6, getPinnedBlueprintLabels());
        }
    }

    private DestroyableResizableImageView getEngineerGrade() {

        final Integer engineerRank = APPLICATION_STATE.getEngineerRank(this.engineer);
        final String imageString;
        if ((engineerRank > 0)) {
            imageString = "rank_" + engineerRank.toString();
        } else if (APPLICATION_STATE.isEngineerInvited(this.engineer)) {
            imageString = "invited";
        } else {
            imageString = "lock";
        }

        this.gradeIcon = ResizableImageViewBuilder.builder()
                .withStyleClass("grade-bar-image")
                .withImage("/images/ships/engineers/ranks/" + imageString + ".png")
                .build();
        return this.gradeIcon;
    }

    @SuppressWarnings("java:S1640")
    private List<HBox> getBlueprints(final Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>>> blueprints, final boolean withType) {
        final Map<HorizonsBlueprint, Integer> maxGrades = new HashMap<>();
        blueprints.values().stream()
                .flatMap(horizonsBlueprintTypeMapMap -> horizonsBlueprintTypeMapMap.values().stream())
                .flatMap(horizonsBlueprintGradeHorizonsBlueprintMap -> horizonsBlueprintGradeHorizonsBlueprintMap.values().stream())
                .filter(horizonsBlueprint -> horizonsBlueprint.getEngineers().contains(this.engineer))
                .sorted((Comparator.comparing(HorizonsBlueprint::getHorizonsBlueprintType)))
                .sorted((Comparator.comparing((HorizonsBlueprint horizonsBlueprint) -> horizonsBlueprint.getHorizonsBlueprintGrade().getGrade()).reversed()))
                .forEach(horizonsBlueprint -> {
                    if (maxGrades.keySet().stream().noneMatch(horizonsBlueprintInMap -> horizonsBlueprintInMap.getHorizonsBlueprintName().equals(horizonsBlueprint.getBlueprintName()))) {
                        maxGrades.put(horizonsBlueprint, horizonsBlueprint.getHorizonsBlueprintGrade().getGrade());
                    }
                });
        return getBlueprintLabels(maxGrades,withType);
    }


    private ArrayList<HBox> getBlueprintLabels(final Map<HorizonsBlueprint, Integer> maxGrades, final boolean withType) {
        return maxGrades.entrySet().stream()
                .sorted(((Comparator<Map.Entry<HorizonsBlueprint, Integer>>) (Comparator<?>) Map.Entry.comparingByValue().reversed()).thenComparing(entry -> LocaleService.getLocalizedStringForCurrentLocale(entry.getKey().getHorizonsBlueprintName().getLocalizationKey())))
                .map(entry -> {
                    final DestroyableLabel name =
                            LabelBuilder.builder()
                                    .withStyleClass("engineer-blueprint")
                                    .withText((!withType) ? LocaleService.getStringBinding(entry.getKey().getHorizonsBlueprintName().getLocalizationKey()) : LocaleService.getStringBinding(() -> LocaleService.getLocalizedStringForCurrentLocale(entry.getKey().getHorizonsBlueprintName().getLocalizationKey()) + " - " + LocaleService.getLocalizedStringForCurrentLocale(entry.getKey().getHorizonsBlueprintType().getLocalizationKey())))
                                    .withOnMouseClicked(event -> EventService.publish(new HorizonsBlueprintClickEvent(entry.getKey())))
                                    .build();
                    return BoxBuilder.builder()
                            .withNodes(LabelBuilder.builder()
                                            .withStyleClass("engineer-bullet")
                                            .withNonLocalizedText(entry.getValue().toString())
                                            .withOnMouseClicked(event -> EventService.publish(new HorizonsBlueprintClickEvent(entry.getKey())))
                                            .build(),
                                    name).buildHBox();
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private Label getHardpointTitle() {
        return LabelBuilder.builder()
                .withStyleClass(ENGINEER_CATEGORY_STYLE_CLASS)
                .withText(LocaleService.getStringBinding("tab.engineer.hardpoint"))
                .build();
    }

    private Label getUtilityMountTitle() {
        return LabelBuilder.builder()
                .withStyleClass(ENGINEER_CATEGORY_STYLE_CLASS)
                .withText(LocaleService.getStringBinding("tab.engineer.utility.mount"))
                .build();
    }

    private Label getCoreInternalTitle() {
        return LabelBuilder.builder()
                .withStyleClass(ENGINEER_CATEGORY_STYLE_CLASS)
                .withText(LocaleService.getStringBinding("tab.engineer.core.internal"))
                .build();
    }

    private Label getOptionalInternalTitle() {
        return LabelBuilder.builder()
                .withStyleClass(ENGINEER_CATEGORY_STYLE_CLASS)
                .withText(LocaleService.getStringBinding("tab.engineer.optional.internal"))
                .build();
    }
}
