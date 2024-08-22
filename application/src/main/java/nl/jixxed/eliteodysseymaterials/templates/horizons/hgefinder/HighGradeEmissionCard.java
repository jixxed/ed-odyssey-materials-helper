package nl.jixxed.eliteodysseymaterials.templates.horizons.hgefinder;

import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.domain.HgeStarSystem;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.HighGradeEmissionService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.SegmentType;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegment;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegmentView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;
import org.controlsfx.control.SegmentedBar;

import java.util.*;

@Slf4j
public class HighGradeEmissionCard extends VBox implements Template {

    private DestroyableResizableImageView groupImage;
    private DestroyableResizableImageView material1GradeImage;
    private Label material1NameLabel;
    private SegmentedBar<TypeSegment> material1SegmentedBar;
    private DestroyableResizableImageView material2GradeImage;
    private Label material2NameLabel;
    private SegmentedBar<TypeSegment> material2SegmentedBar;
    private Label groupLabel;
    private Label systemsLabel;
    private List<CopyableSystemPopoverLabel> systemLabels;
    @Getter
    private final HorizonsMaterial material1;
    @Getter
    private final Optional<HorizonsMaterial> material2;
    private final HorizonsMaterialType materialType;
    private TypeSegment material1Present;
    private TypeSegment material1NotPresent;
    private TypeSegment material2Present;
    private TypeSegment material2NotPresent;
    private final List<EventListener<?>> eventListeners = new ArrayList<>();

    HighGradeEmissionCard(final HorizonsMaterialType materialType) {
        this.materialType = materialType;
        this.material1 = Arrays.stream(Manufactured.materialsForType(materialType)).filter(manufactured -> manufactured.getRarity().equals(Rarity.VERY_RARE)).findFirst().orElseThrow();
        if (HorizonsMaterialType.COMPOSITE.equals(materialType) || HorizonsMaterialType.ALLOYS.equals(materialType)) {
            this.material2 = Optional.of(Arrays.stream(Manufactured.materialsForType(materialType)).filter(manufactured -> manufactured.getRarity().equals(Rarity.RARE)).findFirst().orElseThrow());
        } else {
            this.material2 = Optional.empty();
        }
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("hge-tracker-card");
        this.groupImage = ResizableImageViewBuilder.builder().withStyleClass("hge-tracker-card-group-image").withImage("/images/material/categories/"+ this.materialType.name().toLowerCase() + ".png").build();
        this.groupLabel = LabelBuilder.builder()
                .withStyleClass("hge-tracker-card-group")
                .withText(LocaleService.getStringBinding(this.materialType.getLocalizationKey()))
                .build();
        this.systemsLabel = LabelBuilder.builder()
                .withStyleClass("hge-tracker-card-systems")
                .withText(LocaleService.getStringBinding("hge.most.recently.found"))
                .build();
        this.systemLabels = new ArrayList<>();

        final HBox group = BoxBuilder.builder().withNodes(groupImage, this.groupLabel).buildHBox();
        this.getChildren().add(group);
        group.getChildren().add(new GrowingRegion());
        addMaterial1(group);

        this.material2.ifPresent(material -> {
            final HBox materials = BoxBuilder.builder().withStyleClass("hge-tracker-card-materials").buildHBox();
            materials.getChildren().add(new GrowingRegion());
            addMaterial2(materials);
            this.getChildren().add(materials);
        });
        this.getChildren().add(this.systemsLabel);
        VBox systemsLeft = BoxBuilder.builder().withStyleClass("hge-tracker-card-systems").buildVBox();
        VBox systemsRight = BoxBuilder.builder().withStyleClass("hge-tracker-card-systems").buildVBox();
        for (int i = 0; i < 5; i++) {
            createSystemLabel(systemsLeft);
            createSystemLabel(systemsRight);
        }
        updateLastFoundSystemLabels();
        this.getChildren().add(BoxBuilder.builder().withNodes(systemsLeft, systemsRight).buildHBox());

        final Region region = new Region();
        VBox.setVgrow(region, Priority.ALWAYS);
        this.getChildren().add(region);
        update();
    }

    private void createSystemLabel(VBox box) {
        final CopyableSystemPopoverLabel label = new CopyableSystemPopoverLabel();
        this.systemLabels.add(label);
        box.getChildren().add(BoxBuilder.builder()
                .withStyleClass("hge-tracker-card-system")
                .withNodes(label)
                .buildHBox());
        HBox.setHgrow(label, Priority.NEVER);
    }

    private void addMaterial1(HBox materials) {
        this.material1GradeImage = ResizableImageViewBuilder.builder().withStyleClass("hge-tracker-card-rarity-image").withImage(this.material1.getRarity().getImagePath()).build();
        this.material1NameLabel = LabelBuilder.builder()
                .withStyleClass("hge-tracker-card-material")
                .withText(LocaleService.getStringBinding(this.material1.getLocalizationKey()))
                .build();

        final Integer materialCount1 = StorageService.getMaterialCount(this.material1);
        final Integer maxAmount1 = this.material1.getMaxAmount();
        this.material1SegmentedBar = new SegmentedBar<>();
        this.material1SegmentedBar.getStyleClass().add("hge-tracker-card-progressbar");
        this.material1SegmentedBar.setOrientation(Orientation.HORIZONTAL);
        this.material1SegmentedBar.setInfoNodeFactory(segment -> null);
        this.material1SegmentedBar.setSegmentViewFactory(segment -> new TypeSegmentView(segment, Map.of(SegmentType.PRESENT, Color.web("#89d07f"), SegmentType.NOT_PRESENT, Color.web("#ff7c7c")), true));
        this.material1Present = new TypeSegment(materialCount1, SegmentType.PRESENT);
        this.material1NotPresent = new TypeSegment(maxAmount1 - materialCount1, SegmentType.NOT_PRESENT);
        this.material1SegmentedBar.getSegments().addAll(this.material1Present, this.material1NotPresent);
        final HBox material1HBox = BoxBuilder.builder().withStyleClass("hge-tracker-card-textline").withNodes(this.material1GradeImage, this.material1NameLabel).buildHBox();
        materials.getChildren().add(BoxBuilder.builder().withNodes(material1HBox, this.material1SegmentedBar).buildVBox());
    }

