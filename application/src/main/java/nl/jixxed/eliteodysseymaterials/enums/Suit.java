package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.LevelValue;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
public enum Suit implements Equipment {
    MAVERICK(
            new LevelValue(BlueprintName.NONE, BlueprintName.MAVERICK_SUIT_GRADE_1_2, BlueprintName.MAVERICK_SUIT_GRADE_2_3, BlueprintName.MAVERICK_SUIT_GRADE_3_4, BlueprintName.MAVERICK_SUIT_GRADE_4_5),
            Map.entry(DynamicStat.MODIFICATION_SLOTS, new LevelValue(0, 1, 2, 3, 4)),
            Map.entry(StaticStat.HEALTH, 30),
            Map.entry(StaticStat.MASS, 100),
            Map.entry(StaticStat.PRIMARY_SLOTS, 1),
            Map.entry(StaticStat.SECONDARY_SLOTS, 1),
            Map.entry(DynamicStat.EMERGENCY_AIR, 60),
            Map.entry(DynamicStat.SHIELD_STRENGTH, new LevelValue(13.5, 16.5, 20.3, 24.9, 30.4)),
            Map.entry(DynamicStat.SHIELD_REGEN, new LevelValue(0.99, 1.21, 1.49, 1.82, 2.23)),
            Map.entry(DynamicStat.KINETIC_RESIST, new LevelValue(-60, -34, -13, 6, 19)),
            Map.entry(DynamicStat.THERMAL_RESIST, new LevelValue(50, 58, 64, 70, 75)),
            Map.entry(DynamicStat.PLASMA_RESIST, new LevelValue(-10, 7, 22, 35, 45)),
            Map.entry(DynamicStat.EXPLOSIVE_RESIST, new LevelValue(0, 16, 29, 41, 50)),
            Map.entry(DynamicStat.BATTERY, 13.5),
            Map.entry(StaticStat.ENERGYLINK_RECHARGE_TIME, 5),
            Map.entry(DynamicStat.ARCCUTTER_POWER_USAGE, 150.0),
            Map.entry(DynamicStat.AMMO_CAPACITY, 100),
            Map.entry(DynamicStat.GOODS_CAPACITY, 15),
            Map.entry(DynamicStat.ASSETS_CAPACITY, 30),
            Map.entry(DynamicStat.DATA_CAPACITY, 10)
    ),
    DOMINATOR(
            new LevelValue(BlueprintName.NONE, BlueprintName.DOMINATOR_SUIT_GRADE_1_2, BlueprintName.DOMINATOR_SUIT_GRADE_2_3, BlueprintName.DOMINATOR_SUIT_GRADE_3_4, BlueprintName.DOMINATOR_SUIT_GRADE_4_5),
            Map.entry(DynamicStat.MODIFICATION_SLOTS, new LevelValue(0, 1, 2, 3, 4)),
            Map.entry(StaticStat.HEALTH, 30),
            Map.entry(StaticStat.MASS, 100),
            Map.entry(StaticStat.PRIMARY_SLOTS, 2),
            Map.entry(StaticStat.SECONDARY_SLOTS, 1),
            Map.entry(DynamicStat.EMERGENCY_AIR, 60),
            Map.entry(DynamicStat.SHIELD_STRENGTH, new LevelValue(15.0, 18.3, 22.5, 27.6, 33.8)),
            Map.entry(DynamicStat.SHIELD_REGEN, new LevelValue(1.10, 1.34, 1.65, 2.02, 2.48)),
            Map.entry(DynamicStat.KINETIC_RESIST, new LevelValue(-50, -26, -7, 19, 25)),
            Map.entry(DynamicStat.THERMAL_RESIST, new LevelValue(60, 66, 72, 78, 80)),
            Map.entry(DynamicStat.PLASMA_RESIST, new LevelValue(0, 16, 29, 46, 50)),
            Map.entry(DynamicStat.EXPLOSIVE_RESIST, new LevelValue(0, 16, 29, 46, 50)),
            Map.entry(DynamicStat.BATTERY, 10.0),
            Map.entry(StaticStat.ENERGYLINK_RECHARGE_TIME, 4),
            Map.entry(DynamicStat.AMMO_CAPACITY, 100),
            Map.entry(DynamicStat.GOODS_CAPACITY, 5),
            Map.entry(DynamicStat.ASSETS_CAPACITY, 10),
            Map.entry(DynamicStat.DATA_CAPACITY, 10)
    ),
    ARTEMIS(
            new LevelValue(BlueprintName.NONE, BlueprintName.ARTEMIS_SUIT_GRADE_1_2, BlueprintName.ARTEMIS_SUIT_GRADE_2_3, BlueprintName.ARTEMIS_SUIT_GRADE_3_4, BlueprintName.ARTEMIS_SUIT_GRADE_4_5),
            Map.entry(DynamicStat.MODIFICATION_SLOTS, new LevelValue(0, 1, 2, 3, 4)),
            Map.entry(StaticStat.HEALTH, 30),
            Map.entry(StaticStat.MASS, 100),
            Map.entry(StaticStat.PRIMARY_SLOTS, 1),
            Map.entry(StaticStat.SECONDARY_SLOTS, 1),
            Map.entry(DynamicStat.EMERGENCY_AIR, 60),
            Map.entry(DynamicStat.SHIELD_STRENGTH, new LevelValue(12.0, 14.7, 18.0, 22.1, 27.0)),
            Map.entry(DynamicStat.SHIELD_REGEN, new LevelValue(0.88, 1.07, 1.32, 1.62, 1.98)),
            Map.entry(DynamicStat.KINETIC_RESIST, new LevelValue(-70, -42, -21, 0, 14)),
            Map.entry(DynamicStat.THERMAL_RESIST, new LevelValue(39, 50, 57, 65, 70)),
            Map.entry(DynamicStat.PLASMA_RESIST, new LevelValue(-20, 0, 14, 29, 39)),
            Map.entry(DynamicStat.EXPLOSIVE_RESIST, new LevelValue(0, 16, 29, 41, 50)),
            Map.entry(DynamicStat.BATTERY, 17.0),
            Map.entry(StaticStat.ENERGYLINK_RECHARGE_TIME, 6),
            Map.entry(DynamicStat.AMMO_CAPACITY, 100),
            Map.entry(DynamicStat.GOODS_CAPACITY, 10),
            Map.entry(DynamicStat.ASSETS_CAPACITY, 20),
            Map.entry(DynamicStat.DATA_CAPACITY, 10)
    ),
    FLIGHTSUIT(
            new LevelValue(BlueprintName.NONE, BlueprintName.NONE, BlueprintName.NONE, BlueprintName.NONE, BlueprintName.NONE),
            Map.entry(DynamicStat.MODIFICATION_SLOTS, new LevelValue(0, 0, 0, 0, 0)),
            Map.entry(StaticStat.HEALTH, 30),
            Map.entry(StaticStat.MASS, 100),
            Map.entry(StaticStat.PRIMARY_SLOTS, 0),
            Map.entry(StaticStat.SECONDARY_SLOTS, 1),
            Map.entry(DynamicStat.EMERGENCY_AIR, 60),
            Map.entry(DynamicStat.SHIELD_STRENGTH, new LevelValue(7.5, 7.5, 7.5, 7.5, 7.5)),
            Map.entry(DynamicStat.SHIELD_REGEN, new LevelValue(0.55, 0.55, 0.55, 0.55, 0.55)),
            Map.entry(DynamicStat.KINETIC_RESIST, new LevelValue(-70, -70, -70, -70, -70)),
            Map.entry(DynamicStat.THERMAL_RESIST, new LevelValue(39, 39, 39, 39, 39)),
            Map.entry(DynamicStat.PLASMA_RESIST, new LevelValue(-20, -20, -20, -20, -20)),
            Map.entry(DynamicStat.EXPLOSIVE_RESIST, new LevelValue(0, 0, 0, 0, 0)),
            Map.entry(DynamicStat.BATTERY, 7.0),
            Map.entry(StaticStat.ENERGYLINK_RECHARGE_TIME, 6),
            Map.entry(DynamicStat.AMMO_CAPACITY, 100),
            Map.entry(DynamicStat.GOODS_CAPACITY, 5),
            Map.entry(DynamicStat.ASSETS_CAPACITY, 10),
            Map.entry(DynamicStat.DATA_CAPACITY, 10)
    );
    private final List<Map.Entry<Stat, Object>> genericStats = List.of(
            Map.entry(StaticStat.SPRINT_SPEED, 7.0),
            Map.entry(StaticStat.SPRINT_SPEED_PISTOL, 7.0),
            Map.entry(DynamicStat.SPRINT_SPEED_CARBINE_SHOTGUN, 6.65),
            Map.entry(DynamicStat.SPRINT_SPEED_RIFLE, 5.9),
            Map.entry(DynamicStat.SPRINT_SPEED_SNIPER_LAUNCHER, 5.6),
            Map.entry(StaticStat.WALK_SPEED, 4.0),
            Map.entry(StaticStat.WALK_SPEED_PISTOL, 4.0),
            Map.entry(DynamicStat.WALK_SPEED_CARBINE_SHOTGUN, 3.8),
            Map.entry(DynamicStat.WALK_SPEED_RIFLE, 3.38),
            Map.entry(DynamicStat.WALK_SPEED_SNIPER_LAUNCHER, 3.2),
            Map.entry(DynamicStat.ADS_SPEED_PISTOL, 3.4),
            Map.entry(DynamicStat.ADS_SPEED_CARBINE_SHOTGUN, 3.0),
            Map.entry(DynamicStat.ADS_SPEED_RIFLE, 2.0),
            Map.entry(DynamicStat.ADS_SPEED_SNIPER_LAUNCHER, 2.0),
            Map.entry(StaticStat.LATERAL_SPEED, 80),
            Map.entry(DynamicStat.SPRINT_DURATION, 13),
            Map.entry(DynamicStat.NIGHT_VISION, 1.2),
            Map.entry(DynamicStat.JUMP_ASSIST_BATTERY_CONSUMPTION, 100),
            Map.entry(DynamicStat.JUMP_ASSIST_DRAIN, 100),
            Map.entry(DynamicStat.JUMP_ASSIST_RECHARGE, 100),
            Map.entry(StaticStat.SHIELD_POWER_USAGE, 34.0),
            Map.entry(StaticStat.LIGHTS_POWER_USAGE, 0.1),
            Map.entry(DynamicStat.ENERGYLINK_OVERLOAD_POWER_USAGE, 3.0),
            Map.entry(DynamicStat.LOS_ANALYSIS_RANGE, 50),
            Map.entry(DynamicStat.LOS_ANALYSIS_TIME, 1),
            Map.entry(DynamicStat.FOOTSTEPS_AUDIBLE_RANGE, 8),
            Map.entry(DynamicStat.MELEE_DAMAGE, 100)
    );
    private final Map<Stat, Object> stats;

    private final LevelValue recipes;

    Suit(final LevelValue recipes, final Map.Entry<Stat, Object>... statsList) {
        this.recipes = recipes;
        this.stats = new LinkedHashMap<>(statsList.length);
        Arrays.stream(statsList).forEach(entry -> this.stats.put(entry.getKey(), entry.getValue()));
        this.genericStats.forEach(entry -> this.stats.put(entry.getKey(), entry.getValue()));
    }


    @Override
    public String getImage() {
        return "/images/suit/" + name().toLowerCase() + ".png";
    }

    @Override
    public String getLocalizationKey() {
        return "loadout.equipment.name." + this.name().toLowerCase();
    }

}
