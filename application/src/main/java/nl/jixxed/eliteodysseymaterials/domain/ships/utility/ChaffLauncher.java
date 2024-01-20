package nl.jixxed.eliteodysseymaterials.domain.ships.utility;

import nl.jixxed.eliteodysseymaterials.constants.horizons.utilitymounts.ChaffLauncherBlueprints;
import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ChaffLauncher extends UtilityModule {
//    50090 : { mtype:'ucl',  cost:   8500, name:'Chaff Launcher',            class:0, rating:'I', mass:1.30, integ:20, pwrdraw:0.20, passive:1, boottime:0, distdraw:4.00, thmload:4.0, rof:1.0, bstint:1.00, ammoclip:1, ammomax:10, rldtime:10.0, jamdur:20, ammocost:100, fdid:128049513, fdname:'Hpt_ChaffLauncher_Tiny', eddbid:884 },
    public static final ChaffLauncher CHAFF_LAUNCHER_0_I = new ChaffLauncher("CHAFF_LAUNCHER_0_I", HorizonsBlueprintName.CHAFF_LAUNCHER, ModuleSize.SIZE_0, ModuleClass.I, true, Mounting.NA, 8500, "Hpt_ChaffLauncher_Tiny", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  20.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.2), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  4.0), Map.entry(HorizonsModifier.THERMAL_LOAD,  4.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  1.0), Map.entry(HorizonsModifier.BURST_INTERVAL,  1.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  10.0), Map.entry(HorizonsModifier.RELOAD_TIME,  10.0), Map.entry(HorizonsModifier.JAM_DURATION,  20.0), Map.entry(HorizonsModifier.AMMO_COST,  100.0)));

    public static final List<ChaffLauncher> CHAFF_LAUNCHERS = List.of(
            CHAFF_LAUNCHER_0_I
    );
    public ChaffLauncher(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public ChaffLauncher(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public ChaffLauncher(ChaffLauncher chaffLauncher) {
        super(chaffLauncher);
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints() {
        return ChaffLauncherBlueprints.BLUEPRINTS.keySet().stream().toList();
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedExperimentalEffects() {
        return Collections.emptyList();
    }

    @Override
    public ChaffLauncher Clone() {
        return new ChaffLauncher(this);
    }
    @Override
    public boolean isPassivePower(){
        return true;
    }
}
