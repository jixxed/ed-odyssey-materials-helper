package nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals;

import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleClass;
import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleSize;
import nl.jixxed.eliteodysseymaterials.domain.ships.OptionalModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.Origin;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ResearchLimpetController extends OptionalModule {
    public static final ResearchLimpetController RESEARCH_LIMPET_CONTROLLER_1_E = new ResearchLimpetController("RESEARCH_LIMPET_CONTROLLER_1_E", HorizonsBlueprintName.RESEARCH_LIMPET_CONTROLLER, ModuleSize.SIZE_1, ModuleClass.E, true, 1749600, "Int_DroneControl_UnkVesselResearch", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  20.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.4), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  1.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  2000.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME,  300.0), Map.entry(HorizonsModifier.DRONE_SPEED,  200.0)));

    public static final List<ResearchLimpetController> RESEARCH_LIMPET_CONTROLLERS = List.of(
            RESEARCH_LIMPET_CONTROLLER_1_E
    );
    public ResearchLimpetController(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, basePrice, internalName, attributes);
    }

    public ResearchLimpetController(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, basePrice, internalName, attributes);
    }

    public ResearchLimpetController(ResearchLimpetController researchLimpetController) {
        super(researchLimpetController);
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints() {
        return Collections.emptyList();
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedExperimentalEffects() {
        return Collections.emptyList();
    }

    @Override
    public ResearchLimpetController Clone() {
        return new ResearchLimpetController(this);
    }
}
