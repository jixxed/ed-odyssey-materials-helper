package nl.jixxed.eliteodysseymaterials.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class HorizonsEngineerBlueprint extends HorizonsBlueprint {
    @Getter
    private final List<String> other;
    @Getter
    private final List<String> leveling;
    private final Supplier<Boolean> isCompletedSupplier;
    @Getter
    private final Engineer engineer;


    public HorizonsEngineerBlueprint(final Engineer engineer, final HorizonsBlueprintName blueprintName, final List<String> other, final List<String> leveling, final Supplier<Boolean> isCompletedSupplier) {
        this(engineer, blueprintName, Collections.emptyMap(), other,leveling, isCompletedSupplier);
    }

    public HorizonsEngineerBlueprint(final Engineer engineer, final HorizonsBlueprintName blueprintName, final Map<? extends HorizonsMaterial, Integer> materials, final List<String> other, final List<String> leveling, final Supplier<Boolean> isCompletedSupplier) {
        super(blueprintName, materials);
        this.engineer = engineer;
        this.other = other;
        this.leveling = leveling;
        this.isCompletedSupplier = isCompletedSupplier;

    }

    public boolean isCompleted() {
        return this.isCompletedSupplier.get();
    }

}
