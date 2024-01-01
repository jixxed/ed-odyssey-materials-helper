package nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.military;

import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GuardianHullReinforcementPackage extends MilitaryOptionalModule {
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
//            4151 : { mtype:'ihrp', cost:    10000, name:'Guardian Hull Reinforcemen', class:1, rating:'E', mass: 2.00, pwrdraw:0.45, powerlock:1, hullrnf:100, thmres:2.0, caures:5.0, noblueprints:{'*':1}, fdid:128833945, fdname:'Int_GuardianHullReinforcement_Size1_Class1', eddbid:1700 }, // verify *res // guardian tech broker
//            4141 : { mtype:'ihrp', cost:    30000, name:'Guardian Hull Reinforcemen', class:1, rating:'D', mass: 1.00, pwrdraw:0.56, powerlock:1, hullrnf:138, thmres:2.0, caures:5.0, noblueprints:{'*':1}, fdid:128833946, fdname:'Int_GuardianHullReinforcement_Size1_Class2', eddbid:1701 }, // verify *res // guardian tech broker
//            4251 : { mtype:'ihrp', cost:    24000, name:'Guardian Hull Reinforcemen', class:2, rating:'E', mass: 4.00, pwrdraw:0.68, powerlock:1, hullrnf:188, thmres:2.0, caures:5.0, noblueprints:{'*':1}, fdid:128833947, fdname:'Int_GuardianHullReinforcement_Size2_Class1', eddbid:1702 }, // verify *res // guardian tech broker
//            4241 : { mtype:'ihrp', cost:    72000, name:'Guardian Hull Reinforcemen', class:2, rating:'D', mass: 2.00, pwrdraw:0.79, powerlock:1, hullrnf:238, thmres:2.0, caures:5.0, noblueprints:{'*':1}, fdid:128833948, fdname:'Int_GuardianHullReinforcement_Size2_Class2', eddbid:1703 }, // verify *res // guardian tech broker
//            4351 : { mtype:'ihrp', cost:    57600, name:'Guardian Hull Reinforcemen', class:3, rating:'E', mass: 8.00, pwrdraw:0.90, powerlock:1, hullrnf:288, thmres:2.0, caures:5.0, noblueprints:{'*':1}, fdid:128833949, fdname:'Int_GuardianHullReinforcement_Size3_Class1', eddbid:1704 }, // verify *res // guardian tech broker
//            4341 : { mtype:'ihrp', cost:   172800, name:'Guardian Hull Reinforcemen', class:3, rating:'D', mass: 4.00, pwrdraw:1.01, powerlock:1, hullrnf:325, thmres:2.0, caures:5.0, noblueprints:{'*':1}, fdid:128833950, fdname:'Int_GuardianHullReinforcement_Size3_Class2', eddbid:1705 }, // verify *res // guardian tech broker
//            4451 : { mtype:'ihrp', cost:   138240, name:'Guardian Hull Reinforcemen', class:4, rating:'E', mass:16.00, pwrdraw:1.13, powerlock:1, hullrnf:375, thmres:2.0, caures:5.0, noblueprints:{'*':1}, fdid:128833951, fdname:'Int_GuardianHullReinforcement_Size4_Class1', eddbid:1706 }, // verify *res // guardian tech broker
//            4441 : { mtype:'ihrp', cost:   414720, name:'Guardian Hull Reinforcemen', class:4, rating:'D', mass: 8.00, pwrdraw:1.24, powerlock:1, hullrnf:413, thmres:2.0, caures:5.0, noblueprints:{'*':1}, fdid:128833952, fdname:'Int_GuardianHullReinforcement_Size4_Class2', eddbid:1707 }, // verify *res // guardian tech broker
//            4551 : { mtype:'ihrp', cost:   331780, name:'Guardian Hull Reinforcemen', class:5, rating:'E', mass:32.00, pwrdraw:1.35, powerlock:1, hullrnf:450, thmres:2.0, caures:5.0, noblueprints:{'*':1}, fdid:128833953, fdname:'Int_GuardianHullReinforcement_Size5_Class1', eddbid:1708 }, // verify *res // guardian tech broker
//            4541 : { mtype:'ihrp', cost:   995330, name:'Guardian Hull Reinforcemen', class:5, rating:'D', mass:16.00, pwrdraw:1.46, powerlock:1, hullrnf:488, thmres:2.0, caures:5.0, noblueprints:{'*':1}, fdid:128833954, fdname:'Int_GuardianHullReinforcement_Size5_Class2', eddbid:1709 }, // verify *res // guardian tech broker
    public static final GuardianHullReinforcementPackage GUARDIAN_HULL_REINFORCEMENT_PACKAGE_1_E = new GuardianHullReinforcementPackage("GUARDIAN_HULL_REINFORCEMENT_PACKAGE_1_E", HorizonsBlueprintName.GUARDIAN_HULL_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_1, ModuleClass.E, Origin.GUARDIAN, 10000, "Int_GuardianHullReinforcement_Size1_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.00), Map.entry(HorizonsModifier.HULL_REINFORCEMENT,100.0), Map.entry(HorizonsModifier.POWER_DRAW,0.45), Map.entry(HorizonsModifier.THERMAL_RESISTANCE, 0.02), Map.entry(HorizonsModifier.CAUSTIC_RESISTANCE, 0.05)));
    public static final GuardianHullReinforcementPackage GUARDIAN_HULL_REINFORCEMENT_PACKAGE_1_D = new GuardianHullReinforcementPackage("GUARDIAN_HULL_REINFORCEMENT_PACKAGE_1_D", HorizonsBlueprintName.GUARDIAN_HULL_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_1, ModuleClass.D, Origin.GUARDIAN, 30000, "Int_GuardianHullReinforcement_Size1_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.00), Map.entry(HorizonsModifier.HULL_REINFORCEMENT,138.0), Map.entry(HorizonsModifier.POWER_DRAW,0.56), Map.entry(HorizonsModifier.THERMAL_RESISTANCE, 0.02), Map.entry(HorizonsModifier.CAUSTIC_RESISTANCE, 0.05)));
    public static final GuardianHullReinforcementPackage GUARDIAN_HULL_REINFORCEMENT_PACKAGE_2_E = new GuardianHullReinforcementPackage("GUARDIAN_HULL_REINFORCEMENT_PACKAGE_2_E", HorizonsBlueprintName.GUARDIAN_HULL_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_2, ModuleClass.E, Origin.GUARDIAN, 24000, "Int_GuardianHullReinforcement_Size2_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  4.00), Map.entry(HorizonsModifier.HULL_REINFORCEMENT,188.0), Map.entry(HorizonsModifier.POWER_DRAW,0.68), Map.entry(HorizonsModifier.THERMAL_RESISTANCE, 0.02), Map.entry(HorizonsModifier.CAUSTIC_RESISTANCE, 0.05)));
    public static final GuardianHullReinforcementPackage GUARDIAN_HULL_REINFORCEMENT_PACKAGE_2_D = new GuardianHullReinforcementPackage("GUARDIAN_HULL_REINFORCEMENT_PACKAGE_2_D", HorizonsBlueprintName.GUARDIAN_HULL_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_2, ModuleClass.D, Origin.GUARDIAN, 72000, "Int_GuardianHullReinforcement_Size2_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.00), Map.entry(HorizonsModifier.HULL_REINFORCEMENT,238.0), Map.entry(HorizonsModifier.POWER_DRAW,0.79), Map.entry(HorizonsModifier.THERMAL_RESISTANCE, 0.02), Map.entry(HorizonsModifier.CAUSTIC_RESISTANCE, 0.05)));
    public static final GuardianHullReinforcementPackage GUARDIAN_HULL_REINFORCEMENT_PACKAGE_3_E = new GuardianHullReinforcementPackage("GUARDIAN_HULL_REINFORCEMENT_PACKAGE_3_E", HorizonsBlueprintName.GUARDIAN_HULL_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_3, ModuleClass.E, Origin.GUARDIAN, 57600, "Int_GuardianHullReinforcement_Size3_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  8.00), Map.entry(HorizonsModifier.HULL_REINFORCEMENT,288.0), Map.entry(HorizonsModifier.POWER_DRAW,0.90), Map.entry(HorizonsModifier.THERMAL_RESISTANCE, 0.02), Map.entry(HorizonsModifier.CAUSTIC_RESISTANCE, 0.05)));
    public static final GuardianHullReinforcementPackage GUARDIAN_HULL_REINFORCEMENT_PACKAGE_3_D = new GuardianHullReinforcementPackage("GUARDIAN_HULL_REINFORCEMENT_PACKAGE_3_D", HorizonsBlueprintName.GUARDIAN_HULL_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_3, ModuleClass.D, Origin.GUARDIAN,172800, "Int_GuardianHullReinforcement_Size3_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  4.00), Map.entry(HorizonsModifier.HULL_REINFORCEMENT,325.0), Map.entry(HorizonsModifier.POWER_DRAW,1.01), Map.entry(HorizonsModifier.THERMAL_RESISTANCE, 0.02), Map.entry(HorizonsModifier.CAUSTIC_RESISTANCE, 0.05)));
    public static final GuardianHullReinforcementPackage GUARDIAN_HULL_REINFORCEMENT_PACKAGE_4_E = new GuardianHullReinforcementPackage("GUARDIAN_HULL_REINFORCEMENT_PACKAGE_4_E", HorizonsBlueprintName.GUARDIAN_HULL_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_4, ModuleClass.E, Origin.GUARDIAN,138240, "Int_GuardianHullReinforcement_Size4_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 16.00), Map.entry(HorizonsModifier.HULL_REINFORCEMENT,375.0), Map.entry(HorizonsModifier.POWER_DRAW,1.13), Map.entry(HorizonsModifier.THERMAL_RESISTANCE, 0.02), Map.entry(HorizonsModifier.CAUSTIC_RESISTANCE, 0.05)));
    public static final GuardianHullReinforcementPackage GUARDIAN_HULL_REINFORCEMENT_PACKAGE_4_D = new GuardianHullReinforcementPackage("GUARDIAN_HULL_REINFORCEMENT_PACKAGE_4_D", HorizonsBlueprintName.GUARDIAN_HULL_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_4, ModuleClass.D, Origin.GUARDIAN,414720, "Int_GuardianHullReinforcement_Size4_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  8.00), Map.entry(HorizonsModifier.HULL_REINFORCEMENT,413.0), Map.entry(HorizonsModifier.POWER_DRAW,1.24), Map.entry(HorizonsModifier.THERMAL_RESISTANCE, 0.02), Map.entry(HorizonsModifier.CAUSTIC_RESISTANCE, 0.05)));
    public static final GuardianHullReinforcementPackage GUARDIAN_HULL_REINFORCEMENT_PACKAGE_5_E = new GuardianHullReinforcementPackage("GUARDIAN_HULL_REINFORCEMENT_PACKAGE_5_E", HorizonsBlueprintName.GUARDIAN_HULL_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_5, ModuleClass.E, Origin.GUARDIAN,331780, "Int_GuardianHullReinforcement_Size5_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 32.00), Map.entry(HorizonsModifier.HULL_REINFORCEMENT,450.0), Map.entry(HorizonsModifier.POWER_DRAW,1.35), Map.entry(HorizonsModifier.THERMAL_RESISTANCE, 0.02), Map.entry(HorizonsModifier.CAUSTIC_RESISTANCE, 0.05)));
    public static final GuardianHullReinforcementPackage GUARDIAN_HULL_REINFORCEMENT_PACKAGE_5_D = new GuardianHullReinforcementPackage("GUARDIAN_HULL_REINFORCEMENT_PACKAGE_5_D", HorizonsBlueprintName.GUARDIAN_HULL_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_5, ModuleClass.D, Origin.GUARDIAN,995330, "Int_GuardianHullReinforcement_Size5_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 16.00), Map.entry(HorizonsModifier.HULL_REINFORCEMENT,488.0), Map.entry(HorizonsModifier.POWER_DRAW,1.46), Map.entry(HorizonsModifier.THERMAL_RESISTANCE, 0.02), Map.entry(HorizonsModifier.CAUSTIC_RESISTANCE, 0.05)));

    public static final List<GuardianHullReinforcementPackage> GUARDIAN_HULL_REINFORCEMENT_PACKAGES = List.of(
            GUARDIAN_HULL_REINFORCEMENT_PACKAGE_1_E,
            GUARDIAN_HULL_REINFORCEMENT_PACKAGE_1_D,
            GUARDIAN_HULL_REINFORCEMENT_PACKAGE_2_E,
            GUARDIAN_HULL_REINFORCEMENT_PACKAGE_2_D,
            GUARDIAN_HULL_REINFORCEMENT_PACKAGE_3_E,
            GUARDIAN_HULL_REINFORCEMENT_PACKAGE_3_D,
            GUARDIAN_HULL_REINFORCEMENT_PACKAGE_4_E,
            GUARDIAN_HULL_REINFORCEMENT_PACKAGE_4_D,
            GUARDIAN_HULL_REINFORCEMENT_PACKAGE_5_E,
            GUARDIAN_HULL_REINFORCEMENT_PACKAGE_5_D
    );

    public GuardianHullReinforcementPackage(final String id, final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final Origin origin, final int basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, basePrice, internalName, attributes);
    }

    public GuardianHullReinforcementPackage(final GuardianHullReinforcementPackage hullReinforcementPackage) {
        super(hullReinforcementPackage  );
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
    public ShipModule Clone() {
        return new GuardianHullReinforcementPackage(this);
    }

}
