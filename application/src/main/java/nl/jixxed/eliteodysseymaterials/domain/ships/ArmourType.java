package nl.jixxed.eliteodysseymaterials.domain.ships;

import nl.jixxed.eliteodysseymaterials.service.LocaleService;

public enum ArmourType {
    LIGHTWEIGHT_ALLOY,
    REINFORCED_ALLOY,
    MILITARY_GRADE_COMPOSITE,
    MIRRORED_SURFACE_COMPOSITE,
    REACTIVE_SURFACE_COMPOSITE,
    LIGHTWEIGHT_ALLOY_ABLATIVE,
    REINFORCED_ALLOY_ABLATIVE,
    MILITARY_GRADE_COMPOSITE_ABLATIVE,
    MIRRORED_SURFACE_COMPOSITE_ABLATIVE,
    REACTIVE_SURFACE_COMPOSITE_ABLATIVE;

    public String getLocalizationKey() {
        return "ships.module.name.armour." + this.name().toLowerCase();
    }


    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }
}
