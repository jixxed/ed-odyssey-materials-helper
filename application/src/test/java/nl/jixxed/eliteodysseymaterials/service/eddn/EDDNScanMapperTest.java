package nl.jixxed.eliteodysseymaterials.service.eddn;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.scan.Message;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.scan.Parent;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Scan.Scan;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class EDDNScanMapperTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    {
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.registerModule(new Jdk8Module().configureAbsentsAsNulls(true));
    }

    @Test
    void testMappingParents() throws IOException {
        final Scan scan = this.objectMapper.readValue(EDDNScanMapperTest.class.getResourceAsStream("/eddn/scan_parents.json"), Scan.class);

        final Message message = EDDNScanMapper.mapToEDDN(scan, Expansion.ODYSSEY);
        System.out.println(this.objectMapper.writeValueAsString(message));
        Assertions.assertThat(message.getParents()).contains(List.of(new Parent.ParentBuilder().withPlanet(BigInteger.valueOf(6L)).build(),new Parent.ParentBuilder().withStar(BigInteger.valueOf(0L)).build()));

    }

    @Test
    void testMappingNoParents() throws IOException {
        final Scan scan = this.objectMapper.readValue(EDDNScanMapperTest.class.getResourceAsStream("/eddn/scan_no_parents.json"), Scan.class);

        final Message message = EDDNScanMapper.mapToEDDN(scan, Expansion.ODYSSEY);
        System.out.println(this.objectMapper.writeValueAsString(message));
        Assertions.assertThat(message.getParents()).isEmpty();
    }


}
