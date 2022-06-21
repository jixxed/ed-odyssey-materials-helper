package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.databind.JsonNode;
import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.service.OrderService;

import java.util.Iterator;

public class FleetCarrierSellOrderParser {
    public void parse(final Iterator<JsonNode> sales, final Expansion expansion) {
        if (Expansion.HORIZONS.equals(expansion)) {
            sales.forEachRemaining(jsonNode ->
                    OrderService.addSellOrder(HorizonsMaterial.subtypeForName(jsonNode.get("name").asText()), jsonNode.get("price").asInt(), jsonNode.get("stock").asInt())
            );
        } else {
            sales.forEachRemaining(jsonNode ->
                    OrderService.addSellOrder(OdysseyMaterial.subtypeForName(jsonNode.get("name").asText()), jsonNode.get("price").asInt(), jsonNode.get("stock").asInt())
            );
        }
    }
}
