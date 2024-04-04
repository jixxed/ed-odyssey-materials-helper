package nl.jixxed.eliteodysseymaterials.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterialsShow;

@AllArgsConstructor
@Getter
@Setter
public class HorizonsMaterialsSearch {
    private String query;
    private HorizonsMaterialsShow materialsShow;

}
