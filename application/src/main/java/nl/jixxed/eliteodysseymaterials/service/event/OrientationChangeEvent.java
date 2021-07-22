package nl.jixxed.eliteodysseymaterials.service.event;

import nl.jixxed.eliteodysseymaterials.enums.MaterialOrientation;

public class OrientationChangeEvent extends Event {
    private final MaterialOrientation materialOrientation;

    public OrientationChangeEvent(final MaterialOrientation materialOrientation) {
        this.materialOrientation = materialOrientation;
    }

    public MaterialOrientation getMaterialOrientation() {
        return this.materialOrientation;
    }
}
