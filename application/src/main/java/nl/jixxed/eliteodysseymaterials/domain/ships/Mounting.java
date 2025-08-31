package nl.jixxed.eliteodysseymaterials.domain.ships;

import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIcon;

public enum Mounting {
    FIXED,
    GIMBALLED,
    TURRETED,
    NA;

    public String getShortName(){
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }
    public String getLocalizationKey() {
        return "ships.mounting." + this.name().toLowerCase();
    }
    public boolean isHigher(Mounting mounting) {
        return this.ordinal() > mounting.ordinal();
    }
    public boolean isLower(Mounting mounting) {
        return this.ordinal() < mounting.ordinal();
    }

    public EdAwesomeIcon getIcon() {
        return switch (this){
            case FIXED, NA -> EdAwesomeIcon.SHIPS_FIXED;
            case GIMBALLED -> EdAwesomeIcon.SHIPS_GIMBALLED;
            case TURRETED -> EdAwesomeIcon.SHIPS_TURRETED;
        };
    }
}
