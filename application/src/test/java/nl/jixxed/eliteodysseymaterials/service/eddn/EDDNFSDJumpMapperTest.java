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
