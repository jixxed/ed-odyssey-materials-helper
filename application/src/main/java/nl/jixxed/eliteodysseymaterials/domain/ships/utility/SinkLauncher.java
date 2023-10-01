package nl.jixxed.eliteodysseymaterials.domain.ships.utility;

import nl.jixxed.eliteodysseymaterials.constants.horizons.utilitymounts.HeatSinkLauncherBlueprints;
import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SinkLauncher extends UtilityModule {
//    	52090 : { mtype:'uhsl', cost:   3500, name:'Heat Sink Launcher',        class:0, rating:'I', mass:1.30, integ:45, pwrdraw:0.20, passive:1, boottime:0, distdraw:2.00, rof:0.2, bstint:5.00, ammoclip:1, ammomax:2, rldtime:10.0, hsdur:10, thmdrain:100.0, ammocost:25, fdid:128049519, fdname:'Hpt_HeatSinkLauncher_Turret_Tiny', eddbid:886 },
//            52091 : { mtype:'uhsl', cost:  50000, name:'Caustic Sink Launcher',     class:0, rating:'I', mass:1.70, integ:45, pwrdraw:0.60, passive:1, boottime:0, distdraw:2.00, rof:0.2, bstint:5.00, ammoclip:1, ammomax:5, rldtime:10.0,                           ammocost:10, fdid:129019262, fdname:'Hpt_CausticSinkLauncher_Turret_Tiny', eddbid:null }, // Rescue Ship tech broker // verify: cost // TODO: eddbid
    public static final SinkLauncher HEAT_SINK_LAUNCHER_0_I = new SinkLauncher("HEAT_SINK_LAUNCHER_0_I", HorizonsBlueprintName.HEAT_SINK_LAUNCHER, ModuleSize.SIZE_0, ModuleClass.I, false, Mounting.NA, 3500, "Hpt_HeatSinkLauncher_Turret_Tiny", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  45.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.2), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  2.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  0.2), Map.entry(HorizonsModifier.BURST_INTERVAL,  5.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  2.0), Map.entry(HorizonsModifier.RELOAD_TIME,  10.0), Map.entry(HorizonsModifier.HEATSINK_DURATION,  10.0), Map.entry(HorizonsModifier.THERMAL_DRAIN,  100.0)));
    public static final SinkLauncher CAUSTIC_SINK_LAUNCHER_0_I = new SinkLauncher("CAUSTIC_SINK_LAUNCHER_0_I", HorizonsBlueprintName.CAUSTIC_SINK_LAUNCHER, ModuleSize.SIZE_0, ModuleClass.I, false, Mounting.NA, 50000, "Hpt_CausticSinkLauncher_Turret_Tiny", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.7), Map.entry(HorizonsModifier.INTEGRITY,  45.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.6), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  2.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  0.2), Map.entry(HorizonsModifier.BURST_INTERVAL,  5.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  5.0), Map.entry(HorizonsModifier.RELOAD_TIME,  10.0)));
    public static final SinkLauncher HEAT_SINK_LAUNCHER_0_I_PRE = new SinkLauncher("HEAT_SINK_LAUNCHER_0_I_PRE", HorizonsBlueprintName.HEAT_SINK_LAUNCHER, ModuleSize.SIZE_0, ModuleClass.I, false, Mounting.NA, 3500, "Hpt_HeatSinkLauncher_Turret_Tiny", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  45.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.2), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  2.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  0.2), Map.entry(HorizonsModifier.BURST_INTERVAL,  5.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  2.0), Map.entry(HorizonsModifier.RELOAD_TIME,  10.0), Map.entry(HorizonsModifier.HEATSINK_DURATION,  10.0), Map.entry(HorizonsModifier.THERMAL_DRAIN,  100.0)));

    static {
        HEAT_SINK_LAUNCHER_0_I_PRE.getModifications().addAll(
                List.of(
                        new Modification(HorizonsBlueprintType.AMMO_CAPACITY, 1.0, HorizonsBlueprintGrade.GRADE_5),
                        new Modification(HorizonsBlueprintType.AMMO_CAPACITY, 1.0, HorizonsBlueprintGrade.GRADE_5)
                )
        );
    }
    public static final List<SinkLauncher> SINK_LAUNCHERS = List.of(
            HEAT_SINK_LAUNCHER_0_I,
            CAUSTIC_SINK_LAUNCHER_0_I,
            HEAT_SINK_LAUNCHER_0_I_PRE
    );
    public SinkLauncher(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public SinkLauncher(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public SinkLauncher(SinkLauncher sinkLauncher) {
        super(sinkLauncher);
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints() {
        return HeatSinkLauncherBlueprints.BLUEPRINTS.keySet().stream().toList();
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedExperimentalEffects() {
        return Collections.emptyList();
    }

    @Override
    public SinkLauncher Clone() {
        return new SinkLauncher(this);
    }

    @Override
    public boolean isPreEngineered() {
        return HEAT_SINK_LAUNCHER_0_I_PRE.equals(this);
    }

    @Override
    public String getNonSortingClarifier() {
        if(this.getName().equals(HorizonsBlueprintName.HEAT_SINK_LAUNCHER)){
            return " Heat";
        }
        if(this.getName().equals(HorizonsBlueprintName.CAUSTIC_SINK_LAUNCHER)){
            return " Caustic";
        }
        return super.getNonSortingClarifier();
    }
}
