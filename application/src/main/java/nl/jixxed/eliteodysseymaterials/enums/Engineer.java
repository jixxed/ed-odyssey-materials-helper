package nl.jixxed.eliteodysseymaterials.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public enum Engineer {

    DOMINO_GREEN("The Jackrabbit", "Orishis", Specialisation.STRATEGIC, -31, 93.96875, -3.5625, List.of(EngineerPrerequisite.A1)),
    KIT_FOWLER("The Last Call", "Capoya", Specialisation.FORCE, -60.65625, 82.4375, -45.0625, List.of(EngineerPrerequisite.A2, EngineerPrerequisite.A3)),
    YARDEN_BOND("Salamander Bank", "Bayan", Specialisation.DYNAMIC, -19.96875, 117.625, -90.46875, List.of(EngineerPrerequisite.A4, EngineerPrerequisite.A5)),
    HERO_FERRARI("Nevermore Terrace", "Siris", Specialisation.DYNAMIC, 131.0625, -73.59375, -11.25, List.of(EngineerPrerequisite.B1)),
    WELLINGTON_BECK("Beck Facility", "Jolapa", Specialisation.STRATEGIC, 100.1875, -41.34375, -78, List.of(EngineerPrerequisite.B2, EngineerPrerequisite.B3)),
    UMA_LASZLO("Laszlo's Resolve", "Xuane", Specialisation.FORCE, 93.875, -9.25, -32.53125, List.of(EngineerPrerequisite.B4, EngineerPrerequisite.B5)),
    JUDE_NAVARRO("Marshall's Drift", "Aurai", Specialisation.FORCE, 0.9375, -47.8125, 46.28125, List.of(EngineerPrerequisite.C1)),
    TERRA_VELASQUEZ("Rascal's Choice", "Shou Xing", Specialisation.DYNAMIC, -16.28125, -44.53125, 94.375, List.of(EngineerPrerequisite.C2, EngineerPrerequisite.C3)),
    ODEN_GEIGER("Ankh's Promise", "Candiaei", Specialisation.STRATEGIC, -113.5, -4.9375, 66.84375, List.of(EngineerPrerequisite.C4, EngineerPrerequisite.C5)),
    UNKNOWN("UNKNOWN", "UNKNOWN", Specialisation.UNKNOWN, 0, 0, 0, List.of());

    @Getter
    private final String settlement;
    @Getter
    private final String system;
    @Getter
    private final Specialisation specialisation;
    private final double x;
    private final double y;
    private final double z;
    @Getter
    private final List<EngineerPrerequisite> prerequisites;


    public Double getDistance(final double x, final double y, final double z) {
        return Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2) + Math.pow(this.z - z, 2));
    }

    public String getLocalizationKey() {
        return "engineer.name." + this.name().toLowerCase();
    }
}
