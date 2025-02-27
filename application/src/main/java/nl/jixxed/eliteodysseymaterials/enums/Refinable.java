package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum Refinable {
    BAUXITE_ALUMINIUM(Commodity.BAUXITE, Commodity.ALUMINIUM),
    BERTRANDITE_BERYLLIUM(Commodity.BERTRANDITE, Commodity.BERYLLIUM),
    COLTAN_TANTALUM(Commodity.COLTAN, Commodity.TANTALUM),
    GALLITE_GALLIUM(Commodity.GALLITE, Commodity.GALLIUM),
    HAEMATITE_STEEL(Commodity.HAEMATITE, Commodity.STEEL),
    INDITE_INDIUM(Commodity.INDITE, Commodity.INDIUM),
    LEPIDOLITE_LITHIUM(Commodity.LEPIDOLITE, Commodity.LITHIUM),
    RUTILE_TITANIUM(Commodity.RUTILE, Commodity.TITANIUM),
    URANINITE_URANIUM(Commodity.URANINITE, Commodity.URANIUM);

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
