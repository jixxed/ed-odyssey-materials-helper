package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import nl.jixxed.eliteodysseymaterials.domain.EngineerRecipe;
import nl.jixxed.eliteodysseymaterials.enums.RecipeName;

public class EngineerTitledPane extends TitledPane {
    private final EngineerRecipe engineerRecipe;

    public EngineerTitledPane(final RecipeName recipeName, final Node content, final EngineerRecipe engineerRecipe) {
        super(recipeName.friendlyName(), content);
        this.engineerRecipe = engineerRecipe;
    }

    public EngineerRecipe getEngineerRecipe() {
        return this.engineerRecipe;
    }

}
