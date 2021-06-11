package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.databind.JsonNode;
import nl.jixxed.eliteodysseymaterials.enums.ContainerTarget;
import nl.jixxed.eliteodysseymaterials.enums.Good;
import nl.jixxed.eliteodysseymaterials.enums.Material;
import nl.jixxed.eliteodysseymaterials.models.Container;

import java.util.Iterator;
import java.util.Map;

public class GoodParser extends Parser {
    @Override
    public void parse(final Iterator<JsonNode> items, final ContainerTarget containerTarget, final Map<? extends Material, Container> knownMap, final Map<String, Container> unknownMap) {
        items.forEachRemaining(itemNode ->
        {
            final String name = itemNode.get("Name").asText();
            final Good good = Good.forName(name);
            final int amount = itemNode.get("Count").asInt();
            if (Good.UNKNOWN.equals(good)) {
                System.out.println("Unknown Good detected: " + itemNode.toPrettyString());
                final String nameLocalised = itemNode.get("Name_Localised") != null ? itemNode.get("Name_Localised").asText() : name;
                final Container container = getOrCreateContainer(unknownMap, name + ":" + nameLocalised);
                //stack values as items occur multiple times in the json
                container.setValue(container.getValue(containerTarget) + amount, containerTarget);
            } else {
                final Container container = knownMap.get(good);
                //stack values as items occur multiple times in the json
                container.setValue(container.getValue(containerTarget) + amount, containerTarget);
            }
        });
    }
}
