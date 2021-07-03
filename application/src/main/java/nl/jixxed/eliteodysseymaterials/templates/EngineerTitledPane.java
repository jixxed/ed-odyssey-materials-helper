package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import nl.jixxed.eliteodysseymaterials.domain.EngineerRecipe;
import nl.jixxed.eliteodysseymaterials.enums.RecipeName;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

public class EngineerTitledPane extends TitledPane {
    private final EngineerRecipe engineerRecipe;

    public EngineerTitledPane(final RecipeName recipeName, final Node content, final EngineerRecipe engineerRecipe) {
        super();
        this.setContent(content);
        this.textProperty().bind(LocaleService.getStringBinding(recipeName.getLocalizationKey()));
        this.engineerRecipe = engineerRecipe;
    }

    public EngineerRecipe getEngineerRecipe() {
        return this.engineerRecipe;
    }

}
