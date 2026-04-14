/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.service.carrier;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.enums.CarrierDockingAccess;
import nl.jixxed.eliteodysseymaterials.enums.CarrierState;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.service.OrderService;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIcon;

import java.math.BigInteger;

@Setter
@Getter
public class FleetCarrierInfoProvider implements CarrierInfoProvider {
    @Getter(AccessLevel.PRIVATE)
    private final BooleanProperty fleetCarrier = new SimpleBooleanProperty(false);
    private String carrierName;
    private CarrierState carrierState;
    private CarrierDockingAccess carrierDockingAccess;
    private Boolean carrierNotoriousAccess;
    private BigInteger carrierBalance;
    private BigInteger carrierBankBalance;
    private Integer carrierFuel;
    private String carrierCallSign;
    private Integer shipPacks;
    private Integer modulePacks;
    private Integer cargo;
    private Integer cargoSpaceReserved;
    private Integer crew;
    private Integer freeSpace;
    private Integer microresourceCapacityTotal;
    private Integer microresourceCapacityFree;
    private Integer microresourceCapacityUsed;
    private Integer microresourceCapacityReserved;
    private Integer squadronBankTotal;

    @Override
    public String getCarrierTitle() {
        return "statusbar.fleetcarrier";
    }

    @Override
    public EdAwesomeIcon getCarrierIcon() {
        return EdAwesomeIcon.OTHER_CARRIER_SIMPLE;
    }

    @Override
    public Integer getCarrierOrders() {
        return OrderService.getBuyOrderOutstandingCount(StoragePool.FLEETCARRIER);
    }

    @Override
    public BooleanProperty carrierExistsProperty() {
        return fleetCarrier;
    }

    @Override
    public Integer getUsedSpace() {
        if (freeSpace == null) {
            return null;
        }
        return 25000 - freeSpace;
    }
}
