package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.enums.Raw;

import java.util.Map;

@Slf4j
public class RawTradeParser implements HorizonsParser {

    @Override
    public void parse(final JsonNode item, final Map<HorizonsMaterial, Integer> storage) {
        final JsonNode paid = item.get("Paid");
        final String paidName = paid.get("Material").asText();
        final int paidAmount = paid.get("Quantity").asInt();
        final JsonNode received = item.get("Received");
        final String receivedName = received.get("Material").asText();
        final int receivedAmount = received.get("Quantity").asInt();
        final Raw paidMaterial = Raw.forName(paidName);
        if (Raw.UNKNOWN.equals(paidMaterial)) {
            log.warn("Unknown Raw material detected: " + paid.toPrettyString());
        } else {
            storage.put(paidMaterial, storage.get(paidMaterial) - paidAmount);
        }
        final Raw receivedMaterial = Raw.forName(receivedName);
        if (Raw.UNKNOWN.equals(receivedMaterial)) {
            log.warn("Unknown Raw material detected: " + received.toPrettyString());
        } else {
            storage.put(receivedMaterial, storage.get(receivedMaterial) + receivedAmount);
        }
    }
}
