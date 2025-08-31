package nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint;

import nl.jixxed.eliteodysseymaterials.constants.horizons.ExperimentalEffectBlueprints;
import nl.jixxed.eliteodysseymaterials.constants.horizons.SynthesisBlueprints;
import nl.jixxed.eliteodysseymaterials.constants.horizons.hardpoints.MineLauncherBlueprints;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsSynthesisBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class MineLauncher extends HardpointModule {
    public static final MineLauncher MINE_LAUNCHER_1_I_F = new MineLauncher("MINE_LAUNCHER_1_I_F", HorizonsBlueprintName.MINE_LAUNCHER, ModuleSize.SIZE_1, ModuleClass.I, false, Mounting.FIXED, 24260, "Hpt_MineLauncher_Fixed_Small",                             Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  40.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.4), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  44.0), Map.entry(HorizonsModifier.DAMAGE,  44.0), Map.entry(HorizonsModifier.THERMAL_LOAD,  5.0), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  60.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  1.0), Map.entry(HorizonsModifier.BURST_INTERVAL,  1.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  36.0), Map.entry(HorizonsModifier.RELOAD_TIME,  2.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  0.3), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, .26/.44), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, .18/.44), Map.entry(HorizonsModifier.JITTER,  0.0), Map.entry(HorizonsModifier.TARGET_THRUSTERS_REBOOT,false), Map.entry(HorizonsModifier.AREA_FSDS_REBOOT,false), Map.entry(HorizonsModifier.TARGET_SIGNATURE_INCREASED,false), Map.entry(HorizonsModifier.DAMAGE_PARTIALLY_THERMAL,false), Map.entry(HorizonsModifier.AREA_HEAT_INCREASED_SENSORS_DISRUPTED,false), Map.entry(HorizonsModifier.TARGET_SHIELD_GENERATOR_DAMAGED,false), Map.entry(HorizonsModifier.AMMO_COST,209.0)));
    public static final MineLauncher SHOCK_MINE_LAUNCHER_1_I_F = new MineLauncher("SHOCK_MINE_LAUNCHER_1_I_F", HorizonsBlueprintName.SHOCK_MINE_LAUNCHER, ModuleSize.SIZE_1, ModuleClass.I, false, Mounting.FIXED, 36390, "Hpt_MineLauncher_Fixed_Small_Impulse",   Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  40.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.4), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  32.0), Map.entry(HorizonsModifier.DAMAGE,  32.0), Map.entry(HorizonsModifier.THERMAL_LOAD,  5.0), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  60.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  1.0), Map.entry(HorizonsModifier.BURST_INTERVAL,  1.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  36.0), Map.entry(HorizonsModifier.RELOAD_TIME,  2.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  0.3), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, .20/.32), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, .12/.32), Map.entry(HorizonsModifier.JITTER,  0.0), Map.entry(HorizonsModifier.TARGET_THRUSTERS_REBOOT,false), Map.entry(HorizonsModifier.AREA_FSDS_REBOOT,false), Map.entry(HorizonsModifier.TARGET_SIGNATURE_INCREASED,false), Map.entry(HorizonsModifier.DAMAGE_PARTIALLY_THERMAL,false), Map.entry(HorizonsModifier.AREA_HEAT_INCREASED_SENSORS_DISRUPTED,false), Map.entry(HorizonsModifier.TARGET_SHIELD_GENERATOR_DAMAGED,false), Map.entry(HorizonsModifier.AMMO_COST,209.0)));
    public static final MineLauncher MINE_LAUNCHER_2_I_F = new MineLauncher("MINE_LAUNCHER_2_I_F", HorizonsBlueprintName.MINE_LAUNCHER, ModuleSize.SIZE_2, ModuleClass.I, false, Mounting.FIXED, 294080, "Hpt_MineLauncher_Fixed_Medium",                           Map.ofEntries(Map.entry(HorizonsModifier.MASS,  4.0), Map.entry(HorizonsModifier.INTEGRITY,  51.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.4), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  44.0), Map.entry(HorizonsModifier.DAMAGE,  44.0), Map.entry(HorizonsModifier.THERMAL_LOAD,  7.5), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  60.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  1.0), Map.entry(HorizonsModifier.BURST_INTERVAL,  1.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  3.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  72.0), Map.entry(HorizonsModifier.RELOAD_TIME,  6.6), Map.entry(HorizonsModifier.BREACH_DAMAGE,  0.3), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, .26/.44), Map.entry(HorizonsModifier.THERMAL_DAMAGE_RATIO, .18/.44), Map.entry(HorizonsModifier.JITTER,  0.0), Map.entry(HorizonsModifier.TARGET_THRUSTERS_REBOOT,false), Map.entry(HorizonsModifier.AREA_FSDS_REBOOT,false), Map.entry(HorizonsModifier.TARGET_SIGNATURE_INCREASED,false), Map.entry(HorizonsModifier.DAMAGE_PARTIALLY_THERMAL,false), Map.entry(HorizonsModifier.AREA_HEAT_INCREASED_SENSORS_DISRUPTED,false), Map.entry(HorizonsModifier.TARGET_SHIELD_GENERATOR_DAMAGED,false), Map.entry(HorizonsModifier.AMMO_COST,209.0)));

    public static final List<MineLauncher> MINE_LAUNCHERS = List.of(
            MINE_LAUNCHER_1_I_F,
            SHOCK_MINE_LAUNCHER_1_I_F,
            MINE_LAUNCHER_2_I_F
    );
    public MineLauncher(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public MineLauncher(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
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
            return " " + LocaleService.getLocalizedStringForCurrentLocale(getName().getLocalizationKey() + ".short");
        }
        return super.getClarifier();
    }

    @Override
    public Collection<HorizonsSynthesisBlueprint> synthesisBlueprints() {
        return SynthesisBlueprints.EXPLOSIVE_MUNITIONS.values();
    }
}
