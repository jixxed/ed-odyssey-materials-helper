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

    public MaterialCard(final Good good, final String name, final String amount) {
        this(name, amount);
        this.name.setTooltip(new Tooltip(name + "\n" + "Used in recipes:\n" + RecipeConstants.findRecipesContaining(good)));
        this.image.setImage(new Image(getClass().getResourceAsStream("/images/good.png")));
    }

    public MaterialCard(final Data data, final String name, final String amount) {
        this(name, amount);
        this.name.setTooltip(new Tooltip(name + "\n" + "Used in recipes:\n" + RecipeConstants.findRecipesContaining(data)));
        this.image.setImage(new Image(getClass().getResourceAsStream("/images/data.png")));
    }

    public MaterialCard(final Asset asset, final String name, final String amount) {
        this(name, amount);
        this.name.setTooltip(new Tooltip(name + "\n" + "Used in recipes:\n" + RecipeConstants.findRecipesContaining(asset)));
        switch (asset.getType()) {
            case TECH -> this.image.setImage(new Image(getClass().getResourceAsStream("/images/tech.png")));
            case CIRCUIT -> this.image.setImage(new Image(getClass().getResourceAsStream("/images/circuit.png")));
            case CHEMICAL -> this.image.setImage(new Image(getClass().getResourceAsStream("/images/chemical.png")));
            default -> this.image.setFitWidth(0);
        }
    }

    public Label getName() {
        return this.name;
    }
}
