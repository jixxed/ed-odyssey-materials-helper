package nl.jixxed.eliteodysseymaterials.domain;

import lombok.EqualsAndHashCode;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyModifier;

import java.util.Map;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class UpgradeBlueprint extends Blueprint {
    public UpgradeBlueprint(final BlueprintName blueprintName, final Map<? extends OdysseyMaterial, Integer> materials) {
        super(blueprintName, materials);
    }

    public UpgradeBlueprint(final BlueprintName blueprintName, final Map<? extends OdysseyMaterial, Integer> materials, final Map<OdysseyModifier, String> modifiers) {
        super(blueprintName, materials, modifiers);
    }
}
