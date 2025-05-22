package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.jixxed.eliteodysseymaterials.schemas.capi.CapiFleetcarrier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.InputStream;

public class MessageHandlerTest {
    private static final ObjectMapper OBJECT_MAPPER2 = new ObjectMapper();

    static {
        OBJECT_MAPPER2.registerModule(new JavaTimeModule());
        OBJECT_MAPPER2.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
    }

    @ParameterizedTest
    @ValueSource(strings = {"test1.json", "test2.json", "test3.json", "test4.json", "test5.json", "test6.json", "test7.json"})
    public void canMapFCFile(String file) {
        final InputStream resourceAsStream = MessageHandlerTest.class.getResourceAsStream("/parser/capifc/" + file);
        if (resourceAsStream == null) {
            throw new IllegalArgumentException("Resource not found: " + file);
        }
        try (resourceAsStream) {
            final String json = new String(resourceAsStream.readAllBytes());
            OBJECT_MAPPER2.readValue(json, CapiFleetcarrier.class);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }
}
