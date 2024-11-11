package nl.jixxed.eliteodysseymaterials.templates.odyssey;

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
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyStorageType;
import nl.jixxed.eliteodysseymaterials.enums.StorageType;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.MaterialService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.JournalLineProcessedEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;
import nl.jixxed.eliteodysseymaterials.templates.generic.Ingredient;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class OdysseyMaterialIngredient extends Ingredient {
    private static final String INGREDIENT_WITH_AMOUNT_CLASS = "ingredient-with-amount";
    private static final String INGREDIENT_FILLED_CLASS = "ingredient-filled";
    private static final String INGREDIENT_UNFILLED_CLASS = "ingredient-unfilled";
    @EqualsAndHashCode.Include
    private final OdysseyStorageType storageType;
    @EqualsAndHashCode.Include
    private final OdysseyMaterial odysseyMaterial;
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
    protected List<EventListener<?>> eventListeners = new ArrayList<>();


    public OdysseyMaterialIngredient(final OdysseyStorageType storageType, final OdysseyMaterial odysseyMaterial, final Integer leftAmount, final Integer rightAmount) {
        if (storageType.equals(OdysseyStorageType.OTHER)) {
            throw new IllegalArgumentException("StorageType Other must use MissionIngredient class");
        }
        this.storageType = storageType;
        this.leftAmount = leftAmount;
        this.odysseyMaterial = odysseyMaterial;
        this.rightAmount = rightAmount;
        initComponents();
        initEventHandling();
    }

    private void initEventHandling() {
        this.eventListeners.add(EventService.addListener(true, this, JournalLineProcessedEvent.class, journalProcessedEvent -> this.update()));
    }

    private void initComponents() {
        this.nameLabel = LabelBuilder.builder().withStyleClass("ingredient-name").withText(LocaleService.getStringBinding(this.odysseyMaterial.getLocalizationKey())).build();
        initImage();

        this.leftAmountLabel = LabelBuilder.builder().withStyleClass("ingredient-required").build();
        this.rightAmountLabel = LabelBuilder.builder().withStyleClass("ingredient-available").build();
        this.leftDescriptionLabel = LabelBuilder.builder().withStyleClass("ingredient-quantity-label").withText(LocaleService.getStringBinding("blueprint.header.required")).build();
        this.rightDescriptionLabel = LabelBuilder.builder().withStyleClass("ingredient-quantity-label").withText(LocaleService.getStringBinding("blueprint.header.available")).build();

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

        MaterialService.addMaterialInfoPopOver(this, this.odysseyMaterial, false);
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
                switch (((Asset) this.odysseyMaterial).getType()) {
                    case TECH -> imageViewBuilder.withImage("/images/material/tech.png");
                    case CIRCUIT -> imageViewBuilder.withImage("/images/material/circuit.png");
                    case CHEMICAL -> imageViewBuilder.withImage("/images/material/chemical.png");
                }
            }
            case CONSUMABLE -> imageViewBuilder.withImage("/images/material/unknown.png");
            case OTHER -> throw new IllegalArgumentException("StorageType Other must use MissionIngredient class");
        }
        this.image = imageViewBuilder.build();
    }

    private void setRightAmount(final Integer rightAmount) {
        this.rightAmount = rightAmount;
    }


    protected void update() {
        setRightAmount(StorageService.getMaterialStorage(this.odysseyMaterial).getTotalValue());
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
    public StorageType getType() {
        return this.storageType;
    }

    @Override
    public String getName() {
        return this.nameLabel.getText();
    }

    public OdysseyMaterial getOdysseyMaterial() {
        return this.odysseyMaterial;
    }

    protected Label getLeftAmountLabel() {
        return this.leftAmountLabel;
    }

    public Integer getLeftAmount() {
        return this.leftAmount;
    }

    protected Integer getRightAmount() {
        return this.rightAmount;
    }

    protected Label getRightAmountLabel() {
        return this.rightAmountLabel;
    }


    protected void setLeftDescriptionLabel(final StringBinding leftDescriptionLabel) {
        this.leftDescriptionLabel.textProperty().bind(leftDescriptionLabel);
    }

    protected void setRightDescriptionLabel(final StringBinding rightDescriptionLabel) {
        this.rightDescriptionLabel.textProperty().bind(rightDescriptionLabel);
    }

    protected Label getLeftDescriptionLabel() {
        return this.leftDescriptionLabel;
    }

    protected Label getRightDescriptionLabel() {
        return this.rightDescriptionLabel;
    }

    public void onDestroy() {
        this.eventListeners.forEach(EventService::removeListener);
    }
}
