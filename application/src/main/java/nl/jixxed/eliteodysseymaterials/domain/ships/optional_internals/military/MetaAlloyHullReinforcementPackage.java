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

public class MetaAlloyHullReinforcementPackage extends MilitaryOptionalModule {

//		 9151 : { mtype:'imahrp', cost:  7500, name:'Meta Alloy Hull Reinforcement Package', class:1, rating:'E', mass:  2, hullrnf: 72, caures:3.0, fdid:128793117, fdname:'Int_MetaAlloyHullReinforcement_Size1_Class1', eddbid:1664 }, // human tech broker
//            9141 : { mtype:'imahrp', cost: 22500, name:'Meta Alloy Hull Reinforcement Package', class:1, rating:'D', mass:  1, hullrnf: 99, caures:3.0, fdid:128793118, fdname:'Int_MetaAlloyHullReinforcement_Size1_Class2', eddbid:1665 }, // human tech broker
//            9251 : { mtype:'imahrp', cost: 18000, name:'Meta Alloy Hull Reinforcement Package', class:2, rating:'E', mass:  4, hullrnf:135, caures:3.0, fdid:128793119, fdname:'Int_MetaAlloyHullReinforcement_Size2_Class1', eddbid:1666 }, // human tech broker
//            9241 : { mtype:'imahrp', cost: 54000, name:'Meta Alloy Hull Reinforcement Package', class:2, rating:'D', mass:  2, hullrnf:171, caures:3.0, fdid:128793120, fdname:'Int_MetaAlloyHullReinforcement_Size2_Class2', eddbid:1667 }, // human tech broker
//            9351 : { mtype:'imahrp', cost: 42000, name:'Meta Alloy Hull Reinforcement Package', class:3, rating:'E', mass:  8, hullrnf:207, caures:3.0, fdid:128793121, fdname:'Int_MetaAlloyHullReinforcement_Size3_Class1', eddbid:1668 }, // human tech broker
//            9341 : { mtype:'imahrp', cost:126000, name:'Meta Alloy Hull Reinforcement Package', class:3, rating:'D', mass:  4, hullrnf:234, caures:3.0, fdid:128793122, fdname:'Int_MetaAlloyHullReinforcement_Size3_Class2', eddbid:1669 }, // human tech broker
//            9451 : { mtype:'imahrp', cost: 97500, name:'Meta Alloy Hull Reinforcement Package', class:4, rating:'E', mass: 16, hullrnf:270, caures:3.0, fdid:128793123, fdname:'Int_MetaAlloyHullReinforcement_Size4_Class1', eddbid:1670 }, // human tech broker
//            9441 : { mtype:'imahrp', cost:292500, name:'Meta Alloy Hull Reinforcement Package', class:4, rating:'D', mass:  8, hullrnf:297, caures:3.0, fdid:128793124, fdname:'Int_MetaAlloyHullReinforcement_Size4_Class2', eddbid:1671 }, // human tech broker
//            9551 : { mtype:'imahrp', cost:225000, name:'Meta Alloy Hull Reinforcement Package', class:5, rating:'E', mass: 32, hullrnf:324, caures:3.0, fdid:128793125, fdname:'Int_MetaAlloyHullReinforcement_Size5_Class1', eddbid:1672 }, // human tech broker
//            9541 : { mtype:'imahrp', cost:675000, name:'Meta Alloy Hull Reinforcement Package', class:5, rating:'D', mass: 16, hullrnf:351, caures:3.0, fdid:128793126, fdname:'Int_MetaAlloyHullReinforcement_Size5_Class2', eddbid:1673 }, // human tech broker
    public static final MetaAlloyHullReinforcementPackage META_ALLOY_HULL_REINFORCEMENT_PACKAGE_1_E = new MetaAlloyHullReinforcementPackage("META_ALLOY_HULL_REINFORCEMENT_PACKAGE_1_E", HorizonsBlueprintName.META_ALLOY_HULL_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_1, ModuleClass.E, 7500, "Int_MetaAlloyHullReinforcement_Size1_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.HULL_REINFORCEMENT,  72.0), Map.entry(HorizonsModifier.CAUSTIC_RESISTANCE,  3.0)));
    public static final MetaAlloyHullReinforcementPackage META_ALLOY_HULL_REINFORCEMENT_PACKAGE_1_D = new MetaAlloyHullReinforcementPackage("META_ALLOY_HULL_REINFORCEMENT_PACKAGE_1_D", HorizonsBlueprintName.META_ALLOY_HULL_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_1, ModuleClass.D, 22500, "Int_MetaAlloyHullReinforcement_Size1_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.0), Map.entry(HorizonsModifier.HULL_REINFORCEMENT,  99.0), Map.entry(HorizonsModifier.CAUSTIC_RESISTANCE,  3.0)));
    public static final MetaAlloyHullReinforcementPackage META_ALLOY_HULL_REINFORCEMENT_PACKAGE_2_E = new MetaAlloyHullReinforcementPackage("META_ALLOY_HULL_REINFORCEMENT_PACKAGE_2_E", HorizonsBlueprintName.META_ALLOY_HULL_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_2, ModuleClass.E, 18000, "Int_MetaAlloyHullReinforcement_Size2_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  4.0), Map.entry(HorizonsModifier.HULL_REINFORCEMENT,  135.0), Map.entry(HorizonsModifier.CAUSTIC_RESISTANCE,  3.0)));
    public static final MetaAlloyHullReinforcementPackage META_ALLOY_HULL_REINFORCEMENT_PACKAGE_2_D = new MetaAlloyHullReinforcementPackage("META_ALLOY_HULL_REINFORCEMENT_PACKAGE_2_D", HorizonsBlueprintName.META_ALLOY_HULL_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_2, ModuleClass.D, 54000, "Int_MetaAlloyHullReinforcement_Size2_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.HULL_REINFORCEMENT,  171.0), Map.entry(HorizonsModifier.CAUSTIC_RESISTANCE,  3.0)));
    public static final MetaAlloyHullReinforcementPackage META_ALLOY_HULL_REINFORCEMENT_PACKAGE_3_E = new MetaAlloyHullReinforcementPackage("META_ALLOY_HULL_REINFORCEMENT_PACKAGE_3_E", HorizonsBlueprintName.META_ALLOY_HULL_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_3, ModuleClass.E, 42000, "Int_MetaAlloyHullReinforcement_Size3_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  8.0), Map.entry(HorizonsModifier.HULL_REINFORCEMENT,  207.0), Map.entry(HorizonsModifier.CAUSTIC_RESISTANCE,  3.0)));
    public static final MetaAlloyHullReinforcementPackage META_ALLOY_HULL_REINFORCEMENT_PACKAGE_3_D = new MetaAlloyHullReinforcementPackage("META_ALLOY_HULL_REINFORCEMENT_PACKAGE_3_D", HorizonsBlueprintName.META_ALLOY_HULL_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_3, ModuleClass.D, 126000, "Int_MetaAlloyHullReinforcement_Size3_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  4.0), Map.entry(HorizonsModifier.HULL_REINFORCEMENT,  234.0), Map.entry(HorizonsModifier.CAUSTIC_RESISTANCE,  3.0)));
    public static final MetaAlloyHullReinforcementPackage META_ALLOY_HULL_REINFORCEMENT_PACKAGE_4_E = new MetaAlloyHullReinforcementPackage("META_ALLOY_HULL_REINFORCEMENT_PACKAGE_4_E", HorizonsBlueprintName.META_ALLOY_HULL_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_4, ModuleClass.E, 97500, "Int_MetaAlloyHullReinforcement_Size4_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  16.0), Map.entry(HorizonsModifier.HULL_REINFORCEMENT,  270.0), Map.entry(HorizonsModifier.CAUSTIC_RESISTANCE,  3.0)));
    public static final MetaAlloyHullReinforcementPackage META_ALLOY_HULL_REINFORCEMENT_PACKAGE_4_D = new MetaAlloyHullReinforcementPackage("META_ALLOY_HULL_REINFORCEMENT_PACKAGE_4_D", HorizonsBlueprintName.META_ALLOY_HULL_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_4, ModuleClass.D, 292500, "Int_MetaAlloyHullReinforcement_Size4_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  8.0), Map.entry(HorizonsModifier.HULL_REINFORCEMENT,  297.0), Map.entry(HorizonsModifier.CAUSTIC_RESISTANCE,  3.0)));
    public static final MetaAlloyHullReinforcementPackage META_ALLOY_HULL_REINFORCEMENT_PACKAGE_5_E = new MetaAlloyHullReinforcementPackage("META_ALLOY_HULL_REINFORCEMENT_PACKAGE_5_E", HorizonsBlueprintName.META_ALLOY_HULL_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_5, ModuleClass.E, 225000, "Int_MetaAlloyHullReinforcement_Size5_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  32.0), Map.entry(HorizonsModifier.HULL_REINFORCEMENT,  324.0), Map.entry(HorizonsModifier.CAUSTIC_RESISTANCE,  3.0)));
    public static final MetaAlloyHullReinforcementPackage META_ALLOY_HULL_REINFORCEMENT_PACKAGE_5_D = new MetaAlloyHullReinforcementPackage("META_ALLOY_HULL_REINFORCEMENT_PACKAGE_5_D", HorizonsBlueprintName.META_ALLOY_HULL_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_5, ModuleClass.D, 675000, "Int_MetaAlloyHullReinforcement_Size5_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  16.0), Map.entry(HorizonsModifier.HULL_REINFORCEMENT,  351.0), Map.entry(HorizonsModifier.CAUSTIC_RESISTANCE,  3.0)));

    public static final List<MetaAlloyHullReinforcementPackage> META_ALLOY_HULL_REINFORCEMENT_PACKAGES = List.of(
            META_ALLOY_HULL_REINFORCEMENT_PACKAGE_1_E,
            META_ALLOY_HULL_REINFORCEMENT_PACKAGE_1_D,
            META_ALLOY_HULL_REINFORCEMENT_PACKAGE_2_E,
            META_ALLOY_HULL_REINFORCEMENT_PACKAGE_2_D,
            META_ALLOY_HULL_REINFORCEMENT_PACKAGE_3_E,
            META_ALLOY_HULL_REINFORCEMENT_PACKAGE_3_D,
            META_ALLOY_HULL_REINFORCEMENT_PACKAGE_4_E,
            META_ALLOY_HULL_REINFORCEMENT_PACKAGE_4_D,
            META_ALLOY_HULL_REINFORCEMENT_PACKAGE_5_E,
            META_ALLOY_HULL_REINFORCEMENT_PACKAGE_5_D
    );

    public MetaAlloyHullReinforcementPackage(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, basePrice, internalName, attributes);
    }

    public MetaAlloyHullReinforcementPackage(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, basePrice, internalName, attributes);
    }

    public MetaAlloyHullReinforcementPackage(MilitaryOptionalModule militaryOptionalModule) {
        super(militaryOptionalModule);
    }

    public MetaAlloyHullReinforcementPackage(MetaAlloyHullReinforcementPackage metaAlloyHullReinforcementPackage) {
        super(metaAlloyHullReinforcementPackage);
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
    public MetaAlloyHullReinforcementPackage Clone() {
        return new MetaAlloyHullReinforcementPackage(this);
    }
}
