package nl.jixxed.eliteodysseymaterials.templates.horizons.materials;

import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterialType;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.MaterialService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsMaterialSearchEvent;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.SegmentType;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegment;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegmentView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;
import org.controlsfx.control.SegmentedBar;

import java.util.Map;

public class HorizonsMaterialCard extends VBox implements Template {

    private DestroyableResizableImageView gradeImage;
    private Label nameLabel;
    private SegmentedBar<TypeSegment> segmentedBar;
    @Getter
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
        this.segmentedBar.setSegmentViewFactory(segment -> new TypeSegmentView(segment, Map.of(SegmentType.PRESENT, Color.web("#89d07f"), SegmentType.NOT_PRESENT, Color.web("#ff7c7c")), true));
        this.present = new TypeSegment(materialCount, SegmentType.PRESENT);
        this.notPresent = new TypeSegment(maxAmount - materialCount, SegmentType.NOT_PRESENT);
        this.segmentedBar.getSegments().addAll(this.present, this.notPresent);
        final HBox hBox = BoxBuilder.builder().withStyleClass("horizons-materialcard-textline").withNodes(this.gradeImage, this.nameLabel).buildHBox();
        this.getChildren().add(hBox);
        final Region region = new Region();
        VBox.setVgrow(region, Priority.ALWAYS);
        this.getChildren().add(region);
        this.getChildren().add(this.segmentedBar);
        MaterialService.addMaterialInfoPopOver(this, this.material);
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

        EventService.addListener(this, HorizonsMaterialSearchEvent.class, horizonsMaterialSearchEvent -> {
            update(horizonsMaterialSearchEvent.getSearch());
        });
    }

    private void update(final String search) {
        if (search.isBlank() || !LocaleService.getLocalizedStringForCurrentLocale(this.material.getLocalizationKey()).toLowerCase(LocaleService.getCurrentLocale()).contains(search.toLowerCase(LocaleService.getCurrentLocale()))) {
            this.nameLabel.getStyleClass().remove("horizons-materialcard-name-highlight");
        } else if (!this.nameLabel.getStyleClass().contains("horizons-materialcard-name-highlight") && LocaleService.getLocalizedStringForCurrentLocale(this.material.getLocalizationKey()).toLowerCase(LocaleService.getCurrentLocale()).contains(search.toLowerCase(LocaleService.getCurrentLocale()))) {
            this.nameLabel.getStyleClass().add("horizons-materialcard-name-highlight");
        }
    }


}
