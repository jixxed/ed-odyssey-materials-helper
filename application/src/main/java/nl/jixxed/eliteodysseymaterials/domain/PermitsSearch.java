package nl.jixxed.eliteodysseymaterials.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsPermitsShow;

@AllArgsConstructor
@Getter
@Setter
public class PermitsSearch {
    private String query;
    private HorizonsPermitsShow permitsShow;

}
