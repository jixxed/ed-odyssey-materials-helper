package nl.jixxed.eliteodysseymaterials.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.Location;

import java.util.List;

@AllArgsConstructor
public enum Engineer {

    DOMINO_GREEN(Settlement.THE_JACKRABBIT, Specialisation.STRATEGIC, new Location("Orishis", -31, 93.96875, -3.5625), List.of(EngineerPrerequisite.A1)),
    KIT_FOWLER(Settlement.THE_LAST_CALL, Specialisation.FORCE, new Location("Capoya", -60.65625, 82.4375, -45.0625), List.of(EngineerPrerequisite.A2, EngineerPrerequisite.A3)),
    YARDEN_BOND(Settlement.SALAMANDER_BANK, Specialisation.DYNAMIC, new Location("Bayan", -19.96875, 117.625, -90.46875), List.of(EngineerPrerequisite.A4, EngineerPrerequisite.A5)),
    HERO_FERRARI(Settlement.NEVERMORE_TERRACE, Specialisation.DYNAMIC, new Location("Siris", 131.0625, -73.59375, -11.25), List.of(EngineerPrerequisite.B1)),
    WELLINGTON_BECK(Settlement.BECK_FACILITY, Specialisation.STRATEGIC, new Location("Jolapa", 100.1875, -41.34375, -78), List.of(EngineerPrerequisite.B2, EngineerPrerequisite.B3)),
    UMA_LASZLO(Settlement.LASZLOS_RESOLVE, Specialisation.FORCE, new Location("Xuane", 93.875, -9.25, -32.53125), List.of(EngineerPrerequisite.B4, EngineerPrerequisite.B5)),
    JUDE_NAVARRO(Settlement.MARSHALLS_DRIFT, Specialisation.FORCE, new Location("Aurai", 0.9375, -47.8125, 46.28125), List.of(EngineerPrerequisite.C1)),
    TERRA_VELASQUEZ(Settlement.RASCALS_CHOICE, Specialisation.DYNAMIC, new Location("Shou Xing", -16.28125, -44.53125, 94.375), List.of(EngineerPrerequisite.C2, EngineerPrerequisite.C3)),
    ODEN_GEIGER(Settlement.ANKHS_PROMISE, Specialisation.STRATEGIC, new Location("Candiaei", -113.5, -4.9375, 66.84375), List.of(EngineerPrerequisite.C4, EngineerPrerequisite.C5)),
    BALTANOS(Settlement.THE_DIVINE_APPARATUS, Specialisation.DYNAMIC, new Location("Deriso", -9520.3125, -909.5, 19808.75), List.of(EngineerPrerequisite.D1_1)),
    ROSA_DAYETTE(Settlement.ROSAS_SHOP, Specialisation.STRATEGIC, new Location("Kojeara", -9513.09375, -908.84375, 19814.28125), List.of(EngineerPrerequisite.D1_2)),
    ELEANOR_BRESA(Settlement.BRESA_MODIFICATIONS, Specialisation.FORCE, new Location("Desy", -9534.21875, -912.21875, 19792.375), List.of(EngineerPrerequisite.D1_3)),
    YI_SHEN(Settlement.EIDOLON_HOLD, Specialisation.STRATEGIC, new Location("Einheriar", -9557.8125, -880.1875, 19801.5625), List.of(EngineerPrerequisite.D2, EngineerPrerequisite.D3)),
    UNKNOWN(Settlement.UNKNOWN, Specialisation.UNKNOWN, new Location("UNKNOWN", 0, 0, 0), List.of());

    @Getter
    private final Settlement settlement;
    @Getter
    private final Specialisation specialisation;
    @Getter
    private final Location location;
    @Getter
    private final List<EngineerPrerequisite> prerequisites;


    public Double getDistance(final double x, final double y, final double z) {
        return Math.sqrt(Math.pow(this.getLocation().getX() - x, 2) + Math.pow(this.getLocation().getY() - y, 2) + Math.pow(this.getLocation().getZ() - z, 2));
    }

    public Double getDistance(final Location location) {
        return getDistance(location.getX(), location.getY(), location.getZ());
    }

    public String getLocalizationKey() {
        return "engineer.name." + this.name().toLowerCase();
    }
}
