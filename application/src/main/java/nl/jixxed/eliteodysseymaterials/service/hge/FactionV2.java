package nl.jixxed.eliteodysseymaterials.service.hge;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import nl.jixxed.eliteodysseymaterials.enums.Allegiance;
import nl.jixxed.eliteodysseymaterials.enums.Government;
import nl.jixxed.eliteodysseymaterials.enums.State;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(access = AccessLevel.PUBLIC)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FactionV2 {
    private String name;
    private Double influence;
    private State state;
    private Allegiance allegiance;
    private Government government;
    private Set<State> pendingStates;
    private Set<State> activeStates;
    private Set<State> recoveringStates;
    private boolean leading;
}
