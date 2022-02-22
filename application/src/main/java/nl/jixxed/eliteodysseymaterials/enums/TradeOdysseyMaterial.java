package nl.jixxed.eliteodysseymaterials.enums;

public enum TradeOdysseyMaterial implements OdysseyMaterial {
    ANY_RELEVANT, NOTHING, UNKNOWN;

    public static TradeOdysseyMaterial forName(final String name) {
        try {
            return TradeOdysseyMaterial.valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException ex) {
            return TradeOdysseyMaterial.UNKNOWN;
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
        return this == TradeOdysseyMaterial.UNKNOWN;
    }

    @Override
    public boolean isIllegal() {
        return false;
    }

}
