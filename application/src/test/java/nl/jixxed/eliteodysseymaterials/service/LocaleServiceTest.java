package nl.jixxed.eliteodysseymaterials.service;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class LocaleServiceTest {

    @Test
    void testAllCSVColumnsPresent() {
        assertDoesNotThrow(() -> LocaleService.setCurrentLocale(Locale.forLanguageTag("ru")));
    }
}