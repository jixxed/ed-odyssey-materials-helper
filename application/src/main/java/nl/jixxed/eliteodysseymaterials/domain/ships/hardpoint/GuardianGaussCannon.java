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
    public static final GuardianGaussCannon GUARDIAN_GAUSS_CANNON_1_D_F = new GuardianGaussCannon("GUARDIAN_GAUSS_CANNON_1_D_F", HorizonsBlueprintName.GUARDIAN_GAUSS_CANNON, ModuleSize.SIZE_1, ModuleClass.D, Origin.GUARDIAN, false, Mounting.FIXED, 167250, "Hpt_Guardian_GaussCannon_Fixed_Small", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  40.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.91), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  48.193), Map.entry(HorizonsModifier.DAMAGE,  40.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  3.8), Map.entry(HorizonsModifier.THERMAL_LOAD,  15.0), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  140.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  3000.0), Map.entry(HorizonsModifier.CHARGE_TIME,  1.2), Map.entry(HorizonsModifier.RATE_OF_FIRE,  1.205), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.83), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  80.0), Map.entry(HorizonsModifier.RELOAD_TIME,  1.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  20.0), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.20), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.40), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  1500.0), Map.entry(HorizonsModifier.AMMO_COST,  75.0)));
    public static final GuardianGaussCannon GUARDIAN_GAUSS_CANNON_2_B_F = new GuardianGaussCannon("GUARDIAN_GAUSS_CANNON_2_B_F", HorizonsBlueprintName.GUARDIAN_GAUSS_CANNON, ModuleSize.SIZE_2, ModuleClass.B, Origin.GUARDIAN, false, Mounting.FIXED, 543800, "Hpt_Guardian_GaussCannon_Fixed_Medium", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  4.0), Map.entry(HorizonsModifier.INTEGRITY,  42.0), Map.entry(HorizonsModifier.POWER_DRAW,  2.61), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  84.337), Map.entry(HorizonsModifier.DAMAGE,  70.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  7.2), Map.entry(HorizonsModifier.THERMAL_LOAD,  25.0), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  140.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  3000.0), Map.entry(HorizonsModifier.CHARGE_TIME,  1.2), Map.entry(HorizonsModifier.RATE_OF_FIRE,  1.205), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.83), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  80.0), Map.entry(HorizonsModifier.RELOAD_TIME,  1.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  35.0), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.20), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.40), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  1500.0), Map.entry(HorizonsModifier.AMMO_COST,  75.0)));
    public static final GuardianGaussCannon GUARDIAN_GAUSS_CANNON_1_D_F_PRE = new GuardianGaussCannon("GUARDIAN_GAUSS_CANNON_1_D_F_PRE", HorizonsBlueprintName.GUARDIAN_GAUSS_CANNON_PRE, ModuleSize.SIZE_1, ModuleClass.D, Origin.GUARDIAN, false, Mounting.FIXED, 167250, "Hpt_Guardian_GaussCannon_Fixed_Small", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  40.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.91), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  48.193), Map.entry(HorizonsModifier.DAMAGE,  40.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  3.8), Map.entry(HorizonsModifier.THERMAL_LOAD,  15.0), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  140.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  3000.0), Map.entry(HorizonsModifier.CHARGE_TIME,  1.2), Map.entry(HorizonsModifier.RATE_OF_FIRE,  1.205), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.83), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  80.0), Map.entry(HorizonsModifier.RELOAD_TIME,  1.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  20.0), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.20), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.40), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  1500.0), Map.entry(HorizonsModifier.AMMO_COST,  75.0)));
    public static final GuardianGaussCannon GUARDIAN_GAUSS_CANNON_2_B_F_PRE = new GuardianGaussCannon("GUARDIAN_GAUSS_CANNON_2_B_F_PRE", HorizonsBlueprintName.GUARDIAN_GAUSS_CANNON_PRE, ModuleSize.SIZE_2, ModuleClass.B, Origin.GUARDIAN, false, Mounting.FIXED, 543800, "Hpt_Guardian_GaussCannon_Fixed_Medium", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  4.0), Map.entry(HorizonsModifier.INTEGRITY,  42.0), Map.entry(HorizonsModifier.POWER_DRAW,  2.61), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  84.337), Map.entry(HorizonsModifier.DAMAGE,  70.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  7.2), Map.entry(HorizonsModifier.THERMAL_LOAD,  25.0), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  140.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  3000.0), Map.entry(HorizonsModifier.CHARGE_TIME,  1.2), Map.entry(HorizonsModifier.RATE_OF_FIRE,  1.205), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.83), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  80.0), Map.entry(HorizonsModifier.RELOAD_TIME,  1.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  35.0), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.20), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.40), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  1500.0), Map.entry(HorizonsModifier.AMMO_COST,  75.0)));

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