package nl.jixxed.eliteodysseymaterials.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ShipConfigurationSlot {
    @Getter
    private Integer index;
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;
    @Getter
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ShipConfigurationModification> modification = new ArrayList<>();
    @Getter
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ShipConfigurationExperimentalEffect> experimentalEffect = new ArrayList<>();
}
