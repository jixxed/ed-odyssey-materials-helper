package nl.jixxed.eliteodysseymaterials.templates;

import javafx.beans.binding.StringBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.AssetType;
import nl.jixxed.eliteodysseymaterials.enums.Data;
import nl.jixxed.eliteodysseymaterials.enums.Good;
import nl.jixxed.eliteodysseymaterials.enums.Material;

import java.io.IOException;

public class LegendCard extends HBox {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    @FXML
    private ImageView image;
    @FXML
    private Label name;


    public LegendCard(final StringBinding nameBinding) {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LegendItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);


        try {
            fxmlLoader.load();
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }
        this.name.textProperty().bind(nameBinding);
        this.image.setFitWidth(0);
        HBox.setHgrow(this.name, Priority.ALWAYS);
    }

    public LegendCard(final Class<? extends Material> materialClass, final StringBinding nameBinding, final boolean isEngineerUnlockMaterial, final boolean isBlueprintMaterial) {
        this(nameBinding);

        if (isEngineerUnlockMaterial) {
            this.image.setImage(new Image(getClass().getResourceAsStream("/images/engineer.png")));
        } else if (materialClass.equals(Data.class)) {
            this.image.setImage(new Image(getClass().getResourceAsStream("/images/data.png")));
        } else if (materialClass.equals(Good.class)) {
            this.image.setImage(new Image(getClass().getResourceAsStream("/images/good.png")));
        }
        final String materialType = materialClass.getSimpleName().toLowerCase();
        if (isEngineerUnlockMaterial && isBlueprintMaterial) {
            this.getStyleClass().addAll("material", "material-relevant", "material-" + materialType + "-engineer-relevant");
        } else if (isEngineerUnlockMaterial) {
            this.getStyleClass().addAll("material", "material-irrelevant", "material-" + materialType + "-engineer-irrelevant");
        } else if (isBlueprintMaterial) {
            this.getStyleClass().addAll("material", "material-relevant", "material-" + materialType + "-relevant");
        } else {
            this.getStyleClass().addAll("material", "material-irrelevant", "material-" + materialType + "-irrelevant");
        }
    }

    public LegendCard(final Class<? extends Material> materialClass, final AssetType assetType, final StringBinding nameBinding, final boolean isEngineerUnlockMaterial, final boolean isBlueprintMaterial) {
        this(materialClass, nameBinding, isEngineerUnlockMaterial, isBlueprintMaterial);

        switch (assetType) {
            case TECH -> this.image.setImage(new Image(getClass().getResourceAsStream("/images/tech.png")));
            case CIRCUIT -> this.image.setImage(new Image(getClass().getResourceAsStream("/images/circuit.png")));
            case CHEMICAL -> this.image.setImage(new Image(getClass().getResourceAsStream("/images/chemical.png")));
            default -> this.image.setFitWidth(0);
        }
        switch (assetType) {
            case TECH -> this.getStyleClass().addAll("material-asset-tech");
            case CIRCUIT -> this.getStyleClass().addAll("material-asset-circuit");
            case CHEMICAL -> this.getStyleClass().addAll("material-asset-chemical");
            default -> this.getStyleClass().addAll("material-asset-unknown");
        }
    }

}
