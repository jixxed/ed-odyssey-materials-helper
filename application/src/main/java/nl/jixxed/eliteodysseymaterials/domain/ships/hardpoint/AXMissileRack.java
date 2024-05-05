package nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint;

import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AXMissileRack extends HardpointModule {
    public static final AXMissileRack AX_MISSILE_RACK_2_E_F          = new AXMissileRack("AX_MISSILE_RACK_2_E_F"         , HorizonsBlueprintName.AX_MISSILE_RACK, ModuleSize.SIZE_2, ModuleClass.E, false, Mounting.FIXED   ,  540900, "Hpt_ATDumbfireMissile_Fixed_Medium"      , Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.0), Map.entry(HorizonsModifier.INTEGRITY,51.0), Map.entry(HorizonsModifier.POWER_DRAW,1.20), Map.entry(HorizonsModifier.BOOT_TIME,0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,15.65), Map.entry(HorizonsModifier.DAMAGE,31.3), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,0.14), Map.entry(HorizonsModifier.THERMAL_LOAD,2.4), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0),                                                      Map.entry(HorizonsModifier.SHOT_SPEED, 750.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,0.5), Map.entry(HorizonsModifier.BURST_INTERVAL,2.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 8.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  64.0), Map.entry(HorizonsModifier.RELOAD_TIME,5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.0), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,0.80), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,1.0), Map.entry(HorizonsModifier.JITTER,  0.0), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.666), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 0.334), Map.entry(HorizonsModifier.AMMO_COST,235.0)));
    public static final AXMissileRack AX_MISSILE_RACK_2_F_T          = new AXMissileRack("AX_MISSILE_RACK_2_F_T"         , HorizonsBlueprintName.AX_MISSILE_RACK, ModuleSize.SIZE_2, ModuleClass.F, true , Mounting.TURRETED, 2022700, "Hpt_ATDumbfireMissile_Turret_Medium"     , Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.0), Map.entry(HorizonsModifier.INTEGRITY,51.0), Map.entry(HorizonsModifier.POWER_DRAW,1.20), Map.entry(HorizonsModifier.BOOT_TIME,0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,11.85), Map.entry(HorizonsModifier.DAMAGE,23.7), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,0.08), Map.entry(HorizonsModifier.THERMAL_LOAD,1.5), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,5000.0), Map.entry(HorizonsModifier.SHOT_SPEED, 750.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,0.5), Map.entry(HorizonsModifier.BURST_INTERVAL,2.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 8.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  64.0), Map.entry(HorizonsModifier.RELOAD_TIME,5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.0), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,0.80), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,1.0), Map.entry(HorizonsModifier.JITTER,  0.0), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.666), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 0.334), Map.entry(HorizonsModifier.AMMO_COST,235.0)));
    public static final AXMissileRack AX_MISSILE_RACK_3_C_F          = new AXMissileRack("AX_MISSILE_RACK_3_C_F"         , HorizonsBlueprintName.AX_MISSILE_RACK, ModuleSize.SIZE_3, ModuleClass.C, false, Mounting.FIXED   , 1352250, "Hpt_ATDumbfireMissile_Fixed_Large"       , Map.ofEntries(Map.entry(HorizonsModifier.MASS, 8.0), Map.entry(HorizonsModifier.INTEGRITY,64.0), Map.entry(HorizonsModifier.POWER_DRAW,1.62), Map.entry(HorizonsModifier.BOOT_TIME,0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,15.65), Map.entry(HorizonsModifier.DAMAGE,31.3), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,0.24), Map.entry(HorizonsModifier.THERMAL_LOAD,3.6), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0),                                                      Map.entry(HorizonsModifier.SHOT_SPEED, 750.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,0.5), Map.entry(HorizonsModifier.BURST_INTERVAL,2.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,12.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 128.0), Map.entry(HorizonsModifier.RELOAD_TIME,5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.0), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,0.80), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,1.0), Map.entry(HorizonsModifier.JITTER,  0.0), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.666), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 0.334), Map.entry(HorizonsModifier.AMMO_COST,235.0)));
    public static final AXMissileRack AX_MISSILE_RACK_3_E_T          = new AXMissileRack("AX_MISSILE_RACK_3_E_T"         , HorizonsBlueprintName.AX_MISSILE_RACK, ModuleSize.SIZE_3, ModuleClass.E, true , Mounting.TURRETED, 4056750, "Hpt_ATDumbfireMissile_Turret_Large"      , Map.ofEntries(Map.entry(HorizonsModifier.MASS, 8.0), Map.entry(HorizonsModifier.INTEGRITY,64.0), Map.entry(HorizonsModifier.POWER_DRAW,1.75), Map.entry(HorizonsModifier.BOOT_TIME,0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,11.85), Map.entry(HorizonsModifier.DAMAGE,23.7), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,0.14), Map.entry(HorizonsModifier.THERMAL_LOAD,1.9), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,5000.0), Map.entry(HorizonsModifier.SHOT_SPEED, 750.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,0.5), Map.entry(HorizonsModifier.BURST_INTERVAL,2.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,12.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 128.0), Map.entry(HorizonsModifier.RELOAD_TIME,5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.0), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,0.80), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,1.0), Map.entry(HorizonsModifier.JITTER,  0.0), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.666), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 0.334), Map.entry(HorizonsModifier.AMMO_COST,235.0)));
    public static final AXMissileRack ENHANCED_AX_MISSILE_RACK_2_D_F = new AXMissileRack("ENHANCED_AX_MISSILE_RACK_2_D_F", HorizonsBlueprintName.ENHANCED_AX_MISSILE_RACK, ModuleSize.SIZE_2, ModuleClass.D, false, Mounting.FIXED   ,  681530, "Hpt_ATDumbfireMissile_Fixed_Medium_V2"   , Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.0), Map.entry(HorizonsModifier.INTEGRITY,51.0), Map.entry(HorizonsModifier.POWER_DRAW,1.30), Map.entry(HorizonsModifier.BOOT_TIME,0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,16.0), Map.entry(HorizonsModifier.DAMAGE,32.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,0.14), Map.entry(HorizonsModifier.THERMAL_LOAD,2.4), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0),                                                      Map.entry(HorizonsModifier.SHOT_SPEED,1250.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,0.5), Map.entry(HorizonsModifier.BURST_INTERVAL,2.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 8.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  64.0), Map.entry(HorizonsModifier.RELOAD_TIME,5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.0), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,0.80), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,1.0), Map.entry(HorizonsModifier.JITTER,  0.0), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.666), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 0.334), Map.entry(HorizonsModifier.AMMO_COST,235.0)));
    public static final AXMissileRack ENHANCED_AX_MISSILE_RACK_2_E_T = new AXMissileRack("ENHANCED_AX_MISSILE_RACK_2_E_T", HorizonsBlueprintName.ENHANCED_AX_MISSILE_RACK, ModuleSize.SIZE_2, ModuleClass.E, true , Mounting.TURRETED, 2666290, "Hpt_ATDumbfireMissile_Turret_Medium_V2"  , Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.0), Map.entry(HorizonsModifier.INTEGRITY,51.0), Map.entry(HorizonsModifier.POWER_DRAW,1.30), Map.entry(HorizonsModifier.BOOT_TIME,0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,12.2), Map.entry(HorizonsModifier.DAMAGE,24.4), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,0.08), Map.entry(HorizonsModifier.THERMAL_LOAD,1.5), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,5000.0), Map.entry(HorizonsModifier.SHOT_SPEED,1250.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,0.5), Map.entry(HorizonsModifier.BURST_INTERVAL,2.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 8.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  64.0), Map.entry(HorizonsModifier.RELOAD_TIME,5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.0), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,0.80), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,1.0), Map.entry(HorizonsModifier.JITTER,  0.0), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.666), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 0.334), Map.entry(HorizonsModifier.AMMO_COST,235.0)));
    public static final AXMissileRack ENHANCED_AX_MISSILE_RACK_3_B_F = new AXMissileRack("ENHANCED_AX_MISSILE_RACK_3_B_F", HorizonsBlueprintName.ENHANCED_AX_MISSILE_RACK, ModuleSize.SIZE_3, ModuleClass.B, false, Mounting.FIXED   , 1703830, "Hpt_ATDumbfireMissile_Fixed_Large_V2"    , Map.ofEntries(Map.entry(HorizonsModifier.MASS, 8.0), Map.entry(HorizonsModifier.INTEGRITY,64.0), Map.entry(HorizonsModifier.POWER_DRAW,1.72), Map.entry(HorizonsModifier.BOOT_TIME,0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,16.0), Map.entry(HorizonsModifier.DAMAGE,32.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,0.24), Map.entry(HorizonsModifier.THERMAL_LOAD,3.6), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0),                                                      Map.entry(HorizonsModifier.SHOT_SPEED,1250.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,0.5), Map.entry(HorizonsModifier.BURST_INTERVAL,2.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,12.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 128.0), Map.entry(HorizonsModifier.RELOAD_TIME,5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.0), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,0.80), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,1.0), Map.entry(HorizonsModifier.JITTER,  0.0), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.666), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 0.334), Map.entry(HorizonsModifier.AMMO_COST,235.0)));
    public static final AXMissileRack ENHANCED_AX_MISSILE_RACK_3_D_T = new AXMissileRack("ENHANCED_AX_MISSILE_RACK_3_D_T", HorizonsBlueprintName.ENHANCED_AX_MISSILE_RACK, ModuleSize.SIZE_3, ModuleClass.D, true , Mounting.TURRETED, 5347530, "Hpt_ATDumbfireMissile_Turret_Large_V2"   , Map.ofEntries(Map.entry(HorizonsModifier.MASS, 8.0), Map.entry(HorizonsModifier.INTEGRITY,64.0), Map.entry(HorizonsModifier.POWER_DRAW,1.85), Map.entry(HorizonsModifier.BOOT_TIME,0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,12.2), Map.entry(HorizonsModifier.DAMAGE,24.4), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,0.14), Map.entry(HorizonsModifier.THERMAL_LOAD,1.9), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,5000.0), Map.entry(HorizonsModifier.SHOT_SPEED,1250.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,0.5), Map.entry(HorizonsModifier.BURST_INTERVAL,2.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,12.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 128.0), Map.entry(HorizonsModifier.RELOAD_TIME,5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.0), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,0.80), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,1.0), Map.entry(HorizonsModifier.JITTER,  0.0), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.666), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 0.334), Map.entry(HorizonsModifier.AMMO_COST,235.0)));
    public static final AXMissileRack AX_MISSILE_RACK_2_E_F_PRE      = new AXMissileRack("AX_MISSILE_RACK_2_E_F_PRE"     , HorizonsBlueprintName.AX_MISSILE_RACK_PRE, ModuleSize.SIZE_2, ModuleClass.E, Origin.SIRIUS,false, Mounting.FIXED   , 0, "Hpt_ATDumbfireMissile_Fixed_Medium"      , Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.0), Map.entry(HorizonsModifier.INTEGRITY,51.0), Map.entry(HorizonsModifier.POWER_DRAW,1.20), Map.entry(HorizonsModifier.BOOT_TIME,0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,15.65), Map.entry(HorizonsModifier.DAMAGE,31.3), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,0.14), Map.entry(HorizonsModifier.THERMAL_LOAD,2.4), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0),                                                      Map.entry(HorizonsModifier.SHOT_SPEED, 750.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,0.5), Map.entry(HorizonsModifier.BURST_INTERVAL,2.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 8.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  64.0), Map.entry(HorizonsModifier.RELOAD_TIME,5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.0), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,0.80), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,1.0), Map.entry(HorizonsModifier.JITTER,  0.0), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.666), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 0.334), Map.entry(HorizonsModifier.AMMO_COST,235.0)));
    public static final AXMissileRack AX_MISSILE_RACK_3_C_F_PRE      = new AXMissileRack("AX_MISSILE_RACK_3_C_F_PRE"     , HorizonsBlueprintName.AX_MISSILE_RACK_PRE, ModuleSize.SIZE_3, ModuleClass.C, Origin.SIRIUS,false, Mounting.FIXED   , 0, "Hpt_ATDumbfireMissile_Fixed_Large"       , Map.ofEntries(Map.entry(HorizonsModifier.MASS, 8.0), Map.entry(HorizonsModifier.INTEGRITY,64.0), Map.entry(HorizonsModifier.POWER_DRAW,1.62), Map.entry(HorizonsModifier.BOOT_TIME,0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,15.65), Map.entry(HorizonsModifier.DAMAGE,31.3), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,0.24), Map.entry(HorizonsModifier.THERMAL_LOAD,3.6), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0),                                                      Map.entry(HorizonsModifier.SHOT_SPEED, 750.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,0.5), Map.entry(HorizonsModifier.BURST_INTERVAL,2.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,12.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 128.0), Map.entry(HorizonsModifier.RELOAD_TIME,5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 0.0), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,0.80), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,1.0), Map.entry(HorizonsModifier.JITTER,  0.0), Map.entry(HorizonsModifier.ANTI_XENO_DAMAGE_RATIO, 0.666), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 0.334), Map.entry(HorizonsModifier.AMMO_COST,235.0)));

    static {
        AX_MISSILE_RACK_2_E_F_PRE.getModifications().add(
            new Modification(HorizonsBlueprintType.HIGH_CAPACITY_MAGAZINE_RAPID_FIRE_MODIFICATION, 1.0, HorizonsBlueprintGrade.GRADE_5)
        );
        AX_MISSILE_RACK_3_C_F_PRE.getModifications().add(
            new Modification(HorizonsBlueprintType.HIGH_CAPACITY_MAGAZINE_RAPID_FIRE_MODIFICATION, 1.0, HorizonsBlueprintGrade.GRADE_5)
        );
    }
    public static final List<AXMissileRack> AX_MISSILE_RACKS = List.of(
            AX_MISSILE_RACK_2_E_F,
            AX_MISSILE_RACK_2_F_T,
            AX_MISSILE_RACK_3_C_F,
            AX_MISSILE_RACK_3_E_T,
            ENHANCED_AX_MISSILE_RACK_2_D_F,
            ENHANCED_AX_MISSILE_RACK_2_E_T,
            ENHANCED_AX_MISSILE_RACK_3_B_F,
            ENHANCED_AX_MISSILE_RACK_3_D_T,
            AX_MISSILE_RACK_2_E_F_PRE,
            AX_MISSILE_RACK_3_C_F_PRE
    );

    public AXMissileRack(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public AXMissileRack(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public AXMissileRack(AXMissileRack axMissileRack) {
        super(axMissileRack);
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
    public AXMissileRack Clone() {
        return new AXMissileRack(this);
    }

    @Override
    public boolean isPreEngineered() {
        return AX_MISSILE_RACK_2_E_F_PRE.equals(this) || AX_MISSILE_RACK_3_C_F_PRE.equals(this);
    }
    @Override
    public boolean isEnhanced() {
        return ENHANCED_AX_MISSILE_RACK_2_D_F.equals(this) || ENHANCED_AX_MISSILE_RACK_2_E_T.equals(this) || ENHANCED_AX_MISSILE_RACK_3_B_F.equals(this) || ENHANCED_AX_MISSILE_RACK_3_D_T.equals(this);
    }

    @Override
    public int getGrouping() {
        return switch (getId()) {
            case "AX_MISSILE_RACK_2_E_F", "AX_MISSILE_RACK_2_F_T", "AX_MISSILE_RACK_2_E_F_PRE" -> 1;
            case "ENHANCED_AX_MISSILE_RACK_2_D_F", "ENHANCED_AX_MISSILE_RACK_2_E_T" -> 2;
            case "AX_MISSILE_RACK_3_C_F", "AX_MISSILE_RACK_3_E_T", "AX_MISSILE_RACK_3_C_F_PRE" -> 4;
            case "ENHANCED_AX_MISSILE_RACK_3_B_F", "ENHANCED_AX_MISSILE_RACK_3_D_T" -> 5;
            default -> 0;
        };
    }
}
