package nl.jixxed.eliteodysseymaterials.enums;

import nl.jixxed.eliteodysseymaterials.service.LocaleService;

public enum HorizonsBlueprintType {
    //inara
    ENGINEER,
    AMMO_CAPACITY,
    ARMOURED,
    BLAST_RESISTANT,
    CHARGE_ENHANCED,
    DOUBLE_SHOT,
    ENGINE_FOCUSED,
    EXPANDED_PROBE_SCANNING_RADIUS,
    HEAVY_DUTY,
    HIGH_CHARGE_CAPACITY,
    KINETIC_RESISTANT,
    LIGHTWEIGHT,
    LOW_EMISSIONS,
    OVERCHARGED,
    RAPID_CHARGE,
    REINFORCED,
    RESISTANCE_AUGMENTED,
    SHIELDED,
    SPECIALISED,
    SYSTEM_FOCUSED,
    THERMAL_RESISTANT,
    WEAPON_FOCUSED,
    INCREASED_FSD_RANGE,
    SHIELDED_FSD,
    KINETIC_RESISTANT_SHIELDS,
    ENHANCED_LOW_POWER_SHIELDS,
    LIGHTWEIGHT_HULL_REINFORCEMENT,
    KINETIC_RESISTANT_HULL_REINFORCEMENT,
    BLAST_RESISTANT_HULL_REINFORCEMENT,
    THERMAL_RESISTANT_HULL_REINFORCEMENT,
    CLEAN_DRIVE_TUNING,
    LONG_RANGE_SCANNER,
    LIGHT_WEIGHT_SCANNER,
    WIDE_ANGLE_SCANNER,
    FAST_SCANNER,
    EXPANDED_FSD_INTERDICTOR_CAPTURE_ARC,
    SHORT_RANGE_BLASTER,
    HEAVY_DUTY_HULL_REINFORCEMENT,
    DRIVE_STRENGTHENING,
    EFFICIENT_WEAPON,
    THERMAL_RESISTANT_SHIELDS,
    LIGHTWEIGHT_MOUNT,
    STURDY_MOUNT,
    LONG_RANGE_FSD_INTERDICTOR,
    REINFORCED_SHIELDS,
    FASTER_FSD_BOOT_SEQUENCE,
    LONG_RANGE_WEAPON,
    FOCUSED_WEAPON,
    RAPID_FIRE_MODIFICATION,
    OVERCHARGED_WEAPON,
    HIGH_CAPACITY_MAGAZINE,
    DIRTY_DRIVE_TUNING,
    //EXPERIMENTAL
    SYNTHESIS,
    CONCORDANT_SEQUENCE,
    REGENERATION_SEQUENCE,
    THERMAL_CONDUIT,
    THERMAL_SHOCK,
    THERMAL_VENT,
    OVERSIZED,
    STRIPPED_DOWN,
    DOUBLE_BRACED,
    FLOW_CONTROL,
    MULTI_SERVOS,
    INERTIAL_IMPACT,
    PHASING_SEQUENCE,
    SCRAMBLE_SPECTRUM,
    EMISSIVE_MUNITIONS,
    CORROSIVE_SHELL,
    INCENDIARY_ROUNDS,
    SMART_ROUNDS,
    DISPERSAL_FIELD,
    FORCE_SHELL,
    HIGH_YIELD_SHELL,
    THERMAL_CASCADE,
    DAZZLE_SHELL,
    DRAG_MUNITION,
    SCREENING_SHELL,
    OVERLOAD_MUNITIONS,
    PENETRATOR_MUNITIONS,
    FSD_INTERRUPT,
    PENETRATOR_PAYLOAD,
    REVERBERATING_CASCADE,
    RADIANT_CANISTER,
    SHIFT_LOCK_CANISTER,
    TARGET_LOCK_BREAKER,
    PLASMA_SLUG,
    SUPER_PENETRATOR,
    MONSTERED,
    LAYERED_PLATING,
    REFLECTIVE_PLATING,
    DEEP_PLATING,
    BOSS_CELLS,
    RECYCLING_CELLS,
    THERMO_BLOCK,
    BLAST_BLOCK,
    SUPER_CAPACITOR,
    MULTI_WEAVE,
    FAST_CHARGE,
    FORCE_BLOCK,
    LO_DRAW,
    MASS_MANAGER,
    THERMAL_SPREAD,
    DRIVE_DISTRIBUTORS,
    DRAG_DRIVES,
    SUPER_CONDUITS,
    CLUSTER_CAPACITOR,
    ANGLED_PLATING,
    AUTO_LOADER,
    MASS_LOCK_MUNITION,
    ION_DISRUPTOR,
    FEEDBACK_CASCADE,
    HI_CAP,
    DEEP_CHARGE,
    GUARDIAN_HYBRID_POWER_PLANT,
    GUARDIAN_FSD_BOOSTER,
    GUARDIAN_POWER_DISTRIBUTOR,
    GUARDIAN_HULL_REINFORCEMENT,
    GUARDIAN_MODULE_REINFORCEMENT,
    GUARDIAN_SHIELD_REINFORCEMENT,
    GUARDIAN_GAUSS_CANNON_FIXED_MEDIUM,
    GUARDIAN_GAUSS_CANNON_FIXED_SMALL,
    GUARDIAN_PLASMA_CHARGER_FIXED_LARGE,
    GUARDIAN_PLASMA_CHARGER_FIXED_MEDIUM,
    GUARDIAN_PLASMA_CHARGER_FIXED_SMALL,
    GUARDIAN_PLASMA_CHARGER_TURRETED_LARGE,
    GUARDIAN_PLASMA_CHARGER_TURRETED_MEDIUM,
    GUARDIAN_PLASMA_CHARGER_TURRETED_SMALL,
    GUARDIAN_SHARD_CANNON_FIXED_LARGE,
    GUARDIAN_SHARD_CANNON_FIXED_MEDIUM,
    GUARDIAN_SHARD_CANNON_FIXED_SMALL,
    GUARDIAN_SHARD_CANNON_TURRETED_LARGE,
    GUARDIAN_SHARD_CANNON_TURRETED_MEDIUM,
    GUARDIAN_SHARD_CANNON_TURRETED_SMALL,
    MODIFIED_GAUSS_CANNON_FIXED_MEDIUM,
    MODIFIED_GAUSS_CANNON_FIXED_SMALL,
    MODIFIED_SHARD_CANNON_FIXED_MEDIUM,
    MODIFIED_SHARD_CANNON_FIXED_SMALL,
    MODIFIED_PLASMA_CHARGER_FIXED_MEDIUM,
    MODIFIED_PLASMA_CHARGER_FIXED_SMALL,
    JAVELIN_FIGHTER,
    LANCE_FIGHTER,
    TRIDENT_FIGHTER,
    REMOTE_RELEASE_FLECHETTE_LAUNCHER_FIXED,
    REMOTE_RELEASE_FLECHETTE_LAUNCHER_TURRETED,
    SHOCK_CANNON_FIXED_LARGE,
    SHOCK_CANNON_FIXED_MEDIUM,
    SHOCK_CANNON_FIXED_SMALL,
    SHOCK_CANNON_GIMBALLED_LARGE,
    SHOCK_CANNON_GIMBALLED_MEDIUM,
    SHOCK_CANNON_GIMBALLED_SMALL,
    SHOCK_CANNON_TURRETED_LARGE,
    SHOCK_CANNON_TURRETED_MEDIUM,
    SHOCK_CANNON_TURRETED_SMALL,
    ENZYME_MISSILE_RACK,
    META_ALLOY_HULL_REINFORCEMENT,
    ENGINEERED_MISSILE_RACK_V1,
    MODIFIED_MINING_LASER_FIXED_SMALL,
    ENGINEERED_DETAILED_SURFACE_SCANNER_V1,
    ENGINEERED_FSD_V1,
    ENGINEERED_HEAT_SINK_LAUNCHER_V1,
    CORROSION_RESISTANT_CARGO_RACK,
    BOBBLEHEAD;

