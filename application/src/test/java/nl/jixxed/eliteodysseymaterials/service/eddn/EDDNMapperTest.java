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