package nl.jixxed.eliteodysseymaterials.domain;

import lombok.EqualsAndHashCode;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class HorizonsEngineerBlueprint extends HorizonsBlueprint {
    private final List<String> other;
    private final Supplier<Boolean> isCompletedSupplier;


    public HorizonsEngineerBlueprint(final HorizonsBlueprintName blueprintName, final List<String> other, final Supplier<Boolean> isCompletedSupplier) {
        this(blueprintName, Collections.emptyMap(), other, isCompletedSupplier);
    }

    public HorizonsEngineerBlueprint(final HorizonsBlueprintName blueprintName, final Map<? extends HorizonsMaterial, Integer> materials, final List<String> other, final Supplier<Boolean> isCompletedSupplier) {
        super(blueprintName, materials);
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
