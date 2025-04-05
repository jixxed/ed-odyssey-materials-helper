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
import nl.jixxed.eliteodysseymaterials.service.event.EngineerEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsEngineerSearchEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.SegmentType;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegment;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegmentView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import nl.jixxed.eliteodysseymaterials.templates.generic.EngineerCard;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("java:S2177")
class HorizonsEngineerCard extends EngineerCard implements DestroyableEventTemplate {

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
    }

    @Override
    public void initComponents() {
        super.initComponents();
        this.getStyleClass().add("engineer-card");

        if (APPLICATION_STATE.isEngineerUnlockedExact(engineer)) {
            this.image.setImage(ImageService.getImage("/images/engineer/" + engineer.name().toLowerCase() + ".jpg"));
        } else {
            this.image.setImage(ImageService.getImage("/images/engineer/locked.png"));
        }
        DestroyableLabel hardpointTitle = register(getHardpointTitle());
        DestroyableLabel utilityMountTitle = register(getUtilityMountTitle());
        DestroyableLabel coreInternalTitle = register(getCoreInternalTitle());
        DestroyableLabel optionalInternalTitle = register(getOptionalInternalTitle());
        this.grade = getEngineerGrade();
        List<DestroyableHBox> hardpointBlueprintLabels = getBlueprints(HorizonsBlueprintConstants.getHardpointBlueprints());
        List<DestroyableHBox> utilityMountBlueprintLabels = getBlueprints(HorizonsBlueprintConstants.getUtilityMountBlueprints());
        List<DestroyableHBox> coreInternalBlueprintLabels = getBlueprints(HorizonsBlueprintConstants.getCoreInternalBlueprints());
        List<DestroyableHBox> optionalInternalBlueprintLabels = getBlueprints(HorizonsBlueprintConstants.getOptionalInternalBlueprints());

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
        final DestroyableVBox text = BoxBuilder.builder()
                .withStyleClass("text-box")
                .withNodes(this.name, box, this.location).buildVBox();
        this.getNodes().addAll(this.image, text);
        text.getNodes().addAll(new PinnedBlueprintSection(engineer));
        if (!hardpointBlueprintLabels.isEmpty()) {
            text.getNodes().addAll(new DestroyableSeparator(Orientation.HORIZONTAL), hardpointTitle);
            text.getNodes().addAll(hardpointBlueprintLabels);
        }
        if (!utilityMountBlueprintLabels.isEmpty()) {
            text.getNodes().addAll(new DestroyableSeparator(Orientation.HORIZONTAL), utilityMountTitle);
            text.getNodes().addAll(utilityMountBlueprintLabels);
        }
        if (!coreInternalBlueprintLabels.isEmpty()) {
            text.getNodes().addAll(new DestroyableSeparator(Orientation.HORIZONTAL), coreInternalTitle);
            text.getNodes().addAll(coreInternalBlueprintLabels);
        }
        if (!optionalInternalBlueprintLabels.isEmpty()) {
            text.getNodes().addAll(new DestroyableSeparator(Orientation.HORIZONTAL), optionalInternalTitle);
            text.getNodes().addAll(optionalInternalBlueprintLabels);
        }
        text.getNodes().add(this.unlockSection);

    }

    @Override
    public void initEventHandling() {
        super.initEventHandling();
        register(EventService.addListener(true, this, EngineerEvent.class, _ -> updateProgress()));
        register(EventService.addListener(true, this, HorizonsEngineerSearchEvent.class, horizonsEngineerSearchEvent -> update(horizonsEngineerSearchEvent.getSearch())));
    }

    private void updateProgress() {
        final Integer rank = APPLICATION_STATE.getEngineerRank(this.engineer);
        final int engineerProgress = rank.equals(5) ? 100 : APPLICATION_STATE.getEngineerProgress(this.engineer);
        this.present.setValue(engineerProgress);
        this.notPresent.setValue(100.0 - engineerProgress);
        this.gradeIcon.setImage(ImageService.getImage("/images/ships/engineers/ranks/" + getGradeImage() + ".png"));
        if (APPLICATION_STATE.isEngineerUnlockedExact(engineer)) {
            this.image.setImage(ImageService.getImage("/images/engineer/" + engineer.name().toLowerCase() + ".jpg"));
        } else {
            this.image.setImage(ImageService.getImage("/images/engineer/locked.png"));
        }
    }

    private String getGradeImage() {
        final Integer rank = APPLICATION_STATE.getEngineerRank(this.engineer);
        final String imageString;
        if ((rank > 0)) {
            imageString = "rank_" + rank;
        } else if (APPLICATION_STATE.isEngineerInvited(this.engineer)) {
            imageString = "invited";
        } else {
            imageString = "lock";
        }
        return imageString;
    }

    private void update(final String search) {
        boolean visible = search.isBlank()
                || engineer.getSettlement().getSettlementName().toLowerCase().contains(search.toLowerCase())
                || engineer.getStarSystem().getName().toLowerCase().contains(search.toLowerCase())
                || LocaleService.getLocalizedStringForCurrentLocale(engineer.getLocalizationKey()).toLowerCase().contains(search.toLowerCase())
                || hasBlueprintLike(engineer, search);
        this.setVisible(visible);
        this.setManaged(visible);
    }

    private boolean hasBlueprintLike(final Engineer engineer, final String search) {
        return hasBlueprintLike(HorizonsBlueprintConstants.getHardpointBlueprints(), engineer, search)
                || hasBlueprintLike(HorizonsBlueprintConstants.getUtilityMountBlueprints(), engineer, search)
                || hasBlueprintLike(HorizonsBlueprintConstants.getCoreInternalBlueprints(), engineer, search)
                || hasBlueprintLike(HorizonsBlueprintConstants.getOptionalInternalBlueprints(), engineer, search);
    }

    private static boolean hasBlueprintLike(Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>>> blueprints, Engineer engineer, String search) {
        return blueprints.values().stream()
                .flatMap(horizonsBlueprintTypeMapMap -> horizonsBlueprintTypeMapMap.values().stream())
                .flatMap(horizonsBlueprintGradeHorizonsBlueprintMap -> horizonsBlueprintGradeHorizonsBlueprintMap.values().stream())
                .filter(horizonsBlueprint -> horizonsBlueprint.getEngineers().contains(engineer))
                .anyMatch(horizonsBlueprint -> LocaleService.getLocalizedStringForCurrentLocale(horizonsBlueprint.getBlueprintName().getLocalizationKey()).toLowerCase().contains(search.toLowerCase()));
    }

    private DestroyableResizableImageView getEngineerGrade() {
        this.gradeIcon = ResizableImageViewBuilder.builder()
                .withStyleClass("grade-bar-image")
                .withImage("/images/ships/engineers/ranks/" + getGradeImage() + ".png")
                .build();
        return this.gradeIcon;
    }

    @SuppressWarnings("java:S1640")
    private List<DestroyableHBox> getBlueprints(final Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>>> blueprints) {
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
        return getBlueprintLabels(maxGrades);
    }

    @SuppressWarnings("unchecked")
    private List<DestroyableHBox> getBlueprintLabels(final Map<HorizonsBlueprint, Integer> maxGrades) {
        return maxGrades.entrySet().stream()
                .sorted(((Comparator<Map.Entry<HorizonsBlueprint, Integer>>) (Comparator<?>) Map.Entry.comparingByValue().reversed())
                        .thenComparing(entry -> LocaleService.getLocalizedStringForCurrentLocale(entry.getKey().getHorizonsBlueprintName().getLocalizationKey())))
                .map(entry -> new Blueprint(entry.getKey(), entry.getValue()))
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
