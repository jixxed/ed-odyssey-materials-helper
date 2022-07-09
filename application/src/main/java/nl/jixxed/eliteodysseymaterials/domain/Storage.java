package nl.jixxed.eliteodysseymaterials.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Storage {
    private Integer backPack;
    private Integer shipLocker;
    private Integer fleetCarrier;

    public Storage() {
        this.backPack = 0;
        this.shipLocker = 0;
        this.fleetCarrier = 0;
    }

    public static Storage of(final Integer backPack, final Integer shipLocker) {
        return new Storage(backPack, shipLocker, 0);
    }

    public static Storage of(final Integer backPack, final Integer shipLocker, final Integer fleetCarrier) {
        return new Storage(backPack, shipLocker, fleetCarrier);
    }

    public Integer getBackPackValue() {
        return this.backPack;
    }

    public Integer getFleetCarrierValue() {
        return this.fleetCarrier;
    }

    public Integer getShipLockerValue() {
        return this.shipLocker;
    }

    public void setValue(final Integer value, final StoragePool pool) {
        if (pool == StoragePool.BACKPACK) {
            this.backPack = value;
        } else if (pool == StoragePool.SHIPLOCKER) {
            this.shipLocker = value;
        } else if (pool == StoragePool.FLEETCARRIER) {
            this.fleetCarrier = value;
        }

    }

    public Integer getTotalValue() {
        return getAvailableValue() + this.fleetCarrier;
    }

    public Integer getAvailableValue() {
        return this.backPack + this.shipLocker;
    }

    public Integer getValue(final StoragePool target) {
        return switch (target) {
            case BACKPACK -> this.backPack;
            case SHIPLOCKER -> this.shipLocker;
            case FLEETCARRIER -> this.fleetCarrier;
            case SHIP -> null;
        };
    }
}
