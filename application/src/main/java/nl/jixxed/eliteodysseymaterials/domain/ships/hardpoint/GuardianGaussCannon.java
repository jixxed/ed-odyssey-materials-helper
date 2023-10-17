package nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint;

import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GuardianGaussCannon extends HardpointModule {
    //      	  88140 : {mtype:'hexgg',cost:  167250, name:'Guardian Gauss Cannon',       mount:'F', tag:'G',     class:1, rating:'D', mass: 2.00, integ:40, pwrdraw:1.91, boottime:0, dps:48.193, damage:40.000, distdraw:3.800,thmload:15.00, pierce:140, maximumrng:3000, duration:1.2, rof:1.205, bstint:0.830,                      ammoclip: 1, ammomax:  80,            rldtime:1.0, brcdmg:20.0, minbrc:20, maxbrc:40,             thmwgt:20   /.40  , axewgt:20   /.40  , dmgfall:1500, ammocost: 75, limit:'hex', fdid:128891610, fdname:'Hpt_Guardian_GaussCannon_Fixed_Small', eddbid:1784 }, // guardian tech broker // TODO: 50% brcmul
    //            88220 : {mtype:'hexgg',cost:  543800, name:'Guardian Gauss Cannon',       mount:'F', tag:'G',     class:2, rating:'B', mass: 4.00, integ:42, pwrdraw:2.61, boottime:0, dps:84.337, damage:70.000, distdraw:7.200,thmload:25.00, pierce:140, maximumrng:3000, duration:1.2, rof:1.205, bstint:0.830,                      ammoclip: 1, ammomax:  80,            rldtime:1.0, brcdmg:35.0, minbrc:20, maxbrc:40,             thmwgt:35   /.70  , axewgt:35   /.70  , dmgfall:1500, ammocost: 75, limit:'hex', fdid:128833687, fdname:'Hpt_Guardian_GaussCannon_Fixed_Medium', eddbid:1698 }, // guardian tech broker // verify cost,integ // TODO: 50% brcmul

    //    public static final Cannon CANNON_1_D_F = new Cannon("CANNON_1_D_F", HorizonsBlueprintName.CANNON, ModuleSize.SIZE_1, ModuleClass.D, false, Mounting.FIXED    ,   21100, "Hpt_Cannon_Fixed_Small"  ,Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.00), Map.entry(HorizonsModifier.INTEGRITY, 40.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.34), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 11.250), Map.entry(HorizonsModifier.DAMAGE, 22.500), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.460), Map.entry(HorizonsModifier.THERMAL_LOAD, 1.38), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  35.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE, 3000.0), Map.entry(HorizonsModifier.SHOT_SPEED, 1200.0  ), Map.entry(HorizonsModifier.RATE_OF_FIRE, 0.500), Map.entry(HorizonsModifier.BURST_INTERVAL, 2.000), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  6.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  120.0), Map.entry(HorizonsModifier.RELOAD_TIME, 3.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 21.4), Map.entry(HorizonsModifier.BREACH_CHANCE_MIN, 60.0), Map.entry(HorizonsModifier.BREACH_CHANCE_MAX, 90.0), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO, 100.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 0.0), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START, 3000.0), Map.entry(HorizonsModifier.AMMO_COST, 20.0)));
    public static final GuardianGaussCannon GUARDIAN_GAUSS_CANNON_1_D_F = new GuardianGaussCannon("GUARDIAN_GAUSS_CANNON_1_D_F", HorizonsBlueprintName.GUARDIAN_GAUSS_CANNON, ModuleSize.SIZE_1, ModuleClass.D, Origin.GUARDIAN, false, Mounting.FIXED, 167250, "Hpt_Guardian_GaussCannon_Fixed_Small", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  40.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.91), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  48.193), Map.entry(HorizonsModifier.DAMAGE,  40.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  3.8), Map.entry(HorizonsModifier.THERMAL_LOAD,  15.0), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  140.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  3000.0), Map.entry(HorizonsModifier.DURATION,  1.2), Map.entry(HorizonsModifier.RATE_OF_FIRE,  1.205), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.83), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  80.0), Map.entry(HorizonsModifier.RELOAD_TIME,  1.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  20.0), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  20.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  40.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  1500.0)));
    public static final GuardianGaussCannon GUARDIAN_GAUSS_CANNON_2_B_F = new GuardianGaussCannon("GUARDIAN_GAUSS_CANNON_2_B_F", HorizonsBlueprintName.GUARDIAN_GAUSS_CANNON, ModuleSize.SIZE_2, ModuleClass.B, Origin.GUARDIAN, false, Mounting.FIXED, 543800, "Hpt_Guardian_GaussCannon_Fixed_Medium", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  4.0), Map.entry(HorizonsModifier.INTEGRITY,  42.0), Map.entry(HorizonsModifier.POWER_DRAW,  2.61), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  84.337), Map.entry(HorizonsModifier.DAMAGE,  70.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  7.2), Map.entry(HorizonsModifier.THERMAL_LOAD,  25.0), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  140.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  3000.0), Map.entry(HorizonsModifier.DURATION,  1.2), Map.entry(HorizonsModifier.RATE_OF_FIRE,  1.205), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.83), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  80.0), Map.entry(HorizonsModifier.RELOAD_TIME,  1.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  35.0), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  20.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  40.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  1500.0)));

    public static final GuardianGaussCannon GUARDIAN_GAUSS_CANNON_1_D_F_PRE = new GuardianGaussCannon("GUARDIAN_GAUSS_CANNON_1_D_F_PRE", HorizonsBlueprintName.GUARDIAN_GAUSS_CANNON_PRE, ModuleSize.SIZE_1, ModuleClass.D, Origin.GUARDIAN, false, Mounting.FIXED, 167250, "Hpt_Guardian_GaussCannon_Fixed_Small", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  40.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.91), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  48.193), Map.entry(HorizonsModifier.DAMAGE,  40.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  3.8), Map.entry(HorizonsModifier.THERMAL_LOAD,  15.0), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  140.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  3000.0), Map.entry(HorizonsModifier.DURATION,  1.2), Map.entry(HorizonsModifier.RATE_OF_FIRE,  1.205), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.83), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  80.0), Map.entry(HorizonsModifier.RELOAD_TIME,  1.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  20.0), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  20.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  40.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  1500.0)));
    public static final GuardianGaussCannon GUARDIAN_GAUSS_CANNON_2_B_F_PRE = new GuardianGaussCannon("GUARDIAN_GAUSS_CANNON_2_B_F_PRE", HorizonsBlueprintName.GUARDIAN_GAUSS_CANNON_PRE, ModuleSize.SIZE_2, ModuleClass.B, Origin.GUARDIAN, false, Mounting.FIXED, 543800, "Hpt_Guardian_GaussCannon_Fixed_Medium", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  4.0), Map.entry(HorizonsModifier.INTEGRITY,  42.0), Map.entry(HorizonsModifier.POWER_DRAW,  2.61), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  84.337), Map.entry(HorizonsModifier.DAMAGE,  70.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  7.2), Map.entry(HorizonsModifier.THERMAL_LOAD,  25.0), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  140.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  3000.0), Map.entry(HorizonsModifier.DURATION,  1.2), Map.entry(HorizonsModifier.RATE_OF_FIRE,  1.205), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.83), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  80.0), Map.entry(HorizonsModifier.RELOAD_TIME,  1.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  35.0), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  20.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  40.0), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  1500.0)));

    static {
        GUARDIAN_GAUSS_CANNON_1_D_F_PRE.getModifications().add(
                new Modification(HorizonsBlueprintType.HIGH_CAPACITY_MAGAZINE_RAPID_FIRE_MODIFICATION, 1.0, HorizonsBlueprintGrade.GRADE_5)
        );
        GUARDIAN_GAUSS_CANNON_2_B_F_PRE.getModifications().add(
                new Modification(HorizonsBlueprintType.HIGH_CAPACITY_MAGAZINE_RAPID_FIRE_MODIFICATION, 1.0, HorizonsBlueprintGrade.GRADE_5)
        );
    }
    public static final List<GuardianGaussCannon> GUARDIAN_GAUSS_CANNONS = List.of(
        GUARDIAN_GAUSS_CANNON_1_D_F,
        GUARDIAN_GAUSS_CANNON_2_B_F,
        GUARDIAN_GAUSS_CANNON_1_D_F_PRE,
        GUARDIAN_GAUSS_CANNON_2_B_F_PRE
    );
    public GuardianGaussCannon(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public GuardianGaussCannon(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public GuardianGaussCannon(GuardianGaussCannon guardianGaussCannon) {
        super(guardianGaussCannon);
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
    public GuardianGaussCannon Clone() {
        return new GuardianGaussCannon(this);
    }

    @Override
    public boolean isPreEngineered() {
        return GUARDIAN_GAUSS_CANNON_1_D_F_PRE.equals(this) || GUARDIAN_GAUSS_CANNON_2_B_F_PRE.equals(this);
    }
}
