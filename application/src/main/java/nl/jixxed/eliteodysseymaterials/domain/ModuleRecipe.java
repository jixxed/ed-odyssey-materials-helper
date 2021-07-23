package nl.jixxed.eliteodysseymaterials.domain;

import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.Material;
import nl.jixxed.eliteodysseymaterials.enums.Modifier;
import nl.jixxed.eliteodysseymaterials.enums.RecipeName;

import java.util.List;
import java.util.Map;

public class ModuleRecipe extends Recipe {
    private final List<Engineer> engineers;

    public ModuleRecipe(final RecipeName recipeName, final Map<? extends Material, Integer> materials, final List<Engineer> engineers) {
        super(recipeName, materials);
        this.engineers = engineers;
    }

    public ModuleRecipe(final RecipeName recipeName, final Map<? extends Material, Integer> materials, final List<Engineer> engineers, final Map<Modifier, String> modifiers) {
        super(recipeName, materials, modifiers);
        this.engineers = engineers;
    }

    public List<Engineer> getEngineers() {
        return this.engineers;
    }

}
