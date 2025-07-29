package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.Location;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.service.LocationService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public enum HorizonsMaterialSpawnLocation implements SpawnLocation {
    HOT_JUPITER(List.of(new Location(new StarSystem("Col 285 Sector RF-C b14-7", -164.0625, 12.84375, -55.90625), "B1", null, null, null, null))),
    DAVS_HOPE(List.of(new Location(new StarSystem("Hyades Sector DR-V c2-23", -104.625, -0.8125, -151.90625), "A5", null, null, null, null))),
    BRAIN_TREES_SELENIUM(List.of(
            new Location(new StarSystem("HR 3230", 394.15625, 19.25, -152.375), "3AA", null, null, null, null),
            new Location(new StarSystem("Synuefe SE-V b49-4", 387.78125, -180.90625, -4.5625), "B3A @ -51.48, 63.90", null, null, null, null))),
    BRAIN_TREES_POLONIUM(List.of(new Location(new StarSystem("Synuefe AA-P c22-7", 420.75, -172.78125, -75.0625), "5C", null, null, null, null))),
    BRAIN_TREES_RUTHENIUM(List.of(new Location(new StarSystem("35 G. Carinae", 395.5, -126.59375, -44.3125), "2C", null, null, null, null))),
    BRAIN_TREES_TELLURIUM(List.of(new Location(new StarSystem("Synuefe SE-V b49-4", 387.78125, -180.90625, -4.5625), "B3A @ -50.97, 63.98", null, null, null, null))),
    BRAIN_TREES_TECHNETIUM(List.of(
            new Location(new StarSystem("35 G. Carinae", 395.5, -126.59375, -44.3125), "2A", null, null, null, null),
            new Location(new StarSystem("HR 3230", 394.15625, 19.25, -152.375), "3AA", null, null, null, null))),
    BRAIN_TREES_YTTRIUM(List.of(new Location(new StarSystem("35 G. Carinae", 395.5, -126.59375, -44.3125), "2D", null, null, null, null))),
    BRAIN_TREES_ANTIMONY(List.of(new Location(new StarSystem("35 G. Carinae", 395.5, -126.59375, -44.3125), "1E", null, null, null, null))),
    JAMESON_CRASH_SITE(List.of(new Location(new StarSystem("HIP 12099", -101.90625, -95.46875, -165.59375), "1B", null, null, null, null))),
    MISSION_REWARD(null),
    SCAN_MISSIONS(null),

    HGE_FEDERATION(null),
    HGE_EMPIRE(null),
    HGE_CIVIL_UNREST(null),
    HGE_WAR_CIVIL_WAR(null),
    HGE_BOOM_EXPANSION(null),
    HGE_OUTBREAK(null),

    CRYSTAL_SHARDS_POLONIUM(List.of(new Location(new StarSystem("HIP 36601", 337.8125, 562.96875, -1457.84375), "C1A", null, null, null, null))),
    CRYSTAL_SHARDS_RUTHENIUM(List.of(
            new Location(new StarSystem("HIP 36601", 337.8125, 562.96875, -1457.84375), "C1D", null, null, null, null),
            new Location(new StarSystem("Outotz LS-K d8-3", 526.3125, 238.46875, -1613.75), "B7B", null, null, null, null))),
    CRYSTAL_SHARDS_TELLURIUM(List.of(new Location(new StarSystem("HIP 36601", 337.8125, 562.96875, -1457.84375), "C3B", null, null, null, null))),
    CRYSTAL_SHARDS_TECHNETIUM(List.of(new Location(new StarSystem("HIP 36601", 337.8125, 562.96875, -1457.84375), "C5A", null, null, null, null))),
    CRYSTAL_SHARDS_YTTRIUM(List.of(new Location(new StarSystem("Outotz LS-K d8-3", 526.3125, 238.46875, -1613.75), "B5A", null, null, null, null))),
    CRYSTAL_SHARDS_ANTIMONY(List.of(new Location(new StarSystem("Outotz LS-K d8-3", 526.3125, 238.46875, -1613.75), "B5C", null, null, null, null))),
    RING_ICE(null),
    RING_ALL(null),
    RING_PRISTINE(null),
    ASTEROIDS(null),
    SURFACE_PROSPECTING(null),
    BASIC_SCAN_COMBAT_PIRATE(null),
    BASIC_SCAN_HAULAGE(null),
    BASIC_SCAN_AUTHORITY_MILITARY(null),
    WAKE_SCAN_HIGH(null),
    MEGA_SHIP_HACKING(null),
    SETTLEMENT_DATAPOINT(null),
    ENCODED_EMISSIONS_DATA_BEACON(null),
    DEGRADED_EMISSIONS_DATA_BEACON(null),
    THARGOID_INTERCEPTOR_XENOSCAN(null),
    THARGOID_SCOUT_XENOSCAN(null),
    THARGOID_UPLINK_DEVICE_SCAN(null),
    THARGOID_SCAVENGER_SCAN(null),
    THARGOID_WAKE_SCAN(null),
    GUARDIAN_DATA_TERMINAL(null),
    GUARDIAN_OBELISK(null),
    THARGOID_SCAVENGER(null),
    THARGOID_SENSOR(null),
    THARGOID_SHIP(null),
    GUARDIAN_PANELS(null),
    GUARDIAN_SENTINELS(null),
    ENCODED_EMISSIONS_ANARCHY(null),
    ENCODED_EMISSIONS_HIGH_MED(null),
    ENCODED_EMISSIONS_LOW(null),
    DEGRADED_EMISSIONS(null),
    COMBAT_AFTERMATH(null),
    RES_HAULAGE(null),
    RES_COMBAT(null),
    CZ_AUTHORITY(null),
    CRASHED_SHIP_ANACONDA_005(List.of(
            new Location(new StarSystem("BD+49 1280", -19.84375, 4.625, -49.15625), "B2", null, null, null, null),
            new Location(new StarSystem("Beditjari", -6.53125, -60.9375, 104.34375), "B3A", null, null, null, null),
            new Location(new StarSystem("Belu", -30.3125, 13.3125, 59.34375), "B11A", null, null, null, null),
            new Location(new StarSystem("Furbaide", -28.5625, -10.65625, 28.8125), "4A", null, null, null, null),
            new Location(new StarSystem("HIP 24157", 127.15625, -92.65625, -13.125), "A4A", null, null, null, null),
            new Location(new StarSystem("HIP 42783", 36.125, 69.125, -91.5625), "B2", null, null, null, null),
            new Location(new StarSystem("HIP 48391", -21.03125, 112.90625, -93.53125), "1E", null, null, null, null),
            new Location(new StarSystem("HIP 80221", 60.15625, -7.53125, 109.8125), "1A", null, null, null, null),
            new Location(new StarSystem("HIP 85141", -46.3125, 46.46875, 179.90625), "A4A", null, null, null, null),
            new Location(new StarSystem("Jannonandji", 68.34375, -42.375, 119.09375), "3", null, null, null, null),
            new Location(new StarSystem("Jilnytis", 83.03125, -20.625, 19.96875), "C1A", null, null, null, null),
            new Location(new StarSystem("Koli Discii", 108.84375, 83.53125, -58.71875), "C6A", null, null, null, null),
            new Location(new StarSystem("Kungkalenja", 61.28125, 115.875, -0.5625), "1A", null, null, null, null),
            new Location(new StarSystem("LHS 4046", -36.6875, -72.84375, -0.3125), "5B", null, null, null, null),
            new Location(new StarSystem("LP 675-76", 27.21875, 45.59375, 11.59375), "B2A", null, null, null, null),
            new Location(new StarSystem("LP 804-27", 3.3125, 17.84375, 43.28125), "4B", null, null, null, null),
            new Location(new StarSystem("LTT 606", 0.5625, -78.125, -5.28125), "C1A", null, null, null, null),
            new Location(new StarSystem("LTT 9097", 25.3125, -82.5625, 55.21875), "3A", null, null, null, null),
            new Location(new StarSystem("Lushu", -7.9375, -90.6875, 0.40625), "3", null, null, null, null),
            new Location(new StarSystem("Maiku", 95.03125, -81.65625, -39.40625), "ABC1A", null, null, null, null),
            new Location(new StarSystem("Manindini", 34.1875, -93.71875, 158.03125), "B1", null, null, null, null),
            new Location(new StarSystem("Mingin", -87.9375, 106, 149.09375), "AB3A", null, null, null, null),
            new Location(new StarSystem("Orrere", 68.84375, 48.75, 76.75), "2B", null, null, null, null),
            new Location(new StarSystem("Qudshep", 57.65625, -81.4375, 104.78125), "A5A", null, null, null, null),
            new Location(new StarSystem("Rutu", -16.625, 15.40625, 133.375), "A1", null, null, null, null),
            new Location(new StarSystem("Tamit", -12.0, -76.5625, -132.3125), "3B", null, null, null, null),
            new Location(new StarSystem("Varpet", 84.8125, -20.90625, -1.40625), "7A", null, null, null, null),
            new Location(new StarSystem("Woyeru", 77.5625, -64.09375, -160.15625), "1B", null, null, null, null)));
    @Getter
    private final List<Location> locations;

    public static HorizonsMaterialSpawnLocation forName(final String name) {
        return HorizonsMaterialSpawnLocation.valueOf(name.toUpperCase());
    }

    @Override
    public String getLocalizationKey() {
        return "spawn.location.horizons." + this.name().toLowerCase();
    }

    public boolean hasLocations() {
        return this.locations != null && !this.locations.isEmpty();
    }

    public Location getClosest() {
        if (!hasLocations()) return null;
        final StarSystem currentSystem = LocationService.getCurrentLocation().getStarSystem();
        Optional<Location> closest = locations.stream().min(Comparator.comparing(location -> currentSystem.getDistance(location.getStarSystem())));
        return closest.orElse(null);
    }
}
