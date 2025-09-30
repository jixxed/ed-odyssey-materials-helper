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
    private static final CarrierAssetParser ASSET_PARSER = new CarrierAssetParser();
    private static final CarrierDataParser DATA_PARSER = new CarrierDataParser();
    private static final CarrierGoodParser GOOD_PARSER = new CarrierGoodParser();
    private static final CarrierCommodityParser COMMODITY_PARSER = new CarrierCommodityParser();
    private static final CarrierBuyOrderParser BUY_ORDER_PARSER = new CarrierBuyOrderParser();
    private static final CarrierSellOrderParser SELL_ORDER_PARSER = new CarrierSellOrderParser();

    @Override
    public void process(final CapiFleetcarrier capiFleetcarrier) {

        StorageService.resetFleetCarrierCounts();
        OrderService.clearOrders(StoragePool.FLEETCARRIER);

        SELL_ORDER_PARSER.parseOdyssey(capiFleetcarrier.getOrders().getOnfootmicroresources().getSales(), StoragePool.FLEETCARRIER);
        BUY_ORDER_PARSER.parseOdyssey(capiFleetcarrier.getOrders().getOnfootmicroresources().getPurchases(), StoragePool.FLEETCARRIER);
        SELL_ORDER_PARSER.parse(capiFleetcarrier.getOrders().getCommodities().getSales(), StoragePool.FLEETCARRIER);
        BUY_ORDER_PARSER.parse(capiFleetcarrier.getOrders().getCommodities().getPurchases(), StoragePool.FLEETCARRIER);

        ASSET_PARSER.parse(capiFleetcarrier.getCarrierLocker().getAssets(), StoragePool.FLEETCARRIER);
        GOOD_PARSER.parse(capiFleetcarrier.getCarrierLocker().getGoods(), StoragePool.FLEETCARRIER);
        DATA_PARSER.parse(capiFleetcarrier.getCarrierLocker().getData(), StoragePool.FLEETCARRIER);

        COMMODITY_PARSER.parse(capiFleetcarrier.getCargo(), StoragePool.FLEETCARRIER);
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
        OrderService.clearOrders(StoragePool.FLEETCARRIER);
        EventService.publish(new StorageEvent(StoragePool.FLEETCARRIER));
    }
}
