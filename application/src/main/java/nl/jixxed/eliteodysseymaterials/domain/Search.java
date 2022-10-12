package nl.jixxed.eliteodysseymaterials.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterialShow;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterialSort;

@AllArgsConstructor
@Getter
@Setter
public class Search {
    private String query;
    private OdysseyMaterialSort materialSort;
    private OdysseyMaterialShow materialShow;

}
