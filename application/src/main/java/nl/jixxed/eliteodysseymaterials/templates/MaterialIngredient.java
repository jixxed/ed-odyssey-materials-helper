package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TooltipBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.Asset;
import nl.jixxed.eliteodysseymaterials.enums.Material;
import nl.jixxed.eliteodysseymaterials.enums.StorageType;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.JournalProcessedEvent;

public class MaterialIngredient extends Ingredient {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final String INGREDIENT_WITH_AMOUNT_CLASS = "ingredient-with-amount";
    private static final String INGREDIENT_FILLED_CLASS = "ingredient-filled";
    private static final String INGREDIENT_UNFILLED_CLASS = "ingredient-unfilled";
    public static final String INGREDIENT_WITHOUT_AMOUNT_CLASS = "ingredient-without-amount";
    private final StorageType storageType;
    private final Material material;
    private final Integer amountRequired;
    private Integer amountAvailable;

    private Label nameLabel;
    private ResizableImageView image;
    private Label amountRequiredLabel;
    private Label amountAvailableLabel;
    private Label requiredLabel;
    private Label availableLabel;
    private HBox requiredHBox;
    private HBox availableHBox;
    private HBox firstLine;
    private Region region;
    private HBox secondLine;
    private Region region2;
    private Tooltip tooltip;


    MaterialIngredient(final StorageType storageType, final Material material, final Integer amount, final Integer amountAvailable) {
        if (storageType.equals(StorageType.OTHER)) {
            throw new IllegalArgumentException("StorageType Other must use MissionIngredient class");
        }
        this.storageType = storageType;
        this.amountRequired = amount;
        this.material = material;
        this.amountAvailable = amountAvailable;
        initComponents();
        initEventHandling();
    }

    private void initEventHandling() {
        EventService.addListener(JournalProcessedEvent.class, (journalProcessedEvent) -> this.update());
    }

    private void initComponents() {
        this.nameLabel = LabelBuilder.builder().withStyleClass("ingredient-name").withText(LocaleService.getStringBinding(this.material.getLocalizationKey())).build();
        initImage();

        this.amountRequiredLabel = LabelBuilder.builder().withStyleClass("ingredient-required").build();
        this.amountAvailableLabel = LabelBuilder.builder().withStyleClass("ingredient-available").build();
        this.requiredLabel = LabelBuilder.builder().withStyleClass("ingredient-quantity-label").withText(LocaleService.getStringBinding("recipe.header.required")).build();
        this.availableLabel = LabelBuilder.builder().withStyleClass("ingredient-quantity-label").withText(LocaleService.getStringBinding("recipe.header.available")).build();

        this.requiredHBox = BoxBuilder.builder().withNodes(this.requiredLabel, this.amountRequiredLabel).withStyleClass("ingredient-quantity-section").buildHBox();
        this.availableHBox = BoxBuilder.builder().withNodes(this.amountAvailableLabel, this.availableLabel).withStyleClass("ingredient-quantity-section").buildHBox();
        this.amountRequiredLabel.setText(this.amountRequired.toString());
        HBox.setHgrow(this.amountRequiredLabel, Priority.ALWAYS);
        this.amountAvailableLabel.setText(this.amountAvailable.toString());
        this.region = new Region();
        HBox.setHgrow(this.region, Priority.ALWAYS);
        this.region2 = new Region();
        VBox.setVgrow(this.region2, Priority.ALWAYS);

        this.firstLine = BoxBuilder.builder().withNodes(this.image, this.nameLabel).buildHBox();
        this.secondLine = new HBox(this.requiredHBox, this.region, this.availableHBox);
        this.getChildren().addAll(this.firstLine, this.region2, this.secondLine);

        this.tooltip = TooltipBuilder.builder().withText(LocaleService.getToolTipStringBinding(this.material)).withShowDelay(Duration.millis(100)).build();
        Tooltip.install(this, this.tooltip);

        this.getStyleClass().add("ingredient");

        update();
    }

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
            case OTHER -> throw new IllegalArgumentException("StorageType Other must use MissionIngredient class");
        }
        this.image = imageViewBuilder.build();
    }

    private void setAmountAvailable(final Integer amountAvailable) {
        this.amountAvailable = amountAvailable;
    }


    protected void update() {
        setAmountAvailable(APPLICATION_STATE.getMaterials(this.storageType).get(this.material).getTotalValue());
        if (this.amountAvailable >= this.amountRequired) {
            this.amountAvailableLabel.setText(this.amountAvailable.toString());
            this.getStyleClass().removeAll(INGREDIENT_WITH_AMOUNT_CLASS, INGREDIENT_FILLED_CLASS, INGREDIENT_UNFILLED_CLASS);
            this.getStyleClass().addAll(INGREDIENT_WITH_AMOUNT_CLASS, INGREDIENT_FILLED_CLASS);
        } else {
            this.amountAvailableLabel.setText(this.amountAvailable.toString());
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

    public Material getMaterial() {
        return this.material;
    }

    Label getAmountRequiredLabel() {
        return this.amountRequiredLabel;
    }

    Integer getAmountRequired() {
        return this.amountRequired;
    }

    Integer getAmountAvailable() {
        return this.amountAvailable;
    }

    Label getAmountAvailableLabel() {
        return this.amountAvailableLabel;
    }
}
