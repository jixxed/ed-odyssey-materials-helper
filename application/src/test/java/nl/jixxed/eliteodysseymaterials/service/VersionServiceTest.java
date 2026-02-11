package nl.jixxed.eliteodysseymaterials.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VersionServiceTest {

    @Test
    void isNewer() {
        var isNewer = VersionService.isEqualOrNewer("3.3.5", "3.3.4");
        assertTrue(isNewer);
    }
    @Test
    void isOlder() {
        var isOlder = VersionService.isEqualOrNewer("3.3.4", "3.3.5");
        assertFalse(isOlder);
    }
    @Test
    void isEqual() {
        var isEqual = VersionService.isEqualOrNewer("3.3.4", "3.3.4");
        assertTrue(isEqual);
    }
}