package nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint;

import nl.jixxed.eliteodysseymaterials.domain.ships.Modification;
import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RemoteReleaseFlakLauncher extends HardpointModule {
    public static final RemoteReleaseFlakLauncher REMOTE_RELEASE_FLAK_LAUNCHER_2_B_F = new RemoteReleaseFlakLauncher("REMOTE_RELEASE_FLAK_LAUNCHER_2_B_F", HorizonsBlueprintName.REMOTE_RELEASE_FLAK_LAUNCHER, ModuleSize.SIZE_2, ModuleClass.B, false, Mounting.FIXED, 261800, "Hpt_FlakMortar_Fixed_Medium", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.0), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.2), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 17.0), Map.entry(HorizonsModifier.DAMAGE, 34.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.24), Map.entry(HorizonsModifier.THERMAL_LOAD, 3.6), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.SHOT_SPEED, 550.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 0.5), Map.entry(HorizonsModifier.BURST_INTERVAL, 2.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 32.0), Map.entry(HorizonsModifier.RELOAD_TIME, 2.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 1.7), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.AMMO_COST, 125.0)));
    public static final RemoteReleaseFlakLauncher REMOTE_RELEASE_FLAK_LAUNCHER_2_B_T = new RemoteReleaseFlakLauncher("REMOTE_RELEASE_FLAK_LAUNCHER_2_B_T", HorizonsBlueprintName.REMOTE_RELEASE_FLAK_LAUNCHER, ModuleSize.SIZE_2, ModuleClass.B, true, Mounting.TURRETED, 1259200, "Hpt_FlakMortar_Turret_Medium", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.0), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.2), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 17.0), Map.entry(HorizonsModifier.DAMAGE, 34.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.24), Map.entry(HorizonsModifier.THERMAL_LOAD, 3.6), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.SHOT_SPEED, 550.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 0.5), Map.entry(HorizonsModifier.BURST_INTERVAL, 2.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 32.0), Map.entry(HorizonsModifier.RELOAD_TIME, 2.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 1.7), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.AMMO_COST, 125.0)));
    public static final RemoteReleaseFlakLauncher REMOTE_RELEASE_FLAK_LAUNCHER_2_B_T_PRE_GREEN = new RemoteReleaseFlakLauncher("REMOTE_RELEASE_FLAK_LAUNCHER_2_B_T_PRE_GREEN", HorizonsBlueprintName.FESTIVE_LAUNCHER, ModuleSize.SIZE_2, ModuleClass.B, true, Mounting.TURRETED, 0, "Hpt_FlakMortar_Turret_Medium", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.0), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.2), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 17.0), Map.entry(HorizonsModifier.DAMAGE, 34.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.24), Map.entry(HorizonsModifier.THERMAL_LOAD, 3.6), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.SHOT_SPEED, 550.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 0.5), Map.entry(HorizonsModifier.BURST_INTERVAL, 2.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 32.0), Map.entry(HorizonsModifier.RELOAD_TIME, 2.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 1.7), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.AMMO_COST, 125.0)));
    public static final RemoteReleaseFlakLauncher REMOTE_RELEASE_FLAK_LAUNCHER_2_B_T_PRE_YELLOW = new RemoteReleaseFlakLauncher("REMOTE_RELEASE_FLAK_LAUNCHER_2_B_T_PRE_YELLOW", HorizonsBlueprintName.FESTIVE_LAUNCHER, ModuleSize.SIZE_2, ModuleClass.B, true, Mounting.TURRETED, 0, "Hpt_FlakMortar_Turret_Medium", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.0), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.2), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 17.0), Map.entry(HorizonsModifier.DAMAGE, 34.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.24), Map.entry(HorizonsModifier.THERMAL_LOAD, 3.6), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.SHOT_SPEED, 550.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 0.5), Map.entry(HorizonsModifier.BURST_INTERVAL, 2.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 32.0), Map.entry(HorizonsModifier.RELOAD_TIME, 2.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 1.7), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.AMMO_COST, 125.0)));
    public static final RemoteReleaseFlakLauncher REMOTE_RELEASE_FLAK_LAUNCHER_2_B_T_PRE_RED = new RemoteReleaseFlakLauncher("REMOTE_RELEASE_FLAK_LAUNCHER_2_B_T_PRE_RED", HorizonsBlueprintName.FESTIVE_LAUNCHER, ModuleSize.SIZE_2, ModuleClass.B, true, Mounting.TURRETED, 0, "Hpt_FlakMortar_Turret_Medium", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 4.0), Map.entry(HorizonsModifier.INTEGRITY, 51.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.2), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DAMAGE_PER_SECOND, 17.0), Map.entry(HorizonsModifier.DAMAGE, 34.0), Map.entry(HorizonsModifier.DISTRIBUTOR_DRAW, 0.24), Map.entry(HorizonsModifier.THERMAL_LOAD, 3.6), Map.entry(HorizonsModifier.ARMOUR_PIERCING, 60.0), Map.entry(HorizonsModifier.SHOT_SPEED, 550.0), Map.entry(HorizonsModifier.RATE_OF_FIRE, 0.5), Map.entry(HorizonsModifier.BURST_INTERVAL, 2.0), Map.entry(HorizonsModifier.AMMO_CLIP_SIZE, 1.0), Map.entry(HorizonsModifier.AMMO_MAXIMUM, 32.0), Map.entry(HorizonsModifier.RELOAD_TIME, 2.0), Map.entry(HorizonsModifier.BREACH_DAMAGE, 1.7), Map.entry(HorizonsModifier.MIN_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.MAX_BREACH_CHANCE, 1.0), Map.entry(HorizonsModifier.EXPLOSIVE_DAMAGE_RATIO, 1.0), Map.entry(HorizonsModifier.AMMO_COST, 125.0)));

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

    public RemoteReleaseFlakLauncher(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public RemoteReleaseFlakLauncher(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
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

    public MatchType getPreEngineeredMatchType(){
        return MatchType.BLUEPRINT;
    }

    @Override
    public int getGrouping() {
        return switch (getId()) {
            case "REMOTE_RELEASE_FLAK_LAUNCHER_2_B_F", "REMOTE_RELEASE_FLAK_LAUNCHER_2_B_T" -> 1;
            case "REMOTE_RELEASE_FLAK_LAUNCHER_2_B_T_PRE_GREEN" -> 2;
            case "REMOTE_RELEASE_FLAK_LAUNCHER_2_B_T_PRE_YELLOW" -> 3;
            case "REMOTE_RELEASE_FLAK_LAUNCHER_2_B_T_PRE_RED" -> 4;
            default -> 0;
        };
    }

    @Override
    public String getClarifier() {
        if (isPreEngineered()) {
            return switch (getId()) {
                case "REMOTE_RELEASE_FLAK_LAUNCHER_2_B_T_PRE_GREEN" ->
                        " " + LocaleService.getLocalizedStringForCurrentLocale(getName().getLocalizationKey() + ".green");
                case "REMOTE_RELEASE_FLAK_LAUNCHER_2_B_T_PRE_YELLOW" ->
                        " " + LocaleService.getLocalizedStringForCurrentLocale(getName().getLocalizationKey() + ".yellow");
                case "REMOTE_RELEASE_FLAK_LAUNCHER_2_B_T_PRE_RED" ->
                        " " + LocaleService.getLocalizedStringForCurrentLocale(getName().getLocalizationKey() + ".red");
                default -> "";
            };
        }
        return super.getClarifier();
    }

    @Override
    public boolean isCGExclusive() {
        return isPreEngineered();
    }
}
