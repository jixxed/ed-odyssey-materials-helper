package nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.military;

import nl.jixxed.eliteodysseymaterials.constants.horizons.ExperimentalEffectBlueprints;
import nl.jixxed.eliteodysseymaterials.constants.horizons.optional_internals.HullReinforcementBlueprints;
import nl.jixxed.eliteodysseymaterials.domain.ships.MilitaryOptionalModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleClass;
import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleSize;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.List;
import java.util.Map;

public class HullReinforcementPackage extends MilitaryOptionalModule {
    public static final HullReinforcementPackage HULL_REINFORCEMENT_PACKAGE_1_E = new HullReinforcementPackage("HULL_REINFORCEMENT_PACKAGE_1_E", HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_1, ModuleClass.E, 5000, "Int_HullReinforcement_Size1_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.00), Map.entry(HorizonsModifier.HULL_REINFORCEMENT, 80.0), Map.entry(HorizonsModifier.HULL_BOOST, 0.0), Map.entry(HorizonsModifier.CAUSTIC_RESISTANCE, 0D), Map.entry(HorizonsModifier.KINETIC_RESISTANCE, 0.005), Map.entry(HorizonsModifier.THERMAL_RESISTANCE, 0.005), Map.entry(HorizonsModifier.EXPLOSIVE_RESISTANCE, 0.005)));
    public static final HullReinforcementPackage HULL_REINFORCEMENT_PACKAGE_1_D = new HullReinforcementPackage("HULL_REINFORCEMENT_PACKAGE_1_D", HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_1, ModuleClass.D, 15000, "Int_HullReinforcement_Size1_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 1.00), Map.entry(HorizonsModifier.HULL_REINFORCEMENT, 110.0), Map.entry(HorizonsModifier.HULL_BOOST, 0.0), Map.entry(HorizonsModifier.CAUSTIC_RESISTANCE, 0D), Map.entry(HorizonsModifier.KINETIC_RESISTANCE, 0.005), Map.entry(HorizonsModifier.THERMAL_RESISTANCE, 0.005), Map.entry(HorizonsModifier.EXPLOSIVE_RESISTANCE, 0.005)));
    public static final HullReinforcementPackage HULL_REINFORCEMENT_PACKAGE_2_E = new HullReinforcementPackage("HULL_REINFORCEMENT_PACKAGE_2_E", HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_2, ModuleClass.E, 12000, "Int_HullReinforcement_Size2_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.00), Map.entry(HorizonsModifier.HULL_REINFORCEMENT, 150.0), Map.entry(HorizonsModifier.HULL_BOOST, 0.0), Map.entry(HorizonsModifier.CAUSTIC_RESISTANCE, 0D), Map.entry(HorizonsModifier.KINETIC_RESISTANCE, 0.010), Map.entry(HorizonsModifier.THERMAL_RESISTANCE, 0.010), Map.entry(HorizonsModifier.EXPLOSIVE_RESISTANCE, 0.010)));
    public static final HullReinforcementPackage HULL_REINFORCEMENT_PACKAGE_2_D = new HullReinforcementPackage("HULL_REINFORCEMENT_PACKAGE_2_D", HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_2, ModuleClass.D, 36000, "Int_HullReinforcement_Size2_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 2.00), Map.entry(HorizonsModifier.HULL_REINFORCEMENT, 190.0), Map.entry(HorizonsModifier.HULL_BOOST, 0.0), Map.entry(HorizonsModifier.CAUSTIC_RESISTANCE, 0D), Map.entry(HorizonsModifier.KINETIC_RESISTANCE, 0.010), Map.entry(HorizonsModifier.THERMAL_RESISTANCE, 0.010), Map.entry(HorizonsModifier.EXPLOSIVE_RESISTANCE, 0.010)));
    public static final HullReinforcementPackage HULL_REINFORCEMENT_PACKAGE_3_E = new HullReinforcementPackage("HULL_REINFORCEMENT_PACKAGE_3_E", HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_3, ModuleClass.E, 28000, "Int_HullReinforcement_Size3_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 8.00), Map.entry(HorizonsModifier.HULL_REINFORCEMENT, 230.0), Map.entry(HorizonsModifier.HULL_BOOST, 0.0), Map.entry(HorizonsModifier.CAUSTIC_RESISTANCE, 0D), Map.entry(HorizonsModifier.KINETIC_RESISTANCE, 0.015), Map.entry(HorizonsModifier.THERMAL_RESISTANCE, 0.015), Map.entry(HorizonsModifier.EXPLOSIVE_RESISTANCE, 0.015)));
    public static final HullReinforcementPackage HULL_REINFORCEMENT_PACKAGE_3_D = new HullReinforcementPackage("HULL_REINFORCEMENT_PACKAGE_3_D", HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_3, ModuleClass.D, 84000, "Int_HullReinforcement_Size3_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.00), Map.entry(HorizonsModifier.HULL_REINFORCEMENT, 260.0), Map.entry(HorizonsModifier.HULL_BOOST, 0.0), Map.entry(HorizonsModifier.CAUSTIC_RESISTANCE, 0D), Map.entry(HorizonsModifier.KINETIC_RESISTANCE, 0.015), Map.entry(HorizonsModifier.THERMAL_RESISTANCE, 0.015), Map.entry(HorizonsModifier.EXPLOSIVE_RESISTANCE, 0.015)));
    public static final HullReinforcementPackage HULL_REINFORCEMENT_PACKAGE_4_E = new HullReinforcementPackage("HULL_REINFORCEMENT_PACKAGE_4_E", HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_4, ModuleClass.E, 65000, "Int_HullReinforcement_Size4_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 16.00), Map.entry(HorizonsModifier.HULL_REINFORCEMENT, 300.0), Map.entry(HorizonsModifier.HULL_BOOST, 0.0), Map.entry(HorizonsModifier.CAUSTIC_RESISTANCE, 0D), Map.entry(HorizonsModifier.KINETIC_RESISTANCE, 0.020), Map.entry(HorizonsModifier.THERMAL_RESISTANCE, 0.020), Map.entry(HorizonsModifier.EXPLOSIVE_RESISTANCE, 0.020)));
    public static final HullReinforcementPackage HULL_REINFORCEMENT_PACKAGE_4_D = new HullReinforcementPackage("HULL_REINFORCEMENT_PACKAGE_4_D", HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_4, ModuleClass.D, 195000, "Int_HullReinforcement_Size4_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 8.00), Map.entry(HorizonsModifier.HULL_REINFORCEMENT, 330.0), Map.entry(HorizonsModifier.HULL_BOOST, 0.0), Map.entry(HorizonsModifier.CAUSTIC_RESISTANCE, 0D), Map.entry(HorizonsModifier.KINETIC_RESISTANCE, 0.020), Map.entry(HorizonsModifier.THERMAL_RESISTANCE, 0.020), Map.entry(HorizonsModifier.EXPLOSIVE_RESISTANCE, 0.020)));
    public static final HullReinforcementPackage HULL_REINFORCEMENT_PACKAGE_5_E = new HullReinforcementPackage("HULL_REINFORCEMENT_PACKAGE_5_E", HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_5, ModuleClass.E, 150000, "Int_HullReinforcement_Size5_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 32.00), Map.entry(HorizonsModifier.HULL_REINFORCEMENT, 360.0), Map.entry(HorizonsModifier.HULL_BOOST, 0.0), Map.entry(HorizonsModifier.CAUSTIC_RESISTANCE, 0D), Map.entry(HorizonsModifier.KINETIC_RESISTANCE, 0.025), Map.entry(HorizonsModifier.THERMAL_RESISTANCE, 0.025), Map.entry(HorizonsModifier.EXPLOSIVE_RESISTANCE, 0.025)));
    public static final HullReinforcementPackage HULL_REINFORCEMENT_PACKAGE_5_D = new HullReinforcementPackage("HULL_REINFORCEMENT_PACKAGE_5_D", HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, ModuleSize.SIZE_5, ModuleClass.D, 450000, "Int_HullReinforcement_Size5_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 16.00), Map.entry(HorizonsModifier.HULL_REINFORCEMENT, 390.0), Map.entry(HorizonsModifier.HULL_BOOST, 0.0), Map.entry(HorizonsModifier.CAUSTIC_RESISTANCE, 0D), Map.entry(HorizonsModifier.KINETIC_RESISTANCE, 0.025), Map.entry(HorizonsModifier.THERMAL_RESISTANCE, 0.025), Map.entry(HorizonsModifier.EXPLOSIVE_RESISTANCE, 0.025)));

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
    public static final List<HorizonsModifier> HIDDEN_STATS = List.of(HorizonsModifier.POWER_DRAW, HorizonsModifier.CAUSTIC_RESISTANCE);

    private HullReinforcementPackage(final String id, final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final int basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, basePrice, internalName, attributes);
    }

    private HullReinforcementPackage(final HullReinforcementPackage hullReinforcementPackage) {
        super(hullReinforcementPackage);
    }


    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints() {
        return HullReinforcementBlueprints.BLUEPRINTS.keySet().stream().toList();
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedExperimentalEffects() {
        return ExperimentalEffectBlueprints.HULL_REINFORCEMENT_PACKAGE.keySet().stream().toList();
    }


    @Override
    public HullReinforcementPackage Clone() {
        return new HullReinforcementPackage(this);
    }

    @Override
    public boolean isHiddenStat(HorizonsModifier modifier) {
        if(HIDDEN_STATS.contains(modifier)){
            return true;
        }
        return super.isHiddenStat(modifier);
    }
}
