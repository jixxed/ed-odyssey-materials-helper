package nl.jixxed.eliteodysseymaterials.templates;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import nl.jixxed.eliteodysseymaterials.RecipeConstants;
import nl.jixxed.eliteodysseymaterials.enums.Asset;
import nl.jixxed.eliteodysseymaterials.enums.Data;
import nl.jixxed.eliteodysseymaterials.enums.Good;
import nl.jixxed.eliteodysseymaterials.enums.Material;

import java.io.IOException;

public class MaterialCard extends HBox {
    @FXML
    private ImageView image;
    @FXML
    private Label name;
    @FXML
    private Label amount;

    public MaterialCard(final String name, final String amount) {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Material.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);


        try {
            fxmlLoader.load();
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }
        this.name.setText(name);
        this.amount.setText(amount);
        this.image.setFitWidth(0);
    }

    public MaterialCard(final Material material, final String name, final String amount, final boolean isEngineerUnlockMaterial) {
        this(name, amount);
        final boolean isUnknown = Data.UNKNOWN.equals(material) || Good.UNKNOWN.equals(material);
        if (isUnknown) {
            this.name.setTooltip(new Tooltip("Unknown material, please report to the developer."));
        } else {
            final String recipesContaining = RecipeConstants.findRecipesContaining(material);
            this.name.setTooltip(new Tooltip(name + (!recipesContaining.isBlank() ? "\n" + "Used in recipes:\n" + recipesContaining : "")));
        }
        if (isEngineerUnlockMaterial) {
            this.image.setImage(new Image(getClass().getResourceAsStream("/images/engineer.png")));
        } else if (material instanceof Data) {
            this.image.setImage(new Image(getClass().getResourceAsStream("/images/data.png")));
        } else if (material instanceof Good) {
            this.image.setImage(new Image(getClass().getResourceAsStream("/images/good.png")));
        }
        final String materialType = material.getClass().getSimpleName().toLowerCase();
        if (isUnknown) {
            this.getStyleClass().addAll("material", "material-irrelevant", "material-" + materialType + "-unknown");
        } else if (RecipeConstants.isEngineeringOnlyIngredient(material)) {
            this.getStyleClass().addAll("material", "material-irrelevant", "material-" + materialType + "-engineer-irrelevant");
        } else if (RecipeConstants.isEngineeringIngredient(material)) {
            this.getStyleClass().addAll("material", "material-relevant", "material-" + materialType + "-engineer-relevant");
        } else if (RecipeConstants.isBlueprintIngredient(material)) {
            this.getStyleClass().addAll("material", "material-relevant", "material-" + materialType + "-relevant");
        } else {
            this.getStyleClass().addAll("material", "material-irrelevant", "material-" + materialType + "-irrelevant");
        }
    }

    public MaterialCard(final Asset asset, final String name, final String amount) {
        this(name, amount);
        final String recipesContaining = RecipeConstants.findRecipesContaining(asset);
        this.name.setTooltip(new Tooltip(name + (!recipesContaining.isBlank() ? "\n" + "Used in recipes:\n" + recipesContaining : "")));
        switch (asset.getType()) {
            case TECH -> this.image.setImage(new Image(getClass().getResourceAsStream("/images/tech.png")));
            case CIRCUIT -> this.image.setImage(new Image(getClass().getResourceAsStream("/images/circuit.png")));
            case CHEMICAL -> this.image.setImage(new Image(getClass().getResourceAsStream("/images/chemical.png")));
            default -> this.image.setFitWidth(0);
        }
        switch (asset.getType()) {
            case TECH -> this.getStyleClass().addAll("material", "material-relevant", "material-asset-tech");
            case CIRCUIT -> this.getStyleClass().addAll("material", "material-relevant", "material-asset-circuit");
            case CHEMICAL -> this.getStyleClass().addAll("material", "material-relevant", "material-asset-chemical");
            default -> this.getStyleClass().addAll("material", "material-relevant", "material-asset-unknown");
        }
    }
}
