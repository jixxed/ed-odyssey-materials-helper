package nl.jixxed.eliteodysseymaterials.templates.horizons;

import javafx.beans.binding.StringBinding;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.MaterialService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.JournalLineProcessedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import nl.jixxed.eliteodysseymaterials.templates.generic.Ingredient;
import nl.jixxed.eliteodysseymaterials.templates.horizons.wishlist.HorizonsWishlistIngredient;

@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class HorizonsMaterialIngredient extends Ingredient implements DestroyableComponent, DestroyableEventTemplate {
    private static final String INGREDIENT_WITH_AMOUNT_CLASS = "ingredient-with-amount";
    private static final String INGREDIENT_FILLED_CLASS = "ingredient-filled";
    private static final String INGREDIENT_UNFILLED_CLASS = "ingredient-unfilled";
    private static final String INGREDIENT_FILLED_NOT_SHIPLOCKER_CLASS = "ingredient-filled-partial";
    @EqualsAndHashCode.Include
    private final HorizonsStorageType storageType;
    @Getter
    @EqualsAndHashCode.Include
    private final HorizonsMaterial horizonsMaterial;
    @Getter
    @Setter
    private Integer leftAmount;
    private Integer rightAmount;

    private DestroyableLabel nameLabel;
    private DestroyableResizableImageView image;
    private DestroyableLabel leftAmountLabel;
    private DestroyableLabel rightAmountLabel;
    private DestroyableLabel leftDescriptionLabel;
    private DestroyableLabel rightDescriptionLabel;
    private DestroyableHBox leftHBox;
    private DestroyableHBox rightHBox;
    private DestroyableHBox firstLine;
    private DestroyableHBox secondLine;

    public HorizonsMaterialIngredient(final HorizonsStorageType storageType, final HorizonsMaterial horizonsMaterial, final Integer leftAmount, final Integer rightAmount) {
        if (storageType.equals(HorizonsStorageType.OTHER)) {
            throw new IllegalArgumentException("StorageType Other must use MissionIngredient class");
        }
        this.storageType = storageType;
        this.leftAmount = leftAmount;
        this.horizonsMaterial = horizonsMaterial;
        this.rightAmount = rightAmount;
        initComponents();
        initEventHandling();
    }

    public void initEventHandling() {
        register(EventService.addListener(true, this, JournalLineProcessedEvent.class, journalProcessedEvent -> this.update()));
        register(EventService.addListener(true, this, StorageEvent.class, evt -> {
            if (evt.getStoragePool().equals(StoragePool.SHIP)) {
                this.update();
            }
        }));
    }

    public void initComponents() {
        this.nameLabel = LabelBuilder.builder()
                .withStyleClass("ingredient-name")
                .withText(this.horizonsMaterial.getLocalizationKey())
                .build();
        initImage();

        final Boolean showRemaining = PreferencesService.getPreference(PreferenceConstants.FLIP_WISHLIST_REMAINING_AVAILABLE_HORIZONS, Boolean.FALSE);
        this.leftAmountLabel = LabelBuilder.builder()
                .withStyleClass("ingredient-required")
                .build();
        this.rightAmountLabel = LabelBuilder.builder()
                .withStyleClass("ingredient-available")
                .build();
        this.leftDescriptionLabel = LabelBuilder.builder()
                .withStyleClass("ingredient-quantity-label")
                .withText("blueprint.header.required")
                .build();
        this.rightDescriptionLabel = LabelBuilder.builder()
                .withStyleClass("ingredient-quantity-label")
                .withText(Boolean.TRUE.equals(showRemaining && this instanceof HorizonsWishlistIngredient) ? "blueprint.header.remaining" : "blueprint.header.available")
                .build();

        this.leftHBox = BoxBuilder.builder()
                .withNodes(this.leftDescriptionLabel, this.leftAmountLabel)
                .withStyleClass("ingredient-quantity-section").buildHBox();
        this.rightHBox = BoxBuilder.builder()
                .withNodes(this.rightAmountLabel, this.rightDescriptionLabel)
                .withStyleClass("ingredient-quantity-section").buildHBox();
        this.leftAmountLabel.setText(getLeftAmountString());
        HBox.setHgrow(this.leftAmountLabel, Priority.ALWAYS);
        this.rightAmountLabel.setText(getRightAmountString());

        this.firstLine = BoxBuilder.builder()
                .withNodes(this.image, this.nameLabel)
                .buildHBox();
        this.firstLine.addBinding(this.firstLine.prefHeightProperty(), this.nameLabel.heightProperty());
        this.secondLine = BoxBuilder.builder()
                .withNodes(this.leftHBox, new GrowingRegion(), this.rightHBox)
                .buildHBox();
        this.getNodes().addAll(this.firstLine, new GrowingRegion(), this.secondLine);

        installPopOver();
        this.getStyleClass().add("ingredient");

        update();
    }

    protected String getRightAmountString() {
        return this.rightAmount.toString();
    }

    protected String getLeftAmountString() {
        return this.leftAmount.toString();
    }

    protected void installPopOver() {
        MaterialService.addMaterialInfoPopOver(this, this.horizonsMaterial, false);
    }

    @SuppressWarnings("java:S6205")
    private void initImage() {
        if (this.horizonsMaterial instanceof Commodity commodity) {
            this.image = ResizableImageViewBuilder.builder()
                    .withStyleClass("horizons-materialcard-image")
                    .withImage(commodity.getCommodityType().getImagePath())
                    .build();
        } else {
            this.image = ResizableImageViewBuilder.builder()
                    .withStyleClass("horizons-materialcard-image")
                    .withImage(this.horizonsMaterial.getRarity().getImagePath())
                    .build();
        }
    }

    protected void setRightAmount(final Integer rightAmount) {
        this.rightAmount = rightAmount;
    }


    protected void update() {
        final Integer materialCountShip;
        final Integer materialCountBoth;
        if (this.horizonsMaterial instanceof Commodity commodity) {
            materialCountShip = StorageService.getCommodityCount(commodity, StoragePool.SHIP);
            materialCountBoth = materialCountShip + StorageService.getCommodityCount(commodity, StoragePool.FLEETCARRIER);
        } else {
            materialCountBoth = StorageService.getMaterialCount(this.horizonsMaterial);
            materialCountShip = materialCountBoth;
        }
        setRightAmount(materialCountBoth);
        this.rightAmountLabel.setText(getRightAmountString());
        this.getStyleClass().removeAll(INGREDIENT_WITH_AMOUNT_CLASS, INGREDIENT_FILLED_CLASS, INGREDIENT_UNFILLED_CLASS, INGREDIENT_FILLED_NOT_SHIPLOCKER_CLASS);
        if (materialCountBoth >= this.leftAmount && materialCountShip < this.leftAmount) {
            this.getStyleClass().addAll(INGREDIENT_WITH_AMOUNT_CLASS, INGREDIENT_FILLED_NOT_SHIPLOCKER_CLASS);
        } else if (materialCountBoth >= this.leftAmount) {
            this.getStyleClass().addAll(INGREDIENT_WITH_AMOUNT_CLASS, INGREDIENT_FILLED_CLASS);
        } else {
            this.getStyleClass().addAll(INGREDIENT_WITH_AMOUNT_CLASS, INGREDIENT_UNFILLED_CLASS);
        }
    }

    @Override
    public StorageType getType() {
        return this.storageType;
    }

    @Override
    public String getName() {
        return this.nameLabel.getText();
    }

    protected Label getLeftAmountLabel() {
        return this.leftAmountLabel;
    }

    protected Integer getRightAmount() {
        return this.rightAmount;
    }

    protected Label getRightAmountLabel() {
        return this.rightAmountLabel;
    }

    protected void setRightDescriptionLabel(final StringBinding rightDescriptionLabel) {
        this.rightDescriptionLabel.addBinding(this.rightDescriptionLabel.textProperty(), rightDescriptionLabel);
    }

}
