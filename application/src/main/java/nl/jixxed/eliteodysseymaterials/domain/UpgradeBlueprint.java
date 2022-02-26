package nl.jixxed.eliteodysseymaterials.domain;

import lombok.EqualsAndHashCode;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyModifier;

import java.util.Map;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class UpgradeBlueprint extends OdysseyBlueprint {
    public UpgradeBlueprint(final OdysseyBlueprintName odysseyBlueprintName, final Map<? extends OdysseyMaterial, Integer> materials) {
        super(odysseyBlueprintName, materials);
    }

    public UpgradeBlueprint(final OdysseyBlueprintName odysseyBlueprintName, final Map<? extends OdysseyMaterial, Integer> materials, final Map<OdysseyModifier, String> modifiers) {
        super(odysseyBlueprintName, materials, modifiers);
    }
}
