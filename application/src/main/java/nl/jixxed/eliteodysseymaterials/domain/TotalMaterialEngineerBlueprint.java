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
