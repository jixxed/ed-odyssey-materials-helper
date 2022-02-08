package nl.jixxed.eliteodysseymaterials.enums;

public enum TradeMaterial implements Material {
    ANY_RELEVANT, NOTHING, UNKNOWN;

    public static TradeMaterial forName(final String name) {
        try {
            return TradeMaterial.valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException ex) {
            return TradeMaterial.UNKNOWN;
        }
    }

    @Override
    public OdysseyStorageType getStorageType() {
        return OdysseyStorageType.TRADE;
    }

    @Override
    public String getLocalizationKey() {
        return "material.trade." + this.toString().toLowerCase();
    }

    @Override
    public boolean isUnknown() {
        return this == TradeMaterial.UNKNOWN;
    }

    @Override
    public boolean isIllegal() {
        return false;
    }

}
