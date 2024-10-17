package nl.jixxed.eliteodysseymaterials.service.eddn;

import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.schemas.journal.FCMaterials.Item;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EDDNFCMaterialsJournalMapperTest {

    @Test
    void mapToEDDN() {
        // Arrange
        final var fcmaterials = new nl.jixxed.eliteodysseymaterials.schemas.journal.FCMaterials.FCMaterials();
        fcmaterials.setTimestamp(LocalDateTime.now());
        final Item item = new Item();
        item.setDemand(BigInteger.ONE);
        item.setId(BigInteger.ONE);
        item.setName("test");
        item.setPrice(BigInteger.ONE);
        item.setStock(BigInteger.ONE);

        fcmaterials.setItems(List.of(item));

        // Act
        final var result = EDDNFCMaterialsJournalMapper.mapToEDDN(fcmaterials, Expansion.ODYSSEY);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getItems().size());
    }
}