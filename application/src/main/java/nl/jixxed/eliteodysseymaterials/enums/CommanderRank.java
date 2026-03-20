package nl.jixxed.eliteodysseymaterials.enums;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
@RequiredArgsConstructor
public enum CommanderRank {
    COMBAT("Combat"),
    TRADE("Trade"),
    EXPLORE("Explore"),
    SOLDIER("Soldier"),
    EXOBIOLOGIST("Exobiologist"),
    EMPIRE("Empire"),
    FEDERATION("Federation"),
    CQC("CQC");
    private final String key;

    public static CommanderRank forKey(final String name) {
        return Arrays.stream(CommanderRank.values())
                .filter(commanderRank  -> commanderRank.key.equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown CommanderRank: " + name));
    }

    public String getLocalizationKey() {
        return "commander.rank." + this.name().toLowerCase();
    }
}
