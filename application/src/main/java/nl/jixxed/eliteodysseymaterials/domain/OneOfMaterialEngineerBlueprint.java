/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
