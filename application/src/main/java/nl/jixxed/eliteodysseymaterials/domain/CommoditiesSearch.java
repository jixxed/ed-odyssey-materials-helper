package nl.jixxed.eliteodysseymaterials.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.enums.CommoditiesShow;
import nl.jixxed.eliteodysseymaterials.enums.CommoditiesSort;

@AllArgsConstructor
@Getter
@Setter
public class CommoditiesSearch {
    private String query;
    private CommoditiesSort commoditiesSort;
    private CommoditiesShow commoditiesShow;

}
