package nl.jixxed.eliteodysseymaterials.enums;

public enum Good implements Material {
    AGRICULTURALPROCESSSAMPLE,
    BIOCHEMICALAGENT,
    BUILDINGSCHEMATIC,
    CALIFORNIUM,
    CASTFOSSIL,
    CHEMICALPROCESSSAMPLE,
    CHEMICALSAMPLE,
    COMPACTLIBRARY,
    COMPRESSIONLIQUEFIEDGAS,
    DEEPMANTLESAMPLE,
    DEGRADEDPOWERREGULATOR,
    GENETICREPAIRMEDS,
    GENETICSAMPLE,
    GMEDS,
    HEALTHMONITOR,
    HUSH,
    INERTIACANISTER,
    INFINITY,
    INORGANICCONTAMINANT,
    INSIGHT,
    INSIGHTDATABANK,
    INSIGHTENTERTAINMENTSUITE,
    IONISEDGAS,
    LARGECAPACITYPOWERREGULATOR,
    LAZARUS,
    MICROBIALINHIBITOR,
    MUTAGENICCATALYST,
    NUTRITIONALCONCENTRATE,
    PERSONALCOMPUTER,
    PERSONALDOCUMENTS,
    PETRIFIEDFOSSIL,
    PUSH,
    PYROLYTICCATALYST,
    REFINEMENTPROCESSSAMPLE,
    SHIPSCHEMATIC,
    SUITSCHEMATIC,
    SURVEILLANCEEQUIPMENT,
    SYNTHETICGENOME,
    SYNTHETICPATHOGEN,
    TRUEFORMFOSSIL,
    UNIVERSALTRANSLATOR,
    VEHICLESCHEMATIC,
    WEAPONSCHEMATIC,
    UNKNOWN;

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
        return "material.good." + this.toString().toLowerCase();
    }

    @Override
    public boolean isUnknown() {
        return this == Good.UNKNOWN;
    }
}
