package nl.jixxed.eliteodysseymaterials.enums;

import javafx.geometry.Orientation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

@AllArgsConstructor
@Getter
public enum MaterialOrientation {
    HORIZONTAL(Orientation.HORIZONTAL),
    VERTICAL(Orientation.VERTICAL);

    private final Orientation orientation;

    public String getLocalizationKey() {
        return "application.orientation." + this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }

}
