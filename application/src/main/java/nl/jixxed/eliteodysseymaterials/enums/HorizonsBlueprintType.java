/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.core_internals.*;
import nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint.PlasmaAccelerator;
import nl.jixxed.eliteodysseymaterials.domain.ships.hardpoint.RailGun;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.*;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.military.HullReinforcementPackage;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.military.ShieldCellBank;
import nl.jixxed.eliteodysseymaterials.domain.ships.utility.*;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

@RequiredArgsConstructor
public enum HorizonsBlueprintType {
    OUTFITTING(false),
    ENGINEER(false),
    AMMO_CAPACITY(false),
    ARMOURED(false),
    BLAST_RESISTANT(false),
    CHARGE_ENHANCED(false),
    DOUBLE_SHOT(false),
    ENGINE_FOCUSED(false),
    EXPANDED_PROBE_SCANNING_RADIUS(false),
    HEAVY_DUTY(false),
    HIGH_CHARGE_CAPACITY(false),
    KINETIC_RESISTANT(false),
    LIGHTWEIGHT(false),
    LOW_EMISSIONS(false),
    OVERCHARGED(false),
    RAPID_CHARGE(false),
    REINFORCED(false),
    RESISTANCE_AUGMENTED(false),
    SHIELDED(false),
    SPECIALISED(false),
    SYSTEM_FOCUSED(false),
    THERMAL_RESISTANT(false),
    WEAPON_FOCUSED(false),
    INCREASED_FSD_RANGE(false),
    SHIELDED_FSD(false),
    KINETIC_RESISTANT_SHIELDS(false),
    ENHANCED_LOW_POWER_SHIELDS(false),
    LIGHTWEIGHT_HULL_REINFORCEMENT(false),
    KINETIC_RESISTANT_HULL_REINFORCEMENT(false),
    BLAST_RESISTANT_HULL_REINFORCEMENT(false),
    THERMAL_RESISTANT_HULL_REINFORCEMENT(false),
    CLEAN_DRIVE_TUNING(false),
    LONG_RANGE_SCANNER(false),
    LIGHT_WEIGHT_SCANNER(false),
    WIDE_ANGLE_SCANNER(false),
    FAST_SCANNER(false),
    EXPANDED_FSD_INTERDICTOR_CAPTURE_ARC(false),
    SHORT_RANGE_BLASTER(false),
    HEAVY_DUTY_HULL_REINFORCEMENT(false),
    DRIVE_STRENGTHENING(false),
    EFFICIENT_WEAPON(false),
    THERMAL_RESISTANT_SHIELDS(false),
    LIGHTWEIGHT_MOUNT(false),
    STURDY_MOUNT(false),
    LONG_RANGE_FSD_INTERDICTOR(false),
    REINFORCED_SHIELDS(false),
    FASTER_FSD_BOOT_SEQUENCE(false),
    LONG_RANGE_WEAPON(false),
    FOCUSED_WEAPON(false),
    RAPID_FIRE_MODIFICATION(false),
    OVERCHARGED_WEAPON(false),
    HIGH_CAPACITY_MAGAZINE(false),
    DIRTY_DRIVE_TUNING(false),
    //EXPERIMENTAL
    SYNTHESIS(false),
    CONCORDANT_SEQUENCE(true),
    REGENERATION_SEQUENCE(true),
    THERMAL_CONDUIT(true),
    THERMAL_SHOCK(true),
    THERMAL_VENT(true),
    OVERSIZED(true),
    STRIPPED_DOWN(true),
    DOUBLE_BRACED(true),
    FLOW_CONTROL(true),
    MULTI_SERVOS(true),
    INERTIAL_IMPACT(true),
    PHASING_SEQUENCE(true),
    SCRAMBLE_SPECTRUM(true),
    EMISSIVE_MUNITIONS(true),
    CORROSIVE_SHELL(true),
    INCENDIARY_ROUNDS(true),
    SMART_ROUNDS(true),
    DISPERSAL_FIELD(true),
    FORCE_SHELL(true),
    HIGH_YIELD_SHELL(true),
    THERMAL_CASCADE(true),
    DAZZLE_SHELL(true),
    DRAG_MUNITION(true),
    SCREENING_SHELL(true),
    OVERLOAD_MUNITIONS(true),
    PENETRATOR_MUNITIONS(true),
    FSD_INTERRUPT(true),
    PENETRATOR_PAYLOAD(true),
    REVERBERATING_CASCADE(true),
    RADIANT_CANISTER(true),
    SHIFT_LOCK_CANISTER(true),
    TARGET_LOCK_BREAKER(true),
    PLASMA_SLUG(true),
    SUPER_PENETRATOR(true),
    MONSTERED(true),
    LAYERED_PLATING(true),
    REFLECTIVE_PLATING(true),
    DEEP_PLATING(true),
    BOSS_CELLS(true),
    RECYCLING_CELLS(true),
    THERMO_BLOCK(true),
    BLAST_BLOCK(true),
    SUPER_CAPACITOR(true),
    MULTI_WEAVE(true),
    FAST_CHARGE(true),
    FORCE_BLOCK(true),
    LO_DRAW(true),
    MASS_MANAGER(true),
    THERMAL_SPREAD(true),
    DRIVE_DISTRIBUTORS(true),
    DRAG_DRIVES(true),
    SUPER_CONDUITS(true),
    CLUSTER_CAPACITOR(true),
    ANGLED_PLATING(true),
    AUTO_LOADER(true),
    MASS_LOCK_MUNITION(true),
    ION_DISRUPTOR(true),
    FEEDBACK_CASCADE(true),
    HI_CAP(true),
    DEEP_CHARGE(true),
    GUARDIAN_HYBRID_POWER_PLANT(false),
    GUARDIAN_FSD_BOOSTER(false),
    GUARDIAN_POWER_DISTRIBUTOR(false),
    GUARDIAN_HULL_REINFORCEMENT(false),
    GUARDIAN_MODULE_REINFORCEMENT(false),
    GUARDIAN_SHIELD_REINFORCEMENT(false),
    GUARDIAN_GAUSS_CANNON_FIXED_MEDIUM(false),
    GUARDIAN_GAUSS_CANNON_FIXED_SMALL(false),
    GUARDIAN_PLASMA_CHARGER_FIXED_LARGE(false),
    GUARDIAN_PLASMA_CHARGER_FIXED_MEDIUM(false),
    GUARDIAN_PLASMA_CHARGER_FIXED_SMALL(false),
    GUARDIAN_PLASMA_CHARGER_TURRETED_LARGE(false),
    GUARDIAN_PLASMA_CHARGER_TURRETED_MEDIUM(false),
    GUARDIAN_PLASMA_CHARGER_TURRETED_SMALL(false),
    GUARDIAN_SHARD_CANNON_FIXED_LARGE(false),
    GUARDIAN_SHARD_CANNON_FIXED_MEDIUM(false),
    GUARDIAN_SHARD_CANNON_FIXED_SMALL(false),
    GUARDIAN_SHARD_CANNON_TURRETED_LARGE(false),
    GUARDIAN_SHARD_CANNON_TURRETED_MEDIUM(false),
    GUARDIAN_SHARD_CANNON_TURRETED_SMALL(false),
    MODIFIED_GAUSS_CANNON_FIXED_MEDIUM(false),
    MODIFIED_GAUSS_CANNON_FIXED_SMALL(false),
    MODIFIED_SHARD_CANNON_FIXED_MEDIUM(false),
    MODIFIED_SHARD_CANNON_FIXED_SMALL(false),
    MODIFIED_PLASMA_CHARGER_FIXED_MEDIUM(false),
    MODIFIED_PLASMA_CHARGER_FIXED_SMALL(false),
    JAVELIN_FIGHTER(false),
    LANCE_FIGHTER(false),
    TRIDENT_FIGHTER(false),
    REMOTE_RELEASE_FLECHETTE_LAUNCHER_FIXED(false),
    REMOTE_RELEASE_FLECHETTE_LAUNCHER_TURRETED(false),
    SHOCK_CANNON_FIXED_LARGE(false),
    SHOCK_CANNON_FIXED_MEDIUM(false),
    SHOCK_CANNON_FIXED_SMALL(false),
    SHOCK_CANNON_GIMBALLED_LARGE(false),
    SHOCK_CANNON_GIMBALLED_MEDIUM(false),
    SHOCK_CANNON_GIMBALLED_SMALL(false),
    SHOCK_CANNON_TURRETED_LARGE(false),
    SHOCK_CANNON_TURRETED_MEDIUM(false),
    SHOCK_CANNON_TURRETED_SMALL(false),
    ENZYME_MISSILE_RACK(false),
    META_ALLOY_HULL_REINFORCEMENT(false),
    ENGINEERED_MISSILE_RACK_V1(false),
    MODIFIED_MINING_LASER_FIXED_SMALL(false),
    ENGINEERED_DETAILED_SURFACE_SCANNER_V1(false),
    ENGINEERED_FSD_V1(false),
    ENGINEERED_FSD_SCO_A2_V1(false),
    ENGINEERED_FSD_SCO_A3_V1(false),
    ENGINEERED_FSD_SCO_A4_V1(false),
    ENGINEERED_FSD_SCO_A5_V1(false),
    ENGINEERED_FSD_SCO_A6_V1(false),
    ENGINEERED_FSD_SCO_A7_V1(false),
    SIRIUS_HEAT_SINK_LAUNCHER(false),
    CORROSION_RESISTANT_CARGO_RACK(false),
    BOBBLEHEAD(false),
    E2_SIRIUS_CORPORATION_AX_MISSILE_RACK(false),
    C3_SIRIUS_CORPORATION_AX_MISSILE_RACK(false),
    E2_AZIMUTH_ENHANCED_AX_MULTICANNON(false),
    C3_AZIMUTH_ENHANCED_AX_MULTICANNON(false),
    CAUSTIC_SINK_LAUNCHER(false),
    THARGOID_PULSE_NEUTRALISER(false),
    DECORATIVE_GREEN(false, true),
    DECORATIVE_RED(false, true),
    DECORATIVE_YELLOW(false, true),
    DECORATIVE_PINK(false, true),
    HIGH_CAPACITY_MAGAZINE_RAPID_FIRE_MODIFICATION(false, true),
    FAST_SCANNER_LONG_RANGE_SCANNER(false, true),
    LONG_RANGE_WEAPON_INCENDIARY_ROUNDS(false, true),
    LONG_RANGE_WEAPON_INCENDIARY_ROUNDS_ARX(false, true),
    THERMAL_RESISTANT_SHIELDS_KINETIC_RESISTANT_SHIELDS(false, true),
    AMMO_CAPACITY_X2(false, true),
    LIGHTWEIGHT_FOCUSED(false, true),
    LONG_RANGE_WEAPON_HIGH_CAPACITY_MAGAZINE_FEEDBACK_CASCADE(false, true),
    HIGH_CAPACITY_MAGAZINE_SUPER_PENETRATOR(false, true),
    OVERCHARGED_WEAPON_AUTO_LOADER(false, true),
    HIGH_CAPACITY_MAGAZINE_INCREASED_DAMAGE(false, true),
    RAPID_FIRE_MODIFICATION_PHASING_SEQUENCE(false, true),
    ARMOURED_OVERCHARGED(false, true),
    OVERCHARGED_OVERCHARGED(false, true),
    OVERCHARGED_WEAPON_FOCUSED_WEAPON(false, true),
    LONG_RANGE_WEAPON_FOCUSED_WEAPON_PENETRATOR_MUNITIONS(false, true),
    LONG_RANGE_WEAPON_FOCUSED_WEAPON_PENETRATOR_MUNITIONS_GOD(false, true),
    HIGH_CAPACITY_MAGAZINE_THERMAL_CASCADE(false, true),
    EXPANDED_PROBE_SCANNING_RADIUS_X2(false, true),
    INCREASED_FSD_RANGE_FASTER_FSD_BOOT_SEQUENCE(false, true),
    ANTI_GUARDIAN_ZONE_RESISTANCE(false, false),
    SYSTEM_ENGINE_FOCUSED(false, true),
    HIGH_CAPACITY_MAGAZINE_STURDY_FSD_INTERRUPT(false, true),
    DOUBLE_SHOT_HIGH_CAPACITY_MAGAZINE_SCREENING_SHELL(false, true),
    DOUBLE_SHOT_HIGH_CAPACITY_MAGAZINE_SCREENING_SHELL_B(false, true),
    INCREASED_CARGO_CAPACITY(false, true),
    MERC_BALANCED_POWER_DISTRIBUTOR(false, false, true, true),
    MERC_SUPPORT_FOCUSED_POWER_DISTRIBUTOR_3D(false, false, true, true),
    MERC_SUPPORT_FOCUSED_POWER_DISTRIBUTOR_3A(false, false, true, true),
    MERC_SUPPORT_FOCUSED_POWER_DISTRIBUTOR_4D(false, false, true, true),
    MERC_SUPPORT_FOCUSED_POWER_DISTRIBUTOR_4A(false, false, true, true),
    MERC_SUPPORT_FOCUSED_POWER_DISTRIBUTOR_6A(false, false, true, true),
    MERC_LONG_RANGE_DETAILED_SURFACE_SCANNER(false, false, true, true),
    MERC_SCOOP_RATE_ENHANCED_FUEL_SCOOP(false, false, true, false),
    MERC_PLASMA_CONVERSION_BEAM_LASER(false, false, true, false),
    MERC_PLASMA_CONVERSION_PULSE_LASER(false, false, true, false),
    MERC_PLASMA_CONVERSION_BURST_LASER(false, false, true, false),
    MERC_HEAVY_DUTY_MODULE_REINFORCEMENT_PACKAGE(false, false, true, true),
    MERC_EXTENDED_CARGO_RACK_5E(false, false, true, true),
    MERC_EXTENDED_CARGO_RACK_6E(false, false, true, true),
    MERC_DOUBLE_SCREAMING_FRAGMENT_CANNON_1EG(false, false, true, true),
    MERC_DOUBLE_SCREAMING_FRAGMENT_CANNON_3CG(false, false, true, true),
    MERC_RAPID_PHASE_MULTI_CANNON(false, false, true, true),
    MERC_FAR_REACHING_ABRASION_BLASTER(false, false, true, true),
    MERC_HIGH_YIELD_ENZYME_MISSILE_RACK(false, false, true, true),
    MERC_LONG_RANGE_MINING_LASER(false, false, true, true),
    MERC_DRAG_SEEKER_MISSILE_RACK(false, false, true, true),
    MERC_LIGHTWEIGHT_THERMAL_SEEKER_MISSILE_RACK(false, false, true, true),
    MERC_LOCKDOWN_SEEKER_MISSILE_RACK_2B(false, false, true, true),
    MERC_LOCKDOWN_SEEKER_MISSILE_RACK_3A(false, false, true, true),
    MERC_ENDURING_FEEDBACK_RAIL_GUN(false, false, true, true),
    NONE(false, false);
    @Getter
    private final boolean experimental;
    @Getter
    private final boolean preEngineered;
    @Getter
    private final boolean merc;
    @Getter
    private final boolean mercOnly;

