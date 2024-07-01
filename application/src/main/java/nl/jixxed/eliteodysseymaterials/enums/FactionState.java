package nl.jixxed.eliteodysseymaterials.enums;

import java.util.Arrays;

public enum FactionState {
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


    public static FactionState forName(final String name) {
        return Arrays.stream(FactionState.values()).filter(factionState -> factionState.name().equalsIgnoreCase(name)).findFirst().orElse(FactionState.UNKNOWN);
    }


    public String getLocalizationKey() {
        return "faction.state." + this.name().toLowerCase();
    }
}
