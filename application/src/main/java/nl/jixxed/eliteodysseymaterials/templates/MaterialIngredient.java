package nl.jixxed.eliteodysseymaterials.templates;

import javafx.beans.binding.StringBinding;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import lombok.EqualsAndHashCode;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.enums.Asset;
import nl.jixxed.eliteodysseymaterials.enums.Material;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyStorageType;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.MaterialService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.JournalLineProcessedEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class MaterialIngredient extends Ingredient {
    private static final String INGREDIENT_WITH_AMOUNT_CLASS = "ingredient-with-amount";
    private static final String INGREDIENT_FILLED_CLASS = "ingredient-filled";
    private static final String INGREDIENT_UNFILLED_CLASS = "ingredient-unfilled";
    @EqualsAndHashCode.Include
    private final OdysseyStorageType storageType;
    @EqualsAndHashCode.Include
    private final Material material;
    private final Integer leftAmount;
    private Integer rightAmount;

    private Label nameLabel;
    private DestroyableResizableImageView image;
    private Label leftAmountLabel;
    private Label rightAmountLabel;
    private Label leftDescriptionLabel;
    private Label rightDescriptionLabel;
    private HBox leftHBox;
    private HBox rightHBox;
    private HBox firstLine;
    private Region region;
    private HBox secondLine;
    private Region region2;
    List<EventListener<?>> eventListeners = new ArrayList<>();


    MaterialIngredient(final OdysseyStorageType storageType, final Material material, final Integer leftAmount, final Integer rightAmount) {
        if (storageType.equals(OdysseyStorageType.OTHER)) {
            throw new IllegalArgumentException("StorageType Other must use MissionIngredient class");
        }
        this.storageType = storageType;
        this.leftAmount = leftAmount;
        this.material = material;
        this.rightAmount = rightAmount;
        initComponents();
        initEventHandling();
    }

    private void initEventHandling() {
        this.eventListeners.add(EventService.addListener(this, JournalLineProcessedEvent.class, journalProcessedEvent -> this.update()));
    }

    private void initComponents() {
        this.nameLabel = LabelBuilder.builder().withStyleClass("ingredient-name").withText(LocaleService.getStringBinding(this.material.getLocalizationKey())).build();
        initImage();

        this.leftAmountLabel = LabelBuilder.builder().withStyleClass("ingredient-required").build();
        this.rightAmountLabel = LabelBuilder.builder().withStyleClass("ingredient-available").build();
        this.leftDescriptionLabel = LabelBuilder.builder().withStyleClass("ingredient-quantity-label").withText(LocaleService.getStringBinding("recipe.header.required")).build();
        this.rightDescriptionLabel = LabelBuilder.builder().withStyleClass("ingredient-quantity-label").withText(LocaleService.getStringBinding("recipe.header.available")).build();

        this.leftHBox = BoxBuilder.builder().withNodes(this.leftDescriptionLabel, this.leftAmountLabel).withStyleClass("ingredient-quantity-section").buildHBox();
        this.rightHBox = BoxBuilder.builder().withNodes(this.rightAmountLabel, this.rightDescriptionLabel).withStyleClass("ingredient-quantity-section").buildHBox();
        this.leftAmountLabel.setText(this.leftAmount.toString());
        HBox.setHgrow(this.leftAmountLabel, Priority.ALWAYS);
        this.rightAmountLabel.setText(this.rightAmount.toString());
        this.region = new Region();
        HBox.setHgrow(this.region, Priority.ALWAYS);
        this.region2 = new Region();
        VBox.setVgrow(this.region2, Priority.ALWAYS);

        this.firstLine = BoxBuilder.builder().withNodes(this.image, this.nameLabel).buildHBox();
        this.secondLine = new HBox(this.leftHBox, this.region, this.rightHBox);
        this.getChildren().addAll(this.firstLine, this.region2, this.secondLine);

        MaterialService.addMaterialInfoPopOver(this, this.material);
        this.getStyleClass().add("ingredient");

        update();
    }

    @SuppressWarnings("java:S6205")
    private void initImage() {
        final ResizableImageViewBuilder imageViewBuilder = ResizableImageViewBuilder.builder().withStyleClass("ingredient-image");
        switch (this.storageType) {
            case DATA -> imageViewBuilder.withImage("/images/material/data.png");
            case GOOD -> imageViewBuilder.withImage("/images/material/good.png");
            case ASSET -> {
                switch (((Asset) this.material).getType()) {
                    case TECH -> imageViewBuilder.withImage("/images/material/tech.png");
                    case CIRCUIT -> imageViewBuilder.withImage("/images/material/circuit.png");
                    case CHEMICAL -> imageViewBuilder.withImage("/images/material/chemical.png");
                }
            }
            case TRADE -> imageViewBuilder.withImage("/images/material/unknown.png");
            case CONSUMABLE -> imageViewBuilder.withImage("/images/material/unknown.png");
            case OTHER -> throw new IllegalArgumentException("StorageType Other must use MissionIngredient class");
        }
        this.image = imageViewBuilder.build();
    }

    private void setRightAmount(final Integer rightAmount) {
        this.rightAmount = rightAmount;
    }


    protected void update() {
        setRightAmount(StorageService.getMaterials(this.storageType).get(this.material).getTotalValue());
        if (this.rightAmount >= this.leftAmount) {
            this.rightAmountLabel.setText(this.rightAmount.toString());
            this.getStyleClass().removeAll(INGREDIENT_WITH_AMOUNT_CLASS, INGREDIENT_FILLED_CLASS, INGREDIENT_UNFILLED_CLASS);
            this.getStyleClass().addAll(INGREDIENT_WITH_AMOUNT_CLASS, INGREDIENT_FILLED_CLASS);
        } else {
            this.rightAmountLabel.setText(this.rightAmount.toString());
            this.getStyleClass().removeAll(INGREDIENT_WITH_AMOUNT_CLASS, INGREDIENT_FILLED_CLASS, INGREDIENT_UNFILLED_CLASS);
            this.getStyleClass().addAll(INGREDIENT_WITH_AMOUNT_CLASS, INGREDIENT_UNFILLED_CLASS);
        }
    }

    @Override
    public OdysseyStorageType getType() {
        return this.storageType;
    }

    @Override
    public String getName() {
        return this.nameLabel.getText();
    }

    public Material getMaterial() {
        return this.material;
    }

    Label getLeftAmountLabel() {
        return this.leftAmountLabel;
    }

    Integer getLeftAmount() {
        return this.leftAmount;
    }

    Integer getRightAmount() {
        return this.rightAmount;
    }

    Label getRightAmountLabel() {
        return this.rightAmountLabel;
    }


    void setLeftDescriptionLabel(final StringBinding leftDescriptionLabel) {
        this.leftDescriptionLabel.textProperty().bind(leftDescriptionLabel);
    }

    void setRightDescriptionLabel(final StringBinding rightDescriptionLabel) {
        this.rightDescriptionLabel.textProperty().bind(rightDescriptionLabel);
    }

    Label getLeftDescriptionLabel() {
        return this.leftDescriptionLabel;
    }

    Label getRightDescriptionLabel() {
        return this.rightDescriptionLabel;
    }

    void onDestroy() {
        this.eventListeners.forEach(EventService::removeListener);
    }
}
