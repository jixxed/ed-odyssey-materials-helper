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

import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyStorageType;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.service.StorageService;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class AnyRelevantStorage extends Storage {

    @Override
    public Integer getBackPackValue() {
        return 0;
    }

    @Override
    public Integer getShipLockerValue() {
        return 0;
    }

    @Override
    public void setValue(final Integer value, final StoragePool pool) {
        //NOOP
    }

    @Override
    public Integer getTotalValue() {
        final Map<OdysseyMaterial, Storage> goods = StorageService.getMaterials(OdysseyStorageType.GOOD);
        final Map<OdysseyMaterial, Storage> data = StorageService.getMaterials(OdysseyStorageType.DATA);
        final Map<OdysseyMaterial, Storage> assets = StorageService.getMaterials(OdysseyStorageType.ASSET);
        final Map<OdysseyMaterial, Storage> materials = new HashMap<>();
        goods.forEach((key, value) -> materials.merge(key, value, (v1, v2) -> value));
        data.forEach((key, value) -> materials.merge(key, value, (v1, v2) -> value));
        assets.forEach((key, value) -> materials.merge(key, value, (v1, v2) -> value));
        return materials.entrySet().stream()
                .filter(material -> OdysseyBlueprintConstants.isBlueprintIngredientWithOverride(material.getKey()))
                .map(entry -> entry.getValue().getTotalValue())
                .reduce(0, Integer::sum);
    }

    @Override
    public Integer getValue(final StoragePool target) {
        return switch (target) {
            case BACKPACK, SHIPLOCKER, SHIP, FLEETCARRIER, SQUADRONCARRIER, SRV -> 0;
        };
    }
}
