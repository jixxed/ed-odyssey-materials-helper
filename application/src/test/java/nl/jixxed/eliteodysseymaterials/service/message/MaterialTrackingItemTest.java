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