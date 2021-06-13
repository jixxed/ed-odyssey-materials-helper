package nl.jixxed.eliteodysseymaterials.domain;

import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.Material;

import java.util.List;
import java.util.Map;

public class ModuleRecipe extends Recipe {
    private final List<Engineer> engineers;

    public ModuleRecipe(final Map<? extends Material, Integer> materials, final List<Engineer> engineers) {
        super(materials);
        this.engineers = engineers;
    }

    public List<Engineer> getEngineers() {
        return this.engineers;
    }
}
