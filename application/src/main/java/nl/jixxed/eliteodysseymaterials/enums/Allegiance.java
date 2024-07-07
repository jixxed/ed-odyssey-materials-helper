package nl.jixxed.eliteodysseymaterials.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@AllArgsConstructor
@Getter
@Slf4j
public enum Allegiance {
    FEDERATION("Federation"),
    EMPIRE("Empire"),
    ALLIANCE("Alliance"),
    INDEPENDENT("Independent"),
    PILOTSFEDERATION("PilotsFederation"),
    NONE("");
    private final String key;

    public static Allegiance forKey(final String name) {
        return Arrays.stream(Allegiance.values()).filter(allegiance -> allegiance.key.equalsIgnoreCase(name)).findFirst().orElse(Allegiance.NONE);
    }


    public String getLocalizationKey() {
        return "allegiance." + this.name().toLowerCase();
    }
}
