/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.CarrierDockingAccess;
import nl.jixxed.eliteodysseymaterials.enums.CarrierType;
import nl.jixxed.eliteodysseymaterials.schemas.journal.CarrierStats.CarrierStats;
import nl.jixxed.eliteodysseymaterials.service.CarrierService;
import nl.jixxed.eliteodysseymaterials.service.UserPreferencesService;

public class CarrierStatsMessageProcessor implements MessageProcessor<CarrierStats> {
    @Override
    public void process(final CarrierStats event) {
        if(event.getCarrierID().toString().equals(UserPreferencesService.getPreference(PreferenceConstants.FLEET_CARRIER_ID, "none"))) {
            updateCarrierInfo(CarrierType.FLEETCARRIER, event);

        } else if(event.getCarrierID().toString().equals(UserPreferencesService.getPreference(PreferenceConstants.SQUADRON_CARRIER_ID, "none"))) {
            updateCarrierInfo(CarrierType.SQUADRONCARRIER, event);
        }
    }

    private static void updateCarrierInfo(CarrierType carrierType, CarrierStats event) {
        CarrierService.carrierExistsProperty(carrierType).set(true);
        CarrierService.setCarrierCallSign(carrierType, event.getCallsign());
        CarrierService.setCarrierName(carrierType, event.getName());
        CarrierService.setCarrierBalance(carrierType, event.getFinance().getCarrierBalance());
        CarrierService.setSpaceUsage(carrierType, event.getSpaceUsage());
        CarrierService.setCarrierNotoriousAccess(carrierType, event.getAllowNotorious());
        CarrierService.setCarrierDockingAccess(carrierType, CarrierDockingAccess.forKey(event.getDockingAccess()));
        CarrierService.setCarrierFuel(carrierType, event.getFuelLevel().intValue());
    }

    @Override
    public Class<CarrierStats> getMessageClass() {
        return CarrierStats.class;
    }

}
