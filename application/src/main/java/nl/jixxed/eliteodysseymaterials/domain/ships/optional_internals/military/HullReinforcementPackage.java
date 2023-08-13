package nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.military;

import nl.jixxed.eliteodysseymaterials.domain.ships.MilitaryOptionalModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleClass;
import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleSize;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HullReinforcementPackage extends MilitaryOptionalModule {
//            4150 : { mtype:'ihrp', cost:     5000, name:'Hull Reinforcement Package', class:1, rating:'E', mass: 2.00,                            hullrnf: 80, thmres:0.5, expres:0.5, kinres: 0.5, fdid:128668537, fdname:'Int_HullReinforcement_Size1_Class1', eddbid:1373 },
//            4140 : { mtype:'ihrp', cost:    15000, name:'Hull Reinforcement Package', class:1, rating:'D', mass: 1.00,                            hullrnf:110, thmres:0.5, expres:0.5, kinres: 0.5, fdid:128668538, fdname:'Int_HullReinforcement_Size1_Class2', eddbid:1374 },
//            4250 : { mtype:'ihrp', cost:    12000, name:'Hull Reinforcement Package', class:2, rating:'E', mass: 4.00,                            hullrnf:150, thmres:1.0, expres:1.0, kinres: 1.0, fdid:128668539, fdname:'Int_HullReinforcement_Size2_Class1', eddbid:1375 },
//            4240 : { mtype:'ihrp', cost:    36000, name:'Hull Reinforcement Package', class:2, rating:'D', mass: 2.00,                            hullrnf:190, thmres:1.0, expres:1.0, kinres: 1.0, fdid:128668540, fdname:'Int_HullReinforcement_Size2_Class2', eddbid:1376 },
//            4350 : { mtype:'ihrp', cost:    28000, name:'Hull Reinforcement Package', class:3, rating:'E', mass: 8.00,                            hullrnf:230, thmres:1.5, expres:1.5, kinres: 1.5, fdid:128668541, fdname:'Int_HullReinforcement_Size3_Class1', eddbid:1377 },
//            4340 : { mtype:'ihrp', cost:    84000, name:'Hull Reinforcement Package', class:3, rating:'D', mass: 4.00,                            hullrnf:260, thmres:1.5, expres:1.5, kinres: 1.5, fdid:128668542, fdname:'Int_HullReinforcement_Size3_Class2', eddbid:1378 },
//            4450 : { mtype:'ihrp', cost:    65000, name:'Hull Reinforcement Package', class:4, rating:'E', mass:16.00,                            hullrnf:300, thmres:2.0, expres:2.0, kinres: 2.0, fdid:128668543, fdname:'Int_HullReinforcement_Size4_Class1', eddbid:1379 },
//            4440 : { mtype:'ihrp', cost:   195000, name:'Hull Reinforcement Package', class:4, rating:'D', mass: 8.00,                            hullrnf:330, thmres:2.0, expres:2.0, kinres: 2.0, fdid:128668544, fdname:'Int_HullReinforcement_Size4_Class2', eddbid:1380 },
//            4550 : { mtype:'ihrp', cost:   150000, name:'Hull Reinforcement Package', class:5, rating:'E', mass:32.00,                            hullrnf:360, thmres:2.5, expres:2.5, kinres: 2.5, fdid:128668545, fdname:'Int_HullReinforcement_Size5_Class1', eddbid:1381 },
//            4540 : { mtype:'ihrp', cost:   450000, name:'Hull Reinforcement Package', class:5, rating:'D', mass:16.00,                            hullrnf:390, thmres:2.5, expres:2.5, kinres: 2.5, fdid:128668546, fdname:'Int_HullReinforcement_Size5_Class2', eddbid:1382 },

    public static final HullReinforcementPackage HULL_REINFORCEMENT_PACKAGE_1_E = new HullReinforcementPackage(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_1, ModuleClass.E, 5000, "Int_HullReinforcement_Size1_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.00), Map.entry(HorizonsModifier.HULL_REINFORCEMENT, 80.0), Map.entry(HorizonsModifier.KINETIC_RESISTANCE, 0.5), Map.entry(HorizonsModifier.THERMAL_RESISTANCE, 0.5), Map.entry(HorizonsModifier.EXPLOSIVE_RESISTANCE, 0.5)));
    public static final HullReinforcementPackage HULL_REINFORCEMENT_PACKAGE_1_D = new HullReinforcementPackage(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_1, ModuleClass.D, 15000, "Int_HullReinforcement_Size1_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 1.00), Map.entry(HorizonsModifier.HULL_REINFORCEMENT, 110.0), Map.entry(HorizonsModifier.KINETIC_RESISTANCE, 0.5), Map.entry(HorizonsModifier.THERMAL_RESISTANCE, 0.5), Map.entry(HorizonsModifier.EXPLOSIVE_RESISTANCE, 0.5)));
    public static final HullReinforcementPackage HULL_REINFORCEMENT_PACKAGE_2_E = new HullReinforcementPackage(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_2, ModuleClass.E, 12000, "Int_HullReinforcement_Size2_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.00), Map.entry(HorizonsModifier.HULL_REINFORCEMENT, 150.0), Map.entry(HorizonsModifier.KINETIC_RESISTANCE, 1.0), Map.entry(HorizonsModifier.THERMAL_RESISTANCE, 1.0), Map.entry(HorizonsModifier.EXPLOSIVE_RESISTANCE, 1.0)));
    public static final HullReinforcementPackage HULL_REINFORCEMENT_PACKAGE_2_D = new HullReinforcementPackage(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_2, ModuleClass.D, 36000, "Int_HullReinforcement_Size2_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.00), Map.entry(HorizonsModifier.HULL_REINFORCEMENT, 190.0), Map.entry(HorizonsModifier.KINETIC_RESISTANCE, 1.0), Map.entry(HorizonsModifier.THERMAL_RESISTANCE, 1.0), Map.entry(HorizonsModifier.EXPLOSIVE_RESISTANCE, 1.0)));
    public static final HullReinforcementPackage HULL_REINFORCEMENT_PACKAGE_3_E = new HullReinforcementPackage(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_3, ModuleClass.E, 28000, "Int_HullReinforcement_Size3_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 8.00), Map.entry(HorizonsModifier.HULL_REINFORCEMENT, 230.0), Map.entry(HorizonsModifier.KINETIC_RESISTANCE, 1.5), Map.entry(HorizonsModifier.THERMAL_RESISTANCE, 1.5), Map.entry(HorizonsModifier.EXPLOSIVE_RESISTANCE, 1.5)));
    public static final HullReinforcementPackage HULL_REINFORCEMENT_PACKAGE_3_D = new HullReinforcementPackage(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_3, ModuleClass.D, 84000, "Int_HullReinforcement_Size3_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.00), Map.entry(HorizonsModifier.HULL_REINFORCEMENT, 260.0), Map.entry(HorizonsModifier.KINETIC_RESISTANCE, 1.5), Map.entry(HorizonsModifier.THERMAL_RESISTANCE, 1.5), Map.entry(HorizonsModifier.EXPLOSIVE_RESISTANCE, 1.5)));
    public static final HullReinforcementPackage HULL_REINFORCEMENT_PACKAGE_4_E = new HullReinforcementPackage(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_4, ModuleClass.E, 65000, "Int_HullReinforcement_Size4_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 16.00), Map.entry(HorizonsModifier.HULL_REINFORCEMENT, 300.0), Map.entry(HorizonsModifier.KINETIC_RESISTANCE, 2.0), Map.entry(HorizonsModifier.THERMAL_RESISTANCE, 2.0), Map.entry(HorizonsModifier.EXPLOSIVE_RESISTANCE, 2.0)));
    public static final HullReinforcementPackage HULL_REINFORCEMENT_PACKAGE_4_D = new HullReinforcementPackage(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_4, ModuleClass.D, 195000, "Int_HullReinforcement_Size4_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 8.00), Map.entry(HorizonsModifier.HULL_REINFORCEMENT, 330.0), Map.entry(HorizonsModifier.KINETIC_RESISTANCE, 2.0), Map.entry(HorizonsModifier.THERMAL_RESISTANCE, 2.0), Map.entry(HorizonsModifier.EXPLOSIVE_RESISTANCE, 2.0)));
    public static final HullReinforcementPackage HULL_REINFORCEMENT_PACKAGE_5_E = new HullReinforcementPackage(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_5, ModuleClass.E, 150000, "Int_HullReinforcement_Size5_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 32.00), Map.entry(HorizonsModifier.HULL_REINFORCEMENT, 360.0), Map.entry(HorizonsModifier.KINETIC_RESISTANCE, 2.5), Map.entry(HorizonsModifier.THERMAL_RESISTANCE, 2.5), Map.entry(HorizonsModifier.EXPLOSIVE_RESISTANCE, 2.5)));
    public static final HullReinforcementPackage HULL_REINFORCEMENT_PACKAGE_5_D = new HullReinforcementPackage(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_5, ModuleClass.D, 450000, "Int_HullReinforcement_Size5_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 16.00), Map.entry(HorizonsModifier.HULL_REINFORCEMENT, 390.0), Map.entry(HorizonsModifier.KINETIC_RESISTANCE, 2.5), Map.entry(HorizonsModifier.THERMAL_RESISTANCE, 2.5), Map.entry(HorizonsModifier.EXPLOSIVE_RESISTANCE, 2.5)));

    public static final List<HullReinforcementPackage> HULL_REINFORCEMENT_PACKAGES = List.of(
            HULL_REINFORCEMENT_PACKAGE_1_E,
            HULL_REINFORCEMENT_PACKAGE_1_D,
            HULL_REINFORCEMENT_PACKAGE_2_E,
            HULL_REINFORCEMENT_PACKAGE_2_D,
            HULL_REINFORCEMENT_PACKAGE_3_E,
            HULL_REINFORCEMENT_PACKAGE_3_D,
            HULL_REINFORCEMENT_PACKAGE_4_E,
            HULL_REINFORCEMENT_PACKAGE_4_D,
            HULL_REINFORCEMENT_PACKAGE_5_E,
            HULL_REINFORCEMENT_PACKAGE_5_D
    );

    private HullReinforcementPackage(final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final int basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        super(name, moduleSize, moduleClass, basePrice, internalName, attributes);
    }

    private HullReinforcementPackage(final HullReinforcementPackage hullReinforcementPackage) {
        super(hullReinforcementPackage);
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
    public HullReinforcementPackage Clone() {
        return new HullReinforcementPackage(this);
    }
}