    public static HorizonsBlueprintType forName(final String name) {
        try {
            return HorizonsBlueprintType.valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException ex) {
            return null;
        }
    }


    public static HorizonsBlueprintType forInternalName(final String internalName) {
        return switch (internalName.toLowerCase()) {
            //modules
            case "misc_lightweight" -> LIGHTWEIGHT;
            case "misc_reinforced" -> REINFORCED;
            case "misc_shielded" -> SHIELDED;

            case "weapon_doubleshot" -> DOUBLE_SHOT;
            case "weapon_efficient" -> EFFICIENT_WEAPON;
            case "weapon_focused" -> FOCUSED_WEAPON;
            case "weapon_highcapacity" -> HIGH_CAPACITY_MAGAZINE;
            case "weapon_lightweight" -> LIGHTWEIGHT_MOUNT;
            case "weapon_longrange" -> LONG_RANGE_WEAPON;
            case "weapon_overcharged" -> OVERCHARGED_WEAPON;
            case "weapon_rapidfire" -> RAPID_FIRE_MODIFICATION;
            case "weapon_shortrange" -> SHORT_RANGE_BLASTER;
            case "weapon_sturdy" -> STURDY_MOUNT;

            case "sensor_fastscan" -> FAST_SCANNER;
            case "sensor_longrange" -> LONG_RANGE_SCANNER;
            case "sensor_wideangle" -> WIDE_ANGLE_SCANNER;

            case "sensor_lightweight" -> LIGHT_WEIGHT_SCANNER;
            case "sensor_expanded" -> EXPANDED_PROBE_SCANNING_RADIUS;

            case "misc_chaffcapacity" -> AMMO_CAPACITY;
            case "misc_heatsinkcapacity" -> AMMO_CAPACITY;
            case "misc_pointdefensecapacity" -> AMMO_CAPACITY;

            case "shieldbooster_explosive" -> BLAST_RESISTANT;
            case "shieldbooster_heavyduty" -> HEAVY_DUTY;
            case "shieldbooster_kinetic" -> KINETIC_RESISTANT;
            case "shieldbooster_resistive" -> RESISTANCE_AUGMENTED;
            case "shieldbooster_thermic" -> THERMAL_RESISTANT;

            case "armour_explosive" -> BLAST_RESISTANT;
            case "armour_heavyduty" -> HEAVY_DUTY;
            case "armour_kinetic" -> KINETIC_RESISTANT;
            case "armour_advanced" -> LIGHTWEIGHT;
            case "armour_thermic" -> THERMAL_RESISTANT;

            case "powerplant_armoured" -> ARMOURED;
            case "powerplant_stealth" -> LOW_EMISSIONS;
            case "powerplant_boosted" -> OVERCHARGED;

            case "engine_tuned" -> CLEAN_DRIVE_TUNING;
            case "engine_dirty" -> DIRTY_DRIVE_TUNING;
            case "engine_reinforced" -> DRIVE_STRENGTHENING;

            case "fsd_fastboot" -> FASTER_FSD_BOOT_SEQUENCE;
            case "fsd_longrange" -> INCREASED_FSD_RANGE;
            case "fsd_shielded" -> SHIELDED_FSD;

            case "powerdistributor_highfrequency" -> CHARGE_ENHANCED;
            case "powerdistributor_priorityengines" -> ENGINE_FOCUSED;
            case "powerdistributor_highcapacity" -> HIGH_CHARGE_CAPACITY;
            case "powerdistributor_shielded" -> SHIELDED;
            case "powerdistributor_prioritysystems" -> SYSTEM_FOCUSED;
            case "powerdistributor_priorityweapons" -> WEAPON_FOCUSED;

            case "fsdinterdictor_expanded" -> EXPANDED_FSD_INTERDICTOR_CAPTURE_ARC;
            case "fsdinterdictor_longrange" -> LONG_RANGE_FSD_INTERDICTOR;

            case "hullreinforcement_explosive" -> BLAST_RESISTANT_HULL_REINFORCEMENT;
            case "hullreinforcement_heavyduty" -> HEAVY_DUTY_HULL_REINFORCEMENT;
            case "hullreinforcement_kinetic" -> KINETIC_RESISTANT_HULL_REINFORCEMENT;
            case "hullreinforcement_advanced" -> LIGHTWEIGHT_HULL_REINFORCEMENT;
            case "hullreinforcement_thermic" -> THERMAL_RESISTANT_HULL_REINFORCEMENT;

            case "shieldcellbank_rapid" -> RAPID_CHARGE;
            case "shieldcellbank_specialised" -> SPECIALISED;

            case "shieldgenerator_optimised" -> ENHANCED_LOW_POWER_SHIELDS;
            case "shieldgenerator_kinetic" -> KINETIC_RESISTANT_SHIELDS;
            case "shieldgenerator_reinforced" -> REINFORCED_SHIELDS;
            case "shieldgenerator_thermic" -> THERMAL_RESISTANT_SHIELDS;
            //experimental effects
            case "special_auto_loader" -> AUTO_LOADER;
            case "special_concordant_sequence" -> CONCORDANT_SEQUENCE;
            case "special_corrosive_shell" -> CORROSIVE_SHELL;
            case "special_blinding_shell" -> DAZZLE_SHELL;
            case "special_dispersal_field" -> DISPERSAL_FIELD;
            case "special_weapon_toughened" -> DOUBLE_BRACED;
            case "special_drag_munitions" -> DRAG_MUNITION;
            case "special_emissive_munitions" -> EMISSIVE_MUNITIONS;
            case "special_feedback_cascade" -> FEEDBACK_CASCADE;
            case "special_feedback_cascade_cooled" -> FEEDBACK_CASCADE;
            case "special_weapon_efficient" -> FLOW_CONTROL;
            case "special_force_shell" -> FORCE_SHELL;
            case "special_fsd_interrupt" -> FSD_INTERRUPT;
            case "special_high_yield_shell" -> HIGH_YIELD_SHELL;
            case "special_incendiary_rounds" -> INCENDIARY_ROUNDS;
            case "special_distortion_field" -> INERTIAL_IMPACT;
            case "special_choke_canister" -> ION_DISRUPTOR;
            case "special_mass_lock" -> MASS_LOCK_MUNITION;
            case "special_weapon_rateoffire" -> MULTI_SERVOS;
            case "special_overload_munitions" -> OVERLOAD_MUNITIONS;
            case "special_weapon_damage" -> OVERSIZED;
            case "special_penetrator_munitions" -> PENETRATOR_MUNITIONS;
            case "special_deep_cut_payload" -> PENETRATOR_PAYLOAD;
            case "special_phasing_sequence" -> PHASING_SEQUENCE;
            case "special_plasma_slug" -> PLASMA_SLUG;
            case "special_plasma_slug_cooled" -> PLASMA_SLUG;
            case "special_radiant_canister" -> RADIANT_CANISTER;
            case "special_regeneration_sequence" -> REGENERATION_SEQUENCE;
            case "special_reverberating_cascade" -> REVERBERATING_CASCADE;
            case "special_scramble_spectrum" -> SCRAMBLE_SPECTRUM;
            case "special_screening_shell" -> SCREENING_SHELL;
            case "special_shiftlock_canister" -> SHIFT_LOCK_CANISTER;
            case "special_smart_rounds" -> SMART_ROUNDS;
            case "special_weapon_lightweight" -> STRIPPED_DOWN;
            case "special_super_penetrator" -> SUPER_PENETRATOR;
            case "special_super_penetrator_cooled" -> SUPER_PENETRATOR;
            case "special_lock_breaker" -> TARGET_LOCK_BREAKER;
            case "special_thermal_cascade" -> THERMAL_CASCADE;
            case "special_thermal_conduit" -> THERMAL_CONDUIT;
            case "special_thermalshock" -> THERMAL_SHOCK;
            case "special_thermal_vent" -> THERMAL_VENT;
            case "special_shieldbooster_explosive" -> BLAST_BLOCK;
            case "special_shieldbooster_toughened" -> DOUBLE_BRACED;
            case "special_shieldbooster_efficient" -> FLOW_CONTROL;
            case "special_shieldbooster_kinetic" -> FORCE_BLOCK;
            case "special_shieldbooster_chunky" -> SUPER_CAPACITOR;
            case "special_shieldbooster_thermic" -> THERMO_BLOCK;
            case "special_armour_kinetic" -> ANGLED_PLATING;
            case "special_armour_chunky" -> DEEP_PLATING;
            case "special_armour_explosive" -> LAYERED_PLATING;
            case "special_armour_thermic" -> REFLECTIVE_PLATING;
            case "special_powerplant_toughened" -> DOUBLE_BRACED;
            case "special_powerplant_highcharge" -> MONSTERED;
            case "special_powerplant_lightweight" -> STRIPPED_DOWN;
            case "special_powerplant_cooled" -> THERMAL_SPREAD;
            case "special_engine_toughened" -> DOUBLE_BRACED;
            case "special_engine_overloaded" -> DRAG_DRIVES;
            case "special_engine_haulage" -> DRIVE_DISTRIBUTORS;
            case "special_engine_lightweight" -> STRIPPED_DOWN;
            case "special_engine_cooled" -> THERMAL_SPREAD;
            case "special_fsd_fuelcapacity" -> DEEP_CHARGE;
            case "special_fsd_toughened" -> DOUBLE_BRACED;
            case "special_fsd_heavy" -> MASS_MANAGER;
            case "special_fsd_lightweight" -> STRIPPED_DOWN;
            case "special_fsd_cooled" -> THERMAL_SPREAD;
            case "special_powerdistributor_capacity" -> CLUSTER_CAPACITOR;
            case "special_powerdistributor_toughened" -> DOUBLE_BRACED;
            case "special_powerdistributor_efficient" -> FLOW_CONTROL;
            case "special_powerdistributor_lightweight" -> STRIPPED_DOWN;
            case "special_powerdistributor_fast" -> SUPER_CONDUITS;
            case "special_hullreinforcement_kinetic" -> ANGLED_PLATING;
            case "special_hullreinforcement_chunky" -> DEEP_PLATING;
            case "special_hullreinforcement_explosive" -> LAYERED_PLATING;
            case "special_hullreinforcement_thermic" -> REFLECTIVE_PLATING;
            case "special_shieldcell_oversized" -> BOSS_CELLS;
            case "special_shieldcell_toughened" -> DOUBLE_BRACED;
            case "special_shieldcell_efficient" -> FLOW_CONTROL;
            case "special_shieldcell_gradual" -> RECYCLING_CELLS;
            case "special_shieldcell_lightweight" -> STRIPPED_DOWN;
            case "special_shield_toughened" -> DOUBLE_BRACED;
            case "special_shield_regenerative" -> FAST_CHARGE;
            case "special_shield_kinetic" -> FORCE_BLOCK;
            case "special_shield_health" -> HI_CAP;
            case "special_shield_efficient" -> LO_DRAW;
            case "special_shield_resistive" -> MULTI_WEAVE;
            case "special_shield_lightweight" -> STRIPPED_DOWN;
            case "special_shield_thermic" -> THERMO_BLOCK;
            default -> throw new IllegalArgumentException("Unknown blueprint type: " + internalName);
        };

    }

    public String getLocalizationKey() {
        return "blueprint.horizons.type." + this.name().toLowerCase();
    }

    public String getDescriptionLocalizationKey() {
        return "blueprint.horizons.type.description." + this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }

}
