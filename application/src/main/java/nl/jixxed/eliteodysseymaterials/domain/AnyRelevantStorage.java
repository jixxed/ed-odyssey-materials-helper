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
            case BACKPACK, SHIPLOCKER, SHIP, FLEETCARRIER, SRV -> 0;
        };
    }
}
