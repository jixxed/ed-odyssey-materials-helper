package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import nl.jixxed.eliteodysseymaterials.enums.Asset;
import nl.jixxed.eliteodysseymaterials.enums.Material;
import nl.jixxed.eliteodysseymaterials.enums.StorageType;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

public class Ingredient extends VBox {
    private final StorageType storageType;
    private String code;
    private Material material;
    private Integer amountRequired;
    private Integer amountAvailable;

    private final Label nameLabel = new Label();
    private final ImageView image = new ImageView();
    private final Label amountRequiredLabel = new Label();
    private final Label amountAvailableLabel = new Label();
    private final Label requiredLabel = new Label();
    private final Label availableLabel = new Label();
    private final HBox required = new HBox(this.requiredLabel, this.amountRequiredLabel);
    private final HBox available = new HBox(this.amountAvailableLabel, this.availableLabel);
    private final HBox firstLine = new HBox(this.image, this.nameLabel);
    private final Region region = new Region();
    private final HBox secondLine = new HBox(this.required, this.region, this.available);

    //STYLE
    {


        this.nameLabel.getStyleClass().add("ingredient-name");
        this.image.getStyleClass().add("ingredient-image");
        this.image.setFitWidth(28);
        this.image.setFitHeight(28);
        this.amountRequiredLabel.getStyleClass().add("ingredient-required");
        this.amountAvailableLabel.getStyleClass().add("ingredient-available");
        this.requiredLabel.getStyleClass().add("ingredient-quantity-label");
        this.availableLabel.getStyleClass().add("ingredient-quantity-label");
        this.required.getStyleClass().add("ingredient-quantity-section");
        this.available.getStyleClass().add("ingredient-quantity-section");
        HBox.setHgrow(this.region, Priority.ALWAYS);

    }

    //LAYOUT
    {
        this.getChildren().add(this.firstLine);
    }

    private Ingredient(final StorageType storageType) {
        this.storageType = storageType;
        this.requiredLabel.textProperty().bind(LocaleService.getStringBinding("recipe.header.required"));
        this.availableLabel.textProperty().bind(LocaleService.getStringBinding("recipe.header.available"));
        this.getStyleClass().add("ingredient");
    }

    public Ingredient(final String text) {
        this(StorageType.OTHER);
        this.nameLabel.textProperty().bind(LocaleService.getStringBinding(text));
        this.firstLine.getChildren().remove(this.image);
        this.material = null;
    }

    public Ingredient(final StorageType storageType, final Material material, final Integer amount, final Integer amountAvailable) {
        this(storageType);
        this.code = material.toString();
        this.nameLabel.textProperty().bind(LocaleService.getStringBinding(material.getLocalizationKey()));
        this.code = material.toString();
        this.amountRequired = amount;
        this.amountAvailable = amountAvailable;
        this.amountRequiredLabel.setText(this.amountRequired.toString());
        this.amountAvailableLabel.setText(amountAvailable.toString());

        final Region reg = new Region();
        VBox.setVgrow(reg, Priority.ALWAYS);
        this.getChildren().add(reg);
        this.getChildren().add(this.secondLine);

        HBox.setHgrow(this.amountRequiredLabel, Priority.ALWAYS);
        this.material = material;
        final Tooltip tooltip = new Tooltip();
        tooltip.textProperty().bind(LocaleService.getToolTipStringBinding(material));
        tooltip.setShowDelay(Duration.millis(100));
        Tooltip.install(this, tooltip);
        switch (storageType) {
            case DATA -> this.image.setImage(new Image(getClass().getResourceAsStream("/images/material/data.png")));
            case GOOD -> this.image.setImage(new Image(getClass().getResourceAsStream("/images/material/good.png")));
            case ASSET -> {
                switch (((Asset) material).getType()) {
                    case TECH -> this.image.setImage(new Image(getClass().getResourceAsStream("/images/material/tech.png")));
                    case CIRCUIT -> this.image.setImage(new Image(getClass().getResourceAsStream("/images/material/circuit.png")));
                    case CHEMICAL -> this.image.setImage(new Image(getClass().getResourceAsStream("/images/material/chemical.png")));
                    default -> this.image.setFitWidth(0);
                }
            }
            default -> this.image.setFitWidth(0);
        }


        update();
    }

    void setAmountAvailable(final Integer amountAvailable) {
        this.amountAvailable = amountAvailable;
    }

    public StorageType getType() {
        return this.storageType;
    }

    String getCode() {
        return this.code;
    }

    public void update() {
        if (StorageType.OTHER.equals(this.storageType)) {
            this.amountAvailableLabel.setText("");
            this.amountRequiredLabel.setText("");
            this.getStyleClass().add("ingredient-without-amount");
        } else if (this.amountAvailable >= this.amountRequired) {
            this.amountAvailableLabel.setText(this.amountAvailable.toString());
            this.getStyleClass().removeAll("ingredient-with-amount", "ingredient-filled", "ingredient-unfilled");
            this.getStyleClass().addAll("ingredient-with-amount", "ingredient-filled");
        } else {
            this.amountAvailableLabel.setText(this.amountAvailable.toString());
            this.getStyleClass().removeAll("ingredient-with-amount", "ingredient-filled", "ingredient-unfilled");
            this.getStyleClass().addAll("ingredient-with-amount", "ingredient-unfilled");
        }
    }

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
