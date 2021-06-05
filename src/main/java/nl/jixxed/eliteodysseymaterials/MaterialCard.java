package nl.jixxed.eliteodysseymaterials;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import nl.jixxed.eliteodysseymaterials.enums.Asset;
import nl.jixxed.eliteodysseymaterials.enums.Data;
import nl.jixxed.eliteodysseymaterials.enums.Good;

import java.io.IOException;

public class MaterialCard extends HBox {
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
    }

    public MaterialCard(final Good key, final String name, final String amount) {
        this(name, amount);
        this.name.setTooltip(new Tooltip(name + "\n" + "Used in recipes:\n" + RecipeConstants.findRecipesContaining(key)));
    }

    public MaterialCard(final Data key, final String name, final String amount) {
        this(name, amount);
        this.name.setTooltip(new Tooltip(name + "\n" + "Used in recipes:\n" + RecipeConstants.findRecipesContaining(key)));
    }

    public MaterialCard(final Asset key, final String name, final String amount) {
        this(name, amount);
        this.name.setTooltip(new Tooltip(name + "\n" + "Used in recipes:\n" + RecipeConstants.findRecipesContaining(key)));
    }

    public Label getName() {
        return this.name;
    }
}
