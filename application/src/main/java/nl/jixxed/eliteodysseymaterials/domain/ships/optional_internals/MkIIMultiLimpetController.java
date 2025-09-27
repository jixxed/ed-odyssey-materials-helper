package nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals;

import nl.jixxed.eliteodysseymaterials.constants.horizons.SynthesisBlueprints;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsSynthesisBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MkIIMultiLimpetController extends LimpetOptionalModule {
    public static final MkIIMultiLimpetController MK_II_MINING_MULTI_LIMPET_CONTROLLER_5_A = new MkIIMultiLimpetController("MK_II_MINING_MULTI_LIMPET_CONTROLLER_5_A", HorizonsBlueprintName.MK_II_MINING_MULTI_LIMPET_CONTROLLER, ModuleSize.SIZE_5, ModuleClass.A, true, 2332801, "Int_MultiDroneControl_MiningV2_Size5_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 64.0), Map.entry(HorizonsModifier.INTEGRITY, 105.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.4), Map.entry(HorizonsModifier.BOOT_TIME, 6.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS, 14.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE, 9100.0), Map.entry(HorizonsModifier.DRONE_LIFE_TIME, Double.POSITIVE_INFINITY), Map.entry(HorizonsModifier.DRONE_SPEED, 200.0), Map.entry(HorizonsModifier.DRONE_MULTI_TARGET_SPEED, 176.0)));

    public static final List<MkIIMultiLimpetController> MK_II_MULTI_LIMPET_CONTROLLERS = List.of(
            MK_II_MINING_MULTI_LIMPET_CONTROLLER_5_A
    );

    public MkIIMultiLimpetController(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, basePrice, internalName, attributes);
    }

    public MkIIMultiLimpetController(MkIIMultiLimpetController multiLimpetController) {
        super(multiLimpetController);
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
    public MkIIMultiLimpetController Clone() {
        return new MkIIMultiLimpetController(this);
    }

    @Override
    public int getModuleLimit() {
        return 1;
    }

    @Override
    public Collection<HorizonsSynthesisBlueprint> synthesisBlueprints() {
        return SynthesisBlueprints.LIMPETS.values();
    }
}
