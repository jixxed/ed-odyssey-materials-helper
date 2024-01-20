package nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint;

import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RemoteReleaseFlakLauncher extends HardpointModule {
    //TODO: renumber to 89xxx?

    //		      86226 : { mtype:'hex', cost:  261800, name:'Remote Release Flak Launcher',mount:'F',              class:2, rating:'B', mass: 4.00, integ:51, pwrdraw:1.20, boottime:0, dps:17.000, damage:34.000, distdraw:0.240, thmload:3.60, pierce: 60,                  shotspd: 550, rof:0.500, bstint:2.000,                      ammoclip: 1, ammomax:  32,            rldtime:2.0, brcdmg: 1.7,minbrc:100,maxbrc:100,             expwgt:100,                  ammocost:125,              noblueprints:{'*':1},                         fdid:128785626, fdname:'Hpt_FlakMortar_Fixed_Medium', eddbid:1620 },
    //            86228 : { mtype:'hex', cost: 1259200, name:'Remote Release Flak Launcher',mount:'T',              class:2, rating:'B', mass: 4.00, integ:51, pwrdraw:1.20, boottime:0, dps:17.000, damage:34.000, distdraw:0.240, thmload:3.60, pierce: 60,                  shotspd: 550, rof:0.500, bstint:2.000,                      ammoclip: 1, ammomax:  32,            rldtime:2.0, brcdmg: 1.7,minbrc:100,maxbrc:100,             expwgt:100,                  ammocost:125,              noblueprints:{'wpn_hc':1},                    fdid:128793058, fdname:'Hpt_FlakMortar_Turret_Medium', eddbid:1621 },
    public static final RemoteReleaseFlakLauncher REMOTE_RELEASE_FLAK_LAUNCHER_2_B_F = new RemoteReleaseFlakLauncher("REMOTE_RELEASE_FLAK_LAUNCHER_2_B_F", HorizonsBlueprintName.REMOTE_RELEASE_FLAK_LAUNCHER, ModuleSize.SIZE_2, ModuleClass.B, false, Mounting.FIXED, 261800, "Hpt_FlakMortar_Fixed_Medium", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.0), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.2), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 17.0), Map.entry(HorizonsModifier.DAMAGE, 34.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.24), Map.entry(HorizonsModifier.THERMAL_LOAD, 3.6), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.SHOT_SPEED, 550.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 0.5), Map.entry(HorizonsModifier.BURST_INTERVAL, 2.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 32.0), Map.entry(HorizonsModifier.RELOAD_TIME, 2.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 1.7), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.AMMO_COST, 125.0)));
    public static final RemoteReleaseFlakLauncher REMOTE_RELEASE_FLAK_LAUNCHER_2_B_T = new RemoteReleaseFlakLauncher("REMOTE_RELEASE_FLAK_LAUNCHER_2_B_T", HorizonsBlueprintName.REMOTE_RELEASE_FLAK_LAUNCHER, ModuleSize.SIZE_2, ModuleClass.B, true, Mounting.TURRETED, 1259200, "Hpt_FlakMortar_Turret_Medium", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.0), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.2), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 17.0), Map.entry(HorizonsModifier.DAMAGE, 34.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.24), Map.entry(HorizonsModifier.THERMAL_LOAD, 3.6), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.SHOT_SPEED, 550.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 0.5), Map.entry(HorizonsModifier.BURST_INTERVAL, 2.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 32.0), Map.entry(HorizonsModifier.RELOAD_TIME, 2.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 1.7), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.AMMO_COST, 125.0)));
    public static final RemoteReleaseFlakLauncher REMOTE_RELEASE_FLAK_LAUNCHER_2_B_T_PRE_GREEN = new RemoteReleaseFlakLauncher("REMOTE_RELEASE_FLAK_LAUNCHER_2_B_T_PRE", HorizonsBlueprintName.FESTIVE_LAUNCHER, ModuleSize.SIZE_2, ModuleClass.B, true, Mounting.TURRETED, 1259200, "Hpt_FlakMortar_Turret_Medium", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.0), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.2), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 17.0), Map.entry(HorizonsModifier.DAMAGE, 34.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.24), Map.entry(HorizonsModifier.THERMAL_LOAD, 3.6), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.SHOT_SPEED, 550.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 0.5), Map.entry(HorizonsModifier.BURST_INTERVAL, 2.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 32.0), Map.entry(HorizonsModifier.RELOAD_TIME, 2.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 1.7), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.AMMO_COST, 125.0)));
    public static final RemoteReleaseFlakLauncher REMOTE_RELEASE_FLAK_LAUNCHER_2_B_T_PRE_YELLOW = new RemoteReleaseFlakLauncher("REMOTE_RELEASE_FLAK_LAUNCHER_2_B_T_PRE", HorizonsBlueprintName.FESTIVE_LAUNCHER, ModuleSize.SIZE_2, ModuleClass.B, true, Mounting.TURRETED, 1259200, "Hpt_FlakMortar_Turret_Medium", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.0), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.2), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 17.0), Map.entry(HorizonsModifier.DAMAGE, 34.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.24), Map.entry(HorizonsModifier.THERMAL_LOAD, 3.6), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.SHOT_SPEED, 550.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 0.5), Map.entry(HorizonsModifier.BURST_INTERVAL, 2.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 32.0), Map.entry(HorizonsModifier.RELOAD_TIME, 2.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 1.7), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.AMMO_COST, 125.0)));
    public static final RemoteReleaseFlakLauncher REMOTE_RELEASE_FLAK_LAUNCHER_2_B_T_PRE_RED = new RemoteReleaseFlakLauncher("REMOTE_RELEASE_FLAK_LAUNCHER_2_B_T_PRE", HorizonsBlueprintName.FESTIVE_LAUNCHER, ModuleSize.SIZE_2, ModuleClass.B, true, Mounting.TURRETED, 1259200, "Hpt_FlakMortar_Turret_Medium", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.0), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.2), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 17.0), Map.entry(HorizonsModifier.DAMAGE, 34.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.24), Map.entry(HorizonsModifier.THERMAL_LOAD, 3.6), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.SHOT_SPEED, 550.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 0.5), Map.entry(HorizonsModifier.BURST_INTERVAL, 2.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 32.0), Map.entry(HorizonsModifier.RELOAD_TIME, 2.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 1.7), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.AMMO_COST, 125.0)));

    static {
        REMOTE_RELEASE_FLAK_LAUNCHER_2_B_T_PRE_GREEN.getModifications().add(
                new Modification(HorizonsBlueprintType.DECORATIVE_GREEN, 1.0, HorizonsBlueprintGrade.GRADE_5)
        );
        REMOTE_RELEASE_FLAK_LAUNCHER_2_B_T_PRE_YELLOW.getModifications().add(
                new Modification(HorizonsBlueprintType.DECORATIVE_YELLOW, 1.0, HorizonsBlueprintGrade.GRADE_5)
        );
        REMOTE_RELEASE_FLAK_LAUNCHER_2_B_T_PRE_RED.getModifications().add(
                new Modification(HorizonsBlueprintType.DECORATIVE_RED, 1.0, HorizonsBlueprintGrade.GRADE_5)
        );
    }
    public static final List<RemoteReleaseFlakLauncher> REMOTE_RELEASE_FLAK_LAUNCHERS = List.of(
        REMOTE_RELEASE_FLAK_LAUNCHER_2_B_F,
        REMOTE_RELEASE_FLAK_LAUNCHER_2_B_T,
        REMOTE_RELEASE_FLAK_LAUNCHER_2_B_T_PRE_GREEN,
        REMOTE_RELEASE_FLAK_LAUNCHER_2_B_T_PRE_YELLOW,
        REMOTE_RELEASE_FLAK_LAUNCHER_2_B_T_PRE_RED
    );

    public RemoteReleaseFlakLauncher(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public RemoteReleaseFlakLauncher(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public RemoteReleaseFlakLauncher(RemoteReleaseFlakLauncher remoteReleaseFlakLauncher) {
        super(remoteReleaseFlakLauncher);
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
    public RemoteReleaseFlakLauncher Clone() {
        return new RemoteReleaseFlakLauncher(this);
    }

    @Override
    public boolean isPreEngineered() {
        return HorizonsBlueprintName.FESTIVE_LAUNCHER.equals(this.getName());
    }
    @Override
    public String getClarifier() {
        if(isPreEngineered()){
            return " " + LocaleService.getLocalizedStringForCurrentLocale(getName().getLocalizationKey());
        }
        return super.getClarifier();
    }
    @Override
    public boolean isCGExclusive() {
        return isPreEngineered();
    }
}
