package nl.jixxed.eliteodysseymaterials.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyModifier;

import java.util.List;
import java.util.Map;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class ModuleBlueprint extends OdysseyBlueprint {
    private final List<Engineer> engineers;

    public ModuleBlueprint(final OdysseyBlueprintName odysseyBlueprintName, final Map<? extends OdysseyMaterial, Integer> materials, final List<Engineer> engineers) {
        super(odysseyBlueprintName, materials);
        this.engineers = engineers;
    }

    public ModuleBlueprint(final OdysseyBlueprintName odysseyBlueprintName, final Map<? extends OdysseyMaterial, Integer> materials, final List<Engineer> engineers, final Map<OdysseyModifier, String> modifiers) {
        super(odysseyBlueprintName, materials, modifiers);
        this.engineers = engineers;
    }

    public List<Engineer> getEngineers() {
        return this.engineers;
    }

    public boolean hasSingleEngineerPerRegion() {
        return this.engineers != null && this.engineers.size() == 2;
    }
}
