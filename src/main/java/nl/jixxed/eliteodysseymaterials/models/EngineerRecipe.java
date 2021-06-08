package nl.jixxed.eliteodysseymaterials.models;

import nl.jixxed.eliteodysseymaterials.enums.Material;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class EngineerRecipe extends Recipe {
    private final List<String> other;
    private final Supplier<Boolean> isCompletedSupplier;

    public EngineerRecipe(final Map<? extends Material, Integer> materials, final Supplier<Boolean> isCompletedSupplier) {
        super(materials);
        this.other = Collections.emptyList();
        this.isCompletedSupplier = isCompletedSupplier;

    }

    public EngineerRecipe(final List<String> other, final Supplier<Boolean> isCompletedSupplier) {
        super(Collections.emptyMap());
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
