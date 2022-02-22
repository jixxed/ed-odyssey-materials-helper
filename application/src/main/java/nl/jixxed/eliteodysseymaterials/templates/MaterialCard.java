package nl.jixxed.eliteodysseymaterials.templates;

import javafx.beans.binding.StringBinding;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.constants.BlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Storage;
import nl.jixxed.eliteodysseymaterials.enums.Asset;
import nl.jixxed.eliteodysseymaterials.enums.Data;
import nl.jixxed.eliteodysseymaterials.enums.Good;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.MaterialService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;

@Slf4j
class MaterialCard extends HBox {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final String MATERIAL_IRRELEVANT_CLASS = "material-irrelevant";
    private static final String MATERIAL_RELEVANT_CLASS = "material-relevant";
    private static final String MATERIAL_FAVOURITE_CLASS = "material-favourite";
    private static final String MATERIAL_SPECIFIC_CLASS_PREFIX = "material-";

    private final OdysseyMaterial odysseyMaterial;
    private final Storage amounts;
    private DestroyableResizableImageView image;
    private Label name;
    private Label amount;


    private MaterialCard(final OdysseyMaterial odysseyMaterial, final StringBinding nameBinding, final Storage amounts) {
        this.amounts = amounts;
        this.odysseyMaterial = odysseyMaterial;
        initComponents(odysseyMaterial, nameBinding);
    }

    MaterialCard(final OdysseyMaterial odysseyMaterial, final String name, final Storage amounts) {
        this(odysseyMaterial, LocaleService.getStringBinding(() -> name), amounts);
    }

    MaterialCard(final OdysseyMaterial odysseyMaterial, final Storage amounts) {
        this(odysseyMaterial, LocaleService.getStringBinding(odysseyMaterial), amounts);
    }

    private void initComponents(final OdysseyMaterial odysseyMaterial, final StringBinding nameBinding) {
        this.getStyleClass().add("material");
        this.name = LabelBuilder.builder()
                .withStyleClass("materialcard-name")
                .withText(nameBinding)
                .build();
        final String amountText;
        if (this.amounts != null) {
            amountText = (!this.amounts.getBackPackValue().equals(0)) ? "(" + this.amounts.getBackPackValue() + ") " + this.amounts.getShipLockerValue().toString() : this.amounts.getShipLockerValue().toString();
        } else {
            amountText = "";
        }
        this.amount = LabelBuilder.builder()
                .withStyleClass("materialcard-amount")
                .withNodeOrientation(NodeOrientation.RIGHT_TO_LEFT)
                .withNonLocalizedText(amountText)
                .build();

        this.image = createMaterialImage(odysseyMaterial);

        final Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        MaterialService.addMaterialInfoPopOver(this, odysseyMaterial);
        initMaterialCardStyle();

        this.setFavourite(odysseyMaterial, APPLICATION_STATE.isFavourite(odysseyMaterial));
        this.setOnMouseClicked(event -> setFavourite(odysseyMaterial, APPLICATION_STATE.toggleFavourite(odysseyMaterial)));
        this.getChildren().addAll(this.image, this.name, region, this.amount);
    }

    private void initMaterialCardStyle() {
        final String materialType = this.odysseyMaterial.getClass().getSimpleName().toLowerCase();
        if (this.odysseyMaterial.isUnknown()) {
            this.getStyleClass().addAll(MATERIAL_IRRELEVANT_CLASS, MATERIAL_SPECIFIC_CLASS_PREFIX + materialType + "-unknown");
        } else if (this.odysseyMaterial instanceof Asset asset) {
            switch (asset.getType()) {
                case TECH -> this.getStyleClass().addAll(MATERIAL_RELEVANT_CLASS, MATERIAL_SPECIFIC_CLASS_PREFIX + materialType + "-tech");
                case CIRCUIT -> this.getStyleClass().addAll(MATERIAL_RELEVANT_CLASS, MATERIAL_SPECIFIC_CLASS_PREFIX + materialType + "-circuit");
                case CHEMICAL -> this.getStyleClass().addAll(MATERIAL_RELEVANT_CLASS, MATERIAL_SPECIFIC_CLASS_PREFIX + materialType + "-chemical");
            }
        } else if (BlueprintConstants.isEngineeringIngredientAndNotCompleted(this.odysseyMaterial)) {
            this.getStyleClass().addAll(MATERIAL_RELEVANT_CLASS, MATERIAL_SPECIFIC_CLASS_PREFIX + materialType + "-engineer-relevant");
        } else if (APPLICATION_STATE.getSoloMode() && BlueprintConstants.isEngineeringOnlyIngredient(this.odysseyMaterial)) {
            this.getStyleClass().addAll(MATERIAL_IRRELEVANT_CLASS, MATERIAL_SPECIFIC_CLASS_PREFIX + materialType + "-engineer-irrelevant");
        } else if (BlueprintConstants.isEngineeringIngredient(this.odysseyMaterial)) {
            this.getStyleClass().addAll(MATERIAL_RELEVANT_CLASS, MATERIAL_SPECIFIC_CLASS_PREFIX + materialType + "-engineer-relevant");
        } else if (BlueprintConstants.isBlueprintIngredientWithOverride(this.odysseyMaterial)) {
            this.getStyleClass().addAll(MATERIAL_RELEVANT_CLASS, MATERIAL_SPECIFIC_CLASS_PREFIX + materialType + "-relevant");
        } else {
            this.getStyleClass().addAll(MATERIAL_IRRELEVANT_CLASS, MATERIAL_SPECIFIC_CLASS_PREFIX + materialType + "-irrelevant");
        }
    }

    private DestroyableResizableImageView createMaterialImage(final OdysseyMaterial odysseyMaterial) {

        final boolean isEngineerUnlockMaterial = (APPLICATION_STATE.getSoloMode()) ? BlueprintConstants.isEngineeringIngredientAndNotCompleted(odysseyMaterial) : BlueprintConstants.isEngineeringIngredient(odysseyMaterial);
        ResizableImageViewBuilder imageViewBuilder = ResizableImageViewBuilder.builder().withStyleClass("materialcard-image");
        if (odysseyMaterial.isUnknown()) {
            imageViewBuilder.withImage("/images/material/unknown.png");
        } else if (isEngineerUnlockMaterial) {
            imageViewBuilder = imageViewBuilder.withImage("/images/material/engineer.png");
        } else if (odysseyMaterial instanceof Data) {
            imageViewBuilder = imageViewBuilder.withImage("/images/material/data.png");
        } else if (odysseyMaterial instanceof Good) {
            imageViewBuilder = imageViewBuilder.withImage("/images/material/good.png");
        } else if (odysseyMaterial instanceof Asset asset) {
            imageViewBuilder = switch (asset.getType()) {
                case TECH -> imageViewBuilder.withImage("/images/material/tech.png");
                case CIRCUIT -> imageViewBuilder.withImage("/images/material/circuit.png");
                case CHEMICAL -> imageViewBuilder.withImage("/images/material/chemical.png");
            };
        }
        return imageViewBuilder.build();
    }

    private void setFavourite(final OdysseyMaterial odysseyMaterial, final boolean isFavourite) {
        if (isFavourite) {
            if (!this.getStyleClass().contains(MATERIAL_FAVOURITE_CLASS)) {
                this.getStyleClass().add(MATERIAL_FAVOURITE_CLASS);
            }
        } else {
            this.getStyleClass().remove(MATERIAL_FAVOURITE_CLASS);
        }
        this.name.textProperty().bind(LocaleService.getStringBinding(odysseyMaterial));
    }
}
