package nl.jixxed.eliteodysseymaterials;

import nl.jixxed.eliteodysseymaterials.enums.Component;
import nl.jixxed.eliteodysseymaterials.enums.Data;
import nl.jixxed.eliteodysseymaterials.enums.Goods;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class EngineerRecipe extends Recipe {
    private final List<String> other;
    private final Supplier<Boolean> isCompletedSupplier;

    public EngineerRecipe(Map<Goods, Integer> goods, Map<Data, Integer> data, Map<Component, Integer> components, List<String> other, Supplier<Boolean> isCompletedSupplier) {
        super(goods, data, components);
        this.other = other;
        this.isCompletedSupplier = isCompletedSupplier;
    }

    public List<String> getOther() {
        return other;
    }

    public boolean isCompleted() {
        return this.isCompletedSupplier.get();
    }

    ;
}
