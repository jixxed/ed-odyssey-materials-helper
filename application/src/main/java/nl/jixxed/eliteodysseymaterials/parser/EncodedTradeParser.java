package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.Encoded;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;

import java.util.Map;

@Slf4j
public class EncodedTradeParser implements HorizonsParser {

    @Override
    public void parse(final JsonNode item, final Map<HorizonsMaterial, Integer> storage) {
        final JsonNode paid = item.get("Paid");
        final String paidName = paid.get("Material").asText();
        final int paidAmount = paid.get("Quantity").asInt();
        final JsonNode received = item.get("Received");
        final String receivedName = received.get("Material").asText();
        final int receivedAmount = received.get("Quantity").asInt();
        final Encoded paidMaterial = Encoded.forName(paidName);
        if (Encoded.UNKNOWN.equals(paidMaterial)) {
            log.warn("Unknown Encoded data detected: " + paid.toPrettyString());
        } else {
            storage.put(paidMaterial, storage.get(paidMaterial) - paidAmount);
        }
        final Encoded receivedMaterial = Encoded.forName(receivedName);
        if (Encoded.UNKNOWN.equals(receivedMaterial)) {
            log.warn("Unknown Encoded data detected: " + received.toPrettyString());
        } else {
            storage.put(receivedMaterial, storage.get(receivedMaterial) + receivedAmount);
        }
    }
}
