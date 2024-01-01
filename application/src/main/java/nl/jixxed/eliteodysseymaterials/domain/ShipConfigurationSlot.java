package nl.jixxed.eliteodysseymaterials.domain;

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
public class ShipConfigurationSlot {

    private Integer index;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean legacy;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ShipConfigurationModification> modification = new ArrayList<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ShipConfigurationExperimentalEffect> experimentalEffect = new ArrayList<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final Map<HorizonsModifier, Object> modifiers = new HashMap<>();

    public ShipConfigurationSlot cloneShipConfigurationSlot() {
        ShipConfigurationSlot clone = new ShipConfigurationSlot();
        clone.index = this.index;
        clone.id = this.id;
        clone.legacy = this.legacy;
        clone.modification.addAll(modification.stream().map(mod -> new ShipConfigurationModification(mod.getType(),mod.getGrade(),mod.getPercentComplete())).toList());
        clone.experimentalEffect.addAll(experimentalEffect.stream().map(mod -> new ShipConfigurationExperimentalEffect(mod.getType())).toList());
        modifiers.entrySet().stream().map(mod -> Map.entry(mod.getKey(), cloneMod(mod.getValue()))).forEach(entry-> clone.modifiers.put(entry.getKey(), entry.getValue()));
        return clone;
    }

    private Object cloneMod(Object value) {
        return switch (value){
            case Double d -> Double.valueOf(d);
            case String s -> String.valueOf(s);
            case Integer i -> Integer.valueOf(i);
            case Long l -> Long.valueOf(l);
            default -> throw new IllegalStateException("Unexpected legacy modification value type: " + value);
        };
    }

    public boolean isLegacy() {
        return Boolean.TRUE.equals(legacy);
    }
}
