package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

@RequiredArgsConstructor
public enum HorizonsBlueprintType {
    //inara
    ENGINEER(false),
    AMMO_CAPACITY(false),
    ARMOURED(false),
    BLAST_RESISTANT(false),
    CHARGE_ENHANCED(false),
    DOUBLE_SHOT(false),
    ENGINE_FOCUSED(false),
    EXPANDED_PROBE_SCANNING_RADIUS(false),
    EXPANDED_PROBE_SCANNING_RADIUS_X2(false),
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
    HIGH_CAPACITY_MAGAZINE_RAPID_FIRE_MODIFICATION(false, true),
    FAST_SCANNER_LONG_RANGE_SCANNER(false, true),
    LONG_RANGE_WEAPON_INCENDIARY_ROUNDS(false, true),
    THERMAL_RESISTANT_SHIELDS_KINETIC_RESISTANT_SHIELDS(false, true),
    AMMO_CAPACITY_X2(false, true),
    LIGHTWEIGHT_FOCUSED(false, true),
    LONG_RANGE_WEAPON_HIGH_CAPACITY_MAGAZINE_FEEDBACK_CASCADE(false, true),
    OVERCHARGED_WEAPON_AUTO_LOADER(false, true),
    HIGH_CAPACITY_MAGAZINE_INCREASED_DAMAGE(false, true),
    RAPID_FIRE_MODIFICATION_PHASING_SEQUENCE(false, true),
    ARMOURED_OVERCHARGED(false, true),
    OVERCHARGED_OVERCHARGED(false, true),
    OVERCHARGED_WEAPON_FOCUSED_WEAPON(false, true),
    LONG_RANGE_WEAPON_FOCUSED_WEAPON_PENETRATOR_MUNITIONS(false, true),
    HIGH_CAPACITY_MAGAZINE_THERMAL_CASCADE(false, true),
    INCREASED_FSD_RANGE_FASTER_FSD_BOOT_SEQUENCE(false, true);
    @Getter
    private final boolean experimental;
    @Getter
    private final boolean preEngineered;

    HorizonsBlueprintType(boolean experimental) {
        this.experimental = experimental;
        this.preEngineered = false;
    }

    public static HorizonsBlueprintType forName(final String name) {
        try {
            return HorizonsBlueprintType.valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException ex) {
            return null;
        }
    }
//// blueprint mappings are per-mtype
//			for (var mtypeid in eddb.mtype) {
//        // current blueprint mappings
//        fdevmap.mtypeBlueprint[mtypeid] = {};
//        var mtype = eddb.mtype[mtypeid];
//        for (var b = 0;  b < (mtype.blueprints || EMPTY_ARR).length;  b++) {
//            var bpid = mtype.blueprints[b];
//            if (eddb.blueprint[bpid]) {
//                var fdname = (eddb.blueprint[bpid].fdname || '').trim().toUpperCase();
//                fdevmap.mtypeBlueprint[mtypeid][fdname] = bpid;
//            }
//        }
//
//        // renamed blueprint mappings
//        for (var bpname in { LIGHTWEIGHT:1, REINFORCED:1, SHIELDED:1 }) {
//            if (fdevmap.mtypeBlueprint[mtypeid]['MISC_' + bpname]) {
//                for (var modtype in { CHAFFLAUNCHER:1, ECM:1, HEATSINKLAUNCHER:1, KILLWARRANTSCANNER:1, CARGOSCANNER:1, POINTDEFENCE:1, WAKESCANNER:1, LIFESUPPORT:1, COLLECTIONLIMPET:1, FUELTRANSFERLIMPET:1, HATCHBREAKERLIMPET:1, PROSPECTINGLIMPET:1 }) {
//                    fdevmap.mtypeBlueprint[mtypeid][modtype + '_' + bpname] = fdevmap.mtypeBlueprint[mtypeid]['MISC_' + bpname];
//                }
//            }
//        }
//        for (var bpname in { FASTSCAN:1, LONGRANGE:1, WIDEANGLE:1 }) {
//            if (fdevmap.mtypeBlueprint[mtypeid]['SENSOR_' + bpname]) {
//                for (var modtype in { SENSOR_KILLWARRANTSCANNER:1, KILLWARRANTSCANNER:1, SENSOR_CARGOSCANNER:1, SENSOR_WAKESCANNER:1, SENSOR_SENSOR:1, SENSOR_SURFACESCANNER:1 }) {
//                    fdevmap.mtypeBlueprint[mtypeid][modtype + '_' + bpname] = fdevmap.mtypeBlueprint[mtypeid]['SENSOR_' + bpname];
//                }
//            }
//        }
//        fdevmap.mtypeBlueprint[mtypeid]['CHAFFLAUNCHER_CHAFFCAPACITY'] = fdevmap.mtypeBlueprint[mtypeid]['MISC_CHAFFCAPACITY'];
//        fdevmap.mtypeBlueprint[mtypeid]['HEATSINKLAUNCHER_HEATSINKCAPACITY'] = fdevmap.mtypeBlueprint[mtypeid]['MISC_HEATSINKCAPACITY'];
//        fdevmap.mtypeBlueprint[mtypeid]['POINTDEFENCE_POINTDEFENSECAPACITY'] = fdevmap.mtypeBlueprint[mtypeid]['MISC_POINTDEFENSECAPACITY'];
//        fdevmap.mtypeBlueprint[mtypeid]['SENSOR_SENSOR_LIGHTWEIGHT'] = fdevmap.mtypeBlueprint[mtypeid]['SENSOR_LIGHTWEIGHT'];
//        if (fdevmap.mtypeBlueprint[mtypeid]['MISC_SHIELDED']) {
//            for (var modtype in { AFM:1, FUELSCOOP:1, REFINERIES:1 }) {
//                fdevmap.mtypeBlueprint[mtypeid][modtype + '_SHIELDED'] = fdevmap.mtypeBlueprint[mtypeid]['MISC_SHIELDED'];
//            }
//        }
//    }

    public static HorizonsBlueprintType forInternalName(final String internalName) {
        return switch (internalName.toLowerCase()) {
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
                    "prospectinglimpet_lightweight" -> LIGHTWEIGHT;

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
                    "refineries_shielded" -> SHIELDED;

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
                    "chafflauncher_chaffcapacity",
                    "misc_heatsinkcapacity",
                    "heatsinklauncher_heatsinkcapacity",
                    "misc_pointdefensecapacity",
                    "pointdefence_pointdefensecapacity" -> AMMO_CAPACITY;

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
            case "decorative_green" -> DECORATIVE_GREEN;
            case "decorative_red" -> DECORATIVE_RED;
            case "decorative_yellow" -> DECORATIVE_YELLOW;
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
