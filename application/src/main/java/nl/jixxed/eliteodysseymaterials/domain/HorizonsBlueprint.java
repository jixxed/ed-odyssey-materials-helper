package nl.jixxed.eliteodysseymaterials.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class HorizonsBlueprint {
    private final Map<HorizonsMaterial, Integer> raw;
    private final Map<HorizonsMaterial, Integer> encoded;
    private final Map<HorizonsMaterial, Integer> manufactured;
    @EqualsAndHashCode.Include
    @Getter
    private final HorizonsBlueprintObjectName horizonsBlueprintObjectName;
    @Getter
    private final HorizonsBlueprintModificationType horizonsBlueprintModificationType;
    @Getter
    private final HorizonsBlueprintGrade horizonsBlueprintGrade;
    @Getter
    private final Map<HorizonsModifier, HorizonsModifierValue> modifiers;
    @Getter
    private final List<Engineer> engineers;

    public HorizonsBlueprint(final HorizonsBlueprintObjectName horizonsBlueprintObjectName, final HorizonsBlueprintModificationType horizonsBlueprintModificationType, final HorizonsBlueprintGrade horizonsBlueprintGrade, final Map<? extends HorizonsMaterial, Integer> materials, final List<Engineer> engineers) {
        this(horizonsBlueprintObjectName, horizonsBlueprintModificationType, horizonsBlueprintGrade, materials, Collections.emptyMap(), engineers);
    }

    public HorizonsBlueprint(final HorizonsBlueprintObjectName horizonsBlueprintObjectName, final HorizonsBlueprintModificationType horizonsBlueprintModificationType, final HorizonsBlueprintGrade horizonsBlueprintGrade, final Map<? extends HorizonsMaterial, Integer> materials, final Map<HorizonsModifier, HorizonsModifierValue> modifiers, final List<Engineer> engineers) {
        this.horizonsBlueprintObjectName = horizonsBlueprintObjectName;
        this.horizonsBlueprintModificationType = horizonsBlueprintModificationType;
        this.horizonsBlueprintGrade = horizonsBlueprintGrade;
        this.modifiers = modifiers;
        this.engineers = engineers;
        this.raw = materials.entrySet().stream().filter(materialIntegerEntry -> materialIntegerEntry.getKey() instanceof Raw).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        this.encoded = materials.entrySet().stream().filter(materialIntegerEntry -> materialIntegerEntry.getKey() instanceof Encoded).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        this.manufactured = materials.entrySet().stream().filter(materialIntegerEntry -> materialIntegerEntry.getKey() instanceof Manufactured).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public <T extends HorizonsMaterial> Map<HorizonsMaterial, Integer> getMaterialCollection(final Class<T> clazz) {
        if (clazz.equals(Raw.class)) {
            return this.raw;
        } else if (clazz.equals(Encoded.class)) {
            return this.encoded;
        } else if (clazz.equals(Manufactured.class)) {
            return this.manufactured;
        } else if (clazz.equals(HorizonsMaterial.class)) {
            return Stream.concat(Stream.concat(this.raw.entrySet().stream(), this.encoded.entrySet().stream()), this.manufactured.entrySet().stream()).collect(
                    Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        throw new ClassCastException("Invalid Material Collection Type");

    }

    public boolean hasIngredient(final HorizonsMaterial material) {
        return getMaterialCollection(HorizonsMaterial.class).containsKey(material);
    }

    public Integer getRequiredAmount(final HorizonsMaterial material) {
        final Integer amount = getMaterialCollection(HorizonsMaterial.class).get(material);
        return amount != null ? amount : 0;
    }

}
