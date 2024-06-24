package nl.jixxed.eliteodysseymaterials.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@AllArgsConstructor
@Getter
@Slf4j
public enum SystemAllegiance {
    FEDERATION("Federation"),
    EMPIRE("Empire"),
    ALLIANCE("Alliance"),
    INDEPENDENT("Independent"),
    UNKNOWN("");
    private final String key;

    public static SystemAllegiance forKey(final String name) {
        return Arrays.stream(SystemAllegiance.values()).filter(systemAllegiance -> systemAllegiance.key.equalsIgnoreCase(name)).findFirst().orElse(SystemAllegiance.UNKNOWN);
    }
}
