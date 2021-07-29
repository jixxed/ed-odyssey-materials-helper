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

    public Storage() {
        this.backPack = 0;
        this.shipLocker = 0;
    }

    public static Storage of(final Integer backPack, final Integer shipLocker) {
        return new Storage(backPack, shipLocker);
    }

    public Integer getBackPackValue() {
        return this.backPack;
    }

    public Integer getShipLockerValue() {
        return this.shipLocker;
    }

    public void setValue(final Integer value, final StoragePool pool) {
        switch (pool) {
            case BACKPACK -> this.backPack = value;
            case SHIPLOCKER -> this.shipLocker = value;
        }
    }

    public Integer getTotalValue() {
        return this.backPack + this.shipLocker;
    }

    public Integer getValue(final StoragePool target) {
        return switch (target) {
            case BACKPACK -> this.backPack;
            case SHIPLOCKER -> this.shipLocker;
        };
    }
}
