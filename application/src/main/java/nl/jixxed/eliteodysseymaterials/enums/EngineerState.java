package nl.jixxed.eliteodysseymaterials.enums;

public enum EngineerState {
    UNKNOWN, KNOWN, INVITED, UNLOCKED, UNDEFINED;

    public static EngineerState forName(final String name) {
        try {
            return EngineerState.valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException ex) {
            return EngineerState.UNKNOWN;
        }
    }
}
