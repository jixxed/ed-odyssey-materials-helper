package nl.jixxed.eliteodysseymaterials.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ShipConfigurationOldModule {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;//module

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean legacy;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean powered;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer powerGroup;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long buyPrice;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<HorizonsModifier, Object> modifiers = new HashMap<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ShipConfigurationModification> modification = new ArrayList<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ShipConfigurationExperimentalEffect> experimentalEffect = new ArrayList<>();

    /**
     * Returns the first modification of this slot, or null if there are no modifications.
     *
     * @return the first modification, or null if none exist
     */
    @JsonIgnore
    public ShipConfigurationModification getFirstModification() {
        if (modification.isEmpty()) {
            return null;
        }
        return modification.getFirst();
    }

    /**
     * Returns the first experimental effect of this slot, or null if there are no experimental effects.
     *
     * @return the first experimental effect, or null if none exist
     */
    @JsonIgnore
    public ShipConfigurationExperimentalEffect getFirstExperimentalEffect() {
        if (experimentalEffect.isEmpty()) {
            return null;
        }
        return experimentalEffect.getFirst();
    }
}
