package nl.jixxed.eliteodysseymaterials.templates.horizons.materials;

import javafx.beans.property.SimpleStringProperty;
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
import nl.jixxed.eliteodysseymaterials.constants.SpawnConstants;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.MaterialService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsMaterialSearchEvent;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.SegmentType;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegment;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegmentView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;
import org.controlsfx.control.SegmentedBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Slf4j
public class HorizonsMaterialCard extends VBox implements Template {

    private DestroyableResizableImageView gradeImage;
    private Label nameLabel;
    private SegmentedBar<TypeSegment> segmentedBar;
    @Getter
    private final HorizonsMaterial material;
    private TypeSegment present;
    private TypeSegment notPresent;
    private final List<EventListener<?>> eventListeners = new ArrayList<>();

    HorizonsMaterialCard(final HorizonsMaterial material) {
        this.material = material;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("horizons-materialcard");
        if(GameVersion.LIVE.equals(this.material.getGameVersion())){
            this.getStyleClass().add("horizons-materialcard-live");
        }
        if (HorizonsMaterialType.THARGOID.equals(this.material.getMaterialType())) {
            this.getStyleClass().add("horizons-materialcard-thargoid");
            if(GameVersion.LIVE.equals(this.material.getGameVersion())){
                this.getStyleClass().add("horizons-materialcard-thargoid-live");
            }
        } else if (HorizonsMaterialType.GUARDIAN.equals(this.material.getMaterialType())) {
            this.getStyleClass().add("horizons-materialcard-guardian");
            if(GameVersion.LIVE.equals(this.material.getGameVersion())){
                this.getStyleClass().add("horizons-materialcard-guardian-live");
            }
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
        this.present = new TypeSegment(Math.max(0D, materialCount), SegmentType.PRESENT);

        if(maxAmount - materialCount < 0){
            log.error("Material count is higher than max amount for material: " + LocaleService.getLocalizedStringForCurrentLocale(this.material.getLocalizationKey()));
        }
        this.notPresent = new TypeSegment(Math.max(0D, (maxAmount - materialCount)), SegmentType.NOT_PRESENT);
        this.segmentedBar.getSegments().addAll(this.present, this.notPresent);
        final HBox hBox = BoxBuilder.builder().withStyleClass("horizons-materialcard-textline").withNodes(this.gradeImage, this.nameLabel).buildHBox();
        this.getChildren().add(hBox);
        final Region region = new Region();
        VBox.setVgrow(region, Priority.ALWAYS);
        this.getChildren().add(region);
        this.getChildren().add(this.segmentedBar);
        MaterialService.addMaterialInfoPopOver(this, this.material, false);
        update();
    }

    @Override
    public void initEventHandling() {
        this.eventListeners.add(EventService.addListener(true, this, StorageEvent.class, storageEvent -> {
            if (storageEvent.getStoragePool().equals(StoragePool.SHIP)) {
                update();
            }
        }));

        this.eventListeners.add(EventService.addListener(true, this, HorizonsMaterialSearchEvent.class, horizonsMaterialSearchEvent -> {
            update(horizonsMaterialSearchEvent.getSearch().getQuery());
        }));
    }

    private void update() {
        final Integer materialCount = StorageService.getMaterialCount(this.material);
        final Integer maxAmount = this.material.getMaxAmount();
        this.present.setValue(materialCount.equals(0) ? materialCount : Math.max(materialCount, this.material.getMaxAmount() / 7));
        this.present.textProperty().bind(new SimpleStringProperty(materialCount.toString()));
        final Integer availableStorage = maxAmount - materialCount;
        this.notPresent.setValue(availableStorage.equals(0) ? availableStorage : Math.max(availableStorage, this.material.getMaxAmount() / 7));
        this.notPresent.textProperty().bind(new SimpleStringProperty(String.valueOf(availableStorage)));
    }

    private void update(final String search) {
        if (search.isBlank() || !searchForMaterial(search)) {
            this.nameLabel.getStyleClass().remove("horizons-materialcard-name-highlight");
        } else if (!this.nameLabel.getStyleClass().contains("horizons-materialcard-name-highlight") && searchForMaterial(search)) {
            this.nameLabel.getStyleClass().add("horizons-materialcard-name-highlight");
        }
    }

    private boolean searchForMaterial(final String search) {
        return LocaleService.getLocalizedStringForCurrentLocale(this.material.getLocalizationKey()).toLowerCase(LocaleService.getCurrentLocale()).contains(search.toLowerCase(LocaleService.getCurrentLocale()))
                || searchForSpawnLocation(search);
    }

    private boolean searchForSpawnLocation(final String search) {
        final List<HorizonsMaterialSpawnLocation> horizonsMaterialSpawnLocations = SpawnConstants.HORIZONSMATERIAL_LOCATION.get(this.material);
        if (horizonsMaterialSpawnLocations != null && !horizonsMaterialSpawnLocations.isEmpty()) {
            return horizonsMaterialSpawnLocations.stream().map(horizonsMaterialSpawnLocation -> LocaleService.getLocalizedStringForCurrentLocale(horizonsMaterialSpawnLocation.getLocalizationKey())).anyMatch(spawn -> spawn.toLowerCase().contains(search.toLowerCase(LocaleService.getCurrentLocale())));
        }
        return false;
    }

}
