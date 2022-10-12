package nl.jixxed.eliteodysseymaterials.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsCommoditiesShow;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsCommoditiesSort;

@AllArgsConstructor
@Getter
@Setter
public class CommoditiesSearch {
    private String query;
    private HorizonsCommoditiesSort commoditiesSort;
    private HorizonsCommoditiesShow commoditiesShow;

}
