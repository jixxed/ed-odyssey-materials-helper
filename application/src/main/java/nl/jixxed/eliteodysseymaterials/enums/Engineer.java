package nl.jixxed.eliteodysseymaterials.enums;

public enum Engineer {

    DOMINO_GREEN("Domino Green"),
    HERO_FERRARI("Hero Ferrari"),
    JUDE_NAVARRO("Jude Navarro"),
    KIT_FOWLER("Kit Fowler"),
    ODEN_GEIGER("Oden Geiger"),
    TERRA_VELASQUEZ("Terra Velasquez"),
    UMA_LASZLO("Uma Laszlo"),
    WELLINGTON_BECK("Wellington Beck"),
    YARDEN_BOND("Yarden Bond"),
    UNKNOWN("UNKNOWN");

    String name;

    Engineer(final String name) {
        this.name = name;
    }

    public String friendlyName() {
        return this.name;
    }
}
