package nl.jixxed.eliteodysseymaterials.domain.ships.utility;

import nl.jixxed.eliteodysseymaterials.constants.horizons.ExperimentalEffectBlueprints;
import nl.jixxed.eliteodysseymaterials.constants.horizons.utilitymounts.ShieldBoosterBlueprints;
import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.List;
import java.util.Map;

public class ShieldBooster extends UtilityModule {
    public static final ShieldBooster SHIELD_BOOSTER_0_E = new ShieldBooster("SHIELD_BOOSTER_0_E", HorizonsBlueprintName.SHIELD_BOOSTER, ModuleSize.SIZE_0, ModuleClass.E, true, Mounting.NA, 10000, "Hpt_ShieldBooster_Size0_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  0.5), Map.entry(HorizonsModifier.INTEGRITY,  25.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.2), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.SHIELD_BOOST,  0.04), Map.entry(HorizonsModifier.CAUSTIC_RESISTANCE, 0D), Map.entry(HorizonsModifier.KINETIC_RESISTANCE,  0.0), Map.entry(HorizonsModifier.THERMAL_RESISTANCE,  0.0), Map.entry(HorizonsModifier.EXPLOSIVE_RESISTANCE,  0.0)));
    public static final ShieldBooster SHIELD_BOOSTER_0_D = new ShieldBooster("SHIELD_BOOSTER_0_D", HorizonsBlueprintName.SHIELD_BOOSTER, ModuleSize.SIZE_0, ModuleClass.D, true, Mounting.NA, 23000, "Hpt_ShieldBooster_Size0_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.0), Map.entry(HorizonsModifier.INTEGRITY,  35.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.5), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.SHIELD_BOOST,  0.08), Map.entry(HorizonsModifier.CAUSTIC_RESISTANCE, 0D), Map.entry(HorizonsModifier.KINETIC_RESISTANCE,  0.0), Map.entry(HorizonsModifier.THERMAL_RESISTANCE,  0.0), Map.entry(HorizonsModifier.EXPLOSIVE_RESISTANCE,  0.0)));
    public static final ShieldBooster SHIELD_BOOSTER_0_C = new ShieldBooster("SHIELD_BOOSTER_0_C", HorizonsBlueprintName.SHIELD_BOOSTER, ModuleSize.SIZE_0, ModuleClass.C, true, Mounting.NA, 53000, "Hpt_ShieldBooster_Size0_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  40.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.7), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.SHIELD_BOOST,  0.12), Map.entry(HorizonsModifier.CAUSTIC_RESISTANCE, 0D), Map.entry(HorizonsModifier.KINETIC_RESISTANCE,  0.0), Map.entry(HorizonsModifier.THERMAL_RESISTANCE,  0.0), Map.entry(HorizonsModifier.EXPLOSIVE_RESISTANCE,  0.0)));
    public static final ShieldBooster SHIELD_BOOSTER_0_B = new ShieldBooster("SHIELD_BOOSTER_0_B", HorizonsBlueprintName.SHIELD_BOOSTER, ModuleSize.SIZE_0, ModuleClass.B, true, Mounting.NA, 122000, "Hpt_ShieldBooster_Size0_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  3.0), Map.entry(HorizonsModifier.INTEGRITY,  45.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.0), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.SHIELD_BOOST,  0.16), Map.entry(HorizonsModifier.CAUSTIC_RESISTANCE, 0D), Map.entry(HorizonsModifier.KINETIC_RESISTANCE,  0.0), Map.entry(HorizonsModifier.THERMAL_RESISTANCE,  0.0), Map.entry(HorizonsModifier.EXPLOSIVE_RESISTANCE,  0.0)));
    public static final ShieldBooster SHIELD_BOOSTER_0_A = new ShieldBooster("SHIELD_BOOSTER_0_A", HorizonsBlueprintName.SHIELD_BOOSTER, ModuleSize.SIZE_0, ModuleClass.A, true, Mounting.NA, 281000, "Hpt_ShieldBooster_Size0_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  3.5), Map.entry(HorizonsModifier.INTEGRITY,  48.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.2), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.SHIELD_BOOST,  0.20), Map.entry(HorizonsModifier.CAUSTIC_RESISTANCE, 0D), Map.entry(HorizonsModifier.KINETIC_RESISTANCE,  0.0), Map.entry(HorizonsModifier.THERMAL_RESISTANCE,  0.0), Map.entry(HorizonsModifier.EXPLOSIVE_RESISTANCE,  0.0)));

    public static final List<ShieldBooster> SHIELD_BOOSTERS = List.of(
            SHIELD_BOOSTER_0_E,
            SHIELD_BOOSTER_0_D,
            SHIELD_BOOSTER_0_C,
            SHIELD_BOOSTER_0_B,
            SHIELD_BOOSTER_0_A
    );
    public ShieldBooster(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public ShieldBooster(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public ShieldBooster(ShieldBooster shieldBooster) {
        super(shieldBooster);
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints() {
        return ShieldBoosterBlueprints.BLUEPRINTS.keySet().stream().toList();
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedExperimentalEffects() {
        return ExperimentalEffectBlueprints.SHIELD_BOOSTER.keySet().stream().toList();
    }

    @Override
    public ShieldBooster Clone() {
        return new ShieldBooster(this);
    }
    @Override
    public boolean isPassivePower(){
        return true;
    }
}
