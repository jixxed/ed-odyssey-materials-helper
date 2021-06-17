package nl.jixxed.eliteodysseymaterials.domain;

import nl.jixxed.eliteodysseymaterials.enums.Asset;
import nl.jixxed.eliteodysseymaterials.enums.Data;
import nl.jixxed.eliteodysseymaterials.enums.Good;
import nl.jixxed.eliteodysseymaterials.enums.Material;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Recipe {
    private final Map<Material, Integer> assets;
    private final Map<Material, Integer> data;
    private final Map<Material, Integer> goods;

    public Recipe(final Map<? extends Material, Integer> materials) {
        this.assets = materials.entrySet().stream().filter(materialIntegerEntry -> materialIntegerEntry.getKey() instanceof Asset).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        this.data = materials.entrySet().stream().filter(materialIntegerEntry -> materialIntegerEntry.getKey() instanceof Data).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        this.goods = materials.entrySet().stream().filter(materialIntegerEntry -> materialIntegerEntry.getKey() instanceof Good).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public <T extends Material> Map<T, Integer> getMaterialCollection(final Class<T> clazz) {
        if (clazz.equals(Data.class)) {
            return (Map<T, Integer>) this.data;
        } else if (clazz.equals(Good.class)) {
            return (Map<T, Integer>) this.goods;
        } else if (clazz.equals(Asset.class)) {
            return (Map<T, Integer>) this.assets;
        } else if (clazz.equals(Material.class)) {
            final Map<Material, Integer> combined = Stream.concat(Stream.concat(this.goods.entrySet().stream(), this.assets.entrySet().stream()), this.data.entrySet().stream()).collect(
                    Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            return (Map<T, Integer>) combined;
        }
        throw new ClassCastException("Invalid Material Collection Type");

    }

}
