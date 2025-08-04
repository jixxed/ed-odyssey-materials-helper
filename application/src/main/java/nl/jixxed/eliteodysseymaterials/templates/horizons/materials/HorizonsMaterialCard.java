package nl.jixxed.eliteodysseymaterials.templates.horizons.materials;

import javafx.beans.property.SimpleStringProperty;
import javafx.css.PseudoClass;
import javafx.geometry.Orientation;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.builder.SegmentedBarBuilder;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.constants.SpawnConstants;
import nl.jixxed.eliteodysseymaterials.domain.IntegerRange;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.*;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.components.EdAwesomeIconViewPane;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIcon;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIconView;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.SegmentType;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegment;
import nl.jixxed.eliteodysseymaterials.templates.components.segmentbar.TypeSegmentView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.util.List;

@Slf4j
public class HorizonsMaterialCard extends DestroyableVBox implements DestroyableEventTemplate {

    @Getter
    private final HorizonsMaterial material;
    private TypeSegment present;
    private TypeSegment notPresent;
    private EdAwesomeIconViewPane wishlistImage;
    private DestroyableLabel materialCount;
    private DestroyableLabel storageSpace;

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
        EdAwesomeIconView icon = new EdAwesomeIconView(getGrade(this.material.getRarity()));
        icon.getStyleClass().add("icon");
        wishlistImage = new EdAwesomeIconViewPane(new EdAwesomeIconView(EdAwesomeIcon.WISHLIST));
        wishlistImage.getStyleClass().add("wishlist-icon");
//        wishlistImage = ResizableImageViewBuilder.builder()
//                .withStyleClasses("wishlist-image")
//                .withImage("/images/material/wishlist.png")
//                .build();
        DestroyableLabel nameLabel = LabelBuilder.builder()
                .withStyleClass("name")
                .withText(this.material.getLocalizationKey())
                .build();
        DestroyableLabel categoryLabel = LabelBuilder.builder()
                .withStyleClass("category")
                .withText(this.material.getMaterialType().getLocalizationKey())
                .build();
        DestroyableLabel materialCountLabel = LabelBuilder.builder()
                .withStyleClass("count-label")
                .withText("quantity.header.collected")
                .build();
        DestroyableLabel storageSpaceLabel = LabelBuilder.builder()
                .withStyleClass("storage-label")
                .withText("quantity.header.free")
                .build();
        materialCount = LabelBuilder.builder()
                .withStyleClass("count")
                .withNonLocalizedText("0")
                .build();
        storageSpace = LabelBuilder.builder()
                .withStyleClass("storage")
                .withNonLocalizedText("0")
                .build();
        HBox.setHgrow(nameLabel, Priority.ALWAYS);
        final Integer count = StorageService.getMaterialCount(this.material);
        final Integer maxAmount = this.material.getMaxAmount();
        this.present = new TypeSegment(Math.max(0D, count), SegmentType.PRESENT);

