package nl.jixxed.eliteodysseymaterials.domain.ships.utility;

import nl.jixxed.eliteodysseymaterials.constants.horizons.utilitymounts.PointDefenceBlueprints;
import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PointDefence extends UtilityModule {
//    53090 : { mtype:'upd',  cost:  18550, name:'Point Defence',  mount:'T', class:0, rating:'I', mass:0.50, integ:30, pwrdraw:0.20, passive:1, boottime:0, dps:2.0, damage:0.2, thmload:0.07, maximumrng:2500, shotspd:1000, rof:10.0, bstint:0.20, bstrof:15, bstsize:4, ammoclip:12, ammomax:10000, rldtime:0.4, jitter:0.75, kinwgt:100, ammocost:1, fdid:128049522, fdname:'Hpt_PlasmaPointDefence_Turret_Tiny', eddbid:887 },
//public static final PointDefence POINT_DEFENCE_0_I = new PointDefence("POINT_DEFENCE_0_I", HorizonsBlueprintName.POINT_DEFENCE, ModuleSize.SIZE_0, ModuleClass.I, true, Mounting.TURRETED, 18550, "Hpt_PlasmaPointDefence_Turret_Tiny", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  0.5), Map.entry(HorizonsModifier.INTEGRITY,  30.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.2), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  2.0), Map.entry(HorizonsModifier.DAMAGE,  0.2), Map.entry(HorizonsModifier.THERMAL_LOAD,  0.07), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  2500.0), Map.entry(HorizonsModifier.SHOT_SPEED,  1000.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  10.0), Map.entry(HorizonsModifier.bstint,  0.2), Map.entry(HorizonsModifier.BURST_RATE_OF_FIRE,  15.0), Map.entry(HorizonsModifier.BURST_SIZE,  4.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  12.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  10000.0), Map.entry(HorizonsModifier.RELOAD_TIME,  0.4), Map.entry(HorizonsModifier.JITTER,  0.75), Map.entry(HorizonsModifier.kinwgt,  100.0)));
    public static final PointDefence POINT_DEFENCE_0_I = new PointDefence("POINT_DEFENCE_0_I", HorizonsBlueprintName.POINT_DEFENCE, ModuleSize.SIZE_0, ModuleClass.I, true, Mounting.TURRETED, 18550, "Hpt_PlasmaPointDefence_Turret_Tiny", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  0.5), Map.entry(HorizonsModifier.INTEGRITY,  30.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.2), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  2.0), Map.entry(HorizonsModifier.DAMAGE,  0.2), Map.entry(HorizonsModifier.THERMAL_LOAD,  0.07), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  2500.0), Map.entry(HorizonsModifier.SHOT_SPEED,  1000.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  10.0), Map.entry(HorizonsModifier.BURST_INTERVAL,  0.2), Map.entry(HorizonsModifier.BURST_RATE_OF_FIRE,  15.0), Map.entry(HorizonsModifier.BURST_SIZE,  4.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  12.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  10000.0), Map.entry(HorizonsModifier.RELOAD_TIME,  0.4), Map.entry(HorizonsModifier.JITTER,  0.75), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO,  1.0)));

    public static final List<PointDefence> POINT_DEFENCES = List.of(
            POINT_DEFENCE_0_I
    );

    public PointDefence(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public PointDefence(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public PointDefence(PointDefence pointDefence) {
        super(pointDefence);
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints() {
        return PointDefenceBlueprints.BLUEPRINTS.keySet().stream().toList();
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedExperimentalEffects() {
        return Collections.emptyList();
    }

    @Override
    public PointDefence Clone() {
        return new PointDefence(this);
    }
}
