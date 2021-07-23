package nl.jixxed.eliteodysseymaterials.templates;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.io.IOException;

public class IngredientHeader extends HBox {

    @FXML
    private Label material;
    @FXML
    private Label required;
    @FXML
    private Label available;

    public IngredientHeader() {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("IngredientHeader.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }
        this.getStyleClass().add("ingredient-header");
        HBox.setHgrow(this.required, Priority.ALWAYS);
        this.material.textProperty().bind(LocaleService.getStringBinding("recipe.header.material"));
        this.required.textProperty().bind(LocaleService.getStringBinding("recipe.header.required"));
        this.available.textProperty().bind(LocaleService.getStringBinding("recipe.header.available"));

    }
}
