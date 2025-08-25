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
