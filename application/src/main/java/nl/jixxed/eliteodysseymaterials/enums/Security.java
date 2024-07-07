package nl.jixxed.eliteodysseymaterials.enums;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum Security {
    ANARCHY("$GAlAXY_MAP_INFO_state_anarchy;", "Anarchy"),
    LAWLESS("$GALAXY_MAP_INFO_state_lawless;", "Lawless"),
    HIGH("$SYSTEM_SECURITY_high;", "High"),
    LOW("$SYSTEM_SECURITY_low;", "Low"),
    MEDIUM("$SYSTEM_SECURITY_medium;", "Medium"),
    UNKNOWN("", "Unknown");

    private final String id;
    private final String key;

    public static Security forKey(final String key) {
        return Arrays.stream(Security.values()).filter(security -> security.key.equalsIgnoreCase(key)).findFirst().orElse(Security.UNKNOWN);
    }

    public static Security forId(final String id) {
        return Arrays.stream(Security.values()).filter(security -> security.id.equalsIgnoreCase(id)).findFirst().orElse(Security.UNKNOWN);
    }

    public String getLocalizationKey() {
        return "security." + this.name().toLowerCase();
    }
}
