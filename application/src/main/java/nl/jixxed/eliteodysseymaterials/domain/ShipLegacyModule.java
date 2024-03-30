package nl.jixxed.eliteodysseymaterials.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ShipLegacyModule {

    @EqualsAndHashCode.Include
    private String uuid = UUID.randomUUID().toString();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;//module

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;//module

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<HorizonsModifier, Object> modifiers = new HashMap<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ShipConfigurationModification> modification = new ArrayList<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ShipConfigurationExperimentalEffect> experimentalEffect = new ArrayList<>();
    public ShipLegacyModule(String id,String name, Map<HorizonsModifier, Object> modifiers, List<ShipConfigurationModification> modification, List<ShipConfigurationExperimentalEffect> experimentalEffect) {
        this.id = id;
        this.name = name;
        this.modifiers = modifiers;
        this.modification = modification;
        this.experimentalEffect = experimentalEffect;
    }
    public ShipLegacyModule(ShipModule shipModule) {
        this(
                shipModule.getId(),
                shipModule.getId(),
                shipModule.getAttibutes().stream().map(modifier -> Map.entry(modifier, shipModule.getAttributeValue(modifier))).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)),
                shipModule.getModifications().stream().map(modification -> new ShipConfigurationModification(modification.getModification(), modification.getGrade(), modification.getModificationCompleteness().orElse(BigDecimal.ZERO))).toList(),
                shipModule.getExperimentalEffects().stream().map(ShipConfigurationExperimentalEffect::new).toList()
        );
    }

}