    HorizonsBlueprintType(boolean experimental) {
        this(experimental, false, false, false);
    }

    HorizonsBlueprintType(boolean experimental, boolean preEngineered) {
        this(experimental, preEngineered, false, false);
    }

    public static HorizonsBlueprintType forName(final String name) {
        try {
            return HorizonsBlueprintType.valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException ex) {
            return null;
        }
    }
//    public static HorizonsBlueprintType forInternalNameMerc(final String internalName) {
//        return switch (internalName.toLowerCase()) {
//            case "cargorack_increasedcapacity" -> INCREASED_CARGO_CAPACITY;
//        };
//    }

    public static HorizonsBlueprintType forInternalName(final String internalName) {
        return switch (internalName.toLowerCase()) {
            case "cargorack_increasedcapacity" -> INCREASED_CARGO_CAPACITY;
            //modules
            case "misc_lightweight",
                 "chafflauncher_lightweight",
                 "ecm_lightweight",
                 "heatsinklauncher_lightweight",
                 "killwarrantscanner_lightweight",
                 "cargoscanner_lightweight",
                 "pointdefence_lightweight",
                 "wakescanner_lightweight",
                 "lifesupport_lightweight",
                 "collectionlimpet_lightweight",
                 "fueltransferlimpet_lightweight",
                 "hatchbreakerlimpet_lightweight",
                 "prospectinglimpet_lightweight", "armour_advanced" -> LIGHTWEIGHT;

            case "misc_reinforced",
                 "chafflauncher_reinforced",
                 "ecm_reinforced",
                 "heatsinklauncher_reinforced",
                 "killwarrantscanner_reinforced",
                 "cargoscanner_reinforced",
                 "pointdefence_reinforced",
                 "wakescanner_reinforced",
                 "lifesupport_reinforced",
                 "collectionlimpet_reinforced",
                 "fueltransferlimpet_reinforced",
                 "hatchbreakerlimpet_reinforced",
                 "prospectinglimpet_reinforced" -> REINFORCED;

            case "misc_shielded",
                 "chafflauncher_shielded",
                 "ecm_shielded",
                 "heatsinklauncher_shielded",
                 "killwarrantscanner_shielded",
                 "cargoscanner_shielded",
                 "pointdefence_shielded",
                 "wakescanner_shielded",
                 "lifesupport_shielded",
                 "collectionlimpet_shielded",
                 "fueltransferlimpet_shielded",
                 "hatchbreakerlimpet_shielded",
                 "prospectinglimpet_shielded",
                 "afm_shielded",
                 "fuelscoop_shielded",
                 "refineries_shielded", "powerdistributor_shielded" -> SHIELDED;

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

            case "sensor_fastscan",
                 "sensor_killwarrantscanner_fastscan",
                 "killwarrantscanner_fastscan",
                 "sensor_cargoscanner_fastscan",
                 "sensor_wakescanner_fastscan",
                 "sensor_sensor_fastscan",
                 "sensor_surfacescanner_fastscan" -> FAST_SCANNER;

            case "sensor_longrange",
                 "sensor_killwarrantscanner_longrange",
                 "killwarrantscanner_longrange",
                 "sensor_cargoscanner_longrange",
                 "sensor_wakescanner_longrange",
                 "sensor_sensor_longrange",
                 "sensor_surfacescanner_longrange" -> LONG_RANGE_SCANNER;

            case "sensor_wideangle",
                 "sensor_killwarrantscanner_wideangle",
                 "killwarrantscanner_wideangle",
                 "sensor_cargoscanner_wideangle",
                 "sensor_wakescanner_wideangle",
                 "sensor_sensor_wideangle",
                 "sensor_surfacescanner_wideangle" -> WIDE_ANGLE_SCANNER;

            case "sensor_lightweight",
                 "sensor_sensor_lightweight" -> LIGHT_WEIGHT_SCANNER;

            case "sensor_expanded" -> EXPANDED_PROBE_SCANNING_RADIUS;

            case "misc_chaffcapacity",
                 "chafflauncher_chaffcapacity",//legacy
                 "misc_heatsinkcapacity",
                 "heatsinklauncher_heatsinkcapacity",//legacy
                 "misc_pointdefensecapacity",
                 "pointdefence_pointdefensecapacity" /*legacy*/ -> AMMO_CAPACITY;

            case "shieldbooster_explosive", "armour_explosive" -> BLAST_RESISTANT;
            case "shieldbooster_heavyduty", "armour_heavyduty" -> HEAVY_DUTY;
            case "shieldbooster_kinetic", "armour_kinetic" -> KINETIC_RESISTANT;
            case "shieldbooster_resistive" -> RESISTANCE_AUGMENTED;
            case "shieldbooster_thermic", "armour_thermic" -> THERMAL_RESISTANT;

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
            case "decorative_green" -> DECORATIVE_GREEN;
            case "decorative_red" -> DECORATIVE_RED;
            case "decorative_pink" -> DECORATIVE_PINK;
            case "decorative_yellow" -> DECORATIVE_YELLOW;
            case "guardianweapon_sturdy",//has been replaced on weapons by guardianmodule_sturdy
                 "guardianmodule_sturdy" -> ANTI_GUARDIAN_ZONE_RESISTANCE;
            //experimental effects
            case "special_auto_loader" -> AUTO_LOADER;
            case "special_concordant_sequence" -> CONCORDANT_SEQUENCE;
            case "special_corrosive_shell" -> CORROSIVE_SHELL;
            case "special_blinding_shell" -> DAZZLE_SHELL;
            case "special_dispersal_field" -> DISPERSAL_FIELD;
            case "special_weapon_toughened", "special_shieldbooster_toughened", "special_powerplant_toughened",
                 "special_engine_toughened", "special_fsd_toughened", "special_powerdistributor_toughened",
                 "special_shieldcell_toughened", "special_shield_toughened" -> DOUBLE_BRACED;
            case "special_drag_munitions" -> DRAG_MUNITION;
            case "special_emissive_munitions" -> EMISSIVE_MUNITIONS;
            case "special_feedback_cascade", "special_feedback_cascade_cooled" -> FEEDBACK_CASCADE;
            case "special_weapon_efficient", "special_shieldbooster_efficient", "special_powerdistributor_efficient",
                 "special_shieldcell_efficient" -> FLOW_CONTROL;
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
            case "special_plasma_slug", "special_plasma_slug_cooled" -> PLASMA_SLUG;
            case "special_radiant_canister" -> RADIANT_CANISTER;
            case "special_regeneration_sequence" -> REGENERATION_SEQUENCE;
            case "special_reverberating_cascade" -> REVERBERATING_CASCADE;
            case "special_scramble_spectrum" -> SCRAMBLE_SPECTRUM;
            case "special_screening_shell" -> SCREENING_SHELL;
            case "special_shiftlock_canister" -> SHIFT_LOCK_CANISTER;
            case "special_smart_rounds" -> SMART_ROUNDS;
            case "special_weapon_lightweight", "special_powerplant_lightweight", "special_engine_lightweight",
                 "special_fsd_lightweight", "special_powerdistributor_lightweight", "special_shieldcell_lightweight",
                 "special_shield_lightweight" -> STRIPPED_DOWN;
            case "special_super_penetrator", "special_super_penetrator_cooled" -> SUPER_PENETRATOR;
            case "special_lock_breaker" -> TARGET_LOCK_BREAKER;
            case "special_thermal_cascade" -> THERMAL_CASCADE;
            case "special_thermal_conduit" -> THERMAL_CONDUIT;
            case "special_thermalshock" -> THERMAL_SHOCK;
            case "special_thermal_vent" -> THERMAL_VENT;
            case "special_shieldbooster_explosive" -> BLAST_BLOCK;
            case "special_shieldbooster_kinetic", "special_shield_kinetic" -> FORCE_BLOCK;
            case "special_shieldbooster_chunky" -> SUPER_CAPACITOR;
            case "special_shieldbooster_thermic", "special_shield_thermic" -> THERMO_BLOCK;
            case "special_armour_kinetic", "special_hullreinforcement_kinetic" -> ANGLED_PLATING;
            case "special_armour_chunky", "special_hullreinforcement_chunky" -> DEEP_PLATING;
            case "special_armour_explosive", "special_hullreinforcement_explosive" -> LAYERED_PLATING;
            case "special_armour_thermic", "special_hullreinforcement_thermic" -> REFLECTIVE_PLATING;
            case "special_powerplant_highcharge" -> MONSTERED;
            case "special_powerplant_cooled", "special_engine_cooled", "special_fsd_cooled" -> THERMAL_SPREAD;
            case "special_engine_overloaded" -> DRAG_DRIVES;
            case "special_engine_haulage" -> DRIVE_DISTRIBUTORS;
            case "special_fsd_fuelcapacity" -> DEEP_CHARGE;
            case "special_fsd_heavy" -> MASS_MANAGER;
            case "special_powerdistributor_capacity" -> CLUSTER_CAPACITOR;
            case "special_powerdistributor_fast" -> SUPER_CONDUITS;
            case "special_shieldcell_oversized" -> BOSS_CELLS;
            case "special_shieldcell_gradual" -> RECYCLING_CELLS;
            case "special_shield_regenerative" -> FAST_CHARGE;
            case "special_shield_health" -> HI_CAP;
            case "special_shield_efficient" -> LO_DRAW;
            case "special_shield_resistive" -> MULTI_WEAVE;
            case "detailedsurfacescanner_longrange" -> MERC_LONG_RANGE_DETAILED_SURFACE_SCANNER;
            case "burstlaser_thermalplasmaconversion" -> MERC_PLASMA_CONVERSION_BURST_LASER;
            case "beamlaser_thermalplasmaconversion" -> MERC_PLASMA_CONVERSION_BEAM_LASER;
            case "pulselaser_thermalplasmaconversion" -> MERC_PLASMA_CONVERSION_PULSE_LASER;
            case "fuelscoop_efficiency" -> MERC_SCOOP_RATE_ENHANCED_FUEL_SCOOP;
            case "modulereinforcement_heavyduty" -> MERC_HEAVY_DUTY_MODULE_REINFORCEMENT_PACKAGE;
            case "cargoracks5c1_extended" -> MERC_EXTENDED_CARGO_RACK_5E;
            case "cargoracks6c1_extended" -> MERC_EXTENDED_CARGO_RACK_6E;
            case "fragmentcannonsmall_doublescreaming" -> MERC_DOUBLE_SCREAMING_FRAGMENT_CANNON_1EG;
            case "fragmentcannonlarge_doublescreaming" -> MERC_DOUBLE_SCREAMING_FRAGMENT_CANNON_3CG;
            case "multicannon_rapid" -> MERC_RAPID_PHASE_MULTI_CANNON;
            case "powerdistributor_balanced" -> MERC_BALANCED_POWER_DISTRIBUTOR;
            case "powerdistributors3c2_supportfocused" -> MERC_SUPPORT_FOCUSED_POWER_DISTRIBUTOR_3D;
            case "powerdistributors3c5_supportfocused" -> MERC_SUPPORT_FOCUSED_POWER_DISTRIBUTOR_3A;
            case "powerdistributors4c2_supportfocused" -> MERC_SUPPORT_FOCUSED_POWER_DISTRIBUTOR_4D;
            case "powerdistributors4c5_supportfocused" -> MERC_SUPPORT_FOCUSED_POWER_DISTRIBUTOR_4A;
            case "powerdistributors6c5_supportfocused" -> MERC_SUPPORT_FOCUSED_POWER_DISTRIBUTOR_6A;
            case "abrasionblaster_farreaching" -> MERC_FAR_REACHING_ABRASION_BLASTER;
            case "enzymemissilerack_highyield" -> MERC_HIGH_YIELD_ENZYME_MISSILE_RACK;
            case "mininglaser_longrange" -> MERC_LONG_RANGE_MINING_LASER;
            case "seekermissilerack_drag" -> MERC_DRAG_SEEKER_MISSILE_RACK;
            case "seekermissilerack_lightweightthermal" -> MERC_LIGHTWEIGHT_THERMAL_SEEKER_MISSILE_RACK;
            case "seekermissilerackmedium_lockdown" -> MERC_LOCKDOWN_SEEKER_MISSILE_RACK_2B;
            case "seekermissileracklarge_lockdown" -> MERC_LOCKDOWN_SEEKER_MISSILE_RACK_3A;
            case "railgun_longshot" -> MERC_ENDURING_FEEDBACK_RAIL_GUN;
            default -> throw new IllegalArgumentException("Unknown blueprint type: " + internalName);
        };

    }

