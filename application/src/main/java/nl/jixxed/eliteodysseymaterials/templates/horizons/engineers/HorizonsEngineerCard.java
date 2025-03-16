package nl.jixxed.eliteodysseymaterials.templates.horizons.engineers;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import nl.jixxed.eliteodysseymaterials.builder.*;
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
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import nl.jixxed.eliteodysseymaterials.templates.generic.EngineerCard;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("java:S2177")
class HorizonsEngineerCard extends EngineerCard {

    private DestroyableLabel pinnedBlueprintTitle;
    private DestroyableSeparator pinnedBlueprintSeparator;

    protected DestroyableResizableImageView grade;
    private DestroyableResizableImageView gradeIcon;

    private TypeSegment present;
    private TypeSegment notPresent;
    private static final Callback<TypeSegment, Node> segmentViewFactory = segment -> new TypeSegmentView(segment, Map.of(
            SegmentType.PRESENT, Color.rgb(255, 255, 255),
            SegmentType.NOT_PRESENT, Color.rgb(128, 128, 128)
    ), false);


    HorizonsEngineerCard(final Engineer engineer) {
        super(engineer);
        initComponents();
        initEventHandling();
    }


    public void initComponents() {
        this.getStyleClass().add("engineer-card");

        if (APPLICATION_STATE.isEngineerUnlockedExact(engineer)) {
            this.image.setImage(ImageService.getImage("/images/engineer/" + engineer.name().toLowerCase() + ".jpg"));
        } else {
            this.image.setImage(ImageService.getImage("/images/engineer/locked.png"));
        }
        this.pinnedBlueprintTitle = register(getPinnedBlueprintTitle());
        this.pinnedBlueprintSeparator = register(new DestroyableSeparator(Orientation.HORIZONTAL));
        DestroyableLabel hardpointTitle = register(getHardpointTitle());
        DestroyableLabel utilityMountTitle = register(getUtilityMountTitle());
        DestroyableLabel coreInternalTitle = register(getCoreInternalTitle());
        DestroyableLabel optionalInternalTitle = register(getOptionalInternalTitle());
        this.grade = getEngineerGrade();
        List<DestroyableHBox> hardpointBlueprintLabels = getBlueprints(HorizonsBlueprintConstants.getHardpointBlueprints(), false);
        List<DestroyableHBox> utilityMountBlueprintLabels = getBlueprints(HorizonsBlueprintConstants.getUtilityMountBlueprints(), false);
        List<DestroyableHBox> coreInternalBlueprintLabels = getBlueprints(HorizonsBlueprintConstants.getCoreInternalBlueprints(), false);
        List<DestroyableHBox> optionalInternalBlueprintLabels = getBlueprints(HorizonsBlueprintConstants.getOptionalInternalBlueprints(), false);

        final int engineerProgress = APPLICATION_STATE.getEngineerRank(this.engineer).equals(5) ? 100 : APPLICATION_STATE.getEngineerProgress(this.engineer);
        this.present = new TypeSegment(Math.max(0D, engineerProgress), SegmentType.PRESENT);
        this.notPresent = new TypeSegment(Math.max(0D, 100D - engineerProgress), SegmentType.NOT_PRESENT);
        DestroyableSegmentedBar<TypeSegment> segmentedBar = SegmentedBarBuilder.builder(TypeSegment.class)
                .withSegments(this.present, this.notPresent)
                .withOrientation(Orientation.HORIZONTAL)
                .withInfoNodeFactory(_ -> null)
                .withSegmentViewFactory(segmentViewFactory)
                .build();

        final DestroyableStackPane stackPane = StackPaneBuilder.builder()
                .withNodes(segmentedBar, this.grade)
                .build();
        HBox.setHgrow(stackPane, Priority.ALWAYS);
        this.grade.addBinding(this.grade.translateXProperty(), segmentedBar.widthProperty().subtract(ScalingHelper.getPixelDoubleBindingFromEm(0.25)));
        segmentedBar.setTranslateY(stackPane.getHeight() / 2);
        StackPane.setAlignment(segmentedBar, Pos.CENTER_LEFT);
        final DestroyableHBox box = BoxBuilder.builder()
                .withStyleClass("grade-box")
                .withNodes(stackPane).buildHBox();
        this.getNodes().addAll(this.image, this.name, box, this.location);
        if (hasPinnedBlueprint()) {
            this.getNodes().addAll(this.pinnedBlueprintSeparator, this.pinnedBlueprintTitle);
            this.getNodes().addAll(getPinnedBlueprintLabels());
        }
        if (!hardpointBlueprintLabels.isEmpty()) {
            this.getNodes().addAll(new DestroyableSeparator(Orientation.HORIZONTAL), hardpointTitle);
            this.getNodes().addAll(hardpointBlueprintLabels);
        }
        if (!utilityMountBlueprintLabels.isEmpty()) {
            this.getNodes().addAll(new DestroyableSeparator(Orientation.HORIZONTAL), utilityMountTitle);
            this.getNodes().addAll(utilityMountBlueprintLabels);
        }
        if (!coreInternalBlueprintLabels.isEmpty()) {
            this.getNodes().addAll(new DestroyableSeparator(Orientation.HORIZONTAL), coreInternalTitle);
            this.getNodes().addAll(coreInternalBlueprintLabels);
        }
        if (!optionalInternalBlueprintLabels.isEmpty()) {
            this.getNodes().addAll(new DestroyableSeparator(Orientation.HORIZONTAL), optionalInternalTitle);
            this.getNodes().addAll(optionalInternalBlueprintLabels);
        }
        if (!APPLICATION_STATE.isEngineerUnlockedExact(engineer)) {
            this.getNodes().addAll(this.unlockSeparator, this.unlockRequirementsTitle);
            this.getNodes().addAll(this.unlockRequirementsLabels);
        }
    }

