package nl.jixxed.eliteodysseymaterials.domain;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.jixxed.eliteodysseymaterials.enums.Modification;

import java.io.IOException;

public class SelectedModificationDeserializer extends JsonDeserializer<SelectedModification[]> {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public SelectedModification[] deserialize(final JsonParser jsonParser, final DeserializationContext ctxt) throws IOException {
        final ObjectCodec oc = jsonParser.getCodec();
        final JsonNode node = oc.readTree(jsonParser);
        final SelectedModification[] retVal = new SelectedModification[4];
        if (node.get(0).isArray() || node.get(1).isArray() || node.get(2).isArray() || node.get(3).isArray()) {
            for (int i = 0; i < 4; i++) {
                final Modification modification1 = OBJECT_MAPPER.readValue(node.get(i).toString(), Modification.class);
                retVal[i] = new SelectedModification(modification1, false);
            }
        } else {
            for (int i = 0; i < 4; i++) {
                retVal[i] = new SelectedModification(node.get(i) != null && node.get(i).get("modification") != null ? OBJECT_MAPPER.readValue(node.get(i).get("modification").toString(), Modification.class) : null, node.get(i) != null && node.get(i).get("present") != null && node.get(i).get("present").asBoolean());
            }
        }

        return retVal;
    }
}
