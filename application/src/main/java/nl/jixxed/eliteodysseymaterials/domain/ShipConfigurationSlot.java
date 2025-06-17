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
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ShipConfigurationSlot {

    private Integer index;

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
    private List<ShipConfigurationModification> modification = new ArrayList<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ShipConfigurationExperimentalEffect> experimentalEffect = new ArrayList<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final Map<HorizonsModifier, Object> modifiers = new HashMap<>();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ShipConfigurationOldModule oldModule;

    public ShipConfigurationSlot cloneShipConfigurationSlot() {
        ShipConfigurationSlot clone = new ShipConfigurationSlot();
        clone.index = this.index;
        clone.id = this.id;
        clone.legacy = this.legacy;
        clone.powered = this.powered;
        clone.powerGroup = this.powerGroup;
        clone.buyPrice = this.buyPrice;
        if (this.oldModule != null) {
            clone.oldModule = new ShipConfigurationOldModule(
                    this.oldModule.getId(),
                    this.oldModule.getLegacy(),
                    this.oldModule.getPowered(),
                    this.oldModule.getPowerGroup(),
                    this.oldModule.getBuyPrice(),
                    this.oldModule.getModifiers().entrySet().stream().collect(Collectors.toMap(Map.Entry<HorizonsModifier, Object>::getKey, Map.Entry<HorizonsModifier, Object>::getValue)),
                    this.oldModule.getModification().stream().map(mod -> new ShipConfigurationModification(mod.getType(), mod.getGrade(), mod.getPercentComplete())).toList(),
                    this.oldModule.getExperimentalEffect().stream().map(mod -> new ShipConfigurationExperimentalEffect(mod.getType())).toList()
            );
        }
        clone.modification.addAll(modification.stream().map(mod -> new ShipConfigurationModification(mod.getType(), mod.getGrade(), mod.getPercentComplete())).toList());
        clone.experimentalEffect.addAll(experimentalEffect.stream().map(mod -> new ShipConfigurationExperimentalEffect(mod.getType())).toList());
        modifiers.entrySet().stream().map(mod -> Map.entry(mod.getKey(), cloneMod(mod.getValue()))).forEach(entry -> clone.modifiers.put(entry.getKey(), entry.getValue()));
        return clone;
    }

    private Object cloneMod(Object value) {
        return switch (value) {
            case Double d -> Double.valueOf(d);
            case String s -> String.valueOf(s);
            case Integer i -> Integer.valueOf(i);
            case Long l -> Long.valueOf(l);
            default -> throw new IllegalStateException("Unexpected legacy modification value type: " + value);
        };
    }

    //    public void trackChanges(){
//        oldModule = new ShipConfigurationOldModule(
//                getId(),
//                getModification().stream().map(mod -> new ShipConfigurationModification(mod.getType(), mod.getGrade(), mod.getPercentComplete())).toList(),
//                getExperimentalEffect().stream().map(mod -> new ShipConfigurationExperimentalEffect(mod.getType())).toList()
//        );
//    }
    public boolean isLegacy() {
        return Boolean.TRUE.equals(legacy);
    }

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
