/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.service.eddn;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.fsdjump.Message;
import nl.jixxed.eliteodysseymaterials.schemas.journal.FSDJump.FSDJump;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Optional;

public class EDDNFSDJumpMapperTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    {
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.registerModule(new Jdk8Module().configureAbsentsAsNulls(true));
    }

    @Test
    void testMappingFSDJump() throws IOException {
        final FSDJump fsdJump = this.objectMapper.readValue(EDDNScanMapperTest.class.getResourceAsStream("/eddn/fsdjump.json"), FSDJump.class);

        final Message message = EDDNFSDJumpMapper.mapToEDDN(fsdJump, Expansion.ODYSSEY);
        System.out.println(this.objectMapper.writeValueAsString(message));
        Assertions.assertThat(message.getPowerplayState()).isEqualTo(Optional.of("Exploited"));
        Assertions.assertThat(message.getFactions().get().get(1).getPendingStates().get().get(0).getTrend()).isEqualTo(0);
        Assertions.assertThat(message.getFactions().get().get(1).getPendingStates().get().get(0).getState()).isEqualTo("Outbreak");
        Assertions.assertThat(message.getFactions().get().get(1).getRecoveringStates().get().get(0).getTrend()).isEqualTo(0);
        Assertions.assertThat(message.getFactions().get().get(1).getRecoveringStates().get().get(0).getState()).isEqualTo("Election");

    }
}
