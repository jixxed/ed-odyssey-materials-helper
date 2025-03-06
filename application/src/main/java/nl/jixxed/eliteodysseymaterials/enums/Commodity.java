package nl.jixxed.eliteodysseymaterials.enums;

public sealed interface Commodity extends HorizonsMaterial permits RegularCommodity, RareCommodity {

    @Override
    default String getLocalizationKey() {
        return "material.commodity." + this.name().toLowerCase();
    }

    default String getDescriptionLocalizationKey() {
        return "material.commodity.description." + this.name().toLowerCase();
    }

    @Override
    default HorizonsMaterialType getMaterialType() {
        return HorizonsMaterialType.COMMODITY;
    }

    static Commodity forName(final String name) {
        try {
            return RareCommodity.valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException ex) {
            return RegularCommodity.forName(name);
        }
    }

    @Override
    default Rarity getRarity() {
        return Rarity.UNKNOWN;
    }

    @Override
    default int getMaxAmount() {
        return 999;
    }

    @Override
    default HorizonsStorageType getStorageType() {
        return HorizonsStorageType.COMMODITY;
    }
    CommodityType getCommodityType();

}