    private void addMaterial2(HBox materials) {
        this.material2.ifPresent(material -> {
            this.material2GradeImage = ResizableImageViewBuilder.builder().withStyleClass("hge-tracker-card-rarity-image").withImage(material.getRarity().getImagePath()).build();
            this.material2NameLabel = LabelBuilder.builder()
                    .withStyleClass("hge-tracker-card-material")
                    .withText(LocaleService.getStringBinding(material.getLocalizationKey()))
                    .build();
            final Integer materialCount = StorageService.getMaterialCount(material);
            final Integer maxAmount = material.getMaxAmount();
            this.material2SegmentedBar = new SegmentedBar<>();
            this.material2SegmentedBar.getStyleClass().add("hge-tracker-card-progressbar");
            this.material2SegmentedBar.setOrientation(Orientation.HORIZONTAL);
            this.material2SegmentedBar.setInfoNodeFactory(segment -> null);
            this.material2SegmentedBar.setSegmentViewFactory(segment -> new TypeSegmentView(segment, Map.of(SegmentType.PRESENT, Color.web("#89d07f"), SegmentType.NOT_PRESENT, Color.web("#ff7c7c")), true));
            this.material2Present = new TypeSegment(materialCount, SegmentType.PRESENT);
            this.material2NotPresent = new TypeSegment(maxAmount - materialCount, SegmentType.NOT_PRESENT);
            this.material2SegmentedBar.getSegments().addAll(this.material2Present, this.material2NotPresent);
            final HBox material2HBox = BoxBuilder.builder().withStyleClass("hge-tracker-card-textline").withNodes(this.material2GradeImage, this.material2NameLabel).buildHBox();
            materials.getChildren().add(BoxBuilder.builder().withNodes(material2HBox, this.material2SegmentedBar).buildVBox());
        });
    }

    private void updateLastFoundSystemLabels() {
        final List<HgeStarSystem> lastFoundSystems = HighGradeEmissionService.getLastFoundSystems(this.materialType);
        for (int i = 0; i < lastFoundSystems.size(); i++) {
            this.systemLabels.get(i).setStarSystem(lastFoundSystems.get(i), this.materialType);
        }
    }

    @Override
    public void initEventHandling() {
        this.eventListeners.add(EventService.addListener(true, this, StorageEvent.class, storageEvent -> {
            if (storageEvent.getStoragePool().equals(StoragePool.SHIP)) {
                update();
            }
        }));

        this.eventListeners.add(EventService.addListener(true, this, HighGradeEmissionLastFoundEvent.class, event -> {
            Platform.runLater(this::updateLastFoundSystemLabels);
        }));

        this.eventListeners.add(EventService.addListener(true, this, HighGradeEmissionReceivedEvent.class, event -> {
            Platform.runLater(this::updateLastFoundSystemLabels);
        }));
    }

    private void update() {
        final Integer materialCount = StorageService.getMaterialCount(this.material1);
        final Integer maxAmount = this.material1.getMaxAmount();
        this.material1Present.setValue(materialCount.equals(0) ? materialCount : Math.max(materialCount, this.material1.getMaxAmount() / 7));
        this.material1Present.setText(materialCount.toString());
        final Integer availableStorage = maxAmount - materialCount;
        this.material1NotPresent.setValue(availableStorage.equals(0) ? availableStorage : Math.max(availableStorage, this.material1.getMaxAmount() / 7));
        this.material1NotPresent.setText(String.valueOf(availableStorage));
        material2.ifPresent(material -> {
            final Integer materialCount2 = StorageService.getMaterialCount(material);
            final Integer maxAmount2 = material.getMaxAmount();
            this.material2Present.setValue(materialCount2.equals(0) ? materialCount2 : Math.max(materialCount2, material.getMaxAmount() / 7));
            this.material2Present.setText(materialCount2.toString());
            final Integer availableStorage2 = maxAmount2 - materialCount2;
            this.material2NotPresent.setValue(availableStorage2.equals(0) ? availableStorage2 : Math.max(availableStorage2, material.getMaxAmount() / 7));
            this.material2NotPresent.setText(String.valueOf(availableStorage2));
        });
    }

//    private void update(final String search) {
//        if (search.isBlank() || !LocaleService.getLocalizedStringForCurrentLocale(this.material.getLocalizationKey()).toLowerCase(LocaleService.getCurrentLocale()).contains(search.toLowerCase(LocaleService.getCurrentLocale()))) {
//            this.nameLabel.getStyleClass().remove("horizons-materialcard-name-highlight");
//        } else if (!this.nameLabel.getStyleClass().contains("horizons-materialcard-name-highlight") && LocaleService.getLocalizedStringForCurrentLocale(this.material.getLocalizationKey()).toLowerCase(LocaleService.getCurrentLocale()).contains(search.toLowerCase(LocaleService.getCurrentLocale()))) {
//            this.nameLabel.getStyleClass().add("horizons-materialcard-name-highlight");
//        }
//    }


}
