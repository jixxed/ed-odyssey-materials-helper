package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import nl.jixxed.eliteodysseymaterials.models.EngineerRecipe;

public class EngineerTitledPane extends TitledPane {
    private final EngineerRecipe engineerRecipe;

    public EngineerTitledPane(final String title, final Node content, final EngineerRecipe engineerRecipe) {
        super(title, content);
        this.engineerRecipe = engineerRecipe;
    }

    public EngineerRecipe getEngineerRecipe() {
        return this.engineerRecipe;
    }

}
