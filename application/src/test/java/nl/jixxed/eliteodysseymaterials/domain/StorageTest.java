package nl.jixxed.eliteodysseymaterials.domain;

import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StorageTest {

    @Test
    void constuct() {
        final Storage storage = new Storage();
        Assertions.assertEquals(0, storage.getBackPackValue());
        Assertions.assertEquals(0, storage.getShipLockerValue());
        Assertions.assertEquals(0, storage.getTotalValue());
        Assertions.assertEquals(0, storage.getValue(StoragePool.BACKPACK));
        Assertions.assertEquals(0, storage.getValue(StoragePool.SHIPLOCKER));
    }


    @Test
    void setValueBackPack() {

        final Storage storage = new Storage();
        storage.setValue(10, StoragePool.BACKPACK);
        storage.setValue(12, StoragePool.BACKPACK);

        Assertions.assertEquals(12, storage.getBackPackValue());
        Assertions.assertEquals(0, storage.getShipLockerValue());
        Assertions.assertEquals(12, storage.getTotalValue());
        Assertions.assertEquals(12, storage.getValue(StoragePool.BACKPACK));
        Assertions.assertEquals(0, storage.getValue(StoragePool.SHIPLOCKER));
    }

    @Test
    void setValueShipLocker() {

        final Storage storage = new Storage();
        storage.setValue(10, StoragePool.SHIPLOCKER);
        storage.setValue(12, StoragePool.SHIPLOCKER);

        Assertions.assertEquals(0, storage.getBackPackValue());
        Assertions.assertEquals(12, storage.getShipLockerValue());
        Assertions.assertEquals(12, storage.getTotalValue());
        Assertions.assertEquals(0, storage.getValue(StoragePool.BACKPACK));
        Assertions.assertEquals(12, storage.getValue(StoragePool.SHIPLOCKER));
    }

    @Test
    void setValueShipLockerAndBackPack() {

        final Storage storage = new Storage();
        storage.setValue(10, StoragePool.BACKPACK);
        storage.setValue(12, StoragePool.SHIPLOCKER);

        Assertions.assertEquals(10, storage.getBackPackValue());
        Assertions.assertEquals(12, storage.getShipLockerValue());
        Assertions.assertEquals(22, storage.getTotalValue());
        Assertions.assertEquals(10, storage.getValue(StoragePool.BACKPACK));
        Assertions.assertEquals(12, storage.getValue(StoragePool.SHIPLOCKER));
    }

}