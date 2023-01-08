package nl.jixxed.eliteodysseymaterials.domain;

import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.List;
import java.util.Map;

public class HorizonsTechBrokerBlueprint extends HorizonsBlueprint {
    @Getter
    private final List<HorizonsBrokerType> horizonsBrokerTypes;

    public HorizonsTechBrokerBlueprint(final HorizonsBlueprintName horizonsBlueprintName, final HorizonsBlueprintType horizonsBlueprintType, final Map<? extends HorizonsMaterial, Integer> materials, final Map<HorizonsModifier, HorizonsModifierValue> modifiers, final List<Engineer> engineers, final List<HorizonsBrokerType> horizonsBrokerTypes) {
        super(horizonsBlueprintName, horizonsBlueprintType, materials, modifiers, engineers);
        this.horizonsBrokerTypes = horizonsBrokerTypes;
    }
}
