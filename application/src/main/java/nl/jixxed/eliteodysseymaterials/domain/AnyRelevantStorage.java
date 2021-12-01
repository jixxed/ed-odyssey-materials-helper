package nl.jixxed.eliteodysseymaterials.domain;

import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.constants.RecipeConstants;
import nl.jixxed.eliteodysseymaterials.enums.Material;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.enums.StorageType;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class AnyRelevantStorage extends Storage {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

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
        final Map<Material, Storage> goods = APPLICATION_STATE.getMaterials(StorageType.GOOD);
        final Map<Material, Storage> data = APPLICATION_STATE.getMaterials(StorageType.DATA);
        final Map<Material, Storage> assets = APPLICATION_STATE.getMaterials(StorageType.ASSET);
        final Map<Material, Storage> materials = new HashMap<>();
        goods.forEach((key, value) -> materials.merge(key, value, (v1, v2) -> value));
        data.forEach((key, value) -> materials.merge(key, value, (v1, v2) -> value));
        assets.forEach((key, value) -> materials.merge(key, value, (v1, v2) -> value));
        return materials.entrySet().stream()
                .filter(material -> RecipeConstants.isBlueprintIngredient(material.getKey()))
                .map(entry -> entry.getValue().getTotalValue())
                .reduce(0, Integer::sum);
    }

    @Override
    public Integer getValue(final StoragePool target) {
        return switch (target) {
            case BACKPACK, SHIPLOCKER -> 0;
        };
    }
}
