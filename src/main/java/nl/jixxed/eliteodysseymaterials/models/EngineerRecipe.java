package nl.jixxed.eliteodysseymaterials.models;

import nl.jixxed.eliteodysseymaterials.enums.Asset;
import nl.jixxed.eliteodysseymaterials.enums.Data;
import nl.jixxed.eliteodysseymaterials.enums.Good;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class EngineerRecipe extends Recipe {
    private final List<String> other;
    private final Supplier<Boolean> isCompletedSupplier;

    public EngineerRecipe(final Map<Good, Integer> goods, final Map<Data, Integer> data, final Map<Asset, Integer> components, final List<String> other, final Supplier<Boolean> isCompletedSupplier) {
        super(goods, data, components);
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
