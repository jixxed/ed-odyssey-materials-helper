package nl.jixxed.eliteodysseymaterials.domain;

import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Recipe {
    private final Map<Material, Integer> assets;
    private final Map<Material, Integer> data;
    private final Map<Material, Integer> goods;
    private final RecipeName recipeName;
    private final Map<Modifier, String> modifiers;

    public Recipe(final RecipeName recipeName, final Map<? extends Material, Integer> materials) {
        this(recipeName, materials, Collections.emptyMap());
    }

    public Recipe(final RecipeName recipeName, final Map<? extends Material, Integer> materials, final Map<Modifier, String> modifiers) {
        this.recipeName = recipeName;
        this.modifiers = modifiers;
        this.assets = materials.entrySet().stream().filter(materialIntegerEntry -> materialIntegerEntry.getKey() instanceof Asset).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        this.data = materials.entrySet().stream().filter(materialIntegerEntry -> materialIntegerEntry.getKey() instanceof Data).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        this.goods = materials.entrySet().stream().filter(materialIntegerEntry -> materialIntegerEntry.getKey() instanceof Good).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public <T extends Material> Map<? extends Material, Integer> getMaterialCollection(final Class<T> clazz) {
        if (clazz.equals(Data.class)) {
            return this.data;
        } else if (clazz.equals(Good.class)) {
            return this.goods;
        } else if (clazz.equals(Asset.class)) {
            return this.assets;
        } else if (clazz.equals(Material.class)) {
            return Stream.concat(Stream.concat(this.goods.entrySet().stream(), this.assets.entrySet().stream()), this.data.entrySet().stream()).collect(
                    Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        throw new ClassCastException("Invalid Material Collection Type");

    }

    public boolean hasIngredient(final Material material) {
        return getMaterialCollection(Material.class).containsKey(material);
    }

    public Integer getRequiredAmount(final Material material) {
        final Integer amount = getMaterialCollection(Material.class).get(material);
        return amount != null ? amount : 0;
    }

    public RecipeName getRecipeName() {
        return this.recipeName;
    }

    public Map<Modifier, String> getModifiers() {
        return this.modifiers;
    }
}
