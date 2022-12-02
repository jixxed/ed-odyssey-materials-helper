package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.service.ReportService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;

public class CargoTransferMessageProcessor implements MessageProcessor {

    @Override
    @SuppressWarnings("java:S1192")
    public void process(final JsonNode journalMessage) {
//        if (journalMessage.get("Inventory") == null) {
//            EventService.publish(new CargoEvent(journalMessage.get("timestamp").asText()));
//            return;
//        }
        journalMessage.get("Transfers").elements().forEachRemaining(transfer -> {
            //{ "Type":"unknownartifact", "Type_Localised":"Thargoid Sensor", "Count":1, "Direction":"tocarrier" }
            if ("tocarrier".equals(transfer.get("Direction").asText())) {
                final Commodity commodity = Commodity.forName(transfer.get("Type").asText());
                if (commodity.isUnknown()) {
                    ReportService.reportMaterial(journalMessage);
                } else {
                    StorageService.removeCommodity(commodity, StoragePool.SHIP, transfer.get("Count").asInt());
                    StorageService.addCommodity(commodity, StoragePool.FLEETCARRIER, transfer.get("Count").asInt());
                }
            } else if ("tosrv".equals(transfer.get("Direction").asText())) {
                final Commodity commodity = Commodity.forName(transfer.get("Type").asText());
                if (commodity.isUnknown()) {
                    ReportService.reportMaterial(journalMessage);
                } else {
                    StorageService.removeCommodity(commodity, StoragePool.SHIP, transfer.get("Count").asInt());
                    StorageService.addCommodity(commodity, StoragePool.SRV, transfer.get("Count").asInt());
                }
            } else if ("toship".equals(transfer.get("Direction").asText())) {
                final Commodity commodity = Commodity.forName(transfer.get("Type").asText());
                if (commodity.isUnknown()) {
                    ReportService.reportMaterial(journalMessage);
                } else {
                    if (ApplicationState.getInstance().playerInSrv()) {
                        StorageService.removeCommodity(commodity, StoragePool.SRV, transfer.get("Count").asInt());
                    } else {
                        StorageService.removeCommodity(commodity, StoragePool.FLEETCARRIER, transfer.get("Count").asInt());
                    }
                    StorageService.addCommodity(commodity, StoragePool.SHIP, transfer.get("Count").asInt());
                }
            }
        });
    }
}