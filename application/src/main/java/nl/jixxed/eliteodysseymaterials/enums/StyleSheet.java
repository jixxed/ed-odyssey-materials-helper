package nl.jixxed.eliteodysseymaterials.enums;

import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

@RequiredArgsConstructor
public enum StyleSheet {
    DEFAULT("/css/compiled/main.css"),
    COLORBLIND("/css/compiled/colorblind.css");

    private final String path;

    public String getStyleSheet() {
        return path;
    }

    public String getLocalizationKey() {
        return "stylesheet.name." + this.name().toLowerCase();
    }


    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }
}
