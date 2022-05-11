package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.LevelValue;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@SuppressWarnings("java:S1192")
public enum Weapon implements Equipment {


    KINEMATIC_P15(
            new LevelValue(OdysseyBlueprintName.NONE, OdysseyBlueprintName.KARMA_1_2, OdysseyBlueprintName.KARMA_2_3, OdysseyBlueprintName.KARMA_3_4, OdysseyBlueprintName.KARMA_4_5),
            Map.entry(DynamicStat.MODIFICATION_SLOTS, new LevelValue(0, 1, 2, 3, 4)),
            Map.entry(StaticStat.CLASS, "loadout.stat.value.class.pistol"),
            Map.entry(StaticStat.TYPE, "loadout.stat.value.type.secondary"),
            Map.entry(StaticStat.DAMAGE_TYPE, "loadout.stat.value.damage_type.kinetic"),
            Map.entry(StaticStat.FIRE_MODE, "loadout.stat.value.fire_mode.semi-automatic"),
            Map.entry(DynamicStat.DAMAGE, new LevelValue(1.4, 1.8, 2.4, 3.8, 4.1)),
            Map.entry(StaticStat.RATE_OF_FIRE, 10.0),
            Map.entry(DynamicStat.MAGAZINE_SIZE, 24),
            Map.entry(DynamicStat.RESERVE_AMMO, 120),
            Map.entry(DynamicStat.HEADSHOT_DAMAGE, 200),
            Map.entry(DynamicStat.EFFECTIVE_RANGE, 25),
            Map.entry(DynamicStat.HIP_FIRE_ACCURACY, 80.7),
            Map.entry(DynamicStat.STABILITY, 40.6),
            Map.entry(DynamicStat.SILENCED_INSIDE, false),
            Map.entry(DynamicStat.SILENCED_OUTSIDE, false),
            Map.entry(DynamicStat.RELOAD_SPEED, 1.75),
            Map.entry(DynamicStat.SCOPE, false),
            Map.entry(DynamicStat.STOWED_RELOADING, false),
            Map.entry(DynamicStat.DRAW_SPEED, 0.45),
            Map.entry(DynamicStat.STOW_SPEED, 0.63),
            Map.entry(DynamicStat.ADS_SPEED, 0.20)


    ),
    KINEMATIC_AR50(
            new LevelValue(OdysseyBlueprintName.NONE, OdysseyBlueprintName.KARMA_1_2, OdysseyBlueprintName.KARMA_2_3, OdysseyBlueprintName.KARMA_3_4, OdysseyBlueprintName.KARMA_4_5),
            Map.entry(DynamicStat.MODIFICATION_SLOTS, new LevelValue(0, 1, 2, 3, 4)),
            Map.entry(StaticStat.CLASS, "loadout.stat.value.class.rifle"),
            Map.entry(StaticStat.TYPE, "loadout.stat.value.type.primary"),
            Map.entry(StaticStat.DAMAGE_TYPE, "loadout.stat.value.damage_type.kinetic"),
            Map.entry(StaticStat.FIRE_MODE, "loadout.stat.value.fire_mode.automatic"),
            Map.entry(DynamicStat.DAMAGE, new LevelValue(0.9, 1.2, 1.6, 2.0, 2.7)),
            Map.entry(StaticStat.RATE_OF_FIRE, 10.0),
            Map.entry(DynamicStat.MAGAZINE_SIZE, 40),
            Map.entry(DynamicStat.RESERVE_AMMO, 200),
            Map.entry(DynamicStat.HEADSHOT_DAMAGE, 200),
            Map.entry(DynamicStat.EFFECTIVE_RANGE, 50),
            Map.entry(DynamicStat.HIP_FIRE_ACCURACY, 75.3),
            Map.entry(DynamicStat.STABILITY, 78.9),
            Map.entry(DynamicStat.SILENCED_INSIDE, false),
            Map.entry(DynamicStat.SILENCED_OUTSIDE, false),
            Map.entry(DynamicStat.RELOAD_SPEED, 2.7),
            Map.entry(DynamicStat.SCOPE, false),
            Map.entry(DynamicStat.STOWED_RELOADING, false),
            Map.entry(DynamicStat.DRAW_SPEED, 0.50),
            Map.entry(DynamicStat.STOW_SPEED, 0.50),
            Map.entry(DynamicStat.ADS_SPEED, 0.50)
    ),
    KINEMATIC_C44(
            new LevelValue(OdysseyBlueprintName.NONE, OdysseyBlueprintName.KARMA_1_2, OdysseyBlueprintName.KARMA_2_3, OdysseyBlueprintName.KARMA_3_4, OdysseyBlueprintName.KARMA_4_5),
            Map.entry(DynamicStat.MODIFICATION_SLOTS, new LevelValue(0, 1, 2, 3, 4)),
            Map.entry(StaticStat.CLASS, "loadout.stat.value.class.carbine"),
            Map.entry(StaticStat.TYPE, "loadout.stat.value.type.primary"),
            Map.entry(StaticStat.DAMAGE_TYPE, "loadout.stat.value.damage_type.kinetic"),
            Map.entry(StaticStat.FIRE_MODE, "loadout.stat.value.fire_mode.automatic"),
            Map.entry(DynamicStat.DAMAGE, new LevelValue(0.6, 0.9, 1.1, 1.5, 1.9)),
            Map.entry(StaticStat.RATE_OF_FIRE, 13.3),
            Map.entry(DynamicStat.MAGAZINE_SIZE, 60),
            Map.entry(DynamicStat.RESERVE_AMMO, 360),
            Map.entry(DynamicStat.HEADSHOT_DAMAGE, 200),
            Map.entry(DynamicStat.EFFECTIVE_RANGE, 25),
            Map.entry(DynamicStat.HIP_FIRE_ACCURACY, 82.1),
            Map.entry(DynamicStat.STABILITY, 62.4),
            Map.entry(DynamicStat.SILENCED_INSIDE, false),
            Map.entry(DynamicStat.SILENCED_OUTSIDE, false),
            Map.entry(DynamicStat.RELOAD_SPEED, 2.46),
            Map.entry(DynamicStat.SCOPE, false),
            Map.entry(DynamicStat.STOWED_RELOADING, false),
            Map.entry(DynamicStat.DRAW_SPEED, 0.47),
            Map.entry(DynamicStat.STOW_SPEED, 0.47),
            Map.entry(DynamicStat.ADS_SPEED, 0.53)
    ),
    KINEMATIC_L6(
            new LevelValue(OdysseyBlueprintName.NONE, OdysseyBlueprintName.KARMA_1_2, OdysseyBlueprintName.KARMA_2_3, OdysseyBlueprintName.KARMA_3_4, OdysseyBlueprintName.KARMA_4_5),
            Map.entry(DynamicStat.MODIFICATION_SLOTS, new LevelValue(0, 1, 2, 3, 4)),
            Map.entry(StaticStat.CLASS, "loadout.stat.value.class.launcher"),
            Map.entry(StaticStat.TYPE, "loadout.stat.value.type.primary"),
            Map.entry(StaticStat.DAMAGE_TYPE, "loadout.stat.value.damage_type.explosive"),
            Map.entry(StaticStat.FIRE_MODE, "loadout.stat.value.fire_mode.automatic"),
            Map.entry(DynamicStat.DAMAGE, new LevelValue(40.0, 52.4, 69.2, 90.0, 119.2)),
            Map.entry(StaticStat.RATE_OF_FIRE, 1.0),
            Map.entry(DynamicStat.MAGAZINE_SIZE, 2),
            Map.entry(DynamicStat.RESERVE_AMMO, 8),
            Map.entry(DynamicStat.HEADSHOT_DAMAGE, 100),
            Map.entry(DynamicStat.EFFECTIVE_RANGE, 300),
            Map.entry(DynamicStat.HIP_FIRE_ACCURACY, 69.9),
            Map.entry(DynamicStat.STABILITY, 51.0),
            Map.entry(DynamicStat.SILENCED_INSIDE, false),
            Map.entry(DynamicStat.SILENCED_OUTSIDE, false),
            Map.entry(DynamicStat.RELOAD_SPEED, 5.78),
            Map.entry(DynamicStat.SCOPE, false),
            Map.entry(DynamicStat.STOWED_RELOADING, false),
            Map.entry(DynamicStat.DRAW_SPEED, 0.48),
            Map.entry(DynamicStat.STOW_SPEED, 1.12),
            Map.entry(DynamicStat.ADS_SPEED, 0.60)
    ),
    TAKADA_APHELION(
            new LevelValue(OdysseyBlueprintName.NONE, OdysseyBlueprintName.TK_1_2, OdysseyBlueprintName.TK_2_3, OdysseyBlueprintName.TK_3_4, OdysseyBlueprintName.TK_4_5),
            Map.entry(DynamicStat.MODIFICATION_SLOTS, new LevelValue(0, 1, 2, 3, 4)),
            Map.entry(StaticStat.CLASS, "loadout.stat.value.class.rifle"),
            Map.entry(StaticStat.TYPE, "loadout.stat.value.type.primary"),
            Map.entry(StaticStat.DAMAGE_TYPE, "loadout.stat.value.damage_type.thermal"),
            Map.entry(StaticStat.FIRE_MODE, "loadout.stat.value.fire_mode.automatic"),
            Map.entry(DynamicStat.DAMAGE, new LevelValue(1.6, 2.1, 2.7, 3.6, 4.7)),
            Map.entry(StaticStat.RATE_OF_FIRE, 5.7),
            Map.entry(DynamicStat.MAGAZINE_SIZE, 25),
            Map.entry(DynamicStat.RESERVE_AMMO, 150),
            Map.entry(DynamicStat.HEADSHOT_DAMAGE, 100),
            Map.entry(DynamicStat.EFFECTIVE_RANGE, 70),
            Map.entry(DynamicStat.HIP_FIRE_ACCURACY, 78.1),
            Map.entry(DynamicStat.STABILITY, 81.9),
            Map.entry(DynamicStat.SILENCED_INSIDE, false),
            Map.entry(DynamicStat.SILENCED_OUTSIDE, false),
            Map.entry(DynamicStat.RELOAD_SPEED, 2.27),
            Map.entry(DynamicStat.SCOPE, false),
            Map.entry(DynamicStat.STOWED_RELOADING, false),
            Map.entry(DynamicStat.DRAW_SPEED, 0.49),
            Map.entry(DynamicStat.STOW_SPEED, 0.49),
            Map.entry(DynamicStat.ADS_SPEED, 0.55)
    ),
    TAKADA_ECLIPSE(
            new LevelValue(OdysseyBlueprintName.NONE, OdysseyBlueprintName.TK_1_2, OdysseyBlueprintName.TK_2_3, OdysseyBlueprintName.TK_3_4, OdysseyBlueprintName.TK_4_5),
            Map.entry(DynamicStat.MODIFICATION_SLOTS, new LevelValue(0, 1, 2, 3, 4)),
            Map.entry(StaticStat.CLASS, "loadout.stat.value.class.carbine"),
            Map.entry(StaticStat.TYPE, "loadout.stat.value.type.primary"),
            Map.entry(StaticStat.DAMAGE_TYPE, "loadout.stat.value.damage_type.thermal"),
            Map.entry(StaticStat.FIRE_MODE, "loadout.stat.value.fire_mode.automatic"),
            Map.entry(DynamicStat.DAMAGE, new LevelValue(0.9, 1.1, 1.5, 1.9, 2.5)),
            Map.entry(StaticStat.RATE_OF_FIRE, 10.0),
            Map.entry(DynamicStat.MAGAZINE_SIZE, 40),
            Map.entry(DynamicStat.RESERVE_AMMO, 280),
            Map.entry(DynamicStat.HEADSHOT_DAMAGE, 100),
            Map.entry(DynamicStat.EFFECTIVE_RANGE, 25),
            Map.entry(DynamicStat.HIP_FIRE_ACCURACY, 78.3),
            Map.entry(DynamicStat.STABILITY, 76.3),
            Map.entry(DynamicStat.SILENCED_INSIDE, false),
            Map.entry(DynamicStat.SILENCED_OUTSIDE, false),
            Map.entry(DynamicStat.RELOAD_SPEED, 2.03),
            Map.entry(DynamicStat.SCOPE, false),
            Map.entry(DynamicStat.STOWED_RELOADING, false),
            Map.entry(DynamicStat.DRAW_SPEED, 0.47),
            Map.entry(DynamicStat.STOW_SPEED, 0.47),
            Map.entry(DynamicStat.ADS_SPEED, 0.42)
    ),
    TAKADA_ZENITH(
            new LevelValue(OdysseyBlueprintName.NONE, OdysseyBlueprintName.TK_1_2, OdysseyBlueprintName.TK_2_3, OdysseyBlueprintName.TK_3_4, OdysseyBlueprintName.TK_4_5),
            Map.entry(DynamicStat.MODIFICATION_SLOTS, new LevelValue(0, 1, 2, 3, 4)),
            Map.entry(StaticStat.CLASS, "loadout.stat.value.class.pistol"),
            Map.entry(StaticStat.TYPE, "loadout.stat.value.type.secondary"),
            Map.entry(StaticStat.DAMAGE_TYPE, "loadout.stat.value.damage_type.thermal"),
            Map.entry(StaticStat.FIRE_MODE, "loadout.stat.value.fire_mode.burst"),
            Map.entry(DynamicStat.DAMAGE, new LevelValue(1.7, 2.2, 2.9, 3.8, 5.0)),
            Map.entry(StaticStat.RATE_OF_FIRE, 5.7),
            Map.entry(DynamicStat.MAGAZINE_SIZE, 18),
            Map.entry(DynamicStat.RESERVE_AMMO, 90),
            Map.entry(DynamicStat.HEADSHOT_DAMAGE, 100),
            Map.entry(DynamicStat.EFFECTIVE_RANGE, 35),
            Map.entry(DynamicStat.HIP_FIRE_ACCURACY, 74.9),
            Map.entry(DynamicStat.STABILITY, 68.6),
            Map.entry(DynamicStat.SILENCED_INSIDE, false),
            Map.entry(DynamicStat.SILENCED_OUTSIDE, false),
            Map.entry(DynamicStat.RELOAD_SPEED, 1.75),
            Map.entry(DynamicStat.SCOPE, false),
            Map.entry(DynamicStat.STOWED_RELOADING, false),
            Map.entry(DynamicStat.DRAW_SPEED, 0.45),
            Map.entry(DynamicStat.STOW_SPEED, 0.65),
            Map.entry(DynamicStat.ADS_SPEED, 0.28)
    ),
    MANTICORE_EXECUTIONER(
            new LevelValue(OdysseyBlueprintName.NONE, OdysseyBlueprintName.MANTICORE_1_2, OdysseyBlueprintName.MANTICORE_2_3, OdysseyBlueprintName.MANTICORE_3_4, OdysseyBlueprintName.MANTICORE_4_5),
            Map.entry(DynamicStat.MODIFICATION_SLOTS, new LevelValue(0, 1, 2, 3, 4)),
            Map.entry(StaticStat.CLASS, "loadout.stat.value.class.sniper"),
            Map.entry(StaticStat.TYPE, "loadout.stat.value.type.primary"),
            Map.entry(StaticStat.DAMAGE_TYPE, "loadout.stat.value.damage_type.plasma"),
            Map.entry(StaticStat.FIRE_MODE, "loadout.stat.value.fire_mode.semi-automatic"),
            Map.entry(DynamicStat.DAMAGE, new LevelValue(15.0, 19.6, 26.0, 34.0, 44.7)),
            Map.entry(StaticStat.RATE_OF_FIRE, 0.8),
            Map.entry(DynamicStat.MAGAZINE_SIZE, 3),
            Map.entry(DynamicStat.RESERVE_AMMO, 30),
            Map.entry(DynamicStat.HEADSHOT_DAMAGE, 300),
            Map.entry(DynamicStat.EFFECTIVE_RANGE, 100),
            Map.entry(DynamicStat.HIP_FIRE_ACCURACY, 35.3),
            Map.entry(DynamicStat.STABILITY, 47.4),
            Map.entry(DynamicStat.SILENCED_INSIDE, false),
            Map.entry(DynamicStat.SILENCED_OUTSIDE, false),
            Map.entry(DynamicStat.RELOAD_SPEED, 3.5),
            Map.entry(DynamicStat.SCOPE, false),
            Map.entry(DynamicStat.STOWED_RELOADING, false),
            Map.entry(DynamicStat.DRAW_SPEED, 0.47),
            Map.entry(DynamicStat.STOW_SPEED, 0.65),
            Map.entry(DynamicStat.ADS_SPEED, 0.85)
    ),
    MANTICORE_TORMENTOR(
            new LevelValue(OdysseyBlueprintName.NONE, OdysseyBlueprintName.MANTICORE_1_2, OdysseyBlueprintName.MANTICORE_2_3, OdysseyBlueprintName.MANTICORE_3_4, OdysseyBlueprintName.MANTICORE_4_5),
            Map.entry(DynamicStat.MODIFICATION_SLOTS, new LevelValue(0, 1, 2, 3, 4)),
            Map.entry(StaticStat.CLASS, "loadout.stat.value.class.pistol"),
            Map.entry(StaticStat.TYPE, "loadout.stat.value.type.secondary"),
            Map.entry(StaticStat.DAMAGE_TYPE, "loadout.stat.value.damage_type.plasma"),
            Map.entry(StaticStat.FIRE_MODE, "loadout.stat.value.fire_mode.semi-automatic"),
            Map.entry(DynamicStat.DAMAGE, new LevelValue(7.5, 9.8, 13.0, 17.0, 22.4)),
            Map.entry(StaticStat.RATE_OF_FIRE, 1.7),
            Map.entry(DynamicStat.MAGAZINE_SIZE, 6),
            Map.entry(DynamicStat.RESERVE_AMMO, 72),
            Map.entry(DynamicStat.HEADSHOT_DAMAGE, 200),
            Map.entry(DynamicStat.EFFECTIVE_RANGE, 15),
            Map.entry(DynamicStat.HIP_FIRE_ACCURACY, 56.8),
            Map.entry(DynamicStat.STABILITY, 18.2),
            Map.entry(DynamicStat.SILENCED_INSIDE, false),
            Map.entry(DynamicStat.SILENCED_OUTSIDE, false),
            Map.entry(DynamicStat.RELOAD_SPEED, 2.5),
            Map.entry(DynamicStat.SCOPE, false),
            Map.entry(DynamicStat.STOWED_RELOADING, false),
            Map.entry(DynamicStat.DRAW_SPEED, 0.45),
            Map.entry(DynamicStat.STOW_SPEED, 0.63),
            Map.entry(DynamicStat.ADS_SPEED, 0.33)
    ),
    MANTICORE_INTIMIDATOR(
            new LevelValue(OdysseyBlueprintName.NONE, OdysseyBlueprintName.MANTICORE_1_2, OdysseyBlueprintName.MANTICORE_2_3, OdysseyBlueprintName.MANTICORE_3_4, OdysseyBlueprintName.MANTICORE_4_5),
            Map.entry(DynamicStat.MODIFICATION_SLOTS, new LevelValue(0, 1, 2, 3, 4)),
            Map.entry(StaticStat.CLASS, "loadout.stat.value.class.shotgun"),
            Map.entry(StaticStat.TYPE, "loadout.stat.value.type.primary"),
            Map.entry(StaticStat.DAMAGE_TYPE, "loadout.stat.value.damage_type.plasma"),
            Map.entry(StaticStat.FIRE_MODE, "loadout.stat.value.fire_mode.semi-automatic"),
            Map.entry(DynamicStat.DAMAGE, new LevelValue(1.8, 2.3, 3.0, 4.0, 5.2)),
            Map.entry(StaticStat.RATE_OF_FIRE, 1.3),
            Map.entry(DynamicStat.MAGAZINE_SIZE, 2),
            Map.entry(DynamicStat.RESERVE_AMMO, 24),
            Map.entry(DynamicStat.HEADSHOT_DAMAGE, 150),
            Map.entry(DynamicStat.EFFECTIVE_RANGE, 7),
            Map.entry(DynamicStat.HIP_FIRE_ACCURACY, 16.1),
            Map.entry(DynamicStat.STABILITY, 38.8),
            Map.entry(DynamicStat.SILENCED_INSIDE, false),
            Map.entry(DynamicStat.SILENCED_OUTSIDE, false),
            Map.entry(DynamicStat.RELOAD_SPEED, 2.5),
            Map.entry(DynamicStat.SCOPE, false),
            Map.entry(DynamicStat.STOWED_RELOADING, false),
            Map.entry(DynamicStat.DRAW_SPEED, 0.47),
            Map.entry(DynamicStat.STOW_SPEED, 0.52),
            Map.entry(DynamicStat.ADS_SPEED, 0.53)
    ),
    MANTICORE_OPPRESSOR(
            new LevelValue(OdysseyBlueprintName.NONE, OdysseyBlueprintName.MANTICORE_1_2, OdysseyBlueprintName.MANTICORE_2_3, OdysseyBlueprintName.MANTICORE_3_4, OdysseyBlueprintName.MANTICORE_4_5),
            Map.entry(DynamicStat.MODIFICATION_SLOTS, new LevelValue(0, 1, 2, 3, 4)),
            Map.entry(StaticStat.CLASS, "loadout.stat.value.class.rifle"),
            Map.entry(StaticStat.TYPE, "loadout.stat.value.type.primary"),
            Map.entry(StaticStat.DAMAGE_TYPE, "loadout.stat.value.damage_type.plasma"),
            Map.entry(StaticStat.FIRE_MODE, "loadout.stat.value.fire_mode.automatic"),
            Map.entry(DynamicStat.DAMAGE, new LevelValue(0.8, 1.0, 1.4, 1.8, 2.4)),
            Map.entry(StaticStat.RATE_OF_FIRE, 6.7),
            Map.entry(DynamicStat.MAGAZINE_SIZE, 50),
            Map.entry(DynamicStat.RESERVE_AMMO, 300),
            Map.entry(DynamicStat.HEADSHOT_DAMAGE, 150),
            Map.entry(DynamicStat.EFFECTIVE_RANGE, 35),
            Map.entry(DynamicStat.HIP_FIRE_ACCURACY, 75.3),
            Map.entry(DynamicStat.STABILITY, 69.4),
            Map.entry(DynamicStat.SILENCED_INSIDE, false),
            Map.entry(DynamicStat.SILENCED_OUTSIDE, false),
            Map.entry(DynamicStat.RELOAD_SPEED, 2.46),
            Map.entry(DynamicStat.SCOPE, false),
            Map.entry(DynamicStat.STOWED_RELOADING, false),
            Map.entry(DynamicStat.DRAW_SPEED, 0.53),
            Map.entry(DynamicStat.STOW_SPEED, 0.59),
            Map.entry(DynamicStat.ADS_SPEED, 0.58)
    );
    @Getter
    private static final Map<Weapon, Double> HIP_FIRE_FACTORS = Map.ofEntries(
            Map.entry(Weapon.KINEMATIC_P15, 43.00),
            Map.entry(Weapon.KINEMATIC_C44, 41.25),
            Map.entry(Weapon.KINEMATIC_AR50, 41.25),
            Map.entry(Weapon.KINEMATIC_L6, 40.75),
            Map.entry(Weapon.TAKADA_APHELION, 40.75),
            Map.entry(Weapon.TAKADA_ZENITH, 43.00),
            Map.entry(Weapon.TAKADA_ECLIPSE, 41.25),
            Map.entry(Weapon.MANTICORE_EXECUTIONER, 42.25),
            Map.entry(Weapon.MANTICORE_TORMENTOR, 43.00),
            Map.entry(Weapon.MANTICORE_INTIMIDATOR, 40.75),
            Map.entry(Weapon.MANTICORE_OPPRESSOR, 41.25)
    );
    @Getter
    private static final Map<Weapon, Double> DRAW_STOW_FACTORS = Map.ofEntries(
            Map.entry(Weapon.KINEMATIC_P15, 30.0),
            Map.entry(Weapon.KINEMATIC_C44, 35.0),
            Map.entry(Weapon.KINEMATIC_AR50, 33.0),
            Map.entry(Weapon.KINEMATIC_L6, 30.0),
            Map.entry(Weapon.TAKADA_APHELION, 33.0),
            Map.entry(Weapon.TAKADA_ZENITH, 30.0),
            Map.entry(Weapon.TAKADA_ECLIPSE, 31.0),
            Map.entry(Weapon.MANTICORE_EXECUTIONER, 32.0),
            Map.entry(Weapon.MANTICORE_TORMENTOR, 30.0),
            Map.entry(Weapon.MANTICORE_INTIMIDATOR, 32.0),
            Map.entry(Weapon.MANTICORE_OPPRESSOR, 50.0)
    );
    @Getter
    private static final Map<Weapon, Double> ADS_FACTORS = Map.ofEntries(
            Map.entry(Weapon.KINEMATIC_P15, 45.0),
            Map.entry(Weapon.KINEMATIC_C44, 40.0),
            Map.entry(Weapon.KINEMATIC_AR50, 40.0),
            Map.entry(Weapon.KINEMATIC_L6, 30.0),
            Map.entry(Weapon.TAKADA_APHELION, 40.0),
            Map.entry(Weapon.TAKADA_ZENITH, 30.0),
            Map.entry(Weapon.TAKADA_ECLIPSE, 45.0),
            Map.entry(Weapon.MANTICORE_EXECUTIONER, 40.0),
            Map.entry(Weapon.MANTICORE_TORMENTOR, 40.0),
            Map.entry(Weapon.MANTICORE_INTIMIDATOR, 40.0),
            Map.entry(Weapon.MANTICORE_OPPRESSOR, 45.0)
    );

