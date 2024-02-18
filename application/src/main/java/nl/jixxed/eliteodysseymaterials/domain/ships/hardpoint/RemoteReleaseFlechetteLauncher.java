package nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint;

import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RemoteReleaseFlechetteLauncher extends HardpointModule {
    public static final RemoteReleaseFlechetteLauncher REMOTE_RELEASE_FLECHETTE_LAUNCHER_2_B_F = new RemoteReleaseFlechetteLauncher("REMOTE_RELEASE_FLECHETTE_LAUNCHER_2_B_F", HorizonsBlueprintName.REMOTE_RELEASE_FLECHETTE_LAUNCHER, ModuleSize.SIZE_2, ModuleClass.B, false, Mounting.FIXED, 353760, "Hpt_FlechetteLauncher_Fixed_Medium", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  4.0), Map.entry(HorizonsModifier.INTEGRITY,  51.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.2), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  6.5), Map.entry(HorizonsModifier.DAMAGE,  13.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  0.24), Map.entry(HorizonsModifier.THERMAL_LOAD,  3.6), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  80.0), Map.entry(HorizonsModifier.SHOT_SPEED,  550.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  0.5), Map.entry(HorizonsModifier.BURST_INTERVAL,  2.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  72.0), Map.entry(HorizonsModifier.RELOAD_TIME,  2.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  6.5), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  1.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  1.0), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO, 0.77), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 0.23), Map.entry(HorizonsModifier.AMMO_COST, 56.0)));
    public static final RemoteReleaseFlechetteLauncher REMOTE_RELEASE_FLECHETTE_LAUNCHER_2_B_T = new RemoteReleaseFlechetteLauncher("REMOTE_RELEASE_FLECHETTE_LAUNCHER_2_B_T", HorizonsBlueprintName.REMOTE_RELEASE_FLECHETTE_LAUNCHER, ModuleSize.SIZE_2, ModuleClass.B, true, Mounting.TURRETED, 1279200, "Hpt_FlechetteLauncher_Turret_Medium", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  4.0), Map.entry(HorizonsModifier.INTEGRITY,  51.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.2), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND,  6.5), Map.entry(HorizonsModifier.DAMAGE,  13.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW,  0.24), Map.entry(HorizonsModifier.THERMAL_LOAD,  3.6), Map.entry(HorizonsModifier.ARMOUR_PIERCING,  70.0), Map.entry(HorizonsModifier.SHOT_SPEED,  550.0), Map.entry(HorizonsModifier.RATE_OF_FIRE,  0.5), Map.entry(HorizonsModifier.BURST_INTERVAL,  2.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE,  1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM,  72.0), Map.entry(HorizonsModifier.RELOAD_TIME,  2.0), Map.entry(HorizonsModifier.BREACH_DAMAGE,  6.5), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE,  1.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE,  1.0), Map.entry(HorizonsModifier.KINETIC_DAMAGE_RATIO, 0.77), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 0.23), Map.entry(HorizonsModifier.AMMO_COST, 56.0)));
    public static final List<RemoteReleaseFlechetteLauncher> REMOTE_RELEASE_FLECHETTE_LAUNCHERS = List.of(
            REMOTE_RELEASE_FLECHETTE_LAUNCHER_2_B_F,
            REMOTE_RELEASE_FLECHETTE_LAUNCHER_2_B_T
    );
    public RemoteReleaseFlechetteLauncher(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public RemoteReleaseFlechetteLauncher(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public RemoteReleaseFlechetteLauncher(RemoteReleaseFlechetteLauncher remoteReleaseFlechetteLauncher) {
        super(remoteReleaseFlechetteLauncher);
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
    public RemoteReleaseFlechetteLauncher Clone() {
        return new RemoteReleaseFlechetteLauncher(this);
    }
}
