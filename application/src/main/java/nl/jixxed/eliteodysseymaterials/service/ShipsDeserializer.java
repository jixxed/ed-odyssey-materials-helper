package nl.jixxed.eliteodysseymaterials.service;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import nl.jixxed.eliteodysseymaterials.schemas.capi.squadron.ShipItem;

import java.io.IOException;
import java.util.*;

public class ShipsDeserializer extends JsonDeserializer<Map<String, ShipItem[]>> implements ContextualDeserializer {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        if (property != null && "ships".equals(property.getName())) {
            return this;
        }
        return ctxt.findContextualValueDeserializer(property.getType(), property);
    }

    @Override
    public Map<String, ShipItem[]> deserialize(com.fasterxml.jackson.core.JsonParser p, DeserializationContext ctxt) throws IOException {
        Map<String, ShipItem[]> result = new LinkedHashMap<>();

        JsonNode node = p.getCodec().readTree(p);

        // Iterate over ship categories ("All", "Other", ...)
        node.properties().forEach(category -> {
            List<ShipItem> items = new ArrayList<>();

            // Each entry inside category ("SideWinder", "Type9", ...)
            category.getValue().properties().forEach(entry -> {
                try {
                    ShipItem ship = mapper.treeToValue(entry.getValue(), ShipItem.class);
                    items.add(ship);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            result.put(category.getKey(), items.toArray(new ShipItem[0]));
        });

        return result;
    }

}