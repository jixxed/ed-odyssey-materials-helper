package nl.jixxed.eliteodysseymaterials.domain;

import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HorizonsModuleBlueprint extends HorizonsBlueprint implements HorizonsEngineeringBlueprint {
    private final List<Engineer> engineers;

    public HorizonsModuleBlueprint(final HorizonsBlueprintName horizonsBlueprintName, final HorizonsBlueprintType horizonsBlueprintType, final HorizonsBlueprintGrade horizonsBlueprintGrade, final Map<? extends HorizonsMaterial, Integer> materials, final List<Engineer> engineers) {
        super(horizonsBlueprintName, horizonsBlueprintType, horizonsBlueprintGrade, materials, engineers);
        this.engineers = engineers;
    }

    public HorizonsModuleBlueprint(final HorizonsBlueprintName horizonsBlueprintName, final HorizonsBlueprintType horizonsBlueprintType, final HorizonsBlueprintGrade horizonsBlueprintGrade, final Map<? extends HorizonsMaterial, Integer> materials, final Map<HorizonsModifier, HorizonsModifierValue> modifiers, final List<Engineer> engineers) {
        super(horizonsBlueprintName, horizonsBlueprintType, horizonsBlueprintGrade, materials, modifiers, engineers);
        this.engineers = engineers;
    }

    //experimentals
    public HorizonsModuleBlueprint(final HorizonsBlueprintName horizonsBlueprintName, final HorizonsBlueprintType horizonsBlueprintType, final Map<? extends HorizonsMaterial, Integer> materials, final Map<HorizonsModifier, HorizonsModifierValue> modifiers, final List<Engineer> engineers) {
        super(horizonsBlueprintName, horizonsBlueprintType, materials, modifiers, engineers);
        this.engineers = engineers;
    }

    @Override
    public List<Engineer> getEngineers() {
        if (this.engineers.stream().anyMatch(this::isPinned)) {
            final List<Engineer> withRemote = new ArrayList<>(this.engineers);
            withRemote.add(Engineer.REMOTE_WORKSHOP);
            return withRemote;
        }
        return this.engineers;
    }

    private boolean isPinned(final Engineer engineer) {
        final String preference = PreferencesService.getPreference("blueprint.pinned." + engineer.name(), "");
        if (!preference.isEmpty()) {
            final String[] split = preference.split(":");
            return split.length == 3 && this.getHorizonsBlueprintName().name().equals(split[0]) && this.getHorizonsBlueprintType().name().equals(split[1]) && this.getHorizonsBlueprintGrade().getGrade() <= ApplicationState.getInstance().getEngineerRank(engineer);
        }
        return false;
    }

    @Override
    public boolean hasSingleEngineerPerRegion() {
        return this.engineers != null && this.engineers.size() == 2;
    }
}
