package nl.jixxed.eliteodysseymaterials.templates;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import nl.jixxed.eliteodysseymaterials.BarterConstants;
import nl.jixxed.eliteodysseymaterials.RecipeConstants;
import nl.jixxed.eliteodysseymaterials.SpawnConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Storage;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MaterialCard extends HBox {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    @FXML
    private ImageView image;
    @FXML
    private Label name;
    @FXML
    private Label amount;

    private final Storage amounts;
    NumberFormat numberFormat = NumberFormat.getNumberInstance();

    public MaterialCard(final String name, final Storage amounts) {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Material.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);


        try {
            fxmlLoader.load();
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }
        this.amounts = amounts;
        this.name.setText(name);
        final String amountText = this.amounts != null ? (!this.amounts.getBackPackValue().equals(0)) ? "(" + this.amounts.getBackPackValue() + ") " + this.amounts.getShipLockerValue().toString() :
                this.amounts.getShipLockerValue().toString() : "";
        this.amount.setText(amountText);
        this.image.setFitWidth(0);
    }

    public MaterialCard(final Material material, final String name, final Storage amounts, final boolean isEngineerUnlockMaterial) {
        this(material, false, name, amounts, isEngineerUnlockMaterial);
    }

    public MaterialCard(final Material material, final boolean disableTooltip, final String name, final Storage amounts, final boolean isEngineerUnlockMaterial) {
        this(name + (APPLICATION_STATE.isFavourite(material) ? " \u2605" : ""), amounts);
        this.setOnMouseClicked((event) -> setFavourite(APPLICATION_STATE.toggleFavourite(material)));
        this.setFavourite(APPLICATION_STATE.isFavourite(material));

        final boolean isUnknown = Data.UNKNOWN.equals(material) || Good.UNKNOWN.equals(material) || Asset.UNKNOWN.equals(material);
        if (!disableTooltip) {
            final Tooltip tooltip = createTooltip(name, material);
            this.name.setTooltip(tooltip);
            tooltip.setShowDelay(Duration.millis(100));
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

    public MaterialCard(final Asset asset, final String name, final Storage amounts) {
        this(asset, false, name, amounts);
    }

    public MaterialCard(final Asset asset, final boolean disableTooltip, final String name, final Storage amounts) {
        this(name + (APPLICATION_STATE.isFavourite(asset) ? " \u2605" : ""), amounts);
        this.setOnMouseClicked((event) -> setFavourite(APPLICATION_STATE.toggleFavourite(asset)));
        this.setFavourite(APPLICATION_STATE.isFavourite(asset));
        if (!disableTooltip) {
            this.name.setTooltip(createTooltip(name, asset));
        }
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

    private Tooltip createTooltip(final String name, final Material material) {
        final boolean isUnknown = Data.UNKNOWN.equals(material) || Good.UNKNOWN.equals(material) || Asset.UNKNOWN.equals(material);

        if (isUnknown) {
            return new Tooltip("Unknown material, please report to the developer.");
        } else {
            final String recipesContaining = RecipeConstants.findRecipesContaining(material);
            final StringBuilder builder = new StringBuilder();
            builder.append(name);
            final Integer barterSellPrice = BarterConstants.getBarterSellPrice(material);
            builder.append("\n\nBarter sell price: $").append(barterSellPrice == -1 ? "?" : this.numberFormat.format(barterSellPrice));
            if (material instanceof Asset) {
                builder.append("\nBarter trade buy/sell: ").append(BarterConstants.getBarterValues(material));
            }
            if (!recipesContaining.isBlank()) {
                builder.append("\n\nUsed in recipes:\n");
                builder.append(recipesContaining);
            }
            final Map<SpawnLocationType, List<? extends SpawnLocation>> spawnLocations = SpawnConstants.getSpawnLocations(material);
            if (!spawnLocations.isEmpty()) {
                spawnLocations.forEach((locationType, value) -> {
                    final String locations = value.stream().map(SpawnLocation::friendlyName).collect(Collectors.joining(", "));
                    if (!locations.isBlank()) {
                        builder.append("\n\n").append(locationType.friendlyName()).append(":\n");
                        builder.append(locations);
                    }
                });
            }
            return new Tooltip(builder.toString());
        }
    }

    private void setFavourite(final boolean isFavourite) {
        if (isFavourite) {
            if (!this.getStyleClass().contains("material-favourite")) {
                this.getStyleClass().add("material-favourite");
            }
            if (!this.name.getText().endsWith(" \u2605")) {
                this.name.setText(this.name.getText() + " \u2605");
            }
        } else {
            this.getStyleClass().remove("material-favourite");
            if (this.name.getText().endsWith(" \u2605")) {
                this.name.setText(this.name.getText().substring(0, this.name.getText().length() - 2));
            }
        }
    }
}
