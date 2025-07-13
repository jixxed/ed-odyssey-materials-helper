package nl.jixxed.eliteodysseymaterials.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public enum HorizonsBlueprintName implements BlueprintName<HorizonsBlueprintName> {
    ENGINEER_A(BlueprintGroup.ENGINEER_UNLOCKS, BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_A1(BlueprintGroup.ENGINEER_UNLOCKS, BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_A2(BlueprintGroup.ENGINEER_UNLOCKS, BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_A3(BlueprintGroup.ENGINEER_UNLOCKS, BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_A3A(BlueprintGroup.ENGINEER_UNLOCKS, BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_A3B(BlueprintGroup.ENGINEER_UNLOCKS, BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_A3C(BlueprintGroup.ENGINEER_UNLOCKS, BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_B(BlueprintGroup.ENGINEER_UNLOCKS, BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_B1(BlueprintGroup.ENGINEER_UNLOCKS, BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_B2(BlueprintGroup.ENGINEER_UNLOCKS, BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_B2A(BlueprintGroup.ENGINEER_UNLOCKS, BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_C(BlueprintGroup.ENGINEER_UNLOCKS, BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_C1(BlueprintGroup.ENGINEER_UNLOCKS, BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_C1A(BlueprintGroup.ENGINEER_UNLOCKS, BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_C1B(BlueprintGroup.ENGINEER_UNLOCKS, BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_D(BlueprintGroup.ENGINEER_UNLOCKS, BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_D1(BlueprintGroup.ENGINEER_UNLOCKS, BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_D2(BlueprintGroup.ENGINEER_UNLOCKS, BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_D2A(BlueprintGroup.ENGINEER_UNLOCKS, BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_D2B(BlueprintGroup.ENGINEER_UNLOCKS, BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_E(BlueprintGroup.ENGINEER_UNLOCKS, BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_E1(BlueprintGroup.ENGINEER_UNLOCKS, BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_E2(BlueprintGroup.ENGINEER_UNLOCKS, BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_E2A(BlueprintGroup.ENGINEER_UNLOCKS, BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_E2B(BlueprintGroup.ENGINEER_UNLOCKS, BlueprintCategory.ENGINEER_UNLOCKS),
    //hardpoint
    ABRASION_BLASTER(BlueprintGroup.ABRASION_BLASTERS, BlueprintCategory.HARDPOINT),
    ABRASION_BLASTER_PRE(BlueprintGroup.ABRASION_BLASTERS, BlueprintCategory.HARDPOINT, false),
    SUB_SURFACE_DISPLACEMENT_MISSILE(BlueprintGroup.SUB_SURFACE_DISPLACEMENT_MISSILES, BlueprintCategory.HARDPOINT),
    SUB_SURFACE_EXTRACTION_MISSILE(BlueprintGroup.SUB_SURFACE_DISPLACEMENT_MISSILES, BlueprintCategory.HARDPOINT, false),
    MINING_LASER(BlueprintGroup.MINING_LASERS, BlueprintCategory.HARDPOINT),
    MINING_LASER_PRE(BlueprintGroup.MINING_LASERS, BlueprintCategory.HARDPOINT, false),
    MINING_LASER_PRE_ARX(BlueprintGroup.MINING_LASERS, BlueprintCategory.HARDPOINT, false),
    LANCE_BEAM_MINING_LASER(BlueprintGroup.MINING_LASERS, BlueprintCategory.HARDPOINT, false),
    SEISMIC_CHARGE_LAUNCHER(BlueprintGroup.SEISMIC_CHARGE_LAUNCHERS, BlueprintCategory.HARDPOINT),
    BEAM_LASER(BlueprintGroup.BEAM_LASERS, BlueprintCategory.HARDPOINT),
    RETRIBUTOR_BEAM_LASER(BlueprintGroup.BEAM_LASERS, BlueprintCategory.HARDPOINT, false),
    BURST_LASER(BlueprintGroup.BURST_LASERS, BlueprintCategory.HARDPOINT),
    CYTOSCRAMBLER_BURST_LASER(BlueprintGroup.BURST_LASERS, BlueprintCategory.HARDPOINT, false),
    CANNON(BlueprintGroup.CANNONS, BlueprintCategory.HARDPOINT),
    CONCORD_CANNON(BlueprintGroup.CANNONS, BlueprintCategory.HARDPOINT, false),
    FRAGMENT_CANNON(BlueprintGroup.FRAGMENT_CANNONS, BlueprintCategory.HARDPOINT),
    FRAGMENT_CANNON_PRE(BlueprintGroup.FRAGMENT_CANNONS, BlueprintCategory.HARDPOINT, false),
    PACIFIER_FRAGMENT_CANNON(BlueprintGroup.FRAGMENT_CANNONS, BlueprintCategory.HARDPOINT, false),
    MINE_LAUNCHER(BlueprintGroup.MINE_LAUNCHERS, BlueprintCategory.HARDPOINT),
    SHOCK_MINE_LAUNCHER(BlueprintGroup.MINE_LAUNCHERS, BlueprintCategory.HARDPOINT, false),
    MISSILE_RACK(BlueprintGroup.MISSILE_RACKS, BlueprintCategory.HARDPOINT),
    SEEKER_MISSILE_RACK(BlueprintGroup.MISSILE_RACKS, BlueprintCategory.HARDPOINT, false),
    ADVANCED_MISSILE_RACK(BlueprintGroup.MISSILE_RACKS, BlueprintCategory.HARDPOINT, false),
    PACK_HOUND_MISSILE_RACK(BlueprintGroup.MISSILE_RACKS, BlueprintCategory.HARDPOINT, false),
    ROCKET_PROPELLED_FSD_DISRUPTER_MISSILE_RACK(BlueprintGroup.MISSILE_RACKS, BlueprintCategory.HARDPOINT, false),
    SEEKER_MISSILE_RACK_PRE(BlueprintGroup.MISSILE_RACKS, BlueprintCategory.HARDPOINT, false),
    AX_MISSILE_RACK(BlueprintGroup.AX_MISSILE_RACKS, BlueprintCategory.HARDPOINT),
    AX_MISSILE_RACK_PRE(BlueprintGroup.AX_MISSILE_RACKS, BlueprintCategory.HARDPOINT, false),
    ENHANCED_AX_MISSILE_RACK(BlueprintGroup.AX_MISSILE_RACKS, BlueprintCategory.HARDPOINT, false),
    SHOCK_CANNON(BlueprintGroup.SHOCK_CANNONS, BlueprintCategory.HARDPOINT),
    GUARDIAN_SHARD_CANNON(BlueprintGroup.GUARDIAN_SHARD_CANNONS, BlueprintCategory.HARDPOINT),
    GUARDIAN_SHARD_CANNON_PRE(BlueprintGroup.GUARDIAN_SHARD_CANNONS, BlueprintCategory.HARDPOINT, false),
    GUARDIAN_SHARD_CANNON_PRE_GOD(BlueprintGroup.GUARDIAN_SHARD_CANNONS, BlueprintCategory.HARDPOINT, false),
    GUARDIAN_GAUSS_CANNON(BlueprintGroup.GUARDIAN_GAUSS_CANNONS, BlueprintCategory.HARDPOINT),
    GUARDIAN_GAUSS_CANNON_PRE(BlueprintGroup.GUARDIAN_GAUSS_CANNONS, BlueprintCategory.HARDPOINT, false),
    GUARDIAN_PLASMA_CHARGER(BlueprintGroup.GUARDIAN_PLASMA_CHARGERS, BlueprintCategory.HARDPOINT),
    GUARDIAN_PLASMA_CHARGER_PRE(BlueprintGroup.GUARDIAN_PLASMA_CHARGERS, BlueprintCategory.HARDPOINT, false),
    GUARDIAN_NANITE_TORPEDO_PYLON(BlueprintGroup.GUARDIAN_NANITE_TORPEDO_PYLONS, BlueprintCategory.HARDPOINT),
    REMOTE_RELEASE_FLAK_LAUNCHER(BlueprintGroup.REMOTE_RELEASE_FLAK_LAUNCHERS, BlueprintCategory.HARDPOINT),
    FESTIVE_LAUNCHER(BlueprintGroup.REMOTE_RELEASE_FLAK_LAUNCHERS, BlueprintCategory.HARDPOINT, false),
    REMOTE_RELEASE_FLECHETTE_LAUNCHER(BlueprintGroup.REMOTE_RELEASE_FLECHETTE_LAUNCHERS, BlueprintCategory.HARDPOINT),
    AX_MULTI_CANNON(BlueprintGroup.AX_MULTI_CANNONS, BlueprintCategory.HARDPOINT),
    AX_MULTI_CANNON_PRE(BlueprintGroup.AX_MULTI_CANNONS, BlueprintCategory.HARDPOINT, false),
    ENHANCED_AX_MULTI_CANNON(BlueprintGroup.AX_MULTI_CANNONS, BlueprintCategory.HARDPOINT),
    ENZYME_MISSILE_RACK(BlueprintGroup.ENZYME_MISSILE_RACKS, BlueprintCategory.HARDPOINT),
    ENZYME_MISSILE_RACK_PRE(BlueprintGroup.ENZYME_MISSILE_RACKS, BlueprintCategory.HARDPOINT, false),
    MULTI_CANNON(BlueprintGroup.MULTI_CANNONS, BlueprintCategory.HARDPOINT),
    MULTI_CANNON_PRE(BlueprintGroup.MULTI_CANNONS, BlueprintCategory.HARDPOINT, false),
    ADVANCED_MULTI_CANNON(BlueprintGroup.MULTI_CANNONS, BlueprintCategory.HARDPOINT, false),
    ENFORCER_MULTI_CANNON(BlueprintGroup.MULTI_CANNONS, BlueprintCategory.HARDPOINT, false),
    PLASMA_ACCELERATOR(BlueprintGroup.PLASMA_ACCELERATORS, BlueprintCategory.HARDPOINT),
    ADVANCED_PLASMA_ACCELERATOR(BlueprintGroup.PLASMA_ACCELERATORS, BlueprintCategory.HARDPOINT, false),
    PULSE_LASER(BlueprintGroup.PULSE_LASERS, BlueprintCategory.HARDPOINT),
    PULSE_DISRUPTOR_LASER(BlueprintGroup.PULSE_LASERS, BlueprintCategory.HARDPOINT, false),
    RAIL_GUN(BlueprintGroup.RAIL_GUNS, BlueprintCategory.HARDPOINT),
    RAIL_GUN_PRE(BlueprintGroup.RAIL_GUNS, BlueprintCategory.HARDPOINT, false),
    IMPERIAL_HAMMER_RAIL_GUN(BlueprintGroup.RAIL_GUNS, BlueprintCategory.HARDPOINT, false),
    TORPEDO_PYLON(BlueprintGroup.TORPEDO_PYLONS, BlueprintCategory.HARDPOINT),
    //utility
    CHAFF_LAUNCHER(BlueprintGroup.CHAFF_LAUNCHERS, BlueprintCategory.UTILITY_MOUNT),
    ELECTRONIC_COUNTERMEASURE(BlueprintGroup.ELECTRONIC_COUNTERMEASURES, BlueprintCategory.UTILITY_MOUNT),
    FRAME_SHIFT_WAKE_SCANNER(BlueprintGroup.FRAME_SHIFT_WAKE_SCANNERS, BlueprintCategory.UTILITY_MOUNT),
    HEAT_SINK_LAUNCHER(BlueprintGroup.SINK_LAUNCHERS, BlueprintCategory.UTILITY_MOUNT),
    HEAT_SINK_LAUNCHER_PRE(BlueprintGroup.SINK_LAUNCHERS, BlueprintCategory.UTILITY_MOUNT, false),
    CAUSTIC_SINK_LAUNCHER(BlueprintGroup.SINK_LAUNCHERS, BlueprintCategory.UTILITY_MOUNT),
    KILL_WARRANT_SCANNER(BlueprintGroup.KILL_WARRANT_SCANNERS, BlueprintCategory.UTILITY_MOUNT),
    KILL_WARRANT_SCANNER_PRE(BlueprintGroup.KILL_WARRANT_SCANNERS, BlueprintCategory.UTILITY_MOUNT, false),
    MANIFEST_SCANNER(BlueprintGroup.MANIFEST_SCANNERS, BlueprintCategory.UTILITY_MOUNT),
    POINT_DEFENCE(BlueprintGroup.POINT_DEFENCES, BlueprintCategory.UTILITY_MOUNT),
    POINT_DEFENCE_PRE(BlueprintGroup.POINT_DEFENCES, BlueprintCategory.UTILITY_MOUNT, false),
    PULSE_WAVE_ANALYSER(BlueprintGroup.PULSE_WAVE_ANALYSERS, BlueprintCategory.UTILITY_MOUNT),
    SHIELD_BOOSTER(BlueprintGroup.SHIELD_BOOSTERS, BlueprintCategory.UTILITY_MOUNT),
    SHUTDOWN_FIELD_NEUTRALISER(BlueprintGroup.XENOS, BlueprintCategory.UTILITY_MOUNT),
    THARGOID_PULSE_NEUTRALISER(BlueprintGroup.XENOS, BlueprintCategory.UTILITY_MOUNT),
    XENO_SCANNER(BlueprintGroup.XENOS, BlueprintCategory.UTILITY_MOUNT),
    ENHANCED_XENO_SCANNER(BlueprintGroup.XENOS, BlueprintCategory.UTILITY_MOUNT),
    PULSE_WAVE_XENO_SCANNER(BlueprintGroup.XENOS, BlueprintCategory.UTILITY_MOUNT),
    //special
    FUEL_TANK(BlueprintGroup.FUEL_TANKS, BlueprintCategory.OPTIONAL_INTERNAL),
    CARGO_HATCH(BlueprintGroup.CARGO_HATCHES, BlueprintCategory.CARGO_HATCH),
    //core
    ARMOUR(BlueprintGroup.ARMOURS, BlueprintCategory.CORE_INTERNAL),
    LIGHTWEIGHT_ALLOY_ARMOUR(BlueprintGroup.ARMOURS, BlueprintCategory.CORE_INTERNAL, false),
    REINFORCED_ALLOY_ARMOUR(BlueprintGroup.ARMOURS, BlueprintCategory.CORE_INTERNAL, false),
    MILITARY_GRADE_COMPOSITE_ARMOUR(BlueprintGroup.ARMOURS, BlueprintCategory.CORE_INTERNAL, false),
    MIRRORED_SURFACE_COMPOSITE_ARMOUR(BlueprintGroup.ARMOURS, BlueprintCategory.CORE_INTERNAL, false),
    REACTIVE_SURFACE_COMPOSITE_ARMOUR(BlueprintGroup.ARMOURS, BlueprintCategory.CORE_INTERNAL, false),
    FRAME_SHIFT_DRIVE(BlueprintGroup.FRAME_SHIFT_DRIVES, BlueprintCategory.CORE_INTERNAL),
    FRAME_SHIFT_DRIVE_PRE(BlueprintGroup.FRAME_SHIFT_DRIVES, BlueprintCategory.CORE_INTERNAL, false),
    FRAME_SHIFT_DRIVE_OVERCHARGE(BlueprintGroup.FRAME_SHIFT_DRIVES, BlueprintCategory.CORE_INTERNAL, false),
    FRAME_SHIFT_DRIVE_OVERCHARGE_PRE(BlueprintGroup.FRAME_SHIFT_DRIVES, BlueprintCategory.CORE_INTERNAL, false),
    LIFE_SUPPORT(BlueprintGroup.LIFE_SUPPORTS, BlueprintCategory.CORE_INTERNAL),
    POWER_DISTRIBUTOR(BlueprintGroup.POWER_DISTRIBUTORS, BlueprintCategory.CORE_INTERNAL),
    POWER_DISTRIBUTOR_SYSTEM_ENGINE_FOCUSED(BlueprintGroup.POWER_DISTRIBUTORS, BlueprintCategory.CORE_INTERNAL, false),
    GUARDIAN_POWER_DISTRIBUTOR(BlueprintGroup.POWER_DISTRIBUTORS, BlueprintCategory.CORE_INTERNAL, false),
    POWER_PLANT(BlueprintGroup.POWER_PLANTS, BlueprintCategory.CORE_INTERNAL),
    GUARDIAN_POWER_PLANT(BlueprintGroup.POWER_PLANTS, BlueprintCategory.CORE_INTERNAL, false),
    POWER_PLANT_ARMOURED_OVERCHARGED(BlueprintGroup.POWER_PLANTS, BlueprintCategory.CORE_INTERNAL, false),
    POWER_PLANT_OVERCHARGED_OVERCHARGED(BlueprintGroup.POWER_PLANTS, BlueprintCategory.CORE_INTERNAL, false),
    SENSORS(BlueprintGroup.SENSORS, BlueprintCategory.CORE_INTERNAL),
    THRUSTERS(BlueprintGroup.THRUSTERS, BlueprintCategory.CORE_INTERNAL),
    ENHANCED_THRUSTERS(BlueprintGroup.THRUSTERS, BlueprintCategory.CORE_INTERNAL, false),
    //optional
    ANTI_CORROSION_CARGO_RACK(BlueprintGroup.ANTI_CORROSION_CARGO_RACKS, BlueprintCategory.OPTIONAL_INTERNAL),
    AUTO_FIELD_MAINTENANCE_UNIT(BlueprintGroup.AUTO_FIELD_MAINTENANCE_UNITS, BlueprintCategory.OPTIONAL_INTERNAL),
    CARGO_RACK(BlueprintGroup.CARGO_RACKS, BlueprintCategory.OPTIONAL_INTERNAL),
    COLLECTOR_LIMPET_CONTROLLER(BlueprintGroup.COLLECTOR_LIMPET_CONTROLLERS, BlueprintCategory.OPTIONAL_INTERNAL),
    //    COMPUTER(BlueprintGroup.COMPUTERS, BlueprintCategory.OPTIONAL_INTERNAL),
    STANDARD_DOCKING_COMPUTER(BlueprintGroup.COMPUTERS, BlueprintCategory.OPTIONAL_INTERNAL),
    ADVANCED_DOCKING_COMPUTER(BlueprintGroup.COMPUTERS, BlueprintCategory.OPTIONAL_INTERNAL),
    SUPERCRUISE_ASSIST(BlueprintGroup.COMPUTERS, BlueprintCategory.OPTIONAL_INTERNAL),
    DECONTAMINATION_LIMPET_CONTROLLER(BlueprintGroup.DECONTAMINATION_LIMPET_CONTROLLERS, BlueprintCategory.OPTIONAL_INTERNAL),
    DETAILED_SURFACE_SCANNER(BlueprintGroup.DETAILED_SURFACE_SCANNERS, BlueprintCategory.OPTIONAL_INTERNAL),
    DETAILED_SURFACE_SCANNER_PRE(BlueprintGroup.DETAILED_SURFACE_SCANNERS, BlueprintCategory.OPTIONAL_INTERNAL, false),
    EXPERIMENTAL_WEAPON_STABILISER(BlueprintGroup.EXPERIMENTAL_WEAPON_STABILISERS, BlueprintCategory.OPTIONAL_INTERNAL),
    FIGHTER_HANGAR(BlueprintGroup.FIGHTER_HANGARS, BlueprintCategory.OPTIONAL_INTERNAL),
    FRAME_SHIFT_DRIVE_BOOSTER(BlueprintGroup.FRAME_SHIFT_DRIVE_BOOSTERS, BlueprintCategory.OPTIONAL_INTERNAL),
    FRAME_SHIFT_DRIVE_INTERDICTOR(BlueprintGroup.FRAME_SHIFT_DRIVE_INTERDICTORS, BlueprintCategory.OPTIONAL_INTERNAL),
    FUEL_SCOOP(BlueprintGroup.FUEL_SCOOPS, BlueprintCategory.OPTIONAL_INTERNAL),
    FUEL_TRANSFER_LIMPET_CONTROLLER(BlueprintGroup.FUEL_TRANSFER_LIMPET_CONTROLLERS, BlueprintCategory.OPTIONAL_INTERNAL),
    HATCH_BREAKER_LIMPET_CONTROLLER(BlueprintGroup.HATCH_BREAKER_LIMPET_CONTROLLERS, BlueprintCategory.OPTIONAL_INTERNAL),
    HATCH_BREAKER_LIMPET_CONTROL(BlueprintGroup.HATCH_BREAKER_LIMPET_CONTROLLERS, BlueprintCategory.OPTIONAL_INTERNAL, false),
    META_ALLOY_HULL_REINFORCEMENT_PACKAGE(BlueprintGroup.META_ALLOY_HULL_REINFORCEMENT_PACKAGES, BlueprintCategory.OPTIONAL_INTERNAL),
    MULTI_LIMPET_CONTROLLER(BlueprintGroup.MULTI_LIMPET_CONTROLLERS, BlueprintCategory.OPTIONAL_INTERNAL),
    MINING_MULTI_LIMPET_CONTROLLER(BlueprintGroup.MULTI_LIMPET_CONTROLLERS, BlueprintCategory.OPTIONAL_INTERNAL),
    OPERATIONS_LIMPET_CONTROLLER(BlueprintGroup.MULTI_LIMPET_CONTROLLERS, BlueprintCategory.OPTIONAL_INTERNAL),
    RESCUE_LIMPET_CONTROLLER(BlueprintGroup.MULTI_LIMPET_CONTROLLERS, BlueprintCategory.OPTIONAL_INTERNAL),
    XENO_LIMPET_CONTROLLER(BlueprintGroup.MULTI_LIMPET_CONTROLLERS, BlueprintCategory.OPTIONAL_INTERNAL),
    UNIVERSAL_MULTI_LIMPET_CONTROLLER(BlueprintGroup.MULTI_LIMPET_CONTROLLERS, BlueprintCategory.OPTIONAL_INTERNAL),

    // PASSENGER_CABIN(BlueprintGroup.PASSENGER_CABINS, BlueprintCategory.OPTIONAL_INTERNAL),
    ECONOMY_CLASS_PASSENGER_CABIN(BlueprintGroup.PASSENGER_CABINS, BlueprintCategory.OPTIONAL_INTERNAL),
    BUSINESS_CLASS_PASSENGER_CABIN(BlueprintGroup.PASSENGER_CABINS, BlueprintCategory.OPTIONAL_INTERNAL),
    FIRST_CLASS_PASSENGER_CABIN(BlueprintGroup.PASSENGER_CABINS, BlueprintCategory.OPTIONAL_INTERNAL),
    LUXURY_CLASS_PASSENGER_CABIN(BlueprintGroup.PASSENGER_CABINS, BlueprintCategory.OPTIONAL_INTERNAL),
    PLANETARY_VEHICLE_HANGAR(BlueprintGroup.PLANETARY_VEHICLE_HANGARS, BlueprintCategory.OPTIONAL_INTERNAL),
    PROSPECTOR_LIMPET_CONTROLLER(BlueprintGroup.PROSPECTOR_LIMPET_CONTROLLERS, BlueprintCategory.OPTIONAL_INTERNAL),
    RECON_LIMPET_CONTROLLER(BlueprintGroup.RECON_LIMPET_CONTROLLERS, BlueprintCategory.OPTIONAL_INTERNAL),
    REFINERY(BlueprintGroup.REFINERIES, BlueprintCategory.OPTIONAL_INTERNAL),
    REPAIR_LIMPET_CONTROLLER(BlueprintGroup.REPAIR_LIMPET_CONTROLLERS, BlueprintCategory.OPTIONAL_INTERNAL),
    RESEARCH_LIMPET_CONTROLLER(BlueprintGroup.RESEARCH_LIMPET_CONTROLLERS, BlueprintCategory.OPTIONAL_INTERNAL),
    SHIELD_GENERATOR(BlueprintGroup.SHIELD_GENERATORS, BlueprintCategory.OPTIONAL_INTERNAL),
    SHIELD_GENERATOR_PRE(BlueprintGroup.SHIELD_GENERATORS, BlueprintCategory.OPTIONAL_INTERNAL, false),
    BI_WEAVE_SHIELD_GENERATOR(BlueprintGroup.SHIELD_GENERATORS, BlueprintCategory.OPTIONAL_INTERNAL, false),
    PRISMATIC_SHIELD_GENERATOR(BlueprintGroup.SHIELD_GENERATORS, BlueprintCategory.OPTIONAL_INTERNAL, false),
    GUARDIAN_HULL_REINFORCEMENT_PACKAGE(BlueprintGroup.GUARDIAN_HULL_REINFORCEMENT_PACKAGES, BlueprintCategory.OPTIONAL_MILITARY),
    GUARDIAN_MODULE_REINFORCEMENT_PACKAGE(BlueprintGroup.GUARDIAN_MODULE_REINFORCEMENT_PACKAGES, BlueprintCategory.OPTIONAL_MILITARY),
    GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE(BlueprintGroup.GUARDIAN_SHIELD_REINFORCEMENT_PACKAGES, BlueprintCategory.OPTIONAL_MILITARY),
    HULL_REINFORCEMENT_PACKAGE(BlueprintGroup.HULL_REINFORCEMENT_PACKAGES, BlueprintCategory.OPTIONAL_MILITARY),
    MODULE_REINFORCEMENT_PACKAGE(BlueprintGroup.MODULE_REINFORCEMENT_PACKAGES, BlueprintCategory.OPTIONAL_MILITARY),
    SHIELD_CELL_BANK(BlueprintGroup.SHIELD_CELL_BANKS, BlueprintCategory.OPTIONAL_MILITARY),
    BASIC_DISCOVERY_SCANNER(BlueprintGroup.DISCOVERY_SCANNERS, BlueprintCategory.OPTIONAL_INTERNAL),//removed from game
    INTERMEDIATE_DISCOVERY_SCANNER(BlueprintGroup.DISCOVERY_SCANNERS, BlueprintCategory.OPTIONAL_INTERNAL),//removed from game
    ADVANCED_DISCOVERY_SCANNER(BlueprintGroup.DISCOVERY_SCANNERS, BlueprintCategory.OPTIONAL_INTERNAL),//removed from game
    //Synthesis
    AFM_REFILL(BlueprintGroup.SYNTHESIS, BlueprintCategory.SYNTHESIS),
    AX_EXPLOSIVE_MUNITIONS(BlueprintGroup.SYNTHESIS, BlueprintCategory.SYNTHESIS),
    AX_REMOTE_FLAK_MUNITIONS(BlueprintGroup.SYNTHESIS, BlueprintCategory.SYNTHESIS),
    AX_SMALL_CALIBRE_MUNITIONS(BlueprintGroup.SYNTHESIS, BlueprintCategory.SYNTHESIS),
    CHAFF(BlueprintGroup.SYNTHESIS, BlueprintCategory.SYNTHESIS),
    CONFIGURABLE_EXPLOSIVE_MUNITIONS(BlueprintGroup.SYNTHESIS, BlueprintCategory.SYNTHESIS),
    CONFIGURABLE_SMALL_CALIBRE_MUNITIONS(BlueprintGroup.SYNTHESIS, BlueprintCategory.SYNTHESIS),
    ENZYME_MISSILE_LAUNCHER_MUNITIONS(BlueprintGroup.SYNTHESIS, BlueprintCategory.SYNTHESIS),
    EXPLOSIVE_MUNITIONS(BlueprintGroup.SYNTHESIS, BlueprintCategory.SYNTHESIS),
    FLECHETTE_LAUNCHER_MUNITIONS(BlueprintGroup.SYNTHESIS, BlueprintCategory.SYNTHESIS),
    FSD_INJECTION(BlueprintGroup.SYNTHESIS, BlueprintCategory.SYNTHESIS),
    GUARDIAN_GAUSS_CANNON_MUNITIONS(BlueprintGroup.SYNTHESIS, BlueprintCategory.SYNTHESIS),
    GUARDIAN_NANITE_MUNITIONS(BlueprintGroup.SYNTHESIS, BlueprintCategory.SYNTHESIS),
    GUARDIAN_PLASMA_CHARGER_MUNITIONS(BlueprintGroup.SYNTHESIS, BlueprintCategory.SYNTHESIS),
    GUARDIAN_SHARD_CANNON_MUNITIONS(BlueprintGroup.SYNTHESIS, BlueprintCategory.SYNTHESIS),
    HEATSINKS(BlueprintGroup.SYNTHESIS, BlueprintCategory.SYNTHESIS),
    HIGH_VELOCITY_MUNITIONS(BlueprintGroup.SYNTHESIS, BlueprintCategory.SYNTHESIS),
    LARGE_CALIBRE_MUNITIONS(BlueprintGroup.SYNTHESIS, BlueprintCategory.SYNTHESIS),
    LIFE_SUPPORT_SYNTHESIS(BlueprintGroup.SYNTHESIS, BlueprintCategory.SYNTHESIS),
    LIMPETS(BlueprintGroup.SYNTHESIS, BlueprintCategory.SYNTHESIS),
    PLASMA_MUNITIONS(BlueprintGroup.SYNTHESIS, BlueprintCategory.SYNTHESIS),
    SEISMIC_CHARGE_MUNITIONS(BlueprintGroup.SYNTHESIS, BlueprintCategory.SYNTHESIS),
    SHOCK_CANNON_MUNITIONS(BlueprintGroup.SYNTHESIS, BlueprintCategory.SYNTHESIS),
    SMALL_CALIBRE_MUNITIONS(BlueprintGroup.SYNTHESIS, BlueprintCategory.SYNTHESIS),
    SRV_AMMO_RESTOCK(BlueprintGroup.SYNTHESIS, BlueprintCategory.SYNTHESIS),
    SRV_REFUEL(BlueprintGroup.SYNTHESIS, BlueprintCategory.SYNTHESIS),
    CAUSTIC_SINKS(BlueprintGroup.SYNTHESIS, BlueprintCategory.SYNTHESIS),
    SRV_REPAIR(BlueprintGroup.SYNTHESIS, BlueprintCategory.SYNTHESIS),
    SUB_SURFACE_DISPLACEMENT_MUNITIONS(BlueprintGroup.SYNTHESIS, BlueprintCategory.SYNTHESIS),
    //Techbroker
    GUARDIAN_MODULES(BlueprintGroup.TECHBROKERS, BlueprintCategory.TECHBROKER),
    GUARDIAN_WEAPONS(BlueprintGroup.TECHBROKERS, BlueprintCategory.TECHBROKER),
    GUARDIAN_FIGHTERS(BlueprintGroup.TECHBROKERS, BlueprintCategory.TECHBROKER),
    HUMAN_WEAPONS(BlueprintGroup.TECHBROKERS, BlueprintCategory.TECHBROKER),
    HUMAN_MODULES(BlueprintGroup.TECHBROKERS, BlueprintCategory.TECHBROKER),
    HUMAN_LIVERY(BlueprintGroup.TECHBROKERS, BlueprintCategory.TECHBROKER),
    NONE(BlueprintGroup.TECHBROKERS, BlueprintCategory.TECHBROKER);

    private final BlueprintGroup blueprintGroup;
    private final BlueprintCategory blueprintCategory;
    private boolean primary = true;

    public static HorizonsBlueprintName forName(final String name) {
        try {
            return HorizonsBlueprintName.valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException ex) {
            return null;
        }
    }

    public static HorizonsBlueprintName forEngineer(final Engineer engineer) {
        return switch (engineer) {
            case ELVIRA_MARTUUK -> ENGINEER_A;
            case MEL_BRANDON -> ENGINEER_A1;
            case ZACARIAH_NEMO -> ENGINEER_A2;
            case MARCO_QWENT -> ENGINEER_A3;
            case PROFESSOR_PALIN -> ENGINEER_A3A;
            case LORI_JAMESON -> ENGINEER_A3B;
            case CHLOE_SEDESI -> ENGINEER_A3C;
            case THE_DWELLER -> ENGINEER_B;
            case MARSHA_HICKS -> ENGINEER_B1;
            case LEI_CHEUNG -> ENGINEER_B2;
            case RAM_TAH -> ENGINEER_B2A;
            case FELICITY_FARSEER -> ENGINEER_C;
            case JURI_ISHMAAK -> ENGINEER_C1;
            case COLONEL_BRIS_DEKKER -> ENGINEER_C1A;
            case THE_SARGE -> ENGINEER_C1B;
            case TOD_THE_BLASTER_MCQUINN -> ENGINEER_D;
            case PETRA_OLMANOVA -> ENGINEER_D1;
            case SELENE_JEAN -> ENGINEER_D2;
            case DIDI_VATERMANN -> ENGINEER_D2A;
            case BILL_TURNER -> ENGINEER_D2B;
            case LIZ_RYDER -> ENGINEER_E;
            case ETIENNE_DORN -> ENGINEER_E1;
            case HERA_TANI -> ENGINEER_E2;
            case BROO_TARQUIN -> ENGINEER_E2A;
            case TIANA_FORTUNE -> ENGINEER_E2B;
            case UNKNOWN -> throw new IllegalArgumentException("unknown engineer");
            default -> null;
        };
    }

    public static HorizonsBlueprintName forInternalName(final String internalModuleName) {
        final String nameLowerCase = internalModuleName.toLowerCase();
        if (nameLowerCase.startsWith("hpt_beamlaser")) {
            return BEAM_LASER;
        } else if (nameLowerCase.startsWith("hpt_pulselaserburst")) {
            return BURST_LASER;
        } else if (nameLowerCase.startsWith("hpt_cannon")) {
            return CANNON;
        } else if (nameLowerCase.startsWith("hpt_slugshot")) {
            return FRAGMENT_CANNON;
        } else if (nameLowerCase.startsWith("hpt_minelauncher")) {
            return MINE_LAUNCHER;
        } else if (nameLowerCase.startsWith("hpt_dumbfiremissilerack")
                || nameLowerCase.startsWith("hpt_drunkmissilerack")
                || nameLowerCase.startsWith("hpt_basicmissilerack")) {
            return MISSILE_RACK;
        } else if (nameLowerCase.startsWith("hpt_multicannon")) {
            return MULTI_CANNON;
        } else if (nameLowerCase.startsWith("hpt_plasmaaccelerator")) {
            return PLASMA_ACCELERATOR;
        } else if (nameLowerCase.startsWith("hpt_pulselaser")) {
            return PULSE_LASER;
        } else if (nameLowerCase.startsWith("hpt_railgun")) {
            return RAIL_GUN;
        } else if (nameLowerCase.startsWith("hpt_advancedtorppylon")) {
            return TORPEDO_PYLON;
        } else if (nameLowerCase.startsWith("hpt_chafflauncher")) {
            return CHAFF_LAUNCHER;
        } else if (nameLowerCase.startsWith("hpt_electroniccountermeasure")) {
            return ELECTRONIC_COUNTERMEASURE;
        } else if (nameLowerCase.startsWith("hpt_cloudscanner")) {
            return FRAME_SHIFT_WAKE_SCANNER;
        } else if (nameLowerCase.startsWith("hpt_heatsinklauncher")) {
            return HEAT_SINK_LAUNCHER;
        } else if (nameLowerCase.startsWith("hpt_crimescanner")) {
            return KILL_WARRANT_SCANNER;
        } else if (nameLowerCase.startsWith("hpt_cargoscanner")) {
            return MANIFEST_SCANNER;
        } else if (nameLowerCase.startsWith("hpt_plasmapointdefence")) {
            return POINT_DEFENCE;
        } else if (nameLowerCase.startsWith("hpt_shieldbooster")) {
            return SHIELD_BOOSTER;
        } else if (nameLowerCase.startsWith("hpt_causticsinklauncher")) {
            return CAUSTIC_SINK_LAUNCHER;
        } else if (nameLowerCase.contains("armour")) {
            return ARMOUR;
        } else if (nameLowerCase.startsWith("int_hyperdrive")) {
            return FRAME_SHIFT_DRIVE;
        } else if (nameLowerCase.startsWith("int_lifesupport")) {
            return LIFE_SUPPORT;
        } else if (nameLowerCase.startsWith("int_powerdistributor")) {
            return POWER_DISTRIBUTOR;
        } else if (nameLowerCase.startsWith("int_powerplant")) {
            return POWER_PLANT;
        } else if (nameLowerCase.startsWith("int_sensors")) {
            return SENSORS;
        } else if (nameLowerCase.startsWith("int_engine")) {
            return THRUSTERS;
        } else if (nameLowerCase.startsWith("int_repairer")) {
            return AUTO_FIELD_MAINTENANCE_UNIT;
        } else if (nameLowerCase.startsWith("int_dronecontrol_collection")) {
            return COLLECTOR_LIMPET_CONTROLLER;
        } else if (nameLowerCase.startsWith("int_detailedsurfacescanner")) {
            return DETAILED_SURFACE_SCANNER;
        } else if (nameLowerCase.startsWith("int_fsdinterdictor")) {
            return FRAME_SHIFT_DRIVE_INTERDICTOR;
        } else if (nameLowerCase.startsWith("int_fuelscoop")) {
            return FUEL_SCOOP;
        } else if (nameLowerCase.startsWith("int_dronecontrol_fueltransfer")) {
            return FUEL_TRANSFER_LIMPET_CONTROLLER;
        } else if (nameLowerCase.startsWith("int_dronecontrol_resourcesiphon")) {
            return HATCH_BREAKER_LIMPET_CONTROLLER;
        } else if (nameLowerCase.startsWith("int_hullreinforcement")) {
            return HULL_REINFORCEMENT_PACKAGE;
        } else if (nameLowerCase.startsWith("int_dronecontrol_prospector")) {
            return PROSPECTOR_LIMPET_CONTROLLER;
        } else if (nameLowerCase.startsWith("int_refinery")) {
            return REFINERY;
        } else if (nameLowerCase.startsWith("int_shieldcellbank")) {
            return SHIELD_CELL_BANK;
        } else if (nameLowerCase.startsWith("int_shieldgenerator")) {
            return SHIELD_GENERATOR;
        } else if (nameLowerCase.startsWith("int_cargorack")) {
            return CARGO_RACK;
        } else if (nameLowerCase.startsWith("int_fueltank")) {
            return FUEL_TANK;
        }
        throw new IllegalArgumentException("Unknown module name: " + internalModuleName);
    }


    @Override
    public String getLocalizationKey() {
        return "blueprint.horizons.name." + lcName();
    }

    @Override
    public String getDescriptionLocalizationKey() {
        return "blueprint.horizons.description." + lcName();
    }

    @Override
    public String lcName() {
        return this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }

    public HorizonsBlueprintName getPrimary() {
        if (!this.primary) {
            return Arrays.stream(HorizonsBlueprintName.values()).filter(other -> this.getBlueprintGroup().equals(other.blueprintGroup)).findFirst().orElseThrow(IllegalArgumentException::new);
        }
        return this;
    }
}
