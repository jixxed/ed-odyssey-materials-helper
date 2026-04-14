/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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