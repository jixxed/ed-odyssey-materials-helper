package nl.jixxed.eliteodysseymaterials.enums;

import nl.jixxed.eliteodysseymaterials.service.LocaleService;

public enum Consumable implements OdysseyMaterial {
    BYPASS(true),
    HEALTHPACK(false),
    ENERGYCELL(false),
    AMM_GRENADE_EMP(false),
    AMM_GRENADE_FRAG(false),
    AMM_GRENADE_SHIELD(false),
    UNKNOWN(false);

    private final boolean illegal;

    Consumable(final boolean illegal) {
        this.illegal = illegal;
    }

    public static Consumable forName(final String name) {
        try {
            return Consumable.valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException ex) {
            return Consumable.UNKNOWN;
        }
    }

    @Override
    public OdysseyStorageType getStorageType() {
        return OdysseyStorageType.CONSUMABLE;
    }


    @Override
    public String getLocalizationKey() {
        return "material.consumable." + this.toString().toLowerCase();
    }

    @Override
    public boolean isUnknown() {
        return this == Consumable.UNKNOWN;
    }

    @Override
    public boolean isIllegal() {
        return this.illegal;
    }

    @Override
    public String getTypeNameLocalized() {
        return LocaleService.getLocalizedStringForCurrentLocale("material.asset.type.consumable");
    }

}
