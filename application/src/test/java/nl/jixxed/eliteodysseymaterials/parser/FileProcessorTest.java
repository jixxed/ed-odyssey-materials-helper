package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class FileProcessorTest {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    void processEngineerProgressMessage() throws IOException {
        final JsonNode jsonNode = OBJECT_MAPPER.readTree("{ \"timestamp\":\"2021-07-08T17:46:33Z\", \"event\":\"EngineerProgress\", \"Engineer\":\"Petra Olmanova\", \"EngineerID\":300130, \"Progress\":\"Invited\" }");
        Assertions.assertThatNoException().isThrownBy(() -> FileProcessor.processEngineerProgressMessage(jsonNode));
    }
}
