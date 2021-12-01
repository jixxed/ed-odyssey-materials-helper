package nl.jixxed.eliteodysseymaterials.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum JournalEventType {
    COMMANDER("Commander"),
    ENGINEERPROGRESS("EngineerProgress"),
    EMBARK("Embark"),
    SHIPLOCKER("ShipLocker"),
    BACKPACK("Backpack"),
    BACKPACKCHANGE("BackpackChange"),
    RESUPPLY("Resupply"),
    FSDJUMP("FSDJump"),
    LOCATION("Location"),
    DOCKED("Docked"),
    TOUCHDOWN("Touchdown"),
    UNDOCKED("Undocked"),
    LIFTOFF("Liftoff"),
    APPROACHBODY("ApproachBody"),
    LEAVEBODY("LeaveBody"),
    UNKNOWN("Unknown"),
    COLLECTITEMS("CollectItems");

    private final String name;

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
