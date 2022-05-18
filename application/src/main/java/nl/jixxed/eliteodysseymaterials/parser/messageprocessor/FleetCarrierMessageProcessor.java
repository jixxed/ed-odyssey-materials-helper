package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.parser.*;
import nl.jixxed.eliteodysseymaterials.service.OrderService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;

public class FleetCarrierMessageProcessor implements MessageProcessor {
    private static final FleetCarrierAssetParser ASSET_PARSER = new FleetCarrierAssetParser();
    private static final FleetCarrierDataParser DATA_PARSER = new FleetCarrierDataParser();
    private static final FleetCarrierGoodParser GOOD_PARSER = new FleetCarrierGoodParser();
    private static final FleetCarrierBuyOrderParser BUY_ORDER_PARSER = new FleetCarrierBuyOrderParser();
    private static final FleetCarrierSellOrderParser SELL_ORDER_PARSER = new FleetCarrierSellOrderParser();

    @Override
    public void process(final JsonNode fleetCarrierMessage) {
        final JsonNode carrierLocker = fleetCarrierMessage.get("carrierLocker");
        final JsonNode orders = fleetCarrierMessage.get("orders");
        StorageService.resetFleetCarrierCounts();
        OrderService.clearOrders();
        if (orders != null) {
            final JsonNode onfootmicroresources = orders.get("onfootmicroresources");
            SELL_ORDER_PARSER.parse(onfootmicroresources.get("sales").elements());
            BUY_ORDER_PARSER.parse(onfootmicroresources.get("purchases").elements());
        }
        if (carrierLocker != null) {
            ASSET_PARSER.parse(carrierLocker.get("assets").elements(), StoragePool.FLEETCARRIER, StorageService.getAssets());
            GOOD_PARSER.parse(carrierLocker.get("goods").elements(), StoragePool.FLEETCARRIER, StorageService.getGoods());
            DATA_PARSER.parse(carrierLocker.get("data").elements(), StoragePool.FLEETCARRIER, StorageService.getData());
        }
        EventService.publish(new StorageEvent(StoragePool.FLEETCARRIER));

    }
}
