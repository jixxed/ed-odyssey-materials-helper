package nl.jixxed.eliteodysseymaterials.domain;

import lombok.EqualsAndHashCode;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.StorageService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class OneOfMaterialEngineerBlueprint extends EngineerBlueprint {


    public OneOfMaterialEngineerBlueprint(final BlueprintName<OdysseyBlueprintName> blueprintName, final Map<? extends OdysseyMaterial, Integer> materials, final Supplier<Boolean> isCompletedSupplier) {
        super(blueprintName, materials, isCompletedSupplier);

    }

    public OneOfMaterialEngineerBlueprint(final BlueprintName<OdysseyBlueprintName> blueprintName, final List<String> other, final Supplier<Boolean> isCompletedSupplier) {
        super(blueprintName, other, isCompletedSupplier);

    }


    @Override
    public Craftability getCraftability() {
        final AtomicBoolean hasGoods = new AtomicBoolean(false);
        final AtomicBoolean hasData = new AtomicBoolean(false);
        final AtomicBoolean hasAssets = new AtomicBoolean(false);
        getMaterialCollection(Good.class).forEach((material, amountRequired) -> hasGoods.set(hasGoods.get() || (StorageService.getMaterialStorage(material).getTotalValue() - amountRequired) >= 0));
        getMaterialCollection(Data.class).forEach((material, amountRequired) -> hasData.set(hasData.get() || (StorageService.getMaterialStorage(material).getTotalValue() - amountRequired) >= 0));
        getMaterialCollection(Asset.class).forEach((material, amountRequired) -> hasAssets.set(hasAssets.get() || (StorageService.getMaterialStorage(material).getTotalValue() - amountRequired) >= 0));
        if (hasGoods.get() || hasData.get() || hasAssets.get()) {
            return Craftability.CRAFTABLE;
        } else if (hasGoods.get() && hasData.get() && !hasAssets.get()) {
            return Craftability.CRAFTABLE_WITH_TRADE;
        } else {
            return Craftability.NOT_CRAFTABLE;
        }
    }
}
