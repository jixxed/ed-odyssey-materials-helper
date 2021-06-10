package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.databind.JsonNode;
import nl.jixxed.eliteodysseymaterials.enums.ContainerTarget;
import nl.jixxed.eliteodysseymaterials.enums.Data;
import nl.jixxed.eliteodysseymaterials.enums.Material;
import nl.jixxed.eliteodysseymaterials.models.Container;

import java.util.Iterator;
import java.util.Map;

public class DataParser extends Parser {
    @Override
    public void parse(final Iterator<JsonNode> datas, final ContainerTarget containerTarget, final Map<? extends Material, Container> knownMap, final Map<String, Container> unknownMap) {
        datas.forEachRemaining(dataNode ->
        {
            final String name = dataNode.get("Name").asText();
            final Data data = Data.forName(name);
            if (Data.UNKNOWN.equals(data)) {
                System.out.println("Unknown Data detected: " + dataNode.toPrettyString());
                final String nameLocalised = dataNode.get("Name_Localised") != null ? dataNode.get("Name_Localised").asText() : name;
                getOrCreateContainer(unknownMap, name + ":" + nameLocalised).setValue(dataNode.get("Count").asInt(), containerTarget);
            } else {
                knownMap.get(data).setValue(dataNode.get("Count").asInt(), containerTarget);
            }
        });
    }
}
