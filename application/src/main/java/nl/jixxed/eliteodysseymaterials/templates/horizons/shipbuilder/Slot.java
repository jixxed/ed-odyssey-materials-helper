package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import lombok.Getter;
import lombok.Setter;

public class Slot {
    private final Ship parent;
    @Getter
    private final SlotType slotType;
    @Getter
    private final int index;

    @Getter
    @Setter
    private ShipModule shipModule;

    public Slot(final Ship parent, final SlotType slotType, final int index) {
        this.slotType = slotType;
        this.parent = parent;
        this.index = index;
    }
}
