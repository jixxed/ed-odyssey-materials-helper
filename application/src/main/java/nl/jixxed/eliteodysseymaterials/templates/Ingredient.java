package nl.jixxed.eliteodysseymaterials.templates;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import nl.jixxed.eliteodysseymaterials.enums.Asset;
import nl.jixxed.eliteodysseymaterials.enums.Material;
import nl.jixxed.eliteodysseymaterials.enums.StorageType;

import java.io.IOException;

public class Ingredient extends HBox {
    private final StorageType storageType;
    private String code;

    private Integer amountRequired;
    private Integer amountAvailable;

    @FXML
    private HBox box;
    @FXML
    private Label nameLabel;
    @FXML
    private ImageView image;
    @FXML
    private Label amountRequiredLabel;
    @FXML
    private Label amountAvailableLabel;

    private Ingredient(final StorageType storageType) {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Ingredient.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }
        this.storageType = storageType;
        this.getStyleClass().add("ingredient");
    }

    public Ingredient(final String text) {
        this(StorageType.OTHER);
        this.nameLabel.setText(text);
    }

    public Ingredient(final StorageType storageType, final Material material, final String amount, final String amountAvailable) {
        this(storageType);
        this.code = material.toString();
        this.nameLabel.setText(material.friendlyName());
        this.code = material.toString();
        this.amountRequiredLabel.setText(amount);
        this.amountRequired = Integer.valueOf(amount);
        this.amountAvailable = Integer.valueOf(amountAvailable);
        switch (storageType) {
            case DATA -> this.image.setImage(new Image(getClass().getResourceAsStream("/images/data.png")));
            case GOOD -> this.image.setImage(new Image(getClass().getResourceAsStream("/images/good.png")));
            case ASSET -> {
                switch (((Asset) material).getType()) {
                    case TECH -> this.image.setImage(new Image(getClass().getResourceAsStream("/images/tech.png")));
                    case CIRCUIT -> this.image.setImage(new Image(getClass().getResourceAsStream("/images/circuit.png")));
                    case CHEMICAL -> this.image.setImage(new Image(getClass().getResourceAsStream("/images/chemical.png")));
                    default -> this.image.setFitWidth(0);
                }
            }
            default -> this.image.setFitWidth(0);
        }


        update();
    }

    public void setAmountAvailable(final Integer amountAvailable) {
        this.amountAvailable = amountAvailable;
    }

    public StorageType getType() {
        return this.storageType;
    }

    public String getCode() {
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
}
