package nl.jixxed.eliteodysseymaterials.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Locale;

@AllArgsConstructor
@Getter
public enum ApplicationLocale {
    ENGLISH(Locale.ENGLISH, "eng"),
    GERMAN(Locale.GERMAN, "deu"),
    FRENCH(Locale.FRENCH, "fra"),
    PORTUGUESE(Locale.forLanguageTag("pt"), "por"),
    SPANISH(Locale.forLanguageTag("es"), "spa"),
    RUSSIAN(Locale.forLanguageTag("ru"), "rus");

    private final Locale locale;
    private final String iso6392B;

    public String getLocalizationKey() {
        return "application.language." + this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }

}
