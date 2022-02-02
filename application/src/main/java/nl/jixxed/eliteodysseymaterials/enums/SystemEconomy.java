package nl.jixxed.eliteodysseymaterials.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@AllArgsConstructor
@Getter
@Slf4j
public enum SystemEconomy {
    NONE("$ECONOMY_NONE;"),
    AGRICULTURE("$ECONOMY_AGRI;"),
    COLONY("$ECONOMY_COLONY;"),
    DAMAGED("$ECONOMY_DAMAGED;"),
    EXTRACTION("$ECONOMY_EXTRACTION;"),
    REFINERY("$ECONOMY_REFINERY;"),
    REPAIR("$ECONOMY_REPAIR;"),
    RESCUE("$ECONOMY_RESCUE;"),
    INDUSTRIAL("$ECONOMY_INDUSTRIAL;"),
    TERRAFORMING("$ECONOMY_TERRAFORMING;"),
    HIGH_TECH("$ECONOMY_HIGHTECH;"),
    SERVICE("$ECONOMY_SERVICE;"),
    TOURISM("$ECONOMY_TOURISM;"),
    MILITARY("$ECONOMY_MILITARY;"),
    PRISON("$ECONOMY_PRISON;"),
    CARRIER("$ECONOMY_CARRIER;"),
    UNKNOWN("");
    private final String key;

    public static SystemEconomy forKey(final String name) {
        return Arrays.stream(SystemEconomy.values()).filter(systemEconomy -> systemEconomy.key.equals(name.toUpperCase())).findFirst().orElse(SystemEconomy.UNKNOWN);

    }
}
