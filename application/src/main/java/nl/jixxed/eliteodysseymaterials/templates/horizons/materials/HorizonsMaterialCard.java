package nl.jixxed.eliteodysseymaterials.templates.horizons.materials;

import javafx.beans.property.SimpleStringProperty;
import javafx.css.PseudoClass;
import javafx.geometry.Orientation;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.builder.SegmentedBarBuilder;
import nl.jixxed.eliteodysseymaterials.constants.SpawnConstants;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.MaterialService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsMaterialSearchEvent;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.SegmentType;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegment;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegmentView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.util.List;
import java.util.Map;

@Slf4j
public class HorizonsMaterialCard extends DestroyableVBox implements DestroyableEventTemplate {

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
        this.getStyleClass().add("material-card");
        if (GameVersion.LIVE.equals(this.material.getGameVersion())) {
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass("live"), true);
        }
        if (HorizonsMaterialType.THARGOID.equals(this.material.getMaterialType())) {
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass("thargoid"), true);
        } else if (HorizonsMaterialType.GUARDIAN.equals(this.material.getMaterialType())) {
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass("guardian"), true);
        }
        DestroyableResizableImageView gradeImage = ResizableImageViewBuilder.builder()
                .withStyleClass("image")
                .withImage(this.material.getRarity().getImagePath())
                .build();
        DestroyableLabel nameLabel = LabelBuilder.builder()
                .withStyleClass("name")
                .withText(this.material.getLocalizationKey())
                .build();
        DestroyableLabel categoryLabel = LabelBuilder.builder()
                .withStyleClass("category")
                .withText(this.material.getMaterialType().getLocalizationKey())
                .build();
        HBox.setHgrow(nameLabel, Priority.ALWAYS);
        final Integer materialCount = StorageService.getMaterialCount(this.material);
        final Integer maxAmount = this.material.getMaxAmount();
        this.present = new TypeSegment(Math.max(0D, materialCount), SegmentType.PRESENT);

        if (maxAmount - materialCount < 0) {
            log.error("Material count is higher than max amount for material: " + LocaleService.getLocalizedStringForCurrentLocale(this.material.getLocalizationKey()));
        }
        this.notPresent = new TypeSegment(Math.max(0D, (maxAmount - materialCount)), SegmentType.NOT_PRESENT);
        DestroyableSegmentedBar<TypeSegment> segmentedBar = SegmentedBarBuilder.builder(TypeSegment.class)
                .withSegments(this.present, this.notPresent)
                .withOrientation(Orientation.HORIZONTAL)
                .withInfoNodeFactory(_ -> null)
                .withSegmentViewFactory(segment ->
                        new TypeSegmentView(segment, Map.of(SegmentType.PRESENT, Color.web("#89d07f"), SegmentType.NOT_PRESENT, Color.web("#ff7c7c")), true))
                .build();
        final var nameAndCategory = BoxBuilder.builder()
                .withStyleClass("name-and-category")
                .withNodes(nameLabel, categoryLabel).buildVBox();
        final DestroyableHBox hBox = BoxBuilder.builder()
                .withStyleClass("text-line")
                .withNodes(gradeImage, nameAndCategory).buildHBox();
        VBox.setVgrow(hBox, Priority.ALWAYS);
        this.getNodes().add(hBox);
        this.getNodes().add(new GrowingRegion());
        this.getNodes().add(segmentedBar);
        MaterialService.addMaterialInfoPopOver(this, this.material, false);
        update();
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, StorageEvent.class, storageEvent -> {
            if (storageEvent.getStoragePool().equals(StoragePool.SHIP)) {
                update();
            }
        }));

        register(EventService.addListener(true, this, HorizonsMaterialSearchEvent.class, horizonsMaterialSearchEvent ->
                update(horizonsMaterialSearchEvent.getSearch().getQuery())));
    }

    private void update() {
        final Integer materialCount = StorageService.getMaterialCount(this.material);
        final Integer maxAmount = this.material.getMaxAmount();
        this.present.setValue(materialCount.equals(0) ? materialCount : Math.max(materialCount, this.material.getMaxAmount() / 6));
        this.present.addBinding(this.present.textProperty(), new SimpleStringProperty(materialCount.toString()));
        final Integer availableStorage = maxAmount - materialCount;
        this.notPresent.setValue(availableStorage.equals(0) ? availableStorage : Math.max(availableStorage, this.material.getMaxAmount() / 6));
        this.notPresent.addBinding(this.notPresent.textProperty(), new SimpleStringProperty(String.valueOf(availableStorage)));
    }

    private void update(final String search) {
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("highlight"), !search.isBlank() && searchForMaterial(search));
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
