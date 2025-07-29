package nl.jixxed.eliteodysseymaterials.service;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.enums.ApplicationLocale;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
@Slf4j
class LocaleServiceTest {

    @Test
    void testAllCSVColumnsPresent() {
        ShipModule.getBasicModules();
        //set to last locale to ensure all locales are present
        Locale locale = ApplicationLocale.values()[ApplicationLocale.values().length - 1].getLocale();
        log.info("Locale = {}", locale);
        assertDoesNotThrow(() -> LocaleService.setCurrentLocale(locale));
    }
}