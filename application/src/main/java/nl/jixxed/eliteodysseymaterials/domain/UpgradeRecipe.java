package nl.jixxed.eliteodysseymaterials.domain;

import lombok.EqualsAndHashCode;
import nl.jixxed.eliteodysseymaterials.enums.Material;
import nl.jixxed.eliteodysseymaterials.enums.Modifier;
import nl.jixxed.eliteodysseymaterials.enums.RecipeName;

import java.util.Map;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class UpgradeRecipe extends Recipe {
    public UpgradeRecipe(final RecipeName recipeName, final Map<? extends Material, Integer> materials) {
        super(recipeName, materials);
    }

    public UpgradeRecipe(final RecipeName recipeName, final Map<? extends Material, Integer> materials, final Map<Modifier, String> modifiers) {
        super(recipeName, materials, modifiers);
    }
}