    @JsonCreator
    public static HorizonsBlueprintType fromString(String value) {
        if (value == null) {
            return null;
        }
        return switch (value) {
            case "HIGH_CAPACITY_MAGAZINE_SUPER_PENETRATOR_FEEDBACK_CASCADE" ->
                    HIGH_CAPACITY_MAGAZINE_SUPER_PENETRATOR; // HIGH_CAPACITY_MAGAZINE_SUPER_PENETRATOR_FEEDBACK_CASCADE renamed to HIGH_CAPACITY_MAGAZINE_SUPER_PENETRATOR
            case "MERC_SUPPORT_FOCUSED_POWER_DISTRIBUTOR" -> MERC_SUPPORT_FOCUSED_POWER_DISTRIBUTOR_3A;
            case "MERC_DOUBLE_SCREAMING_FRAGMENT_CANNON" -> MERC_DOUBLE_SCREAMING_FRAGMENT_CANNON_1EG;
            case "MERC_LOCKDOWN_SEEKER_MISSILE_RACK" -> MERC_LOCKDOWN_SEEKER_MISSILE_RACK_2B;
            case "MERC_EXTENDED_CARGO_RACK" -> MERC_EXTENDED_CARGO_RACK_5E;
            default -> HorizonsBlueprintType.valueOf(value);
        };
    }