    public boolean isKinetic() {
        return KINEMATIC_AR50.equals(this) || KINEMATIC_C44.equals(this) || KINEMATIC_L6.equals(this) || KINEMATIC_P15.equals(this);
    }

    public boolean isLaser() {
        return TAKADA_APHELION.equals(this) || TAKADA_ECLIPSE.equals(this) || TAKADA_ZENITH.equals(this);
    }

    public boolean isPlasma() {
        return MANTICORE_EXECUTIONER.equals(this) || MANTICORE_OPPRESSOR.equals(this) || MANTICORE_INTIMIDATOR.equals(this) || MANTICORE_TORMENTOR.equals(this);
    }

    private final Map<Stat, Object> stats;
    private final LevelValue recipes;

    Weapon(final LevelValue recipes, final Map.Entry<Stat, Object>... statsList) {
        this.recipes = recipes;
        this.stats = new LinkedHashMap<>(statsList.length);
        Arrays.stream(statsList).forEach(entry -> this.stats.put(entry.getKey(), entry.getValue()));
    }

    @Override
    public String getImage() {
        return "/images/weapon/" + name().toLowerCase() + ".png";
    }

    @Override
    public String getLocalizationKey() {
        return "loadout.equipment.name." + this.name().toLowerCase();
    }

}
