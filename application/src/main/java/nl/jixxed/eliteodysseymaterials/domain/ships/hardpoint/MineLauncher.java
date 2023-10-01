package nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint;

import nl.jixxed.eliteodysseymaterials.constants.horizons.ExperimentalEffectBlueprints;
import nl.jixxed.eliteodysseymaterials.constants.horizons.hardpoints.MineLauncherBlueprints;
import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.List;
import java.util.Map;

public class MineLauncher extends HardpointModule {
    //    	      80190 : { mtype:'hm',  cost:   24260, name:'Mine Launcher',               mount:'F',              class:1, rating:'I', mass: 2.00, integ:40, pwrdraw:0.40, boottime:0, dps:44.000, damage:44.000,                 thmload:5.00, pierce: 60,                            rof:1.000, bstint:1.000,                      ammoclip: 1, ammomax:  36,            rldtime:2.0,              minbrc: 0, maxbrc: 0,             expwgt:26/.44, thmwgt:18/.44, ammocost:209, fdid:128049500, fdname:'Hpt_MineLauncher_Fixed_Small', eddbid:880 },
    //            80191 : { mtype:'hm',  cost:   36390, name:'Shock Mine Launcher',         mount:'F',              class:1, rating:'I', mass: 2.00, integ:40, pwrdraw:0.40, boottime:0, dps:32.000, damage:32.000,                 thmload:5.00, pierce: 60,                            rof:1.000, bstint:1.000,                      ammoclip: 1, ammomax:  36,            rldtime:2.0, brcdmg: 9.6, minbrc: 0, maxbrc: 0,             expwgt:20/.32, thmwgt:12/.32, ammocost:209, fdid:128671448, fdname:'Hpt_MineLauncher_Fixed_Small_Impulse', eddbid:1523 },
    //            80290 : { mtype:'hm',  cost:  294080, name:'Mine Launcher',               mount:'F',              class:2, rating:'I', mass: 4.00, integ:51, pwrdraw:0.40, boottime:0, dps:44.000, damage:44.000,                 thmload:7.50, pierce: 60,                            rof:1.000, bstint:1.000,                      ammoclip: 3, ammomax:  72,            rldtime:6.6, brcdmg:13.2, minbrc: 0, maxbrc: 0,             expwgt:26/.44, thmwgt:18/.44, ammocost:209, fdid:128049501, fdname:'Hpt_MineLauncher_Fixed_Medium', eddbid:881 },
    public static final MineLauncher MINE_LAUNCHER_1_I_F = new MineLauncher("MINE_LAUNCHER_1_I_F", HorizonsBlueprintName.MINE_LAUNCHER, ModuleSize.SIZE_1, ModuleClass.I, false, Mounting.FIXED, 24260, "Hpt_MineLauncher_Fixed_Small", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  40.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.4), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  44.0), Map.entry(HorizonsModifier.DAMAGE,  44.0), Map.entry(HorizonsModifier.THERMAL_LOAD,  5.0), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  60.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  1.0), Map.entry(HorizonsModifier.BURST_INTERVAL,  1.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  36.0), Map.entry(HorizonsModifier.RELOAD_TIME,  2.0), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, .26/.44), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, .18/.44)));
    public static final MineLauncher SHOCK_MINE_LAUNCHER_1_I_F = new MineLauncher("SHOCK_MINE_LAUNCHER_1_I_F", HorizonsBlueprintName.MINE_LAUNCHER, ModuleSize.SIZE_1, ModuleClass.I, false, Mounting.FIXED, 36390, "Hpt_MineLauncher_Fixed_Small_Impulse", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  40.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.4), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  32.0), Map.entry(HorizonsModifier.DAMAGE,  32.0), Map.entry(HorizonsModifier.THERMAL_LOAD,  5.0), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  60.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  1.0), Map.entry(HorizonsModifier.BURST_INTERVAL,  1.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  36.0), Map.entry(HorizonsModifier.RELOAD_TIME,  2.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  9.6), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, .20/.32), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, .12/.32)));
    public static final MineLauncher MINE_LAUNCHER_2_I_F = new MineLauncher("MINE_LAUNCHER_2_I_F", HorizonsBlueprintName.MINE_LAUNCHER, ModuleSize.SIZE_2, ModuleClass.I, false, Mounting.FIXED, 294080, "Hpt_MineLauncher_Fixed_Medium", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  4.0), Map.entry(HorizonsModifier.INTEGRITY,  51.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.4), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  44.0), Map.entry(HorizonsModifier.DAMAGE,  44.0), Map.entry(HorizonsModifier.THERMAL_LOAD,  7.5), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  60.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  1.0), Map.entry(HorizonsModifier.BURST_INTERVAL,  1.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  3.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  72.0), Map.entry(HorizonsModifier.RELOAD_TIME,  6.6), Map.entry(HorizonsModifier.BREACH_DAMAGE,  13.2), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, .26/.44), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, .18/.44)));
    public static final List<MineLauncher> MINE_LAUNCHERS = List.of(
            MINE_LAUNCHER_1_I_F,
            SHOCK_MINE_LAUNCHER_1_I_F,
            MINE_LAUNCHER_2_I_F
    );
    public MineLauncher(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public MineLauncher(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public MineLauncher(MineLauncher mineLauncher) {
        super(mineLauncher);
    }


    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints() {
        return MineLauncherBlueprints.BLUEPRINTS.keySet().stream().toList();
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedExperimentalEffects() {
        return ExperimentalEffectBlueprints.MINE_LAUNCHER.keySet().stream().toList();
    }
    @Override
    public MineLauncher Clone() {
        return new MineLauncher(this);
    }
    @Override
    public String getClarifier() {
        if(SHOCK_MINE_LAUNCHER_1_I_F.equals(this)){
            return " SHOCK";
        }
        return super.getClarifier();
    }
}
