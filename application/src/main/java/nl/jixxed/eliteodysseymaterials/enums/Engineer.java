package nl.jixxed.eliteodysseymaterials.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Engineer {

    DOMINO_GREEN("Domino Green", "The Jackrabbit", "Orishis", Specialisation.STRATEGIC, -31, 93.96875, -3.5625),
    HERO_FERRARI("Hero Ferrari", "Nevermore Terrace", "Siris", Specialisation.DYNAMIC, 131.0625, -73.59375, -11.25),
    JUDE_NAVARRO("Jude Navarro", "Marshall's Drift", "Aurai", Specialisation.FORCE, 0.9375, -47.8125, 46.28125),
    KIT_FOWLER("Kit Fowler", "The Last Call", "Capoya", Specialisation.FORCE, -60.65625, 82.4375, -45.0625),
    ODEN_GEIGER("Oden Geiger", "Ankh's Promise", "Candiaei", Specialisation.STRATEGIC, -113.5, -4.9375, 66.84375),
    TERRA_VELASQUEZ("Terra Velasquez", "Rascal's Choice", "Shou Xing", Specialisation.DYNAMIC, -16.28125, -44.53125, 94.375),
    UMA_LASZLO("Uma Laszlo", "Laszlo's Resolve", "Xuane", Specialisation.FORCE, 93.875, -9.25, -32.53125),
    WELLINGTON_BECK("Wellington Beck", "Beck Facility", "Jolapa", Specialisation.STRATEGIC, 100.1875, -41.34375, -78),
    YARDEN_BOND("Yarden Bond", "Salamander Bank", "Bayan", Specialisation.DYNAMIC, -19.96875, 117.625, -90.46875),
    UNKNOWN("UNKNOWN", "UNKNOWN", "UNKNOWN", Specialisation.UNKNOWN, 0, 0, 0);

    private final String name;
    @Getter
    private final String settlement;
    @Getter
    private final String system;
    @Getter
    private final Specialisation specialisation;
    private final double x;
    private final double y;
    private final double z;

    public String friendlyName() {
        return this.name;
    }

    public Double getDistance(final double x, final double y, final double z) {
        return Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2) + Math.pow(this.z - z, 2));
    }

    public String getLocalizationKey() {
        return "engineer.name." + this.name().toLowerCase();
    }
}
