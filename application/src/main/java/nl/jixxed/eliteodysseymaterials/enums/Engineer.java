package nl.jixxed.eliteodysseymaterials.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Engineer {

    DOMINO_GREEN("Domino Green", "The Jackrabbit", "Orishis", Specialisation.STRATEGIC),
    HERO_FERRARI("Hero Ferrari", "Nevermore Terrace", "Siris", Specialisation.DYNAMIC),
    JUDE_NAVARRO("Jude Navarro", "Marshall's Drift", "Aurai", Specialisation.FORCE),
    KIT_FOWLER("Kit Fowler", "The Last Call", "Capoya", Specialisation.FORCE),
    ODEN_GEIGER("Oden Geiger", "Ankh's Promise", "Candiaei", Specialisation.STRATEGIC),
    TERRA_VELASQUEZ("Terra Velasquez", "Rascal's Choice", "Shou Xing", Specialisation.DYNAMIC),
    UMA_LASZLO("Uma Laszlo", "Laszlo's Resolve", "Xuane", Specialisation.FORCE),
    WELLINGTON_BECK("Wellington Beck", "Beck Facility", "Jolapa", Specialisation.STRATEGIC),
    YARDEN_BOND("Yarden Bond", "Salamander Bank", "Bayan", Specialisation.DYNAMIC),
    UNKNOWN("UNKNOWN", "UNKNOWN", "UNKNOWN", Specialisation.UNKNOWN);

    String name;
    String settlement;
    String system;
    Specialisation specialisation;

    public String friendlyName() {
        return this.name;
    }

    public String getSettlement() {
        return this.settlement;
    }

    public String getSystem() {
        return this.system;
    }
}
