package nl.jixxed.eliteodysseymaterials.domain;

import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HorizonsSynthesisBlueprint extends HorizonsBlueprint {

    private final Map<HorizonsMaterial, Integer> alternateRaw;
    private final Map<HorizonsMaterial, Integer> alternateEncoded;
    private final Map<HorizonsMaterial, Integer> alternateManufactured;
    private final Map<HorizonsMaterial, Integer> alternateCommodities;
    private final SquadronPerk[] squadronPerks;

    public HorizonsSynthesisBlueprint(final HorizonsBlueprintName horizonsBlueprintName, final HorizonsBlueprintType horizonsBlueprintType, final HorizonsBlueprintGrade horizonsBlueprintGrade, final Map<? extends HorizonsMaterial, Integer> materials, final Map<? extends HorizonsMaterial, Integer> alternateMaterials, final Map<HorizonsModifier, HorizonsModifierValue> modifiers, SquadronPerk... squadronPerks) {
        super(horizonsBlueprintName, horizonsBlueprintType, horizonsBlueprintGrade, materials, modifiers, List.of());
        this.squadronPerks = squadronPerks;
        this.alternateRaw = alternateMaterials.entrySet().stream().filter(materialIntegerEntry -> materialIntegerEntry.getKey() instanceof Raw).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        this.alternateEncoded = alternateMaterials.entrySet().stream().filter(materialIntegerEntry -> materialIntegerEntry.getKey() instanceof Encoded).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        this.alternateManufactured = alternateMaterials.entrySet().stream().filter(materialIntegerEntry -> materialIntegerEntry.getKey() instanceof Manufactured).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        this.alternateCommodities = alternateMaterials.entrySet().stream().filter(materialIntegerEntry -> materialIntegerEntry.getKey() instanceof Commodity).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public HorizonsSynthesisBlueprint(final HorizonsBlueprintName horizonsBlueprintName, final HorizonsBlueprintType horizonsBlueprintType, final HorizonsBlueprintGrade horizonsBlueprintGrade, final Map<? extends HorizonsMaterial, Integer> materials, final Map<? extends HorizonsMaterial, Integer> alternateMaterials, final Map<HorizonsModifier, HorizonsModifierValue> modifiers, final GameVersion gameVersion, SquadronPerk... squadronPerks) {
        this(horizonsBlueprintName, horizonsBlueprintType, horizonsBlueprintGrade, materials, alternateMaterials, modifiers);
    }

    public HorizonsSynthesisBlueprint(final HorizonsBlueprintName horizonsBlueprintName, final HorizonsBlueprintType horizonsBlueprintType, final HorizonsBlueprintGrade horizonsBlueprintGrade, final Map<? extends HorizonsMaterial, Integer> materials, final Map<HorizonsModifier, HorizonsModifierValue> modifiers) {
        this(horizonsBlueprintName, horizonsBlueprintType, horizonsBlueprintGrade, materials, Collections.emptyMap(), modifiers);
    }

    public HorizonsSynthesisBlueprint(final HorizonsBlueprintName horizonsBlueprintName, final HorizonsBlueprintType horizonsBlueprintType, final HorizonsBlueprintGrade horizonsBlueprintGrade, final Map<? extends HorizonsMaterial, Integer> materials, final Map<HorizonsModifier, HorizonsModifierValue> modifiers, final GameVersion gameVersion) {
        this(horizonsBlueprintName, horizonsBlueprintType, horizonsBlueprintGrade, materials, Collections.emptyMap(), modifiers);
    }

    @Override
    public <T extends HorizonsMaterial> Map<HorizonsMaterial, Integer> getMaterialCollection(final Class<T> clazz) {
        if (isPerked()) {
            if (clazz.equals(Raw.class)) {
                return this.alternateRaw;
            } else if (clazz.equals(Encoded.class)) {
                return this.alternateEncoded;
            } else if (clazz.equals(Manufactured.class)) {
                return this.alternateManufactured;
            } else if (clazz.equals(RegularCommodity.class) || clazz.equals(RareCommodity.class) || clazz.equals(Commodity.class)) {
                return this.alternateCommodities;
            } else if (clazz.equals(HorizonsMaterial.class)) {
                return Stream.concat(Stream.concat(Stream.concat(this.alternateRaw.entrySet().stream(), this.alternateEncoded.entrySet().stream()), this.alternateManufactured.entrySet().stream()), this.alternateCommodities.entrySet().stream()).collect(
                        Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            }
            throw new ClassCastException("Invalid Material Collection Type");
        }
        return super.getMaterialCollection(clazz);
    }

    private boolean isPerked() {
        return Arrays.stream(squadronPerks)
                .anyMatch(perk -> perk.equals(ApplicationState.getInstance().getPrimaryPerk()) || perk.equals(ApplicationState.getInstance().getFactionPerk()));
    }
}
