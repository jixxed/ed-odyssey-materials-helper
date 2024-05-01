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

public class ExperimentalWeaponStabiliser extends OptionalModule {
    public static final ExperimentalWeaponStabiliser EXPERIMENTAL_WEAPON_STABILISER_3_F = new ExperimentalWeaponStabiliser("EXPERIMENTAL_WEAPON_STABILISER_3_F", HorizonsBlueprintName.EXPERIMENTAL_WEAPON_STABILISER, ModuleSize.SIZE_3, ModuleClass.F, false, 2000000, "Int_ExpModuleStabiliser_Size3_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  8.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.0), Map.entry(HorizonsModifier.LISTED_POWER_DRAW,  1.5)));
    public static final ExperimentalWeaponStabiliser EXPERIMENTAL_WEAPON_STABILISER_5_F = new ExperimentalWeaponStabiliser("EXPERIMENTAL_WEAPON_STABILISER_5_F", HorizonsBlueprintName.EXPERIMENTAL_WEAPON_STABILISER, ModuleSize.SIZE_5, ModuleClass.F, false, 4000000, "Int_ExpModuleStabiliser_Size5_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  20.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.0), Map.entry(HorizonsModifier.LISTED_POWER_DRAW,  3.0)));

    public static final List<ExperimentalWeaponStabiliser> EXPERIMENTAL_WEAPON_STABILISERS = List.of(
            EXPERIMENTAL_WEAPON_STABILISER_3_F,
            EXPERIMENTAL_WEAPON_STABILISER_5_F
    );
    public ExperimentalWeaponStabiliser(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, basePrice, internalName, attributes);
    }

    public ExperimentalWeaponStabiliser(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, basePrice, internalName, attributes);
    }

    public ExperimentalWeaponStabiliser(ExperimentalWeaponStabiliser experimentalWeaponStabiliser) {
        super(experimentalWeaponStabiliser);
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
    public ExperimentalWeaponStabiliser Clone() {
        return new ExperimentalWeaponStabiliser(this);
    }

    @Override
    public boolean hasPowerToggle() {
        return false;
    }

    @Override
    public boolean isPassivePower() {
        return true;
    }

    @Override
    public int getGrouping() {
        return 1;
    }
}
