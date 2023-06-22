package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

@Getter
@RequiredArgsConstructor
public enum HorizonsBlueprintName implements BlueprintName<HorizonsBlueprintName> {
    ENGINEER_A(BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_A1(BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_A2(BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_A3(BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_A3A(BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_A3B(BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_A3C(BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_B(BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_B1(BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_B2(BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_B2A(BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_C(BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_C1(BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_C1A(BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_C1B(BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_D(BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_D1(BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_D2(BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_D2A(BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_D2B(BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_E(BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_E1(BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_E2(BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_E2A(BlueprintCategory.ENGINEER_UNLOCKS),
    ENGINEER_E2B(BlueprintCategory.ENGINEER_UNLOCKS),
    //hardpoint
    BEAM_LASER(BlueprintCategory.HARDPOINT),
    BURST_LASER(BlueprintCategory.HARDPOINT),
    CANNON(BlueprintCategory.HARDPOINT),
    FRAGMENT_CANNON(BlueprintCategory.HARDPOINT),
    MINE_LAUNCHER(BlueprintCategory.HARDPOINT),
    MISSILE_RACK(BlueprintCategory.HARDPOINT),
    MULTI_CANNON(BlueprintCategory.HARDPOINT),
    PLASMA_ACCELERATOR(BlueprintCategory.HARDPOINT),
    PULSE_LASER(BlueprintCategory.HARDPOINT),
    PULSE_DISRUPTOR_LASER(BlueprintCategory.HARDPOINT),
    RAIL_GUN(BlueprintCategory.HARDPOINT),
    SEEKER_MISSILE_RACK(BlueprintCategory.HARDPOINT),
    TORPEDO_PYLON(BlueprintCategory.HARDPOINT),
    //utility
    CHAFF_LAUNCHER(BlueprintCategory.UTILITY_MOUNT),
    ELECTRONIC_COUNTERMEASURE(BlueprintCategory.UTILITY_MOUNT),
    FRAME_SHIFT_WAKE_SCANNER(BlueprintCategory.UTILITY_MOUNT),
    HEAT_SINK_LAUNCHER(BlueprintCategory.UTILITY_MOUNT),
    CAUSTIC_SINK_LAUNCHER(BlueprintCategory.UTILITY_MOUNT),
    KILL_WARRANT_SCANNER(BlueprintCategory.UTILITY_MOUNT),
    MANIFEST_SCANNER(BlueprintCategory.UTILITY_MOUNT),
    POINT_DEFENCE(BlueprintCategory.UTILITY_MOUNT),
    SHIELD_BOOSTER(BlueprintCategory.UTILITY_MOUNT),
    //core
    ARMOUR(BlueprintCategory.CORE_INTERNAL),
    FRAME_SHIFT_DRIVE(BlueprintCategory.CORE_INTERNAL),
    LIFE_SUPPORT(BlueprintCategory.CORE_INTERNAL),
    POWER_DISTRIBUTOR(BlueprintCategory.CORE_INTERNAL),
    POWER_PLANT(BlueprintCategory.CORE_INTERNAL),
    SENSORS(BlueprintCategory.CORE_INTERNAL),
    THRUSTERS(BlueprintCategory.CORE_INTERNAL),
    //optional
    AUTO_FIELD_MAINTENANCE_UNIT(BlueprintCategory.OPTIONAL_INTERNAL),
    COLLECTOR_LIMPET_CONTROLLER(BlueprintCategory.OPTIONAL_INTERNAL),
    DETAILED_SURFACE_SCANNER(BlueprintCategory.OPTIONAL_INTERNAL),
    FRAME_SHIFT_DRIVE_INTERDICTOR(BlueprintCategory.OPTIONAL_INTERNAL),
    FUEL_SCOOP(BlueprintCategory.OPTIONAL_INTERNAL),
    FUEL_TRANSFER_LIMPET_CONTROLLER(BlueprintCategory.OPTIONAL_INTERNAL),
    HATCH_BREAKER_LIMPET_CONTROLLER(BlueprintCategory.OPTIONAL_INTERNAL),
    HULL_REINFORCEMENT_PACKAGE(BlueprintCategory.OPTIONAL_INTERNAL),
    PROSPECTOR_LIMPET_CONTROLLER(BlueprintCategory.OPTIONAL_INTERNAL),
    REFINERY(BlueprintCategory.OPTIONAL_INTERNAL),
    SHIELD_CELL_BANK(BlueprintCategory.OPTIONAL_INTERNAL),
    SHIELD_GENERATOR(BlueprintCategory.OPTIONAL_INTERNAL),
    //Synthesis
    AFM_REFILL(BlueprintCategory.SYNTHESIS),
    AX_EXPLOSIVE_MUNITIONS(BlueprintCategory.SYNTHESIS),
    AX_REMOTE_FLAK_MUNITIONS(BlueprintCategory.SYNTHESIS),
    AX_SMALL_CALIBRE_MUNITIONS(BlueprintCategory.SYNTHESIS),
    CHAFF(BlueprintCategory.SYNTHESIS),
    CONFIGURABLE_EXPLOSIVE_MUNITIONS(BlueprintCategory.SYNTHESIS),
    CONFIGURABLE_SMALL_CALIBRE_MUNITIONS(BlueprintCategory.SYNTHESIS),
    ENZYME_MISSILE_LAUNCHER_MUNITIONS(BlueprintCategory.SYNTHESIS),
    EXPLOSIVE_MUNITIONS(BlueprintCategory.SYNTHESIS),
    FLECHETTE_LAUNCHER_MUNITIONS(BlueprintCategory.SYNTHESIS),
    FSD_INJECTION(BlueprintCategory.SYNTHESIS),
    GUARDIAN_GAUSS_CANNON_MUNITIONS(BlueprintCategory.SYNTHESIS),
    GUARDIAN_PLASMA_CHARGER_MUNITIONS(BlueprintCategory.SYNTHESIS),
    GUARDIAN_SHARD_CANNON_MUNITIONS(BlueprintCategory.SYNTHESIS),
    HEATSINKS(BlueprintCategory.SYNTHESIS),
    HIGH_VELOCITY_MUNITIONS(BlueprintCategory.SYNTHESIS),
    LARGE_CALIBRE_MUNITIONS(BlueprintCategory.SYNTHESIS),
    LIFE_SUPPORT_SYNTHESIS(BlueprintCategory.SYNTHESIS),
    LIMPETS(BlueprintCategory.SYNTHESIS),
    PLASMA_MUNITIONS(BlueprintCategory.SYNTHESIS),
    SEISMIC_CHARGE_MUNITIONS(BlueprintCategory.SYNTHESIS),
    SHOCK_CANNON_MUNITIONS(BlueprintCategory.SYNTHESIS),
    SMALL_CALIBRE_MUNITIONS(BlueprintCategory.SYNTHESIS),
    SRV_AMMO_RESTOCK(BlueprintCategory.SYNTHESIS),
    SRV_REFUEL(BlueprintCategory.SYNTHESIS),
    CAUSTIC_SINKS(BlueprintCategory.SYNTHESIS),
    SRV_REPAIR(BlueprintCategory.SYNTHESIS),
    SUB_SURFACE_DISPLACEMENT_MUNITIONS(BlueprintCategory.SYNTHESIS),
    //Techbroker
    GUARDIAN_MODULES(BlueprintCategory.TECHBROKER),
    GUARDIAN_WEAPONS(BlueprintCategory.TECHBROKER),
    GUARDIAN_FIGHTERS(BlueprintCategory.TECHBROKER),
    HUMAN_WEAPONS(BlueprintCategory.TECHBROKER),
    HUMAN_MODULES(BlueprintCategory.TECHBROKER),
    HUMAN_LIVERY(BlueprintCategory.TECHBROKER),
    NONE(BlueprintCategory.TECHBROKER),
    CARGO_RACK(BlueprintCategory.OPTIONAL_INTERNAL),
    FUEL_TANK(BlueprintCategory.OPTIONAL_INTERNAL),
    FRAME_SHIFT_DRIVE_BOOSTER(BlueprintCategory.OPTIONAL_INTERNAL);
    private final BlueprintCategory blueprintCategory;

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
}
