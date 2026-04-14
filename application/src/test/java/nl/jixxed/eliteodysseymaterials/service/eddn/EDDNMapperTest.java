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

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class EDDNMapperTest {

    @Test
    void mapToOptionalEmptyIfEmptyList_withValue() {
        // Arrange
        final var optionalList = Optional.of(List.of("test"));

        // Act
        final var result = EDDNMapper.mapToOptionalEmptyIfEmptyList(optionalList);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("test", result.get().getFirst());
    }
    @Test
    void mapToOptionalEmptyIfEmptyList2_emptyList() {
        // Arrange
        final var optionalList = Optional.of(List.of());

        // Act
        final var result = EDDNMapper.mapToOptionalEmptyIfEmptyList(optionalList);

        // Assert
        assertFalse(result.isPresent());
    }
    @Test
    void mapToOptionalEmptyIfEmptyList3_noList() {
        // Arrange
        final Optional<List<String>> optionalList = Optional.empty();

        // Act
        final var result = EDDNMapper.mapToOptionalEmptyIfEmptyList(optionalList);

        // Assert
        assertFalse(result.isPresent());
    }
}