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
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterialType;
import nl.jixxed.eliteodysseymaterials.enums.Manufactured;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.service.HighGradeEmissionService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.MaterialService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.SegmentType;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegment;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegmentView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;
import org.controlsfx.control.SegmentedBar;

import java.util.*;
@Slf4j
public class HgeCategoryCard extends VBox implements Template {

    private DestroyableResizableImageView gradeImage;
    private DestroyableResizableImageView factionImage;
    private Label groupLabel;
    private Label nameLabel;
    private Label systemsLabel;
    private List<CopyableSystemLabel> systemLabels;
    private SegmentedBar<TypeSegment> segmentedBar;
    @Getter
    private final HorizonsMaterial material;
    private final HorizonsMaterialType materialType;
    private TypeSegment present;
    private TypeSegment notPresent;
    private final List<EventListener<?>> eventListeners = new ArrayList<>();

    HgeCategoryCard(final HorizonsMaterialType materialType) {
        this.materialType = materialType;
        this.material = Arrays.stream(Manufactured.materialsForType(materialType)).max(Comparator.comparing(mat -> mat.getRarity().getLevel())).orElseThrow();
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("hge-category-card");
        this.gradeImage = ResizableImageViewBuilder.builder().withStyleClass("hge-category-card-image").withImage(this.material.getRarity().getImagePath()).build();
        this.groupLabel = LabelBuilder.builder()
                .withStyleClass("hge-category-card-group")
                .withText(LocaleService.getStringBinding(this.materialType.getLocalizationKey()))
                .build();
        this.nameLabel = LabelBuilder.builder()
                .withStyleClass("hge-category-card-material")
                .withText(LocaleService.getStringBinding(this.material.getLocalizationKey()))
                .build();
        this.systemsLabel = LabelBuilder.builder()
                .withStyleClass("hge-category-card-systems")
                .withText(LocaleService.getStringBinding("hge.most.recently.found"))
                .build();
        this.systemLabels = new ArrayList<>();
        final CopyableSystemLabel first = new CopyableSystemLabel();
        final CopyableSystemLabel second = new CopyableSystemLabel();
        final CopyableSystemLabel third = new CopyableSystemLabel();
        this.systemLabels.add(first);
        this.systemLabels.add(second);
        this.systemLabels.add(third);
        updateLastFoundSystemLabels();


        final Integer materialCount = StorageService.getMaterialCount(this.material);
        final Integer maxAmount = this.material.getMaxAmount();
        this.segmentedBar = new SegmentedBar<>();
        this.segmentedBar.getStyleClass().add("hge-category-card-progressbar");
        this.segmentedBar.setOrientation(Orientation.HORIZONTAL);
        this.segmentedBar.setInfoNodeFactory(segment -> null);
        this.segmentedBar.setSegmentViewFactory(segment -> new TypeSegmentView(segment, Map.of(SegmentType.PRESENT, Color.web("#89d07f"), SegmentType.NOT_PRESENT, Color.web("#ff7c7c")), true));
        this.present = new TypeSegment(materialCount, SegmentType.PRESENT);
        this.notPresent = new TypeSegment(maxAmount - materialCount, SegmentType.NOT_PRESENT);
        this.segmentedBar.getSegments().addAll(this.present, this.notPresent);
        final HBox hBox = BoxBuilder.builder().withStyleClass("hge-category-card-textline").withNodes(this.gradeImage, this.nameLabel).buildHBox();

        this.getChildren().add(this.groupLabel);
        this.getChildren().add(hBox);
        this.getChildren().add(this.systemsLabel);
        this.getChildren().addAll(this.systemLabels);
        this.getChildren().add(BoxBuilder.builder().withNodes(first).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(second).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(third).buildHBox());
        HBox.setHgrow(first, Priority.NEVER);
        HBox.setHgrow(second, Priority.NEVER);
        HBox.setHgrow(third, Priority.NEVER);
        final Region region = new Region();
        VBox.setVgrow(region, Priority.ALWAYS);
        this.getChildren().add(region);
        this.getChildren().add(this.segmentedBar);
        MaterialService.addMaterialInfoPopOver(this, this.material, false);
        update();
    }

    private void updateLastFoundSystemLabels() {
        log.debug("udpating labels");
        final List<StarSystem> lastFoundSystems = HighGradeEmissionService.getLastFoundSystems(this.materialType);
        for (int i = 0; i < lastFoundSystems.size(); i++) {
            this.systemLabels.get(i).setStarSystem(lastFoundSystems.get(i));
        }
    }

    @Override
    public void initEventHandling() {
        this.eventListeners.add(EventService.addListener(this, StorageEvent.class, storageEvent -> {
            if (storageEvent.getStoragePool().equals(StoragePool.SHIP)) {
                update();
            }
        }));
        this.eventListeners.add(EventService.addListener(this, HighGradeEmissionLastFoundEvent.class, storageEvent -> {

            Platform.runLater(this::updateLastFoundSystemLabels);
        }));


        this.eventListeners.add(EventService.addStaticListener(HighGradeEmissionEvent.class, event -> {
            Platform.runLater(this::updateLastFoundSystemLabels);
        }));
    }

    private void update() {
        final Integer materialCount = StorageService.getMaterialCount(this.material);
        final Integer maxAmount = this.material.getMaxAmount();
        this.present.setValue(materialCount.equals(0) ? materialCount : Math.max(materialCount, this.material.getMaxAmount() / 7));
        this.present.setText(materialCount.toString());
        final Integer availableStorage = maxAmount - materialCount;
        this.notPresent.setValue(availableStorage.equals(0) ? availableStorage : Math.max(availableStorage, this.material.getMaxAmount() / 7));
        this.notPresent.setText(String.valueOf(availableStorage));
    }

//    private void update(final String search) {
//        if (search.isBlank() || !LocaleService.getLocalizedStringForCurrentLocale(this.material.getLocalizationKey()).toLowerCase(LocaleService.getCurrentLocale()).contains(search.toLowerCase(LocaleService.getCurrentLocale()))) {
//            this.nameLabel.getStyleClass().remove("horizons-materialcard-name-highlight");
//        } else if (!this.nameLabel.getStyleClass().contains("horizons-materialcard-name-highlight") && LocaleService.getLocalizedStringForCurrentLocale(this.material.getLocalizationKey()).toLowerCase(LocaleService.getCurrentLocale()).contains(search.toLowerCase(LocaleService.getCurrentLocale()))) {
//            this.nameLabel.getStyleClass().add("horizons-materialcard-name-highlight");
//        }
//    }


}
