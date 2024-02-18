package nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint;

import nl.jixxed.eliteodysseymaterials.constants.horizons.ExperimentalEffectBlueprints;
import nl.jixxed.eliteodysseymaterials.constants.horizons.hardpoints.TorpedoPylonBlueprints;
import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.List;
import java.util.Map;

public class TorpedoPylon extends HardpointModule {
    public static final TorpedoPylon TORPEDO_PYLON_1_I_F = new TorpedoPylon("TORPEDO_PYLON_1_I_F", HorizonsBlueprintName.TORPEDO_PYLON, ModuleSize.SIZE_1, ModuleClass.I, false, Mounting.FIXED, 11200, "Hpt_AdvancedTorpPylon_Fixed_Small", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  40.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.4), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  120.0), Map.entry(HorizonsModifier.DAMAGE,  120.0), Map.entry(HorizonsModifier.THERMAL_LOAD,  45.0), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  10000.0), Map.entry(HorizonsModifier.SHOT_SPEED,  250.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  1.0), Map.entry(HorizonsModifier.BURST_INTERVAL,  1.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  1.0), Map.entry(HorizonsModifier.RELOAD_TIME,  5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  60.0), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  1.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  1.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO,  1.0), Map.entry(HorizonsModifier.TARGET_SHIELD_GENERATOR_DAMAGED,false), Map.entry(HorizonsModifier.TARGET_FSD_INHIBITED,false), Map.entry(HorizonsModifier.TARGET_MODULE_DAMAGE,false), Map.entry(HorizonsModifier.AMMO_COST,  15000.0)));
    public static final TorpedoPylon TORPEDO_PYLON_2_I_F = new TorpedoPylon("TORPEDO_PYLON_2_I_F", HorizonsBlueprintName.TORPEDO_PYLON, ModuleSize.SIZE_2, ModuleClass.I, false, Mounting.FIXED, 44800, "Hpt_AdvancedTorpPylon_Fixed_Medium", Map.ofEntries( Map.entry(HorizonsModifier.MASS,  4.0), Map.entry(HorizonsModifier.INTEGRITY,  51.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.4), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  120.0), Map.entry(HorizonsModifier.DAMAGE,  120.0), Map.entry(HorizonsModifier.THERMAL_LOAD,  50.0), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  10000.0), Map.entry(HorizonsModifier.SHOT_SPEED,  250.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  1.0), Map.entry(HorizonsModifier.BURST_INTERVAL,  1.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  2.0), Map.entry(HorizonsModifier.RELOAD_TIME,  5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  60.0), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  1.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  1.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO,  1.0), Map.entry(HorizonsModifier.TARGET_SHIELD_GENERATOR_DAMAGED,false), Map.entry(HorizonsModifier.TARGET_FSD_INHIBITED,false), Map.entry(HorizonsModifier.TARGET_MODULE_DAMAGE,false), Map.entry(HorizonsModifier.AMMO_COST,  15000.0)));
    public static final TorpedoPylon TORPEDO_PYLON_3_I_F = new TorpedoPylon("TORPEDO_PYLON_3_I_F", HorizonsBlueprintName.TORPEDO_PYLON, ModuleSize.SIZE_3, ModuleClass.I, false, Mounting.FIXED, 157960, "Hpt_AdvancedTorpPylon_Fixed_Large", Map.ofEntries( Map.entry(HorizonsModifier.MASS,  8.0), Map.entry(HorizonsModifier.INTEGRITY,  64.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.6), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  120.0), Map.entry(HorizonsModifier.DAMAGE,  120.0), Map.entry(HorizonsModifier.THERMAL_LOAD,  55.0), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  10000.0), Map.entry(HorizonsModifier.SHOT_SPEED,  250.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  1.0), Map.entry(HorizonsModifier.BURST_INTERVAL,  1.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  4.0), Map.entry(HorizonsModifier.RELOAD_TIME,  5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  60.0), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  1.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  1.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO,  1.0), Map.entry(HorizonsModifier.TARGET_SHIELD_GENERATOR_DAMAGED,false), Map.entry(HorizonsModifier.TARGET_FSD_INHIBITED,false), Map.entry(HorizonsModifier.TARGET_MODULE_DAMAGE,false), Map.entry(HorizonsModifier.AMMO_COST,  15000.0)));

    public static final List<TorpedoPylon> TORPEDO_PYLONS = List.of(
            TORPEDO_PYLON_1_I_F,
            TORPEDO_PYLON_2_I_F,
            TORPEDO_PYLON_3_I_F
    );
    public TorpedoPylon(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public TorpedoPylon(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public TorpedoPylon(TorpedoPylon torpedoPylon) {
        super(torpedoPylon);
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints() {
        return TorpedoPylonBlueprints.BLUEPRINTS.keySet().stream().toList();
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedExperimentalEffects() {
        return ExperimentalEffectBlueprints.TORPEDO_PYLON.keySet().stream().toList();
    }

    @Override
    public TorpedoPylon Clone() {
        return new TorpedoPylon(this);
    }
}
