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

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.schemas.journal.MarketBuy.MarketBuy;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.UserPreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;

import java.math.BigInteger;

@Slf4j
public class MarketBuyMessageProcessor implements MessageProcessor<MarketBuy> {
    @Override
    public void process(final MarketBuy event) {
        try {
            final HorizonsMaterial horizonsMaterial = HorizonsMaterial.subtypeForName(event.getType());
            if (!horizonsMaterial.isUnknown()) {
                StorageService.addCommodity((Commodity) horizonsMaterial, StoragePool.SHIP, event.getCount().intValue());
                EventService.publish(new StorageEvent(StoragePool.SHIP));
                //if bought from carrier, decrease storage
                if (event.getMarketID().equals(new BigInteger(UserPreferencesService.getPreference(PreferenceConstants.FLEET_CARRIER_ID, "0")))) {
                    StorageService.removeCommodity((Commodity) horizonsMaterial, StoragePool.FLEETCARRIER, event.getCount().intValue());
                    EventService.publish(new StorageEvent(StoragePool.FLEETCARRIER));
                }
            }
        } catch (final IllegalArgumentException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public Class<MarketBuy> getMessageClass() {
        return MarketBuy.class;
    }
}
