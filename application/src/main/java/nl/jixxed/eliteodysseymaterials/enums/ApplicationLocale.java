package nl.jixxed.eliteodysseymaterials.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Locale;

@AllArgsConstructor
@Getter
public enum ApplicationLocale {
    ENGLISH(Locale.ENGLISH),
    GERMAN(Locale.GERMAN),
    FRENCH(Locale.FRENCH),
    PORTUGUESE(Locale.forLanguageTag("pt")),
    SPANISH(Locale.forLanguageTag("es")),
    RUSSIAN(Locale.forLanguageTag("ru"));

    private final Locale locale;

    public String getLocalizationKey() {
        return "application.language." + this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }

}
