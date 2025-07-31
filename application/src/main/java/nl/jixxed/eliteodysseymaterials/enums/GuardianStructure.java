package nl.jixxed.eliteodysseymaterials.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;

@AllArgsConstructor
public class GuardianStructure {

    @Getter
    private final StarSystem starSystem;
    @Getter
    private final String body;
    @Getter
    private final GuardianStructureLayout layout;


}
