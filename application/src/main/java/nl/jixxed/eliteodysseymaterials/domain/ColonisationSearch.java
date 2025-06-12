package nl.jixxed.eliteodysseymaterials.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsColonisationShow;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsColonisationSort;

@AllArgsConstructor
@Getter
@Setter
public class ColonisationSearch {
    private String query;
    private HorizonsColonisationSort colonisationSort;
    private HorizonsColonisationShow colonisationShow;

}
