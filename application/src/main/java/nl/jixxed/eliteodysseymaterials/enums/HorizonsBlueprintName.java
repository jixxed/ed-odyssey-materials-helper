package nl.jixxed.eliteodysseymaterials.enums;

import nl.jixxed.eliteodysseymaterials.service.LocaleService;

public enum HorizonsBlueprintName implements BlueprintName {
    ENGINEER_A,
    ENGINEER_A1,
    ENGINEER_A2,
    ENGINEER_A3,
    ENGINEER_A3A,
    ENGINEER_A3B,
    ENGINEER_A3C,
    ENGINEER_B,
    ENGINEER_B1,
    ENGINEER_B2,
    ENGINEER_B2A,
    ENGINEER_C,
    ENGINEER_C1,
    ENGINEER_C1A,
    ENGINEER_C1B,
    ENGINEER_D,
    ENGINEER_D1,
    ENGINEER_D2,
    ENGINEER_D2A,
    ENGINEER_D2B,
    ENGINEER_E,
    ENGINEER_E1,
    ENGINEER_E2,
    ENGINEER_E2A,
    ENGINEER_E2B,
    //hardpoint
    BEAM_LASER,
    BURST_LASER,
    CANNON,
    FRAGMENT_CANNON,
    MINE_LAUNCHER,
    MISSILE_RACK,
    MULTI_CANNON,
    PLASMA_ACCELERATOR,
    PULSE_LASER,
    RAIL_GUN,
    SEEKER_MISSILE_RACK,
    TORPEDO_PYLON,
    //utility
    CHAFF_LAUNCHER,
    ELECTRONIC_COUNTERMEASURE,
    FRAME_SHIFT_WAKE_SCANNER,
    HEAT_SINK_LAUNCHER,
    KILL_WARRANT_SCANNER,
    MANIFEST_SCANNER,
    POINT_DEFENCE,
    SHIELD_BOOSTER,
    //core
    ARMOUR,
    FRAME_SHIFT_DRIVE,
    LIFE_SUPPORT,
    POWER_DISTRIBUTOR,
    POWER_PLANT,
    SENSORS,
    THRUSTERS,
    //optional
    AUTO_FIELD_MAINTENANCE_UNIT,
    COLLECTOR_LIMPET_CONTROLLER,
    DETAILED_SURFACE_SCANNER,
    FRAME_SHIFT_DRIVE_INTERDICTOR,
    FUEL_SCOOP,
    FUEL_TRANSFER_LIMPET_CONTROLLER,
    HATCH_BREAKER_LIMPET_CONTROLLER,
    HULL_REINFORCEMENT_PACKAGE,
    PROSPECTOR_LIMPET_CONTROLLER,
    REFINERY,
    SHIELD_CELL_BANK,
    SHIELD_GENERATOR,
    NONE,
    //Synthesis
    AFM_REFILL,
    AX_EXPLOSIVE_MUNITIONS,
    AX_REMOTE_FLAK_MUNITIONS,
    AX_SMALL_CALIBRE_MUNITIONS,
    CHAFF,
    CONFIGURABLE_EXPLOSIVE_MUNITIONS,
    CONFIGURABLE_SMALL_CALIBRE_MUNITIONS,
    ENZYME_MISSILE_LAUNCHER_MUNITIONS,
    EXPLOSIVE_MUNITIONS,
    FLECHETTE_LAUNCHER_MUNITIONS,
    FSD_INJECTION,
    GUARDIAN_GAUSS_CANNON_MUNITIONS,
    GUARDIAN_PLASMA_CHARGER_MUNITIONS,
    GUARDIAN_SHARD_CANNON_MUNITIONS,
    HEATSINKS,
    HIGH_VELOCITY_MUNITIONS,
    LARGE_CALIBRE_MUNITIONS,
    LIFE_SUPPORT_SYNTHESIS,
    LIMPETS,
    PLASMA_MUNITIONS,
    SEISMIC_CHARGE_MUNITIONS,
    SHOCK_CANNON_MUNITIONS,
    SMALL_CALIBRE_MUNITIONS,
    SRV_AMMO_RESTOCK,
    SRV_REFUEL,
    SRV_REPAIR,
    SUB_SURFACE_DISPLACEMENT_MUNITIONS,
    GUARDIAN_MODULES,
    GUARDIAN_WEAPONS,
    GUARDIAN_FIGHTERS,
    HUMAN_WEAPONS,
    HUMAN_MODULES,
    HUMAN_LIVERY;


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

    @Override
    public String getLocalizationKey() {
        return "blueprint.horizons.name." + this.name().toLowerCase();
    }

    @Override
    public String getDescriptionLocalizationKey() {
        return "blueprint.horizons.description." + this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }
}
