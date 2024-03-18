package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileProcessorTest {
    @Test
    void test() throws JsonProcessingException {
        String line = "{ \"timestamp\":\"2024-03-17T17:05:36Z\", \"event\":\"MissionFailed\", \"Name\":\"Mission_OnFoot_Salvage_MB_name\", \"\":\"Grab the Circuit Board from a crash site\", \"MissionID\":957903110 }";
        ObjectMapper objectMapper = new ObjectMapper();
        final JsonNode jsonNode = objectMapper.readTree(line);
        final String s = FileProcessor.removeBugs(jsonNode);
        assertEquals("{\"timestamp\":\"2024-03-17T17:05:36Z\",\"event\":\"MissionFailed\",\"Name\":\"Mission_OnFoot_Salvage_MB_name\",\"MissionID\":957903110,\"LocalisedName\":\"Grab the Circuit Board from a crash site\"}", s);
    }
}