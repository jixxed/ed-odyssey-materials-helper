package nl.jixxed.eliteodysseymaterials.models;

import nl.jixxed.eliteodysseymaterials.enums.ContainerTarget;

public class Container {
    private Integer backPack;
    private Integer shipLocker;

    public Container() {
        this.backPack = 0;
        this.shipLocker = 0;
    }

    public Integer getBackPackValue() {
        return this.backPack;
    }

    public Integer getShipLockerValue() {
        return this.shipLocker;
    }

    public void setValue(final Integer value, final ContainerTarget target) {
        switch (target) {
            case BACKPACK -> this.backPack = value;
            case SHIPLOCKER -> this.shipLocker = value;
        }
    }

    public Integer getTotalValue() {
        return this.backPack + this.shipLocker;
    }

    public Integer getValue(final ContainerTarget target) {
        return switch (target) {
            case BACKPACK -> this.backPack;
            case SHIPLOCKER -> this.shipLocker;
        };
    }
}
