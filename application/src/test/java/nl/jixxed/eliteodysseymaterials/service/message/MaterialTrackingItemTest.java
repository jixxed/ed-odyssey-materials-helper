/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.service.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.jixxed.eliteodysseymaterials.enums.Data;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MaterialTrackingItemTest {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void jsonMappingTest() throws JsonProcessingException {
        final MaterialTrackingItem item = MaterialTrackingItem.builder()
                .commander("test")
                .odysseyMaterial(Data.ACCIDENTLOGS)
                .body("body")
                .x(1.1)
                .y(1.2)
                .z(-1.3)
                .government("gov")
                .latitude(2.1)
                .longitude(-2.2)
                .primaryEconomy("prime")
                .secondaryEconomy("sec")
                .session("session")
                .amount(123)
                .settlement("settle")
                .security("safe")
                .system("Sol")
                .state("state")
                .timestamp("12-12-12")
                .version("1.80")
                .build();
        final String data = OBJECT_MAPPER.writeValueAsString(item);
        final String expected = "{\"commander\":\"test\",\"timestamp\":\"12-12-12\",\"odysseyMaterial\":\"ACCIDENTLOGS\",\"amount\":123,\"system\":\"Sol\",\"primaryEconomy\":\"prime\",\"secondaryEconomy\":\"sec\",\"government\":\"gov\",\"security\":\"safe\",\"state\":\"state\",\"x\":1.1,\"y\":1.2,\"z\":-1.3,\"latitude\":2.1,\"longitude\":-2.2,\"body\":\"body\",\"settlement\":\"settle\",\"session\":\"session\",\"version\":\"1.80\"}";
        assertThat(data).isEqualTo(expected);

    }
}