    private boolean hasPinnedBlueprint() {
        return PinnedBlueprintService.hasPinnedBlueprint(this.engineer);
    }

    private List<DestroyableHBox> getPinnedBlueprintLabels() {
        HorizonsBlueprint blueprint = PinnedBlueprintService.getPinnedBlueprint(this.engineer);
        blueprint = (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe(blueprint.getBlueprintName(), blueprint.getHorizonsBlueprintType(), HorizonsBlueprintGrade.forDigit(HorizonsBlueprintConstants.getEngineerMaxGrade(blueprint, this.engineer)));
        return getBlueprints(Map.of(blueprint.getHorizonsBlueprintName(), Map.of(blueprint.getHorizonsBlueprintType(), Map.of(HorizonsBlueprintGrade.forDigit(HorizonsBlueprintConstants.getEngineerMaxGrade(blueprint, this.engineer)), blueprint))), true);
    }

    private DestroyableLabel getPinnedBlueprintTitle() {
        return LabelBuilder.builder()
                .withStyleClass(ENGINEER_CATEGORY_STYLE_CLASS)
                .withText("tab.engineer.pinned.blueprint")
                .build();
    }

    public void initEventHandling() {
        register(EventService.addListener(true, this, JournalInitEvent.class, event -> {
            if (event.isInitialised()) {
                updatePinnedBlueprint();
            }
        }));
        register(EventService.addListener(true, this, 9, EngineerPinEvent.class, engineerPinEvent -> {
            if (this.engineer.equals(engineerPinEvent.getEngineer())) {
                updatePinnedBlueprint();
            }
        }));
        register(EventService.addListener(true, this, EngineerEvent.class, _ -> {
            this.getNodes().removeAll(this.unlockSeparator, this.unlockRequirementsTitle);
            this.getNodes().removeAll(this.unlockRequirementsLabels);
            final Integer rank = APPLICATION_STATE.getEngineerRank(this.engineer);
            final String imageString;
            if ((rank > 0)) {
                imageString = "rank_" + rank;
            } else if (APPLICATION_STATE.isEngineerInvited(this.engineer)) {
                imageString = "invited";
            } else {
                imageString = "lock";
            }
            final int engineerProgress = rank.equals(5) ? 100 : APPLICATION_STATE.getEngineerProgress(this.engineer);
            this.present.setValue(engineerProgress);
            this.notPresent.setValue(100.0 - engineerProgress);
            this.gradeIcon.setImage(ImageService.getImage("/images/ships/engineers/ranks/" + imageString + ".png"));
            if (APPLICATION_STATE.isEngineerUnlockedExact(engineer)) {
                this.image.setImage(ImageService.getImage("/images/engineer/" + engineer.name().toLowerCase() + ".jpg"));
            } else {
                this.image.setImage(ImageService.getImage("/images/engineer/locked.png"));
                this.unlockRequirementsLabels = getUnlockRequirements();
                this.getNodes().addAll(this.unlockSeparator, this.unlockRequirementsTitle);
                this.getNodes().addAll(this.unlockRequirementsLabels);
            }
        }));
    }

    private void updatePinnedBlueprint() {
        if (this.getNodes().contains(this.pinnedBlueprintSeparator)) {
            this.getNodes().remove(4, 7);
        }
        if (hasPinnedBlueprint()) {
            this.getNodes().add(4, this.pinnedBlueprintSeparator);
            this.getNodes().add(5, this.pinnedBlueprintTitle);
            this.getNodes().addAll(6, getPinnedBlueprintLabels());
        }
    }

    private DestroyableResizableImageView getEngineerGrade() {

        final Integer engineerRank = APPLICATION_STATE.getEngineerRank(this.engineer);
        final String imageString;
        if ((engineerRank > 0)) {
            imageString = "rank_" + engineerRank;
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
    private List<DestroyableHBox> getBlueprints(final Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>>> blueprints, final boolean withType) {
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
        return getBlueprintLabels(maxGrades, withType);
    }

    @SuppressWarnings("unchecked")
    private ArrayList<DestroyableHBox> getBlueprintLabels(final Map<HorizonsBlueprint, Integer> maxGrades, final boolean withType) {
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

    private DestroyableLabel getHardpointTitle() {
        return LabelBuilder.builder()
                .withStyleClass(ENGINEER_CATEGORY_STYLE_CLASS)
                .withText("tab.engineer.hardpoint")
                .build();
    }

    private DestroyableLabel getUtilityMountTitle() {
        return LabelBuilder.builder()
                .withStyleClass(ENGINEER_CATEGORY_STYLE_CLASS)
                .withText("tab.engineer.utility.mount")
                .build();
    }

    private DestroyableLabel getCoreInternalTitle() {
        return LabelBuilder.builder()
                .withStyleClass(ENGINEER_CATEGORY_STYLE_CLASS)
                .withText("tab.engineer.core.internal")
                .build();
    }

    private DestroyableLabel getOptionalInternalTitle() {
        return LabelBuilder.builder()
                .withStyleClass(ENGINEER_CATEGORY_STYLE_CLASS)
                .withText("tab.engineer.optional.internal")
                .build();
    }
}
