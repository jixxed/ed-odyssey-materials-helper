package nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint;

import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SeismicChargeLauncher extends HardpointModule {
    public static final SeismicChargeLauncher SEISMIC_CHARGE_LAUNCHER_2_B_F = new SeismicChargeLauncher("SEISMIC_CHARGE_LAUNCHER_2_B_F", HorizonsBlueprintName.SEISMIC_CHARGE_LAUNCHER, ModuleSize.SIZE_2, ModuleClass.B, false, Mounting.FIXED, 153110, "Hpt_Mining_SeismChrgWarhd_Fixed_Medium", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  4.0), Map.entry(HorizonsModifier.INTEGRITY,  51.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.2), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  15.0), Map.entry(HorizonsModifier.DAMAGE,  15.0), Map.entry(HorizonsModifier.CHARGE_TIME,  2.0), Map.entry(HorizonsModifier.DAMAGE_MULTIPLIER,  1.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  0.24), Map.entry(HorizonsModifier.THERMAL_LOAD,  3.6), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  35.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  1000.0), Map.entry(HorizonsModifier.SHOT_SPEED,  350.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  1.0), Map.entry(HorizonsModifier.BURST_INTERVAL,  1.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  72.0), Map.entry(HorizonsModifier.RELOAD_TIME,  1.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  3.0), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO,  1.0)));
    public static final SeismicChargeLauncher SEISMIC_CHARGE_LAUNCHER_2_B_T = new SeismicChargeLauncher("SEISMIC_CHARGE_LAUNCHER_2_B_T", HorizonsBlueprintName.SEISMIC_CHARGE_LAUNCHER, ModuleSize.SIZE_2, ModuleClass.B, true, Mounting.TURRETED, 445570, "Hpt_Mining_SeismChrgWarhd_Turret_Medium", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  4.0), Map.entry(HorizonsModifier.INTEGRITY,  51.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.2), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  15.0), Map.entry(HorizonsModifier.DAMAGE,  15.0), Map.entry(HorizonsModifier.CHARGE_TIME,  2.0), Map.entry(HorizonsModifier.DAMAGE_MULTIPLIER,  1.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  0.24), Map.entry(HorizonsModifier.THERMAL_LOAD,  3.6), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  35.0), Map.entry(HorizonsModifier.MAXIMUM_RANGE,  1000.0), Map.entry(HorizonsModifier.SHOT_SPEED,  350.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  1.0), Map.entry(HorizonsModifier.BURST_INTERVAL,  1.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  72.0), Map.entry(HorizonsModifier.RELOAD_TIME,  1.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  3.0), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  0.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  0.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO,  1.0)));
    public static final List<SeismicChargeLauncher> SEISMIC_CHARGE_LAUNCHERS = List.of(
            SEISMIC_CHARGE_LAUNCHER_2_B_F,
            SEISMIC_CHARGE_LAUNCHER_2_B_T
    );
    public SeismicChargeLauncher(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public SeismicChargeLauncher(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public SeismicChargeLauncher(SeismicChargeLauncher seismicChargeLauncher) {
        super(seismicChargeLauncher);
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
    public SeismicChargeLauncher Clone() {
        return new SeismicChargeLauncher(this);
    }
}
