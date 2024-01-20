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

public class ModuleReinforcementPackage extends MilitaryOptionalModule {
//    		 8150 : { mtype:'imrp', cost:     5000, name:'Module Reinforcement Package', class:1, rating:'E', mass: 2.00, integ: 77, dmgprot:30, fdid:128737270, fdname:'Int_ModuleReinforcement_Size1_Class1', eddbid:1577 },
//            8140 : { mtype:'imrp', cost:    15000, name:'Module Reinforcement Package', class:1, rating:'D', mass: 1.00, integ: 70, dmgprot:60, fdid:128737271, fdname:'Int_ModuleReinforcement_Size1_Class2', eddbid:1578 },
//            8250 : { mtype:'imrp', cost:    12000, name:'Module Reinforcement Package', class:2, rating:'E', mass: 4.00, integ:115, dmgprot:30, fdid:128737272, fdname:'Int_ModuleReinforcement_Size2_Class1', eddbid:1579 },
//            8240 : { mtype:'imrp', cost:    36000, name:'Module Reinforcement Package', class:2, rating:'D', mass: 2.00, integ:105, dmgprot:60, fdid:128737273, fdname:'Int_ModuleReinforcement_Size2_Class2', eddbid:1580 },
//            8350 : { mtype:'imrp', cost:    28000, name:'Module Reinforcement Package', class:3, rating:'E', mass: 8.00, integ:170, dmgprot:30, fdid:128737274, fdname:'Int_ModuleReinforcement_Size3_Class1', eddbid:1581 },
//            8340 : { mtype:'imrp', cost:    84000, name:'Module Reinforcement Package', class:3, rating:'D', mass: 4.00, integ:155, dmgprot:60, fdid:128737275, fdname:'Int_ModuleReinforcement_Size3_Class2', eddbid:1582 },
//            8450 : { mtype:'imrp', cost:    65000, name:'Module Reinforcement Package', class:4, rating:'E', mass:16.00, integ:260, dmgprot:30, fdid:128737276, fdname:'Int_ModuleReinforcement_Size4_Class1', eddbid:1583 },
//            8440 : { mtype:'imrp', cost:   195000, name:'Module Reinforcement Package', class:4, rating:'D', mass: 8.00, integ:235, dmgprot:60, fdid:128737277, fdname:'Int_ModuleReinforcement_Size4_Class2', eddbid:1584 },
//            8550 : { mtype:'imrp', cost:   150000, name:'Module Reinforcement Package', class:5, rating:'E', mass:32.00, integ:385, dmgprot:30, fdid:128737278, fdname:'Int_ModuleReinforcement_Size5_Class1', eddbid:1585 },
//            8540 : { mtype:'imrp', cost:   450000, name:'Module Reinforcement Package', class:5, rating:'D', mass:16.00, integ:350, dmgprot:60, fdid:128737279, fdname:'Int_ModuleReinforcement_Size5_Class2', eddbid:1586 },
    public static final ModuleReinforcementPackage MODULE_REINFORCEMENT_PACKAGE_1_E = new ModuleReinforcementPackage("MODULE_REINFORCEMENT_PACKAGE_1_E", HorizonsBlueprintName.MODULE_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_1, ModuleClass.E, 5000, "Int_ModuleReinforcement_Size1_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  77.0), Map.entry(HorizonsModifier.MODULE_DEFENCE_ABSORPTION,  0.30)));
    public static final ModuleReinforcementPackage MODULE_REINFORCEMENT_PACKAGE_1_D = new ModuleReinforcementPackage("MODULE_REINFORCEMENT_PACKAGE_1_D", HorizonsBlueprintName.MODULE_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_1, ModuleClass.D, 15000, "Int_ModuleReinforcement_Size1_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.0), Map.entry(HorizonsModifier.INTEGRITY,  70.0), Map.entry(HorizonsModifier.MODULE_DEFENCE_ABSORPTION,  0.60)));
    public static final ModuleReinforcementPackage MODULE_REINFORCEMENT_PACKAGE_2_E = new ModuleReinforcementPackage("MODULE_REINFORCEMENT_PACKAGE_2_E", HorizonsBlueprintName.MODULE_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_2, ModuleClass.E, 12000, "Int_ModuleReinforcement_Size2_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  4.0), Map.entry(HorizonsModifier.INTEGRITY,  115.0), Map.entry(HorizonsModifier.MODULE_DEFENCE_ABSORPTION,  0.30)));
    public static final ModuleReinforcementPackage MODULE_REINFORCEMENT_PACKAGE_2_D = new ModuleReinforcementPackage("MODULE_REINFORCEMENT_PACKAGE_2_D", HorizonsBlueprintName.MODULE_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_2, ModuleClass.D, 36000, "Int_ModuleReinforcement_Size2_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  105.0), Map.entry(HorizonsModifier.MODULE_DEFENCE_ABSORPTION,  0.60)));
    public static final ModuleReinforcementPackage MODULE_REINFORCEMENT_PACKAGE_3_E = new ModuleReinforcementPackage("MODULE_REINFORCEMENT_PACKAGE_3_E", HorizonsBlueprintName.MODULE_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_3, ModuleClass.E, 28000, "Int_ModuleReinforcement_Size3_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  8.0), Map.entry(HorizonsModifier.INTEGRITY,  170.0), Map.entry(HorizonsModifier.MODULE_DEFENCE_ABSORPTION,  0.30)));
    public static final ModuleReinforcementPackage MODULE_REINFORCEMENT_PACKAGE_3_D = new ModuleReinforcementPackage("MODULE_REINFORCEMENT_PACKAGE_3_D", HorizonsBlueprintName.MODULE_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_3, ModuleClass.D, 84000, "Int_ModuleReinforcement_Size3_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  4.0), Map.entry(HorizonsModifier.INTEGRITY,  155.0), Map.entry(HorizonsModifier.MODULE_DEFENCE_ABSORPTION,  0.60)));
    public static final ModuleReinforcementPackage MODULE_REINFORCEMENT_PACKAGE_4_E = new ModuleReinforcementPackage("MODULE_REINFORCEMENT_PACKAGE_4_E", HorizonsBlueprintName.MODULE_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_4, ModuleClass.E, 65000, "Int_ModuleReinforcement_Size4_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  16.0), Map.entry(HorizonsModifier.INTEGRITY,  260.0), Map.entry(HorizonsModifier.MODULE_DEFENCE_ABSORPTION,  0.30)));
    public static final ModuleReinforcementPackage MODULE_REINFORCEMENT_PACKAGE_4_D = new ModuleReinforcementPackage("MODULE_REINFORCEMENT_PACKAGE_4_D", HorizonsBlueprintName.MODULE_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_4, ModuleClass.D, 195000, "Int_ModuleReinforcement_Size4_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  8.0), Map.entry(HorizonsModifier.INTEGRITY,  235.0), Map.entry(HorizonsModifier.MODULE_DEFENCE_ABSORPTION,  0.60)));
    public static final ModuleReinforcementPackage MODULE_REINFORCEMENT_PACKAGE_5_E = new ModuleReinforcementPackage("MODULE_REINFORCEMENT_PACKAGE_5_E", HorizonsBlueprintName.MODULE_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_5, ModuleClass.E, 150000, "Int_ModuleReinforcement_Size5_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  32.0), Map.entry(HorizonsModifier.INTEGRITY,  385.0), Map.entry(HorizonsModifier.MODULE_DEFENCE_ABSORPTION,  0.30)));
    public static final ModuleReinforcementPackage MODULE_REINFORCEMENT_PACKAGE_5_D = new ModuleReinforcementPackage("MODULE_REINFORCEMENT_PACKAGE_5_D", HorizonsBlueprintName.MODULE_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_5, ModuleClass.D, 450000, "Int_ModuleReinforcement_Size5_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  16.0), Map.entry(HorizonsModifier.INTEGRITY,  350.0), Map.entry(HorizonsModifier.MODULE_DEFENCE_ABSORPTION,  0.60)));

    public static final List<ModuleReinforcementPackage> MODULE_REINFORCEMENT_PACKAGES = List.of(
            MODULE_REINFORCEMENT_PACKAGE_1_E,
            MODULE_REINFORCEMENT_PACKAGE_1_D,
            MODULE_REINFORCEMENT_PACKAGE_2_E,
            MODULE_REINFORCEMENT_PACKAGE_2_D,
            MODULE_REINFORCEMENT_PACKAGE_3_E,
            MODULE_REINFORCEMENT_PACKAGE_3_D,
            MODULE_REINFORCEMENT_PACKAGE_4_E,
            MODULE_REINFORCEMENT_PACKAGE_4_D,
            MODULE_REINFORCEMENT_PACKAGE_5_E,
            MODULE_REINFORCEMENT_PACKAGE_5_D
    );

    public ModuleReinforcementPackage(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, basePrice, internalName, attributes);
    }

    public ModuleReinforcementPackage(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, basePrice, internalName, attributes);
    }

    public ModuleReinforcementPackage(ModuleReinforcementPackage moduleReinforcementPackage) {
        super(moduleReinforcementPackage);
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
    public ModuleReinforcementPackage Clone() {
        return new ModuleReinforcementPackage(this);
    }


    @Override
    public boolean isHiddenStat(HorizonsModifier modifier) {
        if(HorizonsModifier.POWER_DRAW.equals(modifier)){
            return true;
        }
        return super.isHiddenStat(modifier);
    }
}
