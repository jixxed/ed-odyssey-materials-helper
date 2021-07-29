package nl.jixxed.eliteodysseymaterials.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.enums.Show;
import nl.jixxed.eliteodysseymaterials.enums.Sort;

@AllArgsConstructor
@Getter
@Setter
public class Search {
    private String query;
    private Sort sort;
    private Show show;

}
