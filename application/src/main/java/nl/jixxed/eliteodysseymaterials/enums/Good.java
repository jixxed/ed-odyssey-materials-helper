package nl.jixxed.eliteodysseymaterials.enums;

import nl.jixxed.eliteodysseymaterials.service.LocaleService;

public enum Good implements Material {
    AGRICULTURALPROCESSSAMPLE(false),
    BIOCHEMICALAGENT(true),
    BUILDINGSCHEMATIC(false),
    CALIFORNIUM(false),
    CASTFOSSIL(false),
    CHEMICALPROCESSSAMPLE(false),
    CHEMICALSAMPLE(false),
    COMPACTLIBRARY(false),
    COMPRESSIONLIQUEFIEDGAS(false),
    DEEPMANTLESAMPLE(false),
    DEGRADEDPOWERREGULATOR(false),
    GENETICREPAIRMEDS(false),
    GENETICSAMPLE(false),
    GMEDS(false),
    HEALTHMONITOR(false),
    HUSH(true),
    INERTIACANISTER(false),
    INFINITY(false),
    INORGANICCONTAMINANT(true),
    INSIGHT(false),
    INSIGHTDATABANK(false),
    INSIGHTENTERTAINMENTSUITE(false),
    IONISEDGAS(false),
    LARGECAPACITYPOWERREGULATOR(false),
    LAZARUS(true),
    MICROBIALINHIBITOR(false),
    MUTAGENICCATALYST(true),
    NUTRITIONALCONCENTRATE(false),
    PERSONALCOMPUTER(false),
    PERSONALDOCUMENTS(false),
    PETRIFIEDFOSSIL(false),
    PUSH(true),
    PYROLYTICCATALYST(true),
    REFINEMENTPROCESSSAMPLE(false),
    SHIPSCHEMATIC(false),
    SUITSCHEMATIC(false),
    SURVEILLANCEEQUIPMENT(false),
    SYNTHETICGENOME(false),
    SYNTHETICPATHOGEN(true),
    TRUEFORMFOSSIL(false),
    UNIVERSALTRANSLATOR(false),
    VEHICLESCHEMATIC(false),
    WEAPONSCHEMATIC(false),
    UNKNOWN(false);
    private final boolean illegal;

    Good(final boolean illegal) {
        this.illegal = illegal;
    }

    public static Good forName(final String name) {
        try {
            return Good.valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException ex) {
            return Good.UNKNOWN;
        }
    }

    @Override
    public StorageType getStorageType() {
        return StorageType.GOOD;
    }

    @Override
    public String getLocalizationKey() {
        return "material.good." + this.name().toLowerCase();
    }

    @Override
    public boolean isUnknown() {
        return this == Good.UNKNOWN;
    }

    @Override
    public boolean isIllegal() {
        return this.illegal;
    }

    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }
}
