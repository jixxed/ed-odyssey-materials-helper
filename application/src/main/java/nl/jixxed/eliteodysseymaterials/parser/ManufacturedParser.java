package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.enums.Manufactured;

import java.util.Map;

@Slf4j
public class ManufacturedParser implements HorizonsParser {

    @Override
    public void parse(final JsonNode item, final Map<HorizonsMaterial, Integer> storage) {
        final String name = item.get("Name").asText();
        final Manufactured manufactured = Manufactured.forName(name);
        final int amount = item.get("Count").asInt();
        if (Manufactured.UNKNOWN.equals(manufactured)) {
            log.warn("Unknown Manufactured material detected: " + item.toPrettyString());
        } else {
            storage.put(manufactured, storage.get(manufactured) + amount);
        }
    }
}
