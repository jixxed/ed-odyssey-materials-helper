package nl.jixxed.eliteodysseymaterials.templates;

import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import nl.jixxed.eliteodysseymaterials.constants.RecipeConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Storage;
import nl.jixxed.eliteodysseymaterials.enums.Asset;
import nl.jixxed.eliteodysseymaterials.enums.Data;
import nl.jixxed.eliteodysseymaterials.enums.Good;
import nl.jixxed.eliteodysseymaterials.enums.Material;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

class MaterialCard extends HBox {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private final ResizableImageView image = new ResizableImageView("materialcard-image");
    private final Label name = new Label();
    private final Label amount = new Label();

    private final Storage amounts;

    private MaterialCard(final Storage amounts) {

        this.getStyleClass().add("material");
//        this.setMinHeight(40);
//        this.setPrefHeight(40);
//        this.setMaxHeight(40);
//        this.setPrefWidth(380);
        this.image.getStyleClass().add("materialcard-image");
        this.name.getStyleClass().add("materialcard-name");
        this.amount.getStyleClass().add("materialcard-amount");
        final Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        this.getChildren().addAll(this.image, this.name, region, this.amount);
        this.amount.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        this.amounts = amounts;
        final String amountText = this.amounts != null ? (!this.amounts.getBackPackValue().equals(0)) ? "(" + this.amounts.getBackPackValue() + ") " + this.amounts.getShipLockerValue().toString() :
                this.amounts.getShipLockerValue().toString() : "";
        this.amount.setText(amountText);
//        this.image.setFitWidth(0);
//        this.image.setFitHeight(28);
    }

    MaterialCard(final Material material, final String name, final Storage amounts, final boolean isEngineerUnlockMaterial) {
        this(material, amounts, isEngineerUnlockMaterial);
        this.name.textProperty().bind(LocaleService.getStringBinding(() -> name));
    }

    MaterialCard(final Material material, final Storage amounts, final boolean isEngineerUnlockMaterial) {
        this(amounts);
        this.name.textProperty().bind(LocaleService.getStringBinding(material));
        this.setOnMouseClicked((event) -> setFavourite(material, APPLICATION_STATE.toggleFavourite(material)));
        this.setFavourite(material, APPLICATION_STATE.isFavourite(material));


        final Tooltip tooltip = new Tooltip();
        tooltip.textProperty().bind(LocaleService.getToolTipStringBinding(material));
        tooltip.setShowDelay(Duration.millis(100));
        Tooltip.install(this, tooltip);

        if (isEngineerUnlockMaterial) {
            this.image.setImage(new Image(getClass().getResourceAsStream("/images/material/engineer.png")));
        } else if (material instanceof Data) {
            this.image.setImage(new Image(getClass().getResourceAsStream("/images/material/data.png")));
        } else if (material instanceof Good) {
            this.image.setImage(new Image(getClass().getResourceAsStream("/images/material/good.png")));
        }
        final String materialType = material.getClass().getSimpleName().toLowerCase();
        if (material.isUnknown()) {
            this.getStyleClass().addAll("material-irrelevant", "material-" + materialType + "-unknown");
        } else if (RecipeConstants.isEngineeringOnlyIngredient(material)) {
            this.getStyleClass().addAll("material-irrelevant", "material-" + materialType + "-engineer-irrelevant");
        } else if (RecipeConstants.isEngineeringIngredient(material)) {
            this.getStyleClass().addAll("material-relevant", "material-" + materialType + "-engineer-relevant");
        } else if (RecipeConstants.isBlueprintIngredient(material)) {
            this.getStyleClass().addAll("material-relevant", "material-" + materialType + "-relevant");
        } else {
            this.getStyleClass().addAll("material-irrelevant", "material-" + materialType + "-irrelevant");
        }
    }

    MaterialCard(final Asset asset, final Storage amounts) {
        this(amounts);
        this.setOnMouseClicked((event) -> setFavourite(asset, APPLICATION_STATE.toggleFavourite(asset)));
        this.setFavourite(asset, APPLICATION_STATE.isFavourite(asset));


        final Tooltip tooltip = new Tooltip();
        tooltip.textProperty().bind(LocaleService.getToolTipStringBinding(asset));
        tooltip.setShowDelay(Duration.millis(100));
        Tooltip.install(this, tooltip);


        switch (asset.getType()) {
            case TECH -> this.image.setImage(new Image(getClass().getResourceAsStream("/images/material/tech.png")));
            case CIRCUIT -> this.image.setImage(new Image(getClass().getResourceAsStream("/images/material/circuit.png")));
            case CHEMICAL -> this.image.setImage(new Image(getClass().getResourceAsStream("/images/material/chemical.png")));
            default -> this.image.setVisible(false);
        }
        switch (asset.getType()) {
            case TECH -> this.getStyleClass().addAll("material-relevant", "material-asset-tech");
            case CIRCUIT -> this.getStyleClass().addAll("material-relevant", "material-asset-circuit");
            case CHEMICAL -> this.getStyleClass().addAll("material-relevant", "material-asset-chemical");
            default -> this.getStyleClass().addAll("material-relevant", "material-asset-unknown");
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
