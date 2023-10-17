package nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint;

import nl.jixxed.eliteodysseymaterials.constants.horizons.ExperimentalEffectBlueprints;
import nl.jixxed.eliteodysseymaterials.constants.horizons.hardpoints.PlasmaAcceleratorBlueprints;
import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.List;
import java.util.Map;

public class PlasmaAccelerator extends HardpointModule {
    //    		  83230 : { mtype:'hpa', cost:  834200, name:'Plasma Accelerator',          mount:'F',              class:2, rating:'C', mass: 4.00, integ:51, pwrdraw:1.43, boottime:0, dps:17.921, damage:54.300, distdraw:8.650,thmload:15.58, pierce:100, maximumrng:3500, shotspd: 875, rof:0.330, bstint:3.030,                      ammoclip: 5, ammomax: 100,            rldtime:6.0, brcdmg:46.2, minbrc:40, maxbrc:80,             abswgt:32.5 / .543 , kinwgt:10.9/ .543 , thmwgt:10.9/ .543 , dmgfall:2000, ammocost:200, fdid:128049465, fdname:'Hpt_PlasmaAccelerator_Fixed_Medium', eddbid:873 },
    //            83320 : { mtype:'hpa', cost: 3051200, name:'Plasma Accelerator',          mount:'F',              class:3, rating:'B', mass: 8.00, integ:64, pwrdraw:1.97, boottime:0, dps:24.174, damage:83.400,distdraw:13.600,thmload:21.75, pierce:100, maximumrng:3500, shotspd: 875, rof:0.290, bstint:3.450,                      ammoclip: 5, ammomax: 100,            rldtime:6.0, brcdmg:70.9, minbrc:40, maxbrc:80,             abswgt:50   / .834 , kinwgt:16.7/ .834 , thmwgt:16.7/ .834 , dmgfall:2000, ammocost:200, fdid:128049466, fdname:'Hpt_PlasmaAccelerator_Fixed_Large', eddbid:874 },
    //            83410 : { mtype:'hpa', cost:13793600, name:'Plasma Accelerator',          mount:'F',              class:4, rating:'A', mass:16.00, integ:80, pwrdraw:2.63, boottime:0, dps:31.313,damage:125.250,distdraw:21.040,thmload:29.46, pierce:100, maximumrng:3500, shotspd: 875, rof:0.250, bstint:4.000,                      ammoclip: 5, ammomax: 100,            rldtime:6.0,brcdmg:106.5, minbrc:40, maxbrc:80,             abswgt:75.25/1.2525, kinwgt:25  /1.2525, thmwgt:25  /1.2525, dmgfall:2000, ammocost:200, fdid:128049467, fdname:'Hpt_PlasmaAccelerator_Fixed_Huge', eddbid:875 },
    //            83324 : { mtype:'hpa', cost: 4576800, name:'Advanced Plasma Accelerator', mount:'F', tag:'P',     class:3, rating:'B', mass: 8.00, integ:64, pwrdraw:1.97, boottime:0, dps:28.667, damage:34.400, distdraw:5.500,thmload:11.00, pierce:100, maximumrng:3500, shotspd: 875, rof:0.833, bstint:1.200,                      ammoclip:20, ammomax: 300,            rldtime:6.0, brcdmg:30.9, minbrc:40, maxbrc:80,             abswgt:20.6 / .344 , kinwgt: 6.9/ .344 , thmwgt: 6.9/ .344 , dmgfall:2000, ammocost:200, fdid:128671339, fdname:'Hpt_PlasmaAccelerator_Fixed_Large_Advanced', eddbid:1482 }, // powerplay // verify
    public static final PlasmaAccelerator PLASMA_ACCELERATOR_2_C_F = new PlasmaAccelerator("PLASMA_ACCELERATOR_2_C_F", HorizonsBlueprintName.PLASMA_ACCELERATOR, ModuleSize.SIZE_2, ModuleClass.C, false, Mounting.FIXED, 834200, "Hpt_PlasmaAccelerator_Fixed_Medium", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  4.0), Map.entry(HorizonsModifier.INTEGRITY,  51.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.43), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  17.921), Map.entry(HorizonsModifier.DAMAGE,  54.3), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  8.65), Map.entry(HorizonsModifier.THERMAL_LOAD,  15.58), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  100.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  3500.0), Map.entry(HorizonsModifier.SHOT_SPEED,  875.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  0.33), Map.entry(HorizonsModifier.BURST_INTERVAL,  3.03), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  5.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  100.0), Map.entry(HorizonsModifier.RELOAD_TIME,  6.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  46.2), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  40.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  80.0), Map.entry(HorizonsModifier.ABSOLUTE_DAMAGE_RATIO, .325/.543), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO, .109/.543), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, .109/.543), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  2000.0), Map.entry(HorizonsModifier.JITTER,  0.0), Map.entry(HorizonsModifier.DAMAGE_INCREASES_WITH_HEAT_LEVEL,false), Map.entry(HorizonsModifier.TARGET_GIMBAL_TURRET_TRACKING_REDUCED,false), Map.entry(HorizonsModifier.TARGET_LOSES_TARGET_LOCK,false), Map.entry(HorizonsModifier.RELOAD_FROM_SHIP_FUEL,false), Map.entry(HorizonsModifier.TARGET_SENSOR_ACUITY_REDUCED,false), Map.entry(HorizonsModifier.PART_OF_DAMAGE_THROUGH_SHIELDS,false)));
    public static final PlasmaAccelerator PLASMA_ACCELERATOR_3_B_F = new PlasmaAccelerator("PLASMA_ACCELERATOR_3_B_F", HorizonsBlueprintName.PLASMA_ACCELERATOR, ModuleSize.SIZE_3, ModuleClass.B, false, Mounting.FIXED, 3051200, "Hpt_PlasmaAccelerator_Fixed_Large", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  8.0), Map.entry(HorizonsModifier.INTEGRITY,  64.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.97), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  24.174), Map.entry(HorizonsModifier.DAMAGE,  83.4), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  13.6), Map.entry(HorizonsModifier.THERMAL_LOAD,  21.75), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  100.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  3500.0), Map.entry(HorizonsModifier.SHOT_SPEED,  875.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  0.29), Map.entry(HorizonsModifier.BURST_INTERVAL,  3.45), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  5.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  100.0), Map.entry(HorizonsModifier.RELOAD_TIME,  6.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  70.9), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  40.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  80.0), Map.entry(HorizonsModifier.ABSOLUTE_DAMAGE_RATIO, .50/.834), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO, .167/.834), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, .167/.834), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  2000.0), Map.entry(HorizonsModifier.JITTER,  0.0), Map.entry(HorizonsModifier.DAMAGE_INCREASES_WITH_HEAT_LEVEL,false), Map.entry(HorizonsModifier.TARGET_GIMBAL_TURRET_TRACKING_REDUCED,false), Map.entry(HorizonsModifier.TARGET_LOSES_TARGET_LOCK,false), Map.entry(HorizonsModifier.RELOAD_FROM_SHIP_FUEL,false), Map.entry(HorizonsModifier.TARGET_SENSOR_ACUITY_REDUCED,false), Map.entry(HorizonsModifier.PART_OF_DAMAGE_THROUGH_SHIELDS,false)));
    public static final PlasmaAccelerator PLASMA_ACCELERATOR_4_A_F = new PlasmaAccelerator("PLASMA_ACCELERATOR_4_A_F", HorizonsBlueprintName.PLASMA_ACCELERATOR, ModuleSize.SIZE_4, ModuleClass.A, false, Mounting.FIXED, 13793600, "Hpt_PlasmaAccelerator_Fixed_Huge", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  16.0), Map.entry(HorizonsModifier.INTEGRITY,  80.0), Map.entry(HorizonsModifier.POWER_DRAW,  2.63), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  31.313), Map.entry(HorizonsModifier.DAMAGE,  125.25), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  21.04), Map.entry(HorizonsModifier.THERMAL_LOAD,  29.46), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  100.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  3500.0), Map.entry(HorizonsModifier.SHOT_SPEED,  875.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  0.25), Map.entry(HorizonsModifier.BURST_INTERVAL,  4.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  5.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  100.0), Map.entry(HorizonsModifier.RELOAD_TIME,  6.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  106.5), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  40.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  80.0), Map.entry(HorizonsModifier.ABSOLUTE_DAMAGE_RATIO, .7525/1.2525), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO, .25/1.2525), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, .25/1.2525), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  2000.0), Map.entry(HorizonsModifier.JITTER,  0.0), Map.entry(HorizonsModifier.DAMAGE_INCREASES_WITH_HEAT_LEVEL,false), Map.entry(HorizonsModifier.TARGET_GIMBAL_TURRET_TRACKING_REDUCED,false), Map.entry(HorizonsModifier.TARGET_LOSES_TARGET_LOCK,false), Map.entry(HorizonsModifier.RELOAD_FROM_SHIP_FUEL,false), Map.entry(HorizonsModifier.TARGET_SENSOR_ACUITY_REDUCED,false), Map.entry(HorizonsModifier.PART_OF_DAMAGE_THROUGH_SHIELDS,false)));
    public static final PlasmaAccelerator ADVANCED_PLASMA_ACCELERATOR_3_B_F = new PlasmaAccelerator("ADVANCED_PLASMA_ACCELERATOR_3_B_F", HorizonsBlueprintName.ADVANCED_PLASMA_ACCELERATOR, ModuleSize.SIZE_3, ModuleClass.B, Origin.POWERPLAY, false, Mounting.FIXED, 4576800, "Hpt_PlasmaAccelerator_Fixed_Large_Advanced", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  8.0), Map.entry(HorizonsModifier.INTEGRITY,  64.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.97), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  28.667), Map.entry(HorizonsModifier.DAMAGE,  34.4), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  5.5), Map.entry(HorizonsModifier.THERMAL_LOAD,  11.0), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  100.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  3500.0), Map.entry(HorizonsModifier.SHOT_SPEED,  875.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  0.833), Map.entry(HorizonsModifier.BURST_INTERVAL,  1.2), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  20.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  300.0), Map.entry(HorizonsModifier.RELOAD_TIME,  6.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  30.9), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  40.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  80.0), Map.entry(HorizonsModifier.ABSOLUTE_DAMAGE_RATIO, .206/.344), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO, .69/.344), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, .69/.344), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  2000.0), Map.entry(HorizonsModifier.JITTER,  0.0), Map.entry(HorizonsModifier.DAMAGE_INCREASES_WITH_HEAT_LEVEL,false), Map.entry(HorizonsModifier.TARGET_GIMBAL_TURRET_TRACKING_REDUCED,false), Map.entry(HorizonsModifier.TARGET_LOSES_TARGET_LOCK,false), Map.entry(HorizonsModifier.RELOAD_FROM_SHIP_FUEL,false), Map.entry(HorizonsModifier.TARGET_SENSOR_ACUITY_REDUCED,false), Map.entry(HorizonsModifier.PART_OF_DAMAGE_THROUGH_SHIELDS,false)));
