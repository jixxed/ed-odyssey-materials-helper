package nl.jixxed.eliteodysseymaterials.domain;

import lombok.EqualsAndHashCode;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.Craftability;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.service.StorageService;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class TotalMaterialEngineerBlueprint extends EngineerBlueprint {
    private final Integer totalRequired;

    public TotalMaterialEngineerBlueprint(final BlueprintName<OdysseyBlueprintName> blueprintName, final Map<? extends OdysseyMaterial, Integer> materials, final Supplier<Boolean> isCompletedSupplier, final Integer totalRequired) {
        super(blueprintName, materials, isCompletedSupplier);
        this.totalRequired = totalRequired;


    }

    public TotalMaterialEngineerBlueprint(final BlueprintName<OdysseyBlueprintName> blueprintName, final List<String> other, final Supplier<Boolean> isCompletedSupplier, final Integer totalRequired) {
        super(blueprintName, other, isCompletedSupplier);


        this.totalRequired = totalRequired;
    }

    @Override
    public Craftability getCraftability() {

        final Integer total = getMaterialCollection(OdysseyMaterial.class).entrySet().stream().map(materialEntry -> StorageService.getMaterialStorage(materialEntry.getKey()).getTotalValue()).reduce(0, Integer::sum);

        if (total > this.totalRequired) {
            return Craftability.CRAFTABLE;
        } else {
            return Craftability.NOT_CRAFTABLE;
        }
    }
}
