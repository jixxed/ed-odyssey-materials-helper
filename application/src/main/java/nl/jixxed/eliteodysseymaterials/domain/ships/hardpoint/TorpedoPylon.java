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
    //    		  85193 : { mtype:'htp', cost:   11200, name:'Torpedo Pylon',               mount:'F', missile:'S', class:1, rating:'I', mass: 2.00, integ:40, pwrdraw:0.40, boottime:0,dps:120.000,damage:120.000,                thmload:45.00, pierce:1e4,              shotspd: 250, rof:1.000, bstint:1.000,                      ammoclip: 1,                          rldtime:5.0, brcdmg:60.0, minbrc:100,maxbrc:100,            expwgt:100, ammocost:15000, fdid:128049509, fdname:'Hpt_AdvancedTorpPylon_Fixed_Small', eddbid:882 },
    //            85293 : { mtype:'htp', cost:   44800, name:'Torpedo Pylon',               mount:'F', missile:'S', class:2, rating:'I', mass: 4.00, integ:51, pwrdraw:0.40, boottime:0,dps:120.000,damage:120.000,                thmload:50.00, pierce:1e4,              shotspd: 250, rof:1.000, bstint:1.000,                      ammoclip: 2,                          rldtime:5.0, brcdmg:60.0, minbrc:100,maxbrc:100,            expwgt:100, ammocost:15000, fdid:128049510, fdname:'Hpt_AdvancedTorpPylon_Fixed_Medium', eddbid:883 },
    //            85393 : { mtype:'htp', cost:  157960, name:'Torpedo Pylon',               mount:'F', missile:'S', class:3, rating:'I', mass: 8.00, integ:64, pwrdraw:0.60, boottime:0,dps:120.000,damage:120.000,                thmload:55.00, pierce:1e4,              shotspd: 250, rof:1.000, bstint:1.000,                      ammoclip: 4,                          rldtime:5.0, brcdmg:60.0, minbrc:100,maxbrc:100,            expwgt:100, ammocost:15000, fdid:128049511, fdname:'Hpt_AdvancedTorpPylon_Fixed_Large', eddbid:1655 },
    public static final TorpedoPylon TORPEDO_PYLON_1_I_F = new TorpedoPylon("TORPEDO_PYLON_1_I_F", HorizonsBlueprintName.TORPEDO_PYLON, ModuleSize.SIZE_1, ModuleClass.I, false, Mounting.FIXED, 11200, "Hpt_AdvancedTorpPylon_Fixed_Small", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  40.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.4), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  120.0), Map.entry(HorizonsModifier.DAMAGE,  120.0), Map.entry(HorizonsModifier.THERMAL_LOAD,  45.0), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  10000.0), Map.entry(HorizonsModifier.SHOT_SPEED,  250.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  1.0), Map.entry(HorizonsModifier.BURST_INTERVAL,  1.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  1.0), Map.entry(HorizonsModifier.RELOAD_TIME,  5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  60.0), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  1.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  1.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO,  1.0), Map.entry(HorizonsModifier.TARGET_SHIELD_GENERATOR_DAMAGED,false), Map.entry(HorizonsModifier.TARGET_FSD_INHIBITED,false), Map.entry(HorizonsModifier.TARGET_MODULE_DAMAGE,false)));
    public static final TorpedoPylon TORPEDO_PYLON_2_I_F = new TorpedoPylon("TORPEDO_PYLON_2_I_F", HorizonsBlueprintName.TORPEDO_PYLON, ModuleSize.SIZE_2, ModuleClass.I, false, Mounting.FIXED, 44800, "Hpt_AdvancedTorpPylon_Fixed_Medium", Map.ofEntries( Map.entry(HorizonsModifier.MASS,  4.0), Map.entry(HorizonsModifier.INTEGRITY,  51.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.4), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  120.0), Map.entry(HorizonsModifier.DAMAGE,  120.0), Map.entry(HorizonsModifier.THERMAL_LOAD,  50.0), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  10000.0), Map.entry(HorizonsModifier.SHOT_SPEED,  250.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  1.0), Map.entry(HorizonsModifier.BURST_INTERVAL,  1.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  2.0), Map.entry(HorizonsModifier.RELOAD_TIME,  5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  60.0), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  1.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  1.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO,  1.0), Map.entry(HorizonsModifier.TARGET_SHIELD_GENERATOR_DAMAGED,false), Map.entry(HorizonsModifier.TARGET_FSD_INHIBITED,false), Map.entry(HorizonsModifier.TARGET_MODULE_DAMAGE,false)));
    public static final TorpedoPylon TORPEDO_PYLON_3_I_F = new TorpedoPylon("TORPEDO_PYLON_3_I_F", HorizonsBlueprintName.TORPEDO_PYLON, ModuleSize.SIZE_3, ModuleClass.I, false, Mounting.FIXED, 157960, "Hpt_AdvancedTorpPylon_Fixed_Large", Map.ofEntries( Map.entry(HorizonsModifier.MASS,  8.0), Map.entry(HorizonsModifier.INTEGRITY,  64.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.6), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  120.0), Map.entry(HorizonsModifier.DAMAGE,  120.0), Map.entry(HorizonsModifier.THERMAL_LOAD,  55.0), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  10000.0), Map.entry(HorizonsModifier.SHOT_SPEED,  250.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  1.0), Map.entry(HorizonsModifier.BURST_INTERVAL,  1.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  4.0), Map.entry(HorizonsModifier.RELOAD_TIME,  5.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  60.0), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  1.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  1.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO,  1.0), Map.entry(HorizonsModifier.TARGET_SHIELD_GENERATOR_DAMAGED,false), Map.entry(HorizonsModifier.TARGET_FSD_INHIBITED,false), Map.entry(HorizonsModifier.TARGET_MODULE_DAMAGE,false)));

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
