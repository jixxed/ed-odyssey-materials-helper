package nl.jixxed.eliteodysseymaterials.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.enums.MaterialShow;
import nl.jixxed.eliteodysseymaterials.enums.MaterialSort;

@AllArgsConstructor
@Getter
@Setter
public class Search {
    private String query;
    private MaterialSort materialSort;
    private MaterialShow materialShow;

}
