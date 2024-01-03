package nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.military;

import nl.jixxed.eliteodysseymaterials.domain.ships.MilitaryOptionalModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleClass;
import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleSize;
import nl.jixxed.eliteodysseymaterials.domain.ships.Origin;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GuardianModuleReinforcementPackage extends MilitaryOptionalModule {
//    		 8151 : { mtype:'imrp', cost:    10000, name:'Guardian Module Reinforcement Package', tag:'G', class:1, rating:'E', mass: 2.00, integ: 85, pwrdraw:0.27, powerlock:1, dmgprot:30, fdid:128833955, fdname:'Int_GuardianModuleReinforcement_Size1_Class1', eddbid:1710 }, // guardian tech broker
//            8141 : { mtype:'imrp', cost:    30000, name:'Guardian Module Reinforcement Package', tag:'G', class:1, rating:'D', mass: 1.00, integ: 77, pwrdraw:0.34, powerlock:1, dmgprot:60, fdid:128833956, fdname:'Int_GuardianModuleReinforcement_Size1_Class2', eddbid:1711 }, // guardian tech broker
//            8251 : { mtype:'imrp', cost:    24000, name:'Guardian Module Reinforcement Package', tag:'G', class:2, rating:'E', mass: 4.00, integ:127, pwrdraw:0.41, powerlock:1, dmgprot:30, fdid:128833957, fdname:'Int_GuardianModuleReinforcement_Size2_Class1', eddbid:1712 }, // guardian tech broker
//            8241 : { mtype:'imrp', cost:    72000, name:'Guardian Module Reinforcement Package', tag:'G', class:2, rating:'D', mass: 2.00, integ:116, pwrdraw:0.47, powerlock:1, dmgprot:60, fdid:128833958, fdname:'Int_GuardianModuleReinforcement_Size2_Class2', eddbid:1713 }, // guardian tech broker
//            8351 : { mtype:'imrp', cost:    57600, name:'Guardian Module Reinforcement Package', tag:'G', class:3, rating:'E', mass: 8.00, integ:187, pwrdraw:0.54, powerlock:1, dmgprot:30, fdid:128833959, fdname:'Int_GuardianModuleReinforcement_Size3_Class1', eddbid:1714 }, // guardian tech broker
//            8341 : { mtype:'imrp', cost:   172800, name:'Guardian Module Reinforcement Package', tag:'G', class:3, rating:'D', mass: 4.00, integ:171, pwrdraw:0.61, powerlock:1, dmgprot:60, fdid:128833960, fdname:'Int_GuardianModuleReinforcement_Size3_Class2', eddbid:1715 }, // guardian tech broker
//            8451 : { mtype:'imrp', cost:   138240, name:'Guardian Module Reinforcement Package', tag:'G', class:4, rating:'E', mass:16.00, integ:286, pwrdraw:0.68, powerlock:1, dmgprot:30, fdid:128833961, fdname:'Int_GuardianModuleReinforcement_Size4_Class1', eddbid:1716 }, // guardian tech broker
//            8441 : { mtype:'imrp', cost:   414720, name:'Guardian Module Reinforcement Package', tag:'G', class:4, rating:'D', mass: 8.00, integ:259, pwrdraw:0.74, powerlock:1, dmgprot:60, fdid:128833962, fdname:'Int_GuardianModuleReinforcement_Size4_Class2', eddbid:1717 }, // guardian tech broker
//            8551 : { mtype:'imrp', cost:   331780, name:'Guardian Module Reinforcement Package', tag:'G', class:5, rating:'E', mass:32.00, integ:424, pwrdraw:0.81, powerlock:1, dmgprot:30, fdid:128833963, fdname:'Int_GuardianModuleReinforcement_Size5_Class1', eddbid:1718 }, // guardian tech broker
//            8541 : { mtype:'imrp', cost:   995330, name:'Guardian Module Reinforcement Package', tag:'G', class:5, rating:'D', mass:16.00, integ:385, pwrdraw:0.88, powerlock:1, dmgprot:60, fdid:128833964, fdname:'Int_GuardianModuleReinforcement_Size5_Class2', eddbid:1719 }, // guardian tech broker
//
    public static final GuardianModuleReinforcementPackage GUARDIAN_MODULE_REINFORCEMENT_PACKAGE_1_E = new GuardianModuleReinforcementPackage("GUARDIAN_MODULE_REINFORCEMENT_PACKAGE_1_E", HorizonsBlueprintName.GUARDIAN_MODULE_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_1, ModuleClass.E, Origin.GUARDIAN, 10000, "Int_GuardianModuleReinforcement_Size1_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  85.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.27), Map.entry(HorizonsModifier.MODULE_DEFENCE_ABSORPTION,  0.30)));
    public static final GuardianModuleReinforcementPackage GUARDIAN_MODULE_REINFORCEMENT_PACKAGE_1_D = new GuardianModuleReinforcementPackage("GUARDIAN_MODULE_REINFORCEMENT_PACKAGE_1_D", HorizonsBlueprintName.GUARDIAN_MODULE_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_1, ModuleClass.D, Origin.GUARDIAN, 30000, "Int_GuardianModuleReinforcement_Size1_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.0), Map.entry(HorizonsModifier.INTEGRITY,  77.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.34), Map.entry(HorizonsModifier.MODULE_DEFENCE_ABSORPTION,  0.60)));
    public static final GuardianModuleReinforcementPackage GUARDIAN_MODULE_REINFORCEMENT_PACKAGE_2_E = new GuardianModuleReinforcementPackage("GUARDIAN_MODULE_REINFORCEMENT_PACKAGE_2_E", HorizonsBlueprintName.GUARDIAN_MODULE_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_2, ModuleClass.E, Origin.GUARDIAN, 24000, "Int_GuardianModuleReinforcement_Size2_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  4.0), Map.entry(HorizonsModifier.INTEGRITY,  127.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.41), Map.entry(HorizonsModifier.MODULE_DEFENCE_ABSORPTION,  0.30)));
    public static final GuardianModuleReinforcementPackage GUARDIAN_MODULE_REINFORCEMENT_PACKAGE_2_D = new GuardianModuleReinforcementPackage("GUARDIAN_MODULE_REINFORCEMENT_PACKAGE_2_D", HorizonsBlueprintName.GUARDIAN_MODULE_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_2, ModuleClass.D, Origin.GUARDIAN, 72000, "Int_GuardianModuleReinforcement_Size2_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  116.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.47), Map.entry(HorizonsModifier.MODULE_DEFENCE_ABSORPTION,  0.60)));
    public static final GuardianModuleReinforcementPackage GUARDIAN_MODULE_REINFORCEMENT_PACKAGE_3_E = new GuardianModuleReinforcementPackage("GUARDIAN_MODULE_REINFORCEMENT_PACKAGE_3_E", HorizonsBlueprintName.GUARDIAN_MODULE_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_3, ModuleClass.E, Origin.GUARDIAN, 57600, "Int_GuardianModuleReinforcement_Size3_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  8.0), Map.entry(HorizonsModifier.INTEGRITY,  187.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.54), Map.entry(HorizonsModifier.MODULE_DEFENCE_ABSORPTION,  0.30)));
    public static final GuardianModuleReinforcementPackage GUARDIAN_MODULE_REINFORCEMENT_PACKAGE_3_D = new GuardianModuleReinforcementPackage("GUARDIAN_MODULE_REINFORCEMENT_PACKAGE_3_D", HorizonsBlueprintName.GUARDIAN_MODULE_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_3, ModuleClass.D, Origin.GUARDIAN, 172800, "Int_GuardianModuleReinforcement_Size3_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  4.0), Map.entry(HorizonsModifier.INTEGRITY,  171.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.61), Map.entry(HorizonsModifier.MODULE_DEFENCE_ABSORPTION,  0.60)));
    public static final GuardianModuleReinforcementPackage GUARDIAN_MODULE_REINFORCEMENT_PACKAGE_4_E = new GuardianModuleReinforcementPackage("GUARDIAN_MODULE_REINFORCEMENT_PACKAGE_4_E", HorizonsBlueprintName.GUARDIAN_MODULE_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_4, ModuleClass.E, Origin.GUARDIAN, 138240, "Int_GuardianModuleReinforcement_Size4_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  16.0), Map.entry(HorizonsModifier.INTEGRITY,  286.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.68), Map.entry(HorizonsModifier.MODULE_DEFENCE_ABSORPTION,  0.30)));
    public static final GuardianModuleReinforcementPackage GUARDIAN_MODULE_REINFORCEMENT_PACKAGE_4_D = new GuardianModuleReinforcementPackage("GUARDIAN_MODULE_REINFORCEMENT_PACKAGE_4_D", HorizonsBlueprintName.GUARDIAN_MODULE_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_4, ModuleClass.D, Origin.GUARDIAN, 414720, "Int_GuardianModuleReinforcement_Size4_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  8.0), Map.entry(HorizonsModifier.INTEGRITY,  259.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.74), Map.entry(HorizonsModifier.MODULE_DEFENCE_ABSORPTION,  0.60)));
    public static final GuardianModuleReinforcementPackage GUARDIAN_MODULE_REINFORCEMENT_PACKAGE_5_E = new GuardianModuleReinforcementPackage("GUARDIAN_MODULE_REINFORCEMENT_PACKAGE_5_E", HorizonsBlueprintName.GUARDIAN_MODULE_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_5, ModuleClass.E, Origin.GUARDIAN, 331780, "Int_GuardianModuleReinforcement_Size5_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  32.0), Map.entry(HorizonsModifier.INTEGRITY,  424.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.81), Map.entry(HorizonsModifier.MODULE_DEFENCE_ABSORPTION,  0.30)));
    public static final GuardianModuleReinforcementPackage GUARDIAN_MODULE_REINFORCEMENT_PACKAGE_5_D = new GuardianModuleReinforcementPackage("GUARDIAN_MODULE_REINFORCEMENT_PACKAGE_5_D", HorizonsBlueprintName.GUARDIAN_MODULE_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_5, ModuleClass.D, Origin.GUARDIAN, 995330, "Int_GuardianModuleReinforcement_Size5_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  16.0), Map.entry(HorizonsModifier.INTEGRITY,  385.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.88), Map.entry(HorizonsModifier.MODULE_DEFENCE_ABSORPTION,  0.60)));

    public static final List<GuardianModuleReinforcementPackage> GUARDIAN_MODULE_REINFORCEMENT_PACKAGES = List.of(
            GUARDIAN_MODULE_REINFORCEMENT_PACKAGE_1_E,
            GUARDIAN_MODULE_REINFORCEMENT_PACKAGE_1_D,
            GUARDIAN_MODULE_REINFORCEMENT_PACKAGE_2_E,
            GUARDIAN_MODULE_REINFORCEMENT_PACKAGE_2_D,
            GUARDIAN_MODULE_REINFORCEMENT_PACKAGE_3_E,
            GUARDIAN_MODULE_REINFORCEMENT_PACKAGE_3_D,
            GUARDIAN_MODULE_REINFORCEMENT_PACKAGE_4_E,
            GUARDIAN_MODULE_REINFORCEMENT_PACKAGE_4_D,
            GUARDIAN_MODULE_REINFORCEMENT_PACKAGE_5_E,
            GUARDIAN_MODULE_REINFORCEMENT_PACKAGE_5_D
    );

    public GuardianModuleReinforcementPackage(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, basePrice, internalName, attributes);
    }

    public GuardianModuleReinforcementPackage(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, basePrice, internalName, attributes);
    }

    public GuardianModuleReinforcementPackage(GuardianModuleReinforcementPackage guardianModuleReinforcementPackage) {
        super(guardianModuleReinforcementPackage);
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
    public GuardianModuleReinforcementPackage Clone() {
        return new GuardianModuleReinforcementPackage(this);
    }

    @Override
    public boolean hasPowerToggle() {
        return false;
    }
}
