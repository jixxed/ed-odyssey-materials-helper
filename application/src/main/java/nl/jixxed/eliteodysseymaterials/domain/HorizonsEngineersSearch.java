package nl.jixxed.eliteodysseymaterials.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsEngineersShow;

@AllArgsConstructor
@Getter
@Setter
public class HorizonsEngineersSearch {
    private String query;
    private HorizonsEngineersShow engineersShow;

}
