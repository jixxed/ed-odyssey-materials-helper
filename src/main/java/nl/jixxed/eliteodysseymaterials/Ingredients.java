package nl.jixxed.eliteodysseymaterials;

import java.util.Map;

public class Ingredients {
    Map<Component, Integer> components;
    Map<Data, Integer> data;
    Map<Goods, Integer> goods;

    public Ingredients( Map<Goods, Integer> goods, Map<Data, Integer> data,Map<Component, Integer> components) {
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
