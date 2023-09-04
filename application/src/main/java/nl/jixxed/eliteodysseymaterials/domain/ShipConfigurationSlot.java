package nl.jixxed.eliteodysseymaterials.domain;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ShipConfigurationSlot {
    @Getter
    private Integer index;
    @Getter
    private String internalName;
    @Getter
    private List<ShipConfigurationModification> modification;
    @Getter
    private List<ShipConfigurationExperimentalEffect> experimentalEffect;
}
