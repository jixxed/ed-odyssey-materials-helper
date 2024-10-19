package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.service.OrderService;
import nl.jixxed.eliteodysseymaterials.service.ReportService;

import java.util.Iterator;

@Slf4j
public class FleetCarrierBuyOrderParser {
    public void parse(final Iterator<JsonNode> purchases, final Expansion expansion) {
        if (Expansion.HORIZONS.equals(expansion)) {
            purchases.forEachRemaining(jsonNode -> {
                try {
                    OrderService.addBuyOrder(HorizonsMaterial.subtypeForName(jsonNode.get("name").asText()), jsonNode.get("price").asInt(), jsonNode.get("total").asInt(), jsonNode.get("outstanding").asInt());
                } catch (final IllegalArgumentException e) {
                    log.error(e.getMessage());
                    ReportService.reportMaterial(jsonNode);
                    //NotificationService.showWarning(NotificationType.ERROR, "Unknown Material Detected", jsonNode.get("name").asText() + "\nPlease report!");
                }
            });
        } else {
            purchases.forEachRemaining(jsonNode -> {
                try {
                    OrderService.addBuyOrder(OdysseyMaterial.subtypeForName(jsonNode.get("name").asText()), jsonNode.get("price").asInt(), jsonNode.get("total").asInt(), jsonNode.get("outstanding").asInt());
                } catch (final IllegalArgumentException e) {
                    log.error(e.getMessage());
                    ReportService.reportMaterial(jsonNode);
                    //NotificationService.showWarning(NotificationType.ERROR, "Unknown Material Detected", jsonNode.get("name").asText() + "\nPlease report!");
                }
            });
        }
    }
}
