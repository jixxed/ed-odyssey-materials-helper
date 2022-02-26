package nl.jixxed.eliteodysseymaterials.domain;

import lombok.EqualsAndHashCode;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class EngineerBlueprint extends OdysseyBlueprint {
    private final List<String> other;
    private final Supplier<Boolean> isCompletedSupplier;

    public EngineerBlueprint(final BlueprintName blueprintName, final Map<? extends OdysseyMaterial, Integer> materials, final Supplier<Boolean> isCompletedSupplier) {
        super(blueprintName, materials);
        this.other = Collections.emptyList();
        this.isCompletedSupplier = isCompletedSupplier;

    }

    public EngineerBlueprint(final BlueprintName blueprintName, final List<String> other, final Supplier<Boolean> isCompletedSupplier) {
        super(blueprintName, Collections.emptyMap());
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
