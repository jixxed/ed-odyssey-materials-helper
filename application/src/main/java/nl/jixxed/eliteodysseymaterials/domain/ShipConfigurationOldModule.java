/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonIgnoreProperties(ignoreUnknown = true)
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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private HorizonsBlueprintGrade synthesis;
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
