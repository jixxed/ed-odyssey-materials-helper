package nl.jixxed.eliteodysseymaterials.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Locale;

@AllArgsConstructor
@Getter
public enum ApplicationLocale {
    ENGLISH(Locale.ENGLISH, "eng", true),
    GERMAN(Locale.GERMAN, "deu", true),
    FRENCH(Locale.FRENCH, "fra", true),
    PORTUGUESE(Locale.forLanguageTag("pt"), "por", true),
    SPANISH(Locale.forLanguageTag("es"), "spa", true),
    RUSSIAN(Locale.forLanguageTag("ru"), "rus", true),
    CHINESE(Locale.CHINESE, "chi", false);

    private final Locale locale;
    private final String iso6392B;
    private final boolean ar;

    public String getLocalizationKey() {
        return "application.language." + this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }

}
