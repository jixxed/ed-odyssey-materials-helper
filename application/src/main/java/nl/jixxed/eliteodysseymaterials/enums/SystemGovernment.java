package nl.jixxed.eliteodysseymaterials.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@AllArgsConstructor
@Getter
@Slf4j
public enum SystemGovernment {
    NONE("$GOVERNMENT_NONE;"),
    ANARCHY("$GOVERNMENT_ANARCHY;"),
    COLONY("$GOVERNMENT_COLONY;"),
    COMMUNISM("$GOVERNMENT_COMMUNISM;"),
    CONFEDERACY("$GOVERNMENT_CONFEDERACY;"),
    COOPERATIVE("$GOVERNMENT_COOPERATIVE;"),
    CORPORATE("$GOVERNMENT_CORPORATE;"),
    DEMOCRACY("$GOVERNMENT_DEMOCRACY;"),
    DICTATORSHIP("$GOVERNMENT_DICTATORSHIP;"),
    FEUDAL("$GOVERNMENT_FEUDAL;"),
    IMPERIAL("$GOVERNMENT_IMPERIAL;"),
    PATRONAGE("$GOVERNMENT_PATRONAGE;"),
    PRISON("$GOVERNMENT_PRISON;"),
    PRISON_COLONY("$GOVERNMENT_PRISONCOLONY;"),
    THEOCRACY("$GOVERNMENT_THEOCRACY;"),
    WORKSHOP("$GOVERNMENT_WORKSHOP;"),
    ENGINEER("$GOVERNMENT_ENGINEER;"),
    CARRIER("$GOVERNMENT_CARRIER;"),
    UNKNOWN("");
    private final String key;

    public static SystemGovernment forKey(final String name) {
        return Arrays.stream(SystemGovernment.values()).filter(systemGovernment -> systemGovernment.key.equals(name.toUpperCase())).findFirst().orElse(SystemGovernment.UNKNOWN);
    }
}