    public String getLocalizationKey() {
        return "blueprint.horizons.type." + this.name().toLowerCase();
    }

    public String getLocalizationKey(boolean shortFormat) {
        return shortFormat && LocaleService.hasKey("blueprint.horizons.type.short." + this.name().toLowerCase()) ? "blueprint.horizons.type.short." + this.name().toLowerCase() : getLocalizationKey();
    }

    public String getDescriptionLocalizationKey() {
        return "blueprint.horizons.type.description." + this.name().toLowerCase();
    }

    public String getDescriptionLocalizationKey(String module) {
        return LocaleService.hasKey("blueprint.horizons.type.description." + module + "." + this.name().toLowerCase()) ? "blueprint.horizons.type.description." + module + "." + this.name().toLowerCase() : getDescriptionLocalizationKey();
    }

    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }

    public String getJournalName(ShipModule module) {
        return switch (this) {
            case INCREASED_FSD_RANGE_FASTER_FSD_BOOT_SEQUENCE -> "FSD_LongRange";
            case LONG_RANGE_WEAPON_INCENDIARY_ROUNDS,
                 LONG_RANGE_WEAPON_INCENDIARY_ROUNDS_ARX, LONG_RANGE_WEAPON_FOCUSED_WEAPON_PENETRATOR_MUNITIONS,
                 LONG_RANGE_WEAPON_FOCUSED_WEAPON_PENETRATOR_MUNITIONS_GOD, LONG_RANGE_WEAPON -> "weapon_longrange";
            case LONG_RANGE_WEAPON_HIGH_CAPACITY_MAGAZINE_FEEDBACK_CASCADE, HIGH_CAPACITY_MAGAZINE_SUPER_PENETRATOR,
                 HIGH_CAPACITY_MAGAZINE_INCREASED_DAMAGE,
                 HIGH_CAPACITY_MAGAZINE_STURDY_FSD_INTERRUPT, HIGH_CAPACITY_MAGAZINE_THERMAL_CASCADE ->
                    "Weapon_HighCapacity";
            case OVERCHARGED_WEAPON_AUTO_LOADER, OVERCHARGED_WEAPON_FOCUSED_WEAPON -> "Weapon_Overcharged";
            case OVERCHARGED_OVERCHARGED -> "powerplant_boosted";
            case ARMOURED_OVERCHARGED -> "PowerPlant_Armoured";
            case RAPID_FIRE_MODIFICATION_PHASING_SEQUENCE -> "Weapon_RapidFire";
            case THERMAL_RESISTANT_SHIELDS_KINETIC_RESISTANT_SHIELDS -> "ShieldGenerator_Kinetic";
            case FAST_SCANNER_LONG_RANGE_SCANNER -> "sensor_fastscan";
            case AMMO_CAPACITY_X2 -> "Misc_HeatSinkCapacity";
            case LIGHTWEIGHT_FOCUSED -> "Misc_LightWeight";
            case DOUBLE_SHOT_HIGH_CAPACITY_MAGAZINE_SCREENING_SHELL,
                 DOUBLE_SHOT_HIGH_CAPACITY_MAGAZINE_SCREENING_SHELL_B -> "weapon_doubleshot";
            case EXPANDED_PROBE_SCANNING_RADIUS_X2 -> "sensor_expanded";
            case SYSTEM_ENGINE_FOCUSED -> "powerdistributor_priorityengines";
            case HIGH_CAPACITY_MAGAZINE_RAPID_FIRE_MODIFICATION -> "Weapon_HighCapacity";
            case LIGHTWEIGHT -> resolveLightWeight(module);
            case REINFORCED -> "misc_reinforced";
            case AMMO_CAPACITY -> resolveAmmoCapacity(module);
            case DOUBLE_SHOT -> "weapon_doubleshot";
            case EFFICIENT_WEAPON -> "weapon_efficient";
            case FOCUSED_WEAPON -> "weapon_focused";
            case HIGH_CAPACITY_MAGAZINE -> "weapon_highcapacity";
            case LIGHTWEIGHT_MOUNT -> "weapon_lightweight";
            case OVERCHARGED_WEAPON -> "weapon_overcharged";
            case RAPID_FIRE_MODIFICATION -> "weapon_rapidfire";
            case SHORT_RANGE_BLASTER -> "weapon_shortrange";
            case STURDY_MOUNT -> "weapon_sturdy";
            case FAST_SCANNER -> "sensor_fastscan";//+
            case LONG_RANGE_SCANNER -> resolveLongRangeScanner(module);//sensors+
            case WIDE_ANGLE_SCANNER -> resolveWideAngleScanner(module);//sensors+
            case LIGHT_WEIGHT_SCANNER -> "sensor_lightweight";//sensors
            case EXPANDED_PROBE_SCANNING_RADIUS -> "sensor_expanded";
            case BLAST_RESISTANT -> resolveBlastResistant(module);
            case HEAVY_DUTY -> resolveHeavyDuty(module);
            case KINETIC_RESISTANT -> resolveKineticResistant(module);
            case RESISTANCE_AUGMENTED -> "shieldbooster_resistive";
            case THERMAL_RESISTANT -> resolveThermalResistant(module);
            case ARMOURED -> "powerplant_armoured";
            case LOW_EMISSIONS -> "powerplant_stealth";
            case OVERCHARGED -> "powerplant_boosted";
            case CLEAN_DRIVE_TUNING -> "engine_tuned";
            case DIRTY_DRIVE_TUNING -> "engine_dirty";
            case DRIVE_STRENGTHENING -> "engine_reinforced";
            case FASTER_FSD_BOOT_SEQUENCE -> "fsd_fastboot";
            case INCREASED_FSD_RANGE -> "fsd_longrange";
            case SHIELDED_FSD -> "fsd_shielded";
            case CHARGE_ENHANCED -> "powerdistributor_highfrequency";
            case ENGINE_FOCUSED -> "powerdistributor_priorityengines";
            case HIGH_CHARGE_CAPACITY -> "powerdistributor_highcapacity";
            case SYSTEM_FOCUSED -> "powerdistributor_prioritysystems";
            case WEAPON_FOCUSED -> "powerdistributor_priorityweapons";
            case EXPANDED_FSD_INTERDICTOR_CAPTURE_ARC -> "fsdinterdictor_expanded";
            case LONG_RANGE_FSD_INTERDICTOR -> "fsdinterdictor_longrange";
            case BLAST_RESISTANT_HULL_REINFORCEMENT -> "hullreinforcement_explosive";
            case HEAVY_DUTY_HULL_REINFORCEMENT -> "hullreinforcement_heavyduty";
            case KINETIC_RESISTANT_HULL_REINFORCEMENT -> "hullreinforcement_kinetic";
            case LIGHTWEIGHT_HULL_REINFORCEMENT -> "hullreinforcement_advanced";
            case THERMAL_RESISTANT_HULL_REINFORCEMENT -> "hullreinforcement_thermic";
            case RAPID_CHARGE -> "shieldcellbank_rapid";
            case SPECIALISED -> "shieldcellbank_specialised";
            case ENHANCED_LOW_POWER_SHIELDS -> "shieldgenerator_optimised";
            case KINETIC_RESISTANT_SHIELDS -> "shieldgenerator_kinetic";
            case REINFORCED_SHIELDS -> "shieldgenerator_reinforced";
            case THERMAL_RESISTANT_SHIELDS -> "shieldgenerator_thermic";
            case INCREASED_CARGO_CAPACITY -> "cargorack_increasedcapacity";
            case DECORATIVE_GREEN -> "decorative_green";
            case DECORATIVE_RED -> "decorative_red";
            case DECORATIVE_PINK -> "decorative_pink";
            case DECORATIVE_YELLOW -> "decorative_yellow";
            case DOUBLE_BRACED -> resolveSpecialBraced(module);
            case STRIPPED_DOWN -> resolveSpecialStripped(module);
            case FLOW_CONTROL -> resolveSpecialFlow(module);
            case THERMAL_SPREAD -> resolveSpecialThermalSpread(module);
            case SHIELDED -> resolveShielded(module);
            case AUTO_LOADER -> "special_auto_loader";
            case CONCORDANT_SEQUENCE -> "special_concordant_sequence";
            case CORROSIVE_SHELL -> "special_corrosive_shell";
            case DAZZLE_SHELL -> "special_blinding_shell";
            case DISPERSAL_FIELD -> "special_dispersal_field";
            case DRAG_MUNITION -> "special_drag_munitions";
            case EMISSIVE_MUNITIONS -> "special_emissive_munitions";
            case FEEDBACK_CASCADE -> resolveFeedbackCascade(module);
            case FORCE_SHELL -> "special_force_shell";
            case OVERSIZED -> "special_weapon_damage";
            case FSD_INTERRUPT -> "special_fsd_interrupt";
            case HIGH_YIELD_SHELL -> "special_high_yield_shell";
            case INCENDIARY_ROUNDS -> "special_incendiary_rounds";
            case INERTIAL_IMPACT -> "special_distortion_field";
            case ION_DISRUPTOR -> "special_choke_canister";
            case MASS_LOCK_MUNITION -> "special_mass_lock";
            case MULTI_SERVOS -> "special_weapon_rateoffire";
            case OVERLOAD_MUNITIONS -> "special_overload_munitions";
            case PENETRATOR_MUNITIONS -> "special_penetrator_munitions";
            case PENETRATOR_PAYLOAD -> "special_deep_cut_payload";
            case PHASING_SEQUENCE -> "special_phasing_sequence";
            case PLASMA_SLUG -> resolvePlasmaSlug(module);
            case RADIANT_CANISTER -> "special_radiant_canister";
            case REGENERATION_SEQUENCE -> "special_regeneration_sequence";
            case REVERBERATING_CASCADE -> "special_reverberating_cascade";
            case SCRAMBLE_SPECTRUM -> "special_scramble_spectrum";
            case SCREENING_SHELL -> "special_screening_shell";
            case SHIFT_LOCK_CANISTER -> "special_shiftlock_canister";
            case SMART_ROUNDS -> "special_smart_rounds";
            case SUPER_PENETRATOR -> resolveSuperPenetrator(module);
            case TARGET_LOCK_BREAKER -> "special_lock_breaker";
            case THERMAL_CASCADE -> "special_thermal_cascade";
            case THERMAL_CONDUIT -> "special_thermal_conduit";
            case THERMAL_SHOCK -> "special_thermalshock";
            case THERMAL_VENT -> "special_thermal_vent";
            case BLAST_BLOCK -> "special_shieldbooster_explosive";
            case SUPER_CAPACITOR -> "special_shieldbooster_chunky";
            case THERMO_BLOCK -> resolveThermoBlock(module);
            case ANGLED_PLATING -> resolveAngledPlating(module);
            case DEEP_PLATING -> resolveDeepPlating(module);
            case LAYERED_PLATING -> resolveLayeredPlating(module);
            case REFLECTIVE_PLATING -> resolveReflectivePlating(module);
            case MONSTERED -> "special_powerplant_highcharge";
            case DRAG_DRIVES -> "special_engine_overloaded";
            case DRIVE_DISTRIBUTORS -> "special_engine_haulage";
            case DEEP_CHARGE -> "special_fsd_fuelcapacity";
            case MASS_MANAGER -> "special_fsd_heavy";
            case CLUSTER_CAPACITOR -> "special_powerdistributor_capacity";
            case SUPER_CONDUITS -> "special_powerdistributor_fast";
            case BOSS_CELLS -> "special_shieldcell_oversized";
            case RECYCLING_CELLS -> "special_shieldcell_gradual";
            case FAST_CHARGE -> "special_shield_regenerative";
            case HI_CAP -> "special_shield_health";
            case LO_DRAW -> "special_shield_efficient";
            case MULTI_WEAVE -> "special_shield_resistive";
            case FORCE_BLOCK -> resolveForceBlock(module);
            case ANTI_GUARDIAN_ZONE_RESISTANCE -> "guardianmodule_sturdy";
            case MERC_LONG_RANGE_DETAILED_SURFACE_SCANNER -> "detailedsurfacescanner_longrange";
            case MERC_PLASMA_CONVERSION_BURST_LASER -> "burstlaser_thermalplasmaconversion";
            case MERC_PLASMA_CONVERSION_BEAM_LASER -> "beamlaser_thermalplasmaconversion";
            case MERC_PLASMA_CONVERSION_PULSE_LASER -> "pulselaser_thermalplasmaconversion";
            case MERC_SCOOP_RATE_ENHANCED_FUEL_SCOOP -> "fuelscoop_efficiency";
            case MERC_HEAVY_DUTY_MODULE_REINFORCEMENT_PACKAGE -> "modulereinforcement_heavyduty";
            case MERC_EXTENDED_CARGO_RACK_5E -> "cargoracks5c1_extended";
            case MERC_EXTENDED_CARGO_RACK_6E -> "cargoracks6c1_extended";
            case MERC_DOUBLE_SCREAMING_FRAGMENT_CANNON_1EG -> "fragmentcannonsmall_doublescreaming";
            case MERC_DOUBLE_SCREAMING_FRAGMENT_CANNON_3CG -> "fragmentcannonlarge_doublescreaming";
            case MERC_RAPID_PHASE_MULTI_CANNON -> "multicannon_rapid";
            case MERC_BALANCED_POWER_DISTRIBUTOR -> "powerdistributor_balanced";
            case MERC_SUPPORT_FOCUSED_POWER_DISTRIBUTOR_3D -> "powerdistributors3c2_supportfocused";
            case MERC_SUPPORT_FOCUSED_POWER_DISTRIBUTOR_3A -> "powerdistributors3c5_supportfocused";
            case MERC_SUPPORT_FOCUSED_POWER_DISTRIBUTOR_4D -> "powerdistributors4c2_supportfocused";
            case MERC_SUPPORT_FOCUSED_POWER_DISTRIBUTOR_4A -> "powerdistributors4c5_supportfocused";
            case MERC_SUPPORT_FOCUSED_POWER_DISTRIBUTOR_6A -> "powerdistributors6c5_supportfocused";
            case MERC_FAR_REACHING_ABRASION_BLASTER -> "abrasionblaster_farreaching";
            case MERC_HIGH_YIELD_ENZYME_MISSILE_RACK -> "enzymemissilerack_highyield";
            case MERC_LONG_RANGE_MINING_LASER -> "mininglaser_longrange";
            case MERC_DRAG_SEEKER_MISSILE_RACK -> "seekermissilerack_drag";
            case MERC_LIGHTWEIGHT_THERMAL_SEEKER_MISSILE_RACK -> "seekermissilerack_lightweightthermal";
            case MERC_LOCKDOWN_SEEKER_MISSILE_RACK_2B -> "seekermissilerackmedium_lockdown";
            case MERC_LOCKDOWN_SEEKER_MISSILE_RACK_3A -> "seekermissileracklarge_lockdown";
            case MERC_ENDURING_FEEDBACK_RAIL_GUN -> "railgun_longshot";
            default -> "";
        };
    }

    private String resolveLightWeight(ShipModule module) {
//        case "misc_lightweight",
//             "chafflauncher_lightweight",
//             "ecm_lightweight",
//             "heatsinklauncher_lightweight",
//             "killwarrantscanner_lightweight",
//             "cargoscanner_lightweight",
//             "pointdefence_lightweight",
//             "wakescanner_lightweight",
//             "lifesupport_lightweight",
//             "collectionlimpet_lightweight",
//             "fueltransferlimpet_lightweight",
//             "hatchbreakerlimpet_lightweight",
//             "prospectinglimpet_lightweight", "armour_advanced" -> LIGHTWEIGHT;
        return switch (module) {
            case Armour _ -> "Armour_Advanced";
            case KillWarrantScanner _, LifeSupport _, ProspectorLimpetController _, CollectorLimpetController _,
                 FuelTransferLimpetController _, HatchBreakerLimpetController _, FrameShiftWakeScanner _,
                 PointDefence _, ManifestScanner _, SinkLauncher _, ChaffLauncher _, ElectronicCountermeasure _ ->
                    "misc_lightweight";
            default -> "";
        };
    }

    private String resolveFeedbackCascade(ShipModule module) {
        return switch (module) {
            case RailGun _ -> "special_feedback_cascade_cooled";
            default -> "special_feedback_cascade";
        };
    }

    private String resolvePlasmaSlug(ShipModule module) {
        return switch (module) {
            case PlasmaAccelerator _ -> "special_plasma_slug";
            case RailGun _ -> "special_plasma_slug_cooled";
            default -> "";
        };
    }

    private String resolveSuperPenetrator(ShipModule module) {
        return switch (module) {
            case RailGun _ -> module.isPreEngineered() ? "special_super_penetrator" : "special_super_penetrator_cooled";
            default -> "";
        };
    }

    private String resolveThermoBlock(ShipModule module) {
        return switch (module) {
            case ShieldGenerator _ -> "special_shield_thermic";
            case ShieldBooster _ -> "special_shieldbooster_thermic";
            default -> "";
        };
    }

    private String resolveForceBlock(ShipModule module) {
        return switch (module) {
            case ShieldGenerator _ -> "special_shield_kinetic";
            case ShieldBooster _ -> "special_shieldbooster_kinetic";
            default -> "";
        };
    }

    private String resolveWideAngleScanner(ShipModule module) {
        return switch (module) {
            case Sensors _ -> "sensor_wideangle";
            case FrameShiftWakeScanner _ -> "sensor_wideangle";
            case KillWarrantScanner _ -> "sensor_wideangle";
            case ManifestScanner _ -> "sensor_wideangle";
            default -> "";
        };
    }

    private String resolveLongRangeScanner(ShipModule module) {
        return switch (module) {
            case Sensors _ -> "sensor_longrange";
            case FrameShiftWakeScanner _ -> "sensor_longrange";
            case KillWarrantScanner _ -> "sensor_longrange";
            case ManifestScanner _ -> "sensor_longrange";
            default -> "";
        };
    }

    private String resolveReflectivePlating(ShipModule module) {
        return switch (module) {
            case HullReinforcementPackage _ -> "special_hullreinforcement_thermic";
            case Armour _ -> "special_armour_thermic";
            default -> "";
        };
    }

    private String resolveAngledPlating(ShipModule module) {
        return switch (module) {
            case HullReinforcementPackage _ -> "special_hullreinforcement_kinetic";
            case Armour _ -> "special_armour_kinetic";
            default -> "";
        };
    }


    private String resolveLayeredPlating(ShipModule module) {
        return switch (module) {
            case HullReinforcementPackage _ -> "special_hullreinforcement_explosive";
            case Armour _ -> "special_armour_explosive";
            default -> "";
        };
    }

    private String resolveDeepPlating(ShipModule module) {
        return switch (module) {
            case HullReinforcementPackage _ -> "special_hullreinforcement_chunky";
            case Armour _ -> "special_armour_chunky";
            default -> "";
        };
    }

    private String resolveBlastResistant(ShipModule module) {
        return switch (module) {
            case ShieldBooster _ -> "shieldbooster_explosive";
            case Armour _ -> "armour_explosive";
            default -> "";
        };
    }


    private String resolveHeavyDuty(ShipModule module) {
        return switch (module) {
            case ShieldBooster _ -> "shieldbooster_heavyduty";
            case Armour _ -> "armour_heavyduty";
            default -> "";
        };
    }

    private String resolveKineticResistant(ShipModule module) {
        return switch (module) {
            case ShieldBooster _ -> "shieldbooster_kinetic";
            case Armour _ -> "armour_kinetic";
            default -> "";
        };
    }

    private String resolveThermalResistant(ShipModule module) {
        return switch (module) {
            case ShieldBooster _ -> "shieldbooster_thermic";
            case Armour _ -> "armour_thermic";
            default -> "";
        };
    }

    private String resolveAmmoCapacity(ShipModule module) {
//        case "misc_chaffcapacity",
//             "chafflauncher_chaffcapacity",//legacy
//             "misc_heatsinkcapacity",
//             "heatsinklauncher_heatsinkcapacity",//legacy
//             "misc_pointdefensecapacity",
//             "pointdefence_pointdefensecapacity" /*legacy*/ -> AMMO_CAPACITY;
        return switch (module) {
            case ChaffLauncher _ -> "misc_chaffcapacity";
            case SinkLauncher _ -> "misc_heatsinkcapacity";
            case PointDefence _ -> "misc_pointdefensecapacity";
            default -> "";
        };
    }

    private String resolveSpecialBraced(ShipModule module) {
//            case "special_weapon_toughened", "special_shieldbooster_toughened", "special_powerplant_toughened",
//                 "special_engine_toughened", "special_fsd_toughened", "special_powerdistributor_toughened",
//                 "special_shieldcell_toughened", "special_shield_toughened" -> DOUBLE_BRACED;
        return switch (module) {
            case PowerPlant _ -> "special_powerplant_toughened";
            case Thrusters _ -> "special_engine_toughened";
            case FrameShiftDrive _ -> "special_fsd_toughened";
            case PowerDistributor _ -> "special_powerdistributor_toughened";
            case ShieldGenerator _ -> "special_shield_toughened";
            case ShieldBooster _ -> "special_shieldbooster_toughened";
            case ShieldCellBank _ -> "special_shieldcell_toughened";
            default -> "special_weapon_toughened";
        };
    }

    private String resolveShielded(ShipModule module) {

//        case "misc_shielded",
//             "chafflauncher_shielded",
//             "ecm_shielded",
//             "heatsinklauncher_shielded",
//             "killwarrantscanner_shielded",
//             "cargoscanner_shielded",
//             "pointdefence_shielded",
//             "wakescanner_shielded",
//             "lifesupport_shielded",
//             "collectionlimpet_shielded",
//             "fueltransferlimpet_shielded",
//             "hatchbreakerlimpet_shielded",
//             "prospectinglimpet_shielded",
//             "afm_shielded",
//             "fuelscoop_shielded",
//             "refineries_shielded", "powerdistributor_shielded" -> SHIELDED;
        return switch (module) {
            case PowerDistributor _ -> "powerdistributor_shielded";
            default -> "misc_shielded";
        };
    }

    private String resolveSpecialStripped(ShipModule module) {
//        case "special_weapon_lightweight", "special_powerplant_lightweight", "special_engine_lightweight",
//             "special_fsd_lightweight", "special_powerdistributor_lightweight", "special_shieldcell_lightweight",
//             "special_shield_lightweight" -> STRIPPED_DOWN;
        return switch (module) {
            case PowerPlant _ -> "special_powerplant_lightweight";
            case Thrusters _ -> "special_engine_lightweight";
            case FrameShiftDrive _ -> "special_fsd_lightweight";
            case PowerDistributor _ -> "special_powerdistributor_lightweight";
            case ShieldGenerator _ -> "special_shield_lightweight";
            case ShieldCellBank _ -> "special_shieldcell_lightweight";
            default -> "special_weapon_lightweight";
        };
    }

    private String resolveSpecialFlow(ShipModule module) {
//        case "special_weapon_efficient", "special_shieldbooster_efficient", "special_powerdistributor_efficient",
//             "special_shieldcell_efficient" -> FLOW_CONTROL;
        return switch (module) {
            case PowerDistributor _ -> "special_powerdistributor_efficient";
            case ShieldGenerator _ -> "special_shield_efficient";
            case ShieldCellBank _ -> "special_shieldcell_efficient";
            default -> "special_weapon_efficient";
        };
    }

    private String resolveSpecialThermalSpread(ShipModule module) {
//        case "special_powerplant_cooled", "special_engine_cooled", "special_fsd_cooled" -> THERMAL_SPREAD;
        return switch (module) {
            case PowerPlant _ -> "special_powerplant_cooled";
            case Thrusters _ -> "special_engine_cooled";
            case FrameShiftDrive _ -> "special_fsd_cooled";
            default -> "";
        };
    }

    /**
     * Returns the journal-format name for this experimental effect.
     * This is the inverse of the experimental effect cases in forInternalName.
     */
    public String getExperimentalEffectJournalName(ShipModule module) {
        return getJournalName(module);
    }

}
