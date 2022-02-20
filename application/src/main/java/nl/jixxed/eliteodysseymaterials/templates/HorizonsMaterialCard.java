package nl.jixxed.eliteodysseymaterials.templates;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterialType;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;
import org.controlsfx.control.SegmentedBar;

public class HorizonsMaterialCard extends VBox implements Template {

    private DestroyableResizableImageView gradeImage;
    private Label nameLabel;
    private SegmentedBar<TypeSegment> segmentedBar;
    private final HorizonsMaterial material;
    private TypeSegment present;
    private TypeSegment notPresent;

    HorizonsMaterialCard(final HorizonsMaterial material) {
        this.material = material;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("horizons-materialcard");
        if (HorizonsMaterialType.THARGOID.equals(this.material.getMaterialType())) {
            this.getStyleClass().add("horizons-materialcard-thargoid");
        } else if (HorizonsMaterialType.GUARDIAN.equals(this.material.getMaterialType())) {
            this.getStyleClass().add("horizons-materialcard-guardian");
        }
        this.gradeImage = ResizableImageViewBuilder.builder().withStyleClass("horizons-materialcard-image").withImage(this.material.getRarity().getImagePath()).build();
        this.nameLabel = LabelBuilder.builder()
                .withStyleClass("horizons-materialcard-name")
                .withText(LocaleService.getStringBinding(this.material.getLocalizationKey()))
                .build();


        final Integer materialCount = StorageService.getMaterialCount(this.material);
        final Integer maxAmount = this.material.getMaxAmount();
        this.segmentedBar = new SegmentedBar<>();
        this.segmentedBar.setOrientation(Orientation.HORIZONTAL);
        this.segmentedBar.setInfoNodeFactory(segment -> null);
        this.segmentedBar.setSegmentViewFactory(TypeSegmentView::new);
        this.present = new TypeSegment(materialCount, SegmentType.PRESENT);
        this.notPresent = new TypeSegment(maxAmount - materialCount, SegmentType.NOT_PRESENT);
        this.segmentedBar.getSegments().addAll(this.present, this.notPresent);
        final HBox hBox = BoxBuilder.builder().withStyleClass("horizons-materialcard-textline").withNodes(this.gradeImage, this.nameLabel).buildHBox();
        this.getChildren().add(hBox);
        final Region region = new Region();
        VBox.setVgrow(region, Priority.ALWAYS);
        this.getChildren().add(region);
        this.getChildren().add(this.segmentedBar);
    }

    @Override
    public void initEventHandling() {
        EventService.addListener(this, StorageEvent.class, storageEvent -> {
            if (storageEvent.getStoragePool().equals(StoragePool.SHIP)) {
                final Integer materialCount = StorageService.getMaterialCount(this.material);
                final Integer maxAmount = this.material.getMaxAmount();
                this.present.setValue(materialCount.equals(0) ? materialCount : Math.max(materialCount, this.material.getMaxAmount() / 7));
                this.present.setText(materialCount.toString());
                final Integer availableStorage = maxAmount - materialCount;
                this.notPresent.setValue(availableStorage.equals(0) ? availableStorage : Math.max(availableStorage, this.material.getMaxAmount() / 7));
                this.notPresent.setText(String.valueOf(availableStorage));
            }
        });
    }

    class TypeSegmentView extends StackPane {
        private final Label label;

        TypeSegmentView(final TypeSegment segment) {
            this.label = LabelBuilder.builder().build();
            this.label.textProperty().bind(segment.textProperty());
            this.label.getStyleClass().add("horizons-materialcard-amount");
            this.setStyle(SegmentType.PRESENT.equals(segment.getSegmentType()) ? "-fx-background-color: #89d07f;" : "-fx-background-color:  #ff7c7c;");
            this.setPadding(new Insets(5.0));
            this.setPrefHeight(30.0);
            this.getChildren().add(this.label);
            this.label.visibleProperty().bind(segment.textProperty().isNotEqualTo("0"));
        }
    }

    class TypeSegment extends SegmentedBar.Segment {
        @Getter
        final SegmentType segmentType;

        TypeSegment(final Integer materialCount, final SegmentType segmentType) {
            super(materialCount, materialCount.toString());
            this.segmentType = segmentType;
        }
    }

    private enum SegmentType {
        PRESENT, NOT_PRESENT
    }
}
