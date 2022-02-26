package nl.jixxed.eliteodysseymaterials.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import nl.jixxed.eliteodysseymaterials.enums.EngineerState;

@Data
@AllArgsConstructor
public final class EngineerStatus {
    private EngineerState engineerState;
    private Integer rank;
    private Integer progress;
}
