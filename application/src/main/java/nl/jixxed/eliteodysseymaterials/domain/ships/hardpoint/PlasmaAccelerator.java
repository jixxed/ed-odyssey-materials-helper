package nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint;

import nl.jixxed.eliteodysseymaterials.constants.horizons.ExperimentalEffectBlueprints;
import nl.jixxed.eliteodysseymaterials.constants.horizons.hardpoints.PlasmaAcceleratorBlueprints;
import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.List;
import java.util.Map;

public class PlasmaAccelerator extends HardpointModule {
    public static final PlasmaAccelerator PLASMA_ACCELERATOR_2_C_F          = new PlasmaAccelerator("PLASMA_ACCELERATOR_2_C_F", HorizonsBlueprintName.PLASMA_ACCELERATOR, ModuleSize.SIZE_2, ModuleClass.C, false, Mounting.FIXED, 834200, "Hpt_PlasmaAccelerator_Fixed_Medium", Map.ofEntries(                                              Map.entry(HorizonsModifier.MASS,  4.0),  Map.entry(HorizonsModifier.INTEGRITY,  51.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.43), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  17.921), Map.entry(HorizonsModifier.DAMAGE,  54.3),   Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  8.65),  Map.entry(HorizonsModifier.THERMAL_LOAD,  15.58), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  100.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  3500.0), Map.entry(HorizonsModifier.SHOT_SPEED,  875.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  0.33),  Map.entry(HorizonsModifier.BURST_INTERVAL,  3.03), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  5.0),  Map.entry(HorizonsModifier.AMMO_MAXIMUM,  100.0), Map.entry(HorizonsModifier.RELOAD_TIME,  6.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  0.85),  Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.40), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.80), Map.entry(HorizonsModifier.ABSOLUTE_DAMAGE_RATIO, 0.6), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO, 0.2), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.2), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  2000.0), Map.entry(HorizonsModifier.JITTER,  0.0), Map.entry(HorizonsModifier.DAMAGE_INCREASES_WITH_HEAT_LEVEL,false), Map.entry(HorizonsModifier.TARGET_GIMBAL_TURRET_TRACKING_REDUCED,false), Map.entry(HorizonsModifier.TARGET_LOSES_TARGET_LOCK,false), Map.entry(HorizonsModifier.RELOAD_FROM_SHIP_FUEL,false), Map.entry(HorizonsModifier.TARGET_SENSOR_ACUITY_REDUCED,false), Map.entry(HorizonsModifier.PART_OF_DAMAGE_THROUGH_SHIELDS,false), Map.entry(HorizonsModifier.AMMO_COST,200.0)));
    public static final PlasmaAccelerator PLASMA_ACCELERATOR_3_B_F          = new PlasmaAccelerator("PLASMA_ACCELERATOR_3_B_F", HorizonsBlueprintName.PLASMA_ACCELERATOR, ModuleSize.SIZE_3, ModuleClass.B, false, Mounting.FIXED, 3051200, "Hpt_PlasmaAccelerator_Fixed_Large", Map.ofEntries(                                              Map.entry(HorizonsModifier.MASS,  8.0),  Map.entry(HorizonsModifier.INTEGRITY,  64.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.97), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  24.174), Map.entry(HorizonsModifier.DAMAGE,  83.4),   Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  13.6),  Map.entry(HorizonsModifier.THERMAL_LOAD,  21.75), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  100.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  3500.0), Map.entry(HorizonsModifier.SHOT_SPEED,  875.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  0.29),  Map.entry(HorizonsModifier.BURST_INTERVAL,  3.45), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  5.0),  Map.entry(HorizonsModifier.AMMO_MAXIMUM,  100.0), Map.entry(HorizonsModifier.RELOAD_TIME,  6.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  0.85),  Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.40), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.80), Map.entry(HorizonsModifier.ABSOLUTE_DAMAGE_RATIO, 0.6), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO, 0.2), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.2), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  2000.0), Map.entry(HorizonsModifier.JITTER,  0.0), Map.entry(HorizonsModifier.DAMAGE_INCREASES_WITH_HEAT_LEVEL,false), Map.entry(HorizonsModifier.TARGET_GIMBAL_TURRET_TRACKING_REDUCED,false), Map.entry(HorizonsModifier.TARGET_LOSES_TARGET_LOCK,false), Map.entry(HorizonsModifier.RELOAD_FROM_SHIP_FUEL,false), Map.entry(HorizonsModifier.TARGET_SENSOR_ACUITY_REDUCED,false), Map.entry(HorizonsModifier.PART_OF_DAMAGE_THROUGH_SHIELDS,false), Map.entry(HorizonsModifier.AMMO_COST,200.0)));
    public static final PlasmaAccelerator PLASMA_ACCELERATOR_4_A_F          = new PlasmaAccelerator("PLASMA_ACCELERATOR_4_A_F", HorizonsBlueprintName.PLASMA_ACCELERATOR, ModuleSize.SIZE_4, ModuleClass.A, false, Mounting.FIXED, 13793600, "Hpt_PlasmaAccelerator_Fixed_Huge", Map.ofEntries(                                              Map.entry(HorizonsModifier.MASS,  16.0), Map.entry(HorizonsModifier.INTEGRITY,  80.0), Map.entry(HorizonsModifier.POWER_DRAW,  2.63), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  31.313), Map.entry(HorizonsModifier.DAMAGE,  125.25), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  21.04), Map.entry(HorizonsModifier.THERMAL_LOAD,  29.46), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  100.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  3500.0), Map.entry(HorizonsModifier.SHOT_SPEED,  875.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  0.25),  Map.entry(HorizonsModifier.BURST_INTERVAL,  4.0),  Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  5.0),  Map.entry(HorizonsModifier.AMMO_MAXIMUM,  100.0), Map.entry(HorizonsModifier.RELOAD_TIME,  6.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  0.85), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.40), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.80), Map.entry(HorizonsModifier.ABSOLUTE_DAMAGE_RATIO, 0.6), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO, 0.2), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.2), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  2000.0), Map.entry(HorizonsModifier.JITTER,  0.0), Map.entry(HorizonsModifier.DAMAGE_INCREASES_WITH_HEAT_LEVEL,false), Map.entry(HorizonsModifier.TARGET_GIMBAL_TURRET_TRACKING_REDUCED,false), Map.entry(HorizonsModifier.TARGET_LOSES_TARGET_LOCK,false), Map.entry(HorizonsModifier.RELOAD_FROM_SHIP_FUEL,false), Map.entry(HorizonsModifier.TARGET_SENSOR_ACUITY_REDUCED,false), Map.entry(HorizonsModifier.PART_OF_DAMAGE_THROUGH_SHIELDS,false), Map.entry(HorizonsModifier.AMMO_COST,200.0)));
    public static final PlasmaAccelerator ADVANCED_PLASMA_ACCELERATOR_3_B_F = new PlasmaAccelerator("ADVANCED_PLASMA_ACCELERATOR_3_B_F", HorizonsBlueprintName.ADVANCED_PLASMA_ACCELERATOR, ModuleSize.SIZE_3, ModuleClass.B, Origin.POWERPLAY, false, Mounting.FIXED, 4576800, "Hpt_PlasmaAccelerator_Fixed_Large_Advanced", Map.ofEntries( Map.entry(HorizonsModifier.MASS,  8.0),  Map.entry(HorizonsModifier.INTEGRITY,  64.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.97), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  28.667), Map.entry(HorizonsModifier.DAMAGE,  34.4),   Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  5.5),   Map.entry(HorizonsModifier.THERMAL_LOAD,  11.0),  Map.entry(HorizonsModifier.ARMOUR_PIERCING,  100.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  3500.0), Map.entry(HorizonsModifier.SHOT_SPEED,  875.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  0.833), Map.entry(HorizonsModifier.BURST_INTERVAL,  1.2),  Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  20.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  300.0), Map.entry(HorizonsModifier.RELOAD_TIME,  6.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  0.9),  Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.40), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.80), Map.entry(HorizonsModifier.ABSOLUTE_DAMAGE_RATIO, 0.6), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO, 0.2), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.2), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  2000.0), Map.entry(HorizonsModifier.JITTER,  0.0), Map.entry(HorizonsModifier.DAMAGE_INCREASES_WITH_HEAT_LEVEL,false), Map.entry(HorizonsModifier.TARGET_GIMBAL_TURRET_TRACKING_REDUCED,false), Map.entry(HorizonsModifier.TARGET_LOSES_TARGET_LOCK,false), Map.entry(HorizonsModifier.RELOAD_FROM_SHIP_FUEL,false), Map.entry(HorizonsModifier.TARGET_SENSOR_ACUITY_REDUCED,false), Map.entry(HorizonsModifier.PART_OF_DAMAGE_THROUGH_SHIELDS,false), Map.entry(HorizonsModifier.AMMO_COST,200.0)));

    public static final List<PlasmaAccelerator> PLASMA_ACCELERATORS = List.of(
            PLASMA_ACCELERATOR_2_C_F,
            PLASMA_ACCELERATOR_3_B_F,
            PLASMA_ACCELERATOR_4_A_F,
            ADVANCED_PLASMA_ACCELERATOR_3_B_F
    );
    public PlasmaAccelerator(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public PlasmaAccelerator(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public PlasmaAccelerator(PlasmaAccelerator plasmaAccelerator) {
        super(plasmaAccelerator);
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints() {
        return PlasmaAcceleratorBlueprints.BLUEPRINTS.keySet().stream().toList();
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedExperimentalEffects() {
        return ExperimentalEffectBlueprints.PLASMA_ACCELERATOR.keySet().stream().toList();
    }

    @Override
    public PlasmaAccelerator Clone() {
        return new PlasmaAccelerator(this);
    }

    @Override
    public boolean isAdvanced() {
        return ADVANCED_PLASMA_ACCELERATOR_3_B_F.equals(this);
    }

    @Override
    public String getClarifier() {
        if(ADVANCED_PLASMA_ACCELERATOR_3_B_F.equals(this)){
            return " " + LocaleService.getLocalizedStringForCurrentLocale(getName().getLocalizationKey()) ;
        }
        return super.getClarifier();
    }

    @Override
    public int getGrouping() {
        return switch (getId()) {
            case "PLASMA_ACCELERATOR_2_C_F", "PLASMA_ACCELERATOR_3_B_F", "PLASMA_ACCELERATOR_4_A_F" -> 1;
            case "ADVANCED_PLASMA_ACCELERATOR_3_B_F" -> 2;
            default -> 0;
        };
    }
}
