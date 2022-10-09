package nl.jixxed.eliteodysseymaterials.domain;

import lombok.EqualsAndHashCode;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.StorageService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class EngineerBlueprint extends OdysseyBlueprint {
    private final List<String> other;
    private final Supplier<Boolean> isCompletedSupplier;

    public EngineerBlueprint(final BlueprintName<OdysseyBlueprintName> blueprintName, final Map<? extends OdysseyMaterial, Integer> materials, final Supplier<Boolean> isCompletedSupplier) {
        super(blueprintName, materials);
        this.other = Collections.emptyList();
        this.isCompletedSupplier = isCompletedSupplier;

    }

    public EngineerBlueprint(final BlueprintName<OdysseyBlueprintName> blueprintName, final List<String> other, final Supplier<Boolean> isCompletedSupplier) {
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

    public Craftability getCraftability() {
        final AtomicBoolean hasGoods = new AtomicBoolean(true);
        final AtomicBoolean hasData = new AtomicBoolean(true);
        final AtomicBoolean hasAssets = new AtomicBoolean(true);
        getMaterialCollection(Good.class).forEach((material, amountRequired) -> hasGoods.set(hasGoods.get() && (StorageService.getMaterialStorage(material).getTotalValue() - amountRequired) >= 0));
        getMaterialCollection(Data.class).forEach((material, amountRequired) -> hasData.set(hasData.get() && (StorageService.getMaterialStorage(material).getTotalValue() - amountRequired) >= 0));
        getMaterialCollection(Asset.class).forEach((material, amountRequired) -> hasAssets.set(hasAssets.get() && (StorageService.getMaterialStorage(material).getTotalValue() - amountRequired) >= 0));
        if (!hasGoods.get() || !hasData.get()) {
            return Craftability.NOT_CRAFTABLE;
        } else if (hasGoods.get() && hasData.get() && !hasAssets.get()) {
            return Craftability.CRAFTABLE_WITH_TRADE;
        } else {
            return Craftability.CRAFTABLE;
        }
    }

    public String getTipsLocalizationKey() {
        return "blueprint.tips." + this.getBlueprintName().lcName();
    }
}
