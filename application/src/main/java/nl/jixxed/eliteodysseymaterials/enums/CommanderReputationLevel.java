package nl.jixxed.eliteodysseymaterials.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CommanderReputationLevel {
    ALLIED,
    FRIENDLY,
    CORDIAL,
    NEUTRAL,
    UNFRIENDLY,
    HOSTILE;


    public static CommanderReputationLevel forReputation(final Double reputation) {

        // -100 ..  -90: hostile
        // -90  ..  -35: unfriendly
        // -35  ..   +4: neutral
        // +4   ..  +35: cordial
        // +35  ..  +90: friendly
        // +90  .. +100: allied

        return switch (reputation) {
            case Double i when i >= 90 -> ALLIED;
            case Double i when i >= 35 -> FRIENDLY;
            case Double i when i >= 4 -> CORDIAL;
            case Double i when i >= -35 -> NEUTRAL;
            case Double i when i >= -90 -> UNFRIENDLY;
            default -> HOSTILE;
        };
    }

    public String getLocalizationKey() {
        return "commander.reputation.level." + this.name().toLowerCase();
    }
}
