package nl.jixxed.eliteodysseymaterials.domain.ships;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

@RequiredArgsConstructor
public enum ArmourType {
    LIGHTWEIGHT_ALLOY(ArmourMainType.LIGHTWEIGHT_ALLOY, ArmourSubType.NORMAL),
    REINFORCED_ALLOY(ArmourMainType.REINFORCED_ALLOY, ArmourSubType.NORMAL),
    MILITARY_GRADE_COMPOSITE(ArmourMainType.MILITARY_GRADE_COMPOSITE, ArmourSubType.NORMAL),
    MIRRORED_SURFACE_COMPOSITE(ArmourMainType.MIRRORED_SURFACE_COMPOSITE, ArmourSubType.NORMAL),
    REACTIVE_SURFACE_COMPOSITE(ArmourMainType.REACTIVE_SURFACE_COMPOSITE, ArmourSubType.NORMAL),
    LIGHTWEIGHT_ALLOY_ABLATIVE(ArmourMainType.LIGHTWEIGHT_ALLOY, ArmourSubType.ABLATIVE),
    REINFORCED_ALLOY_ABLATIVE(ArmourMainType.REINFORCED_ALLOY, ArmourSubType.ABLATIVE),
    MILITARY_GRADE_COMPOSITE_ABLATIVE(ArmourMainType.MILITARY_GRADE_COMPOSITE, ArmourSubType.ABLATIVE),
    MIRRORED_SURFACE_COMPOSITE_ABLATIVE(ArmourMainType.MIRRORED_SURFACE_COMPOSITE, ArmourSubType.ABLATIVE),
    REACTIVE_SURFACE_COMPOSITE_ABLATIVE(ArmourMainType.REACTIVE_SURFACE_COMPOSITE, ArmourSubType.ABLATIVE);

    @Getter
    private final ArmourMainType MainType;
    @Getter
    private final ArmourSubType subType;

    public String getLocalizationKey() {
        return "ships.module.name.armour." + this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }
}
