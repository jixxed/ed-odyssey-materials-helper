package nl.jixxed.eliteodysseymaterials.enums;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum Government {
    ANARCHY("$government_Anarchy;", "Anarchy"),
    COMMUNISM("$government_Communism;", "Communism"),
    CONFEDERACY("$government_Confederacy;", "Confederacy"),
    COOPERATIVE("$government_Cooperative;", "Cooperative"),
    CORPORATE("$government_Corporate;", "Corporate"),
    DEMOCRACY("$government_Democracy;", "Democracy"),
    DICTATORSHIP("$government_Dictatorship;", "Dictatorship"),
    FEUDAL("$government_Feudal;", "Feudal"),
    IMPERIAL("$government_Imperial;", "Imperial"),
    PATRONAGE("$government_Patronage;", "Patronage"),
    PRISONCOLONY("$government_PrisonColony;", "PrisonColony"),
    THEOCRACY("$government_Theocracy;", "Theocracy"),
    ENGINEER("$government_Engineer;", "Engineer"),
    PRIVATEOWNERSHIP("$government_Carrier;", "PrivateOwnership"),
    NONE("$government_None;", "None"),
    UNKNOWN("", "Unknown");
    private final String id;
    private final String key;

    public static Government forKey(final String key) {
        return Arrays.stream(Government.values()).filter(factionState -> factionState.key.equalsIgnoreCase(key)).findFirst().orElse(Government.UNKNOWN);
    }

    public static Government forId(final String id) {
        return Arrays.stream(Government.values()).filter(factionState -> factionState.id.equalsIgnoreCase(id)).findFirst().orElse(Government.UNKNOWN);
    }

    public String getLocalizationKey() {
        return "government." + this.name().toLowerCase();
    }
}