//, Map.entry(HorizonsModifier.DAMAGE_INCREASES_WITH_HEAT_LEVEL,false), Map.entry(HorizonsModifier.TARGET_GIMBAL_TURRET_TRACKING_REDUCED,false), Map.entry(HorizonsModifier.TARGET_LOSES_TARGET_LOCK,false), Map.entry(HorizonsModifier.RELOAD_FROM_SHIP_FUEL,false), Map.entry(HorizonsModifier.TARGET_SENSOR_ACUITY_REDUCED,false), Map.entry(HorizonsModifier.PART_OF_DAMAGE_THROUGH_SHIELDS,false)
    //    DAMAGE_INCREASES_WITH_HEAT_LEVEL
//            TARGET_GIMBAL_TURRET_TRACKING_REDUCED
//    TARGET_LOSES_TARGET_LOCK
//            RELOAD_FROM_SHIP_FUEL
//    TARGET_SENSOR_ACUITY_REDUCED
//            PART_OF_DAMAGE_THROUGH_SHIELDS
    public static final List<PlasmaAccelerator> PLASMA_ACCELERATORS = List.of(
            PLASMA_ACCELERATOR_2_C_F,
            PLASMA_ACCELERATOR_3_B_F,
            PLASMA_ACCELERATOR_4_A_F,
            ADVANCED_PLASMA_ACCELERATOR_3_B_F
    );
    public PlasmaAccelerator(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public PlasmaAccelerator(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
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
    public String getNonSortingClarifier() {
        //TODO localize
        if(ADVANCED_PLASMA_ACCELERATOR_3_B_F.equals(this)) {
            return " Adv.";
        }

        return super.getNonSortingClarifier();
    }
}
