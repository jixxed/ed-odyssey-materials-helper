package nl.jixxed.eliteodysseymaterials.enums;

import java.util.Arrays;

public enum State {
    UNKNOWN,
    NONE,
    BOOM,
    BUST,
    CIVILUNREST,
    CIVILWAR,
    ELECTION,
    EXPANSION,
    FAMINE,
    INVESTMENT,
    LOCKDOWN,
    OUTBREAK,
    RETREAT,
    WAR,
    CIVILLIBERTY,
    PIRATEATTACK,
    BLIGHT,
    DROUGHT,
    INFRASTRUCTUREFAILURE,
    NATURALDISASTER,
    PUBLICHOLIDAY,
    TERRORISM,
    COLDWAR,
    COLONISATION,
    HISTORICEVENT,
    REVOLUTION,
    TECHNOLOGICALLEAP,
    TRADEWAR;


    public static State forName(final String name) {
        return Arrays.stream(State.values()).filter(factionState -> factionState.name().equalsIgnoreCase(name)).findFirst().orElse(State.UNKNOWN);
    }


    public String getLocalizationKey() {
        return "state." + this.name().toLowerCase();
    }
}
