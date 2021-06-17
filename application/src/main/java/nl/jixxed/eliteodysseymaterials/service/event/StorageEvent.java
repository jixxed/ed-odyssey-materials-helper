package nl.jixxed.eliteodysseymaterials.service.event;

import nl.jixxed.eliteodysseymaterials.enums.StoragePool;

public class StorageEvent extends Event {
    private final StoragePool storagePool;

    public StorageEvent(final StoragePool storagePool) {
        this.storagePool = storagePool;
    }

    public StoragePool getStoragePool() {
        return this.storagePool;
    }
}
