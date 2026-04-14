/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
    @Test
    void test2() throws JsonProcessingException {
        String line = "{ \"timestamp\":\"2024-03-17T17:05:36Z\", \"event\":\"MissionFailed\", \"Name\":\"Mission_OnFoot_Salvage_MB_name\", \"LocalisedName\":\"Grab the Circuit Board from a crash site\", \"MissionID\":957903110 }";
        ObjectMapper objectMapper = new ObjectMapper();
        final JsonNode jsonNode = objectMapper.readTree(line);
        final String s = FileProcessor.removeBugs(jsonNode);
        assertEquals("{\"timestamp\":\"2024-03-17T17:05:36Z\",\"event\":\"MissionFailed\",\"Name\":\"Mission_OnFoot_Salvage_MB_name\",\"LocalisedName\":\"Grab the Circuit Board from a crash site\",\"MissionID\":957903110}", s);
    }
}