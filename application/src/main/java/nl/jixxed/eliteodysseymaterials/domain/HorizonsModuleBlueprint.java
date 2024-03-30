package nl.jixxed.eliteodysseymaterials.domain;

import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.PinnedBlueprintService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HorizonsModuleBlueprint extends HorizonsBlueprint implements HorizonsEngineeringBlueprint {
    private final List<Engineer> engineers;

    public HorizonsModuleBlueprint(final HorizonsBlueprintName horizonsBlueprintName, final HorizonsBlueprintType horizonsBlueprintType, final HorizonsBlueprintGrade horizonsBlueprintGrade, final Map<? extends HorizonsMaterial, Integer> materials, final Map<HorizonsModifier, HorizonsModifierValue> modifiers, final List<Engineer> engineers) {
        super(horizonsBlueprintName, horizonsBlueprintType, horizonsBlueprintGrade, materials, modifiers, engineers);
        this.engineers = engineers;
    }

    public HorizonsModuleBlueprint(final HorizonsBlueprintName horizonsBlueprintName, final HorizonsBlueprintType horizonsBlueprintType, final HorizonsBlueprintGrade horizonsBlueprintGrade, final Map<? extends HorizonsMaterial, Integer> materials, final Map<HorizonsModifier, HorizonsModifierValue> modifiers, final List<Engineer> engineers, final boolean preEngineered) {
        super(horizonsBlueprintName, horizonsBlueprintType, horizonsBlueprintGrade, materials, modifiers, engineers, preEngineered);
        this.engineers = engineers;
    }

    public HorizonsModuleBlueprint(final HorizonsBlueprintName horizonsBlueprintName, final HorizonsBlueprintType horizonsBlueprintType, final HorizonsBlueprintGrade horizonsBlueprintGrade, final Map<? extends HorizonsMaterial, Integer> materials, final Map<HorizonsModifier, HorizonsModifierValue> modifiers, final List<Engineer> engineers, final GameVersion gameVersion) {
        super(horizonsBlueprintName, horizonsBlueprintType, horizonsBlueprintGrade, materials, modifiers, engineers, gameVersion);
        this.engineers = engineers;
    }

    @Override
    public List<Engineer> getEngineers() {
        if (this.engineers.stream().anyMatch(engineer -> PinnedBlueprintService.isPinned(engineer,this))) {
            final List<Engineer> withRemote = new ArrayList<>(this.engineers);
            withRemote.add(Engineer.REMOTE_WORKSHOP);
            return withRemote;
        }
        return this.engineers;
    }


    @Override
    public boolean hasSingleEngineerPerRegion() {
        return this.engineers != null && this.engineers.size() == 2;
    }
}
