package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum Refinable {
    BAUXITE_ALUMINIUM(RegularCommodity.BAUXITE, RegularCommodity.ALUMINIUM),
    BERTRANDITE_BERYLLIUM(RegularCommodity.BERTRANDITE, RegularCommodity.BERYLLIUM),
    COLTAN_TANTALUM(RegularCommodity.COLTAN, RegularCommodity.TANTALUM),
    GALLITE_GALLIUM(RegularCommodity.GALLITE, RegularCommodity.GALLIUM),
    HAEMATITE_STEEL(RegularCommodity.HAEMATITE, RegularCommodity.STEEL),
    INDITE_INDIUM(RegularCommodity.INDITE, RegularCommodity.INDIUM),
    LEPIDOLITE_LITHIUM(RegularCommodity.LEPIDOLITE, RegularCommodity.LITHIUM),
    RUTILE_TITANIUM(RegularCommodity.RUTILE, RegularCommodity.TITANIUM),
    URANINITE_URANIUM(RegularCommodity.URANINITE, RegularCommodity.URANIUM);

    private final Commodity commodityFrom;
    private final Commodity commodityTo;

    public static Optional<Refinable> getRefinable(final Commodity commodity) {
        if (commodity.getCommodityType().equals(CommodityType.METALS) || commodity.getCommodityType().equals(CommodityType.MINERALS)) {
            for (final Refinable refinable : Refinable.values()) {
                if (refinable.commodityFrom.equals(commodity) || refinable.commodityTo.equals(commodity)) {
                    return Optional.of(refinable);
                }
            }
        }
        return Optional.empty();
    }

    public Integer getCommodityIn() {
        return 2;
    }

    public Integer getCommodityOut() {
        return 1;
    }
}