        if (maxAmount - count < 0) {
            log.error("Material count is higher than max amount for material: " + LocaleService.getLocalizedStringForCurrentLocale(this.material.getLocalizationKey()));
        }
        this.notPresent = new TypeSegment(Math.max(0D, (maxAmount - count)), SegmentType.NOT_PRESENT);
        DestroyableSegmentedBar<TypeSegment> segmentedBar = SegmentedBarBuilder.builder(TypeSegment.class)
                .withSegments(this.present, this.notPresent)
                .withOrientation(Orientation.HORIZONTAL)
                .withInfoNodeFactory(_ -> null)
                .withSegmentViewFactory(segment ->
                        new TypeSegmentView(segment, false))
                .build();
        final var images = BoxBuilder.builder()
                .withStyleClass("images")
                .withNodes(icon).buildVBox();
        final var nameAndCategory = BoxBuilder.builder()
                .withStyleClass("name-and-category")
                .withNodes(nameLabel, categoryLabel).buildVBox();
        final DestroyableHBox hBox = BoxBuilder.builder()
                .withStyleClass("text-line")
                .withNodes(images, nameAndCategory, wishlistImage).buildHBox();
        VBox.setVgrow(hBox, Priority.ALWAYS);
        this.getNodes().add(hBox);
        this.getNodes().add(new GrowingRegion());
        DestroyableHBox quantityLine = BoxBuilder.builder()
                .withStyleClass("quantity-line")
                .withNodes(materialCountLabel, materialCount, new GrowingRegion(), storageSpace, storageSpaceLabel)
                .buildHBox();
        this.getNodes().add(quantityLine);
        this.getNodes().add(segmentedBar);
        MaterialService.addMaterialInfoPopOver(this, this.material, false);
        update();
    }

    private EdAwesomeIcon getGrade(Rarity rarity) {
        return switch (rarity) {
            case VERY_COMMON -> EdAwesomeIcon.GRADE_1;
            case COMMON -> EdAwesomeIcon.GRADE_2;
            case STANDARD -> EdAwesomeIcon.GRADE_3;
            case RARE -> EdAwesomeIcon.GRADE_4;
            case VERY_RARE -> EdAwesomeIcon.GRADE_5;
            default -> throw new IllegalStateException("Unexpected value: " + rarity);
        };
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, StorageEvent.class, storageEvent -> {
            if (storageEvent.getStoragePool().equals(StoragePool.SHIP)) {
                update();
            }
        }));

        register(EventService.addListener(true, this, HorizonsWishlistChangedEvent.class, _ -> update()));

        register(EventService.addListener(true, this, HorizonsWishlistSelectedEvent.class, _ -> update()));

        register(EventService.addListener(true, this, HorizonsMaterialSearchEvent.class, horizonsMaterialSearchEvent ->
                update(horizonsMaterialSearchEvent.getSearch().getQuery())));

        register(EventService.addListener(true, this, CollectorModeEvent.class, collectorModeEvent -> updateStyle(collectorModeEvent.isEnabled())));
    }

    private void update() {
        final Integer count = StorageService.getMaterialCount(this.material);
        final Integer maxAmount = this.material.getMaxAmount();
        this.present.setValue(count.equals(0) ? count : Math.max(count, this.material.getMaxAmount() / 6));
        this.present.addBinding(this.present.textProperty(), new SimpleStringProperty(count.toString()));
        final Integer availableStorage = maxAmount - count;
        this.notPresent.setValue(availableStorage.equals(0) ? availableStorage : Math.max(availableStorage, this.material.getMaxAmount() / 6));
        this.notPresent.addBinding(this.notPresent.textProperty(), new SimpleStringProperty(String.valueOf(availableStorage)));
        materialCount.setText(count.toString());
        storageSpace.setText(String.valueOf(maxAmount - count));
        Integer currentWishlistCount = WishlistService.getCurrentWishlistCount(material).max();
        if (currentWishlistCount > 0) {
            this.wishlistImage.setVisible(true);
            this.wishlistImage.setManaged(true);
        } else {
            this.wishlistImage.setVisible(false);
            this.wishlistImage.setManaged(false);
        }
        updateStyle(PreferencesService.getPreference(PreferenceConstants.COLLECTOR_MODE, false));
    }

    private void updateStyle(boolean enabled) {
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("collector"), enabled && StorageService.getMaterialCount(this.material) < this.material.getMaxAmount());
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
            return horizonsMaterialSpawnLocations.stream()
                    .anyMatch(horizonsMaterialSpawnLocation ->
                            LocaleService.getLocalizedStringForCurrentLocale(horizonsMaterialSpawnLocation.getLocalizationKey()).toLowerCase().contains(search.toLowerCase(LocaleService.getCurrentLocale()))
                                    || (horizonsMaterialSpawnLocation.getLocations() != null && horizonsMaterialSpawnLocation.getLocations().stream()
                                    .anyMatch(location -> location.getStarSystem().getName().contains(search.toLowerCase(LocaleService.getCurrentLocale()))
                                            || location.getBody().toLowerCase(LocaleService.getCurrentLocale()).contains(search.toLowerCase(LocaleService.getCurrentLocale())))));
        }
        return false;
    }

}
