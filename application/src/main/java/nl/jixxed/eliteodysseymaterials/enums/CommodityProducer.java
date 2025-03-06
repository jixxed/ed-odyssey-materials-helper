package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public enum CommodityProducer {
    HIGH_TECH(RegularCommodity.ADVANCEDCATALYSERS);

    private final List<Commodity> commodities = new ArrayList<>();

    CommodityProducer(Commodity... commodities) {
        this.commodities.addAll(Arrays.asList(commodities));
    }
}
