package nl.jixxed.eliteodysseymaterials.domain.ships.utility;

import nl.jixxed.eliteodysseymaterials.constants.horizons.utilitymounts.HeatSinkLauncherBlueprints;
import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SinkLauncher extends UtilityModule {
    public static final SinkLauncher HEAT_SINK_LAUNCHER_0_I = new SinkLauncher("HEAT_SINK_LAUNCHER_0_I", HorizonsBlueprintName.HEAT_SINK_LAUNCHER, ModuleSize.SIZE_0, ModuleClass.I, false, Mounting.NA, 3500, "Hpt_HeatSinkLauncher_Turret_Tiny", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  45.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.2), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  2.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  0.2), Map.entry(HorizonsModifier.BURST_INTERVAL,  5.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  2.0), Map.entry(HorizonsModifier.RELOAD_TIME,  10.0), Map.entry(HorizonsModifier.HEATSINK_DURATION,  10.0), Map.entry(HorizonsModifier.THERMAL_DRAIN,  100.0), Map.entry(HorizonsModifier.AMMO_COST,  25.0)));
    public static final SinkLauncher CAUSTIC_SINK_LAUNCHER_0_I = new SinkLauncher("CAUSTIC_SINK_LAUNCHER_0_I", HorizonsBlueprintName.CAUSTIC_SINK_LAUNCHER, ModuleSize.SIZE_0, ModuleClass.I, false, Mounting.NA, 50000, "Hpt_CausticSinkLauncher_Turret_Tiny", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.7), Map.entry(HorizonsModifier.INTEGRITY,  45.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.6), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  2.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  0.2), Map.entry(HorizonsModifier.BURST_INTERVAL,  5.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  5.0), Map.entry(HorizonsModifier.RELOAD_TIME,  10.0), Map.entry(HorizonsModifier.AMMO_COST,  10.0)));
    public static final SinkLauncher HEAT_SINK_LAUNCHER_0_I_PRE = new SinkLauncher("HEAT_SINK_LAUNCHER_0_I_PRE", HorizonsBlueprintName.HEAT_SINK_LAUNCHER_PRE, ModuleSize.SIZE_0, ModuleClass.I, false, Mounting.NA, 3500, "Hpt_HeatSinkLauncher_Turret_Tiny", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  45.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.2), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  2.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  0.2), Map.entry(HorizonsModifier.BURST_INTERVAL,  5.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  2.0), Map.entry(HorizonsModifier.RELOAD_TIME,  10.0), Map.entry(HorizonsModifier.HEATSINK_DURATION,  10.0), Map.entry(HorizonsModifier.THERMAL_DRAIN,  100.0), Map.entry(HorizonsModifier.AMMO_COST,  25.0)));

    static {
        HEAT_SINK_LAUNCHER_0_I_PRE.getModifications().add(
            new Modification(HorizonsBlueprintType.AMMO_CAPACITY_X2, 1.0, HorizonsBlueprintGrade.GRADE_5)
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
        if(isPreEngineered()){
            return Collections.emptyList();
        }
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
        return " " + LocaleService.getLocalizedStringForCurrentLocale(getName().getLocalizationKey() + ".short");
    }
    @Override
    public int getGrouping() {
        return switch (getId()) {
            case "HEAT_SINK_LAUNCHER_0_I", "CAUSTIC_SINK_LAUNCHER_0_I" -> 1;
            case "HEAT_SINK_LAUNCHER_0_I_PRE" -> 2;
            default -> 0;
        };
    }
    @Override
    public boolean isPassivePower(){
        return true;
    }
}
