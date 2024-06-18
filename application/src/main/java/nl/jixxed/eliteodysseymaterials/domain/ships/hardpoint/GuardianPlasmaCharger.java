package nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint;

import nl.jixxed.eliteodysseymaterials.constants.horizons.hardpoints.GuardianPlasmaChargerBlueprints;
import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GuardianPlasmaCharger extends HardpointModule {
    public static final GuardianPlasmaCharger GUARDIAN_PLASMA_CHARGER_1_D_F = new GuardianPlasmaCharger("GUARDIAN_PLASMA_CHARGER_1_D_F", HorizonsBlueprintName.GUARDIAN_PLASMA_CHARGER, ModuleSize.SIZE_1, ModuleClass.D, Origin.GUARDIAN, false, Mounting.FIXED, 176500, "Hpt_Guardian_PlasmaLauncher_Fixed_Small", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  34.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.4), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  5.0 * 1.65), Map.entry(HorizonsModifier.DAMAGE,  1.65), Map.entry(HorizonsModifier.CHARGE_TIME,  1.8), Map.entry(HorizonsModifier.DAMAGE_MULTIPLIER,  17.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  0.68), Map.entry(HorizonsModifier.THERMAL_LOAD,  4.21), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  65.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  3000.0), Map.entry(HorizonsModifier.SHOT_SPEED,  1200.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  5.0), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.2), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  15.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  200.0), Map.entry(HorizonsModifier.RELOAD_TIME,  3.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  0.75), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.50), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.80), Map.entry(HorizonsModifier.ABSOLUTE_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  1000.0), Map.entry(HorizonsModifier.AMMO_COST,  100.0), Map.entry(HorizonsModifier.ANTI_GUARDIAN_ZONE_RESISTANCE,  false)));
    public static final GuardianPlasmaCharger GUARDIAN_PLASMA_CHARGER_1_F_T = new GuardianPlasmaCharger("GUARDIAN_PLASMA_CHARGER_1_F_T", HorizonsBlueprintName.GUARDIAN_PLASMA_CHARGER, ModuleSize.SIZE_1, ModuleClass.F, Origin.GUARDIAN, true, Mounting.TURRETED, 484050, "Hpt_Guardian_PlasmaLauncher_Turret_Small", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  34.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.6), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  5.0 * 1.1), Map.entry(HorizonsModifier.DAMAGE,  1.1), Map.entry(HorizonsModifier.CHARGE_TIME,  1.8), Map.entry(HorizonsModifier.DAMAGE_MULTIPLIER,  17.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  0.8), Map.entry(HorizonsModifier.THERMAL_LOAD,  5.01), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  65.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  3000.0), Map.entry(HorizonsModifier.SHOT_SPEED,  1200.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  5.0), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.2), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  15.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  200.0), Map.entry(HorizonsModifier.RELOAD_TIME,  3.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  0.5), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.50), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.80), Map.entry(HorizonsModifier.ABSOLUTE_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  1000.0), Map.entry(HorizonsModifier.AMMO_COST,  100.0), Map.entry(HorizonsModifier.ANTI_GUARDIAN_ZONE_RESISTANCE,  false)));
    public static final GuardianPlasmaCharger GUARDIAN_PLASMA_CHARGER_2_B_F = new GuardianPlasmaCharger("GUARDIAN_PLASMA_CHARGER_2_B_F", HorizonsBlueprintName.GUARDIAN_PLASMA_CHARGER, ModuleSize.SIZE_2, ModuleClass.B, Origin.GUARDIAN, false, Mounting.FIXED, 567760, "Hpt_Guardian_PlasmaLauncher_Fixed_Medium", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  4.0), Map.entry(HorizonsModifier.INTEGRITY,  42.0), Map.entry(HorizonsModifier.POWER_DRAW,  2.13), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  5.0 * 2.75), Map.entry(HorizonsModifier.DAMAGE,  2.75), Map.entry(HorizonsModifier.CHARGE_TIME,  1.8), Map.entry(HorizonsModifier.DAMAGE_MULTIPLIER,  17.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  1.25), Map.entry(HorizonsModifier.THERMAL_LOAD,  5.21), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  80.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  3000.0), Map.entry(HorizonsModifier.SHOT_SPEED,  1200.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  5.0), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.2), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  15.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  200.0), Map.entry(HorizonsModifier.RELOAD_TIME,  3.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  1.25), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.50), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.80), Map.entry(HorizonsModifier.ABSOLUTE_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  1000.0), Map.entry(HorizonsModifier.AMMO_COST,  100.0), Map.entry(HorizonsModifier.ANTI_GUARDIAN_ZONE_RESISTANCE,  false)));
    public static final GuardianPlasmaCharger GUARDIAN_PLASMA_CHARGER_2_E_T = new GuardianPlasmaCharger("GUARDIAN_PLASMA_CHARGER_2_E_T", HorizonsBlueprintName.GUARDIAN_PLASMA_CHARGER, ModuleSize.SIZE_2, ModuleClass.E, Origin.GUARDIAN, true, Mounting.TURRETED, 1659200, "Hpt_Guardian_PlasmaLauncher_Turret_Medium", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  4.0), Map.entry(HorizonsModifier.INTEGRITY,  42.0), Map.entry(HorizonsModifier.POWER_DRAW,  2.01), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  5.0 * 2.2), Map.entry(HorizonsModifier.DAMAGE, 2.2), Map.entry(HorizonsModifier.CHARGE_TIME,  1.8), Map.entry(HorizonsModifier.DAMAGE_MULTIPLIER,  17.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  1.4), Map.entry(HorizonsModifier.THERMAL_LOAD,  5.8), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  80.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  3000.0), Map.entry(HorizonsModifier.SHOT_SPEED,  1200.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  5.0), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.2), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  15.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  200.0), Map.entry(HorizonsModifier.RELOAD_TIME,  3.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  1.0), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.50), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.80), Map.entry(HorizonsModifier.ABSOLUTE_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  1000.0), Map.entry(HorizonsModifier.AMMO_COST,  100.0), Map.entry(HorizonsModifier.ANTI_GUARDIAN_ZONE_RESISTANCE,  false)));
    public static final GuardianPlasmaCharger GUARDIAN_PLASMA_CHARGER_3_C_F = new GuardianPlasmaCharger("GUARDIAN_PLASMA_CHARGER_3_C_F", HorizonsBlueprintName.GUARDIAN_PLASMA_CHARGER, ModuleSize.SIZE_3, ModuleClass.C, Origin.GUARDIAN, false, Mounting.FIXED, 1423300, "Hpt_Guardian_PlasmaLauncher_Fixed_Large", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  8.0), Map.entry(HorizonsModifier.INTEGRITY,  51.0), Map.entry(HorizonsModifier.POWER_DRAW,  3.1), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  5.0 * 3.9), Map.entry(HorizonsModifier.DAMAGE,  3.9), Map.entry(HorizonsModifier.CHARGE_TIME,  1.8), Map.entry(HorizonsModifier.DAMAGE_MULTIPLIER,  17.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  2.42), Map.entry(HorizonsModifier.THERMAL_LOAD,  6.15), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  95.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  3000.0), Map.entry(HorizonsModifier.SHOT_SPEED,  1200.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  5.0), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.2), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  15.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  200.0), Map.entry(HorizonsModifier.RELOAD_TIME,  3.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  1.75), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.50), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.80), Map.entry(HorizonsModifier.ABSOLUTE_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  1000.0), Map.entry(HorizonsModifier.AMMO_COST,  100.0), Map.entry(HorizonsModifier.ANTI_GUARDIAN_ZONE_RESISTANCE,  false)));
    public static final GuardianPlasmaCharger GUARDIAN_PLASMA_CHARGER_3_D_T = new GuardianPlasmaCharger("GUARDIAN_PLASMA_CHARGER_3_D_T", HorizonsBlueprintName.GUARDIAN_PLASMA_CHARGER, ModuleSize.SIZE_3, ModuleClass.D, Origin.GUARDIAN, true, Mounting.TURRETED, 5495200, "Hpt_Guardian_PlasmaLauncher_Turret_Large", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  8.0), Map.entry(HorizonsModifier.INTEGRITY,  51.0), Map.entry(HorizonsModifier.POWER_DRAW,  2.53), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  5.0 * 3.3), Map.entry(HorizonsModifier.DAMAGE,  3.3), Map.entry(HorizonsModifier.CHARGE_TIME,  1.8), Map.entry(HorizonsModifier.DAMAGE_MULTIPLIER,  17.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  2.6), Map.entry(HorizonsModifier.THERMAL_LOAD,  6.4), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  95.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  3000.0), Map.entry(HorizonsModifier.SHOT_SPEED,  1200.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  5.0), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.2), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  15.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  200.0), Map.entry(HorizonsModifier.RELOAD_TIME,  3.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  1.5), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.50), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.80), Map.entry(HorizonsModifier.ABSOLUTE_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  1000.0), Map.entry(HorizonsModifier.AMMO_COST,  100.0), Map.entry(HorizonsModifier.ANTI_GUARDIAN_ZONE_RESISTANCE,  false)));
    public static final GuardianPlasmaCharger GUARDIAN_PLASMA_CHARGER_1_D_F_PRE = new GuardianPlasmaCharger("GUARDIAN_PLASMA_CHARGER_1_D_F_PRE", HorizonsBlueprintName.GUARDIAN_PLASMA_CHARGER_PRE, ModuleSize.SIZE_1, ModuleClass.D, Origin.GUARDIAN, false, Mounting.FIXED, 0, "Hpt_Guardian_PlasmaLauncher_Fixed_Small", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  34.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.4), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  5.0 * 1.65), Map.entry(HorizonsModifier.DAMAGE,  1.65), Map.entry(HorizonsModifier.CHARGE_TIME,  1.8), Map.entry(HorizonsModifier.DAMAGE_MULTIPLIER,  17.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  0.68), Map.entry(HorizonsModifier.THERMAL_LOAD,  4.21), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  65.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  3000.0), Map.entry(HorizonsModifier.SHOT_SPEED,  1200.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  5.0), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.2), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  15.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  200.0), Map.entry(HorizonsModifier.RELOAD_TIME,  3.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  0.75), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.50), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.80), Map.entry(HorizonsModifier.ABSOLUTE_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  1000.0), Map.entry(HorizonsModifier.AMMO_COST,  100.0), Map.entry(HorizonsModifier.ANTI_GUARDIAN_ZONE_RESISTANCE,  false)));
    public static final GuardianPlasmaCharger GUARDIAN_PLASMA_CHARGER_2_B_F_PRE = new GuardianPlasmaCharger("GUARDIAN_PLASMA_CHARGER_2_B_F_PRE", HorizonsBlueprintName.GUARDIAN_PLASMA_CHARGER_PRE, ModuleSize.SIZE_2, ModuleClass.B, Origin.GUARDIAN, false, Mounting.FIXED, 0, "Hpt_Guardian_PlasmaLauncher_Fixed_Medium", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  4.0), Map.entry(HorizonsModifier.INTEGRITY,  42.0), Map.entry(HorizonsModifier.POWER_DRAW,  2.13), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  5.0 * 2.75), Map.entry(HorizonsModifier.DAMAGE,  2.75), Map.entry(HorizonsModifier.CHARGE_TIME,  1.8), Map.entry(HorizonsModifier.DAMAGE_MULTIPLIER,  17.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  1.25), Map.entry(HorizonsModifier.THERMAL_LOAD,  5.21), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  80.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  3000.0), Map.entry(HorizonsModifier.SHOT_SPEED,  1200.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  5.0), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.2), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  15.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  200.0), Map.entry(HorizonsModifier.RELOAD_TIME,  3.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  1.25), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.50), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.80), Map.entry(HorizonsModifier.ABSOLUTE_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.5), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  1000.0), Map.entry(HorizonsModifier.AMMO_COST,  100.0), Map.entry(HorizonsModifier.ANTI_GUARDIAN_ZONE_RESISTANCE,  false)));
    static {
        GUARDIAN_PLASMA_CHARGER_1_D_F_PRE.getModifications().add(
                new Modification(HorizonsBlueprintType.OVERCHARGED_WEAPON_FOCUSED_WEAPON, 1.0, HorizonsBlueprintGrade.GRADE_5)
        );
        GUARDIAN_PLASMA_CHARGER_2_B_F_PRE.getModifications().add(
                new Modification(HorizonsBlueprintType.OVERCHARGED_WEAPON_FOCUSED_WEAPON, 1.0, HorizonsBlueprintGrade.GRADE_5)
        );
    }

    public static final List<GuardianPlasmaCharger> GUARDIAN_PLASMA_CHARGERS = List.of(
        GUARDIAN_PLASMA_CHARGER_1_D_F,
        GUARDIAN_PLASMA_CHARGER_1_F_T,
        GUARDIAN_PLASMA_CHARGER_2_B_F,
        GUARDIAN_PLASMA_CHARGER_2_E_T,
        GUARDIAN_PLASMA_CHARGER_3_C_F,
        GUARDIAN_PLASMA_CHARGER_3_D_T,
        GUARDIAN_PLASMA_CHARGER_1_D_F_PRE,
        GUARDIAN_PLASMA_CHARGER_2_B_F_PRE
    );
    public GuardianPlasmaCharger(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public GuardianPlasmaCharger(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public GuardianPlasmaCharger(GuardianPlasmaCharger guardianPlasmaCharger) {
        super(guardianPlasmaCharger);
    }


    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints() {
        if (isPreEngineered()) {
            return Collections.emptyList();
        }
        return GuardianPlasmaChargerBlueprints.BLUEPRINTS.keySet().stream().toList();
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedExperimentalEffects() {
        return Collections.emptyList();
    }

    @Override
    public GuardianPlasmaCharger Clone() {
        return new GuardianPlasmaCharger(this);
    }

    @Override
    public boolean isPreEngineered() {
        return GUARDIAN_PLASMA_CHARGER_1_D_F_PRE.equals(this) || GUARDIAN_PLASMA_CHARGER_2_B_F_PRE.equals(this);
    }
    @Override
    public int getGrouping() {
        return switch (getId()) {
            case "GUARDIAN_PLASMA_CHARGER_1_D_F", "GUARDIAN_PLASMA_CHARGER_1_F_T" -> 1;
            case "GUARDIAN_PLASMA_CHARGER_1_D_F_PRE" -> 2;
            case "GUARDIAN_PLASMA_CHARGER_2_B_F", "GUARDIAN_PLASMA_CHARGER_2_E_T" -> 3;
            case "GUARDIAN_PLASMA_CHARGER_2_B_F_PRE" -> 4;
            case "GUARDIAN_PLASMA_CHARGER_3_C_F", "GUARDIAN_PLASMA_CHARGER_3_D_T" -> 5;
            default -> 0;
        };
    }
}
