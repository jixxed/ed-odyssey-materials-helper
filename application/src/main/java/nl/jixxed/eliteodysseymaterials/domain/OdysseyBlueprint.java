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
import lombok.ToString;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class OdysseyBlueprint implements Blueprint<OdysseyBlueprintName> {
    private final Map<OdysseyMaterial, Integer> assets;
    private final Map<OdysseyMaterial, Integer> data;
    private final Map<OdysseyMaterial, Integer> goods;
    @EqualsAndHashCode.Include
    private final BlueprintName<OdysseyBlueprintName> blueprintName;
    private final Map<OdysseyModifier, String> modifiers;

    protected OdysseyBlueprint(final BlueprintName<OdysseyBlueprintName> blueprintName, final Map<? extends OdysseyMaterial, Integer> materials) {
        this(blueprintName, materials, Collections.emptyMap());
    }

    protected OdysseyBlueprint(final BlueprintName<OdysseyBlueprintName> blueprintName, final Map<? extends OdysseyMaterial, Integer> materials, final Map<OdysseyModifier, String> modifiers) {
        this.blueprintName = blueprintName;
        this.modifiers = modifiers;
        this.assets = materials.entrySet().stream().filter(materialIntegerEntry -> materialIntegerEntry.getKey() instanceof Asset).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        this.data = materials.entrySet().stream().filter(materialIntegerEntry -> materialIntegerEntry.getKey() instanceof Data).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        this.goods = materials.entrySet().stream().filter(materialIntegerEntry -> materialIntegerEntry.getKey() instanceof Good).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    public <T extends OdysseyMaterial> Map<OdysseyMaterial, Integer> getMaterialCollection(final Class<T> clazz) {
        if (clazz.equals(Data.class)) {
            return this.data;
        } else if (clazz.equals(Good.class)) {
            return this.goods;
        } else if (clazz.equals(Asset.class)) {
            return this.assets;
        } else if (clazz.equals(OdysseyMaterial.class)) {
            return Stream.concat(Stream.concat(this.goods.entrySet().stream(), this.assets.entrySet().stream()), this.data.entrySet().stream()).collect(
                    Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        throw new ClassCastException("Invalid Material Collection Type");

    }

    public boolean hasIngredient(final OdysseyMaterial odysseyMaterial) {
        return getMaterialCollection(OdysseyMaterial.class).containsKey(odysseyMaterial);
    }

    public Integer getRequiredAmount(final OdysseyMaterial odysseyMaterial) {
        final Integer amount = getMaterialCollection(OdysseyMaterial.class).get(odysseyMaterial);
        return amount != null ? amount : 0;
    }

    @Override
    public BlueprintName<OdysseyBlueprintName> getBlueprintName() {
        return this.blueprintName;
    }

    public Map<OdysseyModifier, String> getModifiers() {
        return this.modifiers;
    }
}
