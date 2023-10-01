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

public class GuardianShieldReinforcementPackage extends MilitaryOptionalModule {
    //    32151 : { mtype:'isrp', cost:   10000, name:'Guardian Shield Reinforcement Package', tag:'G', class:1, rating:'E', mass:  2.00, integ: 36, pwrdraw:0.35, powerlock:1, shieldrnf: 44, noblueprints:{'*':1}, fdid:128833965, fdname:'Int_GuardianShieldReinforcement_Size1_Class1', eddbid:1720 }, // guardian tech broker
//            32141 : { mtype:'isrp', cost:   30000, name:'Guardian Shield Reinforcement Package', tag:'G', class:1, rating:'D', mass:  1.00, integ: 36, pwrdraw:0.46, powerlock:1, shieldrnf: 61, noblueprints:{'*':1}, fdid:128833966, fdname:'Int_GuardianShieldReinforcement_Size1_Class2', eddbid:1721 }, // guardian tech broker
//            32251 : { mtype:'isrp', cost:   24000, name:'Guardian Shield Reinforcement Package', tag:'G', class:2, rating:'E', mass:  4.00, integ: 36, pwrdraw:0.56, powerlock:1, shieldrnf: 83, noblueprints:{'*':1}, fdid:128833967, fdname:'Int_GuardianShieldReinforcement_Size2_Class1', eddbid:1722 }, // guardian tech broker
//            32241 : { mtype:'isrp', cost:   72000, name:'Guardian Shield Reinforcement Package', tag:'G', class:2, rating:'D', mass:  2.00, integ: 36, pwrdraw:0.67, powerlock:1, shieldrnf:105, noblueprints:{'*':1}, fdid:128833968, fdname:'Int_GuardianShieldReinforcement_Size2_Class2', eddbid:1723 }, // guardian tech broker
//            32351 : { mtype:'isrp', cost:   57600, name:'Guardian Shield Reinforcement Package', tag:'G', class:3, rating:'E', mass:  8.00, integ: 36, pwrdraw:0.74, powerlock:1, shieldrnf:127, noblueprints:{'*':1}, fdid:128833969, fdname:'Int_GuardianShieldReinforcement_Size3_Class1', eddbid:1724 }, // guardian tech broker
//            32341 : { mtype:'isrp', cost:  172800, name:'Guardian Shield Reinforcement Package', tag:'G', class:3, rating:'D', mass:  4.00, integ: 36, pwrdraw:0.84, powerlock:1, shieldrnf:143, noblueprints:{'*':1}, fdid:128833970, fdname:'Int_GuardianShieldReinforcement_Size3_Class2', eddbid:1725 }, // guardian tech broker
//            32451 : { mtype:'isrp', cost:  138240, name:'Guardian Shield Reinforcement Package', tag:'G', class:4, rating:'E', mass: 16.00, integ: 36, pwrdraw:0.95, powerlock:1, shieldrnf:165, noblueprints:{'*':1}, fdid:128833971, fdname:'Int_GuardianShieldReinforcement_Size4_Class1', eddbid:1726 }, // guardian tech broker
//            32441 : { mtype:'isrp', cost:  414720, name:'Guardian Shield Reinforcement Package', tag:'G', class:4, rating:'D', mass:  8.00, integ: 36, pwrdraw:1.05, powerlock:1, shieldrnf:182, noblueprints:{'*':1}, fdid:128833972, fdname:'Int_GuardianShieldReinforcement_Size4_Class2', eddbid:1727 }, // guardian tech broker
//            32551 : { mtype:'isrp', cost:  331780, name:'Guardian Shield Reinforcement Package', tag:'G', class:5, rating:'E', mass: 32.00, integ: 36, pwrdraw:1.16, powerlock:1, shieldrnf:198, noblueprints:{'*':1}, fdid:128833973, fdname:'Int_GuardianShieldReinforcement_Size5_Class1', eddbid:1728 }, // guardian tech broker
//            32541 : { mtype:'isrp', cost:  995330, name:'Guardian Shield Reinforcement Package', tag:'G', class:5, rating:'D', mass: 16.00, integ: 36, pwrdraw:1.26, powerlock:1, shieldrnf:215, noblueprints:{'*':1}, fdid:128833974, fdname:'Int_GuardianShieldReinforcement_Size5_Class2', eddbid:1729 }, // guardian tech broker
//
    public static final GuardianShieldReinforcementPackage GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE_1_E = new GuardianShieldReinforcementPackage("GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE_1_E", HorizonsBlueprintName.GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_1, ModuleClass.E, Origin.GUARDIAN, 10000, "Int_GuardianShieldReinforcement_Size1_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  36.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.35), Map.entry(HorizonsModifier.SHIELD_REINFORCEMENT,  44.0)));
    public static final GuardianShieldReinforcementPackage GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE_1_D = new GuardianShieldReinforcementPackage("GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE_1_D", HorizonsBlueprintName.GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_1, ModuleClass.D, Origin.GUARDIAN, 30000, "Int_GuardianShieldReinforcement_Size1_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.0), Map.entry(HorizonsModifier.INTEGRITY,  36.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.46), Map.entry(HorizonsModifier.SHIELD_REINFORCEMENT,  61.0)));
    public static final GuardianShieldReinforcementPackage GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE_2_E = new GuardianShieldReinforcementPackage("GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE_2_E", HorizonsBlueprintName.GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_2, ModuleClass.E, Origin.GUARDIAN, 24000, "Int_GuardianShieldReinforcement_Size2_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  4.0), Map.entry(HorizonsModifier.INTEGRITY,  36.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.56), Map.entry(HorizonsModifier.SHIELD_REINFORCEMENT,  83.0)));
    public static final GuardianShieldReinforcementPackage GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE_2_D = new GuardianShieldReinforcementPackage("GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE_2_D", HorizonsBlueprintName.GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_2, ModuleClass.D, Origin.GUARDIAN, 72000, "Int_GuardianShieldReinforcement_Size2_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  36.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.67), Map.entry(HorizonsModifier.SHIELD_REINFORCEMENT,  105.0)));
    public static final GuardianShieldReinforcementPackage GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE_3_E = new GuardianShieldReinforcementPackage("GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE_3_E", HorizonsBlueprintName.GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_3, ModuleClass.E, Origin.GUARDIAN, 57600, "Int_GuardianShieldReinforcement_Size3_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  8.0), Map.entry(HorizonsModifier.INTEGRITY,  36.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.74), Map.entry(HorizonsModifier.SHIELD_REINFORCEMENT,  127.0)));
    public static final GuardianShieldReinforcementPackage GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE_3_D = new GuardianShieldReinforcementPackage("GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE_3_D", HorizonsBlueprintName.GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_3, ModuleClass.D, Origin.GUARDIAN, 172800, "Int_GuardianShieldReinforcement_Size3_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  4.0), Map.entry(HorizonsModifier.INTEGRITY,  36.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.84), Map.entry(HorizonsModifier.SHIELD_REINFORCEMENT,  143.0)));
    public static final GuardianShieldReinforcementPackage GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE_4_E = new GuardianShieldReinforcementPackage("GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE_4_E", HorizonsBlueprintName.GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_4, ModuleClass.E, Origin.GUARDIAN, 138240, "Int_GuardianShieldReinforcement_Size4_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  16.0), Map.entry(HorizonsModifier.INTEGRITY,  36.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.95), Map.entry(HorizonsModifier.SHIELD_REINFORCEMENT,  165.0)));
    public static final GuardianShieldReinforcementPackage GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE_4_D = new GuardianShieldReinforcementPackage("GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE_4_D", HorizonsBlueprintName.GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_4, ModuleClass.D, Origin.GUARDIAN, 414720, "Int_GuardianShieldReinforcement_Size4_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  8.0), Map.entry(HorizonsModifier.INTEGRITY,  36.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.05), Map.entry(HorizonsModifier.SHIELD_REINFORCEMENT,  182.0)));
    public static final GuardianShieldReinforcementPackage GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE_5_E = new GuardianShieldReinforcementPackage("GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE_5_E", HorizonsBlueprintName.GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_5, ModuleClass.E, Origin.GUARDIAN, 331780, "Int_GuardianShieldReinforcement_Size5_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  32.0), Map.entry(HorizonsModifier.INTEGRITY,  36.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.16), Map.entry(HorizonsModifier.SHIELD_REINFORCEMENT,  198.0)));
    public static final GuardianShieldReinforcementPackage GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE_5_D = new GuardianShieldReinforcementPackage("GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE_5_D", HorizonsBlueprintName.GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_5, ModuleClass.D, Origin.GUARDIAN, 995330, "Int_GuardianShieldReinforcement_Size5_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  16.0), Map.entry(HorizonsModifier.INTEGRITY,  36.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.26), Map.entry(HorizonsModifier.SHIELD_REINFORCEMENT,  215.0)));

    public static final List<GuardianShieldReinforcementPackage> GUARDIAN_SHIELD_REINFORCEMENT_PACKAGES = List.of(
            GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE_1_E,
            GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE_1_D,
            GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE_2_E,
            GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE_2_D,
            GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE_3_E,
            GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE_3_D,
            GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE_4_E,
            GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE_4_D,
            GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE_5_E,
            GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE_5_D
    );

    public GuardianShieldReinforcementPackage(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, basePrice, internalName, attributes);
    }

    public GuardianShieldReinforcementPackage(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, basePrice, internalName, attributes);
    }

    public GuardianShieldReinforcementPackage(GuardianShieldReinforcementPackage guardianShieldReinforcementPackage) {
        super(guardianShieldReinforcementPackage);
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
    public GuardianShieldReinforcementPackage Clone() {
        return new GuardianShieldReinforcementPackage(this);
    }
}
