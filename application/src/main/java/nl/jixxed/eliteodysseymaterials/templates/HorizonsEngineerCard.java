package nl.jixxed.eliteodysseymaterials.templates;

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
import nl.jixxed.eliteodysseymaterials.service.event.BlueprintClickEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EngineerEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;
import nl.jixxed.eliteodysseymaterials.templates.segmentbar.SegmentType;
import nl.jixxed.eliteodysseymaterials.templates.segmentbar.TypeSegment;
import nl.jixxed.eliteodysseymaterials.templates.segmentbar.TypeSegmentView;
import org.controlsfx.control.SegmentedBar;

import java.util.*;
import java.util.stream.Collectors;

class HorizonsEngineerCard extends EngineerCard {

    private Label hardpointTitle;
    private Label utilityMountTitle;
    private Label coreInternalTitle;
    private Label optionalInternalTitle;
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
        this.hardpointTitle = getHardpointTitle();
        this.utilityMountTitle = getUtilityMountTitle();
        this.coreInternalTitle = getCoreInternalTitle();
        this.optionalInternalTitle = getOptionalInternalTitle();
        this.grade = getEngineerGrade();
        this.hardpointBlueprintLabels = getBlueprints(HorizonsBlueprintConstants.getHardpointBlueprints());
        this.utilityMountBlueprintLabels = getBlueprints(HorizonsBlueprintConstants.getUtilityMountBlueprints());
        this.coreInternalBlueprintLabels = getBlueprints(HorizonsBlueprintConstants.getCoreInternalBlueprints());
        this.optionalInternalBlueprintLabels = getBlueprints(HorizonsBlueprintConstants.getOptionalInternalBlueprints());

