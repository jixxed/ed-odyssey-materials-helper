package nl.jixxed.eliteodysseymaterials;

import nl.jixxed.eliteodysseymaterials.enums.Component;
import nl.jixxed.eliteodysseymaterials.enums.Data;
import nl.jixxed.eliteodysseymaterials.enums.Goods;

import java.util.Map;

public class Recipe {
    private final Map<Component, Integer> components;
    private final Map<Data, Integer> data;
    private final Map<Goods, Integer> goods;

    public Recipe(Map<Goods, Integer> goods, Map<Data, Integer> data, Map<Component, Integer> components) {
        this.components = components;
        this.data = data;
        this.goods = goods;
    }

    public Map<Component, Integer> getComponents() {
        return components;
    }

    public Map<Data, Integer> getData() {
        return data;
    }

    public Map<Goods, Integer> getGoods() {
        return goods;
    }

}
