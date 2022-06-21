package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.service.StorageService;

import java.util.Iterator;

@Slf4j
public class FleetCarrierCommodityParser {
    public void parse(final Iterator<JsonNode> items) {
        items.forEachRemaining(itemNode ->
        {
            final String name = itemNode.get(getNameField()).asText();
            final Commodity commodity = Commodity.forName(name);
            final int amount = itemNode.get(getAmountField()).asInt();
            if (commodity.isUnknown()) {
                log.warn("Unknown Commodity detected: " + itemNode.toPrettyString());
            } else {
                StorageService.addCommodity(commodity, StoragePool.FLEETCARRIER, amount);
            }
        });
    }

    private String getAmountField() {
        return "qty";
    }

    private String getNameField() {
        return "commodity";
    }
}