        this.segmentedBar = new SegmentedBar<>();
        this.segmentedBar.setOrientation(Orientation.HORIZONTAL);
        this.segmentedBar.setInfoNodeFactory(segment -> null);
        this.segmentedBar.setSegmentViewFactory(segment -> new TypeSegmentView(segment, Map.of(
                SegmentType.PRESENT, Color.rgb(255, 255, 255),
                SegmentType.NOT_PRESENT, Color.rgb(128, 128, 128)
        ), false));
        final Integer engineerProgress = APPLICATION_STATE.getEngineerRank(this.engineer).equals(5) ? 100 : APPLICATION_STATE.getEngineerProgress(this.engineer);
        this.present = new TypeSegment(engineerProgress, SegmentType.PRESENT);
        this.notPresent = new TypeSegment(100 - engineerProgress, SegmentType.NOT_PRESENT);
        this.segmentedBar.getSegments().addAll(this.present, this.notPresent);
        final StackPane stackPane = new StackPane(this.segmentedBar, this.grade);
        HBox.setHgrow(stackPane, Priority.ALWAYS);
        this.grade.translateXProperty().bind(this.segmentedBar.widthProperty().subtract(ScalingHelper.getPixelDoubleBindingFromEm(0.25)));
//        this.segmentedBar.setLayoutX(0);
        this.segmentedBar.setTranslateY(stackPane.getHeight() / 2);
        StackPane.setAlignment(this.segmentedBar, Pos.CENTER_LEFT);
        this.getChildren().addAll(this.image, this.name, BoxBuilder.builder().withStyleClass("grade-box").withNodes(stackPane).buildHBox(), this.location);
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
        this.getChildren().addAll(this.unlockSeparator, this.unlockRequirementsTitle);
        this.getChildren().addAll(this.unlockRequirementsLabels);
        this.getStyleClass().add("engineer-card");
    }

    private void initEventHandling(final Engineer engineer) {
        EventService.addListener(this, EngineerEvent.class, engineerEvent -> {
            this.getChildren().removeAll(this.unlockSeparator, this.unlockRequirementsTitle);
            this.getChildren().removeAll(this.unlockRequirementsLabels);
            final Integer rank = APPLICATION_STATE.getEngineerRank(this.engineer);
            final String imageString = (rank > 0) ? "rank_" + rank.toString() : (APPLICATION_STATE.isEngineerInvited(this.engineer)) ? "invited" : "lock";
            final Integer engineerProgress = rank.equals(5) ? 100 : APPLICATION_STATE.getEngineerProgress(this.engineer);
            this.present.setValue(engineerProgress);
            this.notPresent.setValue(100 - engineerProgress);
            this.gradeIcon.setImage(ImageService.getImage("/images/ships/engineers/ranks/" + imageString + ".png"));
            if (APPLICATION_STATE.isEngineerUnlockedExact(engineer)) {
                this.image.setImage(ImageService.getImage("/images/engineer/" + engineer.name().toLowerCase() + ".jpg"));
            } else {
                this.image.setImage(ImageService.getImage("/images/engineer/locked.png"));
                this.unlockRequirementsLabels = getUnlockRequirements();
                this.getChildren().addAll(this.unlockSeparator, this.unlockRequirementsTitle);
                this.getChildren().addAll(this.unlockRequirementsLabels);
            }
        });
    }

    private DestroyableResizableImageView getEngineerGrade() {

        final Integer engineerRank = APPLICATION_STATE.getEngineerRank(this.engineer);
        final String imageString = (engineerRank > 0) ? "rank_" + engineerRank.toString() : (APPLICATION_STATE.isEngineerInvited(this.engineer)) ? "invited" : "lock";
        this.gradeIcon = ResizableImageViewBuilder.builder()
                .withStyleClass("grade-bar-image")
                .withImage("/images/ships/engineers/ranks/" + imageString + ".png")
                .build();
        return this.gradeIcon;
//        return BoxBuilder.builder()
//                .withStyleClass("specialisation-line")
//                .withNodes(new StackPane(this.gradeIcon))
//                .buildHBox();
    }


    private List<HBox> getBlueprints(final Map<HorizonsBlueprintName, Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>>> blueprints) {
        final Map<HorizonsBlueprintName, Integer> maxGrades = new HashMap<>();
        blueprints.values().stream()
                .flatMap(horizonsBlueprintTypeMapMap -> horizonsBlueprintTypeMapMap.values().stream())
                .flatMap(horizonsBlueprintGradeHorizonsBlueprintMap -> horizonsBlueprintGradeHorizonsBlueprintMap.values().stream())
                .filter(horizonsBlueprint -> horizonsBlueprint.getEngineers().contains(this.engineer))
                .sorted((Comparator.comparing((HorizonsBlueprint horizonsBlueprint) -> horizonsBlueprint.getHorizonsBlueprintGrade().getGrade()).reversed()))
                .forEach(horizonsBlueprint -> maxGrades.computeIfAbsent(horizonsBlueprint.getHorizonsBlueprintName(), horizonsBlueprintName -> horizonsBlueprint.getHorizonsBlueprintGrade().getGrade()));
        return getBlueprintLabels(maxGrades);
    }


    private ArrayList<HBox> getBlueprintLabels(final Map<HorizonsBlueprintName, Integer> maxGrades) {
        return maxGrades.entrySet().stream()
                .sorted(((Comparator<Map.Entry<HorizonsBlueprintName, Integer>>) (Comparator<?>) Map.Entry.comparingByValue().reversed()).thenComparing((entry) -> LocaleService.getLocalizedStringForCurrentLocale(entry.getKey().getLocalizationKey())))
                .map(entry -> BoxBuilder.builder()
                        .withNodes(LabelBuilder.builder()
                                        .withStyleClass("engineer-bullet")
                                        .withNonLocalizedText(entry.getValue().toString())
                                        .withOnMouseClicked(event -> EventService.publish(new BlueprintClickEvent(entry.getKey())))
                                        .build(),
                                LabelBuilder.builder()
                                        .withStyleClass("engineer-blueprint")
                                        .withText(LocaleService.getStringBinding(entry.getKey().getLocalizationKey()))
                                        .withOnMouseClicked(event -> EventService.publish(new BlueprintClickEvent(entry.getKey())))
                                        .build()).buildHBox())
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
