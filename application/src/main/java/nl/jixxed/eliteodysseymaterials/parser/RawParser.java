package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.enums.Raw;

import java.util.Map;

@Slf4j
public class RawParser implements HorizonsParser {

    @Override
    public void parse(final JsonNode item, final Map<HorizonsMaterial, Integer> storage) {
        final String name = item.get("Name").asText();
        final Raw raw = Raw.forName(name);
        final int amount = item.get("Count").asInt();
        if (Raw.UNKNOWN.equals(raw)) {
            log.warn("Unknown Raw material detected: " + item.toPrettyString());
        } else {
            storage.put(raw, storage.get(raw) + amount);
        }
    }
}
