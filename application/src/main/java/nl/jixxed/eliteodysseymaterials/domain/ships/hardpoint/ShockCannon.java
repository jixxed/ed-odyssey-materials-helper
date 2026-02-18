package nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint;

import nl.jixxed.eliteodysseymaterials.constants.horizons.SynthesisBlueprints;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsSynthesisBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ShockCannon extends HardpointModule {
    public static final ShockCannon SHOCK_CANNON_1_D_F = new ShockCannon("SHOCK_CANNON_1_D_F", HorizonsBlueprintName.SHOCK_CANNON, ModuleSize.SIZE_1, ModuleClass.D, Origin.TECHBROKER,false, Mounting.FIXED, 65940, "Hpt_PlasmaShockCannon_Fixed_Small", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  40.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.41), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  86.4), Map.entry(HorizonsModifier.DAMAGE,  8.64), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  0.27), Map.entry(HorizonsModifier.THERMAL_LOAD,  1.14), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  25.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  3000.0), Map.entry(HorizonsModifier.SHOT_SPEED,  1200.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  10.0), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.1), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  16.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  240.0), Map.entry(HorizonsModifier.RELOAD_TIME,  6.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  0.7), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.40), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.60), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO,  1.0), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  2500.0), Map.entry(HorizonsModifier.AMMO_COST,  9.0)));
    public static final ShockCannon SHOCK_CANNON_1_E_G = new ShockCannon("SHOCK_CANNON_1_E_G", HorizonsBlueprintName.SHOCK_CANNON, ModuleSize.SIZE_1, ModuleClass.E, Origin.TECHBROKER,false, Mounting.GIMBALLED, 137500, "Hpt_PlasmaShockCannon_Gimbal_Small", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  40.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.47), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  69.1), Map.entry(HorizonsModifier.DAMAGE,  6.91), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  0.39), Map.entry(HorizonsModifier.THERMAL_LOAD,  1.45), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  25.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  3000.0), Map.entry(HorizonsModifier.SHOT_SPEED,  1200.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  10.0), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.1), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  16.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  240.0), Map.entry(HorizonsModifier.RELOAD_TIME,  6.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  0.7), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.40), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.80), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO,  1.0), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  2500.0), Map.entry(HorizonsModifier.AMMO_COST,  9.0)));
    public static final ShockCannon SHOCK_CANNON_1_F_T = new ShockCannon("SHOCK_CANNON_1_F_T", HorizonsBlueprintName.SHOCK_CANNON, ModuleSize.SIZE_1, ModuleClass.F, Origin.TECHBROKER,true, Mounting.TURRETED, 364000, "Hpt_PlasmaShockCannon_Turret_Small", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  40.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.54), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  44.7), Map.entry(HorizonsModifier.DAMAGE,  4.47), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  0.21), Map.entry(HorizonsModifier.THERMAL_LOAD,  0.69), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  25.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  3000.0), Map.entry(HorizonsModifier.SHOT_SPEED,  1200.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  10.0), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.1), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  16.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  240.0), Map.entry(HorizonsModifier.RELOAD_TIME,  6.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  0.7), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.40), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.80), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO,  1.0), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  2500.0), Map.entry(HorizonsModifier.AMMO_COST,  9.0)));
    public static final ShockCannon SHOCK_CANNON_2_D_F = new ShockCannon("SHOCK_CANNON_2_D_F", HorizonsBlueprintName.SHOCK_CANNON, ModuleSize.SIZE_2, ModuleClass.D, Origin.TECHBROKER,false, Mounting.FIXED, 367500, "Hpt_PlasmaShockCannon_Fixed_Medium", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  4.0), Map.entry(HorizonsModifier.INTEGRITY,  51.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.57), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  129.6), Map.entry(HorizonsModifier.DAMAGE,  12.96), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  0.47), Map.entry(HorizonsModifier.THERMAL_LOAD,  1.8), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  40.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  3000.0), Map.entry(HorizonsModifier.SHOT_SPEED,  1200.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  10.0), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.1), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  16.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  240.0), Map.entry(HorizonsModifier.RELOAD_TIME,  6.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  0.7), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.40), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.60), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO,  1.0), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  2500.0), Map.entry(HorizonsModifier.AMMO_COST,  9.0)));
    public static final ShockCannon SHOCK_CANNON_2_D_G = new ShockCannon("SHOCK_CANNON_2_D_G", HorizonsBlueprintName.SHOCK_CANNON, ModuleSize.SIZE_2, ModuleClass.D, Origin.TECHBROKER,false, Mounting.GIMBALLED, 565200, "Hpt_PlasmaShockCannon_Gimbal_Medium", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  4.0), Map.entry(HorizonsModifier.INTEGRITY,  51.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.61), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  102.1), Map.entry(HorizonsModifier.DAMAGE,  10.21), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  0.58), Map.entry(HorizonsModifier.THERMAL_LOAD,  2.1), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  40.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  3000.0), Map.entry(HorizonsModifier.SHOT_SPEED,  1200.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  10.0), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.1), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  16.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  240.0), Map.entry(HorizonsModifier.RELOAD_TIME,  6.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  0.7), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.40), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.80), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO,  1.0), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  2500.0), Map.entry(HorizonsModifier.AMMO_COST,  9.0)));
    public static final ShockCannon SHOCK_CANNON_2_E_T = new ShockCannon("SHOCK_CANNON_2_E_T", HorizonsBlueprintName.SHOCK_CANNON, ModuleSize.SIZE_2, ModuleClass.E, Origin.TECHBROKER,true, Mounting.TURRETED, 1359200, "Hpt_PlasmaShockCannon_Turret_Medium", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  4.0), Map.entry(HorizonsModifier.INTEGRITY,  51.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.5), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  89.6), Map.entry(HorizonsModifier.DAMAGE,  8.96), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  0.39), Map.entry(HorizonsModifier.THERMAL_LOAD,  1.24), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  40.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  3000.0), Map.entry(HorizonsModifier.SHOT_SPEED,  1200.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  10.0), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.1), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  16.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  240.0), Map.entry(HorizonsModifier.RELOAD_TIME,  6.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  0.7), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.40), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.80), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO,  1.0), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  2500.0), Map.entry(HorizonsModifier.AMMO_COST,  9.0)));
    public static final ShockCannon SHOCK_CANNON_3_C_F = new ShockCannon("SHOCK_CANNON_3_C_F", HorizonsBlueprintName.SHOCK_CANNON, ModuleSize.SIZE_3, ModuleClass.C, Origin.TECHBROKER,false, Mounting.FIXED, 1015750, "Hpt_PlasmaShockCannon_Fixed_Large", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  8.0), Map.entry(HorizonsModifier.INTEGRITY,  64.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.89), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  181.4), Map.entry(HorizonsModifier.DAMAGE,  18.14), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  0.92), Map.entry(HorizonsModifier.THERMAL_LOAD,  2.66), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  60.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  3000.0), Map.entry(HorizonsModifier.SHOT_SPEED,  1200.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  10.0), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.1), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  16.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  240.0), Map.entry(HorizonsModifier.RELOAD_TIME,  6.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  0.7), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.40), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.60), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO,  1.0), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  2500.0), Map.entry(HorizonsModifier.AMMO_COST,  9.0)));
    public static final ShockCannon SHOCK_CANNON_3_C_G = new ShockCannon("SHOCK_CANNON_3_C_G", HorizonsBlueprintName.SHOCK_CANNON, ModuleSize.SIZE_3, ModuleClass.C, Origin.TECHBROKER,false, Mounting.GIMBALLED, 2249050, "Hpt_PlasmaShockCannon_Gimbal_Large", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  8.0), Map.entry(HorizonsModifier.INTEGRITY,  64.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.89), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  148.7), Map.entry(HorizonsModifier.DAMAGE,  14.87), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  1.07), Map.entry(HorizonsModifier.THERMAL_LOAD,  3.12), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  60.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  3000.0), Map.entry(HorizonsModifier.SHOT_SPEED,  1200.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  10.0), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.1), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  16.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  240.0), Map.entry(HorizonsModifier.RELOAD_TIME,  6.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  0.7), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.40), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.80), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO,  1.0), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  2500.0), Map.entry(HorizonsModifier.AMMO_COST,  9.0)));
    public static final ShockCannon SHOCK_CANNON_3_D_T = new ShockCannon("SHOCK_CANNON_3_D_T", HorizonsBlueprintName.SHOCK_CANNON, ModuleSize.SIZE_3, ModuleClass.D, Origin.TECHBROKER,true, Mounting.TURRETED, 6050200, "Hpt_PlasmaShockCannon_Turret_Large", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  8.0), Map.entry(HorizonsModifier.INTEGRITY,  64.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.64), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  122.6), Map.entry(HorizonsModifier.DAMAGE,  12.26), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  0.79), Map.entry(HorizonsModifier.THERMAL_LOAD,  2.2), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  60.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  3000.0), Map.entry(HorizonsModifier.SHOT_SPEED,  1200.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  10.0), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.1), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  16.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  240.0), Map.entry(HorizonsModifier.RELOAD_TIME,  6.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  0.7), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.40), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.80), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO,  1.0), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  2500.0), Map.entry(HorizonsModifier.AMMO_COST,  9.0)));
    public static final ShockCannon SHOCK_CANNON_3_C_F_MK_II = new ShockCannon("SHOCK_CANNON_3_C_F_MK_II", HorizonsBlueprintName.SHOCK_CANNON_MK_II_PLASMA, ModuleSize.SIZE_3, ModuleClass.D,true, Mounting.FIXED, 0, "hpt_mkiiplasmashockautocannon_fixed_large", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  8.0), Map.entry(HorizonsModifier.INTEGRITY,  64.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.51), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  109.3), Map.entry(HorizonsModifier.DAMAGE,  13.94), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  0.92), Map.entry(HorizonsModifier.THERMAL_LOAD,  1.7), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  60.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  3000.0), Map.entry(HorizonsModifier.SHOT_SPEED,  1200.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  7.84), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.352), Map.entry(HorizonsModifier.BURST_RATE_OF_FIRE,28.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  18.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  Double.POSITIVE_INFINITY), Map.entry(HorizonsModifier.RELOAD_TIME,  5.0), Map.entry(HorizonsModifier.ROUNDS_PER_SHOT, 1.0), Map.entry(HorizonsModifier.BURST_SIZE, 4.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  0.7), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.40), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.60), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO,  1.0), Map.entry(HorizonsModifier.DAMAGE_FALLOFF_START,  2500.0)));

    public static final List<ShockCannon> SHOCK_CANNONS = List.of(
            SHOCK_CANNON_1_D_F,
            SHOCK_CANNON_1_E_G,
            SHOCK_CANNON_1_F_T,
            SHOCK_CANNON_2_D_F,
            SHOCK_CANNON_2_D_G,
            SHOCK_CANNON_2_E_T,
            SHOCK_CANNON_3_C_F,
            SHOCK_CANNON_3_C_G,
            SHOCK_CANNON_3_D_T,
            SHOCK_CANNON_3_C_F_MK_II
    );

    public ShockCannon(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public ShockCannon(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public ShockCannon(ShockCannon shockCannon) {
        super(shockCannon);
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
    public ShockCannon Clone() {
        return new ShockCannon(this);
    }

    @Override
    public Collection<HorizonsSynthesisBlueprint> synthesisBlueprints() {
        return SynthesisBlueprints.SHOCK_CANNON_MUNITIONS.values();
    }

    @Override
    public int getGrouping() {
        return getModuleSize().intValue() * 10
                + (this.equals(SHOCK_CANNON_3_C_F_MK_II) ? 1 : 0);
    }
    @Override
    public String getClarifier() {
        if(this.equals(SHOCK_CANNON_3_C_F_MK_II)){
            return " " + LocaleService.getLocalizedStringForCurrentLocale(this.getLocalizationKey());
        }
        else{
            return "";
        }
    }

    @Override
    public boolean isAllowed(ShipType shipType) {
        if(SHOCK_CANNON_3_C_F_MK_II.equals(this)) {
            return shipType.equals(ShipType.KESTREL_MK_II);
        }
        return true;
    }
}
