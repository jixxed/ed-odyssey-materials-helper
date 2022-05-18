package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.databind.JsonNode;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.service.OrderService;

import java.util.Iterator;

public class FleetCarrierSellOrderParser {
    public void parse(final Iterator<JsonNode> sales) {
        sales.forEachRemaining(jsonNode ->
                OrderService.addSellOrder(OdysseyMaterial.subtypeForName(jsonNode.get("name").asText()), jsonNode.get("price").asInt(), jsonNode.get("stock").asInt())
        );
    }
}
