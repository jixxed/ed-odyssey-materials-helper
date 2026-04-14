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
    private Integer squadronCarrier;

    public Storage() {
        this.backPack = 0;
        this.shipLocker = 0;
        this.fleetCarrier = 0;
        this.squadronCarrier = 0;
    }

    public static Storage of(final Integer backPack, final Integer shipLocker) {
        return new Storage(backPack, shipLocker, 0, 0);
    }

    public static Storage of(final Integer backPack, final Integer shipLocker, final Integer fleetCarrier, final Integer squadronCarrier) {
        return new Storage(backPack, shipLocker, fleetCarrier, squadronCarrier);
    }

    public Integer getBackPackValue() {
        return this.backPack;
    }

    public Integer getFleetCarrierValue() {
        return this.fleetCarrier;
    }
    public Integer getSquadronCarrierValue() {
        return this.squadronCarrier;
    }

    public Integer getShipLockerValue() {
        return this.shipLocker;
    }

    /**
     * Sets the value of the storage pool. Only StorageService should call this method.
     */
    public void setValue(final Integer value, final StoragePool pool) {
        if (pool == StoragePool.BACKPACK) {
            this.backPack = value;
        } else if (pool == StoragePool.SHIPLOCKER) {
            this.shipLocker = value;
        } else if (pool == StoragePool.FLEETCARRIER) {
            this.fleetCarrier = value;
        } else if (pool == StoragePool.SQUADRONCARRIER) {
            this.squadronCarrier = value;
        }

    }

    public Integer getTotalValue() {
        return getAvailableValue() + this.fleetCarrier + this.squadronCarrier;
    }

    public Integer getAvailableValue() {
        return this.backPack + this.shipLocker;
    }

    public Integer getValue(final StoragePool target) {
        return switch (target) {
            case BACKPACK -> this.backPack;
            case SHIPLOCKER -> this.shipLocker;
            case FLEETCARRIER -> this.fleetCarrier;
            case SQUADRONCARRIER -> this.squadronCarrier;
            case SHIP -> null;
            case SRV -> null;
        };
    }

}
