package nl.jixxed.eliteodysseymaterials;

import nl.jixxed.eliteodysseymaterials.enums.Component;
import nl.jixxed.eliteodysseymaterials.enums.Data;
import nl.jixxed.eliteodysseymaterials.enums.Goods;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Ingredients {
    private final Map<Component, Integer> components;
    private final Map<Data, Integer> data;
    private final Map<Goods, Integer> goods;
    private final List<String> other;

    public Ingredients(Map<Goods, Integer> goods, Map<Data, Integer> data, Map<Component, Integer> components) {
        this.components = components;
        this.data = data;
        this.goods = goods;
        this.other = Collections.emptyList();
    }

    public Ingredients(Map<Goods, Integer> goods, Map<Data, Integer> data, Map<Component, Integer> components, List<String> other) {
        this.components = components;
        this.data = data;
        this.goods = goods;
        this.other = other;
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

    public List<String> getOther() {
        return other;
    }
}
