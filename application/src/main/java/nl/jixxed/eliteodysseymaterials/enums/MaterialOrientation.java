package nl.jixxed.eliteodysseymaterials.enums;

import javafx.geometry.Orientation;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

public enum MaterialOrientation {
    HORIZONTAL(Orientation.HORIZONTAL), VERTICAL(Orientation.VERTICAL);
    private final Orientation orientation;

    MaterialOrientation(final Orientation orientation) {
        this.orientation = orientation;
    }

    public Orientation getOrientation() {
        return this.orientation;
    }

    public String getLocalizationKey() {
        return "application.orientation." + this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }

}
