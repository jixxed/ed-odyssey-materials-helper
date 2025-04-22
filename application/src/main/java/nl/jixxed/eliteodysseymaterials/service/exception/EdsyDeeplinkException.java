package nl.jixxed.eliteodysseymaterials.service.exception;

import nl.jixxed.eliteodysseymaterials.service.LocaleService;

public class EdsyDeeplinkException extends RuntimeException {
    private final String errorLocaleKey;

    public EdsyDeeplinkException(final String message, final String errorLocaleKey) {
        super(message);
        this.errorLocaleKey = errorLocaleKey;
    }

    @Override
    public String getLocalizedMessage() {
        return LocaleService.getLocalizedStringForCurrentLocale(errorLocaleKey);
    }

    public LocaleService.LocaleString getLocaleString() {
        return LocaleService.LocaleString.of(errorLocaleKey);
    }
}
