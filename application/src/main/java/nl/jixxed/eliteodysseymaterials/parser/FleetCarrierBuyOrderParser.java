package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.databind.JsonNode;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.service.OrderService;

import java.util.Iterator;

public class FleetCarrierBuyOrderParser {
    public void parse(final Iterator<JsonNode> purchases) {
        purchases.forEachRemaining(jsonNode ->

                OrderService.addBuyOrder(OdysseyMaterial.subtypeForName(jsonNode.get("name").asText()), jsonNode.get("price").asInt(), jsonNode.get("total").asInt(), jsonNode.get("outstanding").asInt())
        );
    }
}
