package nl.jixxed.eliteodysseymaterials.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@AllArgsConstructor
@Getter
@Slf4j
public enum SystemSecurity {
    NONE("NONE"),
    LOW("$SYSTEM_SECURITY_LOW;"),
    MEDIUM("$SYSTEM_SECURITY_MEDIUM;"),
    HIGH("$SYSTEM_SECURITY_HIGH;"),
    HIGH_ANARCHY("$SYSTEM_SECURITY_HIGH_ANARCHY;"),
    ANARCHY("$GALAXY_MAP_INFO_STATE_ANARCHY;"),
    LAWLESS("$GALAXY_MAP_INFO_STATE_LAWLESS;"),
    UNKNOWN("");
    private final String key;

    public static SystemSecurity forKey(final String name) {
        log.info("SystemSecurity: " + name);
        return Arrays.stream(SystemSecurity.values()).filter(systemSecurity -> systemSecurity.key.equals(name.toUpperCase())).findFirst().orElse(SystemSecurity.UNKNOWN);
    }
}
