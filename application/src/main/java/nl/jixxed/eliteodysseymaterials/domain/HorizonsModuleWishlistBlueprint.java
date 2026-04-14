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
import com.fasterxml.jackson.annotation.JsonProperty;
import io.reactivex.rxjava3.annotations.Nullable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;
@Slf4j
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public final class HorizonsModuleWishlistBlueprint extends HorizonsWishlistBlueprint {
    private HorizonsBlueprintType blueprintType;
    private HorizonsBlueprintType experimentalEffect;
    //no longer persisting the data
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Map<HorizonsBlueprintGrade, Integer> blueprintGradeRolls;
    @Nullable
    private Map<HorizonsBlueprintGrade, Double> percentageToComplete;

    public Map<HorizonsBlueprintGrade, Double> getPercentageToComplete() {
        return percentageToComplete != null ? percentageToComplete : blueprintGradeRolls.entrySet().stream().map(entry -> Map.entry(entry.getKey(), entry.getValue() > 0 ? 1D : 0D)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public HorizonsModuleWishlistBlueprint(HorizonsBlueprintType blueprintType, Map<HorizonsBlueprintGrade, Double> percentageToComplete) {
        this.blueprintType = blueprintType;
        this.percentageToComplete = percentageToComplete;
        bugfix(getRecipeName(), experimentalEffect);
    }

    public void setBlueprintGradePercentageToComplete(final HorizonsBlueprintGrade grade, final Double percentage) {
        if (this.percentageToComplete != null && percentage.equals(0D)) {
            this.percentageToComplete.remove(grade);
            return;
        } else if (this.percentageToComplete != null && percentage > 0D) {
            this.percentageToComplete.put(grade, percentage);
            return;
        }
        throw new IllegalArgumentException("Cannot set percentage to complete for grade: " + grade.name() + "/" + percentage);
    }

    @JsonIgnore
    public HorizonsBlueprintGrade getMaxSelectedGrade() {
        //get the highest non-zero key
        return getPercentageToComplete().entrySet().stream()
                .filter(entry -> entry.getValue() > 0.0)
                .max(Comparator.comparing(entry -> entry.getKey().getGrade()))
                .map(Map.Entry::getKey)
                .orElse(HorizonsBlueprintGrade.NONE);
    }

    public void setRecipeName(HorizonsBlueprintName recipeName) {
        super.setRecipeName(recipeName);
        bugfix(recipeName, this.experimentalEffect);
    }

    private void bugfix(HorizonsBlueprintName recipeName, HorizonsBlueprintType experimentalEffect) {
        if (HorizonsBlueprintName.MISSILE_RACK.equals(recipeName)) {
            log.debug("HorizonsModuleWishlistBlueprint: Bugfixing recipe name for MISSILE_RACK to DUMBFIRE_MISSILE_RACK");
            super.setRecipeName(HorizonsBlueprintName.DUMBFIRE_MISSILE_RACK);
        }
        if (HorizonsBlueprintName.DUMBFIRE_MISSILE_RACK.equals(getRecipeName()) && HorizonsBlueprintType.DRAG_MUNITION.equals(experimentalEffect)) {
            log.debug("HorizonsModuleWishlistBlueprint: Bugfixing recipe name for DUMBFIRE_MISSILE_RACK to SEEKER_MISSILE_RACK");
            super.setRecipeName(HorizonsBlueprintName.SEEKER_MISSILE_RACK);
        }
    }

    public void setBlueprintType(HorizonsBlueprintType blueprintType) {
        this.blueprintType = blueprintType;
        bugfix(getRecipeName(), experimentalEffect);
    }

    public void setExperimentalEffect(HorizonsBlueprintType experimentalEffect) {
        this.experimentalEffect = experimentalEffect;
        bugfix(getRecipeName(), experimentalEffect);
    }
}
