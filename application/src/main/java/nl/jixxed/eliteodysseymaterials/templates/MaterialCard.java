package nl.jixxed.eliteodysseymaterials.templates;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import nl.jixxed.eliteodysseymaterials.RecipeConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Storage;
import nl.jixxed.eliteodysseymaterials.enums.Asset;
import nl.jixxed.eliteodysseymaterials.enums.Data;
import nl.jixxed.eliteodysseymaterials.enums.Good;
import nl.jixxed.eliteodysseymaterials.enums.Material;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.io.IOException;

public class MaterialCard extends HBox {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    @FXML
    private ImageView image;
    @FXML
    private Label name;
    @FXML
    private Label amount;

    private final Storage amounts;

    private MaterialCard(final Storage amounts) {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Material.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);


        try {
            fxmlLoader.load();
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }
        this.amounts = amounts;
        final String amountText = this.amounts != null ? (!this.amounts.getBackPackValue().equals(0)) ? "(" + this.amounts.getBackPackValue() + ") " + this.amounts.getShipLockerValue().toString() :
                this.amounts.getShipLockerValue().toString() : "";
        this.amount.setText(amountText);
        this.image.setFitWidth(0);
    }

    public MaterialCard(final Material material, final String name, final Storage amounts, final boolean isEngineerUnlockMaterial) {
        this(material, amounts, isEngineerUnlockMaterial);
        this.name.textProperty().bind(LocaleService.getStringBinding(() -> name));
    }

    public MaterialCard(final Material material, final Storage amounts, final boolean isEngineerUnlockMaterial) {
        this(amounts);
        this.name.textProperty().bind(LocaleService.getStringBinding(material));
        this.setOnMouseClicked((event) -> setFavourite(material, APPLICATION_STATE.toggleFavourite(material)));
        this.setFavourite(material, APPLICATION_STATE.isFavourite(material));


        final Tooltip tooltip = new Tooltip();
        tooltip.textProperty().bind(LocaleService.getToolTipStringBinding(material));
        tooltip.setShowDelay(Duration.millis(100));
        Tooltip.install(this, tooltip);

        if (isEngineerUnlockMaterial) {
            this.image.setImage(new Image(getClass().getResourceAsStream("/images/engineer.png")));
        } else if (material instanceof Data) {
            this.image.setImage(new Image(getClass().getResourceAsStream("/images/data.png")));
        } else if (material instanceof Good) {
            this.image.setImage(new Image(getClass().getResourceAsStream("/images/good.png")));
        }
        final String materialType = material.getClass().getSimpleName().toLowerCase();
        if (material.isUnknown()) {
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

    public MaterialCard(final Asset asset, final Storage amounts) {
        this(amounts);
        this.setOnMouseClicked((event) -> setFavourite(asset, APPLICATION_STATE.toggleFavourite(asset)));
        this.setFavourite(asset, APPLICATION_STATE.isFavourite(asset));


        final Tooltip tooltip = new Tooltip();
        tooltip.textProperty().bind(LocaleService.getToolTipStringBinding(asset));
        tooltip.setShowDelay(Duration.millis(100));
        Tooltip.install(this, tooltip);


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

    private void setFavourite(final Material material, final boolean isFavourite) {
        if (isFavourite) {
            if (!this.getStyleClass().contains("material-favourite")) {
                this.getStyleClass().add("material-favourite");
            }
        } else {
            this.getStyleClass().remove("material-favourite");
        }
        this.name.textProperty().bind(LocaleService.getStringBinding(material));
    }
}
