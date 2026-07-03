/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.constants.horizons;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.helper.ModifierFunctionHelper;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MercenaryBlueprints {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> fromJson(String data) {
        try {
            MercBlueprintDTO dto = OBJECT_MAPPER.readValue(data, MercBlueprintDTO.class);
            return dto.toBlueprints();
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize mercenary blueprints", e);
        }
    }

    // Top-level DTO: name + blueprints map keyed by type->grade
    static class MercBlueprintDTO {
        String name;
        Map<String, Map<String, GradeDataDTO>> blueprints;

        @JsonCreator
        MercBlueprintDTO(
                @JsonProperty("name") String name,
                @JsonProperty("blueprints") Map<String, Map<String, GradeDataDTO>> blueprints
        ) {
            this.name = name != null ? name : "";
            this.blueprints = blueprints != null ? blueprints : Map.of();
        }

        Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> toBlueprints() {
            HorizonsBlueprintName blueprintName = BlueprintNameResolver.resolve(name);
            Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> result = new HashMap<>();
            for (Map.Entry<String, Map<String, GradeDataDTO>> moduleEntry : blueprints.entrySet()) {
                HorizonsBlueprintType blueprintType = toBlueprintType(moduleEntry.getKey());
                Map<HorizonsBlueprintGrade, HorizonsBlueprint> gradeMap = new HashMap<>();
                for (Map.Entry<String, GradeDataDTO> gradeEntry : moduleEntry.getValue().entrySet()) {
                    HorizonsBlueprintGrade blueprintGrade = toBlueprintGrade(gradeEntry.getKey());
                    gradeMap.put(blueprintGrade, gradeEntry.getValue().toBlueprint(blueprintName, blueprintType, blueprintGrade));
                }
                result.put(blueprintType, gradeMap);
            }
            return result;
        }
    }

    // Simplified grade DTO — name/type/grade are resolved from the parent context
    record GradeDataDTO(
            Map<String, Integer> materials,
            Map<String, ModifierDataDTO> modifiers,
            List<String> engineers
    ) {
        @JsonCreator
        GradeDataDTO(
                @JsonProperty("materials") Map<String, Integer> materials,
                @JsonProperty("modifiers") Map<String, ModifierDataDTO> modifiers,
                @JsonProperty("engineers") List<String> engineers
        ) {
            this.materials = materials != null ? materials : Map.of();
            this.modifiers = modifiers != null ? modifiers : Map.of();
            this.engineers = engineers != null ? engineers : List.of();
        }

        HorizonsModuleBlueprint toBlueprint(HorizonsBlueprintName blueprintName,
                                            HorizonsBlueprintType blueprintType,
                                            HorizonsBlueprintGrade blueprintGrade) {
            Map<Material, Integer> materialMap = materials().entrySet().stream()
                    .collect(java.util.stream.Collectors.toMap(
                            e -> MaterialResolver.resolve(e.getKey()),
                            Map.Entry::getValue
                    ));

            Map<HorizonsModifier, HorizonsModifierValue> modifierMap = modifiers().entrySet().stream()
                    .collect(java.util.stream.Collectors.toMap(
                            e -> HorizonsModifier.forInternalName(e.getKey()),
                            e -> e.getValue().toModifier()
                    ));

            List<Engineer> engineerList = engineers().stream()
                    .map(Engineer::valueOf)
                    .toList();

            return new HorizonsModuleBlueprint(
                    blueprintName, blueprintType, blueprintGrade,
                    materialMap, modifierMap, engineerList
            );
        }
    }

    // Modifier data DTO
    record ModifierDataDTO(
            String displayValue,
            boolean positive,
            double start,
            double end,
            double value,
            String fromRatio,
            String toRatio,
            boolean valueBool,
            HorizonsBiFunction.CalculationType calculationType
    ) {
        @JsonCreator
        ModifierDataDTO(
                @JsonProperty("displayValue") String displayValue,
                @JsonProperty("positive") boolean positive,
                @JsonProperty("start") double start,
                @JsonProperty("end") double end,
                @JsonProperty("value") double value,
                @JsonProperty("fromRatio") String fromRatio,
                @JsonProperty("toRatio") String toRatio,
                @JsonProperty("valueBool") boolean valueBool,
                @JsonProperty("calculationType") HorizonsBiFunction.CalculationType calculationType
        ) {
            this.displayValue = displayValue != null ? displayValue : "";
            this.positive = positive;
            this.start = start;
            this.end = end;
            this.value = value;
            this.fromRatio = fromRatio;
            this.toRatio = toRatio;
            this.valueBool = valueBool;
            this.calculationType = calculationType != null ? calculationType : HorizonsBiFunction.CalculationType.PERCENTAGE_POSITIVE;
        }

       public HorizonsModifierValue<?> toModifier() {
            if (calculationType == HorizonsBiFunction.CalculationType.BOOL) {
                return new HorizonsBooleanModifierValue(displayValue, positive, createBooleanFunction());
            }
            return new HorizonsNumberModifierValue(displayValue, positive,
                    createFunction());
        }

        private @NonNull HorizonsBiFunction<Boolean> createBooleanFunction() {
            return
                    ModifierFunctionHelper.bool(valueBool);
        }

        private HorizonsBiFunction<Double> createFunction() {
            return switch (calculationType) {
                case PERCENTAGE_POSITIVE -> ModifierFunctionHelper.percentagePositive(start, end);
                case PERCENTAGE_NEGATIVE -> ModifierFunctionHelper.percentageNegative(start, end);
                case HULL_BOOST_POSITIVE -> ModifierFunctionHelper.hullBoostPositive(start, end);
                case HULL_BOOST_NEGATIVE -> ModifierFunctionHelper.hullBoostNegative(start, end);
                case SHIELD_BOOST_POSITIVE -> ModifierFunctionHelper.shieldBoostPositive(start, end);
                case SHIELD_BOOST_NEGATIVE -> ModifierFunctionHelper.shieldBoostNegative(start, end);
                case RESISTANCE_POSITIVE -> ModifierFunctionHelper.resistancePositive(start, end);
                case RESISTANCE_NEGATIVE -> ModifierFunctionHelper.resistanceNegative(start, end);
                case PLUS -> ModifierFunctionHelper.plus(value);
                case MINUS -> ModifierFunctionHelper.minus(value);
                case DAMAGE_RATIO_FIXED -> ModifierFunctionHelper.damageRatio(HorizonsModifier.forInternalName(fromRatio), HorizonsModifier.forInternalName(toRatio), value);
                case DAMAGE_RATIO_RANGE -> ModifierFunctionHelper.damageRatio(HorizonsModifier.forInternalName(fromRatio), HorizonsModifier.forInternalName(toRatio), start, end);
                case BOOL -> throw new IllegalStateException("BOOL should use createBooleanFunction");
            };
        }
    }

    private static HorizonsBlueprintType toBlueprintType(String type) {
        HorizonsBlueprintType result = HorizonsBlueprintType.forName(type);
        if (result != null) return result;
        return HorizonsBlueprintType.forName(type.toUpperCase());
    }

    private static HorizonsBlueprintGrade toBlueprintGrade(String grade) {
        int gradeNum = Integer.parseInt(grade);
        return HorizonsBlueprintGrade.forDigit(gradeNum);
    }
}
