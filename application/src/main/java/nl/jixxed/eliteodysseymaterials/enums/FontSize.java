package nl.jixxed.eliteodysseymaterials.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

@AllArgsConstructor
@Getter
public enum FontSize {
    EXTRA_SMALL(10),
    SMALL(12),
    NORMAL(14),
    LARGE(16),
    EXTRA_LARGE(18);

    private final Integer size;

    public String getLocalizationKey() {
        return "application.font.size." + this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }

}
