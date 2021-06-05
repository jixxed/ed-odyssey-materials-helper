package nl.jixxed.eliteodysseymaterials;

import nl.jixxed.eliteodysseymaterials.enums.Asset;
import nl.jixxed.eliteodysseymaterials.enums.Data;
import nl.jixxed.eliteodysseymaterials.enums.Good;

import java.util.Map;

public class Recipe {
    private final Map<Asset, Integer> assets;
    private final Map<Data, Integer> data;
    private final Map<Good, Integer> goods;

    public Recipe(final Map<Good, Integer> goods, final Map<Data, Integer> data, final Map<Asset, Integer> assets) {
        this.assets = assets;
        this.data = data;
        this.goods = goods;
    }

    public Map<Asset, Integer> getAssets() {
        return this.assets;
    }

    public Map<Data, Integer> getData() {
        return this.data;
    }

    public Map<Good, Integer> getGoods() {
        return this.goods;
    }

}
