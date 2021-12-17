package nl.jixxed.eliteodysseymaterials.domain;

import lombok.EqualsAndHashCode;
import nl.jixxed.eliteodysseymaterials.enums.Material;
import nl.jixxed.eliteodysseymaterials.enums.RecipeName;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class EngineerRecipe extends Recipe {
    private final List<String> other;
    private final Supplier<Boolean> isCompletedSupplier;

    public EngineerRecipe(final RecipeName recipeName, final Map<? extends Material, Integer> materials, final Supplier<Boolean> isCompletedSupplier) {
        super(recipeName, materials);
        this.other = Collections.emptyList();
        this.isCompletedSupplier = isCompletedSupplier;

    }

    public EngineerRecipe(final RecipeName recipeName, final List<String> other, final Supplier<Boolean> isCompletedSupplier) {
        super(recipeName, Collections.emptyMap());
        this.other = other;
        this.isCompletedSupplier = isCompletedSupplier;

    }

    public List<String> getOther() {
        return this.other;
    }

    public boolean isCompleted() {
        return this.isCompletedSupplier.get();
    }

}
