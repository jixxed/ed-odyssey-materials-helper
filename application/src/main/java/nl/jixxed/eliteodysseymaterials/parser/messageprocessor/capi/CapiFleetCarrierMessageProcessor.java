package nl.jixxed.eliteodysseymaterials.parser.messageprocessor.capi;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.parser.*;
import nl.jixxed.eliteodysseymaterials.service.OrderService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.UserPreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;

@Slf4j
public class CapiFleetCarrierMessageProcessor implements CapiMessageProcessor {
    private static final FleetCarrierAssetParser ASSET_PARSER = new FleetCarrierAssetParser();
    private static final FleetCarrierDataParser DATA_PARSER = new FleetCarrierDataParser();
    private static final FleetCarrierGoodParser GOOD_PARSER = new FleetCarrierGoodParser();
    private static final FleetCarrierCommodityParser COMMODITY_PARSER = new FleetCarrierCommodityParser();
    private static final FleetCarrierBuyOrderParser BUY_ORDER_PARSER = new FleetCarrierBuyOrderParser();
    private static final FleetCarrierSellOrderParser SELL_ORDER_PARSER = new FleetCarrierSellOrderParser();

    @Override
    public void process(final JsonNode fleetCarrierMessage) {
        if (fleetCarrierMessage.isNull() || fleetCarrierMessage.isEmpty()) {
            return;
        }
        final JsonNode carrierLocker = fleetCarrierMessage.get("carrierLocker");
        final JsonNode orders = fleetCarrierMessage.get("orders");
        final JsonNode cargo = fleetCarrierMessage.get("cargo");
        StorageService.resetFleetCarrierCounts();
        OrderService.clearOrders();
        if (orders != null) {
            final JsonNode onfootmicroresources = orders.get("onfootmicroresources");
            SELL_ORDER_PARSER.parse(onfootmicroresources.get("sales").elements(), Expansion.ODYSSEY);
            BUY_ORDER_PARSER.parse(onfootmicroresources.get("purchases").elements(), Expansion.ODYSSEY);
            final JsonNode commodities = orders.get("commodities");
            SELL_ORDER_PARSER.parse(commodities.get("sales").elements(), Expansion.HORIZONS);
            BUY_ORDER_PARSER.parse(commodities.get("purchases").elements(), Expansion.HORIZONS);
        }
        if (carrierLocker != null) {
            ASSET_PARSER.parse(carrierLocker.get("assets").elements(), StoragePool.FLEETCARRIER);
            GOOD_PARSER.parse(carrierLocker.get("goods").elements(), StoragePool.FLEETCARRIER);
            DATA_PARSER.parse(carrierLocker.get("data").elements(), StoragePool.FLEETCARRIER);
        }
        if (cargo != null) {
            COMMODITY_PARSER.parse(cargo.elements());
        }
        try {
            final JsonNode market = fleetCarrierMessage.get("market");
            final JsonNode marketId = market.get("id");
            UserPreferencesService.setPreference("fleetcarrier.carrier.id", marketId.asText());
        } catch (NullPointerException e) {
            log.error("Fleet carrier market id not found in CAPI message", e);
        }
        EventService.publish(new StorageEvent(StoragePool.FLEETCARRIER));

    }
}
