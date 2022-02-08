package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.Encoded;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;

import java.util.Map;

@Slf4j
public class EncodedParser implements HorizonsParser {

    @Override
    public void parse(final JsonNode item, final Map<HorizonsMaterial, Integer> storage) {
        final String name = item.get("Name").asText();
        final Encoded encoded = Encoded.forName(name);
        final int amount = item.get("Count").asInt();
        if (Encoded.UNKNOWN.equals(encoded)) {
            log.warn("Unknown Encoded data detected: " + item.toPrettyString());
        } else {
            storage.put(encoded, storage.get(encoded) + amount);
        }
    }
}
