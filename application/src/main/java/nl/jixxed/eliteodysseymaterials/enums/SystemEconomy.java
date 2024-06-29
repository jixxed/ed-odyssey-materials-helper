package nl.jixxed.eliteodysseymaterials.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@AllArgsConstructor
@Getter
@Slf4j
public enum SystemEconomy {
    NONE("$ECONOMY_NONE;", "None"),
    AGRICULTURE("$ECONOMY_AGRI;", "Agriculture"),
    COLONY("$ECONOMY_COLONY;", "Colony"),
    DAMAGED("$ECONOMY_DAMAGED;", "Damaged"),
    EXTRACTION("$ECONOMY_EXTRACTION;", "Extraction"),
    REFINERY("$ECONOMY_REFINERY;", "Refinery"),
    REPAIR("$ECONOMY_REPAIR;", "Repair"),
    RESCUE("$ECONOMY_RESCUE;", "Rescue"),
    INDUSTRIAL("$ECONOMY_INDUSTRIAL;", "Industrial"),
    TERRAFORMING("$ECONOMY_TERRAFORMING;", "Terraforming"),
    HIGH_TECH("$ECONOMY_HIGHTECH;", "High Tech"),
    SERVICE("$ECONOMY_SERVICE;", "Service"),
    TOURISM("$ECONOMY_TOURISM;", "Tourism"),
    MILITARY("$ECONOMY_MILITARY;", "Military"),
    PRISON("$ECONOMY_PRISON;", "Prison"),
    CARRIER("$ECONOMY_CARRIER;", "Carrier"),
    UNKNOWN("", "Unknown");
    private final String key;
    private final String friendlyName;

    public static SystemEconomy forKey(final String name) {
        return Arrays.stream(SystemEconomy.values()).filter(systemEconomy -> systemEconomy.key.equals(name.toUpperCase())).findFirst().orElse(SystemEconomy.UNKNOWN);

    }
}
