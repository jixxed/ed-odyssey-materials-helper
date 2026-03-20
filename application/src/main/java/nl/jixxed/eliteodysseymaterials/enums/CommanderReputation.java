package nl.jixxed.eliteodysseymaterials.enums;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
@RequiredArgsConstructor
public enum CommanderReputation {
    FEDERATION("Federation"),
    EMPIRE("Empire"),
    ALLIANCE("Alliance"),
    INDEPENDENT("Independent");
    private final String key;

    public static CommanderReputation forKey(final String name) {
        return Arrays.stream(CommanderReputation.values())
                .filter(commanderReputation -> commanderReputation.key.equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown CommanderReputation: " + name));
    }

    public String getLocalizationKey() {
        return "commander.reputation." + this.name().toLowerCase();
    }
}
