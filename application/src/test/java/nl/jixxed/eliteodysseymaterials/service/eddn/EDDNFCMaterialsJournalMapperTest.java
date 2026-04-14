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