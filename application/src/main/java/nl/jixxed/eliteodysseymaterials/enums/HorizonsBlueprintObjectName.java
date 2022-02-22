package nl.jixxed.eliteodysseymaterials.enums;

import nl.jixxed.eliteodysseymaterials.service.LocaleService;

public enum HorizonsBlueprintObjectName {
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
    NONE;

    public static HorizonsBlueprintObjectName forName(final String name) {
        try {
            return HorizonsBlueprintObjectName.valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException ex) {
            return null;
        }
    }

    public String getLocalizationKey() {
        return "blueprint.horizons.name." + this.name().toLowerCase();
    }

    public String getDescriptionLocalizationKey() {
        return "blueprint.horizons.description." + this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }
}
