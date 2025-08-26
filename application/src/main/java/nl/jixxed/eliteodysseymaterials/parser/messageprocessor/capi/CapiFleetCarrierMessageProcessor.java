package nl.jixxed.eliteodysseymaterials.parser.messageprocessor.capi;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.parser.*;
import nl.jixxed.eliteodysseymaterials.schemas.capi.fleetcarrier.CapiFleetcarrier;
import nl.jixxed.eliteodysseymaterials.service.OrderService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.UserPreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;

import java.math.BigInteger;

@Slf4j
public class CapiFleetCarrierMessageProcessor implements CapiMessageProcessor<CapiFleetcarrier> {
    private static final FleetCarrierAssetParser ASSET_PARSER = new FleetCarrierAssetParser();
    private static final FleetCarrierDataParser DATA_PARSER = new FleetCarrierDataParser();
    private static final FleetCarrierGoodParser GOOD_PARSER = new FleetCarrierGoodParser();
    private static final FleetCarrierCommodityParser COMMODITY_PARSER = new FleetCarrierCommodityParser();
    private static final FleetCarrierBuyOrderParser BUY_ORDER_PARSER = new FleetCarrierBuyOrderParser();
    private static final FleetCarrierSellOrderParser SELL_ORDER_PARSER = new FleetCarrierSellOrderParser();

    @Override
    public void process(final CapiFleetcarrier capiFleetcarrier) {

        StorageService.resetFleetCarrierCounts();
        OrderService.clearOrders();

        SELL_ORDER_PARSER.parseOdyssey(capiFleetcarrier.getOrders().getOnfootmicroresources().getSales());
        BUY_ORDER_PARSER.parseOdyssey(capiFleetcarrier.getOrders().getOnfootmicroresources().getPurchases());
        SELL_ORDER_PARSER.parse(capiFleetcarrier.getOrders().getCommodities().getSales());
        BUY_ORDER_PARSER.parse(capiFleetcarrier.getOrders().getCommodities().getPurchases());

        ASSET_PARSER.parse(capiFleetcarrier.getCarrierLocker().getAssets(), StoragePool.FLEETCARRIER);
        GOOD_PARSER.parse(capiFleetcarrier.getCarrierLocker().getGoods(), StoragePool.FLEETCARRIER);
        DATA_PARSER.parse(capiFleetcarrier.getCarrierLocker().getData(), StoragePool.FLEETCARRIER);

        COMMODITY_PARSER.parse(capiFleetcarrier.getCargo());
        try {
            final BigInteger marketId = capiFleetcarrier.getMarket().getId();
            UserPreferencesService.setPreference(PreferenceConstants.FLEET_CARRIER_ID, marketId.toString());
        } catch (NullPointerException e) {
            log.error("Fleet carrier market id not found in CAPI message", e);
        }
        EventService.publish(new StorageEvent(StoragePool.FLEETCARRIER));

    }

    @Override
    public Class<CapiFleetcarrier> getMessageClass() {
        return CapiFleetcarrier.class;
    }

    @Override
    public void clear() {
        StorageService.resetFleetCarrierCounts();
        OrderService.clearOrders();
        EventService.publish(new StorageEvent(StoragePool.FLEETCARRIER));
    }
}
