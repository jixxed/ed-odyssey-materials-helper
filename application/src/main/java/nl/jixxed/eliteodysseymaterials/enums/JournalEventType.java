package nl.jixxed.eliteodysseymaterials.enums;

public enum JournalEventType {
    COMMANDER("Commander"),
    ENGINEERPROGRESS("EngineerProgress"),
    EMBARK("Embark"),
    SHIPLOCKER("ShipLocker"),
    BACKPACK("Backpack"),
    UNKNOWN("Unknown");

    String name;

    JournalEventType(final String name) {
        this.name = name;
    }

    public static JournalEventType forName(final String name) {
        try {
            return JournalEventType.valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException ex) {
            return JournalEventType.UNKNOWN;
        }
    }

    public String friendlyName() {
        return this.name;
    }
}
