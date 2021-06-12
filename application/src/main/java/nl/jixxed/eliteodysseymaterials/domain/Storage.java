package nl.jixxed.eliteodysseymaterials.domain;

import nl.jixxed.eliteodysseymaterials.enums.StoragePool;

import java.util.Objects;

public class Storage {
    private Integer backPack;
    private Integer shipLocker;

    public Storage(final Integer backPack, final Integer shipLocker) {
        this.backPack = backPack;
        this.shipLocker = shipLocker;
    }

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Storage storage = (Storage) o;
        return Objects.equals(this.backPack, storage.backPack) && Objects.equals(this.shipLocker, storage.shipLocker);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.backPack, this.shipLocker);
    }

    @Override
    public String toString() {
        return "Container{" +
                "backPack=" + this.backPack +
                ", shipLocker=" + this.shipLocker +
                '}';
    }
}
