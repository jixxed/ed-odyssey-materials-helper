package nl.jixxed.eliteodysseymaterials.enums;

public enum Suit implements Equipment {
    MAVERICK, DOMINATOR, ARTEMIS;

    public String getImage() {
        return "/images/suit/" + name().toLowerCase() + ".png";
    }
}
