/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.service;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import nl.jixxed.eliteodysseymaterials.schemas.capi.squadron.ShipItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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