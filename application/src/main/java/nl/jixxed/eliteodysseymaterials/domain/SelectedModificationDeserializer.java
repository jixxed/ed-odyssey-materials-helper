/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
