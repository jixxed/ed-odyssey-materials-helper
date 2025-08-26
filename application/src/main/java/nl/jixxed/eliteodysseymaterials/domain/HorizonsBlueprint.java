package nl.jixxed.eliteodysseymaterials.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class HorizonsBlueprint implements Blueprint<HorizonsBlueprintName> {
    private final Map<HorizonsMaterial, Integer> raw;
    private final Map<HorizonsMaterial, Integer> encoded;
    private final Map<HorizonsMaterial, Integer> manufactured;
    private final Map<HorizonsMaterial, Integer> commodities;
    @EqualsAndHashCode.Include
    @Getter
    private final HorizonsBlueprintName horizonsBlueprintName;
    @EqualsAndHashCode.Include
    @Getter
    private final HorizonsBlueprintType horizonsBlueprintType;
    @EqualsAndHashCode.Include
    @Getter
    private final HorizonsBlueprintGrade horizonsBlueprintGrade;
    @Getter
    private final Map<HorizonsModifier, HorizonsModifierValue> modifiers;
    @Getter
    private final List<Engineer> engineers;
    @Getter
    private final GameVersion gameVersion;
    @Getter
    private final boolean preEngineered;

    public HorizonsBlueprint(final HorizonsBlueprintName horizonsBlueprintName, final Map<? extends HorizonsMaterial, Integer> materials) {
        this(horizonsBlueprintName, HorizonsBlueprintType.ENGINEER, HorizonsBlueprintGrade.NONE, materials, Collections.emptyMap(), List.of());
    }

    public HorizonsBlueprint(final HorizonsBlueprintName horizonsBlueprintName, final HorizonsBlueprintType horizonsBlueprintType, final Map<? extends HorizonsMaterial, Integer> materials, final Map<HorizonsModifier, HorizonsModifierValue> modifiers, final List<Engineer> engineers) {
        this(horizonsBlueprintName, horizonsBlueprintType, HorizonsBlueprintGrade.NONE, materials, modifiers, engineers);
    }
    public HorizonsBlueprint(final HorizonsBlueprintName horizonsBlueprintName, final HorizonsBlueprintType horizonsBlueprintType, final Map<? extends HorizonsMaterial, Integer> materials, final Map<HorizonsModifier, HorizonsModifierValue> modifiers, final List<Engineer> engineers, final boolean preEngineered) {
        this(horizonsBlueprintName, horizonsBlueprintType, HorizonsBlueprintGrade.NONE, materials, modifiers, engineers, preEngineered);
    }
    public HorizonsBlueprint(final HorizonsBlueprintName horizonsBlueprintName, final HorizonsBlueprintType horizonsBlueprintType, final HorizonsBlueprintGrade horizonsBlueprintGrade, final Map<? extends HorizonsMaterial, Integer> materials, final Map<HorizonsModifier, HorizonsModifierValue> modifiers, final List<Engineer> engineers, final GameVersion gameVersion) {
        this(horizonsBlueprintName, horizonsBlueprintType, horizonsBlueprintGrade, materials, modifiers, engineers, gameVersion, false);
    }
    public HorizonsBlueprint(final HorizonsBlueprintName horizonsBlueprintName, final HorizonsBlueprintType horizonsBlueprintType, final HorizonsBlueprintGrade horizonsBlueprintGrade, final Map<? extends HorizonsMaterial, Integer> materials, final Map<HorizonsModifier, HorizonsModifierValue> modifiers, final List<Engineer> engineers, final GameVersion gameVersion, final boolean preEngineered) {
        this.horizonsBlueprintName = horizonsBlueprintName;
        this.horizonsBlueprintType = horizonsBlueprintType;
        this.horizonsBlueprintGrade = horizonsBlueprintGrade;
        this.modifiers = modifiers;
        this.engineers = engineers;
        this.raw = materials.entrySet().stream().filter(materialIntegerEntry -> materialIntegerEntry.getKey() instanceof Raw).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        this.encoded = materials.entrySet().stream().filter(materialIntegerEntry -> materialIntegerEntry.getKey() instanceof Encoded).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        this.manufactured = materials.entrySet().stream().filter(materialIntegerEntry -> materialIntegerEntry.getKey() instanceof Manufactured).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        this.commodities = materials.entrySet().stream().filter(materialIntegerEntry -> materialIntegerEntry.getKey() instanceof Commodity).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        this.gameVersion = gameVersion;
        this.preEngineered = preEngineered;
    }

    public HorizonsBlueprint(final HorizonsBlueprintName horizonsBlueprintName, final HorizonsBlueprintType horizonsBlueprintType, final HorizonsBlueprintGrade horizonsBlueprintGrade, final Map<? extends HorizonsMaterial, Integer> materials, final Map<HorizonsModifier, HorizonsModifierValue> modifiers, final List<Engineer> engineers) {
        this(horizonsBlueprintName, horizonsBlueprintType, horizonsBlueprintGrade, materials, modifiers, engineers, GameVersion.LEGACY, false);
    }
    public HorizonsBlueprint(final HorizonsBlueprintName horizonsBlueprintName, final HorizonsBlueprintType horizonsBlueprintType, final HorizonsBlueprintGrade horizonsBlueprintGrade, final Map<? extends HorizonsMaterial, Integer> materials, final Map<HorizonsModifier, HorizonsModifierValue> modifiers, final List<Engineer> engineers, final boolean preEngineered) {
        this(horizonsBlueprintName, horizonsBlueprintType, horizonsBlueprintGrade, materials, modifiers, engineers, GameVersion.LEGACY, preEngineered);
    }

    public <T extends HorizonsMaterial> Map<HorizonsMaterial, Integer> getMaterialCollection(final Class<T> clazz) {
        if (clazz.equals(Raw.class)) {
            return this.raw;
        } else if (clazz.equals(Encoded.class)) {
            return this.encoded;
        } else if (clazz.equals(Manufactured.class)) {
            return this.manufactured;
        } else if (clazz.equals(RegularCommodity.class) || clazz.equals(RareCommodity.class) || clazz.equals(Commodity.class)) {
            return this.commodities;
        } else if (clazz.equals(HorizonsMaterial.class)) {
            return Stream.concat(Stream.concat(Stream.concat(this.raw.entrySet().stream(), this.encoded.entrySet().stream()), this.manufactured.entrySet().stream()), this.commodities.entrySet().stream()).collect(
                    Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        throw new ClassCastException("Invalid Material Collection Type");

    }

    public boolean hasIngredient(final HorizonsMaterial material) {
        return getMaterialCollection(HorizonsMaterial.class).containsKey(material);
    }

    public Integer getRequiredAmount(final HorizonsMaterial material, final Engineer engineer) {
        final Integer amount = getMaterialCollection(HorizonsMaterial.class).get(material);
        return amount != null ? amount * getNumberOfRolls(engineer) : 0;
    }

    public Integer getMinimumAmount(final HorizonsMaterial material) {
        final Engineer engineer = getEngineers().stream().max(Comparator.comparing(eng -> ApplicationState.getInstance().getEngineerRank(eng))).orElse(null);

        final Integer amount = getMaterialCollection(HorizonsMaterial.class).get(material);
        return amount != null ? amount * getNumberOfRolls(engineer) : 0;
    }

    public Integer getMaximumAmount(final HorizonsMaterial material) {
        final Engineer engineer = getEngineers().stream().min(Comparator.comparing(eng -> ApplicationState.getInstance().getEngineerRank(eng))).orElse(null);
        final Integer amount = getMaterialCollection(HorizonsMaterial.class).get(material);

        return amount != null ? amount * getNumberOfRolls(engineer) : 0;
    }

    private int getNumberOfRolls(Engineer engineer) {
        return this instanceof HorizonsModuleBlueprint ? horizonsBlueprintGrade.getNumberOfRolls(engineer, horizonsBlueprintType) : 1;
    }

    @Override
    public HorizonsBlueprintName getBlueprintName() {
        return this.getHorizonsBlueprintName();
    }

}
