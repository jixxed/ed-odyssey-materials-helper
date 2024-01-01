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
//    54050 : { mtype:'usb',  cost:  10000, name:'Shield Booster',            class:0, rating:'E', mass:0.50, integ:25, pwrdraw:0.20, passive:1, boottime:0, shieldbst: 4.0, kinres:0.0, thmres:0.0, expres:0.0, fdid:128668532, fdname:'Hpt_ShieldBooster_Size0_Class1', eddbid:1368 },
//        54040 : { mtype:'usb',  cost:  23000, name:'Shield Booster',            class:0, rating:'D', mass:1.00, integ:35, pwrdraw:0.50, passive:1, boottime:0, shieldbst: 8.0, kinres:0.0, thmres:0.0, expres:0.0, fdid:128668533, fdname:'Hpt_ShieldBooster_Size0_Class2', eddbid:1369 },
//        54030 : { mtype:'usb',  cost:  53000, name:'Shield Booster',            class:0, rating:'C', mass:2.00, integ:40, pwrdraw:0.70, passive:1, boottime:0, shieldbst:12.0, kinres:0.0, thmres:0.0, expres:0.0, fdid:128668534, fdname:'Hpt_ShieldBooster_Size0_Class3', eddbid:1370 },
//        54020 : { mtype:'usb',  cost: 122000, name:'Shield Booster',            class:0, rating:'B', mass:3.00, integ:45, pwrdraw:1.00, passive:1, boottime:0, shieldbst:16.0, kinres:0.0, thmres:0.0, expres:0.0, fdid:128668535, fdname:'Hpt_ShieldBooster_Size0_Class4', eddbid:1371 },
//        54010 : { mtype:'usb',  cost: 281000, name:'Shield Booster',            class:0, rating:'A', mass:3.50, integ:48, pwrdraw:1.20, passive:1, boottime:0, shieldbst:20.0, kinres:0.0, thmres:0.0, expres:0.0, fdid:128668536, fdname:'Hpt_ShieldBooster_Size0_Class5', eddbid:1372 },
    public static final ShieldBooster SHIELD_BOOSTER_0_E = new ShieldBooster("SHIELD_BOOSTER_0_E", HorizonsBlueprintName.SHIELD_BOOSTER, ModuleSize.SIZE_0, ModuleClass.E, true, Mounting.NA, 10000, "Hpt_ShieldBooster_Size0_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  0.5), Map.entry(HorizonsModifier.INTEGRITY,  25.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.2), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.SHIELD_BOOST,  0.04), Map.entry(HorizonsModifier.KINETIC_RESISTANCE,  0.0), Map.entry(HorizonsModifier.THERMAL_RESISTANCE,  0.0), Map.entry(HorizonsModifier.EXPLOSIVE_RESISTANCE,  0.0)));
    public static final ShieldBooster SHIELD_BOOSTER_0_D = new ShieldBooster("SHIELD_BOOSTER_0_D", HorizonsBlueprintName.SHIELD_BOOSTER, ModuleSize.SIZE_0, ModuleClass.D, true, Mounting.NA, 23000, "Hpt_ShieldBooster_Size0_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.0), Map.entry(HorizonsModifier.INTEGRITY,  35.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.5), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.SHIELD_BOOST,  0.08), Map.entry(HorizonsModifier.KINETIC_RESISTANCE,  0.0), Map.entry(HorizonsModifier.THERMAL_RESISTANCE,  0.0), Map.entry(HorizonsModifier.EXPLOSIVE_RESISTANCE,  0.0)));
    public static final ShieldBooster SHIELD_BOOSTER_0_C = new ShieldBooster("SHIELD_BOOSTER_0_C", HorizonsBlueprintName.SHIELD_BOOSTER, ModuleSize.SIZE_0, ModuleClass.C, true, Mounting.NA, 53000, "Hpt_ShieldBooster_Size0_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  40.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.7), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.SHIELD_BOOST,  0.12), Map.entry(HorizonsModifier.KINETIC_RESISTANCE,  0.0), Map.entry(HorizonsModifier.THERMAL_RESISTANCE,  0.0), Map.entry(HorizonsModifier.EXPLOSIVE_RESISTANCE,  0.0)));
    public static final ShieldBooster SHIELD_BOOSTER_0_B = new ShieldBooster("SHIELD_BOOSTER_0_B", HorizonsBlueprintName.SHIELD_BOOSTER, ModuleSize.SIZE_0, ModuleClass.B, true, Mounting.NA, 122000, "Hpt_ShieldBooster_Size0_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  3.0), Map.entry(HorizonsModifier.INTEGRITY,  45.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.0), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.SHIELD_BOOST,  0.16), Map.entry(HorizonsModifier.KINETIC_RESISTANCE,  0.0), Map.entry(HorizonsModifier.THERMAL_RESISTANCE,  0.0), Map.entry(HorizonsModifier.EXPLOSIVE_RESISTANCE,  0.0)));
    public static final ShieldBooster SHIELD_BOOSTER_0_A = new ShieldBooster("SHIELD_BOOSTER_0_A", HorizonsBlueprintName.SHIELD_BOOSTER, ModuleSize.SIZE_0, ModuleClass.A, true, Mounting.NA, 281000, "Hpt_ShieldBooster_Size0_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  3.5), Map.entry(HorizonsModifier.INTEGRITY,  48.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.2), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.SHIELD_BOOST,  0.20), Map.entry(HorizonsModifier.KINETIC_RESISTANCE,  0.0), Map.entry(HorizonsModifier.THERMAL_RESISTANCE,  0.0), Map.entry(HorizonsModifier.EXPLOSIVE_RESISTANCE,  0.0)));

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
}
